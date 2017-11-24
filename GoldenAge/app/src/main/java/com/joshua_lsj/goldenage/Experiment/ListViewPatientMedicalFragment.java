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

public class ListViewPatientMedicalFragment extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Medical> medicalArrayList;
    private String id = "0";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);

        listView = myView.findViewById(R.id.list_view_patient_medical);
        medicalArrayList = new ArrayList<>();


        getData();

        return myView;
    }

    private void getData() {
	//get patient id from shared preference
	id = SharedPrefManager.getInstance(this).getKeyPatientId();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GET_PATIENT_MEDICAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            

                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");
                                
                                for (int i = 0; i < array.length(); i++) {
                                JSONObject medicalJson = array.getJSONObject(i);
                                
                                //Creating a new user object
                                medicalList.add(new Medical(
                                        medicalJson.getString("Date"),
                                        medicalJson.getString("Patient_ID"),
                                        medicalJson.getString("Nurse_ID"),
                                        medicalJson.getDouble("Blood_Pressure"),
                                        medicalJson.getDouble("Sugar_Level"),
                                        medicalJson.getDouble("Heart_Rate"),
                                        medicalJson.getDouble("Temperature")
                                ));
                                }
                                 //creating adapter object and setting it to recyclerview
                            MedicalAdapter adapter = new MedicalAdapter(medicalList, getActivity());
                            listView.setAdapter(adapter);
                               
                            }
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
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //Put Patient id to parameters to search for medical record
                params.put("id", id);
                return params;
            }

};

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}