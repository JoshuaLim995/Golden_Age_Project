package com.joshua_lsj.goldenage.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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
import com.joshua_lsj.goldenage.DataBase.DatabaseContract;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
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

    @Override
    public void onResume() {
        super.onResume();
        DisplayPatientList();
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
                patientArrayList.add(new Patient(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.PatientContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.NAME))
                ));
            }while (cursor.moveToNext());
        }
        PatientAdapter adapter = new PatientAdapter(patientArrayList, getActivity());
        listView.setAdapter(adapter);
    }

}
