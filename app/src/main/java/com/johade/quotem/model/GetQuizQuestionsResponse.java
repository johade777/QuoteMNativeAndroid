package com.johade.quotem.model;

import java.util.List;

public class GetQuizQuestionsResponse {
    private String message;
    private List<Question> questions;

    public GetQuizQuestionsResponse(String message, List<Question> questions){
        this.message = message;
        this.questions = questions;
    }

    public String getMessage() {
        return message;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
