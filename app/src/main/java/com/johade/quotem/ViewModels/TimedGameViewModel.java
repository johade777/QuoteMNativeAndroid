package com.johade.quotem.ViewModels;

import android.content.Context;
import android.os.CountDownTimer;

public class TimedGameViewModel extends BaseGameViewModel {
    private CountDownTimer gameTimer;

    public TimedGameViewModel(Context applicationContext) {
        super(applicationContext);

        gameTimer = new CountDownTimer(45000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameTime.setValue((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        };
    }

    @Override
    public void nextQuestion() {
        if(questions.peek() != null) {
            currentQuestion.setValue(questions.poll());
            int currentQuestionNumber = questionCount.getValue() + 1;
            questionCount.setValue(currentQuestionNumber);
        }else{
            gameOver();
        }
    }

    public void beginGameTimer(){
        gameTimer.start();
    }

    public void stopGameTimer(){
        gameTimer.cancel();
    }
}
