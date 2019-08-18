package com.johade.quotem.ViewModels;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import com.johade.quotem.model.Highscore;
import com.johade.quotem.model.Question;
import com.johade.quotem.service.QuoteMRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BasicGameViewModel extends ViewModel {
    private QuoteMRepository repository;
    private CountDownTimer gameTimer;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private final MutableLiveData<Boolean> gameSetupComplete = new MutableLiveData<>();
    private final MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    private final MutableLiveData<Integer> questionNumber = new MutableLiveData<>();
    private final MutableLiveData<Integer> livesCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> remainingTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    private final MutableLiveData<Boolean> newHighscore = new MutableLiveData<>();
    private Boolean gameInProgress = false;
    private Highscore toBeDeletedScore;
    private List<Highscore> myHighScores;
    private LiveData<List<Question>> questions;
    private int playerScore;

    public BasicGameViewModel(Context applicationContext) {
        repository = new QuoteMRepository(applicationContext);
        questions = repository.getQuestions();
        mDisposable.add(repository.getHighScores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mScores ->
                                setHighscores(mScores),
                        throwable -> Log.e("BasicGameViewModel", "Unable to retrieve high scores", throwable)
                ));
    }

    public LiveData<Boolean> getIsNewHighscore() {
        return newHighscore;
    }

    public LiveData<List<Question>> getQuestions() {
        return questions;
    }

    public LiveData<Question> getCurrentQuestion() {
        return currentQuestion;
    }

    public LiveData<Integer> getLivesCount() {
        return livesCount;
    }

    public LiveData<Integer> getQuestionNumber() {
        return questionNumber;
    }

    public LiveData<Integer> getRemainingTime() {
        return remainingTime;
    }

    public LiveData<Boolean> getIsGameOver() {
        return isGameOver;
    }

    public LiveData<Boolean> getIsSetupComplete() {
        return gameSetupComplete;
    }

    public Boolean getGameInProgress() {
        return this.gameInProgress;
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public void setHighscores(List<Highscore> scores) {
        myHighScores = scores;
    }

    public boolean checkAnswer(String pickedAnswer) {
        boolean isCorrect = currentQuestion.getValue().isCorrect(pickedAnswer);
        int currentLives = livesCount.getValue() - 1;
        if (!isCorrect) {
            livesCount.setValue(currentLives);
            if (currentLives == 0) {
                gameOver();
            }
        } else {
            playerScore++;
        }

        gameTimer.cancel();
        (new Handler()).postDelayed(this::resumeGame, 2500);
        return isCorrect;
    }

    public void nextQuestion() {
        if (!questions.getValue().isEmpty()) {
            Question poppedQuestion = questions.getValue().get(questions.getValue().size() - 1);
            questions.getValue().remove(questions.getValue().size() - 1);
            currentQuestion.setValue(poppedQuestion);

            int currentQuestionNumber = questionNumber.getValue() + 1;
            questionNumber.setValue(currentQuestionNumber);
        } else {
            gameOver();
        }
    }

    public String getCorrectMovie() {
        return currentQuestion.getValue().answer;
    }

    public void gameIsReady() {
        gameSetupComplete.setValue(true);
    }

    private void getNewQuotes() {
        gameSetupComplete.setValue(false);
        //repository.getQuotes();
    }

    public void newGame() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        newHighscore.setValue(false);
        remainingTime.setValue(29);
        questionNumber.setValue(0);
        livesCount.setValue(3);
        isGameOver.setValue(false);
        playerScore = 0;
        gameTimer = new CountDownTimer(29000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime.setValue((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        };
        getNewQuotes();
    }

    public void startGame() {
        gameInProgress = true;
        nextQuestion();
        gameTimer.start();
    }

    private void resumeGame() {
        if (!isGameOver.getValue()) {
            gameTimer = new CountDownTimer(remainingTime.getValue() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime.setValue((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    gameOver();
                }
            };
            gameTimer.start();
            nextQuestion();
        }
    }

    private void gameOver() {
        checkIfNewHighScore();
        isGameOver.setValue(true);
        gameTimer.cancel();
    }

    //Should really be split into multiple methods for testability
    private void checkIfNewHighScore() {
        if (playerScore == 0) {
            return;
        }

        if (myHighScores.size() < 10) {
            newHighscore.setValue(true);
            return;
        }

        for (int i = 0; i < myHighScores.size(); i++) {
            if (playerScore >= myHighScores.get(myHighScores.size() - 1 - i).getScore()) {
                toBeDeletedScore = myHighScores.get(i);
                newHighscore.setValue(true);
            }
        }
    }

    public void saveUserHighScore(String username) {
        deleteHighscore();
        repository.insertHighscore(new Highscore(username, playerScore));

    }

    public void deleteHighscore() {
        if (toBeDeletedScore == null) {
            return;
        }
        repository.deleteHighscore(toBeDeletedScore);
    }

    public void replay() {
        newGame();
    }
}
