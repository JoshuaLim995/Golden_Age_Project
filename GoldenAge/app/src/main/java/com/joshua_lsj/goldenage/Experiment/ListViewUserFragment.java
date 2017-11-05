package com.joshua_lsj.goldenage.Experiment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.joshua_lsj.goldenage.R;

/**
 * Created by limsh on 11/4/2017.
 */

public class ListViewUserFragment extends Fragment {

    View myView;
    ListView listView;
    public static final String USER = "USER";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);

        listView = myView.findViewById(R.id.list_view);
        new DownloadJsonUsers(getActivity()).execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                User user = (User) adapterView.getAdapter().getItem(position);
                Toast.makeText(getActivity(), user.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ViewUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(USER, user);
                intent.putExtras(bundle);

                startActivity(intent);


                //    Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);

                //    Intent intent = new Intent(getActivity(), ViewPatientActivity.class);
                //    intent.putExtra(ID, cursor.getLong(cursor.getColumnIndex(DatabaseContract.PatientContract._ID)));
                //   ListViewPatientFragment.this.startActivity(intent);
            }
        });

        return myView;
    }
}
