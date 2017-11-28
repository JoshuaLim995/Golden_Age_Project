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
    }
}
