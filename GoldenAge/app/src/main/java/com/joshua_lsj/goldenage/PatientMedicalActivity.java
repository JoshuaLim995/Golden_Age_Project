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
import android.widget.Toast;

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
    private Calender calender;
    private Nurse nurse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvPName = (TextView) findViewById(R.id.item_patient_name);
        TextView tvDate = (TextView) findViewById(R.id.item_date);
        TextView tvNname = (TextView) findViewById(R.id.item_nurse_name);

        final EditText etBPressure = (EditText) findViewById(R.id.item_bloodPressure);
        final EditText etSLevel = (EditText) findViewById(R.id.item_sugarLevel);
        final EditText etHeartRate = (EditText) findViewById(R.id.item_heartRate);
        final EditText etTemp = (EditText) findViewById(R.id.item_temperature);

        calender = new Calender();

        //SIMPLY CREATE A NURSE HERE FOR TESTING PURPOSE
        nurse = new Nurse();
        nurse.setName("Nurse Wong Wong Wong");
        nurse.setId(5201314);


        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra(PATIENT);

        tvPName.setText(patient.getName());
//Set date
        tvDate.setText(calender.getToday());
        //Set Nurse Name
        tvNname.setText(nurse.getName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bloodPressure = etBPressure.getText().toString();
                String sugarLevel = etSLevel.getText().toString();
                String heartRate = etHeartRate.getText().toString();
                String temperature = etTemp.getText().toString();

                DailyRecord dailyRecord = new DailyRecord(patient, calender.getToday(), bloodPressure, sugarLevel, heartRate, temperature, nurse);

                Queries queries = new Queries(new DatabaseHelper(getApplicationContext()));

                if(queries.insert(dailyRecord) != 0)
                    Toast.makeText(getApplicationContext(), "DailyRecord created", Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
