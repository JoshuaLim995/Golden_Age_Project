package com.joshua_lsj.goldenage.CursorAdapter;

import android.widget.CursorAdapter;
import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;
import android.view.*;

import com.joshua_lsj.goldenage.DatabaseContract;
import com.joshua_lsj.goldenage.R;

import java.text.*;

/**
 * Created by limsh on 10/21/2017.
 */

public class PatientCursorAdapter extends CursorAdapter {
    private LayoutInflater inflater;

    public PatientCursorAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.list_name, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = view.findViewById(R.id.user_name);

        String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.PatientContract.NAME));

        tvName.setText(name);
    }
}
