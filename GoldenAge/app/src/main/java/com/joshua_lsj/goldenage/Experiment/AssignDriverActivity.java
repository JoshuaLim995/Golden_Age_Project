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

public class AssignDriverActivity extends AppCompatActivity {

    TextInputLayout til_driver, til_patient, til_nurse, til_location, til_description, til_date, til_time;
    TextInputEditText etDriver, etPatient, etNurse, etLocation, etDescription, etDate, etTime;
    Dialog dialog;
    ListView listView;

    Queries dbq;

    private String driver_id, nurse_id, patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbq = new Queries(new DatabaseHelper(getApplicationContext()));

        initialize();

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


    public void showDriverPickerDialog(View view) {

        String selection = DatabaseContract.UserContract.REG_TYPE + " = ?";
        String[] selectionArgs = {"D"};

        String[] columns = {
                DatabaseContract.UserContract._ID,
                DatabaseContract.UserContract.NAME
        }; // closing columns

        Cursor cursor = dbq.query(DatabaseContract.UserContract.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        ArrayList<Picker> pickerList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                pickerList.add(new Picker(
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.UserContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.UserContract.NAME))
                ));
            } while (cursor.moveToNext());
        }
        showPicker("Select Driver", pickerList);

//OnClickListener for listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Picker picker = (Picker) adapterView.getAdapter().getItem(position);

                etDriver.setText(picker.getName());
                driver_id = picker.getId();
            }
        });

    } // closing showDriverPickerDialog


    private void showPicker(String title, ArrayList<Picker> pickerList) {

//final Dialog dialog = new Dialog(this);
//dialog.setContentView(R.layout.picker_dialog);
//dialog.setTitle(title);
//ListView listView = (ListView) dialog.findViewById(R.id.list_picker);
        PickerAdapter pickerAdapter = new PickerAdapter(pickerList, getApplicationContext());
        listView.setAdapter(pickerAdapter);
        dialog.show();

    } // closing showPicker


    public void showDatePickerDialog(View view) {

// Get Current Date
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        etDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();

    } //closing showDatePicker

    public void showTimePickerDialog(View view) {

// Get Current Time
        final Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        etTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    } //closing showTimePicker

} // closing class