package com.joshua_lsj.goldenage.Other;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limsh on 11/18/2017.
 */

public class DeleteHelper {

    private Activity activity;

    public DeleteHelper(Activity activity){
        this.activity = activity;
    }


    public void Delete(final String type, final String id){
       // final boolean[] deleted = {false};
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.DELETE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!obj.getBoolean("error"))
                                activity.finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type", type);
                params.put("id", id);

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(activity).add(multipartRequest);

    //    return deleted[0];
    }
}
