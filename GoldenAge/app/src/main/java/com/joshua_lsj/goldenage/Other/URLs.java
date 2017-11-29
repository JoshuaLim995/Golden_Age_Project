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

/*
    public static final String URL_USER_REGISTER = ROOT_URL + "RegisterUser.php?apicall=REGISTER";
    public static final String URL_PATIENT_REGISTER = ROOT_URL + "PatientRegister.php?apicall=signup";
//    public static final String URL_CLIENT_REGISTER = ROOT_URL +  "RegisterClient.php?apicall=REGISTER";

    public static final String GET_USER_URL = GET_DATA + "users";
    public static final String GET_PATIENT_URL = GET_DATA + "patients";
    public static final String GET_CLIENT_URL = GET_DATA + "clients";

    public static final String DELETE_USER_URL = DELETE_DATA + "User";
    public static final String DELETE_PATIENT_URL = DELETE_DATA + "Patient";
    public static final String DELETE_CLIENT_URL = DELETE_DATA + "Client";
*/
    public static final String UPDATE_USER_URL = UPDATE_DATA + "User";


    public static final String UPDATE_CLIENT_URL = ROOT_URL + "UpdateClient?apicall=Client";
    public static final String UPDATE_PATIENT_URL = ROOT_URL + "UpdatePatient?apicall=Patient";


    public static final String URL_IMAGE_FILE = ROOT_URL + "patient_image/";



/*
    public static final String GET_USER = ROOT_URL + "getdata?apicall=User";
    public static final String GET_CLIENT = ROOT_URL + "getdata?apicall=Client";
    public static final String GET_PATIENT = ROOT_URL + "getdata?apicall=Patient";

    public static final String GET_PATIENT_MEDICAL = ROOT_URL + "getPatientMedical";
*/

    //http://192.168.1.12/Android/getdata?apicall=users
}
