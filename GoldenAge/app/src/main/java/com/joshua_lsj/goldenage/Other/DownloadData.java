package com.joshua_lsj.goldenage.Other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Objects.Medical;
import com.joshua_lsj.goldenage.Objects.Patient;
import com.joshua_lsj.goldenage.Objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 11/30/2017.
 */

public class DownloadData {

    Context context;

    public DownloadData(Context context){
        this.context = context;

    }

    public void downloadForAdmin(){
        downloadUsers();
        downloadClients();
        downloadPatients();
        downloadAllMedicalRecord();
    }

    public void downloadForNurse(){
        downloadPatients();
        downloadAllMedicalRecord();
    }

    public void downloadForClient(){
        
    }

    public void downloadUsers(){

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_ALL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            if(!obj.getBoolean("error")) {

                                //converting the string to json array object
                                JSONArray array = obj.getJSONArray("result");

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting user object from json array
                                    JSONObject userJason = array.getJSONObject(i);

                                    //adding the product to product list
//User(int id, String name, String ic, string contact, int birthyear, String address, String gender, String regisDate, String regisType)
                                    User user = new User(
                                            userJason.getInt("ID"),
                                            userJason.getString("Name"),
                                            userJason.getString("IC"),
                                            userJason.getString("Contact"),
                                            userJason.getInt("BirthYear"),
                                            userJason.getString("Address"),
                                            userJason.getString("Gender"),
                                            userJason.getString("RegisDate"),
                                            userJason.getString("RegisType")
                                    );


                                    //Add user into database
                                    Queries queries = new Queries(new DatabaseHelper(context));
                                    queries.insert(user);
                                }
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to retrieve data from server", Toast.LENGTH_SHORT).show();
                       // DisplayUserList();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("type", "User");
                return params;
            }

        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(context).add(multipartRequest);


    }

    public void downloadClients(){

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_ALL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            if(!obj.getBoolean("error")) {
                                //converting the string to json array object
                                JSONArray array = obj.getJSONArray("result");

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting user object from json array
                                    JSONObject clientJson = array.getJSONObject(i);
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
                                            clientJson.getInt("Patient_ID")
                                    );

                                    //Save client into local
                                    Queries queries = new Queries(new DatabaseHelper(context));

                                    queries.insert(client);
                                    //    if (queries.insert(client) != 0)
                                    //         Toast.makeText(getContext(), "Client Saved", Toast.LENGTH_SHORT).show();

                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to retrieve data from server", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("type", "Client");
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(context).add(multipartRequest);
    }

    public void downloadPatients(){

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_ALL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            if(!obj.getBoolean("error")) {
                                //converting the string to json array object
                                JSONArray array = obj.getJSONArray("result");

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting user object from json array
                                    JSONObject patientJson = array.getJSONObject(i);

                                    Patient patient = new Patient(
                                            patientJson.getInt("ID"),
                                            patientJson.getString("Name"),
                                            patientJson.getString("IC"),
                                            patientJson.getString("Contact"),
                                            patientJson.getInt("BirthYear"),
                                            patientJson.getString("Address"),
                                            patientJson.getString("Gender"),
                                            patientJson.getString("RegisDate"),
                                            patientJson.getString("BloodType"),
                                            patientJson.getString("Meals"),
                                            patientJson.getString("Allergic"),
                                            patientJson.getString("Sickness"),
                                            patientJson.getDouble("Margin"),
                                            patientJson.getString("Image")
                                    );

                                    //Save Patient into local
                                    Queries queries = new Queries(new DatabaseHelper(context));
                                    queries.insert(patient);
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to retrieve data from server", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("type", "Patient");
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(context).add(multipartRequest);
    }

    public void downloadAllMedicalRecord(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.READ_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response));
                            //    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){

                                JSONArray array = obj.getJSONArray("result");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject medicalJson = array.getJSONObject(i);

                                    //Creating a new user object
                                    Medical medical = new Medical(
                                            medicalJson.getInt("ID"),
                                            medicalJson.getString("Date"),
                                            medicalJson.getString("Patient_ID"),
                                            medicalJson.getString("Nurse_ID"),
                                            medicalJson.getDouble("Blood_Pressure"),
                                            medicalJson.getDouble("Sugar_Level"),
                                            medicalJson.getDouble("Heart_Rate"),
                                            medicalJson.getDouble("Temperature")
                                    );
                                    Queries queries  = new Queries(new DatabaseHelper(context));

                                   queries.insert(medical);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to retrieve data from server", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //Put Patient id to parameters to search for medical record
                params.put("type", "Medical");
                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(context).add(stringRequest);
    }



}
