package com.joshua_lsj.goldenage.Experiment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by limsh on 11/4/2017.
 */

public class ListViewPatientsFragment extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Patient> patientArrayList;
    public static final String PATIENT = "PATIENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);

        listView = myView.findViewById(R.id.list_view);
        patientArrayList = new ArrayList<>();

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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GET_PATIENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting user object from json array
                                JSONObject patient = array.getJSONObject(i);

                                //adding the product to product list
//User(int id, String name, String ic, string contact, int birthyear, String address, String gender, String regisDate, String regisType)
                                patientArrayList.add(new Patient(
                                        patient.getInt("ID"),
                                        patient.getString("Name")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            PatientAdapter adapter = new PatientAdapter(patientArrayList, getActivity());
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
