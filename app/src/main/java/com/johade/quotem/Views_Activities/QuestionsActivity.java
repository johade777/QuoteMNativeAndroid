package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.R;
import com.johade.quotem.listeners.OnRecyclerItemClickListener;
import com.johade.quotem.adapters.QuestionAdapter;
import com.johade.quotem.model.DeleteQuestionResponse;
import com.johade.quotem.model.GetQuizQuestionsResponse;
import com.johade.quotem.model.Question;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    private RecyclerView questionReclcyer;
    private Button createQuestionButton;
    private QuoteMRepository repository;
    private QuestionAdapter adapter;
    private int quiz_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        questionReclcyer = findViewById(R.id.questionRecyclerView);
        createQuestionButton = findViewById(R.id.createQuestionButton);
        createQuestionButton.setOnClickListener(v -> createQuestion());

        repository = new QuoteMRepository(this);
        Intent intent = getIntent();
        quiz_id = intent.getIntExtra("quiz_Id", -1);
        if(quiz_id < 0){
            Toast.makeText(this, "Invalid Quiz Id", Toast.LENGTH_LONG).show();
        }

        getQuestions(quiz_id);

        questionReclcyer.setLayoutManager(new LinearLayoutManager(this));
        questionReclcyer.setHasFixedSize(true);

        adapter = new QuestionAdapter(this);
        questionReclcyer.setAdapter(adapter);
    }

    private void getQuestions(int quizId) {
        Call<GetQuizQuestionsResponse> call = repository.getQuizQuestions(quizId);
        call.enqueue(new Callback<GetQuizQuestionsResponse>() {
            @Override
            public void onResponse(Call<GetQuizQuestionsResponse> call, Response<GetQuizQuestionsResponse> response) {
                if(response.isSuccessful()) {
                    adapter.setmQuestions(response.body().getQuestions());
                }
                else{
                    Toast.makeText(QuestionsActivity.this, "Failed To Retrieve Questions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetQuizQuestionsResponse> call, Throwable t) {

            }
        });
    }

    private void createQuestion() {
        Intent intent = new Intent(this, CreateQuestionActivity.class);
        intent.putExtra("quiz_Id", quiz_id);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int itemPosition) {
        Toast.makeText(this, adapter.getQuestion(itemPosition).question, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int itemPostion) {
        Question question = adapter.getQuestion(itemPostion);
        Call<DeleteQuestionResponse> call = repository.deleteQuestion(question.id);

        call.enqueue(new Callback<DeleteQuestionResponse>() {
            @Override
            public void onResponse(Call<DeleteQuestionResponse> call, Response<DeleteQuestionResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(QuestionsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(QuestionsActivity.this, "Failed To Delete Question", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteQuestionResponse> call, Throwable t) {

            }
        });
    }
}
