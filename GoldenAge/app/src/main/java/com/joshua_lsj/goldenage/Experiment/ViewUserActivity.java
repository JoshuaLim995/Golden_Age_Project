package com.joshua_lsj.goldenage.Experiment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Volley.DeleteHelper;
import com.joshua_lsj.goldenage.Volley.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 11/5/2017.
 */

public class ViewUserActivity extends AppCompatActivity {

    private User user;
    private DeleteHelper deleteHelper;

    private TextView tvName, tvId, tvRegisType, tvContact, tvIc, tvGender, tvAge, tvAddress, tvRegisDate;
    private LinearLayout layPatientID, layPatientName;

    public final static String USER = "UPDATE_USER";

    private String id = "0";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
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

    private void getData(){

    //    id = getIntent().getStringExtra(ListViewUserFragment.USER);
        id = SharedPrefManager.getInstance(this).getKeySelectedId();
    //    Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_DATA,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                 //           Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){
                  //              Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");
                                JSONObject userJason = array.getJSONObject(0);

                                //Creating a new user object
                                user = new User(
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

                                intialize();
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
                params.put("type", "User");
                params.put("id", id);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(multipartRequest);
    }

    public void intialize(){
        TextView tvDetail = findViewById(R.id.item_detail);

        tvName = (TextView) findViewById(R.id.item_name);
        tvId = (TextView) findViewById(R.id.item_id);
        tvRegisType = (TextView) findViewById(R.id.item_register_type);
        tvContact = (TextView) findViewById(R.id.item_contact);
        tvIc = (TextView) findViewById(R.id.item_ic);
        tvGender = (TextView) findViewById(R.id.item_gender);
        tvAge = (TextView) findViewById(R.id.item_age);
        tvRegisDate = (TextView) findViewById(R.id.item_register_date);
        layPatientID = findViewById(R.id.layout_patient_id);
        layPatientName =  findViewById(R.id.layout_patient_name);
        tvAddress = findViewById(R.id.item_address);

        tvDetail.setText("User Details");
        if(user != null){
            layPatientID.setVisibility(View.GONE);
            layPatientName.setVisibility(View.GONE);
            tvName.setText(user.getName());
            tvId.setText(user.getID());
            tvRegisType.setText(getRegisterType(user.getRegisType()));
            tvContact.setText(user.getContact());
            tvIc.setText(user.getIc());
            tvGender.setText(user.getGender());
            tvAge.setText(user.getAge());
            tvRegisDate.setText(user.getRegisDate());
            tvAddress.setText(user.getAddress());
        }
    }

    public String getRegisterType(String type){
        String getType;
        switch (type){
            case "A":
                getType = "Admin";
                break;
            case "N":
                getType = "Nurse";
                break;
            case "D":
                getType = "Driver";
                break;
            default:
                getType = "INVALID";
                break;
        }
        return getType;
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
            Intent intent = new Intent(this, AddUserActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(USER, user);
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
                        deleteHelper.Delete("User", user.getID());

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete User?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
    }
}