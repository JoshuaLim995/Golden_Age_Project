package com.joshua_lsj.goldenage.DataBase;

import android.provider.BaseColumns;

/**
 * Created by limsh on 11/28/2017.
 */

public class DatabaseContract implements BaseColumns {
    public static final String NAME = "name";
    public static final String IC = "ic";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String CONTACT = "contact";
    public static final String ADDRESS = "address";
    public static final String REG_DATE = "reg_date";
    public static final String REG_TYPE = "reg_type";



    public class UserContract extends DatabaseContract{
        public static final String TABLE_NAME = "users";
    }

    public class TempUserContract extends DatabaseContract{
        public static final String TABLE_NAME = "temp_users";
        public static final String UPGRADE = "upgrade";
    }

    public class ClientContract extends DatabaseContract {
        public static final String TABLE_NAME = "clients";
        public static final String PATIENT_ID = "patient_id";
    }

    public class PatientContract extends DatabaseContract {
        public static final String TABLE_NAME = "patients";
        public static final String BLOOD_TYPE = "blood_type";
        public static final String MEALS = "meals";
        public static final String ALLERGIC = "allergic";
        public static final String SICKNESS = "sickness";
        public static final String MARGIN = "margin";
        public static final String IMAGE = "image";
    }

    public class MedicalContract extends DatabaseContract {
        public static final String TABLE_NAME = "medical";
        public static final String DATE = "date";
        public static final String PATIENT_ID = "patient_id";
        public static final String NURSE_ID = "nurse_id";
        public static final String BLOOD_PRESSURE = "blood_pressure";
        public static final String SUGAR_LEVEL = "sugar_level";
        public static final String HEART_RATE = "heart_rate";
        public static final String TEMPERATURE = "temperature";
    }
}
