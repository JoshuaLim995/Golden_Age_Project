package com.joshua_lsj.goldenage.Experiment;

import com.joshua_lsj.goldenage.Calender;

import java.io.Serializable;

public class Patient implements Serializable{

    private long id;
    private String name;
    private String ic;
    private String gender;
    private String blood_type;
    private Double margin;
    //private Client client; > relative contact
    //relative name
    private int age;
    private String address;
    private String contact;
    private String meals;
    private String allergic;
    private String sickness;
    private String register_date;
    private String register_type;
//    private Image photo;


    //For Nurse use
  //  private String blood_pressure;
  //  private String blood_sugar_level;
 //   private String heart_rate;

    public Patient(){
        this.register_type = "P";
    }
    public Patient(String name, String ic, int age, String gender,
                   String blood_type, String address, String contact,
                   String meals, String allergic, String sickness,
                   String register_date, double margin){
        this.id = 0;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.blood_type = blood_type;
        this.age = age;
        this.address = address;
        this.contact = contact;
        this.meals = meals;
        this.allergic = allergic;
        this.sickness = sickness;
        this.register_date = register_date;
        this.margin = margin;
        this.register_type = "P";
    }

    public Patient(Long id, String name, String ic, int age, String gender,
                   String blood_type, String address, String contact,
                   String meals, String allergic, String sickness,
                   String register_date, double margin){
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.blood_type = blood_type;
        this.age = age;
        this.address = address;
        this.contact = contact;
        this.meals = meals;
        this.allergic = allergic;
        this.sickness = sickness;
        this.register_date = register_date;
        this.margin = margin;
        this.register_type = "P";
    }


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int birthyear) {
        Calender cal = new Calender();
        this.age = cal.getCurrentYear() - birthyear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getAllergic() {
        return allergic;
    }

    public void setAllergic(String allergic) {
        this.allergic = allergic;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getRegister_type() {
        return register_type;
    }
}
