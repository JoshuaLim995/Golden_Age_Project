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
public class ListViewDriverFragment extends Fragment {

    View myView;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);
        listView = myView.findViewById(R.id.list_view);
        DisplayDriverList();
        return myView;
    }

    public void DisplayDriverList() {

        Queries dbq = new Queries(new DatabaseHelper(getActivity()));

        String[] columns = {
                DatabaseContract.DriverContract._ID,
                DatabaseContract.DriverContract.NAME
        };
        Cursor cursor = dbq.query(DatabaseContract.DriverContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.DriverContract._ID + " ASC");

        DriverCursorAdapter adapter = new DriverCursorAdapter(getActivity(), cursor, 0);

        listView.setAdapter(adapter);
    }


}
