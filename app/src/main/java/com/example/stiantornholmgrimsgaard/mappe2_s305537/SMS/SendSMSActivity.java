package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class SendSMSActivity extends AppCompatActivity {

    private static final String TAG = "SendSMSActivity";
    private static final int ACTIVITY_NUM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        Log.d(TAG, "onCreate: starting");

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(SendSMSActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }
}
