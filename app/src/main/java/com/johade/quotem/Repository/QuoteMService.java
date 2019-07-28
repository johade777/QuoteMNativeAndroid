package com.johade.quotem.Repository;

import com.johade.quotem.Models.QuestionResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteMService {
    @GET("GetQuestions")
    Call<QuestionResponse> getQuestions();
}
