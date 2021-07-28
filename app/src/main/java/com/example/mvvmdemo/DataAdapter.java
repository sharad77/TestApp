package com.example.mvvmdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter  extends RecyclerView.Adapter<DataAdapter.MyViewHolder>{
    private List<DataModel> dataArray;
    private Context context;

    public DataAdapter(ArrayList<DataModel> dataArray, Context context) {
        this.dataArray = dataArray;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, deptName;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tv_name);
            deptName = v.findViewById(R.id.tv_dept_name);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_item_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.MyViewHolder holder, int position) {
        holder.name.setText(dataArray.get(position).getName());
        holder.deptName.setText(dataArray.get(position).getDeptName());

    }

    public void getAllData(List<DataModel> dataModelList)
    {
        this.dataArray = dataModelList;
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }
}
