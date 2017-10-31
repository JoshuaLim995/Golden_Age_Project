package com.joshua_lsj.goldenage.Objects;

import java.io.Serializable;

/**
 * Created by limsh on 10/22/2017.
 */

public class Nurse implements Serializable {

    private long id;
    private String name;
    private String ic;
    private String birthday;
    private String sex;
    private String contact;
    private String address;
    private String register_date;
    private String register_type;

    //Check it later
    private String password;

    public Nurse(){
        this.register_type = "N";
    }
    public Nurse(String name, String ic, String birthday, String sex,
                 String address, String contact, String register_date){
        this.id = 0;
        this.name = name;
        this.ic = ic;
        this.birthday = birthday;
        this.sex = sex;
        this.address = address;
        this.contact = contact;
        this.register_date = register_date;
        this.register_type = "N";
    }

    public Nurse(long id, String name, String ic, String birthday, String sex,
                 String address, String contact, String register_date){
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.birthday = birthday;
        this.sex = sex;
        this.address = address;
        this.contact = contact;
        this.register_date = register_date;
        this.register_type = "N";
    }

    public long getId() {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
}
