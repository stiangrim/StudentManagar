package com.example.stiantornholmgrimsgaard.mappe2_s305537.Broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMS;

import java.util.Calendar;

/**
 * Created by stiantornholmgrimsgaard on 06.10.2017.
 */

public class SetPeriodicSMSService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "SetPeriodicSMSService making a toast", Toast.LENGTH_SHORT).show();

        Intent i;
        DBHandler dbHandler = new DBHandler(this);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        for (SMS sms : dbHandler.getSMS()) {
            if (!sms.isSent()) {
                int _id = (int) System.currentTimeMillis();
                i = new Intent(this, SMSService.class);
                i.putExtra("smsID", sms.getId());
                PendingIntent pendingIntent = PendingIntent.getService(this, _id, i, 0);
                alarm.set(AlarmManager.RTC_WAKEUP, sms.getDate(), pendingIntent);
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }
}
