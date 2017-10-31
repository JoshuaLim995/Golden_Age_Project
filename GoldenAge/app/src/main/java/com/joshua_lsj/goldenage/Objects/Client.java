package com.joshua_lsj.goldenage.Objects;

/**
 * Created by limsh on 10/22/2017.
 */

public class Client {

    private long id;
    private String name;
    private String ic;
    private String birthday;
    private String sex;
    private String contact;
    private String address;
    private String register_date;
    private String register_type;
    private String relationship;
    private String password;

    public Client(){this.register_type = "C";}

    public Client(String name, String ic, String birthday, String sex,
                  String address, String contact, String register_date,
                  String relationship){
        this.id = 0;
        this.name = name;
        this.ic = ic;
        this.birthday = birthday;
        this.sex = sex;
        this.address = address;
        this.contact = contact;
        this.register_date = register_date;
        this.register_type = "C";
        this.relationship = relationship;
        this.password = ic;
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

    public String getRelationship() {
        return relationship;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
