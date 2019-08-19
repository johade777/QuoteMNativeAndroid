package com.johade.quotem.service;

import com.johade.quotem.model.QuestionResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface QuestionService {
//    @GET("getQuestions")
//    Call<QuestionResponse> getQuestions();

    @GET("getQuestions")
    Observable<QuestionResponse> retrieveQuestions();
}
