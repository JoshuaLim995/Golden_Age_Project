package com.joshua_lsj.goldenage.Experiment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
//import com.joshua_lsj.goldenage.DataBase.Queries;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.Objects.Calender;
import com.joshua_lsj.goldenage.Objects.User;
import com.joshua_lsj.goldenage.Objects.Patient;
import com.joshua_lsj.goldenage.Other.VolleyMultipartRequest;
import com.joshua_lsj.goldenage.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 10/22/2017.
 */

public class AddPatientMedicalActivity extends AppCompatActivity{

    private static final String PATIENT = "Patient";
    private Patient patient;
    private Calender calender;
    private User user;

    private TextInputLayout til_bloodPressure, til_sugarLevel, til_heartRate, til_temperature, til_description;
    
    private EditText etBlood_Pressure;
    private EditText etSugar_Level;
    private EditText etHeartRate;
    private EditText etTemperature;
    private EditText etDescription;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_medical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get user from sharedPreference
        user = SharedPrefManager.getInstance(this).getUserSharedPref();
        intialize();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEditText();
                if(!etBlood_Pressure.getText().toString().isEmpty() &&
                        !etSugar_Level.getText().toString().isEmpty() &&
                        !etHeartRate.getText().toString().isEmpty() &&
                        !etTemperature.getText().toString().isEmpty() &&
                        !etDescription.getText().toString().isEmpty()
                        )
                    createMedicakRecord();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void checkEditText(){
        if(!etBlood_Pressure.getText().toString().isEmpty())
            til_bloodPressure.setError(null);
        else
            til_bloodPressure.setError("Please fill in here");

        if(!etSugar_Level.getText().toString().isEmpty())
            til_sugarLevel.setError(null);
        else
            til_sugarLevel.setError("Please fill in here");

        if(!etHeartRate.getText().toString().isEmpty())
            til_heartRate.setError(null);
         else
            til_heartRate.setError("Please fill in here");

        if(!etTemperature.getText().toString().isEmpty())
            til_temperature.setError(null);
        else
            til_temperature.setError("Please fill in here");
        if(!etDescription.getText().toString().isEmpty())
            til_description.setError(null);
        else
            til_description.setError("Please fill in Description");
    }
    
    private void intialize(){
        calender = new Calender();
        patient = (Patient) getIntent().getSerializableExtra(PATIENT);
        //GET USER ID FROM THE SHARED_PREFERENCE IF CAN,, IF NOT, THEN USE THE INTENT?? 

        TextView tvPatient_Name = (TextView) findViewById(R.id.item_patient_name);
        TextView tvDate = (TextView) findViewById(R.id.item_date);
        TextView tvNurse_name = (TextView) findViewById(R.id.item_nurse_name); //CHECK AGAIN IF THIS IS NEEDED OR OPTIONAL
        
        tvPatient_Name.setText(patient.getName());
        tvDate.setText(calender.getToday());
        tvNurse_name.setText(user.getName());
        
        etBlood_Pressure = (EditText) findViewById(R.id.item_bloodPressure);
        etSugar_Level = (EditText) findViewById(R.id.item_sugarLevel);
        etHeartRate = (EditText) findViewById(R.id.item_heartRate);
        etTemperature = (EditText) findViewById(R.id.item_temperature);
        etDescription = (EditText) findViewById(R.id.item_description);

        til_bloodPressure = findViewById(R.id.TIL_bloodPressure);
        til_heartRate = findViewById(R.id.TIL_heartRate);
        til_sugarLevel = findViewById(R.id.TIL_sugarLevel);
        til_temperature = findViewById(R.id.TIL_temperature);
        til_description = findViewById(R.id.til_description);
    }
    
    private void createMedicakRecord(){
        final String bloodPressure = etBlood_Pressure.getText().toString();
        final String sugarLevel = etSugar_Level.getText().toString();
        final String heartRate = etHeartRate.getText().toString();
        final String temperature = etTemperature.getText().toString();
        final String description = etDescription.getText().toString();


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_UPLOAD_MEDICAL_RECORD,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error"))
                                finish();

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Calender calender = new Calender();

                Map<String, String> params = new HashMap<>();
                params.put("type", "Medical");
                //Put Patient data to parameters
                params.put("Date", calender.getToday());
                params.put("Patient_ID", patient.getID()); //GET ID IN P001 FORMAT
                params.put("Nurse_ID", user.getID()); //GET ID IN N001 FORMAT
                params.put("Blood_Pressure", bloodPressure);
                params.put("Sugar_Level", sugarLevel);
                params.put("Heart_Rate", heartRate);
                params.put("Temperature", temperature);
                params.put("Description", description);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }
}
