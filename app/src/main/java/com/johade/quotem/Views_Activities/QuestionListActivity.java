package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.R;
import com.johade.quotem.helpers.RecyclerItemTouchHelper;
import com.johade.quotem.listeners.ConfirmDialogListener;
import com.johade.quotem.listeners.OnRecyclerItemClickListener;
import com.johade.quotem.adapters.QuestionAdapter;
import com.johade.quotem.listeners.RecyclerItemTouchHelperListener;
import com.johade.quotem.model.DeleteQuestionResponse;
import com.johade.quotem.model.GetQuizQuestionsResponse;
import com.johade.quotem.model.Question;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionListActivity extends AppCompatActivity implements OnRecyclerItemClickListener, RecyclerItemTouchHelperListener, ConfirmDialogListener {
    private RecyclerView mQuestionRecycler;
    private Button createQuestionButton;
    private QuoteMRepository repository;
    private QuestionAdapter adapter;
    private int quiz_id;
    private View rootLayout;
    private Drawable deleteIcon;
    private RecyclerView.ViewHolder deleteQuestionView;
    private ItemTouchHelper.SimpleCallback item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        mQuestionRecycler = findViewById(R.id.questionRecyclerView);
        createQuestionButton = findViewById(R.id.createQuestionButton);
        createQuestionButton.setOnClickListener(v -> createQuestion());
        deleteIcon = ContextCompat.getDrawable(this, R.drawable.delete_icon);
        rootLayout = findViewById(R.id.rootLayout);

        repository = new QuoteMRepository(this);
        Intent intent = getIntent();
        quiz_id = intent.getIntExtra("quiz_Id", -1);

        mQuestionRecycler.setLayoutManager(new LinearLayoutManager(this));
        mQuestionRecycler.setHasFixedSize(true);

        adapter = new QuestionAdapter(this);
        mQuestionRecycler.setAdapter(adapter);

        item = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(item);
        itemTouchHelper.attachToRecyclerView(mQuestionRecycler);
    }

    private void getQuestions(int quizId) {
        Call<GetQuizQuestionsResponse> call = repository.getQuizQuestions(quizId);
        call.enqueue(new Callback<GetQuizQuestionsResponse>() {
            @Override
            public void onResponse(Call<GetQuizQuestionsResponse> call, Response<GetQuizQuestionsResponse> response) {
                if (response.isSuccessful()) {
                    adapter.setmQuestions(response.body().getQuestions());
                } else {
                    Toast.makeText(QuestionListActivity.this, "Failed To Retrieve Questions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetQuizQuestionsResponse> call, Throwable t) {

            }
        });
    }

    private void createQuestion() {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("quiz_Id", quiz_id);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int itemPosition) {
        Intent intent = new Intent(this, QuestionActivity.class);
        Question clickedQuestion = adapter.getQuestion(itemPosition);
        intent.putExtra("question", clickedQuestion);
        intent.putExtra("quiz_Id", quiz_id);
        startActivity(intent);

    }

    @Override
    public void onItemLongClick(int itemPosition) {
    }

    @Override
    public void onDialogConfirm(DialogFragment dialog) {
        if(deleteQuestionView != null) {
            Question question = adapter.getQuestion(deleteQuestionView.getAdapterPosition());
            Call<DeleteQuestionResponse> call = repository.deleteQuestion(question.id);

            call.enqueue(new Callback<DeleteQuestionResponse>() {
                @Override
                public void onResponse(Call<DeleteQuestionResponse> call, Response<DeleteQuestionResponse> response) {
                    if (response.isSuccessful()) {
                        adapter.removeQuestion(deleteQuestionView);
                        deleteQuestionView = null;
                    } else {
                        Toast.makeText(QuestionListActivity.this, "Failed To Delete Question", Toast.LENGTH_SHORT).show();
                        deleteQuestionView = null;
                    }
                }

                @Override
                public void onFailure(Call<DeleteQuestionResponse> call, Throwable t) {
                    Toast.makeText(QuestionListActivity.this, "Failed To Delete Question", Toast.LENGTH_SHORT).show();
                    deleteQuestionView = null;
                }
            });
        }else{
            Toast.makeText(QuestionListActivity.this, "Failed To Delete Question", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDialogCancel(DialogFragment dialog) {
        item.clearView(mQuestionRecycler, deleteQuestionView);
        deleteQuestionView = null;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof QuestionAdapter.QuestionViewHolder) {
            Question swipedQuestion = adapter.getQuestion(viewHolder.getAdapterPosition());
            ConfirmDialog deleteAlert = new ConfirmDialog(swipedQuestion.question, ConfirmDialog.AlertType.QUESTION);
            deleteAlert.show(getSupportFragmentManager(), "deleteAlert");
            deleteQuestionView = viewHolder;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (quiz_id < 0) {
            Toast.makeText(this, "Invalid Quiz Id", Toast.LENGTH_LONG).show();
        } else {
            getQuestions(quiz_id);
        }
    }
}
