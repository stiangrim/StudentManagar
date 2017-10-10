package com.example.stiantornholmgrimsgaard.mappe2_s305537.Broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

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


        java.util.Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, SMSService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }
}
