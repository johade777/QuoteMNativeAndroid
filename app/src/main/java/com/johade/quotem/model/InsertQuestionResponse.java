package com.johade.quotem.model;

public class InsertQuestionResponse {
    private String message;

    public InsertQuestionResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
