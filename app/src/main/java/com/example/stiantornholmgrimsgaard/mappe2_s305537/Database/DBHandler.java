package com.example.stiantornholmgrimsgaard.mappe2_s305537.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Party.Student;
import com.example.stiantornholmgrimsgaard.mappe2_s305537.SMS.SMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by stiantornholmgrimsgaard on 25.09.2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = "DBHandler";

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "studentmanager.db";

    private static final String STUDENT_TABLE_NAME = "Student";
    private static final String STUDENT_ID = "_id";
    private static final String STUDENT_FIRST_NAME = "firstName";
    private static final String STUDENT_LAST_NAME = "lastName";
    private static final String STUDENT_PHONE_NUMBER = "phoneNumber";

    private static final String SMS_TABLE_NAME = "Sms";
    private static final String SMS_ID = "_id";
    private static final String SMS_DATE = "date";
    private static final String SMS_MESSAGE = "message";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String studentSql = "CREATE TABLE " + STUDENT_TABLE_NAME + "(" +
                STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                STUDENT_FIRST_NAME + " TEXT," +
                STUDENT_LAST_NAME + " TEXT," +
                STUDENT_PHONE_NUMBER + " TEXT" +
                ");";

        Log.d(TAG, studentSql);
        db.execSQL(studentSql);

        String smsSql = "CREATE TABLE " + SMS_TABLE_NAME + "(" +
                SMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SMS_DATE + " INTEGER," +
                SMS_MESSAGE + " TEXT" +
                ");";

        Log.d(TAG, smsSql);
        db.execSQL(smsSql);


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
            }
            while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return sms;
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
