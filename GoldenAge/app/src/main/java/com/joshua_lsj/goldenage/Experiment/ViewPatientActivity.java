package com.joshua_lsj.goldenage.Experiment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.joshua_lsj.goldenage.Calender;
import com.joshua_lsj.goldenage.PatientMedicalActivity;
import com.joshua_lsj.goldenage.R;

/**
 * Created by limsh on 10/21/2017.
 */

public class ViewPatientActivity extends AppCompatActivity {

    private static final String PATIENT = "Patient";

    private Patient patient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Initialize();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), PatientMedicalActivity.class);
                intent.putExtra(PATIENT, patient);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void Initialize() {


        patient = (Patient) getIntent().getSerializableExtra(ListViewPatientsFragment.PATIENT);


        TextView tvId = (TextView) findViewById(R.id.view_patient_id);
        TextView tvName = (TextView) findViewById(R.id.view_patient_name);
        TextView tvContact = (TextView) findViewById(R.id.view_patient_contact);
        TextView tvEmergencyContact = (TextView) findViewById(R.id.view_patient_emergency_contact);
        TextView tvIC = (TextView) findViewById(R.id.view_patient_ic);
        TextView tvGender = (TextView) findViewById(R.id.view_patient_sex);
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
        tvGender.setText(patient.getGender());
//            tvAge.setText(patient.g());
        tvBirthday.setText(Integer.toString(patient.getAge())); //CHANGE TO AGE
        tvBlood.setText(patient.getBlood_type());
        tvSick.setText(patient.getSickness());
        tvMeal.setText(patient.getMeals());
        tvAllergic.setText(patient.getAllergic());
        tvRegDate.setText(patient.getRegister_date());
        tvRegType.setText(patient.getRegister_type());
        tvMargin.setText(patient.getMargin().toString());

        Calender calender = new Calender();
        tvAge.setText(Integer.toString(calender.getCurrentYear()));
    }
}