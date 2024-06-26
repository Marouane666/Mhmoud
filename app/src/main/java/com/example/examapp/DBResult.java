package com.example.examapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBResult extends SQLiteOpenHelper {
    public static final String dbname = "Exam.db";

    public DBResult(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table result1(email text, subject text, score text, correct text, wrong text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists result1");
        onCreate(sqLiteDatabase);
    }

    public Cursor get_data(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("Select * from result1 where email = ?",new String[]{email});
        return cr;
    }
}
