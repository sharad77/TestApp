package com.example.mvvmdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<DataModel> listArray = new ArrayList<DataModel>();
    private DataViewModel dataViewModel;
    private DataAdapter dataAdapter;
    String URL = "http://karma.equinoxlab.com/betaDailyUpdateApi/Service1.svc/getManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new DataAdapter(listArray, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.getAllData().observe(this, new Observer<List<DataModel>>() {
            @Override
            public void onChanged(List<DataModel> dataModelList) {
                dataAdapter.getAllData(dataModelList);
                mRecyclerView.setAdapter(dataAdapter);

                Log.d("main", "onChanged: " + dataModelList);
            }
        });

        networkRequest();
    }

    public void networkRequest() {
        try {
            listArray.clear();

            OkHttpClient client = new OkHttpClient();
            final FormBody formBody = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody responseBody = response.body();
                    final String myResponse = responseBody.string();

                    runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
//                                if (jsonObject.has("response")) {
//                                    if (jsonObject.getString("response").equalsIgnoreCase("success")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("DATA");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jObj = jsonArray.getJSONObject(i);
                                        DataModel model = new DataModel();
                                        if (jObj.has("name"))
                                            model.setName(jObj.getString("name"));
                                        if (jObj.has("dept_name"))
                                            model.setDeptName(jObj.getString("dept_name"));

                                        listArray.add(model);
                                    }
                                    mAdapter.notifyDataSetChanged();
//                                    } else {
//                                        Toast.makeText(MainActivity.this, "No Data Available", Toast.LENGTH_LONG).show();
//                                    }
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Please Try Later", Toast.LENGTH_SHORT).show();
//                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}