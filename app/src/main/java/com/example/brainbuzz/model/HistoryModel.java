package com.example.brainbuzz.model;

import java.io.Serializable;

public class HistoryModel implements Serializable {
    int id, userId;
    String category, scores, dateTime;

    public HistoryModel() {
    }

    public HistoryModel(int userId, String category, String scores, String dateTime) {
        this.userId = userId;
        this.category = category;
        this.scores = scores;
        this.dateTime = dateTime;
    }

    public HistoryModel(int id, int userId, String category, String scores, String dateTime) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.scores = scores;
        this.dateTime = dateTime;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
