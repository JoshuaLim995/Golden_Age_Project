package com.joshua_lsj.goldenage.Experiment;

/**
 * Created by limsh on 10/26/2017.
 */

public class URLs {

    private static final String IP_ADDRESS = "192.168.1.10"; //192.168.0.110
    //Register Patient Url
    private static final String ROOT_URL = "http://" + IP_ADDRESS + "/Android/PatientRegister.php?apicall=";

    private static final String GET_DATA = "http://192.168.1.10/Android/getdata?apicall=";

    public static final String LOGIN_URL = "http://192.168.1.10/Android/Login.php?apicall=login";
    public static final String URL_USER_REGISTER = "http://192.168.1.10/Android/RegisterUser.php?apicall=REGISTER";
    public static final String URL_PATIENT_REGISTER = "http://192.168.1.10/Android/PatientRegister.php?apicall=signup";

    public static final String GET_USER_URL = GET_DATA + "users";

    //http://192.168.1.12/Android/getdata?apicall=users
}
