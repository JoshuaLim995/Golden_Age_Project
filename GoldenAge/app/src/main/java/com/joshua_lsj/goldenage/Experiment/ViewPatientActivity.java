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

public class ViewPatientActivity extends AppCompatActivity {

    public static final String PATIENT = "Patient";

    private Patient patient;
    private DeleteHelper deleteHelper;

    private String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.item_image);
        Glide.with(this).load(R.drawable.user_icon).apply(RequestOptions.circleCropTransform()).into(imageView);

        deleteHelper = new DeleteHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), PatientMedicalActivity.class);
                intent.putExtra(PATIENT, patient);
                startActivity(intent);
            }
        });

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


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.GET_PATIENT,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            //           Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");
                                JSONObject PatientJson = array.getJSONObject(0);

                                //Creating a new user object
                                patient = new Patient(
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
                                        PatientJson.getDouble("Margin"),
                                        PatientJson.getString("Image")
                                );

                                Initialize();
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


    public void Initialize() {


        TextView tvId = (TextView) findViewById(R.id.view_patient_id);
        TextView tvName = (TextView) findViewById(R.id.view_patient_name);
        TextView tvContact = (TextView) findViewById(R.id.view_patient_contact);
        TextView tvEmergencyContact = (TextView) findViewById(R.id.view_patient_emergency_contact);
        TextView tvIC = (TextView) findViewById(R.id.view_patient_ic);
        TextView tvGender = (TextView) findViewById(R.id.view_patient_sex);
        TextView tvAge = (TextView) findViewById(R.id.view_patient_age);
        TextView tvBirthday = (TextView) findViewById(R.id.view_patient_birthday);
        TextView tvBlood = (TextView) findViewById(R.id.view_patient_blood_type);
        TextView tvSick = (TextView) findViewById(R.id.view_patient_sickness);
        TextView tvMeal = (TextView) findViewById(R.id.view_patient_meals);
        TextView tvAllergic = (TextView) findViewById(R.id.view_patient_allergic);
        TextView tvRegDate = (TextView) findViewById(R.id.view_patient_reg_date);
        TextView tvRegType = (TextView) findViewById(R.id.view_patient_reg_type);
        TextView tvMargin = (TextView) findViewById(R.id.view_patient_margin);

        ImageView imageView = (ImageView) findViewById(R.id.item_image);


        if(patient != null){
            tvId.setText(patient.getID());
            tvName.setText(patient.getName());
            tvContact.setText(patient.getContact());
            tvIC.setText(patient.getIc());
            tvGender.setText(patient.getGender());
            tvAge.setText(patient.getAge());
            tvBlood.setText(patient.getBlood_type());
            tvSick.setText(patient.getSickness());
            tvMeal.setText(patient.getMeals());
            tvAllergic.setText(patient.getAllergic());
            tvRegDate.setText(patient.getRegisDate());
            tvRegType.setText(patient.getRegisType());
            tvMargin.setText(patient.getMargin().toString());

            if(!patient.getImageName().equals("null"))
                Glide.with(this).load(URLs.URL_IMAGE_FILE + patient.getImageName()).apply(RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(imageView);
            else
                Glide.with(this).load(R.drawable.user_icon).apply(RequestOptions.circleCropTransform()).into(imageView);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            Intent intent = new Intent(getApplicationContext(), AddPatientActivityVolley.class);
            intent.putExtra(PATIENT, patient);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_delete){

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            deleteHelper.Delete(URLs.DELETE_PATIENT_URL, patient.getID());

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Delete User?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}