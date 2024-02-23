package com.example.notesapplication;

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
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    static final String DATABASE_NAME = "NotesDB";
    static final String TABLE_NAME = "Notes";
    static final int DATABASE_VERSION = 1;
    static final String PROVIDER_NAME = "com.example.notesapplication.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/notes";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String id = "id";
    static final String title = "title";
    static final String description = "description";
    static final int uriCode = 1;
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "title TEXT, "
            + "description TEXT);";
    static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "notes", uriCode);
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
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case uriCode: cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            break;
            default: throw new IllegalArgumentException("Unsupported uri" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "com.example.notesapplication.provider/notes";
            default:
                throw new IllegalArgumentException("Unsupported URI" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d("TAG", "inside insert()");

        long rowId;
        switch (uriMatcher.match(uri)) {
            case uriCode: rowId = db.insert(TABLE_NAME, "", values);
            break;
            default: throw new IllegalArgumentException("Unsupported Uri" + uri);
        }
//        long rowId = db.insert(TABLE_NAME, "", values);
//        if (uri.equals(CONTENT_URI)) {
//            rowId = db.insert(TABLE_NAME, "", values);
//        } else {
//            throw new IllegalArgumentException("Unsupported Uri" + uri);
//        }
        Log.d("TAG", "rowId" + rowId);

        if (rowId > 0) {
            Uri uriInsert = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(uriInsert, null);
            return uriInsert;
        }
        throw new SQLiteException("Record failed to insert" + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = db.delete(TABLE_NAME, selection, selectionArgs);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
//        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
//        db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.update(TABLE_NAME, values, id + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default: throw new IllegalArgumentException("Unsupported uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

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
        }
    }
}
