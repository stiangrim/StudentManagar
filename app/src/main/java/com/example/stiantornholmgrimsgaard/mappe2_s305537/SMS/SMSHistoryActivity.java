package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.EditStudentActivity;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.Student;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.Adapter.CustomSMSHistoryAdapter;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSHistoryActivity extends AppCompatActivity {

    private static final String TAG = "SMSHistoryActivity";
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
        BottomNavigationViewHelper.setupBottomNavigationView(SMSHistoryActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    private void setListView() {
        ListAdapter listAdapter = new CustomSMSHistoryAdapter(this, getSMS());
        final ListView smsListView = (ListView) findViewById(R.id.sms_history_list_view);
        smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SMS sms = (SMS) smsListView.getItemAtPosition(i);
                showSMSDialog(sms);
            }
        });
        smsListView.setAdapter(listAdapter);
    }

    private void showSMSDialog(SMS sms) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date resultDate = new Date(sms.getDate());

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(sms.getMessage())
                .setTitle(sdf.format(resultDate))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private SMS[] getSMS() {
        DBHandler dbHandler = new DBHandler(this);

        return dbHandler.getSMS().toArray(new SMS[0]);
    }
}
