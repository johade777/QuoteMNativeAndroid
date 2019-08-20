package com.johade.quotem.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
    @SerializedName("Id")
    public int id;

    @SerializedName("Question")
    public String question;

    @SerializedName("Answer")
    public String answer;

    @SerializedName("Wrong1")
    public String wrong1;

    @SerializedName("Wrong2")
    public String wrong2;

    @SerializedName("Wrong3")
    public String wrong3;

    public ArrayList<String> shuffle(){
        ArrayList<String> answers = new ArrayList<>();
        answers.add(answer);
        answers.add(wrong1);
        answers.add(wrong2);
        answers.add(wrong3);
        Collections.shuffle(answers);
        return answers;
    }

    public boolean isCorrect(String clickedText){
        return clickedText.equals(answer);
    }
}
