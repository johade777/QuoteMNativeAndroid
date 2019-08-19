package com.johade.quotem.model;

import java.util.List;

public class GetQuizzesResponse {
    private String message;
    private List<Quiz> quizzes;

    public GetQuizzesResponse(String message, List<Quiz> quizzes){
        this.message = message;
        this.quizzes = quizzes;
    }

    public String getMessage() {
        return message;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
