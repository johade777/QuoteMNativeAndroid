package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.johade.quotem.R;
import com.johade.quotem.model.InsertQuizResponse;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateQuizActivity extends AppCompatActivity {
    private TextInputEditText quizNameInput;
    private Button createQuizButton;
    private QuoteMRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        repository = new QuoteMRepository(this);
        quizNameInput = findViewById(R.id.createQuizName);
        createQuizButton = findViewById(R.id.createQuizButton);
        createQuizButton.setOnClickListener(v -> createQuiz());
    }

    private void createQuiz() {
        String quizName = quizNameInput.getText().toString();
        Call<InsertQuizResponse> call = repository.insertQuiz(quizName, "");
        call.enqueue(new Callback<InsertQuizResponse>() {
            @Override
            public void onResponse(Call<InsertQuizResponse> call, Response<InsertQuizResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(CreateQuizActivity.this, "Quiz Created", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(CreateQuizActivity.this, "Failed To Create Quiz", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertQuizResponse> call, Throwable t) {

            }
        });
    }
}
