package com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Broadcast.SMSService;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SendSMSActivity extends AppCompatActivity {

    private static final String TAG = "SendSMSActivity";
    private static final int ACTIVITY_NUM = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 2;
    private static final int MY_PERMISSIONS_RECEIVE_BOOT_COMPLETED = 3;

    private static long time = 0L;

    BroadcastReceiver smsSentReceiver;
    BroadcastReceiver smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        Log.d(TAG, "onCreate: starting");

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(this, SendSMSActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    public void showDateAndTimePickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    public void sendSMS(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, MY_PERMISSIONS_RECEIVE_BOOT_COMPLETED);
        } else {
            String message = ((EditText) findViewById(R.id.sms_content_edit_text)).getText().toString();

            if (!message.isEmpty()) {
                if (time != 0L) {
                    SMS sms = new SMS(time, message, false, false);
                    startSMSService(sms);

                    Intent smsHistoryIntent = new Intent(SendSMSActivity.this, SMSHistoryActivity.class);
                    startActivity(smsHistoryIntent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.you_need_to_set_the_time, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.your_message_cant_not_be_empty, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startSMSService(SMS sms) {
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final int _id = (int) System.currentTimeMillis();
        Intent intent = new Intent(this, SMSService.class);
        intent.putExtra("smsID", saveSMSInDatabase(sms));
        PendingIntent pendingIntent = PendingIntent.getService(this, _id, intent, 0);
        alarm.set(AlarmManager.RTC_WAKEUP, sms.getDate(), pendingIntent);
    }

    private long saveSMSInDatabase(SMS sms) {
        DBHandler dbHandler = new DBHandler(this);
        return dbHandler.addSMS(sms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS(findViewById(R.id.send_sms_button));
                }
            }
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS(findViewById(R.id.send_sms_button));
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsDeliveredReceiver);
        unregisterReceiver(smsSentReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(SendSMSActivity.this, R.string.sms_sent, Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(SendSMSActivity.this, R.string.generic_failure, Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(SendSMSActivity.this, R.string.no_service, Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(SendSMSActivity.this, R.string.null_pdu, Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(SendSMSActivity.this, R.string.radio_off, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(SendSMSActivity.this, R.string.sms_delivered, Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(SendSMSActivity.this, R.string.sms_not_delivered, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        registerReceiver(smsSentReceiver, new IntentFilter(SMSService.SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(SMSService.DELIVERED));
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Bundle args = new Bundle();
            args.putInt("year", year);
            // DatePickerDialog's Month index starts at 0.
            args.putInt("month", month + 1);
            args.putInt("day", day);

            DialogFragment dialogFragment = new TimePickerFragment();
            dialogFragment.setArguments(args);
            dialogFragment.show(getFragmentManager(), "timePicker");
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        int year;
        int month;
        int day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get Date from DatePickerDialog
            this.year = getArguments().getInt("year");
            this.month = getArguments().getInt("month");
            this.day = getArguments().getInt("day");

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");
            SimpleDateFormat sdfButton = new SimpleDateFormat("dd-MM-YY HH:mm");

            try {
                String dateString = getDateString(hourOfDay, minute);
                Date date = sdf.parse(dateString);
                time = date.getTime();

                Button sendSmsButton = getActivity().findViewById(R.id.send_sms_button);
                sendSmsButton.setAlpha(1);
                sendSmsButton.setEnabled(true);

                Date buttonDate = new Date(time);
                Button setDateAndTimeButton = getActivity().findViewById(R.id.set_date_and_time_button);
                //TODO: Doesn't work when only selected hour is bigger
                if (time <= System.currentTimeMillis()) {
                    setDateAndTimeButton.setText(R.string.time_now);
                } else {
                    setDateAndTimeButton.setText(sdfButton.format(buttonDate));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        private String getDateString(int hour, int minute) {
            StringBuilder dateString = new StringBuilder();

            String dayString = Integer.toString(day);
            String monthString = Integer.toString(month);
            String yearString = Integer.toString(year);
            String hourString = Integer.toString(hour);
            String minuteString = Integer.toString(minute);

            if (dayString.length() < 2) {
                dayString = "0" + dayString;
            }
            if (monthString.length() < 2) {
                monthString = "0" + monthString;
            }
            if (hourString.length() < 2) {
                hourString = "0" + hourString;
            }
            if (minuteString.length() < 2) {
                minuteString = "0" + minuteString;
            }

            dateString.append(dayString).append(monthString).append(yearString).append(hourString).append(minuteString);
            return dateString.toString();
        }


    }
}
