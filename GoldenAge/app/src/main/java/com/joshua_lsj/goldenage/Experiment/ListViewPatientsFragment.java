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

public class ListViewPatientsFragment extends Fragment {

    View myView;
    ListView listView;
    public static final String PATIENT = "PATIENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_view_fragment, container, false);

        listView = myView.findViewById(R.id.list_view);
        new DownloadJsonPatients(getActivity()).execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Patient patient = (Patient) adapterView.getAdapter().getItem(position);
                Toast.makeText(getActivity(), patient.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ViewPatientActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PATIENT, patient);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

        return myView;
    }
}
