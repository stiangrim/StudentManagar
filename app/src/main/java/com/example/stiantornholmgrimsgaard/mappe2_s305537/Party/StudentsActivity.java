package com.example.stiantornholmgrimsgaard.mappe2_s305537.Party;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.Adapter.CustomStudentAdapter;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class StudentsActivity extends AppCompatActivity {

    private static final String TAG = "StudentsActivity";
    private static final int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        Log.d(TAG, "onCreate: starting");

        setupBottomNavigationView();
        setListView();
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(StudentsActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    private void setListView() {
        ListAdapter listAdapter = new CustomStudentAdapter(this, getStudents());
        ListView studentsListView = (ListView) findViewById(R.id.students_list_view);
        studentsListView.setAdapter(listAdapter);
    }

    private Student[] getStudents() {
        DBHandler dbHandler = new DBHandler(this);

        return dbHandler.getStudents().toArray(new Student[0]);
    }

}
