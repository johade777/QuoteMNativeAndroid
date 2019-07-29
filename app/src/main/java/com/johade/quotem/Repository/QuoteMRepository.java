package com.johade.quotem.Repository;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.johade.quotem.Persistance.AppDatabase;
import com.johade.quotem.Persistance.HighScoreDao;
import com.johade.quotem.Models.Highscore;
import com.johade.quotem.Models.Question;
import com.johade.quotem.Models.QuestionResponse;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteMRepository {
    private MutableLiveData<List<Question>> questionData;
    private MutableLiveData<List<Highscore>> highScores;
    Context applicationContext;
    QuoteMService apiService;
    AppDatabase applicationDatabase;
    HighScoreDao highScoreDao;


    public QuoteMRepository(Context applicationContext){
        this.applicationContext = applicationContext;
        questionData = new MutableLiveData<>();
        apiService = RetrofitSingleton.getRetrofitInstance().create(QuoteMService.class);
        applicationDatabase = AppDatabase.getInstance(applicationContext);
        highScoreDao = applicationDatabase.highScoreDao();
    }

    public Completable insertHighscore(Highscore score){
        return highScoreDao.insert(score);
    }

    public Flowable<List<Highscore>> getHighScores(){
        return highScoreDao.getAllHighScores();
    }

    public MutableLiveData<List<Question>> getQuotes(){
        Call<QuestionResponse> call = apiService.getQuestions();
        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if(response.isSuccessful()){
                    QuestionResponse reply = response.body();
                    questionData.setValue(reply.questions);
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
