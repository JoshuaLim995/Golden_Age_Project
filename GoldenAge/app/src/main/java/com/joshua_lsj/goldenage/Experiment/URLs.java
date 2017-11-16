package com.joshua_lsj.goldenage.Experiment;

/**
 * Created by limsh on 10/26/2017.
 */

public class URLs {

    private static final String IP_ADDRESS = "192.168.1.10"; //192.168.0.110
    //Register Patient Url
    private static final String ROOT_URL = "http://" + IP_ADDRESS + "/Android/";

    private static final String GET_DATA = ROOT_URL + "getdata?apicall=";

    //TODO: Make API for general register account (user, client, patient)

    public static final String LOGIN_URL = ROOT_URL + "Login.php?apicall=login";
    public static final String URL_USER_REGISTER = "http://192.168.1.10/Android/RegisterUser.php?apicall=REGISTER";
    public static final String URL_PATIENT_REGISTER = "http://192.168.1.10/Android/PatientRegister.php?apicall=signup";
    public static final String URL_CLIENT_REGISTER = "";

    public static final String GET_USER_URL = GET_DATA + "users";
    public static final String GET_PATIENT_URL = GET_DATA + "patients";
    public static final String GET_CLIENT_URL = GET_DATA + "clients";


    public static final String URL_UPLOAD_MEDICAL_RECORD = ROOT_URL + "MedicalRecord.php?apicall=medical"; //TODO: make API for Upload Medical Record




    //http://192.168.1.12/Android/getdata?apicall=users
}
