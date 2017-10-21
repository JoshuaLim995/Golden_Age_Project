package com.joshua_lsj.goldenage;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by limsh on 10/18/2017.
 */

public class AddPatientActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etIC;
    private EditText etBirthday;
    private EditText etRelativeIC;
    private EditText etRelativeName;
    private EditText etRegisterDate;
    private EditText etAddress;
    private EditText etContact;
    private EditText etAllergic;
    private EditText etSickness;
    private EditText etMargin;

    private RadioGroup rdSex;
    private RadioButton rdSex_male;
    private RadioButton rdSex_female;

    private CheckBox cbPork;
    private CheckBox cbFish;
    private CheckBox cbVege;

    private Spinner spinner;

    private Image photo;

    private String sex;
    private String meals;
    private String allergic;
    private String sickness;

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = (EditText) findViewById(R.id.item_patient_name);
        etIC = (EditText) findViewById(R.id.item_patient_ic);
        etBirthday = (EditText) findViewById(R.id.item_patient_birthday);
        etAddress = (EditText) findViewById(R.id.item_patient_address);
        etContact = (EditText) findViewById(R.id.item_patient_contact);
        etAllergic = (EditText) findViewById(R.id.item_patient_allergic);
        etSickness = (EditText) findViewById(R.id.item_patient_sickness);
        etRegisterDate = (EditText) findViewById(R.id.item_register_date);
        etRelativeName = (EditText) findViewById(R.id.item_patient_relative_name);
        etRelativeIC = (EditText) findViewById(R.id.item_patient_relative_ic);
        etMargin = (EditText) findViewById(R.id.item_patient_margin);

        rdSex = (RadioGroup) findViewById(R.id.sex);
        rdSex_male = (RadioButton) findViewById(R.id.sex_male);
        rdSex_female = (RadioButton) findViewById(R.id.sex_female);

        cbVege = (CheckBox) findViewById(R.id.cbVegetarian);
        cbFish = (CheckBox) findViewById(R.id.cbFish);
        cbPork = (CheckBox) findViewById(R.id.cbPork);

        spinner = (Spinner) findViewById(R.id.spinner_blood_type);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String ic = etIC.getText().toString();
                String birthday = etBirthday.getText().toString();
                String address = etAddress.getText().toString();
                String contact = etContact.getText().toString();
                allergic = etAllergic.getText().toString();
                sickness = etSickness.getText().toString();
                String rela_name = etRelativeName.getText().toString();
                String rela_ic = etRelativeIC.getText().toString();
                String margin = etMargin.getText().toString();
                String register_date = etRegisterDate.getText().toString();
                String blood_type = String.valueOf(spinner.getSelectedItem());

                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append(cbPork.isChecked() ? "Pork " : "");
                stringBuffer.append(cbFish.isChecked() ? "Fish " : "");
                stringBuffer.append(cbVege.isChecked() ? "Vegetarian" : "");

                meals = stringBuffer.toString();

                patient = new Patient("name", "ic", "birht", "sex", "blood", "addr", "con", "meal", "alle", "sick", "date", 20.06);
/*
                patient = new Patient(name, ic, birthday, sex, blood_type, address, contact, meals,
                        allergic, sickness, register_date,
                        Double.parseDouble(margin));
*/
                Queries queries = new Queries(new DatabaseHelper(getApplicationContext()));

                if(queries.insert(patient) != 0)
                    Toast.makeText(getApplicationContext(), "Patient created", Toast.LENGTH_SHORT).show();


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
