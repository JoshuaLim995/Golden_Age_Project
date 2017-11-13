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

import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.R;

/**
 * Created at 9/11/2017
 */

public class ListViewClientFragment extends Fragment {

    View myView;
    ListView listView;
    public static final String CLIENT = "CLIENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);

        listView = myView.findViewById(R.id.list_view);
        new DownloadJsonClients(getActivity()).execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Client client = (Client) adapterView.getAdapter().getItem(position);
                Toast.makeText(getActivity(), client.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ViewClientActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CLIENT, client);
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
