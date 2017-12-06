package com.joshua_lsj.goldenage.Objects;

import java.io.Serializable;

/**
 * Created by limsh on 10/22/2017.
 */

public class Client implements Serializable {

    private int id;
    private String name;
    private String ic;
    private String gender;
    private String contact;
    private String address;
    private String regisDate;   //CONSIDERING USING STRING OR DATE
    private String regisType;
    private int age;
    private String password;
    
    private String patient_id; //patient id
    private String patient_ic; //use in register client in patient ic input
    private String patient_name;

    public Client(int id, String name, String ic, String contact, int birthyear, String gender,
                  String address, String regisDate, String patient_id){
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.contact = contact;
        setAge(birthyear);
        this.address = address;
        this.regisDate = regisDate;
        this.regisType = "C";
        this.patient_id = patient_id;
    }

    public Client(int id, String name, String ic, String contact, int birthyear, String gender,
                  String address, String regisDate, String patient_id, String patient_name){
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.contact = contact;
        setAge(birthyear);
        this.address = address;
        this.regisDate = regisDate;
        this.regisType = "C";
        this.patient_id = patient_id;
        this.patient_name = patient_name;
    }

    public Client(int id, String name){
        this.id = id;
        this.name = name;
        this.regisType = "C";
    }

    public Client(int id, String name, String patient_id){
        this.id = id;
        this.name = name;
        this.regisType = "C";
        this.patient_id = patient_id;
    }

    

    public String getPatientName(){
        return patient_name;
    }

    public String getPatientID(){
        return patient_id;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return Integer.toString(age);
    }

    public void setAge(int birthyear) {
        Calender calender = new Calender();
        this.age = calender.getCurrentYear() - birthyear;
    }

    public String getRegisDate() {
        return regisDate;
    }

    public String getRegisType() {
        return regisType;
    }
}
