package com.example.stiantornholmgrimsgaard.mappe2_s305537.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.Student;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMS;

import java.util.ArrayList;

/**
 * Created by stiantornholmgrimsgaard on 25.09.2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "studentmanager.db";
    public static final String STUDENT_TABLE_NAME = "Student";
    public static final String STUDENT_ID = "_id";
    public static final String STUDENT_FIRST_NAME = "firstName";
    public static final String STUDENT_LAST_NAME = "lastName";
    public static final String STUDENT_PHONE_NUMBER = "phoneNumber";
    public static final String SMS_TABLE_NAME = "Sms";
    public static final String SMS_ID = "_id";
    public static final String SMS_DATE = "date";
    public static final String SMS_MESSAGE = "message";
    public static final String SMS_IS_SENT = "is_sent";
    public static final String SMS_IS_WEEKLY = "is_weekly";
    public static final String CREATE_STUDENT_TABLE = "CREATE TABLE " + STUDENT_TABLE_NAME + "(" +
            STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            STUDENT_FIRST_NAME + " TEXT," +
            STUDENT_LAST_NAME + " TEXT," +
            STUDENT_PHONE_NUMBER + " TEXT" +
            ");";
    public static final String CREATE_SMS_TABLE = "CREATE TABLE " + SMS_TABLE_NAME + "(" +
            SMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SMS_DATE + " INTEGER," +
            SMS_MESSAGE + " TEXT," +
            SMS_IS_SENT + " INTEGER," +
            SMS_IS_WEEKLY + " INTEGER" +
            ");";
    private static final String TAG = "DBHandler";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);

        Log.d(TAG, CREATE_SMS_TABLE);
        db.execSQL(CREATE_SMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SMS_TABLE_NAME);
        onCreate(db);
    }

    public void addSMS(SMS sms) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SMS_DATE, sms.getDate());
        contentValues.put(SMS_MESSAGE, sms.getMessage());
        contentValues.put(SMS_IS_SENT, sms.isSent());
        contentValues.put(SMS_IS_WEEKLY, sms.isWeekly());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(SMS_TABLE_NAME, null, contentValues);
        db.close();
    }

    public ArrayList<SMS> getSMS() {
        ArrayList<SMS> smsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + SMS_TABLE_NAME + ";";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                SMS sms = new SMS();
                sms.setId(cursor.getLong(0));
                sms.setDate(cursor.getLong(1));
                sms.setMessage(cursor.getString(2));
                if (cursor.getInt(3) == 0) {
                    sms.setSent(false);
                } else if (cursor.getInt(3) == 1) {
                    sms.setSent(true);
                }
                if (cursor.getInt(4) == 0) {
                    sms.setWeekly(false);
                } else if (cursor.getInt(4) == 1) {
                    sms.setWeekly(true);
                }
                smsArrayList.add(sms);
            }
            while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return smsArrayList;
    }

    public SMS getSMS(Long id) {
        SMS sms = new SMS();

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + SMS_TABLE_NAME + " WHERE " + SMS_ID + " = " + id + ";";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                sms.setId(cursor.getLong(0));
                sms.setDate(cursor.getLong(1));
                sms.setMessage(cursor.getString(2));
                if (cursor.getInt(3) == 0) {
                    sms.setSent(false);
                } else if (cursor.getInt(3) == 1) {
                    sms.setSent(true);
                }
                if (cursor.getInt(4) == 0) {
                    sms.setWeekly(false);
                } else if (cursor.getInt(4) == 1) {
                    sms.setWeekly(true);
                }
            }
            while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return sms;
    }

    public void setSMSToSent(SMS sms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SMS_MESSAGE, sms.getMessage());
        contentValues.put(SMS_DATE, sms.getDate());
        contentValues.put(SMS_IS_SENT, 1);
        contentValues.put(SMS_IS_WEEKLY, sms.isWeekly());

        db.update(SMS_TABLE_NAME, contentValues, SMS_ID + " =? ", new String[]{String.valueOf(sms.getId())});
        db.close();
    }

    public void updateSMS(SMS sms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SMS_DATE, sms.getDate());
        contentValues.put(SMS_MESSAGE, sms.getMessage());
        contentValues.put(SMS_IS_SENT, sms.isSent());
        contentValues.put(SMS_IS_WEEKLY, sms.isWeekly());

        db.update(SMS_TABLE_NAME, contentValues, SMS_ID + " =? ", new String[]{String.valueOf(sms.getId())});
        db.close();
    }

    public void deleteSMS(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SMS_TABLE_NAME, SMS_ID + " =? ", new String[]{Long.toString(id)});
        db.close();
    }

    public void deletePendingWeeklySMS() {
        SQLiteDatabase db = this.getWritableDatabase();

        int smsIsWeekly = 1;
        int smsIsSent = 0;

        db.delete(SMS_TABLE_NAME, SMS_IS_WEEKLY + " =? AND " + SMS_IS_SENT + "=?", new String[]{Integer.toString(smsIsWeekly), Integer.toString(smsIsSent)});
        db.close();
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

    public Student getStudent(Long id) {
        Student student = new Student();

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + STUDENT_TABLE_NAME + " WHERE " + STUDENT_ID + " = " + id + ";";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                student.setId(cursor.getLong(0));
                student.setFirstName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setPhoneNumber(cursor.getString(3));
            }
            while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return student;
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

    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_FIRST_NAME, student.getFirstName());
        contentValues.put(STUDENT_LAST_NAME, student.getLastName());
        contentValues.put(STUDENT_PHONE_NUMBER, student.getPhoneNumber());

        db.update(STUDENT_TABLE_NAME, contentValues, STUDENT_ID + " =? ", new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public void deleteStudent(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STUDENT_TABLE_NAME, STUDENT_ID + " =? ", new String[]{Long.toString(id)});
        db.close();
    }
}
