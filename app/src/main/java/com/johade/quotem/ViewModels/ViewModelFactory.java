package com.johade.quotem.ViewModels;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    Context applicationContext;

    public ViewModelFactory(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(BasicGameViewModel.class)){
            return (T) new BasicGameViewModel(this.applicationContext);
        }
        if(modelClass.isAssignableFrom(HighScoreViewModel.class)){
            return (T) new HighScoreViewModel(this.applicationContext);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
