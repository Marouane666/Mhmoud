package com.example.examapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EditAndDelete extends SQLiteOpenHelper {
    private static final String dbname="UserInformation.db";
    public EditAndDelete(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String q = "Create table user(name Text, email Text primary key, password Text)";
        sqLiteDatabase.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop Table if exists user");
        onCreate(sqLiteDatabase);
    }

    public boolean update_data(String nm, String mail, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",nm);
        cv.put("email",mail);
        cv.put("password",pass);
        Cursor cr = db.rawQuery("select * from user where email=?",new String[]{mail});
        if(cr.getCount()>0){
            long r = db.update("user",cv,"email=?",new String[]{mail});
            if(r==-1)
                return false;
            else
                return true;
        }else{
            return false;
        }
    }

    public boolean delete_data(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("select * from user where email=?",new  String[]{email});
        if(cr.getCount()>0){
            long r = db.delete("user","email=?",new String[]{email});
            if(r==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }
}
