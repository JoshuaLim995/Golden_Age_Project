package com.joshua_lsj.goldenage;

import android.provider.BaseColumns;

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
}
