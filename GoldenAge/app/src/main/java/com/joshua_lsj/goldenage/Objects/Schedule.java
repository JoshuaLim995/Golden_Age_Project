package com.joshua_lsj.goldenage.Objects;

import java.io.Serializable;

/**
 * Created by limsh on 12/4/2017.
 */

public class Schedule implements Serializable {
    private int id;
    private String driverName;
    private String patientName;
    private String nurseName;
    private String location;
    private String description;
    private String date;
    private String time;

    public Schedule(int id, String driverName, String patientName, String nurseName, String location, String description, String date, String time){
        this.id = id;
        this.driverName = driverName;
        this.patientName = patientName;
        this.nurseName = nurseName;
        this.location = location;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public String getID() {
        return Integer.toString(id);
    }

    public String getDriverName() {
        return driverName;
    }


    public String getPatientName() {
        return patientName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
