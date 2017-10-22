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
public class ListViewNurseFragment extends Fragment {

    View myView;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);
        listView = (ListView) myView.findViewById(R.id.list_view);
        DisplayNurseList();
        return myView;
    }

    public void DisplayNurseList() {

        Queries dbq = new Queries(new DatabaseHelper(getActivity()));

        String[] columns = {
                DatabaseContract.NurseContract._ID,
                DatabaseContract.NurseContract.NAME
        };
        Cursor cursor = dbq.query(DatabaseContract.NurseContract.TABLE_NAME, columns, null, null, null, null, DatabaseContract.NurseContract._ID + " ASC");

        NurseCursorAdapter adapter = new NurseCursorAdapter(getActivity(), cursor, 0);

        listView.setAdapter(adapter);
    }


}
