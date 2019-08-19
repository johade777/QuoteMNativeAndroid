package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.R;
import com.johade.quotem.adapters.OnRecyclerItemClickListener;
import com.johade.quotem.adapters.QuizAdapter;
import com.johade.quotem.model.GetQuizzesResponse;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzesActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    private RecyclerView mQuizReclcyer;
    private Button createQuizButton;
    private QuizAdapter adapter;
    private QuoteMRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);

        mQuizReclcyer = findViewById(R.id.quizRecyclerView);
        createQuizButton = findViewById(R.id.createQuizButton);

        repository = new QuoteMRepository(this);
        createQuizButton.setOnClickListener(view -> createNewQuiz());
        mQuizReclcyer.setLayoutManager(new LinearLayoutManager(this));
        mQuizReclcyer.setHasFixedSize(true);

        adapter = new QuizAdapter(this);
        mQuizReclcyer.setAdapter(adapter);

        getQuizzes();
    }

    private void getQuizzes(){
        Call<GetQuizzesResponse> call = repository.getUserQizzes(repository.getToken());
        call.enqueue(new Callback<GetQuizzesResponse>() {
            @Override
            public void onResponse(Call<GetQuizzesResponse> call, Response<GetQuizzesResponse> response) {
                if(response.isSuccessful()) {
                    adapter.setmQuizzes(response.body().getQuizzes());
                }
                else{
                    Toast.makeText(QuizzesActivity.this, "Failed To Retrieve Quizzes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetQuizzesResponse> call, Throwable t) {

            }
        });
    }


    private void createNewQuiz(){

    }

    @Override
    public void onItemClick(int itemPosition) {
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("Quiz_Id", itemPosition);
        startActivity(intent);
        //Toast.makeText(this, "Position: " + itemPosition, Toast.LENGTH_SHORT).show();
    }
}
