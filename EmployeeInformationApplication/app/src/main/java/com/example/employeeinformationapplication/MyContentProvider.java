package com.example.employeeinformationapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "EmployeeDatabase";
    static final String TABLE_NAME = "Employee";
    static final int DATABASE_VERSION = 1;
    static final String PROVIDER_NAME = "com.example.employeeinformationapplication.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/employee";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String empNo = "empNo";
    static final String empName = "empName";
    static final String designation = "designation";
    static final String salary = "salary";
    static final int uriCode = 1;
    //sql query to create employee table
    static UriMatcher uriMatcher;
//    private static HashMap<String, String> values;
    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(empNo INTEGER PRIMARY KEY, "
            + " empName TEXT NOT NULL, "
            + " designation TEXT NOT NULL, "
            + " salary INTEGER NOT NULL);";
    public static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    static {
        //to match the uri everytime user access table under content provider
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //to access the whole table
        uriMatcher.addURI(PROVIDER_NAME, "employee", uriCode);
        // to access a particular row
        // of the table
        //uriMatcher.addURI(PROVIDER_NAME, "users/*", uriCode);
    }
    public MyContentProvider() {
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)) {
            case uriCode: return "com.example.employeeinformationapplication.provider/employee";
            default: throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.e("MyContentProvider", "Database not found", e);
        }

        return db != null;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return db.query(TABLE_NAME, strings, s, strings1, null, null, s1);
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowId = db.insert(TABLE_NAME, "", contentValues);
        try {
            if (rowId > 0) {
                Uri uri1 = ContentUris.withAppendedId(CONTENT_URI, rowId);
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(uri1, null);
                } else {
                    Log.w("MyContentProvider", "getContext() is null");
                }
                return uri1;
            }
        } catch (NullPointerException e) {
            Log.e("MyContentProvider", "Null pointer exception", e);
        }
        throw new SQLiteException("Record failed to add" + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return db.delete(TABLE_NAME, s, strings);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        // Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
