package com.example.stiantornholmgrimsgaard.mappe2_s305537.Party;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.Adapter.CustomStudentAdapter;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class StudentsActivity extends AppCompatActivity {

    private static final String TAG = "StudentsActivity";
    private static final int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        Log.d(TAG, "onCreate: starting");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_student_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentsActivity.this, AddStudentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setupBottomNavigationView();
        setListView();
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(this, StudentsActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    private void setListView() {
        ListAdapter listAdapter = new CustomStudentAdapter(this, getStudents());
        final ListView studentsListView = (ListView) findViewById(R.id.students_list_view);
        final Context context = this;
        studentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student = (Student) studentsListView.getItemAtPosition(i);

                Intent intent = new Intent(context, EditStudentActivity.class);
                intent.putExtra("id", student.getId());
                startActivity(intent);
                finish();
            }
        });
        studentsListView.setAdapter(listAdapter);

    }

    private Student[] getStudents() {
        DBHandler dbHandler = new DBHandler(this);

        return dbHandler.getStudents().toArray(new Student[0]);
    }

}
