package com.joshua_lsj.goldenage.Experiment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Other.DeleteHelper;
import com.joshua_lsj.goldenage.Other.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 11/9/2017.
 */

public class ViewClientActivity extends AppCompatActivity {

    private Client client;
    private DeleteHelper deleteHelper;

    public final static String CLIENT = "UPDATE_CLIENT";

    private String id = "0";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user); //Share view User and Client
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deleteHelper = new DeleteHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getData();
    }

    protected void intialize(){

        TextView tvDetail = findViewById(R.id.item_detail);
        TextView tvName = (TextView) findViewById(R.id.item_name);
        TextView tvId = (TextView) findViewById(R.id.item_id);
        TextView tvRegisType = (TextView) findViewById(R.id.item_register_type);
        TextView tvContact = (TextView) findViewById(R.id.item_contact);
        TextView tvIc = (TextView) findViewById(R.id.item_ic);
        TextView tvGender = (TextView) findViewById(R.id.item_gender);
        TextView tvAge = (TextView) findViewById(R.id.item_age);
        TextView tvRegisDate = (TextView) findViewById(R.id.item_register_date);
        TextView tvPatientID = (TextView) findViewById(R.id.item_patient_id);
        TextView tvPatientName = (TextView) findViewById(R.id.item_patient_name);

        tvDetail.setText("Client Details");
        if(client != null){
            tvName.setText(client.getName());
            tvId.setText(client.getID());
            tvRegisType.setText(client.getRegisType());
            tvContact.setText(client.getContact());
            tvIc.setText(client.getIc());
            tvGender.setText(client.getGender());
            tvAge.setText(client.getAge()); //GET USER AGE FROM THE BIRTHDATE
            tvRegisDate.setText(client.getRegisDate());

            tvPatientID.setText(Integer.toString(client.getPatientID())); //GET PATIENT ID FROM USER
            tvPatientName.setText(client.getPatientName()); //GET PATIENT NAME FROM USER

        }
    }

    private void getData(){

        //    id = getIntent().getStringExtra(ListViewUserFragment.USER);
        id = SharedPrefManager.getInstance(this).getKeySelectedId();
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_DATA,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");
                                JSONObject clientJson = array.getJSONObject(0);

                                //Creating a new user object
                                client = new Client(
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

                                intialize();
                            }
                        } catch (JSONException e) {
                        //    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(this, AddUpdateClientActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(CLIENT, client);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_delete){

            Delete();
        }

        return super.onOptionsItemSelected(item);
    }

    private void Delete(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteHelper.Delete("Client", client.getID());

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Client?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
    }
}