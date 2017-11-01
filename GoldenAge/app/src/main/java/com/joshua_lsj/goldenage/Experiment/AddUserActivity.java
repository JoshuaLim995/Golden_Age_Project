package com.joshua_lsj.goldenage.Experiment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
 * Created by limsh on 10/29/2017.
 */



public class AddUserActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etIC;
    private EditText etBirthday;
    private EditText etRegisterDate;
    private EditText etAddress;
    private EditText etContact;

    private String sex;
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
        etBirthday = (TextInputEditText) findViewById(R.id.item_birthday);
        etRegisterDate = (TextInputEditText) findViewById(R.id.item_register_date);
        etAddress = (TextInputEditText) findViewById(R.id.item_address);
        etContact = (TextInputEditText) findViewById(R.id.item_contact);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//USED TO ADD ADMIN, NURSE, DRIVER
                registerUser();

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void registerUser(){
       final String name = etName.getText().toString().trim();
        final   String ic = etIC.getText().toString().trim();
        final   String birthdate = etBirthday.getText().toString();
  //      final String birthdate = "1995-10-20".trim();
        final    String address = etAddress.getText().toString().trim();
        final     String contact = etContact.getText().toString().trim();
        final     String register_date = etRegisterDate.getText().toString();
 //       final String register_date = "2017-12-23".trim();


        class RegisterUser extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("Name", name);
                params.put("IC", ic);
                params.put("Gender", sex);
                params.put("Birthdate", birthdate);
                params.put("Contact", contact);
                params.put("Addr", address);
                params.put("regisDate",register_date );
                params.put("regisType", user_type);

     //           Toast.makeText(getApplicationContext(), requestHandler.sendPostRequest(URLs.URL_PATIENT_REGISTER, params), Toast.LENGTH_SHORT).show();


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
            case R.id.sex_male:
                if (checked)
                    sex = "M";
                break;
            case R.id.sex_female:
                if (checked)
                    sex = "F";
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

    public void showBirthDatePickerDialog(View view){
        DialogFragment fragment = new BirthDatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }
}
