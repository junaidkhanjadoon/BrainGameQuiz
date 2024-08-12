package com.example.brainbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_score);
   getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        CardView ivBee = findViewById(R.id.cvLogo);
        TextView tvQuizComplete = findViewById(R.id.tvQuizComplete);
        TextView tvCategoryScore = findViewById(R.id.tvCategoryScore);
        TextView tvFeedback = findViewById(R.id.tvFeedback);
        Button btnHome = findViewById(R.id.btnHome);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        String quizCategory = intent.getStringExtra("quizCategory");

        tvQuizComplete.setText("Quiz Complete!");
        tvCategoryScore.setText(quizCategory + " Quiz\nScore: " + score + "/" + totalQuestions);

        // Feedback based on score
        if (score >= totalQuestions * 0.8) {
            tvFeedback.setText("Excellent work! Keep it up!");
        } else if (score >= totalQuestions * 0.5) {
            tvFeedback.setText("Good job! Keep up the good work :)");
        } else {
            tvFeedback.setText("You can do better! Keep practicing :)");
        }


        btnHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ResultScoreActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }
}
