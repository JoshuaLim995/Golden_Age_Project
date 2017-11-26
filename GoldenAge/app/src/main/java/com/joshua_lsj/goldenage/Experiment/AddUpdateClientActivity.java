package com.joshua_lsj.goldenage.Experiment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Calender;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.RegisterDatePickerFragment;
import com.joshua_lsj.goldenage.Volley.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by limsh on 11/9/2017.
 */

public class AddUpdateClientActivity extends AppCompatActivity {

    private TextInputLayout til_name, til_ic, til_age, til_registerDate, til_address, til_contact, til_patient_name, til_patient_ic;

    private EditText etName;
    private EditText etIC;
    private EditText etAge;
    private EditText etRegisterDate;
    private EditText etAddress;
    private EditText etContact;
    private EditText etPatientIC; //USE PATIENT IC AS FOREIGN_KEY
    private EditText etPatientName;

    private String gender;

    private Client client = null;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = (Client) getIntent().getSerializableExtra(ViewClientActivity.CLIENT);

        initialize();

        if(client != null)
            fillEditText();
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEditText()){
                    if(client != null)
                        registerClient(URLs.UPDATE);
                    else
                        registerClient(URLs.CREATE);
                }

            }
        });

    }
    
    private void initialize(){

        til_name = (TextInputLayout) findViewById(R.id.TIL_Name);
        til_ic = (TextInputLayout) findViewById(R.id.TIL_IC);
        til_age = (TextInputLayout) findViewById(R.id.TIL_Age);
        til_contact = (TextInputLayout) findViewById(R.id.TIL_Contact);
        til_address = (TextInputLayout) findViewById(R.id.TIL_Address);
        til_registerDate = (TextInputLayout) findViewById(R.id.TIL_RegisterDate);
        til_patient_name = (TextInputLayout) findViewById(R.id.TIL_Patient_Name);
        til_patient_ic = (TextInputLayout) findViewById(R.id.TIL_Patient_IC);

        etName = (EditText) findViewById(R.id.item_name);
        etIC = (EditText) findViewById(R.id.item_ic);
        etAge = (EditText) findViewById(R.id.item_age);
        etRegisterDate = (EditText) findViewById(R.id.item_register_date);
        etAddress = (EditText) findViewById(R.id.item_address);
        etContact = (EditText) findViewById(R.id.item_contact);
        etPatientIC = (EditText) findViewById(R.id.item_patient_ic);
        etPatientName = (EditText) findViewById(R.id.item_patient_name);

        Calender calender = new Calender();
        etRegisterDate.setText(calender.getToday());

        RadioButton radGender = findViewById(R.id.gender_male);
        radGender.setChecked(true);
        gender = "M";

        //Change Lable
        if(client != null)
            getSupportActionBar().setTitle("Update Client");
        else
            getSupportActionBar().setTitle("Register Client");

        //Remove Patient Detail
        if(client != null){
            til_patient_name.setVisibility(View.GONE);
            til_patient_ic.setVisibility(View.GONE);
        }

    }

    private void fillEditText(){
            etName.setText(client.getName());
            etIC.setText(client.getIc());
            etAge.setText(client.getAge());
            etRegisterDate.setText(client.getRegisDate());
            etAddress.setText(client.getAddress());
            etContact.setText(client.getContact());
    }

    private boolean checkEditText(){
        boolean validate = false;

        if(!etName.getText().toString().isEmpty()){
            til_name.setError(null);
            validate = true;
        }else {
            til_name.setError("Please fill in name");
            validate = false;
        }

        if(!etIC.getText().toString().isEmpty()){
            til_ic.setError(null);
            validate = true;
        }else {
            til_ic.setError("Please fill in ic");
            validate = false;
        }

        if(!etAge.getText().toString().isEmpty()) {
            til_age.setError(null);
            validate = true;
        }else {
            til_age.setError("Please fill in age");
            validate = false;
        }

        if(!etRegisterDate.getText().toString().isEmpty()) {
            til_registerDate.setError(null);
            validate = true;
        }else {
            til_registerDate.setError("Please fill in register date");
            validate = false;
        }

        if(!etAddress.getText().toString().isEmpty()){
            til_address.setError(null);
            validate = true;
        }else {
            til_address.setError("Please fill in address");
            validate = false;
        }

        if(!etContact.getText().toString().isEmpty()){
            til_contact.setError(null);
            validate = true;
        }else {
            til_contact.setError("Please fill in contact");
            validate = false;
        }

        if(client == null){
            if(!etPatientName.getText().toString().isEmpty()){
                til_patient_name.setError(null);
                validate = true;
            }else {
                til_patient_name.setError("Please fill in patient's name");
                validate = false;
            }

            if(!etPatientIC.getText().toString().isEmpty()){
                til_patient_ic.setError(null);
                validate = true;
            }else {
                til_patient_ic.setError("Please fill in patient's ic");
                validate = false;
            }
        }

        return validate;
    }

    //URLs.UPDATE_CLIENT_URL
    private void registerClient(String url){
      final String name = etName.getText().toString().trim();
      final String ic = etIC.getText().toString().trim();
      final Integer age = Integer.parseInt(etAge.getText().toString());
      final String address = etAddress.getText().toString().trim();
      final String contact = etContact.getText().toString().trim();
      final String register_date = etRegisterDate.getText().toString().trim();
      
      final String patient_IC = etPatientIC.getText().toString().trim();
      final String patient_name = etPatientName.getText().toString().trim();
      final String regis_type = "C";


      VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
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
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

            Map<String, String> params = new HashMap<>();

            Calender cal = new Calender();
            Integer birthYear = cal.getCurrentYear() - age;

            params.put("type", "Client");

            params.put("Name", name);
            params.put("IC", ic);
            params.put("Birthyear", birthYear.toString());
            params.put("Gender", gender);
            params.put("Address", address);
            params.put("Contact", contact);
            params.put("regisType", regis_type);
            params.put("regisDate",register_date);

            //For Update Client
            if(client != null)
                params.put("id", client.getID());

            //For Create Client
            if(client == null){
                params.put("Patient_IC", patient_IC);//PUT PATIENT IC INTO THE PARAMETER
                params.put("Patient_Name", patient_name);
            }

                    return params;
                }

            };

            //adding the request to volley
            Volley.newRequestQueue(this).add(volleyMultipartRequest);
        }
        
        
        public void onRadioButtonClicked(View view) {

            boolean checked = ((RadioButton) view).isChecked();

            switch (view.getId()) {
                case R.id.gender_male:
                if (checked)
                    gender = "M";
                break;
                case R.id.gender_female:
                if (checked)
                    gender = "F";
            }
        }

        public void showDatePickerDialog(View view){
            DialogFragment fragment = new RegisterDatePickerFragment();
            fragment.show(getSupportFragmentManager(), "datePicker");
        }
    }
