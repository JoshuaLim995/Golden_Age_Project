package com.joshua_lsj.goldenage.DataBase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by limsh on 11/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    //Constant for Database
    public static final String DB_NAME = "GoldenAgeDB";
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";

//etc etc etc.....

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
