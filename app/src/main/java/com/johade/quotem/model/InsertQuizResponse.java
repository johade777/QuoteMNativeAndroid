package com.johade.quotem.model;

public class InsertQuizResponse {
    private String message;

    public InsertQuizResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
