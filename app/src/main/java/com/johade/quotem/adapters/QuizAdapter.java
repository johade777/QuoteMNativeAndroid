package com.johade.quotem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.johade.quotem.R;
import com.johade.quotem.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private List<Quiz> mQuizzes = new ArrayList<>();
    private OnRecyclerItemClickListener onItemClick;

    public QuizAdapter(OnRecyclerItemClickListener itemClickListener){
        this.onItemClick = itemClickListener;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_quiz, parent, false);
        return new QuizViewHolder(itemView, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz currentQuiz = mQuizzes.get(position);
        holder.quizName.setText(currentQuiz.getQuiz_name());
        holder.quizId.setText("Score: " + currentQuiz.getQuiz_id());
    }

    @Override
    public int getItemCount() {
        return mQuizzes.size();
    }

    public void setmQuizzes(List<Quiz> quizzes){
        this.mQuizzes = quizzes;
        notifyDataSetChanged();
    }

    public Quiz getQuiz(int postion){
        return mQuizzes.get(postion);
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView quizName;
        private TextView quizId;
        private TextView createdDate;
        private TextView questionCount;
        private OnRecyclerItemClickListener onClickListener;

        public QuizViewHolder(@NonNull View itemView, OnRecyclerItemClickListener onClickListener) {
            super(itemView);
            quizName = itemView.findViewById(R.id.quizName);
            quizId = itemView.findViewById(R.id.quizId);
            createdDate = itemView.findViewById(R.id.quizDate);
            questionCount = itemView.findViewById(R.id.questionNumber);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onItemClick(getAdapterPosition());
        }
    }
}
