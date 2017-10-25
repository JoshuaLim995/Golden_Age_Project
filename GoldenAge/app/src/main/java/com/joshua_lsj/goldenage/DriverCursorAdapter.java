package com.joshua_lsj.goldenage;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by limsh on 10/21/2017.
 */

public class DriverCursorAdapter extends CursorAdapter {
    private LayoutInflater inflater;

    public DriverCursorAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.list_name, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        //USING NURSE LIST
        TextView tvName = view.findViewById(R.id.user_name);

        String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.DriverContract.NAME));

        tvName.setText(name);

    }
}
