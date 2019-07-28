package com.johade.quotem.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Question {
    @SerializedName("id")
    public int id;

    @SerializedName("question")
    public String question;

    @SerializedName("answer")
    public String answer;

    @SerializedName("wrong1")
    public String wrong1;

    @SerializedName("wrong2")
    public String wrong2;

    @SerializedName("wrong3")
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
