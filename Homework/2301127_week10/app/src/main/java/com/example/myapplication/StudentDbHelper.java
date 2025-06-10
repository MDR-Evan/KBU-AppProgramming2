package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME    = "student.db";
    private static final int    DATABASE_VERSION = 1;

    public static final String TABLE_STUDENT      = "student";
    public static final String COLUMN_ID          = "_id";
    public static final String COLUMN_NAME        = "name";
    public static final String COLUMN_AGE         = "age";
    public static final String COLUMN_ADDRESS     = "address";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_STUDENT + " (" +
                    COLUMN_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME    + " TEXT NOT NULL, " +
                    COLUMN_AGE     + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT" +
                    ");";

    private static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_STUDENT;

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }
}
