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
import com.joshua_lsj.goldenage.Adapter.ClientAdapter;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.Experiment.ViewClientActivity;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.Other.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created at 9/11/2017
 */

public class ListViewClientFragment extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Client> clientList;

    public static final String CLIENT = "CLIENT";
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_list_view, container, false);

        listView = myView.findViewById(R.id.list_view);
        TextView emptyText = myView.findViewById(R.id.empty);
        listView.setEmptyView(emptyText);

        clientList = new ArrayList<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait, Retrieving From server");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Get Clients from database
        GetClients();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Client client = (Client) adapterView.getAdapter().getItem(position);

      //          Client client = new Client();


                Intent intent = new Intent(getActivity(), ViewClientActivity.class);
        //        Bundle bundle = new Bundle();
        //        bundle.putSerializable(CLIENT, client);
        //        intent.putExtras(bundle);

                //STORE THE USER DATA IN SHARED PREFERENCE
                SharedPrefManager.getInstance(myView.getContext()).setIdSharedPref(client.getID());
                startActivity(intent);


            }
        });

        return myView;
    }

    private void GetClients() {

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
                                    JSONObject clientJson = array.getJSONObject(i);
				//Creating a new user object
                                Client client = new Client(
                                        clientJson.getInt("ID"),
                                        clientJson.getString("Name"),
                                        clientJson.getString("IC"),
                                        clientJson.getString("Contact"),
                                        clientJson.getInt("BirthYear"),
                                        clientJson.getString("Address"),
                                        clientJson.getString("Gender"),
                                        clientJson.getString("RegisDate"),
                                        clientJson.getInt("Patient_ID")
                                	);
                                    
                                    //Save client into local
                                    Queries queries = new Queries(new DatabaseHelper(getContext()));

                                    if (queries.insert(client) != 0)
                                    	Toast.makeText(getContext(), "Client Saved", Toast.LENGTH_SHORT).show();
                                    
                                }
                                
                            }
                        } catch (JSONException e) {
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

                params.put("type", "Client");
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(multipartRequest);
    }


	
    private void DisplayClientList(){
        Queries dbq = new Queries(new DatabaseHelper(getActivity()));

        String[] columns = {
                DatabaseContract.ClientContract._ID,
                DatabaseContract.ClientContract.NAME
        };

        Cursor cursor = dbq.query(DatabaseContract.ClientContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.ClientContract._ID + " ASC");

        if(cursor.moveToFirst()){
            do{
                clientList.add(new Client(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.ClientContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.ClientContract.NAME))
                ));
            }while (cursor.moveToNext());
        }
        ClientAdapter adapter = new ClientAdapter(clientList, getActivity());
        listView.setAdapter(adapter);
    }


}
