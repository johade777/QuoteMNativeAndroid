package com.johade.quotem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.johade.quotem.model.Highscore;
import com.johade.quotem.R;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreHolder> {
    private List<Highscore> mHighscores = new ArrayList<>();
    private final Format f = new SimpleDateFormat("MM/dd/yyyy");

    @NonNull
    @Override
    public HighScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_highscore, parent, false);
        return new HighScoreHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreHolder holder, int position) {
        Highscore currentScore = mHighscores.get(position);
        holder.username.setText(currentScore.getUsername());
        holder.score.setText("Score: " + currentScore.getScore());
        holder.date.setText("Date: " + f.format(currentScore.getScoreDate()));
    }

    public void setHighscores(List<Highscore> scores){
        this.mHighscores = scores;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHighscores.size();
    }

    public class HighScoreHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private TextView date;
        private TextView score;

        public HighScoreHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
            date = itemView.findViewById(R.id.scoreDate);
            score = itemView.findViewById(R.id.score);
        }
    }
}
