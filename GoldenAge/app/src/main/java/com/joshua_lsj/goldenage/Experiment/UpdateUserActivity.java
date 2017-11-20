package com.joshua_lsj.goldenage.Experiment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Calender;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.RegisterDatePickerFragment;
import com.joshua_lsj.goldenage.Volley.ListViewUserFragmentVolley;
import com.joshua_lsj.goldenage.Volley.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 10/29/2017.
 */



public class UpdateUserActivity extends AppCompatActivity {

    private TextInputLayout til_name, til_ic, til_age, til_registerDate, til_address, til_contact;
    private TextInputEditText etName, etIC, etAge, etRegisterDate, etAddress, etContact;

    private String gender;
    private String user_type;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
        fillEditText();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkEditText())
                    updateUser();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initialize(){
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

        Calender calender = new Calender();
        etRegisterDate.setText(calender.getToday());

        RadioButton radUserType = findViewById(R.id.item_user_admin);
        radUserType.setChecked(true);
        user_type = "A";

        RadioButton radGender = findViewById(R.id.gender_male);
        radGender.setChecked(true);
        gender = "M";
    }

    private void fillEditText(){
        user = (User) getIntent().getSerializableExtra(ViewUserActivity.USER);

        etName.setText(user.getName());
        etIC.setText(user.getIc());
        etAge.setText(user.getAge());
        etRegisterDate.setText(user.getRegisDate());
        etAddress.setText(user.getAddress());
        etContact.setText(user.getContact());
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

        return validate;
    }

    private void updateUser(){
       final String name = etName.getText().toString().trim();
        final   String ic = etIC.getText().toString().trim();
        final   int age = Integer.parseInt(etAge.getText().toString());
        final    String address = etAddress.getText().toString().trim();
        final     String contact = etContact.getText().toString().trim();
        final     String register_date = etRegisterDate.getText().toString();



        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.UPDATE_USER_URL,
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


                Integer birthYear = calender.getCurrentYear() - age;

                params.put("Name", name);
                params.put("IC", ic);
                params.put("Gender", gender);
                params.put("Birthyear", birthYear.toString());
                params.put("Contact", contact);
                params.put("Address", address);
                params.put("regisDate",register_date );
                params.put("regisType", user_type);
                params.put("id", user.getID());
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

        switch (view.getId()) {
            case R.id.item_user_admin:
                if (checked)
                    user_type = "A";
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
