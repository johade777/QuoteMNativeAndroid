package com.johade.quotem.service;

import com.johade.quotem.model.GetQuizzesResponse;
import com.johade.quotem.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Call<LoginResponse> login(@Header("username") String username, @Header("password") String password);

    @GET("getUserQuizzes")
    Call<GetQuizzesResponse> getUserQuizzes(@Header("Authorization") String authToken);
}
