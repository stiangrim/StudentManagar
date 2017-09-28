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

/**
 * Created by stiantornholmgrimsgaard on 27.09.2017.
 */

public class CustomSMSHistoryAdapter extends ArrayAdapter<String> {

    public CustomSMSHistoryAdapter(@NonNull Context context, String[] students) {
        super(context, R.layout.custom_students_listview_row, students);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_sms_history_listview_row, parent, false);

        String date = getItem(position);
        TextView smsListDate = customView.findViewById(R.id.sms_list_date);

        String message = getItem(position);
        TextView smsListMessage = customView.findViewById(R.id.sms_list_message);

        ImageView smsListImage = customView.findViewById(R.id.sms_list_image);

        smsListDate.setText(date);
        smsListMessage.setText(message);
        smsListImage.setImageResource(R.drawable.ic_sms);

        return customView;
    }
}
