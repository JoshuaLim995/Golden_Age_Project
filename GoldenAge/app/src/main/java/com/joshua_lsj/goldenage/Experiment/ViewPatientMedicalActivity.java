package com.joshua_lsj.goldenage.Experiment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.joshua_lsj.goldenage.Objects.Medical;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Volley.DeleteHelper;

import java.util.ArrayList;

/**
 * Created by limsh on 10/21/2017.
 */

public class ViewPatientMedicalActivity extends AppCompatActivity {

    public static final String MEDICAL = "Medical";

    private Medical medical;
    private Patient patient = null;
    private ArrayList<Medical> medicalList;
    private DeleteHelper deleteHelper;

    private String id;
    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_medical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new ListViewPatientMedicalFragment())
                    .commit();
            getSupportActionBar().setTitle("Patient Medical");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}