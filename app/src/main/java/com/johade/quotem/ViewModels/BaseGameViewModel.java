package com.johade.quotem.ViewModels;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.johade.quotem.model.Question;
import com.johade.quotem.model.QuestionResponse;
import com.johade.quotem.service.QuoteMRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseGameViewModel extends ViewModel {
    protected QuoteMRepository repository;
    protected Queue<Question> questions;
    protected CountDownTimer startTimer;

    protected final MutableLiveData<Integer> timeUntilGameStart = new MutableLiveData<>();
    protected final MutableLiveData<Integer> playerScore = new MutableLiveData<>();
    protected final MutableLiveData<Integer> questionCount = new MutableLiveData<>();
    protected final MutableLiveData<Integer> playerLives = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> retrievingQuestions = new MutableLiveData<>();
    protected final MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    protected final MutableLiveData<Integer> gameTime = new MutableLiveData<>();

    protected BaseGameViewModel(Context applicationContext) {
        gameOver.setValue(false);
        playerScore.setValue(0);
        playerLives.setValue(3);
        questionCount.setValue(0);

        startTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeUntilGameStart.setValue((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
            }
        };

        repository = new QuoteMRepository(applicationContext);
        retrieveQuestions();
    }

    public LiveData<Integer> getTimeUntilStart() {
        return this.timeUntilGameStart;
    }

    public LiveData<Integer> getPlayerScore() {
        return this.playerScore;
    }

    public LiveData<Integer> getPlayerLives(){
        return this.playerLives;
    }

    public LiveData<Integer> getQuestionCount(){
        return this.questionCount;
    }

    public LiveData<Boolean> getIsRetrievingQuestions(){
        return retrievingQuestions;
    }

    public LiveData<Question> getCurrentQuestion(){
        return currentQuestion;
    }

    public LiveData<Boolean> getGameOver(){
        return this.gameOver;
    }

    public LiveData<Integer> getGameTime(){
        return this.gameTime;
    }

    protected void setQuestions(List<Question> questionList) {
        if (questions == null) {
            questions = new LinkedList(questionList);
            nextQuestion();
        } else {
            questions.addAll(questionList);
        }
    }

    public boolean checkSelectedMovie(String selectedMovie){
        boolean isCorrect = currentQuestion.getValue().isCorrect(selectedMovie);
        if (!isCorrect) {
            int remainingLives = playerLives.getValue() - 1;
            playerLives.setValue(remainingLives);
            if (remainingLives == 0) {
                gameOver();
            }
        } else {
            int newScore = playerScore.getValue() + 1;
            playerScore.setValue(newScore);
        }

        return isCorrect;
    }

    protected void retrieveQuestions() {
        retrievingQuestions.setValue(true);
        repository.retrieveQuestions().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QuestionResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(QuestionResponse questionResponse) {
                        setQuestions(questionResponse.questions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Test");
                    }

                    @Override
                    public void onComplete() {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> retrievingQuestions.setValue(false), 2000);
                    }
                });
    }

    protected void gameOver() {
        //checkIfNewHighScore();
        gameOver.setValue(true);
    }

    public void newGame(){
        startTimer.start();
    }
    public abstract void nextQuestion();
}
