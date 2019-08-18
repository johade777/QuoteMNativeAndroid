package com.johade.quotem.ViewModels;

import android.content.Context;
import androidx.lifecycle.ViewModel;

import com.johade.quotem.model.Highscore;
import com.johade.quotem.service.QuoteMRepository;
import java.util.List;
import io.reactivex.Flowable;

public class HighScoreViewModel extends ViewModel {

    private QuoteMRepository repository;

    public HighScoreViewModel(Context applicationContext) {
        repository = new QuoteMRepository(applicationContext);
    }

    public Flowable<List<Highscore>> getHighScores(){
        return repository.getHighScores();
    }


}
