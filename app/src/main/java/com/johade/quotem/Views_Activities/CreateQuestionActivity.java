package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.johade.quotem.R;
import com.johade.quotem.model.InsertQuestionResponse;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateQuestionActivity extends AppCompatActivity {
    private QuoteMRepository repository;
    private TextInputEditText questionInput;
    private TextInputEditText answerInput;
    private TextInputEditText wrongAnswerOneInput;
    private TextInputEditText wrongAnswerTwoInput;
    private TextInputEditText wrongAnswerThreeInput;
    private Button insertQuestionButton;
    private int quiz_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        repository = new QuoteMRepository(this);

        questionInput = findViewById(R.id.questionInput);
        answerInput = findViewById(R.id.answerInput);
        wrongAnswerOneInput = findViewById(R.id.wrongOneInput);
        wrongAnswerTwoInput = findViewById(R.id.wrongTwoInput);
        wrongAnswerThreeInput = findViewById(R.id.wrongThreeInput);
        insertQuestionButton = findViewById(R.id.insertQuestionButton);
        insertQuestionButton.setOnClickListener(v -> insertQuestion());

        Intent intent = getIntent();
        quiz_id = intent.getIntExtra("quiz_Id", -1);
        if(quiz_id < 0){
            Toast.makeText(this, "Invalid Quiz Id", Toast.LENGTH_LONG).show();
        }
    }

    private void insertQuestion() {
        String question = questionInput.getText().toString();
        String answer = answerInput.getText().toString();
        String wrong1 = wrongAnswerOneInput.getText().toString();
        String wrong2 = wrongAnswerTwoInput.getText().toString();
        String wrong3 = wrongAnswerThreeInput.getText().toString();

        Call<InsertQuestionResponse> call = repository.insertQuestion(quiz_id, question, answer, wrong1, wrong2, wrong3);
        call.enqueue(new Callback<InsertQuestionResponse>() {
            @Override
            public void onResponse(Call<InsertQuestionResponse> call, Response<InsertQuestionResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(CreateQuestionActivity.this, "Question Created", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(CreateQuestionActivity.this, "Failed To Create Question", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertQuestionResponse> call, Throwable t) {

            }
        });
    }
}
