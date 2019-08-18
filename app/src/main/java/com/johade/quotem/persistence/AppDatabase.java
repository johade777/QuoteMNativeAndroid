package com.johade.quotem.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.johade.quotem.model.Highscore;

@androidx.room.Database(entities = {Highscore.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context applicationContext){
        if(instance == null){
            instance = Room.databaseBuilder(applicationContext, AppDatabase.class, "quotem_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(newDbCallback)
                    .build();
        }
        return instance;
    }

    public abstract HighScoreDao highScoreDao();

    private static RoomDatabase.Callback newDbCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private HighScoreDao highScoreDao;

        public PopulateDbAsync(AppDatabase db){
            highScoreDao = db.highScoreDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            highScoreDao.insert(new Highscore("Test 1", 0));
            return null;
        }
    }
}
