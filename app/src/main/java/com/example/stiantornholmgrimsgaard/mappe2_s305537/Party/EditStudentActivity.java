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

public class EditStudentActivity extends AppCompatActivity {

    private static final String TAG = "EditStudentActivity";
    private static final int ACTIVITY_NUM = 0;

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;

    private Student student;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        Log.d(TAG, "onCreate: starting");

        setupBottomNavigationView();

        Button exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditStudentActivity.this, StudentsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        firstName = (EditText) findViewById(R.id.edit_first_name_edit_text);
        lastName = (EditText) findViewById(R.id.edit_last_name_edit_text);
        phoneNumber = (EditText) findViewById(R.id.edit_phone_number_edit_text);

        long studentId = getIntent().getLongExtra("id", 0);
        dbHandler = new DBHandler(this);
        student = dbHandler.getStudent(studentId);

        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        phoneNumber.setText(student.getPhoneNumber());

        firstName.setSelection(student.getFirstName().length());
        lastName.setSelection(student.getLastName().length());
        phoneNumber.setSelection(student.getPhoneNumber().length());
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(this, EditStudentActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    public void updateStudent(View view) {
        student.setFirstName(firstName.getText().toString());
        student.setLastName(lastName.getText().toString());
        student.setPhoneNumber(phoneNumber.getText().toString());
        dbHandler.updateStudent(student);

        Intent intent = new Intent(this, StudentsActivity.class);
        startActivity(intent);
        finish();
    }

    public void deleteStudent(View view) {
        dbHandler.deleteStudent(student.getId());

        Intent intent = new Intent(this, StudentsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditStudentActivity.this, StudentsActivity.class);
        startActivity(intent);
        finish();
    }
}
