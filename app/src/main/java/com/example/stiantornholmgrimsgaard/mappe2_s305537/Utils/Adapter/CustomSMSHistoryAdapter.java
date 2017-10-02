package com.example.stiantornholmgrimsgaard.mappe2_s305537.Utils.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMS;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stiantornholmgrimsgaard on 27.09.2017.
 */

public class CustomSMSHistoryAdapter extends ArrayAdapter<SMS> {

    public CustomSMSHistoryAdapter(@NonNull Context context, SMS[] sms) {
        super(context, R.layout.custom_students_listview_row, sms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_sms_history_listview_row, parent, false);

        Long date = getItem(position).getDate();
        TextView smsListDate = customView.findViewById(R.id.sms_list_date);

        String message = getItem(position).getMessage();
        TextView smsListMessage = customView.findViewById(R.id.sms_list_message);

        ImageView smsListImage = customView.findViewById(R.id.sms_list_image);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date resultDate = new Date(date);

        smsListDate.setText(sdf.format(resultDate));
        smsListMessage.setText(message);
        smsListImage.setImageResource(R.drawable.ic_sms);

        return customView;
    }
}
