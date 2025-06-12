package com.example.afinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SawonDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME    = "sawon.db";
    private static final int    DB_VERSION = 1;

    public static final String TABLE_NAME  = "sawon";
    public static final String COL_ID      = "id";
    public static final String COL_NAME    = "name";
    public static final String COL_GENDER  = "gender";
    public static final String COL_SALARY  = "salary";
    public static final String COL_IMAGE   = "imageUrl";

    public SawonDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID     + " INTEGER PRIMARY KEY, "
                + COL_NAME   + " TEXT    NOT NULL, "
                + COL_GENDER + " TEXT    NOT NULL, "
                + COL_SALARY + " INTEGER NOT NULL, "
                + COL_IMAGE  + " TEXT"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // 버전 변경 시 단순 drop & recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * 사원 정보 삽입 (중복 ID는 replace)
     */
    public void insertSawon(Sawon s) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID,     s.getId());
        cv.put(COL_NAME,   s.getName());
        cv.put(COL_GENDER, s.getGender());
        cv.put(COL_SALARY, s.getSalary());
        cv.put(COL_IMAGE,  s.getImageUrl());
        db.insertWithOnConflict(TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    /**
     * 성별별 사원 목록 조회
     * @param gender "남" 또는 "여"
     * @param asc    true: salary 오름차순, false: 내림차순
     * @return 리스트
     */
    public List<Sawon> getSawonByGender(String gender, boolean asc) {
        List<Sawon> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String order = COL_SALARY + (asc ? " ASC" : " DESC");
        String[] cols = { COL_ID, COL_NAME, COL_GENDER, COL_SALARY, COL_IMAGE };
        String   sel  = COL_GENDER + " = ?";
        String[] args = { gender };

        Cursor cursor = db.query(
                TABLE_NAME,
                cols,
                sel,
                args,
                null,
                null,
                order
        );

        while (cursor.moveToNext()) {
            Sawon s = new Sawon();
            s.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            s.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
            s.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER)));
            s.setSalary(cursor.getInt(cursor.getColumnIndexOrThrow(COL_SALARY)));
            s.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE)));
            list.add(s);
        }

        cursor.close();
        db.close();
        return list;
    }
}