package com.johade.quotem.model;

public class UserQuiz {
    private int quiz_id;
    private String quiz_name;

    public UserQuiz(int quiz_id, String quiz_name){
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }
}
