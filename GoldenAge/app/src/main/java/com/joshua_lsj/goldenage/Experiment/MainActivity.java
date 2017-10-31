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
//import com.joshua_lsj.goldenage.ListViewFragment.ListViewClientFragment;
//import com.joshua_lsj.goldenage.ListViewFragment.ListViewDriverFragment;
//import com.joshua_lsj.goldenage.ListViewFragment.ListViewNurseFragment;
//import com.joshua_lsj.goldenage.ListViewFragment.ListViewPatientFragment;
import com.joshua_lsj.goldenage.R;
//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private FloatingActionMenu fam;
    private Toolbar toolbar;
    private  DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;


    private static final String FRAGMENT_ADD_PATIENT = "com.addPatientFragment";

    private void Initialize(){
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

        navigationView.setCheckedItem(R.id.nav_patient_listView);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Initialize();

/*
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ListViewPatientFragment())
                .commit();
        getSupportActionBar().setTitle("Patients");
*/
        getSupportActionBar().setTitle("Experiment");

    }



    public void onFloatingActionButtonClicked(View view){
        Intent intent;
        FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fam);
        switch (view.getId()){
            case R.id.menu_add_patient:
//                intent = new Intent(getApplicationContext(), AddPatientActivity.class);
                intent = new Intent(getApplicationContext(),  AddPatientActivity.class);

                startActivity(intent);
                break;
            case R.id.menu_add_nurse:
                intent = new Intent(getApplicationContext(), AddUserActivity.class);
         //       intent = new Intent(getApplicationContext(), AddNurseActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_add_client:
     //           intent = new Intent(getApplicationContext(), AddClientActivity.class);
    //            startActivity(intent);
                break;
            case R.id.menu_add_driver:
     //           intent = new Intent(getApplicationContext(), AddDriverActivity.class);
    //            startActivity(intent);
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
        if (id == R.id.nav_patient_listView) {

            Toast.makeText(getApplicationContext(), "Experiment Main", Toast.LENGTH_SHORT).show();
            /*
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewPatientFragment())
                    .commit();
            getSupportActionBar().setTitle("Patients");
        }
        else if (id == R.id.nav_nurse_listView) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewNurseFragment())
                    .commit();
            getSupportActionBar().setTitle("Nurses");
        }
        else if (id == R.id.nav_driver_listView) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewDriverFragment())
                    .commit();
            getSupportActionBar().setTitle("Drivers");
        }
        else if (id == R.id.nav_client_listView) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListViewClientFragment())
                    .commit();
            getSupportActionBar().setTitle("Clients");
            */
        }
        else if (id == R.id.nav_logout) {
            Toast.makeText(getApplicationContext(), "nav_logout", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
