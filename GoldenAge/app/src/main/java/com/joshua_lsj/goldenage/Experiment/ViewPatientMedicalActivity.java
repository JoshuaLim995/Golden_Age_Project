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

public class ViewPatientMedicalActivity extends AppCompatActivity {

    public static final String MEDICAL = "Medical";

    private Medical medical;
    private Patient patient = null;
    private ArrayList<Medical> medicalList;
    private DeleteHelper deleteHelper;

    private String id;
    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_medical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListViewPatientMedicalFragment())
                        .commit();
	getSupportActionBar().setTitle("Patient Medical");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}