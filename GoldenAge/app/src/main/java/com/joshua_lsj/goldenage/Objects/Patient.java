package com.joshua_lsj.goldenage.Objects;

import java.io.Serializable;

public class Patient implements Serializable{

    private int id;
    private String name;
    private String ic;
    private String contact;
    private int age;
    private String address;
    private String gender;
    private String regisDate;
    private String regisType;

    private String blood_type;
    private String meals;
    private String allergic;
    private String sickness;
    private Double deposit;

    private String imageName;

    public Patient(int id, String name){
        Calender calender = new Calender();
        this.id = id;
        this.name = name;
    }

    public Patient(int id, String name, String ic, String contact,
                   int birthyear, String address, String gender,
                   String regisDate, String blood_type, String meals, String allergic, String sickness, double deposit, String imageName){
        Calender calender = new Calender();
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.contact = contact;
        this.age = calender.getCurrentYear() - birthyear;
        this.address = address;
        this.regisDate = regisDate;
        this.regisType = "P";

        this.blood_type = blood_type;
        this.meals = meals;
        this.allergic = allergic;
        this.sickness = sickness;
        this.deposit = deposit;
        this.imageName = imageName;
    }


    public String getID() {
        return Integer.toString(id);
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

    public Double getDeposit() {
        return deposit;
    }


    public String getAge() {
        return Integer.toString(age);
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


    public String getImageName() {
        return imageName;
    }

    public String getRegisDate() {
        return regisDate;
    }

    public String getRegisType() {
        return regisType;
    }
}
