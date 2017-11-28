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
import com.joshua_lsj.goldenage.Adapter.UserAdapter;
import com.joshua_lsj.goldenage.DataBase.DatabaseContract;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
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

/*
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait, Retrieving From server");
        progressDialog.setCancelable(false);
        progressDialog.show();
*/
        //Get Users from database
        //   GetUsers();
        //SaveUsersToLocal();

        //Display User data from Local Database
       // DisplayUserList();


        //Checking if data are stored in local when create user
        TestingDisplayUserList();

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


    private void SaveUsersToLocal() {

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
                                    JSONObject userJason = array.getJSONObject(i);

                                    //adding the product to product list
//User(int id, String name, String ic, string contact, int birthyear, String address, String gender, String regisDate, String regisType)
                                    User user = new User(
                                            userJason.getInt("ID"),
                                            userJason.getString("Name"),
                                            userJason.getString("IC"),
                                            userJason.getString("Contact"),
                                            userJason.getInt("BirthYear"),
                                            userJason.getString("Address"),
                                            userJason.getString("Gender"),
                                            userJason.getString("RegisDate"),
                                            userJason.getString("RegisType")
                                    );


                                    //Add user into database
                                    Queries queries = new Queries(new DatabaseHelper(getContext()));

                                    if (queries.insert(user) != 0)
                                        Toast.makeText(getContext(), "User created", Toast.LENGTH_SHORT).show();

                                }

                                //creating adapter object and setting it to recyclerview
                          //      UserAdapter adapter = new UserAdapter(userList, getActivity());
                          //      listView.setAdapter(adapter);

                            //    DisplayUserList();
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

    private void DisplayUserList(){
        Queries dbq = new Queries(new DatabaseHelper(getActivity()));

        String[] columns = {
                DatabaseContract.UserContract._ID,
                DatabaseContract.UserContract.NAME,
                DatabaseContract.UserContract.REG_TYPE
        };

        Cursor cursor = dbq.query(DatabaseContract.UserContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.UserContract._ID + " ASC");

        if(cursor.moveToFirst()){
            do{
                userList.add(new User(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.UserContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.UserContract.NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.UserContract.REG_TYPE))
                ));
            }while (cursor.moveToNext());
        }
        UserAdapter userAdapter = new UserAdapter(userList, getActivity());
        listView.setAdapter(userAdapter);
    }


    private void TestingDisplayUserList(){
        Queries dbq = new Queries(new DatabaseHelper(getActivity()));

        String[] columns = {
                DatabaseContract.TempUserContract._ID,
                DatabaseContract.TempUserContract.NAME,
                DatabaseContract.TempUserContract.REG_TYPE
        };

        Cursor cursor = dbq.query(DatabaseContract.TempUserContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.TempUserContract._ID + " ASC");

        if(cursor.moveToFirst()){
            do{
                userList.add(new User(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.TempUserContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.TempUserContract.NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.TempUserContract.REG_TYPE))
                ));
            }while (cursor.moveToNext());
        }
        UserAdapter userAdapter = new UserAdapter(userList, getActivity());
        listView.setAdapter(userAdapter);
    }

}
