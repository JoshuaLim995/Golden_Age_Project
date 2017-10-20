package com.joshua_lsj.goldenage;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

/**
 * Created by limsh on 10/18/2017.
 */

public class AddPatientActivity extends AppCompatActivity{

    private EditText etName;
    private EditText etIC;
    private EditText etRelativeContact;
    private EditText etRelativeName;
    private EditText etBirthday;
    private EditText etRegisterDate;

    private CheckBox cbPork;
    private CheckBox cbFish;
    private CheckBox cbVege;
    private CheckBox cbOther;
    private EditText etOtherMeal;

    private Image photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




    }
}
