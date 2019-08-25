package com.johade.quotem.Views_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.R;
import com.johade.quotem.adapters.OnRecyclerItemClickListener;
import com.johade.quotem.adapters.QuizAdapter;
import com.johade.quotem.model.DeleteQuizResponse;
import com.johade.quotem.model.GetQuizzesResponse;
import com.johade.quotem.model.Quiz;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzesActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    private RecyclerView mQuizReclcyer;
    private Button createQuizButton;
    private QuizAdapter adapter;
    private QuoteMRepository repository;
    private AlertDialog deleteAlert;

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
        createDialog();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Quiz deletedQuiz = adapter.getQuiz(viewHolder.getAdapterPosition());
//                Call<DeleteQuizResponse> call = repository.deleteQuiz(deletedQuiz.getQuiz_id());
//                call.enqueue(new Callback<DeleteQuizResponse>() {
//                    @Override
//                    public void onResponse(Call<DeleteQuizResponse> call, Response<DeleteQuizResponse> response) {
//                        if(response.isSuccessful()) {
//                            adapter.removeQuiz(viewHolder);
//                        }
//                        else{
//                            Toast.makeText(QuizzesActivity.this, "Failed To Delete Quiz", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<DeleteQuizResponse> call, Throwable t) {
//
//                    }
//                });
            }
        };

        adapter = new QuizAdapter(this);
        mQuizReclcyer.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mQuizReclcyer);
    }

    private void getQuizzes(){
        Call<GetQuizzesResponse> call = repository.getUserQizzes();
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
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivity(intent);
    }

    private void createDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Delete Quiz Whatever?");
        alertBuilder.setCancelable(false);

        alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

        deleteAlert = alertBuilder.create();
        deleteAlert.setTitle("Delete Quiz?");
    }

    @Override
    public void onItemClick(int itemPosition) {
        Intent intent = new Intent(this, QuestionsActivity.class);
        Quiz clickedQuiz = adapter.getQuiz(itemPosition);
        intent.putExtra("quiz_Id", clickedQuiz.getQuiz_id());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int itemPostion) {
        Toast.makeText(this, "Long Click: " + adapter.getQuiz(itemPostion).getQuiz_name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getQuizzes();
    }
}
