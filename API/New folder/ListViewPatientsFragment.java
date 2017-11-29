package com.joshua_lsj.goldenage.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Adapter.PatientAdapter;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.Experiment.ViewPatientActivity;
import com.joshua_lsj.goldenage.Objects.Patient;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Other.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 11/4/2017.
 */

public class ListViewPatientsFragment extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Patient> patientArrayList;
    public static final String PATIENT = "PATIENT";
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_list_view, container, false);

        listView = myView.findViewById(R.id.list_view);
        TextView emptyText = myView.findViewById(R.id.empty);
        listView.setEmptyView(emptyText);
        patientArrayList = new ArrayList<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait, Retrieving From server");
        progressDialog.setCancelable(false);
        progressDialog.show();

        GetPatients();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Patient patient = (Patient) adapterView.getAdapter().getItem(position);

                //STORE THE USER DATA IN SHARED PREFERENCE
                SharedPrefManager.getInstance(myView.getContext()).setIdSharedPref(patient.getID());

                Intent intent = new Intent(getActivity(), ViewPatientActivity.class);
                startActivity(intent);

            }
        });

        return myView;
    }

    private void GetPatients() {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_ALL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            if(!obj.getBoolean("error")) {
                                //converting the string to json array object
                                JSONArray array = obj.getJSONArray("result");

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting user object from json array
                                    JSONObject patientJson = array.getJSONObject(i);

                                    Patient patient = new Patient(
                                        PatientJson.getInt("ID"),
                                        PatientJson.getString("Name"),
                                        PatientJson.getString("IC"),
                                        PatientJson.getString("Contact"),
                                        PatientJson.getInt("BirthYear"),
                                        PatientJson.getString("Address"),
                                        PatientJson.getString("Gender"),
                                        PatientJson.getString("RegisDate"),
                                        PatientJson.getString("BloodType"),
                                        PatientJson.getString("Meals"),
                                        PatientJson.getString("Allergic"),
                                        PatientJson.getString("Sickness"),
                                        PatientJson.getDouble("Margin"),
                                        PatientJson.getString("Image")
                                );

				//Save Patient into local
                                    Queries queries = new Queries(new DatabaseHelper(getContext()));

                                    if (queries.insert(patient) != 0)
                                    	Toast.makeText(getContext(), "Patient Saved", 


                                }

                                //creating adapter object and setting it to recyclerview
                                PatientAdapter adapter = new PatientAdapter(patientArrayList, getActivity());
                                listView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Unable to retrieve data from server", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("type", "Patient");
                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(multipartRequest);
    }



    private void DisplayPatientList(){
        Queries dbq = new Queries(new DatabaseHelper(getActivity()));

        String[] columns = {
                DatabaseContract.PatientContract._ID,
                DatabaseContract.PatientContract.NAME
        };

        Cursor cursor = dbq.query(DatabaseContract.PatientContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.PatientContract._ID + " ASC");

        if(cursor.moveToFirst()){
            do{
                patientList.add(new Patient(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.PatientContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.NAME))
                ));
            }while (cursor.moveToNext());
        }
        PatientAdapter adapter = new PatientAdapter(patientList, getActivity());
        listView.setAdapter(adapter);
    }



}
