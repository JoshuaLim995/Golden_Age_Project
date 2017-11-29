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
        super(context, R.layout.picker_dialog, data);
        this.data = data;
        this.context = context;
    } // closing PickerAdapter

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Picker picker = getItem(position);
        ViewHolder viewHolder;
        final View result;


        if(convertView == null) {
            viewHolder = new PickerAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.picker_dialog, parent, false);

            viewHolder.tvName = convertView.findViewById(R.id.item_picker);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (PickerAdapter.ViewHolder)convertView.getTag();
        }

        viewHolder.tvName.setText(picker.getName());


        return convertView;








    } // closing class
}