package com.joshua_lsj.goldenage;

import android.content.Intent;
import android.icu.text.TimeZoneNames;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by limsh on 10/22/2017.
 */

public class PatientMedicalActivity extends AppCompatActivity{

    private static final String PATIENT = "Patient";
    private Patient patient;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvPName = (TextView) findViewById(R.id.item_patient_name);
        TextView tvDate = (TextView) findViewById(R.id.item_date);
        TextView tvNname = (TextView) findViewById(R.id.item_nurse_name);

        EditText etBPressure = (EditText) findViewById(R.id.item_bloodPressure);
        EditText etSLevel = (EditText) findViewById(R.id.item_sugarLevel);
        EditText etTemp = (EditText) findViewById(R.id.item_temperature);





        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra(PATIENT);

        tvPName.setText(patient.getName());
//Set date


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}
