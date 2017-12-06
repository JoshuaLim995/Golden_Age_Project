package com.joshua_lsj.goldenage.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Objects.Medical;
import com.joshua_lsj.goldenage.Objects.Patient;
import com.joshua_lsj.goldenage.Objects.Schedule;
import com.joshua_lsj.goldenage.Objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limsh on 11/28/2017.
 */

public class Queries {
    DatabaseHelper helper;

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

    //ID IS THE CLIENT'S
    public Cursor getJoinClientPatient(String id){
        SQLiteDatabase db = helper.getReadableDatabase();

        String QUERY = "SELECT c._id, c.name, c.ic, c.gender, c.age, c.contact, c.address, c.reg_date, c.reg_type, c.patient_id, p.name AS patient_name FROM clients c INNER JOIN patients p ON c.patient_id = p._id WHERE c._id = ?";

        return db.rawQuery(QUERY, new String[]{id});
    }

    //Insert user into users table
    public Long insert(User user){
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        if(user.getID() != null)
            values.put(DatabaseContract.UserContract._ID, user.getID());
        values.put(DatabaseContract.UserContract.NAME, user.getName());
        values.put(DatabaseContract.UserContract.IC, user.getIc());
        values.put(DatabaseContract.UserContract.GENDER, user.getGender());
        values.put(DatabaseContract.UserContract.AGE, user.getAge());
        values.put(DatabaseContract.UserContract.CONTACT, user.getContact());
        values.put(DatabaseContract.UserContract.ADDRESS, user.getAddress());
        values.put(DatabaseContract.UserContract.REG_DATE, user.getRegisDate());
        values.put(DatabaseContract.UserContract.REG_TYPE, user.getRegisType());

        return database.insertWithOnConflict(DatabaseContract.UserContract.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    //Insert client into client table
    public Long insert(Client client){
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        if(client.getID() != null)
            values.put(DatabaseContract.ClientContract._ID, client.getID());
        values.put(DatabaseContract.ClientContract.NAME, client.getName());
        values.put(DatabaseContract.ClientContract.IC, client.getIc());
        values.put(DatabaseContract.ClientContract.GENDER, client.getGender());
        values.put(DatabaseContract.ClientContract.AGE, client.getAge());
        values.put(DatabaseContract.ClientContract.CONTACT, client.getContact());
        values.put(DatabaseContract.ClientContract.ADDRESS, client.getAddress());
        values.put(DatabaseContract.ClientContract.REG_DATE, client.getRegisDate());
        values.put(DatabaseContract.ClientContract.REG_TYPE, client.getRegisType());
        values.put(DatabaseContract.ClientContract.PATIENT_ID, client.getPatientID());
        return database.insertWithOnConflict(DatabaseContract.ClientContract.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }



    //Insert patient into patient table
    public Long insert(Patient patient){
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

            values.put(DatabaseContract.PatientContract._ID, patient.getID());
        values.put(DatabaseContract.PatientContract.NAME, patient.getName());
        values.put(DatabaseContract.PatientContract.IC, patient.getIc());
        values.put(DatabaseContract.PatientContract.GENDER, patient.getGender());
        values.put(DatabaseContract.PatientContract.AGE, patient.getAge());
        values.put(DatabaseContract.PatientContract.CONTACT, patient.getContact());
        values.put(DatabaseContract.PatientContract.ADDRESS, patient.getAddress());
        values.put(DatabaseContract.PatientContract.REG_DATE, patient.getRegisDate());
        values.put(DatabaseContract.PatientContract.REG_TYPE, patient.getRegisType());
        values.put(DatabaseContract.PatientContract.BLOOD_TYPE, patient.getBlood_type());
        values.put(DatabaseContract.PatientContract.MEALS, patient.getMeals());
        values.put(DatabaseContract.PatientContract.ALLERGIC, patient.getAllergic());
        values.put(DatabaseContract.PatientContract.SICKNESS, patient.getSickness());
        values.put(DatabaseContract.PatientContract.DEPOSIT, patient.getDeposit());
        values.put(DatabaseContract.PatientContract.IMAGE, patient.getImageName());
        return database.insertWithOnConflict(DatabaseContract.PatientContract.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    //Insert medical into medical table
    public Long insert(Medical medical){
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.MedicalContract._ID, medical.getID());
        values.put(DatabaseContract.MedicalContract.DATE, medical.getDate());
        values.put(DatabaseContract.MedicalContract.PATIENT_ID, medical.getPatient_id());
        values.put(DatabaseContract.MedicalContract.NURSE_ID, medical.getNurse_id());
        values.put(DatabaseContract.MedicalContract.BLOOD_PRESSURE, medical.getBlood_pressure());
        values.put(DatabaseContract.MedicalContract.SUGAR_LEVEL, medical.getSugar_level());
        values.put(DatabaseContract.MedicalContract.HEART_RATE, medical.getHeart_rate());
        values.put(DatabaseContract.MedicalContract.TEMPERATURE, medical.getTemperature());
        values.put(DatabaseContract.MedicalContract.DESCRIPTION, medical.getDescription());

        return database.insertWithOnConflict(DatabaseContract.MedicalContract.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public Long insert(Schedule schedule){
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.DriverScheduleContract._ID, schedule.getID());
        values.put(DatabaseContract.DriverScheduleContract.DRIVER_NAME, schedule.getDriverName());
        values.put(DatabaseContract.DriverScheduleContract.PATIENT_NAME, schedule.getPatientName());
        values.put(DatabaseContract.DriverScheduleContract.NURSE_NAME, schedule.getNurseName());
        values.put(DatabaseContract.DriverScheduleContract.LOCATION, schedule.getLocation());
        values.put(DatabaseContract.DriverScheduleContract.DESCRIPTION, schedule.getDescription());
        values.put(DatabaseContract.DriverScheduleContract.DATE, schedule.getDate());
        values.put(DatabaseContract.DriverScheduleContract.TIME, schedule.getTime());

        return database.insertWithOnConflict(DatabaseContract.DriverScheduleContract.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    //Delete
    public boolean delete(String table, String id){
        SQLiteDatabase database = helper.getWritableDatabase();
        return database.delete(table, DatabaseContract._ID + "=" + id, null) > 0;
    }

    public void deleteTables(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        List<String> tables = new ArrayList<>();

        // iterate over the result set, adding every table name to a list
        while (c.moveToNext()) {
            tables.add(c.getString(0));
        }

        // call DROP TABLE on every table name
        for (String table : tables) {
            db.delete(table, null, null);
        }

    }

}
