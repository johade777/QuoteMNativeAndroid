package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.R;
import com.johade.quotem.service.QuoteMRepository;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    private Button playGameButton;
    private Button highScoresButton;
    private Button settingsButton;
    private Button userQuizzesButton;
    private Button logoutButton;
    QuoteMRepository repository;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playGameButton = findViewById(R.id.playGame);
        highScoresButton = findViewById(R.id.highScores);
        settingsButton = findViewById(R.id.settings);
        userQuizzesButton = findViewById(R.id.userQuizzesButton);
        logoutButton = findViewById(R.id.logoutButton);
        playGameButton.setOnClickListener(v -> {
            Intent openActivity = new Intent(MainActivity.this, SelectGameActivity.class);
            startActivity(openActivity);
        });
        highScoresButton.setOnClickListener(v -> viewHighScores());
        settingsButton.setOnClickListener(v -> viewSettings());
        userQuizzesButton.setOnClickListener(v -> showUserQuizzes());
        logoutButton.setOnClickListener(v -> logout());
        repository = new QuoteMRepository(this);
    }

    private void showUserQuizzes(){
        Intent openActivity = new Intent(this, QuizListActivity.class);
        startActivity(openActivity);
    }

    private void logout(){
        repository.logout();
        Intent openActivity = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(openActivity);
        this.finish();
    }

    private void viewHighScores() {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();

//        Intent openActivity = new Intent(this, HighScoresActivity.class);
//        startActivity(openActivity);

//        Call<GetQuizzesResponse> call = repository.getUserQizzes(repository.getToken());
//        call.enqueue(new Callback<GetQuizzesResponse>() {
//            @Override
//            public void onResponse(Call<GetQuizzesResponse> call, Response<GetQuizzesResponse> response) {
//                if(response.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Token: " + response.body().getQuizzes().get(0).getQuiz_name(), Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetQuizzesResponse> call, Throwable t) {
//
//            }
//        });
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

    @Override
    protected void onStop() {
        super.onStop();

        mDisposable.clear();
    }
}
