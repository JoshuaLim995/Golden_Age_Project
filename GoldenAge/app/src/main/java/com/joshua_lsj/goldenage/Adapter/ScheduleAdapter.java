package com.joshua_lsj.goldenage.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joshua_lsj.goldenage.Objects.Schedule;
import com.joshua_lsj.goldenage.Objects.User;
import com.joshua_lsj.goldenage.Other.SharedPrefManager;
import com.joshua_lsj.goldenage.R;

import java.util.ArrayList;

/**
 * Created by limsh on 11/4/2017.
 */

public class ScheduleAdapter extends ArrayAdapter<Schedule> {

    private ArrayList<Schedule> data;
    Context context;

    private static class ViewHolder {
        TextView tvDetail;
        TextView tvDateTime;
        TextView tvDriver;
        TextView tvPatient;
        TextView tvNurse;
        TextView tvDescription;

        LinearLayout driverLayout;
    }

    public ScheduleAdapter(ArrayList<Schedule> data, Context context) {
        super(context, R.layout.list_schedule, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Schedule schedule = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_schedule, parent, false);

            viewHolder.tvDetail = convertView.findViewById(R.id.item_detail);
            viewHolder.tvDateTime = convertView.findViewById(R.id.item_dateTime);
            viewHolder.tvDriver = convertView.findViewById(R.id.item_driver);
            viewHolder.tvPatient = convertView.findViewById(R.id.item_patient);
            viewHolder.tvNurse = convertView.findViewById(R.id.item_nurse);
            viewHolder.tvDescription = convertView.findViewById(R.id.item_description);
            viewHolder.driverLayout = convertView.findViewById(R.id.layout_driver);


            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(schedule != null){

            User user = SharedPrefManager.getInstance(context).getUserSharedPref();
            if(user.getRegisType().equals("D"))
                viewHolder.driverLayout.setVisibility(View.GONE);

            String detail = "Bring Patient to " + schedule.getLocation();
            String dateTime = schedule.getDate() + ", " + schedule.getTime();

            viewHolder.tvDetail.setText(detail);
            viewHolder.tvDateTime.setText(dateTime);
            viewHolder.tvDriver.setText(schedule.getDriverName());
            viewHolder.tvPatient.setText(schedule.getPatientName());
            viewHolder.tvNurse.setText(schedule.getNurseName());
            viewHolder.tvDescription.setText(schedule.getDescription());
        }

        return convertView;
    }
}
