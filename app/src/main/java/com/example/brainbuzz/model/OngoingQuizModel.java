package com.example.brainbuzz.model;


import java.io.Serializable;
import java.util.ArrayList;

public class OngoingQuizModel implements Serializable {
    int id, userId, score;
    private String category;
    private int currentQuestionIndex;
    private ArrayList<QuestionModel> remainingQuestions;

    public OngoingQuizModel() {
    }

    public OngoingQuizModel(int userId, int score, String category, int currentQuestionIndex, ArrayList<QuestionModel> remainingQuestions) {
        this.userId = userId;
        this.score = score;
        this.category = category;
        this.currentQuestionIndex = currentQuestionIndex;
        this.remainingQuestions = remainingQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public ArrayList<QuestionModel> getRemainingQuestions() {
        return remainingQuestions;
    }

    public void setRemainingQuestions(ArrayList<QuestionModel> remainingQuestions) {
        this.remainingQuestions = remainingQuestions;
    }
}