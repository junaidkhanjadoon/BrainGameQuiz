package com.example.brainbuzz.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.brainbuzz.R;
import com.example.brainbuzz.adapter.LeaderboardAdapter;
import com.example.brainbuzz.database.DatabaseHelper;
import com.example.brainbuzz.databinding.FragmentHomeBinding;
import com.example.brainbuzz.databinding.FragmentLeaderboardBinding;
import com.example.brainbuzz.model.HistoryModel;
import com.example.brainbuzz.model.UsersModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardFragment extends Fragment {
    private FragmentLeaderboardBinding binding;
    private DatabaseHelper dbHelper;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rlTop.tvLabel.setText("Leaderboard");
        binding.rvLeaderboard.setLayoutManager(new LinearLayoutManager(requireContext()));

        dbHelper = new DatabaseHelper(getContext());

        // Get highest scores for users
        List<HistoryModel> highestScores = dbHelper.getHighestScoresForUsers();

        // Sort the highestScores list based on scores in descending order
        Collections.sort(highestScores, new Comparator<HistoryModel>() {
            @Override
            public int compare(HistoryModel o1, HistoryModel o2) {
                // Convert scores to integers or double if needed
                // Assuming scores are stored as Strings
                int score1 = Integer.parseInt(o1.getScores());
                int score2 = Integer.parseInt(o2.getScores());

                // For descending order, compare o2 to o1
                return Integer.compare(score2, score1);
            }
        });

        // Set images for top 3 scorers
        if (highestScores.size() > 0) {
            HistoryModel top1 = highestScores.get(0);
            UsersModel user1 = dbHelper.getUserById(top1.getUserId());

            Glide.with(requireContext()).load(user1.getImg()).placeholder(R.drawable.placeholder).into(binding.ivImage1);
            binding.tvName1.setText(formatNameAndScore(user1.getName(), top1.getScores()));
        }
        if (highestScores.size() > 1) {
            HistoryModel top2 = highestScores.get(1);
            UsersModel user2 = dbHelper.getUserById(top2.getUserId());
            Glide.with(requireContext()).load(user2.getImg()).placeholder(R.drawable.placeholder).into(binding.ivImage2);
            binding.tvName2.setText(formatNameAndScore(user2.getName(), top2.getScores()));
        }
        if (highestScores.size() > 2) {
            HistoryModel top3 = highestScores.get(2);
            UsersModel user3 = dbHelper.getUserById(top3.getUserId());
            Glide.with(requireContext()).load(user3.getImg()).placeholder(R.drawable.placeholder).into(binding.ivImage3);
            binding.tvName3.setText(formatNameAndScore(user3.getName(), top3.getScores()));
        }

        // Remove top 3 scores from the list
        List<HistoryModel> remainingScores = highestScores.size() > 3 ? highestScores.subList(3, highestScores.size()) : Collections.emptyList();
        if (highestScores.isEmpty()){
            binding.rvLeaderboard.setVisibility(View.GONE);
            binding.tvNoDataFound.setVisibility(View.VISIBLE);
        }else{
            binding.rvLeaderboard.setVisibility(View.VISIBLE);
            binding.tvNoDataFound.setVisibility(View.GONE);
        }
        // Use the remaining scores in the adapter
        LeaderboardAdapter adapter = new LeaderboardAdapter(requireContext(), remainingScores);
        binding.rvLeaderboard.setAdapter(adapter);

    }

    private SpannableString formatNameAndScore(String name, String score) {
        String formattedText = name + "\n" + score;
        SpannableString spannableString = new SpannableString(formattedText);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
