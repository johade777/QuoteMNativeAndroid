package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.R;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    private Button playGameButton;
    private Button highScoresButton;
    private Button settingsButton;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playGameButton = findViewById(R.id.playGame);
        highScoresButton = findViewById(R.id.highScores);
        settingsButton = findViewById(R.id.settings);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHighScores();
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewEndless();
            }
        });
    }

    private void viewHighScores() {
        Intent openActivity = new Intent(this, HighScoresActivity.class);
        startActivity(openActivity);
    }

    private void viewEndless(){
        Intent openActivity = new Intent(this, EndlessGameActivity.class);
        startActivity(openActivity);
    }

    private void viewSettings() {
//        QuoteMRepository repository = new QuoteMRepository(getApplicationContext());
//        Highscore temp = new Highscore("Ubar", 3);

//        mDisposable.add(repository.insertHighscore(temp)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() ->
//                                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show(),
//                        throwable -> Log.e("HIGH SCORE ACTIVITY", "Unable to retrieve high scores", throwable)
//                ));
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();

    }

    private void openGame() {
        Intent openActivity = new Intent(this, BasicGameActivity.class);
        startActivity(openActivity);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mDisposable.clear();
    }
}
