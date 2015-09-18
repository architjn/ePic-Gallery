package com.architjn.epic.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLCacheHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CacheDB";
    private Context context;

    public MySQLCacheHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYLIST_TABLE = "CREATE TABLE imgCache ( " +
                "imgCache_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "imgCache_original TEXT," +
                "imgCache_new TEXT)";
        db.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS imgCache");

        this.onCreate(db);
    }

    private static final String TABLE_CACHE = "imgCache";

    private static final String CACHE_KEY_ID = "imgCache_id";
    private static final String CACHE_KEY_OLD = "imgCache_original";
    private static final String CACHE_KEY_NEW = "imgCache_new";

    public void addCacheImage(String old, String newPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(CACHE_KEY_ID);
        values.put(CACHE_KEY_OLD, old);
        values.put(CACHE_KEY_NEW, newPath);
        db.insert(TABLE_CACHE, null, values);
        db.close();
    }

    public void removeAImg(String old) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CACHE + " WHERE " + CACHE_KEY_OLD + "='" + old + "'");
    }

    public String checkIfAlready(String old) {
        String query = "SELECT  * FROM " + TABLE_CACHE + " WHERE " + CACHE_KEY_OLD + "='" + old + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                return cursor.getString(2);
            } while (cursor.moveToNext());
        }
        return null;
    }


}