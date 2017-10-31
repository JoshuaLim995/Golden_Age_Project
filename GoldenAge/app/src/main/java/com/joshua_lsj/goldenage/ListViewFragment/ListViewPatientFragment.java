package com.joshua_lsj.goldenage.ListViewFragment;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.joshua_lsj.goldenage.CursorAdapter.PatientCursorAdapter;
import com.joshua_lsj.goldenage.DataBase.DatabaseContract;
import com.joshua_lsj.goldenage.DataBase.DatabaseHelper;
import com.joshua_lsj.goldenage.DataBase.Queries;
import com.joshua_lsj.goldenage.R;
import com.joshua_lsj.goldenage.ViewPatientActivity;

/**
 * Created by user on 12/31/15.
 */
public class ListViewPatientFragment extends Fragment {

    View myView;
    ListView listView;
    private static final String ID = "Patient.ID";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);

        listView = myView.findViewById(R.id.list_view);
        DisplayPatientList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ViewPatientActivity.class);
                intent.putExtra(ID, cursor.getLong(cursor.getColumnIndex(DatabaseContract.PatientContract._ID)));
                ListViewPatientFragment.this.startActivity(intent);
            }
        });

        return myView;
    }


    public void DisplayPatientList() {

        Queries dbq = new Queries(new DatabaseHelper(getActivity()));

        String[] columns = {
                DatabaseContract.PatientContract._ID,
                DatabaseContract.PatientContract.NAME
        };
        Cursor cursor = dbq.query(DatabaseContract.PatientContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.PatientContract._ID + " ASC");

        PatientCursorAdapter adapter = new PatientCursorAdapter(getActivity(), cursor, 0);

        listView.setAdapter(adapter);
    }

}
