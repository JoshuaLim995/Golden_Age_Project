package com.joshua_lsj.goldenage.Fragments;

import android.app.Fragment;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_view_patient_medical, container, false);
        medicalArrayList = new ArrayList<>();

        initialize();

    //    listView = myView.findViewById(R.id.list_view);


        getData();


        return myView;
    }

    private void initialize(){
        String patient_name = SharedPrefManager.getInstance(getActivity()).getPatientName();

        TextView tvPatientName = myView.findViewById(R.id.item_name);
        TextView tvPatientID = myView.findViewById(R.id.item_id);
        TextView emptyText = myView.findViewById(R.id.empty);

        tvPatientName.setText(patient_name);
        listView = myView.findViewById(R.id.list_view);
        listView.setEmptyView(emptyText);

    }

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
                                    medicalArrayList.add(new Medical(
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
                            MedicalAdapter adapter = new MedicalAdapter(medicalArrayList, getActivity());
                            listView.setAdapter(adapter);
                               
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
}