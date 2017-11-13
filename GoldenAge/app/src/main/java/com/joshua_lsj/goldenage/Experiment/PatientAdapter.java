package com.joshua_lsj.goldenage.Experiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.joshua_lsj.goldenage.R;

import java.util.ArrayList;

/**
 * Created by limsh on 11/4/2017.
 */

public class PatientAdapter extends ArrayAdapter<Patient> {

    private ArrayList<Patient> patients;
    Context context;

    private static class ViewHolder {
        TextView txtName;
        TextView txtRegisType;
    }

    public PatientAdapter(ArrayList<Patient> patients, Context context) {
        super(context, R.layout.list_items, patients);
        this.patients = patients;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Patient patient = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_items, parent, false);

            viewHolder.txtName = convertView.findViewById(R.id.item_name);
            viewHolder.txtRegisType = convertView.findViewById(R.id.item_detail);


            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.txtName.setText(patient.getName());
      //  viewHolder.txtRegisType.setText(patient.getRegisType());


        return convertView;
    }
}
