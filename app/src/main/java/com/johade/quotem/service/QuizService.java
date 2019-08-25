package com.johade.quotem.service;

import com.johade.quotem.model.DeleteQuizResponse;
import com.johade.quotem.model.GetQuizQuestionsResponse;
import com.johade.quotem.model.InsertQuizResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuizService {
    @GET("viewQuiz/{quiz_id}")
    Call<GetQuizQuestionsResponse> getUserQuizzes(@Path("quiz_id") int quiz_id, @Header("Authorization") String authToken);

    @POST("insertQuiz")
    Call<InsertQuizResponse> insertQuiz(@Header("quiz_name") String quizName, @Header("quiz_time") String quizTime, @Header("Authorization") String authToken);

    @POST("deleteQuiz")
    Call<DeleteQuizResponse> deleteQuiz(@Header("quiz_id") int quizId, @Header("Authorization") String authToken);
}
