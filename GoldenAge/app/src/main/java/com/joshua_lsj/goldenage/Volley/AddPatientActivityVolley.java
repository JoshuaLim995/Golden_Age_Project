package com.joshua_lsj.goldenage.Volley;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joshua_lsj.goldenage.Calender;
import com.joshua_lsj.goldenage.Experiment.Patient;
import com.joshua_lsj.goldenage.Experiment.URLs;
import com.joshua_lsj.goldenage.Experiment.ViewPatientActivity;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 10/18/2017.
 */

public class AddPatientActivityVolley extends AppCompatActivity {

    private TextInputLayout til_name, til_ic, til_age, til_registerDate, til_address, til_contact, til_allergic, til_sickness, til_margin;

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

    private String gender;
    private String meals;

    private ImageView imageView;



    //Bitmap
    private Bitmap bitmap;

    public  static final int RequestPermissionCode  = 1 ;

    private String userChoosenTask = "";

    private Patient patient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        patient = (Patient) getIntent().getSerializableExtra(ViewPatientActivity.PATIENT);




        initialize();

        if(patient != null)
            fillEditText();

        //Adding Click Listener for imageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });

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
                if(checkEditText()){
                    if(patient == null)
                        RegisterPatient(URLs.URL_PATIENT_REGISTER);
                    else
                        RegisterPatient(URLs.UPDATE_PATIENT_URL);
                }


            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void initialize(){

        til_name = (TextInputLayout) findViewById(R.id.TIL_Name);
        til_ic = (TextInputLayout) findViewById(R.id.TIL_IC);
        til_age = (TextInputLayout) findViewById(R.id.TIL_Age);
        til_contact = (TextInputLayout) findViewById(R.id.TIL_Contact);
        til_address = (TextInputLayout) findViewById(R.id.TIL_Address);
        til_registerDate = (TextInputLayout) findViewById(R.id.TIL_RegisterDate);
        til_allergic = (TextInputLayout) findViewById(R.id.TIL_Allergic);
        til_sickness = (TextInputLayout) findViewById(R.id.TIL_Sickness);
        til_margin = (TextInputLayout) findViewById(R.id.TIL_Margin);

        etName = (EditText) findViewById(R.id.item_name);
        etIC = (EditText) findViewById(R.id.item_ic);
        etAge = (EditText) findViewById(R.id.item_age);
        etAddress = (EditText) findViewById(R.id.item_address);
        etContact = (EditText) findViewById(R.id.item_contact);
        etAllergic = (EditText) findViewById(R.id.item_allergic);
        etSickness = (EditText) findViewById(R.id.item_sickness);
        etRegisterDate = (EditText) findViewById(R.id.item_register_date);
        etMargin = (EditText) findViewById(R.id.item_margin);

        cbVege = (CheckBox) findViewById(R.id.cbVegetarian);
        cbFish = (CheckBox) findViewById(R.id.cbFish);
        cbPork = (CheckBox) findViewById(R.id.cbPork);

        spinner = (Spinner) findViewById(R.id.spinner_blood_type);

        imageView = (ImageView) findViewById(R.id.item_image);
        Glide.with(this).load(R.drawable.user_icon).apply(RequestOptions.circleCropTransform()).into(imageView);

        Calender calender = new Calender();
        etRegisterDate.setText(calender.getToday());

        //Setting default radio check
        RadioButton rbInitial = (RadioButton)findViewById(R.id.gender_male);
        rbInitial.setChecked(true);
        gender = "M";
    }

    private void fillEditText(){
        etName.setText(patient.getName());
        etIC.setText(patient.getIc());
        etAge.setText(patient.getAge());
        etRegisterDate.setText(patient.getRegisDate());
        etAddress.setText(patient.getAddress());
        etContact.setText(patient.getContact());
        etAllergic.setText(patient.getAllergic());
        etSickness.setText(patient.getSickness());
        etMargin.setText(patient.getMargin().toString());

        if(!patient.getImageName().equals("null"))
            Glide.with(this).load(URLs.URL_IMAGE_FILE + patient.getImageName()).apply(RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(imageView);
        else
            Glide.with(this).load(R.drawable.user_icon).apply(RequestOptions.circleCropTransform()).into(imageView);
    }



    private boolean checkEditText(){
        boolean validate = false;

        if(!etName.getText().toString().isEmpty()){
            til_name.setError(null);
            validate = true;
        }else {
            til_name.setError("Please fill in name");
            validate = false;
        }

        if(!etIC.getText().toString().isEmpty()){
            til_ic.setError(null);
            validate = true;
        }else {
            til_ic.setError("Please fill in ic");
            validate = false;
        }

        if(!etAge.getText().toString().isEmpty()) {
            til_age.setError(null);
            validate = true;
        }else {
            til_age.setError("Please fill in age");
            validate = false;
        }

        if(!etRegisterDate.getText().toString().isEmpty()) {
            til_registerDate.setError(null);
            validate = true;
        }else {
            til_registerDate.setError("Please fill in register date");
            validate = false;
        }

        if(!etAddress.getText().toString().isEmpty()){
            til_address.setError(null);
            validate = true;
        }else {
            til_address.setError("Please fill in address");
            validate = false;
        }

        if(!etContact.getText().toString().isEmpty()){
            til_contact.setError(null);
            validate = true;
        }else {
            til_contact.setError("Please fill in contact");
            validate = false;
        }

        if(!etMargin.getText().toString().isEmpty()){
            til_margin.setError(null);
            validate = true;
        }else {
            til_margin.setError("Please fill in Margin");
            validate = false;
        }

        return validate;
    }

    private void selectImage(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int item){
        //        boolean result = Utility.checkPermission(AddPatientActivityVolley.this);

                if(items[item].equals("Camera")){
                    userChoosenTask="Camera";
                    EnableRuntimePermissionToAccessCamera();
                }
                else if(items[item].equals("Gallery")){
                    userChoosenTask="Gallery";
                    EnableRuntimePermissionToAccessGallery();
                }
                else if(items[item].equals("Cancel")){
                    dialog.dismiss();
                }
                else{
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
        {
            // Printing toast message after enabling runtime permission.
            Toast.makeText(this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    public void EnableRuntimePermissionToAccessGallery(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            // Printing toast message after enabling runtime permission.
            Toast.makeText(this,"GALLERY permission allows us to Access GALLERY app", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RequestPermissionCode);
        }
    }

    private void cameraIntent(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }

    private void galleryIntent(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if(requestCode == 100)
                onSelectFromGalleryResult(data);
            else if(requestCode == 7)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
	private void onSelectFromGalleryResult(Intent data) {


	  if (data != null) {
	     try {
             bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
	     } catch (IOException e) {
	        e.printStackTrace();
	     }
	  }

        imageView.setImageBitmap(bitmap);

    }

    private void onCaptureImageResult(Intent data) {
      bitmap = (Bitmap) data.getExtras().get("data");
	  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

	  File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

	  FileOutputStream fo;
	  try {
	     destination.createNewFile();
	     fo = new FileOutputStream(destination);
	     fo.write(bytes.toByteArray());
	     fo.close();
	  } catch (FileNotFoundException e) {
	     e.printStackTrace();
	  } catch (IOException e) {
	     e.printStackTrace();
	  }

        imageView.setImageBitmap(bitmap);

     //   Glide.with(this).load(destination).apply(RequestOptions.circleCropTransform()).into(imageView);
	}

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    //URLs.URL_PATIENT_REGISTER
    private void RegisterPatient(String url) {

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
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
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
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("Name", name);
                params.put("IC", ic);
                params.put("Birthyear", birthyear.toString());
                params.put("Gender", gender);
                params.put("BloodType", blood_type);
                params.put("Address", address);
                params.put("Contact", contact);
                params.put("Meals", meals );
                params.put("Allergic",allergic );
                params.put("Sickness", sickness);
                params.put("regisType", "P");
                params.put("regisDate",register_date );
                params.put("Margin", margin);

                if(patient != null)
                    params.put("id", patient.getID());
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
            //    long imagename = System.currentTimeMillis();
                String imagename = "Patient" + ic;

                if(bitmap != null)
                    params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.gender_male:
                if (checked)
                    gender = "M";
                break;
            case R.id.gender_female:
                if (checked)
                    gender = "F";
        }
    }

    public void showDatePickerDialog(View view){
        DialogFragment fragment = new RegisterDatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	  switch (requestCode) {
	     case RequestPermissionCode:
	        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	           if(userChoosenTask.equals("Camera"))
	              cameraIntent();
	           else if(userChoosenTask.equals("Gallery"))
	              galleryIntent();
	        } else {
	           //code for deny
	        }
	        break;
	  }
	}
	
}
