package com.johade.quotem.service;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.johade.quotem.model.GetQuizQuestionsResponse;
import com.johade.quotem.model.GetQuizzesResponse;
import com.johade.quotem.model.LoginResponse;
import com.johade.quotem.persistence.AppDatabase;
import com.johade.quotem.persistence.HighScoreDao;
import com.johade.quotem.model.Highscore;
import com.johade.quotem.model.Question;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;

public class QuoteMRepository {
    private MutableLiveData<List<Question>> questionData;

    Context applicationContext;
    QuestionService questionService;
    UserService userService;
    QuizService quizSerivce;
    AppDatabase applicationDatabase;
    HighScoreDao highScoreDao;
    SharedPreferences preferences;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public QuoteMRepository(Context applicationContext){
        this.applicationContext = applicationContext;
        questionData = new MutableLiveData<>();
        questionService = RetrofitSingleton.getRetrofitInstance().create(QuestionService.class);
        userService = RetrofitSingleton.getRetrofitInstance().create(UserService.class);
        quizSerivce = RetrofitSingleton.getRetrofitInstance().create(QuizService.class);
        applicationDatabase = AppDatabase.getInstance(applicationContext);
        highScoreDao = applicationDatabase.highScoreDao();
        preferences = applicationContext.getSharedPreferences("myPrefs",  Context.MODE_PRIVATE);
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

    public Observable retrieveQuestions(){
        return questionService.retrieveQuestions();
    }

    public Call<LoginResponse> login(String username, String password){
        return userService.login(username, password);
    }

    public Call<GetQuizzesResponse> getUserQizzes(String token){
        return userService.getUserQuizzes(token);
    }
    public Call<GetQuizQuestionsResponse> getQuizQuestions(int quizId, String token){
        return quizSerivce.getUserQuizzes(quizId, token);
    }


    public void setToken(String token){
        preferences.edit().putString("token", token).commit();
    }

    public String getToken(){
        return preferences.getString("token", "");
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
//
//    public void getQuotes(){
//        Call<QuestionResponse> call = questionService.getQuestions();
//        call.enqueue(new Callback<QuestionResponse>() {
//            @Override
//            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
//                if(response.isSuccessful()){
//                    QuestionResponse reply = response.body();
//                    questionData.setValue(reply.questions);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<QuestionResponse> call, Throwable t) {
//                System.out.println("Failure");
//            }
//        });
//    }
}
