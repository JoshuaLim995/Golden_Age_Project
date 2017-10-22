package com.joshua_lsj.goldenage;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by user on 12/31/15.
 */
public class ListViewPatientFragment extends Fragment {

    View myView;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);
        listView = (ListView) myView.findViewById(R.id.list_view);
        DisplayPatientList();
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
