package com.joshua_lsj.goldenage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
//import com.joshua_lsj.goldenage.DataBase.Queries;
import com.joshua_lsj.goldenage.Objects.DailyRecord;
import com.joshua_lsj.goldenage.Objects.Nurse;
import com.joshua_lsj.goldenage.Experiment.Patient;

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

        intialize();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {              
                createMedicakRecord()
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        tvNurse_name.setText(nurse.getName());      
        
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
        
        class UploadMedicalRecord extends AsyncTask<Void, Void, String>{
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("date", calender.getToday());
                params.put("patient_id", patient.getID()); //GET ID IN P001 FORMAT
                params.put("user_id", user.getID); //GET ID IN N001 FORMAT
                params.put("bloodPressure", bloodPressure);
                params.put("sugarLevel", sugarLevel);
                params.put("heartRate", heartRate);
                params.put("temperature", temperature );
                
                return requestHandler.sendPostRequest(URLs.URL_UPLOAD_MEDICAL_RECORD, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }

        UploadMedicalRecord ru = new UploadMedicalRecord();
        ru.execute();
    }
}
