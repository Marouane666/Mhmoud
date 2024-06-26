package com.example.examapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String dbname="Exam.db";
    public DBHelper(Context context) {
        super(context, dbname, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table user(name Text, email Text primary key, password Text)");
        System.out.println("After table user");
//        sqLiteDatabase.execSQL("create Table result1(email text, subject text, score text, correct text, wrong text)");
//        System.out.println("After table result1");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists user");
//        sqLiteDatabase.execSQL("drop table if exists result1");
        onCreate(sqLiteDatabase);
    }

    public Boolean insertData(String name, String email, String password)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        long result = sqLiteDatabase.insert("user",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean checkuseremail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from user where email = ?",new String[] {email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkuserpass(String email, String password)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from user where email = ? and password = ?",new String[] {email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
