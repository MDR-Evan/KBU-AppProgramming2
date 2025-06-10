package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME    = "app.db";
    private static final int    DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성
        db.execSQL("CREATE TABLE CUSTOMER(" +
                "CID TEXT PRIMARY KEY," +
                "CNAME TEXT)");
        db.execSQL("CREATE TABLE PRODUCT(" +
                "PID TEXT PRIMARY KEY," +
                "PNAME TEXT," +
                "COST REAL)");
        db.execSQL("CREATE TABLE SALE(" +
                "ORD_NO TEXT PRIMARY KEY," +
                "CID TEXT," +
                "PID TEXT," +
                "QTY INTEGER," +
                "FOREIGN KEY(CID) REFERENCES CUSTOMER(CID)," +
                "FOREIGN KEY(PID) REFERENCES PRODUCT(PID))");
        // 샘플 데이터 삽입
        db.execSQL("INSERT INTO CUSTOMER VALUES('C001','홍길동')");
        db.execSQL("INSERT INTO CUSTOMER VALUES('C002','김대한')");
        db.execSQL("INSERT INTO CUSTOMER VALUES('C003','이구슬')");
        db.execSQL("INSERT INTO PRODUCT  VALUES('P021','축구공',25000)");
        db.execSQL("INSERT INTO PRODUCT  VALUES('P022','배구공',19000)");
        db.execSQL("INSERT INTO PRODUCT  VALUES('P023','야구공', 8000)");
        db.execSQL("INSERT INTO SALE     VALUES('O1235','C001','P021',2)");
        db.execSQL("INSERT INTO SALE     VALUES('O1236','C002','P021',1)");
        db.execSQL("INSERT INTO SALE     VALUES('O1237','C002','P023',5)");
        db.execSQL("INSERT INTO SALE     VALUES('O1238','C003','P022',3)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS SALE");
        db.execSQL("DROP TABLE IF EXISTS PRODUCT");
        db.execSQL("DROP TABLE IF EXISTS CUSTOMER");
        onCreate(db);
    }

    // CUSTOMER 전체 조회
    public List<Customer> getAllCustomers() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT CID,CNAME FROM CUSTOMER", null);
        List<Customer> list = new ArrayList<>();
        while (c.moveToNext()) {
            list.add(new Customer(
                    c.getString(0),
                    c.getString(1)
            ));
        }
        c.close();
        return list;
    }

    // PRODUCT 전체 조회
    public List<Product> getAllProducts() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT PID,PNAME,COST FROM PRODUCT", null);
        List<Product> list = new ArrayList<>();
        while (c.moveToNext()) {
            list.add(new Product(
                    c.getString(0),
                    c.getString(1),
                    c.getFloat(2)
            ));
        }
        c.close();
        return list;
    }

    // SALE 전체 조회
    public List<Sale> getAllSales() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ORD_NO,CID,PID,QTY FROM SALE", null);
        List<Sale> list = new ArrayList<>();
        while (c.moveToNext()) {
            list.add(new Sale(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3)
            ));
        }
        c.close();
        return list;
    }
    // 특정 고객이 구입한 제품명·수량·가격·총금액 조회
    public List<Purchase> getPurchasesByCustomerName(String cname) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT p.PNAME, s.QTY, p.COST, (s.QTY * p.COST) AS TOTAL " +
                "FROM SALE s " +
                "JOIN CUSTOMER c ON s.CID=c.CID " +
                "JOIN PRODUCT p  ON s.PID=p.PID " +
                "WHERE c.CNAME=?";
        Cursor c = db.rawQuery(sql, new String[]{cname});
        List<Purchase> list = new ArrayList<>();
        while (c.moveToNext()) {
            list.add(new Purchase(
                    c.getString(0),   // 상품명
                    c.getInt(1),      // 수량
                    c.getFloat(2),    // 가격
                    c.getFloat(3)     // 총금액
            ));
        }
        c.close();
        return list;
    }

}
