package com.example.mvvmdemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_table")
public class DataModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "dept_name")
    private String deptName;

//    public DataModel(int id, String name, String deptName) {
//        this.id = id;
//        this.name = name;
//        this.deptName = deptName;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeptName() {
        return deptName;
    }
}
