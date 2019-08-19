package com.johade.quotem.service;

import com.johade.quotem.model.InsertQuestionResponse;
import com.johade.quotem.model.InsertQuizResponse;
import com.johade.quotem.model.QuestionResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface QuestionService {
    @GET("getQuestions")
    Observable<QuestionResponse> retrieveQuestions();

    @POST("insertNewQuestion")
    Call<InsertQuestionResponse> insertQuestion(@Header("quiz_id") int quiz_id,
                                                @Header("question") String question,
                                                @Header("answer") String answer,
                                                @Header("wrong1") String wrong1,
                                                @Header("wrong2") String wrong2,
                                                @Header("wrong3") String wrong3,
                                                @Header("Authorization") String authToken);
}
