package com.joshua_lsj.goldenage.Objects;

import java.io.Serializable;

/**
 * Created by limsh on 10/28/2017.
 */

public class User implements Serializable {
    private int id;
    private String name;
    private String ic;
    private String gender;
    private String contact;
    private String address;
    private String regisDate;
    private String regisType;
    private int age;


    public User(int id, String name, String regisType){
        this.id = id;
        this.name = name;
        this.regisType = regisType;
    }

    public User(int id, String name, String ic, String contact, int birthyear, String address, String gender, String regisDate, String regisType){
       Calender calender = new Calender();
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.contact = contact;
        this.age = calender.getCurrentYear() - birthyear;
        this.address = address;
        this.regisDate = regisDate;
        this.regisType = regisType;
    }

    public User(String name, String ic, String contact, int birthyear, String address, String gender, String regisDate, String regisType){
        Calender calender = new Calender();
        this.id = 0;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.contact = contact;
        this.age = calender.getCurrentYear() - birthyear;
        this.address = address;
        this.regisDate = regisDate;
        this.regisType = regisType;
    }


    public String getID() {
        return  Integer.toString(id);
    }

    public void setId(int id) {
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
/*
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
*/
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(String regisDate) {
        this.regisDate = regisDate;
    }

    public String getRegisType() {
        return regisType;
    }

    public void setRegisType(String regisType) {
        this.regisType = regisType;
    }

    public String getAge() {
        return Integer.toString(age);
    }

    public void setAge(int birthyear) {
        Calender calender = new Calender();
        this.age = calender.getCurrentYear() - birthyear;

    }
}
