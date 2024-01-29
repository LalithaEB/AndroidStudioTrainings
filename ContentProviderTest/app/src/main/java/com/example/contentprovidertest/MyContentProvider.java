package com.example.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {

    // creating object of database
    // to perform query
    private SQLiteDatabase db;

    // declaring name of the database
    static final String DATABASE_NAME = "ElektrbitUserDB";

    // declaring table name of the database
    static final String TABLE_NAME = "Users";

    // declaring version of the database
    static final int DATABASE_VERSION = 1;

    static final String PROVIDER_NAME = "com.example.contentprovidertest.provider";

    static final String URL = "content://" + PROVIDER_NAME + "/users";

    // parsing the content URI
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String id = "id";
    static final String name = "name";

    static final String dept = "dept";
    static final int uriCode = 1;
    static UriMatcher uriMatcher;
//    private static HashMap<String, String> values;

    // sql query to create the table

    static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME
            + " (id TEXT PRIMARY KEY , "
            + " dept TEXT NOT NULL, "
            + " name TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    static {

        // to match the content URI
        // every time user access table under content provider
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // to access whole table
        uriMatcher.addURI(PROVIDER_NAME, "users", uriCode);

        // to access a particular row
        // of the table
        //uriMatcher.addURI(PROVIDER_NAME, "users/*", uriCode);
    }

    public MyContentProvider() {
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "com.example.contentprovidertest.provider/users";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("LALITHA", "inside insert()");
        long rowId = db.insert(TABLE_NAME, "", values);
        Log.d("LALITHA", "rowId" + rowId);
        if (rowId > 0) {
            Log.d("LALITHA", "inside if");
            Uri uri1 = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uri1, null);
            Log.d("LALITHA", "uri1" + uri1);
            return uri1;
        }
        throw new SQLiteException("Record failed to add" + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null,
                null, sortOrder);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(TABLE_NAME, selection, selectionArgs);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}