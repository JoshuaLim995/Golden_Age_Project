package com.joshua_lsj.goldenage.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by limsh on 11/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "GoldenAgeDB";
    //Constant for Database


    //Create Database to store data from server
    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + DatabaseContract.UserContract.TABLE_NAME + " (" +
                    DatabaseContract.UserContract._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.UserContract.NAME + " TEXT, " +
                    DatabaseContract.UserContract.IC + " TEXT, " +
                    DatabaseContract.UserContract.GENDER + " TEXT, " +
                    DatabaseContract.UserContract.AGE + " TEXT, " +
                    DatabaseContract.UserContract.CONTACT + " TEXT, " +
                    DatabaseContract.UserContract.ADDRESS + " TEXT, " +
                    DatabaseContract.UserContract.REG_DATE + " TEXT, " +
                    DatabaseContract.UserContract.REG_TYPE + " TEXT)";


    //Create Database to store temporary data in local for sending out to server
    private static final String CREATE_TEMP_USERS_TABLE =
            "CREATE TABLE " + DatabaseContract.TempUserContract.TABLE_NAME + " (" +
                    DatabaseContract.TempUserContract._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.TempUserContract.NAME + " TEXT, " +
                    DatabaseContract.TempUserContract.IC + " TEXT, " +
                    DatabaseContract.TempUserContract.GENDER + " TEXT, " +
                    DatabaseContract.TempUserContract.AGE + " TEXT, " +
                    DatabaseContract.TempUserContract.CONTACT + " TEXT, " +
                    DatabaseContract.TempUserContract.ADDRESS + " TEXT, " +
                    DatabaseContract.TempUserContract.REG_DATE + " TEXT, " +
                    DatabaseContract.TempUserContract.REG_TYPE + " TEXT, " +
                    DatabaseContract.TempUserContract.UPGRADE + " INT)";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TEMP_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
