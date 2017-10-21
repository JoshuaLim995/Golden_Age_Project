package com.joshua_lsj.goldenage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by limsh on 10/21/2017.
 */

public class Queries {
    DatabaseHelper helper;
    private static DatabaseContract.PatientContract patientContract;

    public Queries(DatabaseHelper helper){
        this.helper = helper;
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(
                DatabaseContract.PatientContract.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }

    //INSERT DATA INTO PATIENT
    public Long insert(Patient patient){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(patientContract.NAME, patient.getName());
        values.put(patientContract.IC, patient.getIc());
        values.put(patientContract.BIRTH_DATE, patient.getBirthday());
        values.put(patientContract.SEX, patient.getSex());
        values.put(patientContract.BLOOD_TYPE, patient.getBlood_type());
        values.put(patientContract.ADDRESS, patient.getAddress());
        values.put(patientContract.CONTACT, patient.getContact());
        values.put(patientContract.MEALS, patient.getMeals());
        values.put(patientContract.ALLERGIC, patient.getAllergic());
        values.put(patientContract.SICKNESS, patient.getSickness());
        values.put(patientContract.REG_TYPE, patient.getRegister_type());
        values.put(patientContract.REG_DATE, patient.getRegister_date());
        values.put(patientContract.MARGIN, patient.getMargin().toString());
//TODO: LATER NEED TO ADD MORE THINGS HERE



        Long id = db.insert(patientContract.TABLE_NAME, null, values);
        patient.setId(id);

        return id;
    }
}
