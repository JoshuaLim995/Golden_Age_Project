package com.joshua_lsj.goldenage;

import android.provider.BaseColumns;
import android.util.StringBuilderPrinter;

/**
 * Created by limsh on 10/21/2017.
 */

public class DatabaseContract {
    public class PatientContract implements BaseColumns {
        public static final String TABLE_NAME = "patient";
        public static final String NAME = "name";
        public static final String IC = "ic";
        public static final String BIRTH_DATE = "birth_date";
        public static final String SEX = "sex";
        public static final String BLOOD_TYPE = "blood_type";
        public static final String ADDRESS = "address";
        public static final String CONTACT = "contact";
        public static final String MEALS = "meals";
        public static final String ALLERGIC = "allergic";
        public static final String SICKNESS = "sickness";
        public static final String REG_TYPE = "reg_type";
        public static final String REG_DATE = "reg_date";
        public static final String MARGIN = "deposit";
        public static final String PHOTO = "photo";
        public static final String CLIENT_ID = "client_id";
    }

    public class ClientContract implements BaseColumns {
        public static final String TABLE_NAME = "client";
        public static final String NAME = "name";
        public static final String IC = "ic";
        public static final String BIRTH_DATE = "birth_date";
        public static final String SEX = "sex";
        public static final String ADDRESS = "address";
        public static final String CONTACT = "contact";
        public static final String REG_TYPE = "reg_type";
        public static final String REG_DATE = "reg_date";
        public static final String RELATIONSHIP = "relationship";
        public static final String PASSWORD = "password";
    }

    public class NurseContract implements BaseColumns {
        public static final String TABLE_NAME = "nurse";
        public static final String NAME = "name";
        public static final String IC = "ic";
        public static final String BIRTH_DATE = "birth_date";
        public static final String SEX = "sex";
        public static final String ADDRESS = "address";
        public static final String CONTACT = "contact";
        public static final String REG_TYPE = "reg_type";
        public static final String REG_DATE = "reg_date";
    }

    public class DriverContract implements BaseColumns {
        public static final String TABLE_NAME = "driver";
        public static final String NAME = "name";
        public static final String IC = "ic";
        public static final String BIRTH_DATE = "birth_date";
        public static final String SEX = "sex";
        public static final String ADDRESS = "address";
        public static final String CONTACT = "contact";
        public static final String REG_TYPE = "reg_type";
        public static final String REG_DATE = "reg_date";
    }

    public class DailyRecordContract implements BaseColumns {
        public static final String TABLE_NAME = "DailyRecord";
        public static final String PATIENT_ID = "patient_id";
        public static final String PATIENT_NAME = "patient_name";
        public static final String DATE = "date";
        public static final String BLOOD_PRESSURE = "blood_pressure";
        public static final String SUGAR_LEVEL = "sugar_level";
        public static final String HEART_RATE = "heart_rate";
        public static final String TEMPERATURE = "temperature";
        public static final String NURSE_ID = "nurse_id";
        public static final String NURSE_NAME = "nurse_name";
    }
}
