package com.joshua_lsj.goldenage.Experiment;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;

import com.joshua_lsj.goldenage.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by limsh on 11/4/2017.
 */

public class DownloadJsonUsers extends AsyncTask<Void, Void, ArrayList<User>> {




    private Activity activity;

    public DownloadJsonUsers(Activity activity){
        this.activity = activity;
    }


    @Override
    protected ArrayList<User> doInBackground(Void... v) {
        ArrayList<User> users = null;

        try {
            users = downloadJson();
        }
        catch (IOException ex) {
            Log.e("IO_EXCEPTION", ex.toString());
        }

        return users;
    }

    @Override
    protected void onPostExecute(ArrayList<User> users) {
        ListView listView = (ListView)(activity.findViewById(R.id.list_view));
        UserAdapter adapter = new UserAdapter(users, activity);

        listView.setAdapter(adapter);
    }

    private ArrayList<User> downloadJson() throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(URLs.GET_USER_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == 200 || responseCode == 304) {
                is = conn.getInputStream();

                // Convert the InputStream into ArrayList<Person>
                ArrayList<User> users = readInputStream(is);
                return users;
            }
            else {

                Log.e("HTTP_ERROR", Integer.toString(responseCode));
                return null;
            }
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private ArrayList<User> readInputStream(InputStream is)
            throws IOException, UnsupportedEncodingException {

        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return readPersonsArray(reader);
        } finally {
            reader.close();
        }
    }

    private ArrayList<User> readPersonsArray(JsonReader reader) throws IOException {
        ArrayList<User> persons = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            persons.add(readPerson(reader));
        }
        reader.endArray();
        return persons;
    }

    private User readPerson(JsonReader reader) throws IOException {
        User user = new User();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();    // name variable refers to the name (key) of JSON object
            if (name.equals("id")) {
                user.setId(reader.nextInt());
            } else if (name.equals("Name")) {
                user.setName(reader.nextString());
            }

            else  if (name.equals("ic")){
                user.setIc(reader.nextString());
            } else if (name.equals("contact")){
                user.setContact(reader.nextString());
            }else if (name.equals("birthdate")){
                user.setBirthdate(reader.nextString());
            }else if (name.equals("addr")){
                user.setAddress(reader.nextString());
            }else if (name.equals("gender")){
                user.setGender(reader.nextString());
            }else if (name.equals("regisdate")){
                user.setRegisDate(reader.nextString());
            }else if (name.equals("registype")){
                user.setRegisType(reader.nextString());
            }

            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return user;
    }
}
