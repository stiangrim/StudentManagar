package com.example.stiantornholmgrimsgaard.mappe2_s305537.Broadcast;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.Student;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Preferences.PreferencesState;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by stiantornholmgrimsgaard on 06.10.2017.
 */

public class SMSService extends Service {
    public static final String SENT = "SMS_IS_SENT";
    public static final String DELIVERED = "SMS_DELIVERED";
    private PendingIntent sentPendingIntent;
    private PendingIntent deliveredPendingIntent;
    private DBHandler dbHandler;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I SMSService", Toast.LENGTH_LONG).show();

        if (PreferencesState.isSMSBroadcastEnabled(this)) {
            dbHandler = new DBHandler(this);
            checkDatabaseForSMS();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    void checkDatabaseForSMS() {
        for (SMS sms : dbHandler.getSMS()) {
            if (!sms.isSent() && sms.getDate() <= System.currentTimeMillis()) {
                sendSMS(this, sms);
                dbHandler.setSMSToSent(sms);
            }
        }
    }

    void sendSMS(Context context, SMS sms) {
        SmsManager smsManager = SmsManager.getDefault();
        sentPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
        deliveredPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);

        sendSMSToAllStudents(smsManager, sms.getMessage());
    }

    private void sendSMSToAllStudents(SmsManager smsManager, String message) {
        ArrayList<Student> students = dbHandler.getStudents();

        for (Student student : students) {
            smsManager.sendTextMessage(student.getPhoneNumber(), null, message, sentPendingIntent, deliveredPendingIntent);
        }
    }
}
