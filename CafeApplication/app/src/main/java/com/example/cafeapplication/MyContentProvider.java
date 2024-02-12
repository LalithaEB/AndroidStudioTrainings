package com.example.cafeapplication;

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

public class MyContentProvider extends ContentProvider {
    static final String DATABASE_NAME = "CafeDB";
    static final String TABLE_NAME = "Staff";
    static final String TABLE_NAME_ORDERS = "Orders";
    static final int DATABASE_VERSION = 1;
    static final String PROVIDER_NAME = "com.example.cafeapplication.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/staff";
    static final String URL_ORDERS = "content://" + PROVIDER_NAME + "/orders";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final Uri CONTENT_URI_ORDERS = Uri.parse(URL_ORDERS);
    static final String userName = "userName";
    static final String password = "password";
    static final String orders = "orders";
    static final int uriCode = 1;
    static final int uriCodeOrder = 2;
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( userName TEXT PRIMARY KEY, "
            + " password TEXT NOT NULL);";
    static final String CREATE_DB_TABLE_ORDERS = "CREATE TABLE " + TABLE_NAME_ORDERS
            + "( orderId INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " orders TEXT);";
    static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "staff", uriCode);
        uriMatcher.addURI(PROVIDER_NAME, "orders", uriCodeOrder);
    }

    private SQLiteDatabase db;

    public MyContentProvider() {
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

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case uriCode: cursor = db.query(TABLE_NAME, strings, s, strings1, null, null, s1);
            break;
            case uriCodeOrder: cursor = db.query(TABLE_NAME_ORDERS, strings, s, strings1, null, null, s1);
            break;
            default: throw new IllegalArgumentException("Unsupported Uri" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode: return "com.example.cafeapplication.provider/staff";
            case uriCodeOrder: return "com.example.cafeapplication.provider/orders";
            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d("Lalitha", "inside insert()");

        long rowId;
        if(uri.equals(CONTENT_URI)) {
            rowId = db.insert(TABLE_NAME, "", contentValues);
        } else if(uri.equals(CONTENT_URI_ORDERS)) {
            rowId = db.insert(TABLE_NAME_ORDERS, "", contentValues);
        } else {
            throw new IllegalArgumentException("Unknown uri" + uri);
        }
        Log.d("LALITHA", "rowId" + rowId);

        if(rowId > 0) {
            Uri uriInsert = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(uriInsert, null);
            return uriInsert;
        }
        throw new SQLiteException("Record failed to insert" + uri);
        
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int count = 0;

        if(uri.equals(CONTENT_URI)) {
            count = db.delete(TABLE_NAME, s, strings);
        } else if(uri.equals(CONTENT_URI_ORDERS)) {
            count = db.delete(TABLE_NAME_ORDERS, s, strings);
        }

        if(count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

//        int count =db.delete(TABLE_NAME, s, strings);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
            db.execSQL(CREATE_DB_TABLE_ORDERS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ORDERS);
            onCreate(db);
        }
    }
}
