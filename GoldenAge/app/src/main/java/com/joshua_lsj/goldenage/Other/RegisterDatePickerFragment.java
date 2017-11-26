package com.joshua_lsj.goldenage.Other;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.joshua_lsj.goldenage.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog dpd;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        EditText etDate = (EditText)getActivity().findViewById(R.id.item_register_date);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
   //     SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");

        etDate.setText(formatter.format(c.getTime()));
    }

}
