package com.johade.quotem.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionResponse {
    @SerializedName("Questions")
    public List<Question> questions;
}
