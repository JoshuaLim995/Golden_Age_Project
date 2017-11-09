package com.joshua_lsj.goldenage.Experiment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.joshua_lsj.goldenage.BirthDatePickerFragment;
import com.joshua_lsj.goldenage.RegisterDatePickerFragment;
import com.joshua_lsj.goldenage.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by limsh on 11/9/2017.
 */
 
 public class AddClientActivity extends AppCompatActivity {
 
 private EditText etName;
    private EditText etIC;
    private EditText etAge;
    private EditText etRegisterDate;
    private EditText etAddress;
    private EditText etContact;
		
		
		private RadioButton rdMale;
		//NEED TO EDIT THE ADD CLIENT ACTIVITY XML
		private EditText etPatientIC; //USE PATIENT IC AS FOREIGN_KEY 
		
		private String gender;
    private String relation;
		
		protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
				
				initialize();
				
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClient();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				
		}
		
		private void initialize(){
		
        etName = (EditText) findViewById(R.id.item_name);
        etIC = (EditText) findViewById(R.id.item_ic);
        etAge = (EditText) findViewById(R.id.item_age);
        etRegisterDate = (EditText) findViewById(R.id.item_register_date);
        etAddress = (EditText) findViewById(R.id.item_address);
        etContact = (EditText) findViewById(R.id.item_contact);
				
        etPatientIC = (EditText) findViewById(R.id.item_patientIC);
				
				rdMale = (RadioButton) findViewById(R.id.gender_male);

        etOther.setVisibility(View.INVISIBLE);
				rdMale.isChecked = true;
		}
		
		private void registerClient(){
		    final String name = etName.getText().toString().trim();
        final String ic = etIC.getText().toString().trim();
        final Integer age = Integer.parseInt(etAge.getText().toString());
        final String address = etAddress.getText().toString().trim();
        final String contact = etContact.getText().toString().trim();
        final String register_date = etRegisterDate.getText().toString().trim();
				
				final String patient_IC = etPatientIC.getText().toString().trim();
				final String regis_type = "C";
				
				    class RegisterClient extends AsyncTask<Void, Void, String>{
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
								
								Calender cal = new Calender();
								Integer birthYear = calender.getCurrentYear() - age;

                params.put("name", name);
                params.put("ic", ic);
                params.put("birthYear", birthYear.toString);
                params.put("gender", gender);
                params.put("address", address);
                params.put("contact", contact);
                params.put("regType", regis_type);
                params.put("regDate",register_date);
								params.put("patientIC", patient_IC);//PUT PATIENT IC INTO THE PARAMETER
								
                return requestHandler.sendPostRequest(URLs.URL_CLIENT_REGISTER, params);
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

        RegisterClient ru = new RegisterClient();
        ru.execute();
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
