package com.millenialzdev.CedasAPK;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Transaksi.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Transaksi.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create table keranjang(id TEXT primary key, nama TEXT, harga TEXT, jumlah TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop table if exists keranjang");
    }

    public Boolean InsertData(String nama, String harga, String jumlah, String id){
        SQLiteDatabase Mydatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("harga", harga);
        contentValues.put("jumlah", jumlah);
        contentValues.put("id", id);
        long result = Mydatabase.insert("keranjang", null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
    public static final String TABLE_NAME = "keranjang";
    public Cursor getAllUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    public void hapusSemuaData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
        db.close();
    }


}
