package com.example.stiantornholmgrimsgaard.mappe2_s305537.Party;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class AddStudentActivity extends AppCompatActivity {

    private static final String TAG = "AddStudentActivity";
    private static final int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Log.d(TAG, "onCreate: starting");

        Button exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddStudentActivity.this, StudentsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(this, AddStudentActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    public void addStudent(View view) {
        DBHandler dbHandler = new DBHandler(this);

        String firstName = ((EditText) findViewById(R.id.first_name_edit_text)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.last_name_edit_text)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.phone_number_edit_text)).getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty()) {
            Student student = new Student(firstName, lastName, phoneNumber);
            dbHandler.addStudent(student);

            Intent intent = new Intent(this, StudentsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddStudentActivity.this, StudentsActivity.class);
        startActivity(intent);
        finish();
    }
}
