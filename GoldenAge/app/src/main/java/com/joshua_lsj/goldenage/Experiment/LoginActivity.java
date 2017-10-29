package com.joshua_lsj.goldenage.Experiment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joshua_lsj.goldenage.MainActivity;
import com.joshua_lsj.goldenage.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by limsh on 10/29/2017.
 */

public class LoginActivity  extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;

    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin(){

        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }



    class UserLogin extends AsyncTask<Void, Void, String> {



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try{
                JSONObject obj = new JSONObject(s);

                if(!obj.getBoolean("error")){
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

                    JSONObject userJason = obj.getJSONObject("user");



                    //Creating a new user object
                    User user = new User(
                            userJason.getInt("id"),
                            userJason.getString("Name"),
                            userJason.getString("regisType")
                    );

                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }else{
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("Name", username);
            params.put("userPass", password);

            //returing the response
            return requestHandler.sendPostRequest(URLs.LOGIN_URL, params);
        }



    }
}
