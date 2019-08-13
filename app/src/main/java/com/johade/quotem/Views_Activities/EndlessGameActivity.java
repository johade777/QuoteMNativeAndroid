package com.johade.quotem.Views_Activities;

import android.os.Bundle;
import androidx.lifecycle.Observer;

import com.johade.quotem.Models.Question;
import com.johade.quotem.ViewModels.EndlessGameViewModel;
import com.johade.quotem.R;

public class EndlessGameActivity extends BaseGameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endless_game);
        viewSetUp();
        mViewModel = new EndlessGameViewModel(this);

        mViewModel.getTimeUntilStart().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer startTime) {
                startTimerTextView.setText(startTime.toString());
                if(startTime <= 0){
                    showGameView();
                }
            }
        });

        mViewModel.getPlayerScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer score) {
                questionCountTextView.setText("Score: " + score);
            }
        });

        mViewModel.getPlayerLives().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer remainLives) {
                livesTextView.setText("Lives: " + remainLives);
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
                showGameOver();
            }
        });
    }

    private void viewSetUp(){
        startTimerTextView = findViewById(R.id.startTimer);
        startTextTextView = findViewById(R.id.startText);
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
    protected void startNewGame() {
        mViewModel.newGame();
    }
}
