package com.joshua_lsj.goldenage.Experiment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.joshua_lsj.goldenage.R;

/**
 * Created by limsh on 11/9/2017.
 */

public class ViewClientActivity extends AppCompatActivity {

    private Client client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void intialize(){

        client = (Client) getIntent().getSerializableExtra(ListViewClientFragment.USER);

        TextView tvName = (TextView) findViewById(R.id.item_name);
        TextView tvId = (TextView) findViewById(R.id.item_id);
        TextView tvRegisType = (TextView) findViewById(R.id.item_register_type);
        TextView tvContact = (TextView) findViewById(R.id.item_contact);
        TextView tvIc = (TextView) findViewById(R.id.item_ic);
        TextView tvGender = (TextView) findViewById(R.id.item_gender);
        TextView tvAge = (TextView) findViewById(R.id.item_age);
        TextView tvBirthdate = (TextView) findViewById(R.id.item_birthdate);
        TextView tvRegisDate = (TextView) findViewById(R.id.item_register_date);
        TextView tvPatientID = (TextView) findViewById(R.id.item_patient_id);
        TextView tvPatientName = (TextView) findViewById(R.id.item_patient_name);

        tvName.setText(client.getName());
        tvId.setText(client.getID());
        tvRegisType.setText(client.getRegisType());
        tvContact.setText(client.getContact());
        tvIc.setText(client.getIc());
        tvGender.setText(client.getGender());
    //    tvAge.setText(client.get()); //GET USER AGE FROM THE BIRTHDATE
        tvBirthdate.setText(client.getBirthdate());
        tvRegisDate.setText(client.getRegisDate());
    //    tvPatientID.setText(client.getName()); //GET PATIENT ID FROM USER
     //   tvPatientName.setText(client.getName()); //GET PATIENT NAME FROM USER

    }
}