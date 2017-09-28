package com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.AddStudentActivity;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Preferences.PreferencesActivity;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMSHistory;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SendSMSActivity;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.StudentsActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by stiantornholmgrimsgaard on 25.09.2017.
 */

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(final Context context, BottomNavigationViewEx bottomNavigationViewEx, int activityNum) {
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");

        setStyle(bottomNavigationViewEx);
        enableNavigation(context, bottomNavigationViewEx);
        setMenu(bottomNavigationViewEx, activityNum);
    }

    private static void setStyle(BottomNavigationViewEx bottomNavigationViewEx) {
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        bottomNavigationViewEx.setIconSize(35, 35);
        bottomNavigationViewEx.setPadding(0, 0, 0, 30);
    }

    private static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch (item.getItemId()) {
                    case R.id.navigation_students:
                        intent = new Intent(context, StudentsActivity.class);
                        context.startActivity(intent);
                        return true;
                    case R.id.navigation_add_student:
                        intent = new Intent(context, AddStudentActivity.class);
                        context.startActivity(intent);
                        return true;
                    case R.id.navigation_send_sms:
                        intent = new Intent(context, SendSMSActivity.class);
                        context.startActivity(intent);
                        return true;
                    case R.id.navigation_sms_history:
                        intent = new Intent(context, SMSHistory.class);
                        context.startActivity(intent);
                        return true;
                    case R.id.navigation_preferences:
                        intent = new Intent(context, PreferencesActivity.class);
                        context.startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    private static void setMenu(BottomNavigationViewEx view, int activityNum) {
        Menu menu = view.getMenu();
        MenuItem menuItem = menu.getItem(activityNum);
        menuItem.setChecked(true);
    }
}
