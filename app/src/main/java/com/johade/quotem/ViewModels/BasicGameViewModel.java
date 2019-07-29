package com.johade.quotem.ViewModels;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;

import com.johade.quotem.Models.Highscore;
import com.johade.quotem.Models.Question;
import com.johade.quotem.Repository.QuoteMRepository;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;

public class BasicGameViewModel extends ViewModel {
    private QuoteMRepository repository;
    private CountDownTimer startGameCountDown;
    private CountDownTimer gameTimer;
    private final MutableLiveData<Integer> startCountDown = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameInProgress = new MutableLiveData<>();
    private final MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    private final MutableLiveData<Integer> questionNumber = new MutableLiveData<>();
    private final MutableLiveData<Integer> livesCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> remainingTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    private MutableLiveData<List<Question>> questions = new MutableLiveData<>();
    private int playerScore;

    public BasicGameViewModel(Context applicationContext) {
        repository = new QuoteMRepository(applicationContext);
        initalSetup();
    }

    private void initalSetup(){
        gameInProgress.setValue(false);
        remainingTime.setValue(25);
        questionNumber.setValue(0);
        livesCount.setValue(3);
        isGameOver.setValue(false);
        playerScore = 0;
        startGameCountDown = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                startCountDown.setValue((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                gameInProgress.setValue(true);
                startGame();
            }
        };

        gameTimer = new CountDownTimer(25000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime.setValue((int)millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        };

        gameInProgress.setValue(false);
        questions = repository.getQuotes();
    }

    public Completable saveUserHighScore(String username){
        Highscore myHighScore = new Highscore(username, playerScore);
        return repository.insertHighscore(myHighScore);
    }

    public LiveData<Boolean> getGameInProgress() {
        return gameInProgress;
    }

    public LiveData<Integer> getStartCountDown() {
        return startCountDown;
    }

    public LiveData<List<Question>> getQuestions() {
        return questions;
    }

    public LiveData<Question> getCurrentQuestion(){
        return currentQuestion;
    }

    public LiveData<Integer> getLivesCount(){
        return livesCount;
    }

    public LiveData<Integer> getQuestionNumber(){
        return questionNumber;
    }

    public LiveData<Integer> getRemainingTime(){
        return remainingTime;
    }

    public LiveData<Boolean> getIsGameOVer(){
        return isGameOver;
    }

    public void startNewGame() {
        startGameCountDown.start();
    }

    public void startGame() {
        gameTimer.start();
    }

    public boolean checkAnswer(String pickedAnswer){
        boolean isCorrect = currentQuestion.getValue().isCorrect(pickedAnswer);
        int currentLives = livesCount.getValue() - 1;
        if(!isCorrect){
            livesCount.setValue(currentLives);
            if(currentLives == 0){
                gameOver();
            }
        }else{
            playerScore++;
        }

        gameTimer.cancel();
        (new Handler()).postDelayed(this::nextQuestion, 2500);
        return isCorrect;
    }

    public String getCorrectMovie() {
        return currentQuestion.getValue().answer;
    }

    public void nextQuestion() {
        if(questionNumber.getValue() != 0){
            gameTimer = new CountDownTimer(remainingTime.getValue() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime.setValue((int)millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    gameOver();
                }
            };
            gameTimer.start();
        }

        if (!questions.getValue().isEmpty()) {
            Question poppedQuestion = questions.getValue().get(questions.getValue().size() - 1);
            questions.getValue().remove(questions.getValue().size() - 1);
            currentQuestion.setValue(poppedQuestion);

            int currentQuestionNumber = questionNumber.getValue() + 1;
            questionNumber.setValue(currentQuestionNumber);
        }else{
            gameOver();
        }
    }

    private void gameOver(){
        isGameOver.setValue(true);
        gameTimer.cancel();
    }

    public void replay(){
        initalSetup();
    }

}
