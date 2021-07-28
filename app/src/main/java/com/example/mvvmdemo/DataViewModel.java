package com.example.mvvmdemo;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DataViewModel extends AndroidViewModel {

    private DataRepository dataRepository;
    private LiveData<List<DataModel>> getData;

    public DataViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        getData = dataRepository.getGetAllData();
    }

    public void insert(List<DataModel> dataModelList){
        dataRepository.insert(dataModelList);
    }

    public LiveData<List<DataModel>> getAllData(){
        return getData;
    }
}

