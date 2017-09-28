package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.Adapter.CustomSMSHistoryAdapter;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class SMSHistory extends AppCompatActivity {

    private static final String TAG = "SMSHistory";
    private static final int ACTIVITY_NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_history);
        Log.d(TAG, "onCreate: starting");

        setupBottomNavigationView();
        setListView();
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(SMSHistory.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    private void setListView() {
        String[] messages = {"14.04.2017", "20.09.2017", "25.09.2017"};
        ListAdapter listAdapter = new CustomSMSHistoryAdapter(this, messages);
        ListView studentsListView = (ListView) findViewById(R.id.sms_history_list_view);
        studentsListView.setAdapter(listAdapter);
    }
}
