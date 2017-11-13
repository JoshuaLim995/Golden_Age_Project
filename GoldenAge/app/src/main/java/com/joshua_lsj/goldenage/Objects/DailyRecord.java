package com.joshua_lsj.goldenage.Objects;

import com.joshua_lsj.goldenage.Experiment.Patient;

import java.io.Serializable;

/**
 * Created by limsh on 10/23/2017.
 */

public class DailyRecord implements Serializable {

    private Long id;
    private String patient_id;
    private String patient_name;
    private String date;
    private String blood_pressure;
    private String sugar_level;
    private String heart_rate;
    private String temperature;
    private String nurse_id;
    private String nurse_name;

    //TODO: Edit here to allow Admin/Nurse to record patient daily medical checkup data;;;;
    /*
    public DailyRecord(Patient patient, String date, String blood_pressure, String sugar_level,
                       String heart_rate, String temperature, Nurse nurse){

        this.patient_id = patient.getRegister_type() + patient.getId().toString();
        this.patient_name = patient.getName();
        this.date = date;
        this.blood_pressure = blood_pressure;
        this.sugar_level = sugar_level;
        this.heart_rate = heart_rate;
        this.temperature = temperature;
        this.nurse_id = nurse.getRegister_type() + nurse.getId();
        this.nurse_name = nurse.getName();
    }
*/

    public String getPatient_id() {
        return patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getDate() {
        return date;
    }

    public String getBlood_pressure() {
        return blood_pressure;
    }

    public String getSugar_level() {
        return sugar_level;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getNurse_id() {
        return nurse_id;
    }

    public String getNurse_name() {
        return nurse_name;
    }

    public void setId(Long id){
        this.id = id;
    }
}
