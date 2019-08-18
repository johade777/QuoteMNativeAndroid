package com.johade.quotem.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Highscore {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "user_name")
    private String username;

    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "score_date")
    private Date scoreDate;

    public Highscore(String username, int score){
        this.username = username;
        this.score = score;
        this.scoreDate = Calendar.getInstance().getTime();
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public Date getScoreDate() {
        return scoreDate;
    }
}
