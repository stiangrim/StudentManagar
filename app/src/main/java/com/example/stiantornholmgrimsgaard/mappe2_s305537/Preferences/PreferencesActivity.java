package com.example.stiantornholmgrimsgaard.mappe2_s305537.Preferences;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMS;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMSHistoryActivity;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.WeeklySMSHandler;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.ViewHelper.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Calendar;

public class PreferencesActivity extends AppCompatActivity {

    private static final String TAG = "PreferencesActivity";
    private static final int ACTIVITY_NUM = 3;

    private Spinner dayOfWeekSpinner;
    private Button timePickerButton;
    private EditText weeklySMSContentEditText;
    private Button saveWeeklySMSButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Log.d(TAG, "onCreate: starting");

        dayOfWeekSpinner = (Spinner) findViewById(R.id.day_of_week_spinner);
        timePickerButton = (Button) findViewById(R.id.time_picker_button);
        weeklySMSContentEditText = (EditText) findViewById(R.id.weekly_sms_content_edit_text);
        saveWeeklySMSButton = (Button) findViewById(R.id.save_weekly_sms_button);

        setWeeklySMSSwitch();
        setDayOfWeekSpinner();
        setWeeklySMSContent();
        setupBottomNavigationView();

        if (savedInstanceState != null) {
            String time = savedInstanceState.getString("time");
            timePickerButton.setText(time);
        } else {
            timePickerButton.setText(PreferencesState.getWeeklySMSHour(this) + ":" + PreferencesState.getWeeklySMSMinute(this));
        }
    }

    public void openTimePickerDialog(View view) {
        DialogFragment dialogFragment = new PreferencesActivity.TimePickerFragment();
        dialogFragment.show(getFragmentManager(), "timePicker");
    }

    private void setWeeklySMSSwitch() {
        Switch weeklySMSSwitch = (Switch) findViewById(R.id.weekly_sms_switch);

        boolean weeklySMSEnabled = PreferencesState.isWeeklySMSEnabled(this);
        if (weeklySMSEnabled) {
            enableWeeklySMSWidgets();
            setSaveButtonIfNoWeeklySMSExists();
        } else {
            disableWeeklySMSWidgets();
        }

        final Context context = this;
        weeklySMSSwitch.setChecked(weeklySMSEnabled);
        weeklySMSSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                PreferencesState.setWeeklySMSEnabled(PreferencesActivity.this, checked);
                if (checked) {
                    enableWeeklySMSWidgets();
                    setSaveButtonIfNoWeeklySMSExists();
                } else {
                    disableWeeklySMSWidgets();
                    DBHandler dbHandler = new DBHandler(context);
                    dbHandler.deletePendingWeeklySMS();
                }
            }
        });
    }

    private void setSaveButtonIfNoWeeklySMSExists() {
        DBHandler dbHandler = new DBHandler(this);
        boolean dbHasPendingSMS = false;
        for (SMS sms : dbHandler.getSMS()) {
            if (sms.isWeekly() && !sms.isSent()) {
                dbHasPendingSMS = true;
            }
        }
        if (!dbHasPendingSMS) {
            saveWeeklySMSButton.setEnabled(true);
            saveWeeklySMSButton.setAlpha(1);
            saveWeeklySMSButton.setText(getString(R.string.save_weekly_sms));
        }
    }

    private void setWeeklySMSContent() {
        String weeklySMSContent = PreferencesState.getWeeklySMSMessage(this);
        weeklySMSContentEditText.setText(weeklySMSContent);
        weeklySMSContentEditText.setSelection(weeklySMSContent.length());
        final Context context = this;
        weeklySMSContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!weeklySMSContentEditText.getText().toString().equals(PreferencesState.getWeeklySMSMessage(context))) {
                    saveWeeklySMSButton.setEnabled(true);
                    saveWeeklySMSButton.setAlpha(1);
                    saveWeeklySMSButton.setText(getString(R.string.save_weekly_sms));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void saveWeeklySMS(View view) {
        String day = dayOfWeekSpinner.getSelectedItem().toString();
        String hour = timePickerButton.getText().toString().substring(0, 2);
        String minute = timePickerButton.getText().toString().substring(3, 5);
        String message = weeklySMSContentEditText.getText().toString();

        if (hour.equals(getString(R.string.select_time))) {
            Toast.makeText(this, R.string.please_set_a_time, Toast.LENGTH_SHORT).show();
        } else if (message.isEmpty()) {
            Toast.makeText(this, R.string.message_can_not_be_empty, Toast.LENGTH_SHORT).show();
        } else {
            PreferencesState.setWeeklySMSDayPosition(this, dayOfWeekSpinner.getSelectedItemPosition());
            PreferencesState.setWeeklySMSDay(this, day);
            PreferencesState.setWeeklySMSHour(this, hour);
            PreferencesState.setWeeklySMSMessage(this, message);

            WeeklySMSHandler weeklySMSHandler = new WeeklySMSHandler();
            weeklySMSHandler.setNextWeeklySMS(this, day, hour, minute, message);

            saveWeeklySMSButton.setEnabled(false);
            saveWeeklySMSButton.setAlpha(0.5f);
            saveWeeklySMSButton.setText(getString(R.string.saved));

            Intent intent = new Intent(PreferencesActivity.this, SMSHistoryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void enableWeeklySMSWidgets() {
        dayOfWeekSpinner.setEnabled(true);
        dayOfWeekSpinner.setAlpha(1);

        timePickerButton.setEnabled(true);
        timePickerButton.setAlpha(1);

        weeklySMSContentEditText.setEnabled(true);
        weeklySMSContentEditText.setAlpha(1);

        if (PreferencesState.getWeeklySMSMessage(this).equals("")) {
            saveWeeklySMSButton.setEnabled(true);
        } else {
            saveWeeklySMSButton.setEnabled(false);
            saveWeeklySMSButton.setAlpha(0.5f);
        }
    }

    private void disableWeeklySMSWidgets() {
        dayOfWeekSpinner.setEnabled(false);
        dayOfWeekSpinner.setAlpha(0.4f);

        timePickerButton.setEnabled(false);
        timePickerButton.setAlpha(0.4f);

        weeklySMSContentEditText.setEnabled(false);
        weeklySMSContentEditText.setAlpha(0.4f);

        saveWeeklySMSButton.setEnabled(false);
        saveWeeklySMSButton.setAlpha(0.4f);
    }

    private void setDayOfWeekSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days_of_week_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeekSpinner.setAdapter(adapter);
        dayOfWeekSpinner.setSelection(PreferencesState.getWeeklySMSDayPosition(this));
        final Context context = this;
        dayOfWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != PreferencesState.getWeeklySMSDayPosition(context)) {
                    saveWeeklySMSButton.setEnabled(true);
                    saveWeeklySMSButton.setAlpha(1);
                    saveWeeklySMSButton.setText(getString(R.string.save_weekly_sms));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavigationViewHelper.setupBottomNavigationView(this, PreferencesActivity.this, bottomNavigationViewEx, ACTIVITY_NUM);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String time = timePickerButton.getText().toString();
        outState.putString("time", time);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
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
            String timeString = getTimeString(hourOfDay, minute);
            Button timePickerButton = getActivity().findViewById(R.id.time_picker_button);
            timePickerButton.setText(timeString);

            if (!timeString.equals(PreferencesState.getWeeklySMSHour(getContext()))) {
                Button saveWeeklySMSButton = getActivity().findViewById(R.id.save_weekly_sms_button);
                saveWeeklySMSButton.setEnabled(true);
                saveWeeklySMSButton.setAlpha(1);
                saveWeeklySMSButton.setText(getActivity().getString(R.string.save_weekly_sms));
            }

            PreferencesState.setWeeklySMSHour(getContext(), timeString.substring(0, 2));
            PreferencesState.setWeeklySMSMinute(getContext(), timeString.substring(3, 5));
        }

        private String getTimeString(int hour, int minute) {
            StringBuilder timeString = new StringBuilder();

            String hourString = Integer.toString(hour);
            String minuteString = Integer.toString(minute);

            if (hourString.length() < 2) {
                hourString = "0" + hourString;
            }
            if (minuteString.length() < 2) {
                minuteString = "0" + minuteString;
            }

            timeString.append(hourString).append(":").append(minuteString);
            return timeString.toString();
        }
    }
}
