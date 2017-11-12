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

public class DownloadJsonPatients extends AsyncTask<Void, Void, ArrayList<Patient>> {




    private Activity activity;

    public DownloadJsonPatients(Activity activity){
        this.activity = activity;
    }


    @Override
    protected ArrayList<Patient> doInBackground(Void... v) {
        ArrayList<Patient> patients = null;

        try {
            patients = downloadJson();
        }
        catch (IOException ex) {
            Log.e("IO_EXCEPTION", ex.toString());
        }

        return patients;
    }

    @Override
    protected void onPostExecute(ArrayList<Patient> patients) {
        ListView listView = (ListView)(activity.findViewById(R.id.list_view));

        if(patients != null){
            PatientAdapter adapter = new PatientAdapter(patients, activity);

            listView.setAdapter(adapter);
        }
        else
            Log.d("ERROR:", "No connection");
    }

    private ArrayList<Patient> downloadJson() throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(URLs.GET_PATIENT_URL);

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
                ArrayList<Patient> users = readInputStream(is);
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

    private ArrayList<Patient> readInputStream(InputStream is)
            throws IOException, UnsupportedEncodingException {

        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return readPatientsArray(reader);
        } finally {
            reader.close();
        }
    }

    private ArrayList<Patient> readPatientsArray(JsonReader reader) throws IOException {
        ArrayList<Patient> persons = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            persons.add(readPaient(reader));
        }
        reader.endArray();
        return persons;
    }

    private Patient readPaient(JsonReader reader) throws IOException {
        Patient patient = new Patient();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();    // name variable refers to the name (key) of JSON object
            if (name.equals("id")) {
                patient.setId(reader.nextInt());
            } else if (name.equals("name")) {
                patient.setName(reader.nextString());
            } else if (name.equals("ic")){
                patient.setIc(reader.nextString());
            } else if (name.equals("Birthyear")){
                patient.setAge(Integer.parseInt(reader.nextString()));
            } else if (name.equals("gender")){
                patient.setGender(reader.nextString());
            } else if (name.equals("bloodType")){
                patient.setBlood_type(reader.nextString());
            } else if (name.equals("address")){
                patient.setAddress(reader.nextString());
            } else if (name.equals("contact")){
                patient.setContact(reader.nextString());
            } else if (name.equals("meals")){
                patient.setMeals(reader.nextString());
            } else if (name.equals("allegic")){
                patient.setAllergic(reader.nextString());
            } else if (name.equals("sickness")){
                patient.setSickness(reader.nextString());
            } else if (name.equals("regisdate")){
                patient.setRegister_date(reader.nextString());
            } else if (name.equals("margin")){
                patient.setMargin(Double.parseDouble(reader.nextString()));
            }

            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return patient;
    }
}
