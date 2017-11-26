package com.joshua_lsj.goldenage.Objects;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by limsh on 10/22/2017.
 */

public class Calender {

    public Calender(){}

    public String getToday(){
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(timeZone);
        String today = simpleDateFormat.format(calendar.getTime());

        return today;
    }

    public int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy");

        return Integer.parseInt(simpleDateFormat.format(calendar.getTime()));
    }
}
