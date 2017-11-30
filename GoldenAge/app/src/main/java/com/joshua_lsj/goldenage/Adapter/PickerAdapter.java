package com.joshua_lsj.goldenage.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.joshua_lsj.goldenage.Objects.Picker;
import com.joshua_lsj.goldenage.R;

import java.util.ArrayList;

public class PickerAdapter extends ArrayAdapter<Picker> {

    private ArrayList<Picker> data;
    Context context;

    private static class ViewHolder {
        TextView tvName;
    }

    public PickerAdapter(ArrayList<Picker> data, Context context) {
        super(context, R.layout.list_picker, data);
        this.data = data;
        this.context = context;
    } // closing PickerAdapter

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Picker picker = getItem(position);
        ViewHolder viewHolder;


         //   viewHolder = new PickerAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_picker, parent, false);

            TextView tvName = (TextView) convertView.findViewById(R.id.item_picker);



        tvName.setText(picker.getName());


        return convertView;








    } // closing class
}