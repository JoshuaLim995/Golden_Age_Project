package com.joshua_lsj.goldenage.Objects;

import java.io.Serializable;

/**
 * Created by limsh on 11/25/2017.
 */

public class Medical implements Serializable {
    private int id;
    private String patient_id;
    private String patient_name;
    private String date;
    private Double blood_pressure;
    private Double sugar_level;
    private Double heart_rate;
    private Double temperature;
    private String nurse_id;
    private String nurse_name;

    //TODO: Edit here to allow Admin/Nurse to record patient daily medical checkup data;;;;



    public Medical(String date, String patient_id, String nurse_id, Double blood_pressure, Double sugar_level,
                   Double heart_rate, Double temperature){

        this.patient_id = patient_id;
        this.date = date;
        this.blood_pressure = blood_pressure;
        this.sugar_level = sugar_level;
        this.heart_rate = heart_rate;
        this.temperature = temperature;
        this.nurse_id = nurse_id;
    }


    public String getPatient_id() {
        return patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getDate() {
        return date;
    }

    public Double getBlood_pressure() {
        return blood_pressure;
    }

    public Double getSugar_level() {
        return sugar_level;
    }

    public Double getHeart_rate() {
        return heart_rate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getNurse_id() {
        return nurse_id;
    }

    public String getNurse_name() {
        return nurse_name;
    }


    public String getID() {
        return Integer.toString(id);
    }
}
