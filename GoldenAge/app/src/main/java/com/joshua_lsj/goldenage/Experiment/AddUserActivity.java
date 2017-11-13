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
import android.widget.RadioButton;
import android.widget.Toast;

import com.joshua_lsj.goldenage.Calender;
import com.joshua_lsj.goldenage.RegisterDatePickerFragment;
import com.joshua_lsj.goldenage.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by limsh on 10/29/2017.
 */



public class AddUserActivity extends AppCompatActivity {

    private TextInputLayout til_name, til_ic, til_age, til_registerDate, til_address, til_contact;
    private TextInputEditText etName, etIC, etAge, etRegisterDate, etAddress, etContact;

    private String gender;
    private String user_type;

   // private Nurse nurse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = (TextInputEditText) findViewById(R.id.item_name);
        etIC = (TextInputEditText) findViewById(R.id.item_ic);
        etAge = (TextInputEditText) findViewById(R.id.item_age);
        etRegisterDate = (TextInputEditText) findViewById(R.id.item_register_date);
        etAddress = (TextInputEditText) findViewById(R.id.item_address);
        etContact = (TextInputEditText) findViewById(R.id.item_contact);

        til_name = (TextInputLayout) findViewById(R.id.TIL_Name);
        til_ic = (TextInputLayout) findViewById(R.id.TIL_IC);
        til_age = (TextInputLayout) findViewById(R.id.TIL_Age);
        til_contact = (TextInputLayout) findViewById(R.id.TIL_Contact);
        til_address = (TextInputLayout) findViewById(R.id.TIL_Address);
        til_registerDate = (TextInputLayout) findViewById(R.id.TIL_RegisterDate);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(etName.getText().length() == 0 ) {
                    til_name.setError("Name cannot be empty");
                    fab.setEnabled(false);
                } else {
                    til_name.setError(null);
                    fab.setEnabled(true);
                }
            }
        });

        etIC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(etIC.getText().length() == 0) {
                    til_ic.setError("IC number cannot be empty");
                    fab.setEnabled(false);
                } else if (etIC.getText().length() > 12) {
                    til_ic.setError("Length of IC number exceeded");
                    fab.setEnabled(false);
                } else {
                    til_ic.setError(null);
                    fab.setEnabled(true);
                }
            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(etAddress.getText().length() == 0) {
                    til_address.setError("Address cannot be empty");
                    fab.setEnabled(false);
                } else {
                    til_address.setError(null);
                    fab.setEnabled(true);
                }
            }
        });

        etContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(etContact.getText().length() == 0 ) {
                    til_contact.setError("Phone number cannot be empty");
                    fab.setEnabled(false);
                } else if(etContact.getText().length() > 10){
                    til_contact.setError("Length of phone number exceeded");
                    fab.setEnabled(false);
                } else {
                    til_contact.setError(null);
                    fab.setEnabled(true);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//USED TO ADD ADMIN, NURSE, DRIVER

                registerUser();

                finish();

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void registerUser(){
       final String name = etName.getText().toString().trim();
        final   String ic = etIC.getText().toString().trim();
        final   int age = Integer.parseInt(etAge.getText().toString());
        final    String address = etAddress.getText().toString().trim();
        final     String contact = etContact.getText().toString().trim();
        final     String register_date = etRegisterDate.getText().toString();
 //       final String register_date = "2017-12-23".trim();


        class RegisterUser extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                Calender calender = new Calender();
                Integer birthYear = calender.getCurrentYear() - age;

                params.put("Name", name);
                params.put("IC", ic);
                params.put("Gender", gender);
                params.put("Birthyear", birthYear.toString());
                params.put("Contact", contact);
                params.put("Address", address);
                params.put("regisDate",register_date );
                params.put("regisType", user_type);

     //           Toast.makeText(getApplicationContext(), requestHandler.sendPostRequest(URLs.URL_PATIENT_REGISTER, params), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), birthYear.toString(), Toast.LENGTH_SHORT).show();


                return requestHandler.sendPostRequest(URLs.URL_USER_REGISTER, params);

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

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute();

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

        switch (view.getId()) {
            case R.id.item_user_client:
                if (checked)
                    user_type = "C";
                break;
            case R.id.item_user_nurse:
                if (checked)
                    user_type = "N";
                break;
            case R.id.item_user_driver:
                if (checked)
                    user_type = "D";
        }
    }

    public void showRegisterDatePickerDialog(View view){
        DialogFragment fragment = new RegisterDatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

}
