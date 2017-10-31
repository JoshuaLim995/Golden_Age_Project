package com.joshua_lsj.goldenage.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Objects.DailyRecord;
import com.joshua_lsj.goldenage.Objects.Driver;
import com.joshua_lsj.goldenage.Objects.Nurse;
import com.joshua_lsj.goldenage.Objects.Patient;

/**
 * Created by limsh on 10/21/2017.
 */

public class Queries {
    DatabaseHelper helper;
    private static DatabaseContract.PatientContract patientContract;
    private static DatabaseContract.NurseContract nurseContract;
    private static DatabaseContract.DriverContract driverContract;
    private static DatabaseContract.ClientContract clientContract;
    private static DatabaseContract.DailyRecordContract dailyRecordContract;

    public Queries(DatabaseHelper helper){
        this.helper = helper;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(
                tableName,
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

    //INSERT DATA INTO NURSE
    public Long insert(Nurse nurse){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(nurseContract.NAME, nurse.getName());
        values.put(nurseContract.IC, nurse.getIc());
        values.put(nurseContract.BIRTH_DATE, nurse.getBirthday());
        values.put(nurseContract.SEX, nurse.getSex());
        values.put(nurseContract.ADDRESS, nurse.getAddress());
        values.put(nurseContract.CONTACT, nurse.getContact());
        values.put(nurseContract.REG_TYPE, nurse.getRegister_type());
        values.put(nurseContract.REG_DATE, nurse.getRegister_date());
//TODO: LATER NEED TO ADD MORE THINGS HERE


        Long id = db.insert(nurseContract.TABLE_NAME, null, values);
        nurse.setId(id);

        return id;
    }

    //INSERT DATA INTO DRIVER
    public Long insert(Driver driver){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(driverContract.NAME, driver.getName());
        values.put(driverContract.IC, driver.getIc());
        values.put(driverContract.BIRTH_DATE, driver.getBirthday());
        values.put(driverContract.SEX, driver.getSex());
        values.put(driverContract.ADDRESS, driver.getAddress());
        values.put(driverContract.CONTACT, driver.getContact());
        values.put(driverContract.REG_TYPE, driver.getRegister_type());
        values.put(driverContract.REG_DATE, driver.getRegister_date());
//TODO: LATER NEED TO ADD MORE THINGS HERE


        Long id = db.insert(driverContract.TABLE_NAME, null, values);
        driver.setId(id);

        return id;
    }

    //INSERT DATA INTO CLIENT
    public Long insert(Client client){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(clientContract.NAME, client.getName());
        values.put(clientContract.IC, client.getIc());
        values.put(clientContract.BIRTH_DATE, client.getBirthday());
        values.put(clientContract.SEX, client.getSex());
        values.put(clientContract.ADDRESS, client.getAddress());
        values.put(clientContract.CONTACT, client.getContact());
        values.put(clientContract.REG_TYPE, client.getRegister_type());
        values.put(clientContract.REG_DATE, client.getRegister_date());
        values.put(clientContract.RELATIONSHIP, client.getRelationship());
        values.put(clientContract.PASSWORD, client.getPassword());
//TODO: LATER NEED TO ADD MORE THINGS HERE


        Long id = db.insert(clientContract.TABLE_NAME, null, values);
        client.setId(id);

        return id;
    }

    //INSERT DATA INTO DAILY RECORD
    public Long insert(DailyRecord dailyRecord){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(dailyRecordContract.PATIENT_ID, dailyRecord.getPatient_id());
        values.put(dailyRecordContract.PATIENT_NAME, dailyRecord.getPatient_name());
        values.put(dailyRecordContract.DATE, dailyRecord.getDate());
        values.put(dailyRecordContract.BLOOD_PRESSURE, dailyRecord.getBlood_pressure());
        values.put(dailyRecordContract.SUGAR_LEVEL, dailyRecord.getSugar_level());
        values.put(dailyRecordContract.HEART_RATE, dailyRecord.getHeart_rate());
        values.put(dailyRecordContract.TEMPERATURE, dailyRecord.getTemperature());
        values.put(dailyRecordContract.NURSE_ID, dailyRecord.getNurse_id());
        values.put(dailyRecordContract.NURSE_NAME, dailyRecord.getNurse_name());
//TODO: LATER NEED TO ADD MORE THINGS HERE


        Long id = db.insert(dailyRecordContract.TABLE_NAME, null, values);
        dailyRecord.setId(id);

        return id;
    }
}
