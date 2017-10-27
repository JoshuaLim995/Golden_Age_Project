package com.joshua_lsj.goldenage;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private static final String FRAGMENT_ADD_PATIENT = "com.addPatientFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        //Hide the Floating Button
        FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fam);
      //  fam.setVisibility(View.INVISIBLE);

        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_patient_listView);


        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ListViewPatientFragment())
                .commit();
        getSupportActionBar().setTitle("Patients");
*/

        showAlert();
    }

public void showAlert(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

    LayoutInflater li = LayoutInflater.from(this);
    View promptsView = li.inflate(R.layout.prompts, null);

    alertDialogBuilder.setView(promptsView);

    final EditText userInput = (EditText) promptsView
            .findViewById(R.id.editTextDialogUserInput);


    // set dialog message
    alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // get user input and set it to result
                            // edit text
                            if(userInput.getText().toString() == "LOL")
                                Toast.makeText(getApplicationContext(),
                                        "Login Sucessfull", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(),
                                        "NO", Toast.LENGTH_LONG).show();
                        }
                    });


    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();

    // show it
    alertDialog.show();
}

    public void onFloatingActionButtonClicked(View view){
        Intent intent;
        FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fam);
        switch (view.getId()){
            case R.id.menu_add_patient:
//                intent = new Intent(getApplicationContext(), AddPatientActivity.class);
                intent = new Intent(getApplicationContext(),  com.joshua_lsj.goldenage.Experiment.AddPatientActivity.class);

                startActivity(intent);
                break;
            case R.id.menu_add_nurse:
                intent = new Intent(getApplicationContext(), AddNurseActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_add_client:
                intent = new Intent(getApplicationContext(), AddClientActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_add_driver:
                intent = new Intent(getApplicationContext(), AddDriverActivity.class);
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
        if (id == R.id.nav_patient_listView) {
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
        }
        else if (id == R.id.nav_logout) {
            Toast.makeText(getApplicationContext(), "nav_logout", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
