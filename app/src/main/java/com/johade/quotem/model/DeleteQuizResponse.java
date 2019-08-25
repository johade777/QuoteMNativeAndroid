package com.johade.quotem.model;

public class DeleteQuizResponse {
    private String message;

    public DeleteQuizResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
