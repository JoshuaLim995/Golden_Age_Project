package com.joshua_lsj.goldenage.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.joshua_lsj.goldenage.DataBase.DatabaseContract;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
import com.joshua_lsj.goldenage.Objects.Calender;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.Objects.Patient;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Other.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 11/26/2017.
 */

public class ViewPatientFragment extends Fragment {

    View myView;
    String id = "0";
    Patient patient = null;
    private Queries dbq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_view_patient, container, false);

        dbq = new Queries(new DatabaseHelper(getActivity()));
        Client client = SharedPrefManager.getInstance(getActivity()).getClientSharedPref();
        id = client.getPatientID();

        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
  //      getData();
        getFromLocal();
    }



    private void getFromLocal(){

        String selection = DatabaseContract.PatientContract._ID + " = ?";
        String[] selectionArgs = {id};

        String[] columns = {
                DatabaseContract.PatientContract._ID,
                DatabaseContract.PatientContract.NAME,
                DatabaseContract.PatientContract.IC,
                DatabaseContract.PatientContract.CONTACT,
                DatabaseContract.PatientContract.AGE,
                DatabaseContract.PatientContract.ADDRESS,
                DatabaseContract.PatientContract.GENDER,
                DatabaseContract.PatientContract.REG_DATE,
                DatabaseContract.PatientContract.BLOOD_TYPE,
                DatabaseContract.PatientContract.MEALS,
                DatabaseContract.PatientContract.ALLERGIC,
                DatabaseContract.PatientContract.SICKNESS,
                DatabaseContract.PatientContract.DEPOSIT,
                DatabaseContract.PatientContract.IMAGE
        };

        Cursor cursor = dbq.query(DatabaseContract.PatientContract.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if(cursor.moveToNext()){

            Calender calender = new Calender();
            int birthyear = calender.getCurrentYear() - cursor.getInt(cursor.getColumnIndex(DatabaseContract.PatientContract.AGE));


            patient = new Patient(
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.PatientContract._ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.NAME)),		cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.IC)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.CONTACT)),
                    birthyear,
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.GENDER)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.REG_DATE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.BLOOD_TYPE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.MEALS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.ALLERGIC)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.SICKNESS)),
                    cursor.getDouble(cursor.getColumnIndex(DatabaseContract.PatientContract.DEPOSIT)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.IMAGE))
            );

            Initialize();
        }else
            Toast.makeText(getActivity(), "Unable to retrieve data from Local", Toast.LENGTH_SHORT).show();
    }


    public void Initialize() {


        TextView tvId = (TextView) myView.findViewById(R.id.item_id);
        TextView tvName = (TextView) myView.findViewById(R.id.item_name);
        TextView tvContact = (TextView) myView.findViewById(R.id.item_contact);
        TextView tvIC = (TextView) myView.findViewById(R.id.item_ic);
        TextView tvGender = (TextView) myView.findViewById(R.id.item_gender);
        TextView tvAge = (TextView) myView.findViewById(R.id.item_age);
        TextView tvBlood = (TextView) myView.findViewById(R.id.item_blood_type);
        TextView tvSick = (TextView) myView.findViewById(R.id.item_sickness);
        TextView tvMeal = (TextView) myView.findViewById(R.id.item_meals);
        TextView tvAllergic = (TextView) myView.findViewById(R.id.item_allergic);
        TextView tvRegDate = (TextView) myView.findViewById(R.id.item_register_date);
        TextView tvDeposit = (TextView) myView.findViewById(R.id.item_deposit);
        TextView tvAddress = (TextView) myView.findViewById(R.id.item_address);

        ImageView imageView = (ImageView) myView.findViewById(R.id.item_image);


        if(patient != null){
            tvId.setText(patient.getID());
            tvName.setText(patient.getName());
            tvContact.setText(patient.getContact());
            tvIC.setText(patient.getIc());
            tvGender.setText(patient.getGender());
            tvAge.setText(patient.getAge());
            tvBlood.setText(patient.getBlood_type());
            tvSick.setText(patient.getSickness());
            tvMeal.setText(patient.getMeals());
            tvAllergic.setText(patient.getAllergic());
            tvRegDate.setText(patient.getRegisDate());
            tvDeposit.setText(patient.getDeposit().toString());
            tvAddress.setText(patient.getAddress());

            if(!patient.getImageName().equals("null"))
                Glide.with(this).load(URLs.URL_IMAGE_FILE + patient.getImageName()).apply(RequestOptions.circleCropTransform()).into(imageView);
            else
                Glide.with(this).load(R.drawable.user_icon).apply(RequestOptions.circleCropTransform()).into(imageView);
        }
    }
}
