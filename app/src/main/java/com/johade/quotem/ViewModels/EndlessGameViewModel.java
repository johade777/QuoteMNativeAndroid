package com.johade.quotem.ViewModels;

import android.content.Context;

import com.johade.quotem.Models.QuestionResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EndlessGameViewModel extends BaseGameViewModel {

    public EndlessGameViewModel(Context context){
        super(context);
    }

    @Override
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

                    }

                    @Override
                    public void onComplete() {
                        retrievingQuestions.setValue(false);
                    }
                });
    }

    public void retrieveMoreQuestions(){
        retrieveQuestions();
    }

    @Override
    public void newGame() {
        startTimer.start();
    }
}

