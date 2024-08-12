package com.example.brainbuzz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brainbuzz.R;
import com.example.brainbuzz.model.HistoryModel;
import com.example.brainbuzz.model.HelperClass; // Assume this provides user information

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final Context context;
    private final List<HistoryModel> historyList;

    public HistoryAdapter(Context context, List<HistoryModel> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryModel history = historyList.get(position);

        // Assuming HelperClass provides user information
        String userName = HelperClass.users.getName();

        holder.tvUserName.setText("Name : " + userName);
        holder.tvScore.setText("Score : " + history.getScores() + " / 10");
        holder.tvCategory.setText("Category : " + history.getCategory());
        holder.tvDateTime.setText("Date : " + history.getDateTime());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvScore, tvCategory, tvDateTime;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}
