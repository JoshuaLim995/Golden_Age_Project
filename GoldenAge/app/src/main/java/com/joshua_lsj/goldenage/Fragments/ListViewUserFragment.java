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
import com.joshua_lsj.goldenage.Adapter.UserAdapter;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.Experiment.ViewUserActivity;
import com.joshua_lsj.goldenage.Objects.User;
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

public class ListViewUserFragment extends Fragment {

    View myView;
    ListView listView;
    ArrayList<User> userList;
    ProgressDialog progressDialog;

    public static final String USER = "USER";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_list_view, container, false);

        listView = myView.findViewById(R.id.list_view);
        TextView emptyText = myView.findViewById(R.id.empty);
        listView.setEmptyView(emptyText);

        userList = new ArrayList<User>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait, Retrieving From server");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Get Users from database
        GetUsers();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                User user = (User) adapterView.getAdapter().getItem(position);


                Intent intent = new Intent(getActivity(), ViewUserActivity.class);

/*
                Bundle bundle = new Bundle();
                bundle.putSerializable(USER, user);
                intent.putExtras(bundle);
*/
                //STORE THE USER DATA IN SHARED PREFERENCE
                SharedPrefManager.getInstance(myView.getContext()).setIdSharedPref(user.getID());
           //     intent.putExtra(USER, user.getID());
                startActivity(intent);

            }
        });

        return myView;
    }

    private void GetUsers() {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.READ_ALL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            if(!obj.getBoolean("error")) {

                                //converting the string to json array object
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONArray array = obj.getJSONArray("result");

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting user object from json array
                                    JSONObject user = array.getJSONObject(i);

                                    //adding the product to product list
//User(int id, String name, String ic, string contact, int birthyear, String address, String gender, String regisDate, String regisType)
                                    userList.add(new User(
                                            user.getInt("ID"),
                                            user.getString("Name"),
                                            user.getString("RegisType")
                                    ));
                                }

                                //creating adapter object and setting it to recyclerview
                                UserAdapter adapter = new UserAdapter(userList, getActivity());
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

                params.put("type", "User");
                return params;
            }

        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(multipartRequest);
    }

}
