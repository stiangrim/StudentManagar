package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.EditStudentActivity;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.Student;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSViewActivity extends AppCompatActivity {

    private static final String TAG = "SMSViewActivity";
    private static final int ACTIVITY_NUM = 3;

    private SMS sms;
    private DBHandler dbHandler;

    private EditText date;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_view);

        Log.d(TAG, "onCreate: starting");

        setupBottomNavigationView();

        date = (EditText) findViewById(R.id.sms_date_view_edit_text);
        message = (EditText) findViewById(R.id.sms_content_view_edit_text);

        long smsId = getIntent().getLongExtra("id", 0);
        dbHandler = new DBHandler(this);
        sms = dbHandler.getSMS(smsId);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date resultDate = new Date(sms.getDate());

        date.setText(sdf.format(resultDate));
        message.setText(sms.getMessage());
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(SMSViewActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }
}
