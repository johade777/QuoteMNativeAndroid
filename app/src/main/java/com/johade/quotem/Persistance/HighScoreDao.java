package com.johade.quotem.Persistance;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.johade.quotem.Models.Highscore;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface HighScoreDao {

    @Query("Select * from Highscore")
    Flowable<List<Highscore>> getAllHighScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Highscore score);
}
