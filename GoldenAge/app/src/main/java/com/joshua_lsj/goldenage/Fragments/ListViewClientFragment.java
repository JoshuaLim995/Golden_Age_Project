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
import com.joshua_lsj.goldenage.Adapter.ClientAdapter;
import com.joshua_lsj.goldenage.DataBase.DatabaseContract;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_list_view, container, false);

        listView = myView.findViewById(R.id.list_view);
        TextView emptyText = myView.findViewById(R.id.empty);
        listView.setEmptyView(emptyText);

        clientList = new ArrayList<>();



    //    DisplayClientList();
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

    @Override
    public void onResume() {
        super.onResume();
        DisplayClientList();
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
