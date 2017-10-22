package com.joshua_lsj.goldenage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.InterpolatorRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by limsh on 10/21/2017.
 */

public class ViewPatientActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "Patient.ID";
    private static DatabaseContract.PatientContract patientContract;

    private Patient patient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        long id = intent.getLongExtra(EXTRA_ID, 0);

        Queries queries = new Queries(new DatabaseHelper(getApplicationContext()));

        final String[] columns = {
                patientContract._ID,
                patientContract.NAME,
                patientContract.IC,
                patientContract.BIRTH_DATE,
                patientContract.SEX,
                patientContract.BLOOD_TYPE,
                patientContract.ADDRESS,
                patientContract.CONTACT,
                patientContract.MEALS,
                patientContract.ALLERGIC,
                patientContract.SICKNESS,
                patientContract.REG_TYPE,
                patientContract.REG_DATE,
                patientContract.MARGIN,
        };

        String selection = DatabaseContract.PatientContract._ID + " = ?";
        String[] selectionArgs = {Long.toString(id)};

        Cursor cursor = queries.query(columns, selection, selectionArgs, null, null, null);


        if (cursor.moveToNext()) {

            patient = new Patient(
                    cursor.getLong(cursor.getColumnIndex(patientContract._ID)),
                    cursor.getString(cursor.getColumnIndex(patientContract.NAME)),
                    cursor.getString(cursor.getColumnIndex(patientContract.IC)),
                    cursor.getString(cursor.getColumnIndex(patientContract.BIRTH_DATE)),
                    cursor.getString(cursor.getColumnIndex(patientContract.SEX)),
                    cursor.getString(cursor.getColumnIndex(patientContract.BLOOD_TYPE)),
                    cursor.getString(cursor.getColumnIndex(patientContract.ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(patientContract.CONTACT)),
                    cursor.getString(cursor.getColumnIndex(patientContract.MEALS)),
                    cursor.getString(cursor.getColumnIndex(patientContract.ALLERGIC)),
                    cursor.getString(cursor.getColumnIndex(patientContract.SICKNESS)),
                    cursor.getString(cursor.getColumnIndex(patientContract.REG_DATE)),
                    cursor.getDouble(cursor.getColumnIndex(patientContract.MARGIN))
            );

            TextView tvId = (TextView) findViewById(R.id.view_patient_id);
            TextView tvName = (TextView) findViewById(R.id.view_patient_name);
            TextView tvContact = (TextView) findViewById(R.id.view_patient_contact);
            TextView tvEmergencyContact = (TextView) findViewById(R.id.view_patient_emergency_contact);
            TextView tvIC = (TextView) findViewById(R.id.view_patient_ic);
            TextView tvSex = (TextView) findViewById(R.id.view_patient_sex);
            TextView tvAge = (TextView) findViewById(R.id.view_patient_age);
            TextView tvBirthday = (TextView) findViewById(R.id.view_patient_birthday);
            TextView tvBlood = (TextView) findViewById(R.id.view_patient_blood_type);
            TextView tvSick = (TextView) findViewById(R.id.view_patient_sickness);
            TextView tvMeal = (TextView) findViewById(R.id.view_patient_meals);
            TextView tvAllergic = (TextView) findViewById(R.id.view_patient_allergic);
            TextView tvRegDate = (TextView) findViewById(R.id.view_patient_reg_date);
            TextView tvRegType = (TextView) findViewById(R.id.view_patient_reg_type);
            TextView tvMargin = (TextView) findViewById(R.id.view_patient_margin);

            tvId.setText(patient.getId().toString());
            tvName.setText(patient.getName());
            tvContact.setText(patient.getContact());
//            tvEmergencyContact.setText(patient.());
            tvIC.setText(patient.getIc());
            tvSex.setText(patient.getSex());
//            tvAge.setText(patient.g());
            tvBirthday.setText(patient.getBirthday());
            tvBlood.setText(patient.getBlood_type());
            tvSick.setText(patient.getSickness());
            tvMeal.setText(patient.getMeals());
            tvAllergic.setText(patient.getAllergic());
            tvRegDate.setText(patient.getRegister_date());
            tvRegType.setText(patient.getRegister_type());
            tvMargin.setText(patient.getMargin().toString());
        }

    }

}
