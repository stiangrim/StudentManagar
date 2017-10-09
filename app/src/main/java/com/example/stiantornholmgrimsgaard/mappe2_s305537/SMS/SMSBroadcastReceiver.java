package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by stiantornholmgrimsgaard on 06.10.2017.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "SMSBroadcastReceiver making a toast", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(context, SetPeriodicSMSService.class);
        context.startService(i);
    }
}
