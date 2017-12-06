package com.joshua_lsj.goldenage.Other;


/**
 * Created by limsh on 10/26/2017.
 */

public class URLs {

    private static final String IP_ADDRESS = "192.168.0.109"; //192.168.0.110
    //Register Patient Url
   private static final String ROOT_URL = "http://" + IP_ADDRESS + "/Android/";

//    private static final String ROOT_URL =  "https://josh95jl.000webhostapp.com/";

//    private static final String GET_DATA = ROOT_URL + "getalldata?apicall=";
//    private static final String DELETE_DATA = ROOT_URL + "Delete?Delete=";
    private static final String UPDATE_DATA = ROOT_URL + "UpdateUser?apicall=";

    //TODO: Make API for general register account (user, client, patient)

    public static final String LOGIN_URL = ROOT_URL + "Login.php?apicall=login";

    public static final String CREATE = ROOT_URL + "API.php?apicall=Create";
    public static final String READ_ALL = ROOT_URL + "API.php?apicall=ReadAll";
    public static final String DELETE = ROOT_URL + "API.php?apicall=Delete";
    public static final String READ_DATA = ROOT_URL + "API.php?apicall=ReadData";
    public static final String URL_UPLOAD_MEDICAL_RECORD = ROOT_URL + "API.php?apicall=Create_Medical";
    public static final String UPDATE = ROOT_URL + "API.php?apicall=Update";
    public static final String CREATE_DRIVER_SCHEDULE = ROOT_URL + "API.php?apicall=Create_Driver_Schedule";

    public static final String URL_IMAGE_FILE = ROOT_URL + "patient_image/";

}
