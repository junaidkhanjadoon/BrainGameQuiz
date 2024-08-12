package com.example.brainbuzz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.brainbuzz.R;
import com.example.brainbuzz.database.DatabaseHelper;
import com.example.brainbuzz.model.HistoryModel;
import com.example.brainbuzz.model.UsersModel;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private List<HistoryModel> leaderboardList;
    private DatabaseHelper dbHelper; // Database helper instance

    public LeaderboardAdapter(Context context, List<HistoryModel> leaderboardList) {
        this.leaderboardList = leaderboardList;
        this.dbHelper = new DatabaseHelper(context); // Initialize database helper
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel model = leaderboardList.get(position);

        // Fetch user details from the database using the userId from HistoryModel
        UsersModel user = dbHelper.getUserById(model.getUserId());

        if (user != null) {
            holder.tvUserName.setText(user.getUserName());
            holder.tvScore.setText("Score: " + model.getScores());

            // Load image from URI using Glide
            Glide.with(holder.itemView.getContext())
                    .load(user.getImg()) // Use user image URI
                    .placeholder(R.drawable.placeholder) // Placeholder image
                    .into(holder.ivUserImage);
        }
    }

    @Override
    public int getItemCount() {
        return leaderboardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private de.hdodenhof.circleimageview.CircleImageView ivUserImage;
        private TextView tvUserName;
        private TextView tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}
