package com.joshua_lsj.goldenage.Volley;

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
import com.joshua_lsj.goldenage.Experiment.MainActivity;
import com.joshua_lsj.goldenage.Experiment.SharedPrefManager;
import com.joshua_lsj.goldenage.Experiment.URLs;
import com.joshua_lsj.goldenage.Experiment.User;
import com.joshua_lsj.goldenage.Experiment.UserAdapter;
import com.joshua_lsj.goldenage.Experiment.ViewUserActivity;
import com.joshua_lsj.goldenage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by limsh on 11/4/2017.
 */

public class ListViewUserFragmentVolley extends Fragment {

    View myView;
    ListView listView;
    ArrayList<User> userList;

    public static final String USER = "USER";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);

        listView = myView.findViewById(R.id.list_view);
       // new DownloadJsonUsers(getActivity()).execute();

        userList = new ArrayList<User>();

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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GET_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

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
