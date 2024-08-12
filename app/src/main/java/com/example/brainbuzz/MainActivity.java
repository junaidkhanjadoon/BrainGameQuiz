package com.example.brainbuzz;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.pm.PackageManager;

import android.os.Bundle;
import android.view.View;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.brainbuzz.databinding.ActivityMainBinding;
import com.example.brainbuzz.model.ChoicesModel;
import com.example.brainbuzz.model.QuestionModel;


import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    public static ArrayList<QuestionModel> listOfQuestionAnswers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the NavHostFragment and NavController
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Set up MeowBottomNavigation
        setBottomNavigation();

        populateQuestions();

    }

    // Set up MeowBottomNavigation
    private void setBottomNavigation() {

        // Add navigation items to the bottom navigation bar
        binding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        binding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.iv_history));
        binding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.iv_leaderboard));
        binding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_person_24));

        // Navigate to HomeFragment initially
        navController.navigate(R.id.homeFragment);
        binding.bottomNav.bottomNavigation.show(1, true);

        // Set click listener for bottom navigation items
        binding.bottomNav.bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()) {
                    case 1:
                        navController.navigate(R.id.homeFragment);
                        break;

                    case 2:
                        navController.navigate(R.id.historyFragment);
                        break;

                    case 3:
                        navController.navigate(R.id.leaderboardFragment);
                        break;

                    case 4:
                        navController.navigate(R.id.profileFragment);
                        break;
                }

                return null;
            }
        });

        // Set click listeners for custom bottom navigation buttons
        binding.bottomNav.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.homeFragment);
                binding.bottomNav.bottomNavigation.show(1, true);
            }
        });
        binding.bottomNav.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.bottomNav.bottomNavigation.show(2, true);
                navController.navigate(R.id.historyFragment);
            }
        });
        binding.bottomNav.leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.leaderboardFragment);
                binding.bottomNav.bottomNavigation.show(3, true);
            }
        });
        binding.bottomNav.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.profileFragment);
                binding.bottomNav.bottomNavigation.show(4, true);
            }
        });

    }

    private void populateQuestions() {
        listOfQuestionAnswers.clear();
        //English Data
        listOfQuestionAnswers.add(new QuestionModel(
                "What is the antonym of 'happy'?",
                "Sad",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Sad", "Joyful", "Excited", "Delighted"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the synonym of 'quick'?",
                "Fast",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Slow", "Fast", "Lazy", "Lethargic"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "Which word means the same as 'difficult'?",
                "Hard",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Easy", "Simple", "Hard", "Light"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the opposite of 'full'?",
                "Empty",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Empty", "Complete", "Packed", "Loaded"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is a synonym for 'smart'?",
                "Intelligent",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Dull", "Intelligent", "Slow", "Stupid"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the antonym of 'strong'?",
                "Weak",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Weak", "Powerful", "Sturdy", "Tough"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is a synonym for 'happy'?",
                "Joyful",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Sad", "Joyful", "Angry", "Depressed"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the opposite of 'hot'?",
                "Cold",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Warm", "Cold", "Tepid", "Scorching"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is a synonym for 'beautiful'?",
                "Attractive",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Ugly", "Hideous", "Attractive", "Plain"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the antonym of 'begin'?",
                "End",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Start", "Commence", "End", "Initiate"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is a synonym for 'angry'?",
                "Mad",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Calm", "Mad", "Happy", "Joyful"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the opposite of 'light'?",
                "Heavy",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Light", "Bright", "Heavy", "Dim"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is a synonym for 'big'?",
                "Large",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Small", "Tiny", "Large", "Little"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the antonym of 'old'?",
                "Young",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Ancient", "Old", "New", "Young"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is a synonym for 'fast'?",
                "Quick",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Slow", "Quick", "Lethargic", "Lazy"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the opposite of 'clean'?",
                "Dirty",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Dirty", "Neat", "Tidy", "Orderly"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is a synonym for 'tired'?",
                "Exhausted",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Rested", "Awake", "Exhausted", "Energetic"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the antonym of 'above'?",
                "Below",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Over", "On", "Above", "Below"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is a synonym for 'small'?",
                "Tiny",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Huge", "Massive", "Tiny", "Large"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the opposite of 'noisy'?",
                "Quiet",
                "English",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Loud", "Boisterous", "Quiet", "Raucous"));
                }}
        ));

        // Science questions
        listOfQuestionAnswers.add(new QuestionModel(
                "What is the chemical symbol for water?",
                "H2O",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("H2O", "O2", "CO2", "H2"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What planet is known as the Red Planet?",
                "Mars",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Earth", "Mars", "Jupiter", "Saturn"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the hardest natural substance on Earth?",
                "Diamond",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Gold", "Iron", "Diamond", "Platinum"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "Which organ in the human body is responsible for pumping blood?",
                "Heart",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Lung", "Heart", "Liver", "Kidney"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the most abundant gas in Earth's atmosphere?",
                "Nitrogen",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Oxygen", "Carbon Dioxide", "Nitrogen", "Argon"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the chemical symbol for gold?",
                "Au",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Ag", "Au", "Pb", "Fe"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What gas do plants need for photosynthesis?",
                "Carbon Dioxide",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Oxygen", "Hydrogen", "Carbon Dioxide", "Nitrogen"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the largest planet in our solar system?",
                "Jupiter",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Earth", "Mars", "Saturn", "Jupiter"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What type of energy is produced by the sun?",
                "Nuclear Energy",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Electrical Energy", "Thermal Energy", "Nuclear Energy", "Mechanical Energy"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the chemical formula for table salt?",
                "NaCl",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("NaCl", "KCl", "CaCO3", "HCl"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What part of the cell contains the genetic material?",
                "Nucleus",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Cytoplasm", "Nucleus", "Mitochondria", "Ribosome"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the smallest unit of life?",
                "Cell",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Molecule", "Cell", "Tissue", "Organ"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "Which planet is closest to the Sun?",
                "Mercury",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Venus", "Mars", "Earth", "Mercury"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What type of rock is formed from lava?",
                "Igneous",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Sedimentary", "Metamorphic", "Igneous", "Organic"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the freezing point of water?",
                "0°C",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("0°C", "100°C", "50°C", "10°C"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "Which scientist developed the theory of relativity?",
                "Albert Einstein",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Isaac Newton", "Albert Einstein", "Galileo Galilei", "Niels Bohr"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the chemical symbol for oxygen?",
                "O",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("O", "O2", "Ox", "O3"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the process by which plants make their food?",
                "Photosynthesis",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Respiration", "Photosynthesis", "Digestion", "Decomposition"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the largest organ in the human body?",
                "Skin",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Heart", "Liver", "Skin", "Lung"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the primary source of energy for Earth?",
                "Sun",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Moon", "Sun", "Wind", "Geothermal"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What force keeps us grounded on Earth?",
                "Gravity",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Magnetism", "Friction", "Gravity", "Inertia"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "Which organ system is responsible for breaking down food?",
                "Digestive System",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("Respiratory System", "Circulatory System", "Nervous System", "Digestive System"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the chemical formula for carbon dioxide?",
                "CO2",
                "Science",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("CO2", "CO", "C2O", "O2"));
                }}
        ));


        // Mathematics questions
        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of π (pi) to two decimal places?",
                "3.14",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("3.14", "3.15", "3.16", "3.13"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the square root of 64?",
                "8",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("6", "7", "8", "9"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the sum of the angles in a triangle?",
                "180 degrees",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("90 degrees", "180 degrees", "270 degrees", "360 degrees"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of 7 squared?",
                "49",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("42", "49", "56", "64"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the product of 12 and 8?",
                "96",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("80", "88", "96", "104"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of 5 factorial (5!)?",
                "120",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("60", "100", "120", "150"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the area of a rectangle with length 5 and width 3?",
                "15",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("8", "15", "20", "25"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of 10 divided by 2?",
                "5",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("2", "5", "10", "20"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the circumference of a circle with radius 7 (use π ≈ 3.14)?",
                "43.96",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("43.96", "44.00", "45.00", "46.00"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of 3 cubed?",
                "27",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("21", "24", "27", "30"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the sum of 25 and 30?",
                "55",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("45", "50", "55", "60"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the difference between 100 and 45?",
                "55",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("45", "55", "65", "75"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of 2 to the power of 4?",
                "16",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("8", "12", "16", "20"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the median of the numbers 3, 5, 7, 9, 11?",
                "7",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("5", "7", "9", "11"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the perimeter of a square with side length 6?",
                "24",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("20", "22", "24", "26"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the volume of a cube with side length 4?",
                "64",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("48", "56", "64", "72"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of 15% of 200?",
                "30",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("20", "25", "30", "35"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the greatest common divisor of 12 and 18?",
                "6",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("4", "6", "8", "12"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the lowest common multiple of 4 and 6?",
                "12",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("8", "10", "12", "14"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the square of 9?",
                "81",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("71", "72", "81", "90"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the hypotenuse of a right triangle with legs of length 3 and 4?",
                "5",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("4", "5", "6", "7"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of 6 + 4 × 3?",
                "18",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("20", "22", "18", "24"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the value of 9 divided by 3 plus 2?",
                "5",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("4", "5", "6", "7"));
                }}
        ));

        listOfQuestionAnswers.add(new QuestionModel(
                "What is the product of 7 and 6?",
                "42",
                "Mathematics",
                new ArrayList<ChoicesModel>() {{
                    add(new ChoicesModel("36", "42", "48", "54"));
                }}
        ));
    }

}
