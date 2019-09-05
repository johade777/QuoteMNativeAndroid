package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.johade.quotem.R;
import com.johade.quotem.service.QuoteMRepository;

public class MainEmptyActivity extends AppCompatActivity {
    private QuoteMRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_empty);

        repository = new QuoteMRepository(this);
        Intent activityIntent;

        if (checkIfLoggedIn()) {
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

//        activityIntent = new Intent(this, LoginActivity.class);

        startActivity(activityIntent);
        finish();
    }

    private boolean checkIfLoggedIn(){
        return repository.getToken() != null && !repository.getToken().isEmpty();
    }
}
