package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewSMSActivity extends AppCompatActivity {

    private static final String TAG = "EditStudentActivity";
    private static final int ACTIVITY_NUM = 2;
    DBHandler dbHandler;
    SMS sms;

    EditText smsViewTimeEditText;
    EditText smsViewContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sms);
        Log.d(TAG, "onCreate: ViewSMSActivity");
        setupBottomNavigationView();

        Button exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSMSActivity.this, SMSHistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        long smsId = getIntent().getLongExtra("id", 0);
        dbHandler = new DBHandler(this);
        sms = dbHandler.getSMS(smsId);

        smsViewTimeEditText = (EditText) findViewById(R.id.sms_view_time_edit_text);
        smsViewContentEditText = (EditText) findViewById(R.id.sms_view_content_edit_text);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date resultDate = new Date(sms.getDate());

        smsViewTimeEditText.setText(sdf.format(resultDate));
        smsViewContentEditText.setText(sms.getMessage());
    }

    public void deleteSMS(View view) {
        dbHandler.deleteSMS(sms.getId());
        Intent intent = new Intent(ViewSMSActivity.this, SMSHistoryActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(this, ViewSMSActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewSMSActivity.this, SMSHistoryActivity.class);
        startActivity(intent);
        finish();
    }
}
