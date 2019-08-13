package com.johade.quotem.ViewModels;

import android.content.Context;

public class EndlessGameViewModel extends BaseGameViewModel {

    public EndlessGameViewModel(Context context){
        super(context);
    }

    @Override
    public void nextQuestion(){
        currentQuestion.setValue(questions.poll());
        if(questions.size() < 5){
            retrieveQuestions();
        }
    }
}

