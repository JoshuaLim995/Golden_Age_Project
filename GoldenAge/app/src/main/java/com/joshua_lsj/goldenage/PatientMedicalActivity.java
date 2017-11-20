package com.joshua_lsj.goldenage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.joshua_lsj.goldenage.Experiment.SharedPrefManager;
import com.joshua_lsj.goldenage.Experiment.URLs;
import com.joshua_lsj.goldenage.Experiment.User;
import com.joshua_lsj.goldenage.Experiment.Patient;
import com.joshua_lsj.goldenage.Volley.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 10/22/2017.
 */

public class PatientMedicalActivity extends AppCompatActivity{

    private static final String PATIENT = "Patient";
    private Patient patient;
    private Calender calender;
    private User user;
    
    private EditText etBlood_Pressure;
    private EditText etSugar_Level;
    private EditText etHeartRate;
    private EditText etTemperature;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get user from sharedPreference
        user = SharedPrefManager.getInstance(this).getUserSharedPref();
        intialize();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {              
                createMedicakRecord();
            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    private void intialize(){
        calender = new Calender();
        patient = (Patient) getIntent().getSerializableExtra(PATIENT);
        //GET USER ID FROM THE SHARED_PREFERENCE IF CAN,, IF NOT, THEN USE THE INTENT?? 
        
        TextView tvPatient_ID = (TextView) findViewById(R.id.item_patient_id);
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
    }
    
    private void createMedicakRecord(){
        final String bloodPressure = etBlood_Pressure.getText().toString();
        final String sugarLevel = etSugar_Level.getText().toString();
        final String heartRate = etHeartRate.getText().toString();
        final String temperature = etTemperature.getText().toString();


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_UPLOAD_MEDICAL_RECORD,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(obj.getBoolean("error")==false)
                                finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Calender calender = new Calender();

                Map<String, String> params = new HashMap<>();
                //Put Patient data to parameters
                params.put("Date", calender.getToday());
                params.put("Patient_ID", patient.getID()); //GET ID IN P001 FORMAT
                params.put("Nurse_ID", user.getID()); //GET ID IN N001 FORMAT
                params.put("Blood_Pressure", bloodPressure);
                params.put("Sugar_Level", sugarLevel);
                params.put("Heart_Rate", heartRate);
                params.put("Temperature", temperature );
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

//
//
//
//
//        class UploadMedicalRecord extends AsyncTask<Void, Void, String> {
//            @Override
//            protected String doInBackground(Void... voids) {
//
//                RequestHandler requestHandler = new RequestHandler();
//
//                HashMap<String, String> params = new HashMap<>();
//
//                params.put("Date", calender.getToday());
//                params.put("Patient_ID", patient.getId().toString()); //GET ID IN P001 FORMAT
//                params.put("Nurse_ID", user.getID().toString()); //GET ID IN N001 FORMAT
//                params.put("Blood_Pressure", bloodPressure);
//                params.put("Sugar_Level", sugarLevel);
//                params.put("Heart_Rate", heartRate);
//                params.put("Temperature", temperature );
//
//                return requestHandler.sendPostRequest(URLs.URL_UPLOAD_MEDICAL_RECORD, params);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//
//                try {
//                    //converting response to json object
//                    JSONObject obj = new JSONObject(s);
//                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                    }
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }

    }
}
