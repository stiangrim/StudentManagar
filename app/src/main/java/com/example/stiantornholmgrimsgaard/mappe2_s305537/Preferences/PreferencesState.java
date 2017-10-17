package com.example.stiantornholmgrimsgaard.mappe2_s305537.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stiantornholmgrimsgaard on 09.10.2017.
 */

public class PreferencesState {

    private static final String SHARED_PREFERENCES_NAME = "SMSPreferences";
    private static final String SMS_BROADCAST_ENABLED = "smsBroadcastEnabled";
    private static final String WEEKLY_SMS_ENABLED = "weeklySMSEnabled";
    private static final String WEEKLY_SMS_DAY = "weeklySMSDay";
    private static final String WEEKLY_SMS_DAY_POSITION = "weeklySMSDayPosition";
    private static final String WEEKLY_SMS_HOUR = "weeklySMSTime";
    private static final String WEEKLY_SMS_MINUTE = "weeklySMSMinute";
    private static final String WEEKLY_SMS_MESSAGE = "weeklySMSMessage";


    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    static void setSMSBroadcastEnabled(Context context, boolean input) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(SMS_BROADCAST_ENABLED, input);
        editor.apply();
    }

    public static boolean isSMSBroadcastEnabled(Context context) {
        return getPreferences(context).getBoolean(SMS_BROADCAST_ENABLED, true);
    }

    public static void setWeeklySMSEnabled(Context context, boolean input) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(WEEKLY_SMS_ENABLED, input);
        editor.apply();
    }

    static boolean isWeeklySMSEnabled(Context context) {
        return getPreferences(context).getBoolean(WEEKLY_SMS_ENABLED, true);
    }

    static void setWeeklySMSDay(Context context, String input) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(WEEKLY_SMS_DAY, input);
        editor.apply();
    }

    static void setWeeklySMSDayPosition(Context context, int input) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(WEEKLY_SMS_DAY_POSITION, input);
        editor.apply();
    }

    static int getWeeklySMSDayPosition(Context context) {
        return getPreferences(context).getInt(WEEKLY_SMS_DAY_POSITION, 0);
    }

    static void setWeeklySMSHour(Context context, String input) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(WEEKLY_SMS_HOUR, input);
        editor.apply();
    }

    static String getWeeklySMSHour(Context context) {
        return getPreferences(context).getString(WEEKLY_SMS_HOUR, "12");
    }

    static void setWeeklySMSMinute(Context context, String input) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(WEEKLY_SMS_MINUTE, input);
        editor.apply();
    }

    static String getWeeklySMSMinute(Context context) {
        return getPreferences(context).getString(WEEKLY_SMS_MINUTE, "00");
    }

    static void setWeeklySMSMessage(Context context, String input) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(WEEKLY_SMS_MESSAGE, input);
        editor.apply();
    }

    static String getWeeklySMSMessage(Context context) {
        return getPreferences(context).getString(WEEKLY_SMS_MESSAGE, "");
    }
}
