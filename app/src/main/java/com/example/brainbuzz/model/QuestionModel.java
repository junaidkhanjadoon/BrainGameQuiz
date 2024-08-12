package com.example.brainbuzz.model;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionModel implements Serializable {
    String question, answer, category;
    ArrayList<ChoicesModel> listOfChoices;

    public QuestionModel() {
    }

    public QuestionModel(String question, String answer, String category, ArrayList<ChoicesModel> listOfChoices) {
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.listOfChoices = listOfChoices;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<ChoicesModel> getListOfChoices() {
        return listOfChoices;
    }

    public void setListOfChoices(ArrayList<ChoicesModel> listOfChoices) {
        this.listOfChoices = listOfChoices;
    }
}
