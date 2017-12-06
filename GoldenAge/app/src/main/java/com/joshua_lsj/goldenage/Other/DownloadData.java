package com.joshua_lsj.goldenage.Other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.joshua_lsj.goldenage.Experiment.LoginActivity;
import com.joshua_lsj.goldenage.Experiment.MainActivity;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Objects.Medical;
import com.joshua_lsj.goldenage.Objects.Patient;
import com.joshua_lsj.goldenage.Objects.Schedule;
import com.joshua_lsj.goldenage.Objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by limsh on 11/30/2017.
 */

public class DownloadData extends AppCompatActivity{

    Context context;
    private BroadcastReceiver broadcastReceiver;
    private static final String BBR = "twert";



    public DownloadData(Context context){
        this.context = context;
    }

    public void downloadForAdmin(){
        downloadAllUsers();
        downloadAllClients();
        downloadAllPatients();
        downloadAllMedicalRecord();
        downloadAllDriverSchedule();
    }

    public void downloadForNurse(){
        downloadAllUsers();
        downloadAllPatients();
        downloadAllMedicalRecord();
    }

    public void downloadForDriver(String id){
        downloadDriverSchedule(id);
    }


    public void downloadForClient(String id){
        // patient id
        downloadPatient(id);
        downloadMedicalRecord(id);
    }

    public void sendBroadcast(){
        context.sendBroadcast(new Intent(MainActivity.DATA_SAVED_BROADCAST));
    }

    public void downloadAllUsers(){

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
                        //        sendBroadcast();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to retrieve users data from server", Toast.LENGTH_SHORT).show();
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

    public void downloadAllClients(){

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
                                            clientJson.getString("Patient_ID")
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
                        Toast.makeText(context, "Unable to retrieve clients data from server", Toast.LENGTH_SHORT).show();
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

    public void downloadAllPatients(){

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
                                            patientJson.getDouble("Deposit"),
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
                        Toast.makeText(context, "Unable to retrieve patients data from server", Toast.LENGTH_SHORT).show();
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
                     //       Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

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
                                            medicalJson.getDouble("Temperature"),
                                            medicalJson.getString("Description")
                                    );
                                    Queries queries  = new Queries(new DatabaseHelper(context));

                                   queries.insert(medical);
                                }
                                sendBroadcast();
                            }else{
                                sendBroadcast();
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
                        Toast.makeText(context, "Unable to retrieve medical records data from server", Toast.LENGTH_SHORT).show();
                        sendBroadcast();
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

    public void downloadPatient(final String id){

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_DATA,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            //           Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");
                                JSONObject PatientJson = array.getJSONObject(0);

                                //Creating a new user object
                                Patient patient = new Patient(
                                        PatientJson.getInt("ID"),
                                        PatientJson.getString("Name"),
                                        PatientJson.getString("IC"),
                                        PatientJson.getString("Contact"),
                                        PatientJson.getInt("BirthYear"),
                                        PatientJson.getString("Address"),
                                        PatientJson.getString("Gender"),
                                        PatientJson.getString("RegisDate"),
                                        PatientJson.getString("BloodType"),
                                        PatientJson.getString("Meals"),
                                        PatientJson.getString("Allergic"),
                                        PatientJson.getString("Sickness"),
                                        PatientJson.getDouble("Deposit"),
                                        PatientJson.getString("Image")
                                );

                                Queries queries = new Queries(new DatabaseHelper(context));

                                queries.insert(patient);
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
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //Put Patient data to parameters
                params.put("type", "Patient");
                params.put("id", id);
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(context).add(multipartRequest);
    }

    private void downloadMedicalRecord(final String id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.READ_DATA,
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
                                            medicalJson.getDouble("Temperature"),
                                            medicalJson.getString("Description")
                                    );
                                    Queries queries = new Queries(new DatabaseHelper(context));

                                    queries.insert(medical);

                                }
                                sendBroadcast();
                            }
                            else{
                                sendBroadcast();
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
                        sendBroadcast();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //Put Patient id to parameters to search for medical record
                params.put("type", "Medical");
                params.put("id", id);
                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(context).add(stringRequest);
    }

    //DOWNLOAD ALL DRIVER SCHEDULE
    public void downloadAllDriverSchedule(){

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
                                    JSONObject scheduleJason = array.getJSONObject(i);

                                    Schedule schedule = new Schedule(
                                            scheduleJason.getInt("ID"),
                                            scheduleJason.getString("Driver_Name"),
                                            scheduleJason.getString("Patient_Name"),
                                            scheduleJason.getString("Nurse_Name"),
                                            scheduleJason.getString("Location"),
                                            scheduleJason.getString("Description"),
                                            scheduleJason.getString("Date"),
                                            scheduleJason.getString("Time")
                                    );


                                    //Add schedule into database
                                    Queries queries = new Queries(new DatabaseHelper(context));
                                    queries.insert(schedule);

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

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("type", "Driver_Schedule"); //TODO: check type if correct o not
                return params;
            }

        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(context).add(multipartRequest);

    }


    //DOWNLOAD DRIVER SCHEDULE BASE ON ID
    private void downloadDriverSchedule(final String id){

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_DATA,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            //           Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")) {
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject scheduleJason = array.getJSONObject(i);

                                    Schedule schedule = new Schedule(
                                            scheduleJason.getInt("ID"),
                                            scheduleJason.getString("Driver_Name"),
                                            scheduleJason.getString("Patient_Name"),
                                            scheduleJason.getString("Nurse_Name"),
                                            scheduleJason.getString("Location"),
                                            scheduleJason.getString("Description"),
                                            scheduleJason.getString("Date"),
                                            scheduleJason.getString("Time")
                                    );

                                    //Add schedule into database
                                    Queries queries = new Queries(new DatabaseHelper(context));
                                    if (queries.insert(schedule) != 0)
                                        Toast.makeText(context, "Schedule Saved", Toast.LENGTH_SHORT).show();
                                }
                                sendBroadcast();
                            }
                            else{
                                sendBroadcast();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                            sendBroadcast();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        sendBroadcast();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //Put Patient data to parameters
                params.put("type", "Driver_Schedule");
                params.put("id", id);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(context).add(multipartRequest);
    }

}
