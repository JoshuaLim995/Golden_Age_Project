package com.joshua_lsj.goldenage.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Adapter.MedicalAdapter;
import com.joshua_lsj.goldenage.DataBase.DatabaseContract;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
import com.joshua_lsj.goldenage.Experiment.LoginActivity;
import com.joshua_lsj.goldenage.Objects.Calender;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Objects.Patient;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.Objects.Medical;
import com.joshua_lsj.goldenage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 11/4/2017.
 */

public class ListViewPatientMedicalFragment extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Medical> medicalArrayList;
    private String id = "0";
    ProgressDialog progressDialog;
    Queries dbq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_view_patient_medical, container, false);
        medicalArrayList = new ArrayList<>();

        String login_type = SharedPrefManager.getInstance(getActivity()).getLoginType();
        boolean OK = false;

        switch (login_type) {
            case LoginActivity.LOGIN_USER:
                id = SharedPrefManager.getInstance(getActivity()).getKeySelectedId();
                OK = true;
                break;
            case LoginActivity.LOGIN_CLIENT:
                Client client = SharedPrefManager.getInstance(getActivity()).getClientSharedPref();
                id = client.getPatientID();
                OK = true;
                break;
            default:

                break;
        }
        if(OK){
            dbq = new Queries(new DatabaseHelper(getActivity()));
            initialize();
            DisplayPatientList();
        }
        return myView;
    }

    private void initialize(){
        TextView tvPatientName = myView.findViewById(R.id.item_name);
        TextView tvPatientID = myView.findViewById(R.id.item_id);
        TextView emptyText = myView.findViewById(R.id.empty);

        tvPatientName.setText(getPatientName());
        tvPatientID.setText(id);
        listView = myView.findViewById(R.id.list_view);
        listView.setEmptyView(emptyText);
    }

    /*
    private void getData() {
	//get patient id from shared preference
	id = SharedPrefManager.getInstance(getActivity()).getKeySelectedId();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.READ_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response));
                        //    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){

                                JSONArray array = obj.getJSONArray("result");
                                
                                for (int i = 0; i < array.length(); i++) {
                                JSONObject medicalJson = array.getJSONObject(i);
                                //Creating a new user object
                                    Medical medical = new Medical(
                                            medicalJson.getInt("ID"),
                                            medicalJson.getString("Date"),
                                        medicalJson.getString("Patient_ID"),
                                        medicalJson.getString("Nurse_ID"),
                                        medicalJson.getDouble("Blood_Pressure"),
                                        medicalJson.getDouble("Sugar_Level"),
                                        medicalJson.getDouble("Heart_Rate"),
                                        medicalJson.getDouble("Temperature")
                                );
                                    dbq  = new Queries(new DatabaseHelper(getContext()));

                                    if (dbq.insert(medical) != 0)
                                        Toast.makeText(getContext(), "Patient Medical Saved", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(getContext(), "Patient Not Medical Saved", Toast.LENGTH_SHORT).show();
                                }
                                DisplayPatientList();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Unable to retrieve data from server", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //Put Patient id to parameters to search for medical record
                params.put("type", "Medical");
                params.put("id", id);
                return params;
            }

};

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
*/
    private String getPatientName(){

        String selection = DatabaseContract.PatientContract._ID + " = ?";
        String[] selectionArgs = {id};

        String[] columns = {
                DatabaseContract.PatientContract.NAME
        };

        Cursor cursor = dbq.query(DatabaseContract.PatientContract.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if(cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.NAME));
        }else
            Toast.makeText(getActivity(), "Unable to retrieve data from Local", Toast.LENGTH_SHORT).show();
        return "No Name";
    }

    private void DisplayPatientList(){


        String selection = DatabaseContract.MedicalContract.PATIENT_ID + " = ?";
        String[] selectionArgs = {id};

        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();

        String[] columns = {
                DatabaseContract.MedicalContract._ID,
                DatabaseContract.MedicalContract.DATE,
                DatabaseContract.MedicalContract.PATIENT_ID,
                DatabaseContract.MedicalContract.NURSE_ID,
                DatabaseContract.MedicalContract.BLOOD_PRESSURE,
                DatabaseContract.MedicalContract.SUGAR_LEVEL,
                DatabaseContract.MedicalContract.HEART_RATE,
                DatabaseContract.MedicalContract.TEMPERATURE,
                DatabaseContract.MedicalContract.DESCRIPTION
        };

        Cursor cursor = dbq.query(DatabaseContract.MedicalContract.TABLE_NAME, columns, selection, selectionArgs, null, null, DatabaseContract.MedicalContract._ID + " ASC");

        if(cursor.moveToFirst()){
            do{
                medicalArrayList.add(new Medical(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.MedicalContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.MedicalContract.DATE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.MedicalContract.PATIENT_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.MedicalContract.NURSE_ID)),
                        cursor.getDouble(cursor.getColumnIndex(DatabaseContract.MedicalContract.BLOOD_PRESSURE)),
                        cursor.getDouble(cursor.getColumnIndex(DatabaseContract.MedicalContract.SUGAR_LEVEL)),
                        cursor.getDouble(cursor.getColumnIndex(DatabaseContract.MedicalContract.HEART_RATE)),
                        cursor.getDouble(cursor.getColumnIndex(DatabaseContract.MedicalContract.TEMPERATURE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.MedicalContract.DESCRIPTION))
                ));
            }while (cursor.moveToNext());
        }
        MedicalAdapter adapter = new MedicalAdapter(medicalArrayList, getActivity());
        listView.setAdapter(adapter);
    }
}