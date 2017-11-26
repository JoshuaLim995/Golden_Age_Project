package com.joshua_lsj.goldenage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.joshua_lsj.goldenage.Objects.Client;
import com.joshua_lsj.goldenage.R;

import java.util.ArrayList;

/**
 * Created by limsh on 11/4/2017.
 */

public class ClientAdapter extends ArrayAdapter<Client> {

    private ArrayList<Client> data;
    Context context;

    private static class ViewHolder {
        TextView txtName;
       // TextView txtCity;
    }

    public ClientAdapter(ArrayList<Client> data, Context context) {
        super(context, R.layout.list_items, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Client client = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_items, parent, false);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.item_name);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.txtName.setText(client.getName());


        return convertView;
    }
}
