package com.joshua_lsj.goldenage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.ListView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionMenu fam;
    private static final String EXTRA_ID = "Patient.ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        listView = (ListView)findViewById(R.id.list_view_patient);
 /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, ViewPatientActivity.class);
                intent.putExtra(EXTRA_ID, cursor.getLong(cursor.getColumnIndex(DatabaseContract.PatientContract._ID)));
                MainActivity.this.startActivity(intent);
            }
        });
*/
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPatientActivity.class);
                startActivity(intent);
            }
        });
*/
        fam = (FloatingActionMenu) findViewById(R.id.fam);
        FloatingActionButton newPatient = (FloatingActionButton) findViewById(R.id.menu_add_patient);
        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPatientActivity.class);
                startActivity(intent);
                fam.close(true);
            }
        });

        FloatingActionButton newNurse = (FloatingActionButton) findViewById(R.id.menu_add_nurse);
        newNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNurseActivity.class);
                startActivity(intent);
                fam.close(true);
            }
        });
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

    // List View for patient list
/*
    @Override
    public void onResume(){
        super.onResume();

        Queries dbq = new Queries(new DatabaseHelper(getApplicationContext()));

        String[] columns = {
                DatabaseContract.PatientContract._ID,
                DatabaseContract.PatientContract.NAME
        };
        Cursor cursor = dbq.query(DatabaseContract.PatientContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.PatientContract._ID + " ASC");

        PatientCursorAdapter adapter = new PatientCursorAdapter(this, cursor, 0);

        listView.setAdapter(adapter);
    }
*/

// List View for Nurse list

    @Override
    public void onResume(){
        super.onResume();

        Queries dbq = new Queries(new DatabaseHelper(getApplicationContext()));

        String[] columns = {
                DatabaseContract.NurseContract._ID,
                DatabaseContract.NurseContract.NAME
        };
        Cursor cursor = dbq.query(DatabaseContract.NurseContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.NurseContract._ID + " ASC");

        NurseCursorAdapter adapter = new NurseCursorAdapter(this, cursor, 0);

        listView.setAdapter(adapter);
    }
}
