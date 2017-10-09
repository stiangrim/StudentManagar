package com.example.stiantornholmgrimsgaard.mappe2_s305537.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.stiantornholmgrimsgaard.mappe2_s305537.Database.DBHandler;

/**
 * Created by stiantornholmgrimsgaard on 09.10.2017.
 */

public class StudentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.stiantornholmgrimsgaard.mappe2_s305537.ContentProvider.StudentProvider";
    static final int STUDENT = 1;
    static final int MSTUDENT = 2;
    static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/studentManager");
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "studentManager", MSTUDENT);
        uriMatcher.addURI(PROVIDER_NAME, "studentManager/#", STUDENT);
    }

    private DBHandler dbHandler;
    private SQLiteDatabase sqlDB;

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext());
        sqlDB = dbHandler.getWritableDatabase();
        return sqlDB != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cur;
        if (uriMatcher.match(uri) == STUDENT) {
            cur = sqlDB.query(DBHandler.STUDENT_TABLE_NAME, projection, DBHandler.STUDENT_ID + "=" + uri.getPathSegments().get(1), selectionArgs, null, null, sortOrder);
            return cur;
        } else {
            cur = sqlDB.query(DBHandler.STUDENT_TABLE_NAME, null, null, null, null, null, null);
            return cur;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MSTUDENT:
                return "vnd.android.cursor.dir/studentManager";
            case STUDENT:
                return "vnd.android.cursor.item/studentManager";
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.insert(DBHandler.STUDENT_TABLE_NAME, null, contentValues);
        Cursor c = db.query(DBHandler.STUDENT_TABLE_NAME, null, null, null, null, null, null);
        c.moveToLast();
        long myId = c.getLong(0);
        c.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, myId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (uriMatcher.match(uri) == STUDENT) {
            sqlDB.delete(DBHandler.STUDENT_TABLE_NAME, DBHandler.STUDENT_ID + " = " + uri.getPathSegments().get(1), selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if (uriMatcher.match(uri) == MSTUDENT) {
            sqlDB.delete(DBHandler.STUDENT_TABLE_NAME, null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (uriMatcher.match(uri) == STUDENT) {
            sqlDB.update(DBHandler.STUDENT_TABLE_NAME, contentValues, DBHandler.STUDENT_ID + " = " + uri.getPathSegments().get(1), null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if (uriMatcher.match(uri) == MSTUDENT) {
            sqlDB.update(DBHandler.STUDENT_TABLE_NAME, null, null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }
        return 0;
    }
}
