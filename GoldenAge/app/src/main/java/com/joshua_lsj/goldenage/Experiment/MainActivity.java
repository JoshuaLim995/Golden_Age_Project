package com.joshua_lsj.goldenage.Experiment;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.joshua_lsj.goldenage.DataBase.DatabaseContract;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
import com.joshua_lsj.goldenage.Fragments.ListViewClientFragment;
import com.joshua_lsj.goldenage.Fragments.ListViewPatientMedicalFragment;
import com.joshua_lsj.goldenage.Fragments.ListViewPatientsFragment;
import com.joshua_lsj.goldenage.Fragments.ListViewUserFragment;
import com.joshua_lsj.goldenage.Fragments.ViewPatientFragment;
import com.joshua_lsj.goldenage.Notification.AlarmReceiver;
import com.joshua_lsj.goldenage.Notification.NotificationScheduler;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Objects.Schedule;
import com.joshua_lsj.goldenage.Objects.User;
import com.joshua_lsj.goldenage.Other.DownloadData;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.Other.URLs;
import com.joshua_lsj.goldenage.Other.VolleyMultipartRequest;
import com.joshua_lsj.goldenage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private FloatingActionMenu fam;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private final String NAV_USER = "nav_user";
    private final String NAV_CLIENT = "nav_client";
    private final String NAV_PATIENT = "nav_patient";

    private final String NAV_PATIENT_MEDICAL = "nav_patient_medical";
    private final String NAV_PATIENT_INFO = "nav_patient_info";

    private final String NAV_NOW = "nav_now";
    private final String NAV_UPCOMING = "nav_upcoming";

    private ProgressDialog progressDialog;


    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";

    private BroadcastReceiver broadcastReceiver;

    private DownloadData downloadData;

    private User user = null;
    private Client client = null;
/*
    public static Activity getActivity(){
        return getActivity();
    }
*/


    private void AdminLogin() {
        //Set Content
        setContentView(R.layout.activity_admin_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fam = (FloatingActionMenu) findViewById(R.id.fam);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (!SharedPrefManager.getInstance(this).checkIfDownloaded()) {

            progressDialog.setMessage("Downloading Data for Admin");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            downloadData.downloadForAdmin();

            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    progressDialog.dismiss();
                    loadUsers();
                    Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(context).setDownloaded(true);





                    setNotification();
                }
            };
            //registering the broadcast receiver to update sync status
            registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));

        } else
            Toast.makeText(this, "is Downloaded", Toast.LENGTH_SHORT).show();


    }

    private void NurseLogin() {
        setContentView(R.layout.activity_nurse_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fam = (FloatingActionMenu) findViewById(R.id.fam);
        fam.setVisibility(View.GONE);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!SharedPrefManager.getInstance(this).checkIfDownloaded()) {

            progressDialog.setMessage("Downloading Data for Nurse");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            downloadData.downloadForNurse();

            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    progressDialog.dismiss();
                    laodPatients();
                    Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(context).setDownloaded(true);
                }
            };
            //registering the broadcast receiver to update sync status
            registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));

        } else
            Toast.makeText(this, "is Downloaded", Toast.LENGTH_SHORT).show();
    }

    private void ClientLogin() {

        setContentView(R.layout.activity_client_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fam = (FloatingActionMenu) findViewById(R.id.fam);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fam.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!SharedPrefManager.getInstance(this).checkIfDownloaded()) {

            progressDialog.setMessage("Downloading Data for Admin");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            downloadData.downloadForClient(client.getPatientID());

            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    progressDialog.dismiss();
                    loadPatientInfo();
                    Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(context).setDownloaded(true);
                }
            };
            //registering the broadcast receiver to update sync status
            registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));

        } else
            Toast.makeText(this, "is Downloaded", Toast.LENGTH_SHORT).show();


        //    helper.createForClient();
    }

    private void DriverLogin() {

        setContentView(R.layout.activity_driver_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fam = (FloatingActionMenu) findViewById(R.id.fam);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fam.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (!SharedPrefManager.getInstance(this).checkIfDownloaded()) {

            progressDialog.setMessage("Downloading Data for Driver");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            downloadData.downloadForDriver(user.getID());

            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    progressDialog.dismiss();

                    Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(context).setDownloaded(true);





                    setNotification();
                }
            };
            //registering the broadcast receiver to update sync status
            registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));

        } else
            Toast.makeText(this, "is Downloaded", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //CHECK IF USER IS LOGGED IN OR NOT, IF NOT, LOGINACTIVITY WILL OPEN INSTEAD
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            LogOut();
            Toast.makeText(getApplicationContext(), "Shared Logout", Toast.LENGTH_SHORT).show();
        }

        downloadData = new DownloadData(getApplicationContext());
        progressDialog = new ProgressDialog(this);

        String login_type = SharedPrefManager.getInstance(this).getLoginType();

        switch (login_type) {
            case LoginActivity.LOGIN_USER:
                user = SharedPrefManager.getInstance(this).getUserSharedPref();

                if (user != null) {
                    if (user.getRegisType().equals("A"))
                        AdminLogin();
                    else if (user.getRegisType().equals("N"))
                        NurseLogin();
                    else if(user.getRegisType().equals("D"))
                        DriverLogin();
                    else {
                        LogOut();
                        Toast.makeText(getApplicationContext(), "No type", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    LogOut();
                    Toast.makeText(getApplicationContext(), "NULL user", Toast.LENGTH_SHORT).show();
                }
                break;
            case LoginActivity.LOGIN_CLIENT:
                client = SharedPrefManager.getInstance(this).getClientSharedPref();
                if (client != null) {
                    ClientLogin();
                } else {
                    LogOut();
                    Toast.makeText(getApplicationContext(), "NULL Client", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                LogOut();
                break;
        }



    }

    public String getRegisterType(String type) {
        String getType;
        switch (type) {
            case "A":
                getType = "Admin";
                break;
            case "N":
                getType = "Nurse";
                break;
            case "D":
                getType = "Driver";
                break;
            case "C":
                getType = "Client";
                break;
            default:
                getType = "INVALID";
                break;
        }
        return getType;
    }

    private void loadUsers() {
        navigationView.setCheckedItem(R.id.nav_users_listView);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ListViewUserFragment())
                .commit();
        getSupportActionBar().setTitle("Users");
    }

    private void laodPatients() {
        navigationView.setCheckedItem(R.id.nav_patients_listView);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ListViewPatientsFragment())
                .commit();
        getSupportActionBar().setTitle("Patients");
    }

    private void loadPatientInfo() {
        navigationView.setCheckedItem(R.id.nav_view_patient);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ViewPatientFragment())
                .commit();
        getSupportActionBar().setTitle("Patient Info");
    }

    @Override
    protected void onResume() {
        super.onResume();

        switch (SharedPrefManager.getInstance(getApplicationContext()).getSelectedNav()) {
            case NAV_USER:
                    loadUsers();
                break;

            case NAV_CLIENT:
                navigationView.setCheckedItem(R.id.nav_client_listView);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListViewClientFragment())
                        .commit();
                getSupportActionBar().setTitle("Clients");
                break;


            case NAV_PATIENT:
                laodPatients();
                break;

            case NAV_PATIENT_MEDICAL:
                navigationView.setCheckedItem(R.id.nav_patient_medical_listView);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListViewPatientMedicalFragment())
                        .commit();
                getSupportActionBar().setTitle("Patient Medical");
                break;

            case NAV_PATIENT_INFO:
                loadPatientInfo();
                break;

            case NAV_NOW:
                navigationView.setCheckedItem(R.id.nav_driver_now);
                getSupportActionBar().setTitle("Now");

                break;
            case NAV_UPCOMING:
                navigationView.setCheckedItem(R.id.nav_driver_upcoming);
                getSupportActionBar().setTitle("Upcoming Schedule");

                break;
        }

    }


    public void onFloatingActionButtonClicked(View view) {
        Intent intent;
        //   FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fam);
        switch (view.getId()) {
            case R.id.menu_add_patient:
                //               intent = new Intent(getApplicationContext(),  AddPatientActivity.class);
                intent = new Intent(getApplicationContext(), AddPatientActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_add_user:
                intent = new Intent(getApplicationContext(), AddUserActivity.class);
                //       intent = new Intent(getApplicationContext(), AddNurseActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_add_client:
                //        intent = new Intent(getApplicationContext(), AddClientActivity.class);

                //Testing for using only 1 class instead of 2
                intent = new Intent(getApplicationContext(), AddUpdateClientActivity.class);
                startActivity(intent);
                break;

            default:
                fam.close(true);
        }
        fam.close(true);
    }

    public void onAssignDriverClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), AssignDriverActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_users_listView) {
            //STORE THE SELECTED NAV DATA IN SHARED PREFERENCE
            SharedPrefManager.getInstance(this).setSelectedNav(NAV_USER);

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewUserFragment())
                    .commit();
            getSupportActionBar().setTitle("Users");

        } else if (id == R.id.nav_patients_listView) {
            //STORE THE SELECTED NAV DATA IN SHARED PREFERENCE
            SharedPrefManager.getInstance(this).setSelectedNav(NAV_PATIENT);

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewPatientsFragment())
                    .commit();
            getSupportActionBar().setTitle("Patients");

        } else if (id == R.id.nav_client_listView) {
            //STORE THE SELECTED NAV DATA IN SHARED PREFERENCE
            SharedPrefManager.getInstance(this).setSelectedNav(NAV_CLIENT);

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewClientFragment())
                    .commit();
            getSupportActionBar().setTitle("Clients");
        } else if (id == R.id.nav_patient_medical_listView) {
            //STORE THE SELECTED NAV DATA IN SHARED PREFERENCE
            SharedPrefManager.getInstance(this).setSelectedNav(NAV_PATIENT_MEDICAL);

            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new ListViewPatientMedicalFragment())
                    .commit();
            getSupportActionBar().setTitle("Patient Medical");
        } else if (id == R.id.nav_view_patient) {

            SharedPrefManager.getInstance(this).setSelectedNav(NAV_PATIENT_INFO);

            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new ViewPatientFragment())
                    .commit();
            getSupportActionBar().setTitle("Patient Info");


        } else if(id == R.id.nav_driver_now){

            SharedPrefManager.getInstance(this).setSelectedNav(NAV_NOW);
            getSupportActionBar().setTitle("Now");

        } else if(id == R.id.nav_driver_upcoming){

            SharedPrefManager.getInstance(this).setSelectedNav(NAV_UPCOMING);
            getSupportActionBar().setTitle("UpComing Schedule");

        } else if (id == R.id.nav_logout) {
            LogOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void LogOut() {
        finish();
        Queries queries = new Queries(new DatabaseHelper(this));
        queries.deleteTables();
        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
        SharedPrefManager.getInstance(getApplicationContext()).logout();
    }







    //Set Notification
    private void setNotification(){
        Queries dbq = new Queries(new DatabaseHelper(this));

        String[] columns = {
                DatabaseContract.DriverScheduleContract._ID,
                DatabaseContract.DriverScheduleContract.DRIVER_NAME,
                DatabaseContract.DriverScheduleContract.PATIENT_NAME,
                DatabaseContract.DriverScheduleContract.NURSE_NAME,
                DatabaseContract.DriverScheduleContract.LOCATION,
                DatabaseContract.DriverScheduleContract.DESCRIPTION,
                DatabaseContract.DriverScheduleContract.DATE,
                DatabaseContract.DriverScheduleContract.TIME
        };

        Cursor cursor = dbq.query(DatabaseContract.DriverScheduleContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.ClientContract._ID + " ASC");

        if(cursor.moveToFirst()){
            do{
                Schedule schedule = new Schedule(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.DriverScheduleContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.DriverScheduleContract.DRIVER_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.DriverScheduleContract.PATIENT_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.DriverScheduleContract.NURSE_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.DriverScheduleContract.LOCATION)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.DriverScheduleContract.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.DriverScheduleContract.DATE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.DriverScheduleContract.TIME))
                );

                NotificationScheduler.setReminder(this, AlarmReceiver.class, schedule);
            }while (cursor.moveToNext());
        }
    }


}
