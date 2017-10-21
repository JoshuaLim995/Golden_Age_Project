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
import android.support.design.widget.FloatingActionButton;
import android.widget.AdapterView;
import android.widget.ListView;
//import com.github.clans.fab.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private static final String EXTRA_ID = "Patient.ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.list_view_patient);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, ViewPatientActivity.class);
                intent.putExtra(EXTRA_ID, cursor.getLong(cursor.getColumnIndex(DatabaseContract.PatientContract._ID)));
                MainActivity.this.startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPatientActivity.class);
                startActivity(intent);
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

    @Override
    public void onResume(){
        super.onResume();

        Queries dbq = new Queries(new DatabaseHelper(getApplicationContext()));

        String[] columns = {
                DatabaseContract.PatientContract._ID,
                DatabaseContract.PatientContract.NAME
        };
        Cursor cursor = dbq.query(columns, null, null, null, null, DatabaseContract.PatientContract._ID + " ASC");

        PatientCursorAdapter adapter = new PatientCursorAdapter(this, cursor, 0);

        listView.setAdapter(adapter);
    }
}
