package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.johade.quotem.R;
import com.johade.quotem.model.Question;
import com.johade.quotem.ViewModels.BaseGameViewModel;

import java.util.ArrayList;

public abstract class BaseGameActivity extends AppCompatActivity {
    protected TextView questionView;
    protected TextView startTimerTextView;
    protected TextView startTextTextView;
    protected TextView questionCountTextView;
    protected TextView livesTextView;
    protected TextView correctCountTextView;
    protected EditText highScoreUsernameEditText;
    protected Button saveHighScore;
    protected Button playAgain;
    protected Button answerOne;
    protected Button answerTwo;
    protected Button answerThree;
    protected Button answerFour;
    protected Button mainMenuButton;
    protected View startView;
    protected View gameView;
    protected View gameOverView;
    protected View highScoreView;
    protected ProgressBar retrievingProgressBar;
    protected BaseGameViewModel mViewModel;

    protected void disableButtons() {
        answerOne.setEnabled(false);
        answerTwo.setEnabled(false);
        answerThree.setEnabled(false);
        answerFour.setEnabled(false);
    }

    protected void enableButtons() {
        answerOne.setEnabled(true);
        answerTwo.setEnabled(true);
        answerThree.setEnabled(true);
        answerFour.setEnabled(true);
    }

    protected void showStartView() {
        startTimerTextView.setText("3");
        startTextTextView.setText("Get Ready...");
        gameView.setVisibility(View.GONE);
        startView.setVisibility(View.VISIBLE);
    }

    protected void showGameOver() {
        gameView.setVisibility(View.GONE);
        startView.setVisibility(View.GONE);
        gameOverView.setVisibility(View.VISIBLE);
        displayScore();
    }

    protected void showGameView() {
        startView.setVisibility(View.GONE);
        gameOverView.setVisibility(View.GONE);
        gameView.setVisibility(View.VISIBLE);
    }

    protected void highlightRightWrong(Button right) {
        Drawable correct = ContextCompat.getDrawable(this, R.drawable.right_answer);
        Drawable incorrect = ContextCompat.getDrawable(this, R.drawable.wrong_answer);
        if (answerOne == right) {
            answerOne.setBackground(correct);
        } else {
            answerOne.setBackground(incorrect);
        }

        if (answerTwo == right) {
            answerTwo.setBackground(correct);
        } else {
            answerTwo.setBackground(incorrect);
        }

        if (answerThree == right) {
            answerThree.setBackground(correct);
        } else {
            answerThree.setBackground(incorrect);
        }

        if (answerFour == right) {
            answerFour.setBackground(correct);
        } else {
            answerFour.setBackground(incorrect);
        }
    }

    protected void resetQuestionColor() {
        Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.custom_button);
        Drawable defaultDrawable1 = ContextCompat.getDrawable(this, R.drawable.custom_button);
        Drawable defaultDrawable2 = ContextCompat.getDrawable(this, R.drawable.custom_button);
        Drawable defaultDrawable3 = ContextCompat.getDrawable(this, R.drawable.custom_button);
        answerOne.setBackground(defaultDrawable);
        answerTwo.setBackground(defaultDrawable1);
        answerThree.setBackground(defaultDrawable2);
        answerFour.setBackground(defaultDrawable3);
    }

    protected void showRetrieveProgress(){
        retrievingProgressBar.setVisibility(View.VISIBLE);
    }

    protected void hideRetrieveProgress(){
        retrievingProgressBar.setVisibility(View.INVISIBLE);
    }

    protected void displayQuestion(Question question){
        questionView.setText(question.question);
        ArrayList<String> randomAnswers = question.shuffle();
        answerOne.setText(randomAnswers.get(0));
        answerTwo.setText(randomAnswers.get(1));
        answerThree.setText(randomAnswers.get(2));
        answerFour.setText(randomAnswers.get(3));
    }

    protected void addButtonClickListners() {
        View.OnClickListener clickedAnswerListener = v -> {
            Button b = (Button) v;
            disableButtons();
            boolean isCorrect = mViewModel.checkSelectedMovie(b.getText().toString());
            if(isCorrect){
                highlightRightWrong(b);
            }else{
                Button correctMovieButton = findCorrectButton(mViewModel.getCurrentQuestion().getValue().answer);
                highlightRightWrong(correctMovieButton);
            }
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                enableButtons();
                resetQuestionColor();
                if(!mViewModel.getGameOver().getValue()) {
                    mViewModel.nextQuestion();
                }
            }, 2000);
        };

        answerOne.setOnClickListener(clickedAnswerListener);
        answerTwo.setOnClickListener(clickedAnswerListener);
        answerThree.setOnClickListener(clickedAnswerListener);
        answerFour.setOnClickListener(clickedAnswerListener);
        playAgain.setOnClickListener(view -> {
            finish();
            startActivity(getIntent());
        });
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

    private void startNewGame(){
        mViewModel.newGame();
    }

    protected abstract void displayScore();

    @Override
    protected void onResume(){
        super.onResume();
        startNewGame();
    }
}
