package com.johade.quotem.Views_Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.johade.quotem.ViewModels.ViewModelFactory;
import com.johade.quotem.Models.Question;
import com.johade.quotem.R;
import com.johade.quotem.ViewModels.BasicGameViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BasicGameActivity extends AppCompatActivity {
    private BasicGameViewModel mViewModel;

    private TextView questionView;
    private TextView startTimerTextView;
    private TextView startTextTextView;
    private TextView questionCountTextView;
    private TextView gameTimerTextView;
    private TextView livesTextView;
    private TextView correctCountTextView;
    private EditText highScoreUsernameEditText;
    private Button saveHighScore;
    private Button playAgain;
    private Button answerOne;
    private Button answerTwo;
    private Button answerThree;
    private Button answerFour;
    private Button mainMenuButton;
    private View startView;
    private View gameView;
    private View gameOverView;
    private View highScoreView;
    private Observer gameQuestionObserver;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_game);

        startTimerTextView = findViewById(R.id.startTimer);
        startTextTextView = findViewById(R.id.startText);
        questionCountTextView = findViewById(R.id.questionCount);
        gameTimerTextView = findViewById(R.id.timerText);
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

        ViewModelFactory myViewModelFactory = new ViewModelFactory(getApplicationContext());
        mViewModel = ViewModelProviders.of(this, myViewModelFactory).get(BasicGameViewModel.class);

        gameQuestionObserver = new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> response) {
                mViewModel.gameIsReady();
            }
        };
        mViewModel.getIsSetupComplete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameReady) {
                if(gameReady) {
                    mViewModel.startGame();
                }
            }
        });
        mViewModel.getGameInProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameInProgress) {
                if (gameInProgress) {
                    showGameView();
                }
            }
        });
        mViewModel.getRemainingTime().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer remainingTime) {
                if (remainingTime >= 25) {
                    int startTimer = remainingTime - 25;
                    gameTimerTextView.setText(String.format("%d:%02d", 00, 25));
                    if (startTimer <= 1) {
                        startTextTextView.setText("Here We Go!!!");
                    }
                    startTimerTextView.setText(startTimer + "");
                } else {
                    gameTimerTextView.setText(String.format("%d:%02d", 00, remainingTime));
                }
            }
        });
        mViewModel.getQuestions().observe(this, gameQuestionObserver);
        mViewModel.getCurrentQuestion().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                resetQuestionColor();
                questionView.setText(question.question);
                ArrayList<String> randomAnswers = question.shuffle();
                answerOne.setText(randomAnswers.get(0));
                answerTwo.setText(randomAnswers.get(1));
                answerThree.setText(randomAnswers.get(2));
                answerFour.setText(randomAnswers.get(3));
                enableButtons();
            }
        });
        mViewModel.getLivesCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer remainingLives) {
                livesTextView.setText(remainingLives.toString() + " Lives");
            }
        });
        mViewModel.getQuestionNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentQuestionNumber) {
                questionCountTextView.setText(currentQuestionNumber.toString() + "/10");
            }
        });
        mViewModel.getIsGameOver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isGameOver) {
                if (isGameOver) {
                    correctCountTextView.setText(String.format("Correct: %d", mViewModel.getPlayerScore() * 10) + "%");
                    showGameOver();
                }
            }
        });
        mViewModel.getIsNewHighscore().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isHighScore) {
                if(isHighScore) {
                    highScoreView.setVisibility(View.VISIBLE);
                }
            }
        });

        View.OnClickListener clickedAnswerListener = v -> {
            Button b = (Button) v;
            disableButtons();
            boolean isCorrect = mViewModel.checkAnswer(b.getText().toString());
            if (isCorrect) {
                highlightRightWrong(b);
            } else {
                String correctMovie = mViewModel.getCorrectMovie();
                Button correctMovieButton = findCorrectButton(correctMovie);
                highlightRightWrong(correctMovieButton);
            }
        };
        answerOne.setOnClickListener(clickedAnswerListener);
        answerTwo.setOnClickListener(clickedAnswerListener);
        answerThree.setOnClickListener(clickedAnswerListener);
        answerFour.setOnClickListener(clickedAnswerListener);
        saveHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUserHighScore();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replay();
            }
        });
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewModel.newGame();
    }

    private void SaveUserHighScore() {
        String username = highScoreUsernameEditText.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter name for high score", Toast.LENGTH_SHORT).show();
            return;
        } else {
//            mDisposable.add(mViewModel.saveUserHighScore(username)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(() ->
//                                    Toast.makeText(BasicGameActivity.this, "Inserted", Toast.LENGTH_SHORT).show(),
//                            throwable -> Log.e("HIGH SCORE ACTIVITY", "Unable to retrieve high scores", throwable)
//                    ));
            mViewModel.saveUserHighScore(username);
            highScoreView.setVisibility(View.GONE);
        }
    }

    private Button findCorrectButton(String correctMovie) {
        if (answerOne.getText().equals(correctMovie)) {
            return answerOne;
        }

        if (answerTwo.getText().equals(correctMovie)) {
            return answerTwo;
        }

        if (answerThree.getText().equals(correctMovie)) {
            return answerThree;
        }

        return answerFour;
    }

    private void disableButtons() {
        answerOne.setEnabled(false);
        answerTwo.setEnabled(false);
        answerThree.setEnabled(false);
        answerFour.setEnabled(false);
    }

    private void enableButtons() {
        answerOne.setEnabled(true);
        answerTwo.setEnabled(true);
        answerThree.setEnabled(true);
        answerFour.setEnabled(true);
    }

    private void showStartView() {
        startTimerTextView.setText("3");
        startTextTextView.setText("Get Ready...");
        gameView.setVisibility(View.GONE);
        startView.setVisibility(View.VISIBLE);
    }

    private void showGameOver() {
        gameView.setVisibility(View.GONE);
        startView.setVisibility(View.GONE);
        gameOverView.setVisibility(View.VISIBLE);
    }

    private void showGameView() {
        startView.setVisibility(View.GONE);
        gameOverView.setVisibility(View.GONE);
        gameView.setVisibility(View.VISIBLE);
    }

    private void highlightRightWrong(Button right) {
        if (answerOne == right) {
            answerOne.setBackgroundColor(Color.GREEN);
        } else {
            answerOne.setBackgroundColor(Color.RED);
        }

        if (answerTwo == right) {
            answerTwo.setBackgroundColor(Color.GREEN);
        } else {
            answerTwo.setBackgroundColor(Color.RED);
        }

        if (answerThree == right) {
            answerThree.setBackgroundColor(Color.GREEN);
        } else {
            answerThree.setBackgroundColor(Color.RED);
        }

        if (answerFour == right) {
            answerFour.setBackgroundColor(Color.GREEN);
        } else {
            answerFour.setBackgroundColor(Color.RED);
        }
    }

    private void resetQuestionColor() {
        answerOne.setBackgroundColor(Color.WHITE);
        answerTwo.setBackgroundColor(Color.WHITE);
        answerThree.setBackgroundColor(Color.WHITE);
        answerFour.setBackgroundColor(Color.WHITE);
    }

    private void replay() {
        showStartView();
        mViewModel.replay();
        mViewModel.getQuestions().observe(this, gameQuestionObserver);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mDisposable.clear();
    }
}

