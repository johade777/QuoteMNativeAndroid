package com.johade.quotem.Repository;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.johade.quotem.Persistance.AppDatabase;
import com.johade.quotem.Persistance.HighScoreDao;
import com.johade.quotem.Models.Highscore;
import com.johade.quotem.Models.Question;
import com.johade.quotem.Models.QuestionResponse;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteMRepository {
    private MutableLiveData<List<Question>> questionData;

    Context applicationContext;
    QuoteMService apiService;
    AppDatabase applicationDatabase;
    HighScoreDao highScoreDao;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public QuoteMRepository(Context applicationContext){
        this.applicationContext = applicationContext;
        questionData = new MutableLiveData<>();
        apiService = RetrofitSingleton.getRetrofitInstance().create(QuoteMService.class);
        applicationDatabase = AppDatabase.getInstance(applicationContext);
        highScoreDao = applicationDatabase.highScoreDao();
    }

    public void insertHighscore(Highscore score){
        new InsertScore(highScoreDao).execute(score);
        //return highScoreDao.insert(score);
    }

    public void deleteHighscore(Highscore score){
        highScoreDao.delete(score);
    }

    public LiveData<List<Question>> getQuestions(){
        return questionData;
    }

    public Flowable<List<Highscore>> getHighScores(){
        return highScoreDao.getAllHighScores();
    }

//    public Flowable<Highscore> getLowestHighscore(){
//        mDisposable.add(highScoreDao.getLowestHighScore()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(lowest -> lowestHighScore.setValue(lowest),
//                        throwable -> Log.e("QuoteMReposity", "Unable to retrieve Low Score", throwable)
//                ));
//        return highScoreDao.getLowestHighScore();
//    }

    public void getQuotes(){
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
    }

    public Observable retrieveQuestions(){
        return apiService.retrieveQuestions();
    }

    private static class InsertScore extends AsyncTask<Highscore, Void, Void>{
        private HighScoreDao highScoreDao;

        private InsertScore(HighScoreDao highScoreDao){
            this.highScoreDao = highScoreDao;
        }

        @Override
        protected Void doInBackground(Highscore... score) {
            highScoreDao.insert(score[0]);
            return null;
        }
    }
}
