package com.joshua_lsj.goldenage.Experiment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Objects.User;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Other.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 10/29/2017.
 */

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;

    private String username, password;
    private final String NAV_USER = "nav_user";
    private final String NAV_PATIENT = "nav_patient";
    private final String NAV_PATIENT_INFO = "nav_patient_info";

    public final String CLIENT = "CLIENT";
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //CHECK IF USER LOGGED IN BEFORE OR NOT
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();

            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();
                    userLogin();
            }
        });
    }


    private void userLogin(){

        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.LOGIN_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){
                                JSONObject userJason = obj.getJSONObject("user");

                                //Creating a new user object
                                User user = new User(
                                        userJason.getInt("id"),
                                        userJason.getString("Name"),
                                        userJason.getString("regisType")
                                );



                                //STORE THE USER DATA IN SHARED PREFERENCE
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);


                                if(user.getRegisType().equals("A")){
                                    SharedPrefManager.getInstance(getApplicationContext()).setSelectedNav(NAV_USER);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Logged in as Admin", Toast.LENGTH_LONG).show();
                                }
                                else if(user.getRegisType().equals("N")){
                                    SharedPrefManager.getInstance(getApplicationContext()).setSelectedNav(NAV_PATIENT);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Logged in as Nurse", Toast.LENGTH_LONG).show();
                                }
                                else if(user.getRegisType().equals("C")){
                                    SharedPrefManager.getInstance(getApplicationContext()).setSelectedNav(NAV_PATIENT_INFO);
                                    getData(user.getID());
                                    Toast.makeText(getApplicationContext(), "Logged in as Client", Toast.LENGTH_LONG).show();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Currently other users cannot use", Toast.LENGTH_LONG).show();

                            }
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


                Map<String, String> params = new HashMap<>();
                //Put Patient data to parameters
                params.put("Name", username);
                params.put("Password", password);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(multipartRequest);
    }

    private void getData(final String id){


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_DATA,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            //           Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");
                                JSONObject clientJson = array.getJSONObject(0);

                                //Creating a new user object
                                Client client = new Client(
                                        clientJson.getInt("ID"),
                                        clientJson.getString("Name"),
                                        clientJson.getString("IC"),
                                        clientJson.getString("Contact"),
                                        clientJson.getInt("BirthYear"),
                                        clientJson.getString("Address"),
                                        clientJson.getString("Gender"),
                                        clientJson.getString("RegisDate"),
                                        clientJson.getInt("Patient_ID"),
                                        clientJson.getString("P_Name")
                                );

                                SharedPrefManager.getInstance(getApplicationContext()).setIdSharedPref(Integer.toString(client.getPatientID()));
                                SharedPrefManager.getInstance(getApplicationContext()).setPatientName(client.getPatientName());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra(CLIENT, client);
                                startActivity(intent);
                                finish();
                            }
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

                Map<String, String> params = new HashMap<>();
                //Put Patient data to parameters
                params.put("type", "Client");
                params.put("id", id);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(multipartRequest);
    }
}
