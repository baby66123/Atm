package com.tom.atm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseHelper extends SQLiteOpenHelper {
    public ExpenseHelper(Context context){
        this(context,"atm",null,1);
    }



    private ExpenseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE expense(_id INTEGER PRIMARY KEY NOT NULL," +
                "cdate VARCHAR NOT NULL," +
                "info VARCHER,amount INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
