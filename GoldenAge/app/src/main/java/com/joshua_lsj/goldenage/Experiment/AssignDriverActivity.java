package com.joshua_lsj.goldenage.Experiment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Adapter.PickerAdapter;
import com.joshua_lsj.goldenage.DataBase.DatabaseContract;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
import com.joshua_lsj.goldenage.Objects.Calender;
import com.joshua_lsj.goldenage.Objects.Picker;
import com.joshua_lsj.goldenage.Objects.User;
import com.joshua_lsj.goldenage.Other.RegisterDatePickerFragment;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Other.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.TimeZone;

public class AssignDriverActivity extends AppCompatActivity {

    TextInputLayout til_driver, til_patient, til_nurse, til_location, til_description, til_date, til_time;
    TextInputEditText etDriver, etPatient, etNurse, etLocation, etDescription, etDate, etTime;
    Dialog dialog;
    ListView listView;

    Queries dbq;

    private String driver_id, nurse_id, patient_id, date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbq = new Queries(new DatabaseHelper(getApplicationContext()));

        initialize();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkTextBox() == 0)
                    createDriverSchedule();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    } // closing onCreate

    private void initialize() {

        etDriver = findViewById(R.id.item_driver);
        etPatient = findViewById(R.id.item_patient);
        etNurse =  findViewById(R.id.item_nurse);
        etLocation =  findViewById(R.id.item_location);
        etDescription =  findViewById(R.id.item_description);
        etDate =  findViewById(R.id.item_schedule_date);
        etTime =  findViewById(R.id.item_schedule_time);

        til_driver = (TextInputLayout) findViewById(R.id.til_driver);
        til_patient = (TextInputLayout) findViewById(R.id.til_patient);
        til_nurse = (TextInputLayout) findViewById(R.id.til_nurse);
        til_location = (TextInputLayout) findViewById(R.id.til_location);
        til_description = (TextInputLayout) findViewById(R.id.til_description);
        til_date = (TextInputLayout) findViewById(R.id.til_schedule_date);
        til_time = (TextInputLayout) findViewById(R.id.til_schedule_time);

//Initialize the Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.picker_dialog);
        listView = (ListView) dialog.findViewById(R.id.list_picker);

    } // closing initialize

    private int checkTextBox(){
        int i = 0;

        if(etDriver.getText().toString().isEmpty()){
            til_driver.setError("Please select driver");
            i += 1;
        }else
            til_driver.setError(null);

        if(etNurse.getText().toString().isEmpty()){
            til_nurse.setError("Please select nurse");
            i += 1;
        }else
            til_nurse.setError(null);

        if(etPatient.getText().toString().isEmpty()){
            til_patient.setError("Please select nurse");
            i += 1;
        }else
            til_patient.setError(null);

        if(etLocation.getText().toString().isEmpty()){
            til_location.setError("Please enter Location");
            i += 1;
        }else
            til_location.setError(null);

        if(etDescription.getText().toString().isEmpty()){
            til_description.setError("Please enter Description");
            i += 1;
        }else
            til_description.setError(null);

        if(etDate.getText().toString().isEmpty()){
            til_date.setError("Please select Date");
            i += 1;
        }else
            til_date.setError(null);

        if(etTime.getText().toString().isEmpty()){
            til_time.setError("Please select Time");
            i += 1;
        }else
            til_time.setError(null);

        return i;
    }


    public void showDriverPickerDialog(View view) {

        ArrayList<Picker> pickerList = getNames(DatabaseContract.UserContract.TABLE_NAME, "D");
        showPicker("Select Driver", pickerList);

//OnClickListener for listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Picker picker = (Picker) adapterView.getAdapter().getItem(position);

                etDriver.setText(picker.getName());
                driver_id = picker.getId();

                dialog.dismiss();
            }
        });

    } // closing showDriverPickerDialog

    public void showPatientPickerDialog(View view) {

        ArrayList<Picker> pickerList = getNames(DatabaseContract.PatientContract.TABLE_NAME, "P");
        showPicker("Select Patient", pickerList);

//OnClickListener for listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Picker picker = (Picker) adapterView.getAdapter().getItem(position);

                etPatient.setText(picker.getName());
                patient_id = picker.getId();
                dialog.dismiss();

                Toast.makeText(getApplicationContext(), patient_id, Toast.LENGTH_SHORT).show();
            }
        });

    } // closing showPatientPickerDialog

    public void showNursePickerDialog(View view) {

        ArrayList<Picker> pickerList = getNames(DatabaseContract.UserContract.TABLE_NAME, "N");
        showPicker("Select Nurse", pickerList);

//OnClickListener for listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Picker picker = (Picker) adapterView.getAdapter().getItem(position);

                etNurse.setText(picker.getName());
                nurse_id = picker.getId();
                dialog.dismiss();
            }
        });

    } // closing showNursePickerDialog

    private ArrayList<Picker> getNames(String table, String type){
        String selection = DatabaseContract.REG_TYPE + " = ?";
        String[] selectionArgs = {type};

        String[] columns = {
                DatabaseContract._ID,
                DatabaseContract.NAME
        }; // closing columns

        Cursor cursor = dbq.query(table, columns, selection, selectionArgs, null, null, null);

        ArrayList<Picker> pickerList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                pickerList.add(new Picker(
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.UserContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.UserContract.NAME))
                ));
            } while (cursor.moveToNext());
        }
        return pickerList;
    }

    private void showPicker(String title, ArrayList<Picker> pickerList) {

        TextView tvTitle = dialog.findViewById(R.id.item_title);
        PickerAdapter pickerAdapter = new PickerAdapter(pickerList, getApplicationContext());
        listView.setAdapter(pickerAdapter);
        tvTitle.setText(title);
        dialog.show();

    }

    public void showDatePickerDialog(View view) {

// Get Current Date
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        Calendar calendar = Calendar.getInstance(timeZone);
        final int mYear = calendar.get(Calendar.YEAR);
        final int mMonth = calendar.get(Calendar.MONTH);
        final int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        etDate.setText(date);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();

    }

    public void showTimePickerDialog(View view) {

// Get Current Time
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        Calendar calendar = Calendar.getInstance(timeZone);
        final int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int mMinute = calendar.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        time = hourOfDay + ":" + minute;
                        etTime.setText(time);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    private void createDriverSchedule(){
        final String location = etLocation.getText().toString();
        final String description = etDescription.getText().toString();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.CREATE_DRIVER_SCHEDULE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error"))
                                finish();

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type", "Driver_Schedule");
                params.put("Driver_ID", driver_id);
                params.put("Patient_ID", patient_id);
                params.put("Nurse_ID", nurse_id);
                params.put("Location", location);
                params.put("Description", description);
                params.put("Date", date);
                params.put("Time", time);

                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }
}