package com.joshua_lsj.goldenage.Volley;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.joshua_lsj.goldenage.Calender;
import com.joshua_lsj.goldenage.Experiment.Patient;
import com.joshua_lsj.goldenage.Experiment.RequestHandler;
import com.joshua_lsj.goldenage.Experiment.URLs;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.RegisterDatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 10/18/2017.
 */

public class AddPatientActivityVolley extends AppCompatActivity {

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

    private String sex;
    private String meals;

    private ImageView imageView;



    //Bitmap
    private Bitmap bitmap;




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
        etMargin = (EditText) findViewById(R.id.item_patient_margin);

        imageView = (ImageView) findViewById(R.id.item_image);

        //checking the permission
        //if the permission is not given we will open setting to add permission
        //else app will not open
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        //Adding Click Listener for imageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);

            }
        });




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


                //Upload Patient to server
               // registerPatient();

                //Testing to upload image
                RegisterPatient();


            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                imageView.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload image
           //     uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void RegisterPatient() {

        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();

        //Getting Patient Data     
        final String name = etName.getText().toString().trim();
        final String ic = etIC.getText().toString().trim();
        final int age = Integer.parseInt(etAge.getText().toString());
        final String address = etAddress.getText().toString().trim();
        final String contact = etContact.getText().toString().trim();
        final String allergic = etAllergic.getText().toString().trim();
        final String sickness = etSickness.getText().toString().trim();
        final String margin = etMargin.getText().toString().trim();
        final String register_date = etRegisterDate.getText().toString().trim();
        final String blood_type = String.valueOf(spinner.getSelectedItem()).trim();
        meals.trim();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_PATIENT_REGISTER,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(obj.getBoolean("error")==false)
                                finish();

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

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Calender calender = new Calender();
                Integer birthyear = calender.getCurrentYear() - age;

                Map<String, String> params = new HashMap<>();
                //Put Patient data to parameters
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
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


/*
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


                params.put("username", "LOL");
                params.put("email", "LOL");
                params.put("password", "LOL");
                params.put("gender", "LOL");

                Toast.makeText(getApplicationContext(), "llll", Toast.LENGTH_SHORT).show();

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
*/

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
