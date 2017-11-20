package com.joshua_lsj.goldenage.Experiment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;



/**
 * Created by limsh on 10/31/2017.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "GoldenAgeSharedPref";
    private static final String KEY_ID = "KeyID";   //CONSIDERING USE THE ID(INT) OR THE USER_ID(REGISTER_TYPE + ID VALUE)
    private static final String KEY_USERNAME = "KeyLoginName";
    private static final String KEY_IC = "KeyIC";
    private static final String KEY_REGISTER_TYPE = "KeyRegisType";
    private static final String KEY_SELECTED_ID = "selectedID";
    private static final String SELECTED_NAV = "Selected_nav";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //THIS WILL STORE USER DATA IN SHARED PREFERENCE
    public void userLogin(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, Integer.parseInt(user.getID().toString()));
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_REGISTER_TYPE, user.getRegisType());
        editor.apply();
    }

    //THIS WILL CHECK WHETHER USER IS ALREADY LOGGED IN OR NOT
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //THIS WILL GET THE LOGGED IN USER'S DATA FROM SHARED PREFERENCE
    public User getUserSharedPref(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_REGISTER_TYPE, null)
        );
    }

    //THIS WILL CLEAR THE SHARED PREFERENCE AND LOGOUT USER
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public void setIdSharedPref(String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SELECTED_ID, id);
        editor.apply();
    }

    public String getKeySelectedId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SELECTED_ID, null);
    }

    public void setSelectedNav(String selectedNav){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_NAV, selectedNav);
        editor.apply();
    }

    public String getSelectedNav(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SELECTED_NAV, null);
    }

}
