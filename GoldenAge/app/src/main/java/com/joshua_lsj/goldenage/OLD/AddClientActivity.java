package com.joshua_lsj.goldenage.OLD;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.joshua_lsj.goldenage.Client;
import com.joshua_lsj.goldenage.DatabaseHelper;
import com.joshua_lsj.goldenage.Queries;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.RegisterDatePickerFragment;

/**
 * Created by limsh on 10/19/2017.
 */

public class AddClientActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etIC;
    private EditText etBirthday;
    private EditText etRegisterDate;
    private EditText etAddress;
    private EditText etContact;
    private EditText etOther;

    private String sex;
    private String relation;

    private Client client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        etName = (EditText) findViewById(R.id.item_name);
        etIC = (EditText) findViewById(R.id.item_ic);
        etBirthday = (EditText) findViewById(R.id.item_birthday);
        etRegisterDate = (EditText) findViewById(R.id.item_register_date);
        etAddress = (EditText) findViewById(R.id.item_address);
        etContact = (EditText) findViewById(R.id.item_contact);
        etOther = (EditText) findViewById(R.id.item_other);

        etOther.setVisibility(View.INVISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String ic = etIC.getText().toString();
                String birthday = etBirthday.getText().toString();
                String address = etAddress.getText().toString();
                String contact = etContact.getText().toString();
                String register_date = etRegisterDate.getText().toString();

                client = new Client(name, ic, birthday, sex, address, contact, register_date, relation);

                //Queries here
                Queries queries = new Queries(new DatabaseHelper(getApplicationContext()));

                if(queries.insert(client) != 0)
                    Toast.makeText(getApplicationContext(), "Client created", Toast.LENGTH_SHORT).show();

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();


            switch (view.getId()) {
                case R.id.sex_male:
                    if (checked)
                        sex = "M";
                    break;
                case R.id.sex_female:
                    if (checked)
                        sex = "F";
                    break;
            }

            switch (view.getId()) {
                case R.id.relation1:
                    if (checked)
                        relation = "Children";
                    etOther.setVisibility(View.INVISIBLE);
                    break;
                case R.id.relation2:
                    if (checked)
                        relation = "Relative";
                    etOther.setVisibility(View.INVISIBLE);
                    break;
                case R.id.relation3:
                    if (checked)
                        etOther.setVisibility(View.VISIBLE);
                    break;
            }

    }

    public void showDatePickerDialog(View view){
        DialogFragment fragment = new RegisterDatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }
}
