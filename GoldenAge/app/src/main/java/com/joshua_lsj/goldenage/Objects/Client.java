package com.joshua_lsj.goldenage.Objects;

import com.joshua_lsj.goldenage.Calender;

import java.io.Serializable;

/**
 * Created by limsh on 10/22/2017.
 */

public class Client implements Serializable {

    private long id;
    private String Client_ID; //C001 format
    private String name;
    private String ic;
    private int age;
    private String gender;
    private String contact;
    private String address;
    private String register_date;
    private String register_type;
    private String relationship;
    private String password;
    
    private int patient_id; //patient id
    private String patient_ic; //use in register client in patient ic input
    private String patient_name;

    
    public void setPatientName(String patient_name){
        this.patient_name = patient_name;
    }
    
    public void setPatientIC(String patient_ic){
        this.patient_ic = patient_ic;
    }
    
    public void setPatientID(String patient_id){
        this.patient_id = Integer.parseInt(patient_id);
    }
    
    public String getPatientName(){
        return patient_name;
    }
    
    public String getPatientIC(){
        return patient_ic;
    }
    
    public int getPatientID(){
        return patient_id;
    }

    public Client(){this.register_type = "C";}
/*
    public Client(String name, String ic, String birthday, String gender,
                  String address, String contact, String register_date,
                  String relationship){
        this.id = 0;
        this.name = name;
        this.ic = ic;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.register_date = register_date;
        this.register_type = "C";
        this.relationship = relationship;
        this.password = ic;
    }
*/
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

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getRegister_type() {
        return register_type;
    }

    public void setRegister_type(String register_type) {
        this.register_type = register_type;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int birthyear) {
        Calender calender = new Calender();
        this.age = calender.getCurrentYear() - birthyear;
    }
}
