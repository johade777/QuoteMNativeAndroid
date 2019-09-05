package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.johade.quotem.R;
import com.johade.quotem.model.InsertQuestionResponse;
import com.johade.quotem.model.Question;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUpdateQuestionActivity extends AppCompatActivity {
    private QuoteMRepository repository;
    private TextView headerText;
    private TextInputEditText questionInput;
    private TextInputEditText answerInput;
    private TextInputEditText wrongAnswerOneInput;
    private TextInputEditText wrongAnswerTwoInput;
    private TextInputEditText wrongAnswerThreeInput;
    private Button postQuestionButton;
    private Question clickedQuestion;
    private int quiz_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        repository = new QuoteMRepository(this);

        headerText = findViewById(R.id.header);
        questionInput = findViewById(R.id.questionInput);
        answerInput = findViewById(R.id.answerInput);
        wrongAnswerOneInput = findViewById(R.id.wrongOneInput);
        wrongAnswerTwoInput = findViewById(R.id.wrongTwoInput);
        wrongAnswerThreeInput = findViewById(R.id.wrongThreeInput);
        postQuestionButton = findViewById(R.id.insertQuestionButton);
        postQuestionButton.setOnClickListener(v -> insertQuestion());

        Intent intent = getIntent();
        quiz_id = intent.getIntExtra("quiz_Id", -1);
        clickedQuestion = intent.getParcelableExtra("question");
        if(quiz_id < 0){
            Toast.makeText(this, "Invalid Quiz Id", Toast.LENGTH_LONG).show();
        }
        if(clickedQuestion != null){
            questionInput.setText(clickedQuestion.question);
            answerInput.setText(clickedQuestion.answer);
            wrongAnswerOneInput.setText(clickedQuestion.wrong1);
            wrongAnswerTwoInput.setText(clickedQuestion.wrong2);
            wrongAnswerThreeInput.setText(clickedQuestion.wrong3);
            postQuestionButton.setText("Update Question");
            headerText.setText("Update Question");
            postQuestionButton.setOnClickListener(v -> updateQuestion());
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
                    Toast.makeText(CreateUpdateQuestionActivity.this, "Question Created", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(CreateUpdateQuestionActivity.this, "Failed To Create Question", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertQuestionResponse> call, Throwable t) {

            }
        });
    }

    private void updateQuestion(){
        Toast.makeText(this, "Update Question Pressed", Toast.LENGTH_SHORT).show();
    }
}
