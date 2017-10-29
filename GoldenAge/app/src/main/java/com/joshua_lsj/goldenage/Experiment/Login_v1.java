package com.joshua_lsj.goldenage.Experiment;

import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Toast;

import com.joshua_lsj.goldenage.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by limsh on 10/27/2017.
 */

public class Login_v1 extends AsyncTask<Void, Void, String> {

    private String name;
    private String userPass;

    private String response;
    private String lol = "gg";

    /*
    public Login(String name, String userPass){
        this.name = name;
        this.userPass = userPass;
    }
*/




    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{
            JSONObject obj = new JSONObject(s);

            if(!obj.getBoolean("error")){

                JSONObject userJason = obj.getJSONObject("user");

                //Creating a new user object
                User user = new User(
                        userJason.getInt("id"),
                        userJason.getString("name"),
                        userJason.getString("regisType")
                );


            }else{

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
        params.put("Name", "Facehugger");
        params.put("userPass", "123123");

        //returing the response
        return requestHandler.sendPostRequest(URLs.LOGIN_URL, params);
    }

    public String getResponse(){
        return response;
    }

    public String getLol(Sampler.Value value){return lol;}


}
