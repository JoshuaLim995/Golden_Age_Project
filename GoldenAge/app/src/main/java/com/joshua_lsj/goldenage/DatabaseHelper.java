package com.joshua_lsj.goldenage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "patient.db";


    private static DatabaseContract.PatientContract patientContract;


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + patientContract.TABLE_NAME + " (" +
                    patientContract._ID + " INTEGER PRIMARY KEY," +
                    patientContract.NAME + " TEXT," +
                    patientContract.IC + " TEXT," +
                    patientContract.BIRTH_DATE + " TEXT," +
                    patientContract.SEX + " TEXT," +
                    patientContract.BLOOD_TYPE + " TEXT, " +
                    patientContract.ADDRESS + " TEXT, " +
                    patientContract.CONTACT +" TEXT, " +
                    patientContract.MEALS + " TEXT, " +
                    patientContract.ALLERGIC + " TEXT, " +
                    patientContract.SICKNESS + " TEXT, " +
                    patientContract.REG_TYPE + " TEXT, " +
                    patientContract.REG_DATE + " TEXT, " +
                    patientContract.MARGIN + " TEXT)";
//                    patientContract.PHOTO + " BLOB" +
//                    patientContract.CLIENT_ID + " INT" +



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

}
