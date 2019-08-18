package com.johade.quotem.model;

import java.util.List;

public class GetQuizzesResponse {
    private String message;
    private List<UserQuiz> quizzes;

    public GetQuizzesResponse(String message, List<UserQuiz> quizzes){
        this.message = message;
        this.quizzes = quizzes;
    }

    public String getMessage() {
        return message;
    }

    public List<UserQuiz> getQuizzes() {
        return quizzes;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setQuizzes(List<UserQuiz> quizzes) {
        this.quizzes = quizzes;
    }
}
