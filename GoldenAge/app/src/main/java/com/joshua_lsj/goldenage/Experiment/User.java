package com.joshua_lsj.goldenage.Experiment;

/**
 * Created by limsh on 10/28/2017.
 */

public class User {
    private int id;
    private String name;
    private String ic;
    private String regisType;

    public User(int id, String name, String regisType){
        this.id = id;
        this.name = name;
        this.regisType = regisType;
    }

    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getRegisType(){
        return regisType;
    }
}
