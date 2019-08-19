package com.johade.quotem.service;

import com.johade.quotem.model.GetQuizQuestionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface QuizService {
    @GET("viewQuiz/{quiz_id}")
    Call<GetQuizQuestionsResponse> getUserQuizzes(@Path("quiz_id") int quiz_id, @Header("Authorization") String authToken);
}
