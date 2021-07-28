package com.example.mvvmdemo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DataModel.class}, version = 1)
public abstract class DataRoomDb extends RoomDatabase {

    private static final String DATABASE_NAME = "DataListDb";

    public abstract DataDao dataDao();

    private static volatile DataRoomDb INSTANCE;

    public static DataRoomDb getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DataRoomDb.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,DataRoomDb.class,DATABASE_NAME)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE);
        }
    };

    static class PopulateDbAsyncTask extends AsyncTask<Void,Void, Void> {

        private DataDao dataDao;
        PopulateDbAsyncTask(DataRoomDb dataRoomDb){
            dataDao =dataRoomDb.dataDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataDao.deleteAll();
            return null;
        }
    }
}
