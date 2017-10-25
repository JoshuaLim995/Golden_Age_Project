package com.joshua_lsj.goldenage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by limsh on 10/22/2017.
 */

public class AddDriverActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etIC;
    private EditText etBirthday;
    private EditText etRegisterDate;
    private EditText etAddress;
    private EditText etContact;

    private String sex;

    private Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nurse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = (EditText) findViewById(R.id.item_name);
        etIC = (EditText) findViewById(R.id.item_ic);
        etBirthday = (EditText) findViewById(R.id.item_birthday);
        etRegisterDate = (EditText) findViewById(R.id.item_register_date);
        etAddress = (EditText) findViewById(R.id.item_address);
        etContact = (EditText) findViewById(R.id.item_contact);

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

                driver = new Driver(name, ic, birthday, sex, address, contact, register_date);

                //Queries here
                Queries queries = new Queries(new DatabaseHelper(getApplicationContext()));

                if(queries.insert(driver) != 0)
                    Toast.makeText(getApplicationContext(), "Driver created", Toast.LENGTH_SHORT).show();

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
        }
    }

    public void showDatePickerDialog(View view){
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }
}

