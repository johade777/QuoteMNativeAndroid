package com.johade.quotem.model;

public class DeleteQuestionResponse {
    private String message;

    public DeleteQuestionResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
