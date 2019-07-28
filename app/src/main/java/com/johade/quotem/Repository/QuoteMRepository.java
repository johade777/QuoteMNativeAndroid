package com.johade.quotem.Repository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.johade.quotem.Models.Question;
import com.johade.quotem.Models.QuestionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteMRepository {
    private MutableLiveData<QuestionResponse> questionData;
    QuoteMService apiService;

    public QuoteMRepository(){
        questionData = new MutableLiveData<>();
        apiService = RetrofitSingleton.getRetrofitInstance().create(QuoteMService.class);
    }

    public MutableLiveData<QuestionResponse> getQuotes(){
        Call<QuestionResponse> call = apiService.getQuestions();
        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if(response.isSuccessful()){
                    QuestionResponse reply = response.body();
                    questionData.setValue(reply);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                System.out.println("Failure");
            }
        });

        return questionData;
    }
}
