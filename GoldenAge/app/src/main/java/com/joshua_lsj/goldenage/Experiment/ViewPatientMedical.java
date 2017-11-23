package com.joshua_lsj.goldenage.Experiment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joshua_lsj.goldenage.Calender;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.PatientMedicalActivity;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Volley.AddPatientActivityVolley;
import com.joshua_lsj.goldenage.Volley.DeleteHelper;
import com.joshua_lsj.goldenage.Volley.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 10/21/2017.
 */

public class ViewPatientMedical extends AppCompatActivity {

    public static final String MEDICAL = "Medical";

    private Medical medical;
    private ArrayList<Medical> medicalList;
    private DeleteHelper deleteHelper;

    private String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
        deleteHelper = new DeleteHelper(this);

        

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getData();
    }

    private void getData(){

        id = SharedPrefManager.getInstance(this).getKeySelectedId();
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.GET_MEDICAL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            

                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");
                                
                                for (int i = 0; i < array.length(); i++) {
                                JSONObject MedicalJson = array.getJSONObject(i);
                                
                                //Creating a new user object
                                medicalList.add(new Medical(
                                        MedicalJson.getString("Date"),
                                        MedicalJson.getString("Patient_ID"),
                                        MedicalJson.getString("Nurse_ID"),
                                        MedicalJson.getDouble("Blood_Pressure"),
                                        MedicalJson.getDouble("Sugar_Level"),
                                        MedicalJson.getDouble("Heart_Rate"),
                                        MedicalJson.getDouble("Temperature")
                                ));
                                }
                                 //creating adapter object and setting it to recyclerview
                            MedicalAdapter adapter = new MedicalAdapter(medicalList, getActivity());
                            listView.setAdapter(adapter);
                               
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
                params.put("id", id);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(multipartRequest);
    }


}
