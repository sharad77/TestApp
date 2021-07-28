package com.example.mvvmdemo;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DataRepository {
    private DataRoomDb dataRoomDb;
    private LiveData<List<DataModel>> getAllData;

    public DataRepository(Application application){
        dataRoomDb = DataRoomDb.getInstance(application);
        getAllData = dataRoomDb.dataDao().getAllData();
    }

    public void insert(List<DataModel> dataList){
        new InsertAsyncTask(dataRoomDb).execute(dataList);
    }

    public LiveData<List<DataModel>> getGetAllData() {
        return getAllData;
    }

    private static class InsertAsyncTask extends AsyncTask<List<DataModel>, Void, Void> {
        private DataDao dataDao;
        InsertAsyncTask(DataRoomDb dataRoomDb){
            dataDao =dataRoomDb.dataDao();
        }

        @Override
        protected Void doInBackground(List<DataModel>... lists) {
            dataDao.insert(lists[0]);
            return null;
        }
    }
}
