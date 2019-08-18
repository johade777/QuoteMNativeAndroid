package com.johade.quotem.Views_Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.johade.quotem.Models.Question;
import com.johade.quotem.R;
import com.johade.quotem.ViewModels.TimedGameViewModel;

public class TimedGameActivity extends BaseGameActivity {
    private TextView gameTimerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_game);
        viewSetUp();
        mViewModel = new TimedGameViewModel(this);

        mViewModel.getTimeUntilStart().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer startTime) {
                startTimerTextView.setText(startTime.toString());
                if(startTime <= 0){
                    showGameView();
                    ((TimedGameViewModel) mViewModel).beginGameTimer();
                }
            }
        });

        mViewModel.getPlayerLives().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer remainLives) {
                livesTextView.setText("Lives: " + remainLives);
            }
        });

        mViewModel.getQuestionCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentQuestionNumber) {
                questionCountTextView.setText(currentQuestionNumber.toString() + "/10");
            }
        });

        mViewModel.getIsRetrievingQuestions().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRetrieving) {
                if(isRetrieving){
                    showRetrieveProgress();
                }else{
                    hideRetrieveProgress();
                }
            }
        });

        mViewModel.getCurrentQuestion().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                displayQuestion(question);
            }
        });

        mViewModel.getGameOver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameOver) {
                if(gameOver) {
                    ((TimedGameViewModel) mViewModel).stopGameTimer();
                    showGameOver();

                }
            }
        });

        mViewModel.getGameTime().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer remainingTime) {
                gameTimerTextView.setText(String.format("%d:%02d", 00, remainingTime));
            }
        });
    }

    private void viewSetUp(){
        startTimerTextView = findViewById(R.id.startTimer);
        startTextTextView = findViewById(R.id.startText);
        gameTimerTextView = findViewById(R.id.timerText);
        questionCountTextView = findViewById(R.id.questionCount);
        livesTextView = findViewById(R.id.lives);
        correctCountTextView = findViewById(R.id.correctCount);
        saveHighScore = findViewById(R.id.saveScoreButton);
        mainMenuButton = findViewById(R.id.mainMenu);
        highScoreUsernameEditText = findViewById(R.id.highscoreUsernameEditText);
        playAgain = findViewById(R.id.playAgain);
        answerOne = findViewById(R.id.answerOne);
        answerTwo = findViewById(R.id.answerTwo);
        answerThree = findViewById(R.id.answerThree);
        answerFour = findViewById(R.id.answerFour);
        questionView = findViewById(R.id.questionView);
        startView = findViewById(R.id.startView);
        gameView = findViewById(R.id.game_view);
        gameOverView = findViewById(R.id.gameOverView);
        highScoreView = findViewById(R.id.highScoreView);
        retrievingProgressBar = findViewById(R.id.retrievingProgress);

        addButtonClickListners();
    }

    @Override
    protected void displayScore() {
        correctCountTextView.setText(String.format("Correct: %d", mViewModel.getPlayerScore().getValue() * 10) + "%");
    }
}
