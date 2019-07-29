package com.johade.quotem.Views_Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.johade.quotem.Adapters.HighScoreAdapter;
import com.johade.quotem.Models.Highscore;
import com.johade.quotem.R;
import com.johade.quotem.ViewModels.HighScoreViewModel;
import com.johade.quotem.ViewModels.ViewModelFactory;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HighScoresActivity extends AppCompatActivity {

    private HighScoreViewModel mViewModel;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private List<Highscore> mHighScoreList = new ArrayList<>();
    private RecyclerView mScoreRecycler;
    private HighScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        ViewModelFactory myFactory = new ViewModelFactory(getApplicationContext());
        mViewModel = ViewModelProviders.of(this, myFactory).get(HighScoreViewModel.class);
        mScoreRecycler = findViewById(R.id.highScoreRecycler);
        mScoreRecycler.setLayoutManager(new LinearLayoutManager(this));
        mScoreRecycler.setHasFixedSize(true);

        adapter = new HighScoreAdapter();
        mScoreRecycler.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDisposable.add(mViewModel.getHighScores()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mScores ->
                updateScores(mScores),
                throwable -> Log.e("HIGH SCORE ACTIVITY", "Unable to retrieve high scores", throwable)
        ));
    }

    private void updateScores(List<Highscore> updatedList){
        mHighScoreList = updatedList;
        adapter.setHighscores(updatedList);
        Toast.makeText(HighScoresActivity.this, "Highscores Retrieved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.clear();
    }
}
