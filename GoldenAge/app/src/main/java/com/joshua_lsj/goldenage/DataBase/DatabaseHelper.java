package com.joshua_lsj.goldenage.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limsh on 11/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "GoldenAgeDB";
    //Constant for Database

    private SQLiteDatabase db;

    //Create Database to store user data from server
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

    //Create Database to store client data from server
    private static final String CREATE_CLIENTS_TABLE =
            "CREATE TABLE " + DatabaseContract.ClientContract.TABLE_NAME + " (" +
                    DatabaseContract.ClientContract._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.ClientContract.NAME + " TEXT, " +
                    DatabaseContract.ClientContract.IC + " TEXT, " +
                    DatabaseContract.ClientContract.GENDER + " TEXT, " +
                    DatabaseContract.ClientContract.AGE + " TEXT, " +
                    DatabaseContract.ClientContract.CONTACT + " TEXT, " +
                    DatabaseContract.ClientContract.ADDRESS + " TEXT, " +
                    DatabaseContract.ClientContract.REG_DATE + " TEXT, " +
                    DatabaseContract.ClientContract.REG_TYPE + " TEXT, " +
                    DatabaseContract.ClientContract.PATIENT_ID + " INT)";


    //Create Database to store patient data from server
    private static final String CREATE_PATIENTS_TABLE =
            "CREATE TABLE " + DatabaseContract.PatientContract.TABLE_NAME + " (" +
                    DatabaseContract.PatientContract._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.PatientContract.NAME + " TEXT, " +
                    DatabaseContract.PatientContract.IC + " TEXT, " +
                    DatabaseContract.PatientContract.GENDER + " TEXT, " +
                    DatabaseContract.PatientContract.AGE + " TEXT, " +
                    DatabaseContract.PatientContract.CONTACT + " TEXT, " +
                    DatabaseContract.PatientContract.ADDRESS + " TEXT, " +
                    DatabaseContract.PatientContract.REG_DATE + " TEXT, " +
                    DatabaseContract.PatientContract.REG_TYPE + " TEXT, " +
                    DatabaseContract.PatientContract.BLOOD_TYPE + " TEXT, " +
                    DatabaseContract.PatientContract.MEALS + " TEXT, " +
                    DatabaseContract.PatientContract.ALLERGIC + " TEXT, " +
                    DatabaseContract.PatientContract.SICKNESS + " TEXT, " +
                    DatabaseContract.PatientContract.MARGIN + " REAL, " +
                    DatabaseContract.PatientContract.IMAGE + " TEXT)";


    //Create Database to store medical data from server
    private static final String CREATE_MEDICAL_TABLE =
            "CREATE TABLE " + DatabaseContract.MedicalContract.TABLE_NAME + " (" +
                    DatabaseContract.MedicalContract._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.MedicalContract.DATE + " TEXT," +
                    DatabaseContract.MedicalContract.PATIENT_ID + " TEXT," +
                    DatabaseContract.MedicalContract.NURSE_ID + " TEXT, " +
                    DatabaseContract.MedicalContract.BLOOD_PRESSURE + " TEXT, " +
                    DatabaseContract.MedicalContract.SUGAR_LEVEL +" TEXT, " +
                    DatabaseContract.MedicalContract.HEART_RATE + " TEXT, " +
                    DatabaseContract.MedicalContract.TEMPERATURE + " TEXT)";


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CLIENTS_TABLE);
        db.execSQL(CREATE_MEDICAL_TABLE);
        db.execSQL(CREATE_PATIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
