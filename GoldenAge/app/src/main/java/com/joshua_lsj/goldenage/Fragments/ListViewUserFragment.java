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
    //    SaveUsersToLocal();

        //Display User data from Local Database
        DisplayUserList();


        //Checking if data are stored in local when create user
        //TestingDisplayUserList();

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

    @Override
    public void onResume() {
        super.onResume();
    //    DisplayUserList();
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

/*
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
*/
}
