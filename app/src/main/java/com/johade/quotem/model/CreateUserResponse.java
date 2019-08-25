package com.johade.quotem.model;

public class CreateUserResponse {

    private String message;

    public CreateUserResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
