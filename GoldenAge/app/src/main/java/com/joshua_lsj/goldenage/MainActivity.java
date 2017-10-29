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
import android.os.AsyncTask;
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
import com.joshua_lsj.goldenage.Experiment.Login_v1;
import com.joshua_lsj.goldenage.Experiment.RequestHandler;
import com.joshua_lsj.goldenage.Experiment.URLs;
import com.joshua_lsj.goldenage.Experiment.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
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
        navigationView.setVisibility(View.INVISIBLE);


        fam.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.INVISIBLE);
        navigationView.setVisibility(View.INVISIBLE);
//        fam.removeAllViews();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialize();

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ListViewPatientFragment())
                .commit();
        getSupportActionBar().setTitle("Patients");



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

                               // Toast.makeText(getApplicationContext(),    "Login Sucessfull", Toast.LENGTH_LONG).show();

              //                  Login login = new Login("Facehugger", "123123");
                            Login login = new Login();
                                login.execute();




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

    public void check(String result){
        if(result.equals("0")){
            fam.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
            navigationView.setVisibility(View.VISIBLE);




        }else
            Toast.makeText(getApplicationContext(), "EEE", Toast.LENGTH_LONG).show();

    }

    class Login extends AsyncTask<Void, Void, String> {



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try{
                JSONObject obj = new JSONObject(s);

                if(!obj.getBoolean("error")){
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

                    JSONObject userJason = obj.getJSONObject("user");



                    //Creating a new user object
                          User user = new User(
                            userJason.getInt("id"),
                            userJason.getString("Name"),
                            userJason.getString("regisType")
                    );

                    check(user.getRegisType());

                }else{
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("Name", "Facehugger");
            params.put("userPass", "123123");

            //returing the response
            return requestHandler.sendPostRequest(URLs.LOGIN_URL, params);
        }



    }

}
