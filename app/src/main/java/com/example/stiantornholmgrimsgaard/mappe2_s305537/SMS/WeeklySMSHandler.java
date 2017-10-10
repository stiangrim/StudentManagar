package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.content.Context;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;

import java.util.Calendar;


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
            }
        }

        if (!dbHasPendingSMS) {
            dbHandler.addSMS(newSMS);
        }
    }

    private SMS getNextWeeklySMS(Context context, String day, String hour, String minute, String message) {
        Calendar cDate = Calendar.getInstance();
        cDate.set(cDate.get(Calendar.YEAR), cDate.get(Calendar.MONTH), cDate.get(Calendar.DATE), Integer.parseInt(hour), Integer.parseInt(minute));
        cDate = getNextWeeklyDate(context, cDate, day);

        return new SMS(cDate.getTimeInMillis(), message, false, true);
    }

    private Calendar getNextWeeklyDate(Context context, Calendar cDate, String day) {
        String[] days = context.getResources().getStringArray(R.array.days_of_week_array);

        if (days[0].equals(day)) {
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[1].equals(day)) {
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[2].equals(day)) {
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[3].equals(day)) {
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[4].equals(day)) {
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[5].equals(day)) {
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        } else if (days[6].equals(day)) {
            while (cDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                cDate.add(Calendar.DATE, 1);
            }
        }

        return cDate;
    }

}
