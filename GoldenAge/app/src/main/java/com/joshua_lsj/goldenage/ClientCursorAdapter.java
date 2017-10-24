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

public class ClientCursorAdapter extends CursorAdapter {
    private LayoutInflater inflater;

    public ClientCursorAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.list_client, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = view.findViewById(R.id.client_name);

        String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.ClientContract.NAME));

        tvName.setText(name);

    }
}