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

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.Student;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.R;

/**
 * Created by stiantornholmgrimsgaard on 25.09.2017.
 */

public class CustomStudentAdapter extends ArrayAdapter<Student> {

    public CustomStudentAdapter(@NonNull Context context, Student[] students) {
        super(context, R.layout.custom_students_listview_row, students);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_students_listview_row, parent, false);

        String name = getItem(position).getFirstName() + " " + getItem(position).getLastName();
        TextView profileListName = customView.findViewById(R.id.profile_list_name);

        String number = getItem(position).getPhoneNumber();
        TextView profileListNumber = customView.findViewById(R.id.profile_list_number);

        ImageView profileListImage = customView.findViewById(R.id.profile_list_image);

        profileListName.setText(name);
        profileListNumber.setText(number);
        profileListImage.setImageResource(R.mipmap.ic_account_circle_black_24dp);

        return customView;
    }
}
