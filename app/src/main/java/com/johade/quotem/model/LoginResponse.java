package com.johade.quotem.model;

public class LoginResponse {
    private String message;
    private String authToken;

    public LoginResponse(String message, String authToken){
        this.message = message;
        this.authToken = authToken;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
