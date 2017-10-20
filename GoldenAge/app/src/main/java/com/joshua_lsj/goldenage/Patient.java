package com.joshua_lsj.goldenage;

import android.media.Image;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Serializable{

    private long id;
    private String name;
    private String ic;
    //private Client client; > relative contact
    //relative name
    private String birthday;
    private String meals;
    private String register_date;
    private Image photo;
    private String blood_type;

    //For Nurse use
    private String blood_pressure;
    private String blood_sugar_level;
    private String heart_rate;

    public Patient(){}
    public Patient(long id, String name, String ic, String birthday){}
    public void setRelativeInfo(String relative_name, String relative_contact){}
    public void setMeals(){} //
    public void setPhoto(Image photo){}

    public void checkup(String blood_pressure, String blood_sugar_level, String heart_rate){}
}
