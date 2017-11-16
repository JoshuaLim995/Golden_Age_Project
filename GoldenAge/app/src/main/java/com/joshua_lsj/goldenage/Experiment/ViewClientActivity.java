package com.joshua_lsj.goldenage.Experiment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.R;

/**
 * Created by limsh on 11/9/2017.
 */

public class ViewClientActivity extends AppCompatActivity {

    private Client client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user); //Share view User and Client
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intialize();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void intialize(){

        client = (Client) getIntent().getSerializableExtra(ListViewClientFragment.CLIENT);

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
        tvId.setText(client.getId().toString());
        tvRegisType.setText(client.getRegister_type());
        tvContact.setText(client.getContact());
        tvIc.setText(client.getIc());
        tvGender.setText(client.getGender());
        tvAge.setText(Integer.toString(client.getAge())); //GET USER AGE FROM THE BIRTHDATE
        tvRegisDate.setText(client.getRegister_date());

        tvPatientID.setText(Integer.toString(client.getPatientID())); //GET PATIENT ID FROM USER
        if(client.getPatientName() != null)
            tvPatientName.setText(client.getPatientName()); //GET PATIENT NAME FROM USER

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}