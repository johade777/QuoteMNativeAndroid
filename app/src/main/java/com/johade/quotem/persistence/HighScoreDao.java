package com.johade.quotem.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.johade.quotem.model.Highscore;
import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface HighScoreDao {

    @Query("Select * from Highscore ORDER BY score DESC")
    Flowable<List<Highscore>> getAllHighScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Highscore score);

    @Delete
    void delete(Highscore score);

//    @Query("Select * from Highscore ORDER BY score LIMIT 1")
//    Flowable<Highscore> getLowestHighScore();
//
//    @Update
//    Single<Highscore> updateHighscore(Highscore highscore);
}
