package com.joshua_lsj.goldenage.Experiment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.joshua_lsj.goldenage.R;

/**
 * Created by limsh on 11/5/2017.
 */

public class ViewUserActivity extends AppCompatActivity {

    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void intialize(){

        user = (User) getIntent().getSerializableExtra(ListViewUserFragment.USER);

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

        tvName.setText(user.getName());
        tvId.setText(user.getID().toString());
        tvRegisType.setText(user.getRegisType());
        tvContact.setText(user.getContact());
        tvIc.setText(user.getIc());
        tvGender.setText(user.getGender());
    //    tvAge.setText(user.get()); //GET USER AGE FROM THE BIRTHDATE
        tvBirthdate.setText(user.getAge());
        tvRegisDate.setText(user.getRegisDate());
    //    tvPatientID.setText(user.getName()); //GET PATIENT ID FROM USER
     //   tvPatientName.setText(user.getName()); //GET PATIENT NAME FROM USER

    }
}
