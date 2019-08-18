package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.R;
import com.johade.quotem.model.LoginResponse;
import com.johade.quotem.service.QuoteMRepository;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button playGameButton;
    private Button highScoresButton;
    private Button settingsButton;
    QuoteMRepository repository;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playGameButton = findViewById(R.id.playGame);
        highScoresButton = findViewById(R.id.highScores);
        settingsButton = findViewById(R.id.settings);
        playGameButton.setOnClickListener(v -> {
            Intent openActivity = new Intent(MainActivity.this, ActivitySelectGame.class);
            startActivity(openActivity);
        });
        highScoresButton.setOnClickListener(v -> viewHighScores());
        settingsButton.setOnClickListener(v -> viewSettings());
        repository = new QuoteMRepository(this);
    }

    private void viewHighScores() {
        Intent openActivity = new Intent(this, HighScoresActivity.class);
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
//        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();

        Call<LoginResponse> call = repository.login("johade", "12345");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Token: " + response.body().getAuthToken(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


//        .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<QuestionResponse>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(QuestionResponse questionResponse) {
//                        setQuestions(questionResponse.questions);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("Test");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Handler handler = new Handler();
//                        handler.postDelayed(() -> retrievingQuestions.setValue(false), 2000);
//                    }
//                });
    }

    @Override
    protected void onStop() {
        super.onStop();

        mDisposable.clear();
    }
}
