package com.example.brainbuzz;

import static com.example.brainbuzz.MainActivity.listOfQuestionAnswers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.brainbuzz.database.DatabaseHelper;
import com.example.brainbuzz.databinding.ActivityQuizBinding;
import com.example.brainbuzz.model.ChoicesModel;
import com.example.brainbuzz.model.HelperClass;
import com.example.brainbuzz.model.HistoryModel;
import com.example.brainbuzz.model.OngoingQuizModel;
import com.example.brainbuzz.model.QuestionModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
    String quizCategory = "";
    ArrayList<QuestionModel> finalList = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private ProgressBar progressBar;
    private DatabaseHelper databaseHelper;
    OngoingQuizModel previousModel;
    private boolean isOngoingQuiz = false; // Flag to determine if it's an ongoing quiz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        databaseHelper = new DatabaseHelper(this);

        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        progressBar = binding.progressBar;

        // Check if the intent contains ongoing quiz data
        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra("ongoingQuiz")) {
                // Load ongoing quiz data
                previousModel = (OngoingQuizModel) getIntent().getSerializableExtra("ongoingQuiz");
                quizCategory = previousModel.getCategory();
                currentQuestionIndex = previousModel.getCurrentQuestionIndex();
                score = previousModel.getScore();
                finalList = previousModel.getRemainingQuestions();
                isOngoingQuiz = true;
                binding.tvLabel.setText(quizCategory);
            } else if (getIntent().hasExtra("quizCategory")) {
                // Load new quiz
                quizCategory = getIntent().getStringExtra("quizCategory");
                binding.tvLabel.setText(quizCategory);
                generateFilteredAndShuffledQuestions(quizCategory);
            }
        }
        // Set the maximum for the progress bar based on the finalList size
        progressBar.setMax(finalList.size());

        displayCurrentQuestion();

        binding.ivExit.setOnClickListener(v -> {
            saveOngoingQuizData();
            finish();
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = binding.radioGroup.getCheckedRadioButtonId();
                if (selectedOptionId == -1) {
                    // Show a message to select an answer
                    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the selected answer
                RadioButton selectedRadioButton = findViewById(selectedOptionId);
                String selectedAnswer = selectedRadioButton.getText().toString();

                // Check if the selected answer is correct
                QuestionModel currentQuestion = finalList.get(currentQuestionIndex);
                if (selectedAnswer.equals(currentQuestion.getAnswer())) {
                    score++;
                }

                // Move to the next question or finish the quiz
                currentQuestionIndex++;
                if (currentQuestionIndex < finalList.size()) {
                    displayCurrentQuestion();
                } else {
                    // Insert the score into the history database
                    insertScoreIntoHistory(score);

                    // Navigate to the ResultScoreActivity
                    Intent intent = new Intent(QuizActivity.this, ResultScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("totalQuestions", finalList.size());
                    intent.putExtra("quizCategory", quizCategory);

                    // Delete ongoing quiz record since the quiz is completed
                    if (previousModel != null) {
                        databaseHelper.deleteOngoingQuiz(previousModel.getId());
                    }

                    startActivity(intent);
                    finish(); // Close the quiz activity
                }
            }
        });
    }

    private void insertScoreIntoHistory(int score) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        HistoryModel history = new HistoryModel();
        history.setUserId(HelperClass.users.getId()); // Replace with actual user ID retrieval logic
        history.setCategory(quizCategory);
        history.setScores(String.valueOf(score));
        history.setDateTime(getCurrentDateTime());

        dbHelper.insertHistory(history);
    }

    private String getCurrentDateTime() {
        // Get the current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex < finalList.size()) {
            QuestionModel currentQuestion = finalList.get(currentQuestionIndex);

            // Set the question text
            binding.tvQuestion.setText(currentQuestion.getQuestion());

            // Get shuffled choices for the current question
            ChoicesModel choices = currentQuestion.getListOfChoices().get(0);

            // Set the choices to the RadioButtons
            binding.option1.setText(choices.getChoice1());
            binding.option2.setText(choices.getChoice2());
            binding.option3.setText(choices.getChoice3());
            binding.option4.setText(choices.getChoice4());

            // Update the question counter
            binding.tvCount.setText((currentQuestionIndex + 1) + "/" + finalList.size());

            // Update the progress bar
            progressBar.setProgress(currentQuestionIndex + 1);

            // Reset the selected RadioButton (if any)
            binding.radioGroup.clearCheck();

            // Update the button text
            if (currentQuestionIndex == finalList.size() - 1) {
                binding.btnNext.setText("Submit");
            } else {
                binding.btnNext.setText("Next");
            }
        }
    }

    private void generateFilteredAndShuffledQuestions(String category) {
        // Step 1: Filter the list of questions by category
        List<QuestionModel> filteredQuestions = listOfQuestionAnswers.stream()
                .filter(question -> question.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        // Step 2: Shuffle the filtered questions
        Collections.shuffle(filteredQuestions);

        // Step 3: Shuffle the choices within each question
        for (QuestionModel question : filteredQuestions) {
            List<ChoicesModel> shuffledChoices = question.getListOfChoices().stream()
                    .map(choices -> {
                        List<String> choicesList = new ArrayList<>();
                        choicesList.add(choices.getChoice1());
                        choicesList.add(choices.getChoice2());
                        choicesList.add(choices.getChoice3());
                        choicesList.add(choices.getChoice4());
                        Collections.shuffle(choicesList);
                        return new ChoicesModel(
                                choicesList.get(0),
                                choicesList.get(1),
                                choicesList.get(2),
                                choicesList.get(3)
                        );
                    })
                    .collect(Collectors.toList());
            question.setListOfChoices((ArrayList<ChoicesModel>) shuffledChoices);
        }

        // Step 4: Select the first 10 questions (or fewer if there are less than 10)
        finalList = filteredQuestions.stream()
                .limit(10)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void saveOngoingQuizData() {
        if (isOngoingQuiz || currentQuestionIndex < finalList.size()) {
            if (previousModel != null) {
                previousModel.setScore(score);
                previousModel.setCurrentQuestionIndex(currentQuestionIndex);
                databaseHelper.updateOngoingQuiz(previousModel);
            } else {
                OngoingQuizModel ongoingQuiz = new OngoingQuizModel(HelperClass.users.getId(), score, quizCategory, currentQuestionIndex, finalList);
                databaseHelper.insertOngoingQuiz(ongoingQuiz);
            }
        }
    }
}
