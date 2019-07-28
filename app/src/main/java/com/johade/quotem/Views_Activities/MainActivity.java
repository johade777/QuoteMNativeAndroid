package com.johade.quotem.Views_Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.Models.Question;
import com.johade.quotem.Models.QuestionResponse;
import com.johade.quotem.R;
import com.johade.quotem.ViewModels.BasicGameViewModel;
import com.johade.quotem.Views_Activities.BasicGameActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button playGameButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playGameButton = findViewById(R.id.playGame);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
    }

    private void openGame(){
        Intent openActivity = new Intent(this, BasicGameActivity.class);
        startActivity(openActivity);
    }
}
