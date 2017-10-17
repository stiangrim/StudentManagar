package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.content.Context;
import android.content.Intent;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by stiantornholmgrimsgaard on 10.10.2017.
 */

public class WeeklySMSHandler {

    public void setNextWeeklySMS(Context context, String day, String hour, String minute, String message) {
        SMS newSMS = getNextWeeklySMS(context, day, hour, minute, message);
        DBHandler dbHandler = new DBHandler(context);

        boolean dbHasPendingSMS = false;
        for (SMS sms : dbHandler.getSMS()) {
            if (sms.isWeekly() && !sms.isSent()) {
                newSMS.setId(sms.getId());
                dbHandler.updateSMS(newSMS);
                dbHasPendingSMS = true;
                sendBroadcast(context, sms.getId());
            }
        }

        if (!dbHasPendingSMS) {
            sendBroadcast(context, saveSMSInDatabase(context, newSMS));
        }
    }

    public void addNextSMS(Context context, SMS lastWeeklySMS) {
        Calendar cDate = Calendar.getInstance();
        cDate.setTimeInMillis(lastWeeklySMS.getDate());
        cDate.add(Calendar.DATE, 7);

        SMS newSMS = new SMS(cDate.getTimeInMillis(), lastWeeklySMS.getMessage(), lastWeeklySMS.isSent(), lastWeeklySMS.isWeekly());
        sendBroadcast(context, saveSMSInDatabase(context, newSMS));
    }

    private void sendBroadcast(Context context, long smsID) {
        Intent intent = new Intent();
        intent.putExtra("smsID", smsID);
        intent.setAction("com.example.stiantornholmgrimsgaard.mappe2_s305537.SMSBroadcastReceiver");
        context.sendBroadcast(intent);
    }

    private long saveSMSInDatabase(Context context, SMS sms) {
        DBHandler dbHandler = new DBHandler(context);
        return dbHandler.addSMS(sms);
    }

    private SMS getNextWeeklySMS(Context context, String day, String hour, String minute, String message) {
        Calendar cDate = Calendar.getInstance();
        cDate.set(cDate.get(Calendar.YEAR), cDate.get(Calendar.MONTH), cDate.get(Calendar.DATE), Integer.parseInt(hour), Integer.parseInt(minute));
        cDate = getNextWeeklyDate(context, cDate, day, hour, minute);

        return new SMS(cDate.getTimeInMillis(), message, false, true);
    }

    private Calendar getNextWeeklyDate(Context context, Calendar cDate, String day, String hour, String minute) {
        String[] days = context.getResources().getStringArray(R.array.days_of_week_array);

        if (days[0].equals(day)) {
            checkTime(cDate, Calendar.MONDAY, hour, minute);
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[1].equals(day)) {
            checkTime(cDate, Calendar.TUESDAY, hour, minute);
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[2].equals(day)) {
            checkTime(cDate, Calendar.WEDNESDAY, hour, minute);
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[3].equals(day)) {
            checkTime(cDate, Calendar.THURSDAY, hour, minute);
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[4].equals(day)) {
            checkTime(cDate, Calendar.FRIDAY, hour, minute);
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[5].equals(day)) {
            checkTime(cDate, Calendar.SATURDAY, hour, minute);
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[6].equals(day)) {
            checkTime(cDate, Calendar.SUNDAY, hour, minute);
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        }

        return cDate;
    }

    private Calendar checkTime(Calendar cDate, int dayOfWeek, String hour, String minute) {
        Calendar currentDate = GregorianCalendar.getInstance();
        currentDate.setTime(new Date());

        Date dateTime = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
        timeFormat.format(dateTime);
        String time = hour + minute;

        if (cDate.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
            try {
                if (timeFormat.parse(timeFormat.format(dateTime)).after(timeFormat.parse(time))) {
                    cDate.add(Calendar.DATE, 7);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return cDate;
    }

}
