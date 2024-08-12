package com.example.brainbuzz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brainbuzz.R;
import com.example.brainbuzz.model.OngoingQuizModel;

import java.util.List;

public class OngoingQuizAdapter extends RecyclerView.Adapter<OngoingQuizAdapter.OngoingQuizViewHolder> {
    private Context context;
    private List<OngoingQuizModel> ongoingQuizzes;
    private OnQuizClickListener listener;

    public interface OnQuizClickListener {
        void onQuizClick(OngoingQuizModel ongoingQuiz);
    }

    public OngoingQuizAdapter(Context context, List<OngoingQuizModel> ongoingQuizzes, OnQuizClickListener listener) {
        this.context = context;
        this.ongoingQuizzes = ongoingQuizzes;
        this.listener = listener;
    }

    public void setList(List<OngoingQuizModel> list) {
        this.ongoingQuizzes = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OngoingQuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_continue_quiz, parent, false);
        return new OngoingQuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingQuizViewHolder holder, int position) {
        OngoingQuizModel quiz = ongoingQuizzes.get(position);
        holder.bind(quiz, listener);
    }

    @Override
    public int getItemCount() {
        return ongoingQuizzes.size();
    }

    public static class OngoingQuizViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvCategory;
        private ImageView imageView;

        public OngoingQuizViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvItem);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            imageView = itemView.findViewById(R.id.ivType);
        }

        public void bind(final OngoingQuizModel quiz, final OnQuizClickListener listener) {
            tvCategory.setText(quiz.getCategory());

            // Set image based on category
            int imageResId;
            switch (quiz.getCategory()) {
                case "Science":
                    imageResId = R.drawable.science;
                    break;
                case "English":
                    imageResId = R.drawable.eng;
                    break;
                case "Mathematics":
                    imageResId = R.drawable.math;
                    break;
                default:
                    imageResId = R.drawable.placeholder; // Use a default image
                    break;
            }
            imageView.setImageResource(imageResId);

            cardView.setOnClickListener(v -> listener.onQuizClick(quiz));
        }
    }
}
