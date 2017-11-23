package com.joshua_lsj.goldenage.Experiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by limsh on 11/4/2017.
 */

public class MedicalAdapter extends ArrayAdapter<Medical> {

    private ArrayList<Medical> data;
    Context context;

    private static class ViewHolder {
        TextView txtDate;
        TextView txtBP;
        TextView txtSugar;
        TextView txtHeartRate;
        TextView txtTemperature;
    }

    public MedicalAdapter(ArrayList<Medical> data, Context context) {
        super(context, R.layout.list_medical, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Medical medical = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_items, parent, false);

            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.item_date);
            viewHolder.txtBP = (TextView) convertView.findViewById(R.id.item_blood_pressure);
            viewHolder.txtSugar = (TextView) convertView.findViewById(R.id.item_sugar_level);
            viewHolder.txtHeartRate = (TextView) convertView.findViewById(R.id.item_heart_rate);
            viewHolder.txtTemperature = (TextView) convertView.findViewById(R.id.item_temperature);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.txtDate.setText(medical.getDate());
        viewHolder.txtBP.setText(medical.getBloodPressure());
        viewHolder.txtSugar.setText(medical.getSugar());
        viewHolder.txtHeartRate.setText(medical.getHeartRate());
        viewHolder.txtTemperature.setText(medical.getTempeture());

        return convertView;
    }
}
