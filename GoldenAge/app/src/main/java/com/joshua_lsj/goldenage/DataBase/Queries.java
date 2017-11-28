package com.joshua_lsj.goldenage.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joshua_lsj.goldenage.Objects.User;

/**
 * Created by limsh on 11/28/2017.
 */

public class Queries {
    DatabaseHelper helper;

    public Queries(DatabaseHelper helper){
        this.helper = helper;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(
                tableName,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }


    public Long insert(User user){
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        if(user.getID() != null)
            values.put(DatabaseContract.UserContract._ID, user.getID());
        values.put(DatabaseContract.UserContract.NAME, user.getName());
        values.put(DatabaseContract.UserContract.IC, user.getIc());
        values.put(DatabaseContract.UserContract.GENDER, user.getGender());
        values.put(DatabaseContract.UserContract.AGE, user.getAge());
        values.put(DatabaseContract.UserContract.CONTACT, user.getContact());
        values.put(DatabaseContract.UserContract.ADDRESS, user.getAddress());
        values.put(DatabaseContract.UserContract.REG_DATE, user.getRegisDate());
        values.put(DatabaseContract.UserContract.REG_TYPE, user.getRegisType());

        return database.insertWithOnConflict(DatabaseContract.UserContract.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


//CONSIDERING INSERT INTO THE TEMP USER DATABASE







    //Delete
    public boolean delete(String table, String id){
        SQLiteDatabase database = helper.getWritableDatabase();
        return database.delete(table, DatabaseContract._ID + "=" + id, null) > 0;
    }
}
