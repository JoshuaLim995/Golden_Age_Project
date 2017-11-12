package com.joshua_lsj.goldenage;

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
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EE, MMM dd yyyy");
        simpleDateFormat.setTimeZone(timeZone);
        String today = simpleDateFormat.format(calendar.getTime());

/*
        System.out.println("Time zone: " + timeZone.getID());
        System.out.println("default time zone: " + TimeZone.getDefault().getID());
        System.out.println();

        System.out.println("UTC:     " + simpleDateFormat.format(calendar.getTime()));
        System.out.println("Default: " + calendar.getTime());
*/
        return today;
    }

    public int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.YEAR;
    }
}
