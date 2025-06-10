package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "product_db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_PRODUCT = "PRODUCT";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQL = "CREATE TABLE " + TABLE_PRODUCT + " (" +
                "CODE TEXT PRIMARY KEY," +
                "name TEXT," +
                "price REAL CHECK(price > 0.0)" +
                ");";
        db.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    // INSERT (Transaction + SQLiteStatement)
    public void insertProducts(List<Product> products) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO " + TABLE_PRODUCT + "(CODE,name,price) VALUES(?,?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            for (Product p : products) {
                stmt.clearBindings();
                stmt.bindString(1, p.getCode());
                stmt.bindString(2, p.getName());
                stmt.bindDouble(3, p.getPrice());
                stmt.execute();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // SEARCH
    public List<Product> getAllProducts() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT CODE,name,price FROM " + TABLE_PRODUCT, null);
        List<Product> list = new ArrayList<>();
        while (c.moveToNext()) {
            String code = c.getString(0);
            String name = c.getString(1);
            float price = c.getFloat(2);
            list.add(new Product(code, name, price));
        }
        c.close();
        return list;
    }

    // DELETE1: 단일 레코드 삭제
    public void deleteProduct(String code) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PRODUCT, "CODE=?", new String[]{code});
    }

    // DELETE2: 단일 레코드 삭제 + Transaction
    public void deleteProductTransactional(String code) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_PRODUCT, "CODE=?", new String[]{code});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // DELETE3: 단일 레코드 삭제 + Transaction + SQLiteStatement
    public void deleteProductTransactionalStatement(String code) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_PRODUCT + " WHERE CODE=?";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.bindString(1, code);
            stmt.executeUpdateDelete();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // DELETEALL: 모든 레코드 삭제 + Transaction + SQLiteStatement
    public void deleteAllTransactionalStatement() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_PRODUCT;
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.execute();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // UPDATE1: 모든 가격을 2000으로 (INSERT OR REPLACE + Transaction + SQLiteStatement)
    public void updateAllPricesReplace(float newPrice) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO " + TABLE_PRODUCT + "(CODE,name,price) " +
                "SELECT CODE,name,? FROM " + TABLE_PRODUCT;
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.bindDouble(1, newPrice);
            stmt.execute();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // UPDATE2: 모든 가격을 3000으로 (Transaction + SQLiteStatement)
    public void updateAllPrices(float newPrice) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE " + TABLE_PRODUCT + " SET price=?";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.bindDouble(1, newPrice);
            stmt.execute();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
