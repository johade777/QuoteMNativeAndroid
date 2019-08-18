package com.johade.quotem.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionResponse {
    @SerializedName("Questions")
    public List<Question> questions;
}
