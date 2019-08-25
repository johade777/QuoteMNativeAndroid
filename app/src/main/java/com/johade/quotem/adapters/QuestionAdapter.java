package com.johade.quotem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.johade.quotem.R;
import com.johade.quotem.listeners.OnRecyclerItemClickListener;
import com.johade.quotem.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> mQuestions = new ArrayList<>();
    private OnRecyclerItemClickListener onItemClick;

    public QuestionAdapter(OnRecyclerItemClickListener itemClickListener){
        this.onItemClick = itemClickListener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_question, parent, false);
        return new QuestionViewHolder(itemView, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question currentQuestion = mQuestions.get(position);
        if(currentQuestion == null){
            System.out.println("Something Happened");
        }else {
            holder.questionText.setText(currentQuestion.question);
            holder.answerText.setText(currentQuestion.answer);
        }
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public void removeQuestion(RecyclerView.ViewHolder holder){
        mQuestions.remove(holder.getAdapterPosition());
        notifyItemChanged(holder.getAdapterPosition());
    }

    public void setmQuestions(List<Question> questions){
        this.mQuestions = questions;
        notifyDataSetChanged();
    }

    public Question getQuestion(int postion){
        return mQuestions.get(postion);
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView questionText;
        private TextView answerText;
        public View foreground;
        public View background;
        private OnRecyclerItemClickListener onClickListener;

        public QuestionViewHolder(@NonNull View itemView, OnRecyclerItemClickListener onClickListener) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionItem);
            answerText = itemView.findViewById(R.id.answerItem);
            this.onClickListener = onClickListener;
            this.foreground = itemView.findViewById(R.id.view_foreground);
            this.background = itemView.findViewById(R.id.view_background);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }
}