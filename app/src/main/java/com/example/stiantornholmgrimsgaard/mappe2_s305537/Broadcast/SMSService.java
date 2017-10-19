package com.example.stiantornholmgrimsgaard.mappe2_s305537.Broadcast;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.Student;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMS;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.WeeklySMSHandler;

import java.util.ArrayList;

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
        dbHandler = new DBHandler(this);
        checkDatabase(this);

        return super.onStartCommand(intent, flags, startId);
    }

    private void checkDatabase(Context context) {
        for (SMS sms : dbHandler.getSMS()) {
            if (!sms.isSent() && sms.getDate() <= System.currentTimeMillis()) {
                sendSMS(context, sms);
            }
        }
    }

    private void sendSMS(Context context, SMS sms) {
        SmsManager smsManager = SmsManager.getDefault();
        sentPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
        deliveredPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);

        sendSMSToAllStudents(smsManager, sms.getMessage());
        dbHandler.setSMSToSent(sms);

        if (sms.isWeekly()) {
            WeeklySMSHandler weeklySMSHandler = new WeeklySMSHandler();
            weeklySMSHandler.addNextSMS(context, sms);
        }
    }

    private void sendSMSToAllStudents(SmsManager smsManager, String message) {
        ArrayList<Student> students = dbHandler.getStudents();

        for (Student student : students) {
            smsManager.sendTextMessage(student.getPhoneNumber(), null, message, sentPendingIntent, deliveredPendingIntent);
        }
    }
}
