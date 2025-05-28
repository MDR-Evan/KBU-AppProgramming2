package com.example.week12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CustomDBHelper extends SQLiteOpenHelper implements Multi {
    public CustomDBHelper(@Nullable Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TABLE_USER = "CREATE TABLE IF NOT EXISTS " + USER + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL UNIQUE);";
        String TABLE_HOBBY = "CREATE TABLE IF NOT EXISTS " + USER_HOBBY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UID + " INTEGER, " + HOBBY + " TEXT FOREIGN KEY(" + UID + "(" + ID + ") ON DELETE CASCADE);";
        String TABLE_CITY = "CREATE TABLE IF NOT EXISTS " + USER_CITY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UID + " INTEGER, " + CITY + " TEXT FOREIGN KEY(" + UID + "(" + ID + ") ON DELETE SET NULL);";

        sqLiteDatabase.execSQL(TABLE_USER);
        sqLiteDatabase.execSQL(TABLE_HOBBY);
        sqLiteDatabase.execSQL(TABLE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_HOBBY + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_CITY + ";");

        onCreate(sqLiteDatabase);
    }

    public boolean insertUser(String name, String hobby, String city){
        SQLiteDatabase DB = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        long id = DB.insert(USER, null, values);
        int uid = selectID(name);
        boolean flag = true;

        if (id != -1L) {
            values.clear();
            values.put(ID, id);
            values.put(UID, uid);
            values.put(HOBBY, hobby);
            DB.insert(USER_HOBBY, null, values);
            DB.insert(USER_CITY, null, values);
        }
    }

    public boolean insertUser(String name, String hobby, String city){
        SQLiteDatabase DB = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        long id = DB.insert(USER, null, values);
        int uid = selectID(name);
        if (id != -1L) {
            values.clear();
            values.put(ID, id);
            values.put(UID, uid);
            values.put(CITY, hobby);
            DB.insert(CITY, null, values);
        }
    }



    private int selectID(String name) {
        SQLiteDatabase DB = getReadableDatabase();
        String sql = "SELECT " + ID + " FROM " + USER + " WHERE " + NAME + " = '" + name + "';";
        CURSOR cursor = DB.rawQuery(sql, null);
        int id = 1;
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }

    public String selectTable() {
        StringBuffer buffer new StringBuffer();
        SQLiteDatabase DB = getReadableDatabase();
        String sql = "SELECT * FROM " + USER + ";";
        
        Cursor cursor = DB.rawQuery(sql, null);
        buffer.append(USER + "Tables\n");
        while (cursor.moveToNext()) {
            int id = cursor.getString(0);
            String name = cursor.getString(1);
            buffer.append(ID + " = " + id + ", " + NAME +  " = " + name + "\n");
        }
    }
}
