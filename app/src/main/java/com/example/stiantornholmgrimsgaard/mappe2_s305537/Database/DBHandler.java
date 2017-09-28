package com.example.stiantornholmgrimsgaard.mappe2_s305537.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.Student;

import java.util.ArrayList;

/**
 * Created by stiantornholmgrimsgaard on 25.09.2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = "DBHandler";

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "studentmanager.db";

    private static final String STUDENT_TABLE_NAME = "Student";
    private static final String STUDENT_ID = "_id";
    private static final String STUDENT_FIRST_NAME = "firstName";
    private static final String STUDENT_LAST_NAME = "lastName";
    private static final String STUDENT_PHONE_NUMBER = "phoneNumber";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + STUDENT_TABLE_NAME + "(" +
                STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                STUDENT_FIRST_NAME + " TEXT," +
                STUDENT_LAST_NAME + " TEXT," +
                STUDENT_PHONE_NUMBER + " TEXT" +
                ");";

        Log.d(TAG, sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME);
        onCreate(db);
    }

    public void addStudent(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_FIRST_NAME, student.getFirstName());
        contentValues.put(STUDENT_LAST_NAME, student.getLastName());
        contentValues.put(STUDENT_PHONE_NUMBER, student.getPhoneNumber());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(STUDENT_TABLE_NAME, null, contentValues);
        db.close();
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + STUDENT_TABLE_NAME + ";";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getLong(0));
                student.setFirstName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setPhoneNumber(cursor.getString(3));
                students.add(student);
            }
            while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return students;
    }
}
