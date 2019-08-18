package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.johade.quotem.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivitySelectGame extends AppCompatActivity {
    private Button timedGame;
    private Button endlessGame;
    private Button verseGame;
    private Button userGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        timedGame = findViewById(R.id.timedGame);
        endlessGame = findViewById(R.id.endlessGame);
        verseGame = findViewById(R.id.verseGame);
        userGame = findViewById(R.id.userGame);

        timedGame.setOnClickListener(view -> {
            Intent openActivity = new Intent(ActivitySelectGame.this, TimedGameActivity.class);
            startActivity(openActivity);
        });

        endlessGame.setOnClickListener(view -> {
            Intent openActivity = new Intent(ActivitySelectGame.this, EndlessGameActivity.class);
            startActivity(openActivity);
        });

        View.OnClickListener not_implemented = view -> Toast.makeText(ActivitySelectGame.this, "Not Implemented", Toast.LENGTH_LONG).show();
        verseGame.setOnClickListener(not_implemented);
        userGame.setOnClickListener(not_implemented);
    }
}
