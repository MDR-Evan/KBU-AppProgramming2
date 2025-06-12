package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "company.db";
    private static final int DB_VER = 1;

    public DBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "" +
                "CREATE TABLE IF NOT EXISTS employee (" +
                "  id TEXT PRIMARY KEY," +
                "  name TEXT," +
                "  gender TEXT," +
                "  salary INTEGER," +
                "  imageUrl TEXT" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS employee");
        onCreate(db);
    }

    public void insertEmployee(Employee e) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", e.id);
        cv.put("name", e.name);
        cv.put("gender", e.gender);
        cv.put("salary", e.salary);
        cv.put("imageUrl", e.imageUrl);
        // 중복 INSERT 방지
        db.insertWithOnConflict("employee", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    /** @param genderFilter null이면 전체, 아니면 "남자" or "여자"
     * @param asc true면 오름차순, false면 내림차순 */
    public List<Employee> getEmployees(@Nullable String genderFilter, boolean asc) {
        SQLiteDatabase db = getReadableDatabase();
        String order = asc ? "ASC" : "DESC";
        Cursor c;
        if (genderFilter == null) {
            c = db.rawQuery(
                    "SELECT * FROM employee ORDER BY salary " + order, null);
        } else {
            c = db.rawQuery(
                    "SELECT * FROM employee WHERE gender=? ORDER BY salary " + order,
                    new String[]{ genderFilter });
        }

        List<Employee> list = new ArrayList<>();
        while (c.moveToNext()) {
            list.add(new Employee(
                    c.getString(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("gender")),
                    c.getInt   (c.getColumnIndex("salary")),
                    c.getString(c.getColumnIndex("imageUrl"))
            ));
        }
        c.close();
        return list;
    }
}
