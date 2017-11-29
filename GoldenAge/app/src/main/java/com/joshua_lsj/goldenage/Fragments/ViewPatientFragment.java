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
        id = SharedPrefManager.getInstance(getActivity()).getKeySelectedId();

        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        //getFromLocal();
    }



    private void getData(){

        id = SharedPrefManager.getInstance(getActivity()).getKeySelectedId();
        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_DATA,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            //           Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error")){
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");
                                JSONObject PatientJson = array.getJSONObject(0);

                                //Creating a new user object
                                patient = new Patient(
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

                                Queries queries = new Queries(new DatabaseHelper(getContext()));

                                if (queries.insert(patient) != 0)
                                    getFromLocal();
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //Put Patient data to parameters
                params.put("type", "Patient");
                params.put("id", id);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(multipartRequest);
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
                DatabaseContract.PatientContract.MARGIN,
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
                    cursor.getDouble(cursor.getColumnIndex(DatabaseContract.PatientContract.MARGIN)),
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
        TextView tvMargin = (TextView) myView.findViewById(R.id.item_margin);

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
            tvMargin.setText(patient.getMargin().toString());

            if(!patient.getImageName().equals("null"))
                Glide.with(this).load(URLs.URL_IMAGE_FILE + patient.getImageName()).apply(RequestOptions.circleCropTransform()).into(imageView);
            else
                Glide.with(this).load(R.drawable.user_icon).apply(RequestOptions.circleCropTransform()).into(imageView);
        }
    }
}
