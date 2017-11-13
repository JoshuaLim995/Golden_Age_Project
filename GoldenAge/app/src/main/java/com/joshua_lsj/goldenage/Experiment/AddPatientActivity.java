package com.joshua_lsj.goldenage.Experiment;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.joshua_lsj.goldenage.Calender;
import com.joshua_lsj.goldenage.RegisterDatePickerFragment;
import com.joshua_lsj.goldenage.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by limsh on 10/18/2017.
 */

public class AddPatientActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etIC;
    private EditText etAge;
    private EditText etRelativeIC;
    private EditText etRelativeName;
    private EditText etRegisterDate;
    private EditText etAddress;
    private EditText etContact;
    private EditText etAllergic;
    private EditText etSickness;
    private EditText etMargin;


    private CheckBox cbPork;
    private CheckBox cbFish;
    private CheckBox cbVege;

    private Spinner spinner;

    private Image photo;

    private String sex;
    private String meals;


    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = (EditText) findViewById(R.id.item_patient_name);
        etIC = (EditText) findViewById(R.id.item_patient_ic);
        etAge = (EditText) findViewById(R.id.item_age);
        etAddress = (EditText) findViewById(R.id.item_patient_address);
        etContact = (EditText) findViewById(R.id.item_patient_contact);
        etAllergic = (EditText) findViewById(R.id.item_patient_allergic);
        etSickness = (EditText) findViewById(R.id.item_patient_sickness);
        etRegisterDate = (EditText) findViewById(R.id.item_register_date);
        etRelativeName = (EditText) findViewById(R.id.item_patient_relative_name);
        etRelativeIC = (EditText) findViewById(R.id.item_patient_relative_ic);
        etMargin = (EditText) findViewById(R.id.item_patient_margin);

        RadioButton rbInitial = (RadioButton)findViewById(R.id.gender_male);
        rbInitial.setChecked(true);

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


                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append(cbPork.isChecked() ? "Pork " : "");
                stringBuffer.append(cbFish.isChecked() ? "Fish " : "");
                stringBuffer.append(cbVege.isChecked() ? "Vegetarian" : "");

                meals = stringBuffer.toString();

                registerPatient();

                finish();

//                patient = new Patient("name", "ic", "birht", "sex", "blood", "addr", "con", "meal", "alle", "sick", "date", 20.06);
/*
                patient = new Patient(name, ic, birthday, sex, blood_type, address, contact, meals,
                        allergic, sickness, register_date,
                        Double.parseDouble(margin));
*/
//                Queries queries = new Queries(new DatabaseHelper(getApplicationContext()));

   //             if(queries.insert(patient) != 0)
  //                  Toast.makeText(getApplicationContext(), "Patient created", Toast.LENGTH_SHORT).show();


            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void registerPatient(){
        final String name = etName.getText().toString().trim();
        final String ic = etIC.getText().toString().trim();
        final     int age = Integer.parseInt(etAge.getText().toString());
        final String address = etAddress.getText().toString().trim();
        final      String contact = etContact.getText().toString().trim();
        final String allergic = etAllergic.getText().toString().trim();
        final String sickness = etSickness.getText().toString().trim();
        final String rela_name = etRelativeName.getText().toString().trim();
      final String rela_ic = etRelativeIC.getText().toString().trim();
        final String margin = etMargin.getText().toString().trim();
        final String register_date = etRegisterDate.getText().toString().trim();
        final String blood_type = String.valueOf(spinner.getSelectedItem()).trim();

  //      sex.trim();
        meals.trim();

  //      final String LOL = "testing";
  //      final String gen = "gay";
  //      final String blood = "AB-";

        class RegisterPatient extends AsyncTask<Void, Void, String>{
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                Calender calender = new Calender();
                Integer birthyear = calender.getCurrentYear() - age;

                params.put("name", name);
                params.put("ic", ic);
                params.put("Birthyear", birthyear.toString());
                params.put("gender", sex);
                params.put("bloodType", blood_type);
                params.put("address", address);
                params.put("contact", contact);
                params.put("meals", meals );
                params.put("allergic",allergic );
                params.put("sickness", sickness);
                params.put("regType", "P");
                params.put("regDate",register_date );
                params.put("margin", margin);

/*
                params.put("username", "LOL");
                params.put("email", "LOL");
                params.put("password", "LOL");
                params.put("gender", "LOL");
*/
           //     Toast.makeText(getApplicationContext(), "llll", Toast.LENGTH_SHORT).show();

                return requestHandler.sendPostRequest(URLs.URL_PATIENT_REGISTER, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }

        RegisterPatient ru = new RegisterPatient();
        ru.execute();

    }


    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.gender_male:
                if (checked)
                    sex = "M";
                break;
            case R.id.gender_female:
                if (checked)
                    sex = "F";
        }
    }

    public void showDatePickerDialog(View view){
        DialogFragment fragment = new RegisterDatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

}
