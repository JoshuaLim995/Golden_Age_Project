package com.joshua_lsj.goldenage.Experiment;


import android.app.FragmentManager;
import android.content.Intent;
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

import com.github.clans.fab.FloatingActionMenu;
import com.joshua_lsj.goldenage.Fragments.ListViewClientFragment;
import com.joshua_lsj.goldenage.Fragments.ListViewPatientMedicalFragment;
import com.joshua_lsj.goldenage.Fragments.ListViewPatientsFragment;
import com.joshua_lsj.goldenage.Fragments.ListViewUserFragment;
import com.joshua_lsj.goldenage.Fragments.ViewPatientFragment;
import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.Objects.User;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.R;
//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private FloatingActionMenu fam;
    private Toolbar toolbar;
    private  DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private final String NAV_USER = "nav_user";
    private final String NAV_CLIENT = "nav_client";
    private final String NAV_PATIENT = "nav_patient";

    private final String NAV_PATIENT_MEDICAL = "nav_patient_medical";
    private final String NAV_PATIENT_INFO = "nav_patient_info";



    private User user;


private Client client;
/*
    public static Activity getActivity(){
        return getActivity();
    }
*/

    private void AdminLogin(){
        //Set Content
        setContentView(R.layout.activity_admin_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fam = (FloatingActionMenu) findViewById(R.id.fam);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void NurseLogin(){
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
    }
    
    private void ClientLogin(){

        setContentView(R.layout.activity_client_main);

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

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //CHECK IF USER IS LOGGED IN OR NOT, IF NOT, LOGINACTIVITY WILL OPEN INSTEAD
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        user = SharedPrefManager.getInstance(this).getUserSharedPref();

        if(user.getRegisType().equals("A"))
            AdminLogin();
        else if(user.getRegisType().equals("N"))
            NurseLogin();
        else if(user.getRegisType().equals("C"))
            ClientLogin();




    }

    public String getRegisterType(String type){
        String getType;
        switch (type){
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

    @Override
    protected void onResume() {
        super.onResume();

        switch (SharedPrefManager.getInstance(getApplicationContext()).getSelectedNav()){
            case NAV_USER:
                navigationView.setCheckedItem(R.id.nav_users_listView);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListViewUserFragment())
                        .commit();
                getSupportActionBar().setTitle("Users");
                break;


            case NAV_CLIENT:
                navigationView.setCheckedItem(R.id.nav_client_listView);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListViewClientFragment())
                        .commit();
                getSupportActionBar().setTitle("Clients");
                break;


            case NAV_PATIENT:
                navigationView.setCheckedItem(R.id.nav_patients_listView);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListViewPatientsFragment())
                        .commit();
                getSupportActionBar().setTitle("Patients");
                break;
                
            case NAV_PATIENT_MEDICAL:
                navigationView.setCheckedItem(R.id.nav_patient_medical_listView);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListViewPatientMedicalFragment())
                        .commit();
                getSupportActionBar().setTitle("Patient Medical");
                break;

            case NAV_PATIENT_INFO:
                navigationView.setCheckedItem(R.id.nav_view_patient);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ViewPatientFragment())
                        .commit();
                getSupportActionBar().setTitle("Patient Info");
                break;
        }

    }


    public void onFloatingActionButtonClicked(View view){
        Intent intent;
     //   FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fam);
        switch (view.getId()){
            case R.id.menu_add_patient:
 //               intent = new Intent(getApplicationContext(),  AddPatientActivity.class);
                intent = new Intent(getApplicationContext(),  AddPatientActivity.class);
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

        }

        else if (id == R.id.nav_patients_listView) {
            //STORE THE SELECTED NAV DATA IN SHARED PREFERENCE
            SharedPrefManager.getInstance(this).setSelectedNav(NAV_PATIENT);

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewPatientsFragment())
                    .commit();
            getSupportActionBar().setTitle("Patients");


        }

        else if (id == R.id.nav_client_listView) {
            //STORE THE SELECTED NAV DATA IN SHARED PREFERENCE
            SharedPrefManager.getInstance(this).setSelectedNav(NAV_CLIENT);

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewClientFragment())
                    .commit();
            getSupportActionBar().setTitle("Clients");
      }
        
        else if (id == R.id.nav_patient_medical_listView){
            //STORE THE SELECTED NAV DATA IN SHARED PREFERENCE
            SharedPrefManager.getInstance(this).setSelectedNav(NAV_PATIENT_MEDICAL);

            getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListViewPatientMedicalFragment())
                        .commit();
            getSupportActionBar().setTitle("Patient Medical");
        }

        else if(id == R.id.nav_view_patient){

            SharedPrefManager.getInstance(this).setSelectedNav(NAV_PATIENT_INFO);

            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new ViewPatientFragment())
                    .commit();
            getSupportActionBar().setTitle("Patient Info");


        }
        
        else if (id == R.id.nav_logout) {
            finish();
            Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


}
