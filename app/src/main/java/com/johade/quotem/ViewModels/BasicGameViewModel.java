package com.johade.quotem.ViewModels;

import android.app.Application;
import android.os.CountDownTimer;
import android.os.Handler;

import com.johade.quotem.Models.Question;
import com.johade.quotem.Models.QuestionResponse;
import com.johade.quotem.Repository.QuoteMRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BasicGameViewModel extends AndroidViewModel {
    private QuoteMRepository repository;
    private Application application;
    private CountDownTimer startGameCountDown;
    private CountDownTimer gameTimer;
    private final MutableLiveData<Integer> startCountDown = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameInProgress = new MutableLiveData<>();
    private final MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    private final MutableLiveData<Integer> questionNumber = new MutableLiveData<>();
    private final MutableLiveData<Integer> livesCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> remainingTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    private MutableLiveData<QuestionResponse> questions;

    private List<Question> myQuestions = new ArrayList<>();

    public BasicGameViewModel(Application application) {
        super(application);
        this.application = application;
        repository = new QuoteMRepository();
        gameInProgress.setValue(false);
        remainingTime.setValue(25);
        questionNumber.setValue(0);
        livesCount.setValue(3);
        isGameOver.setValue(false);
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

    public LiveData<Boolean> getGameInProgress() {
        return gameInProgress;
    }

    public LiveData<Integer> getStartCountDown() {
        return startCountDown;
    }

    public LiveData<QuestionResponse> getQuestions() {
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

    public void setQuestions(QuestionResponse response) {
        if(myQuestions.isEmpty()) {
            myQuestions = response.questions;
            nextQuestion();
        }
    }

    public boolean checkAnswer(String pickedAnswer){
        boolean isCorrect = currentQuestion.getValue().isCorrect(pickedAnswer);
        int currentLives = livesCount.getValue() - 1;
        if(!isCorrect){
            livesCount.setValue(currentLives);
            if(currentLives == 0){
                gameOver();
            }
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

        int currentQuestionNumber = questionNumber.getValue() + 1;
        questionNumber.setValue(currentQuestionNumber);

        if (!myQuestions.isEmpty()) {
            Question poppedQuestion = myQuestions.get(myQuestions.size() - 1);
            myQuestions.remove(myQuestions.size() - 1);
            currentQuestion.setValue(poppedQuestion);
        }
    }

    private void gameOver(){
        isGameOver.setValue(true);
        gameTimer.cancel();
    }
}
