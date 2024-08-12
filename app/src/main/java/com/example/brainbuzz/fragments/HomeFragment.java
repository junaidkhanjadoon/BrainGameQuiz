package com.example.brainbuzz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.brainbuzz.QuizActivity;
import com.example.brainbuzz.adapter.OngoingQuizAdapter;
import com.example.brainbuzz.databinding.FragmentHomeBinding;
import com.example.brainbuzz.model.HelperClass;
import com.example.brainbuzz.model.OngoingQuizModel;
import com.example.brainbuzz.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private List<OngoingQuizModel> ongoingQuizzes = new ArrayList<>();
    private OngoingQuizAdapter ongoingQuizAdapter;
    private DatabaseHelper databaseHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rlTop.tvLabel.setText("Home");
        // Setup RecyclerView
        binding.rvContinue.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ongoingQuizAdapter = new OngoingQuizAdapter(getContext(), ongoingQuizzes, this::startQuizActivity);
        binding.rvContinue.setAdapter(ongoingQuizAdapter);

        // Example categories
        setupCategoryClickListener();
    }
    private void loadOngoingQuizzes() {
        ongoingQuizzes.clear();
        ongoingQuizzes = databaseHelper.getOngoingQuizByUserId(HelperClass.users.getId());
        if (ongoingQuizzes.isEmpty()){
            binding.tvNoDataFound.setVisibility(View.VISIBLE);
            binding.rvContinue.setVisibility(View.GONE);
        }else{
            if (ongoingQuizAdapter != null) {
                ongoingQuizAdapter.setList(ongoingQuizzes);
            }
            binding.tvNoDataFound.setVisibility(View.GONE);
            binding.rvContinue.setVisibility(View.VISIBLE);
        }
    }

    private void setupCategoryClickListener() {
        binding.cvScience.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QuizActivity.class);
            intent.putExtra("quizCategory", "Science");
            startActivity(intent);
        });

        binding.cvEnglish.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QuizActivity.class);
            intent.putExtra("quizCategory", "English");
            startActivity(intent);
        });

        binding.cvMath.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QuizActivity.class);
            intent.putExtra("quizCategory", "Mathematics");
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadOngoingQuizzes();
    }

    private void startQuizActivity(OngoingQuizModel ongoingQuiz) {
        Intent intent = new Intent(getContext(), QuizActivity.class);
        intent.putExtra("ongoingQuiz", ongoingQuiz);
        startActivity(intent);
    }

}
