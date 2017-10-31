package com.joshua_lsj.goldenage.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "patient.db";


    private static DatabaseContract.PatientContract patientContract;
    private static DatabaseContract.NurseContract nurseContract;
    private static DatabaseContract.DailyRecordContract dailyRecordContract;
    private static DatabaseContract.ClientContract clientContract;
    private static DatabaseContract.DriverContract driverContract;


    private static final String SQL_CREATE_PATIENT_ENTRIES =
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

    private static final String SQL_CREATE_NURSE_ENTRIES =
            "CREATE TABLE " + nurseContract.TABLE_NAME + " (" +
                    nurseContract._ID + " INTEGER PRIMARY KEY," +
                    nurseContract.NAME + " TEXT," +
                    nurseContract.IC + " TEXT," +
                    nurseContract.BIRTH_DATE + " TEXT," +
                    nurseContract.SEX + " TEXT," +
                    nurseContract.ADDRESS + " TEXT, " +
                    nurseContract.CONTACT +" TEXT, " +
                    nurseContract.REG_TYPE + " TEXT, " +
                    nurseContract.REG_DATE + " TEXT)";


    private static final String SQL_CREATE_DRIVER_ENTRIES =
            "CREATE TABLE " + driverContract.TABLE_NAME + " (" +
                    driverContract._ID + " INTEGER PRIMARY KEY," +
                    driverContract.NAME + " TEXT," +
                    driverContract.IC + " TEXT," +
                    driverContract.BIRTH_DATE + " TEXT," +
                    driverContract.SEX + " TEXT," +
                    driverContract.ADDRESS + " TEXT, " +
                    driverContract.CONTACT +" TEXT, " +
                    driverContract.REG_TYPE + " TEXT, " +
                    driverContract.REG_DATE + " TEXT)";


    private static final String SQL_CREATE_CLIENT_ENTRIES =
            "CREATE TABLE " + clientContract.TABLE_NAME + " (" +
                    clientContract._ID + " INTEGER PRIMARY KEY," +
                    clientContract.NAME + " TEXT," +
                    clientContract.IC + " TEXT," +
                    clientContract.BIRTH_DATE + " TEXT," +
                    clientContract.SEX + " TEXT," +
                    clientContract.ADDRESS + " TEXT, " +
                    clientContract.CONTACT +" TEXT, " +
                    clientContract.REG_TYPE + " TEXT, " +
                    clientContract.REG_DATE + " TEXT, " +
                    clientContract.RELATIONSHIP + " TEXT, " +
                    clientContract.PASSWORD + " TEXT)";

    private static final String SQL_CREATE_DAILY_RECORD_ENTRIES =
            "CREATE TABLE " + dailyRecordContract.TABLE_NAME + " (" +
                    dailyRecordContract._ID + " INTEGER PRIMARY KEY," +
                    dailyRecordContract.PATIENT_ID + " TEXT," +
                    dailyRecordContract.PATIENT_NAME + " TEXT," +
                    dailyRecordContract.DATE + " TEXT," +
                    dailyRecordContract.BLOOD_PRESSURE + " TEXT," +
                    dailyRecordContract.SUGAR_LEVEL + " TEXT, " +
                    dailyRecordContract.HEART_RATE +" TEXT, " +
                    dailyRecordContract.TEMPERATURE + " TEXT, " +
                    dailyRecordContract.NURSE_ID + " TEXT, " +
                    dailyRecordContract.NURSE_NAME + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PATIENT_ENTRIES);
        db.execSQL(SQL_CREATE_NURSE_ENTRIES);
        db.execSQL(SQL_CREATE_DRIVER_ENTRIES);
        db.execSQL(SQL_CREATE_CLIENT_ENTRIES);
        db.execSQL(SQL_CREATE_DAILY_RECORD_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

}
