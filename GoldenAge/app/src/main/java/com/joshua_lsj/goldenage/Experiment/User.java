package com.joshua_lsj.goldenage.Experiment;

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
    private String birthdate;   //CONSIDERING USING STRING OR DATE
    private String address;
    private String regisDate;   //CONSIDERING USING STRING OR DATE
    private String regisType;
    //CONSIDERING NEED TO ADD USER_ID OR NOT (USER_ID = REGISTER_TYPE + ID) MAKE IT EASIER BE IDENTIFIED AND UNIQUE

    public User(){}

    public User(int id, String name, String regisType){
        this.id = id;
        this.name = name;
        this.regisType = regisType;
    }

    public User(int id, String name, String ic, String gender, String contact, String regisType){
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.contact = contact;
        this.regisType = regisType;
    }

    public User(int id, String name, String ic, String gender, String contact, String birthdate, String address, String regisDate, String regisType){
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.contact = contact;
        this.birthdate = birthdate;
        this.address = address;
        this.regisDate = regisDate;
        this.regisType = regisType;
    }


    public int getID() {
        return id;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

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
}
