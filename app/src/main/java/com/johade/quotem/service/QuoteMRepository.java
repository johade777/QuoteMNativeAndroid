package com.johade.quotem.service;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.johade.quotem.model.CreateUserResponse;
import com.johade.quotem.model.DeleteQuestionResponse;
import com.johade.quotem.model.DeleteQuizResponse;
import com.johade.quotem.model.GetQuizQuestionsResponse;
import com.johade.quotem.model.GetQuizzesResponse;
import com.johade.quotem.model.InsertQuestionResponse;
import com.johade.quotem.model.InsertQuizResponse;
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

    public Call<CreateUserResponse> createUser(String username, String password, String email){
        return userService.createNewUser(username, password, email);
    }

    public Call<GetQuizzesResponse> getUserQizzes(){
        return userService.getUserQuizzes(getToken());
    }
    public Call<GetQuizQuestionsResponse> getQuizQuestions(int quizId){
        return quizSerivce.getUserQuizzes(quizId, getToken());
    }

    public Call<InsertQuizResponse> insertQuiz(String quizName, String quizTime){
        return quizSerivce.insertQuiz(quizName, quizTime, getToken());
    }

    public Call<DeleteQuizResponse> deleteQuiz(int quiz_id){
        return quizSerivce.deleteQuiz(quiz_id, getToken());
    }

    public Call<InsertQuestionResponse> insertQuestion(int quiz_id, String question, String answer, String wrong1, String wrong2, String wrong3){
        return questionService.insertQuestion(quiz_id, question, answer, wrong1, wrong2, wrong3, getToken());
    }

    public Call<DeleteQuestionResponse> deleteQuestion(int question_id){
        return questionService.deleteQuestion(question_id, getToken());
    }

    public void setToken(String token){
        preferences.edit().putString("token", token).commit();
    }

    public String getToken(){
        return preferences.getString("token", "");
    }

    public void logout(){
        preferences.edit().clear().commit();
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




//    private CompositeDisposable mDisposable = new CompositeDisposable();

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
