package com.example.stiantornholmgrimsgaard.mappe2_s305537.Broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMS;

/**
 * Created by stiantornholmgrimsgaard on 06.10.2017.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DBHandler dbHandler = new DBHandler(context);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (intent.hasExtra("smsID")) {
            long smsId = intent.getExtras().getLong("smsID");
            SMS sms = dbHandler.getSMS(smsId);
            setAlarm(context, alarm, sms);
        } else {
            for (SMS sms : dbHandler.getSMS()) {
                if (!sms.isSent()) {
                    setAlarm(context, alarm, sms);
                }
            }
        }
    }

    private void setAlarm(Context context, AlarmManager alarm, SMS sms) {
        int _id = (int) System.currentTimeMillis();
        Intent i = new Intent(context, SMSService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, _id, i, 0);
        alarm.set(AlarmManager.RTC_WAKEUP, sms.getDate(), pendingIntent);
    }
}
