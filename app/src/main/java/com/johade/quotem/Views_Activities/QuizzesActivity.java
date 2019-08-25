package com.johade.quotem.Views_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.johade.quotem.R;
import com.johade.quotem.adapters.ConfirmDialogListener;
import com.johade.quotem.adapters.OnRecyclerItemClickListener;
import com.johade.quotem.adapters.QuizAdapter;
import com.johade.quotem.adapters.RecyclerItemTouchHelper;
import com.johade.quotem.adapters.RecyclerItemTouchHelperListener;
import com.johade.quotem.model.DeleteQuizResponse;
import com.johade.quotem.model.GetQuizzesResponse;
import com.johade.quotem.model.Quiz;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzesActivity extends AppCompatActivity implements OnRecyclerItemClickListener, RecyclerItemTouchHelperListener, ConfirmDialogListener {
    private RecyclerView mQuizReclcyer;
    private Button createQuizButton;
    private QuizAdapter adapter;
    private QuoteMRepository repository;
    private View rootLayout;
    private Drawable deleteIcon;
    private RecyclerView.ViewHolder deleteQuizView;
    ItemTouchHelper.SimpleCallback item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);

        deleteIcon = ContextCompat.getDrawable(this, R.drawable.delete_icon);
        mQuizReclcyer = findViewById(R.id.quizRecyclerView);
        createQuizButton = findViewById(R.id.createQuizButton);
        rootLayout = findViewById(R.id.rootLayout);

        repository = new QuoteMRepository(this);
        createQuizButton.setOnClickListener(view -> createNewQuiz());
        mQuizReclcyer.setLayoutManager(new LinearLayoutManager(this));
        mQuizReclcyer.setHasFixedSize(true);

        item = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        adapter = new QuizAdapter(this);
        mQuizReclcyer.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(item);
        itemTouchHelper.attachToRecyclerView(mQuizReclcyer);
    }

    private void getQuizzes() {
        Call<GetQuizzesResponse> call = repository.getUserQizzes();
        call.enqueue(new Callback<GetQuizzesResponse>() {
            @Override
            public void onResponse(Call<GetQuizzesResponse> call, Response<GetQuizzesResponse> response) {
                if (response.isSuccessful()) {
                    adapter.setmQuizzes(response.body().getQuizzes());
                } else {
                    Toast.makeText(QuizzesActivity.this, "Failed To Retrieve Quizzes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetQuizzesResponse> call, Throwable t) {

            }
        });
    }

    private void createNewQuiz() {
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivity(intent);
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
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof QuizAdapter.QuizViewHolder) {
            Quiz swipedQuiz = adapter.getQuiz(viewHolder.getAdapterPosition());
            ConfirmDialog deleteAlert = new ConfirmDialog(swipedQuiz.getQuiz_name());
            deleteAlert.show(getSupportFragmentManager(), "What");
            deleteQuizView = viewHolder;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getQuizzes();
    }

    @Override
    public void onDialogConfirm(DialogFragment dialog) {
        if (deleteQuizView != null) {
            Quiz toDeleteQuiz = adapter.getQuiz(deleteQuizView.getAdapterPosition());
            Call<DeleteQuizResponse> call = repository.deleteQuiz(toDeleteQuiz.getQuiz_id());
            call.enqueue(new Callback<DeleteQuizResponse>() {
                @Override
                public void onResponse(Call<DeleteQuizResponse> call, Response<DeleteQuizResponse> response) {
                    if (response.isSuccessful()) {
                        adapter.removeQuiz(deleteQuizView);
                        deleteQuizView = null;
                    } else {
                        Toast.makeText(QuizzesActivity.this, "Failed To Delete Quiz", Toast.LENGTH_SHORT).show();
                        deleteQuizView = null;
                    }
                }

                @Override
                public void onFailure(Call<DeleteQuizResponse> call, Throwable t) {
                    deleteQuizView = null;
                }
            });
        }else{
            Toast.makeText(this, "Error Removing Quiz", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogCancel(DialogFragment dialog) {
        Toast.makeText(this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
        item.clearView(mQuizReclcyer, deleteQuizView);
        deleteQuizView = null;
    }
}