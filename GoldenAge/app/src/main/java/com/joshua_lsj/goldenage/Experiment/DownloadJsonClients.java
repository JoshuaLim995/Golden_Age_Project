package com.joshua_lsj.goldenage.Experiment;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;

import com.joshua_lsj.goldenage.Objects.Client;
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

public class DownloadJsonClients extends AsyncTask<Void, Void, ArrayList<Client>> {




    private Activity activity;

    public DownloadJsonClients(Activity activity){
        this.activity = activity;
    }


    @Override
    protected ArrayList<Client> doInBackground(Void... v) {
        ArrayList<Client> clients = null;

        try {
            clients = downloadJson();
        }
        catch (IOException ex) {
            Log.e("IO_EXCEPTION", ex.toString());
        }

        return clients;
    }

    @Override
    protected void onPostExecute(ArrayList<Client> clients) {
        ListView listView = (ListView)(activity.findViewById(R.id.list_view));

        if(clients != null){
            ClientAdapter adapter = new ClientAdapter(clients, activity);

            listView.setAdapter(adapter);
        }
        else
            Log.d("ERROR:", "No connection");

    }

    private ArrayList<Client> downloadJson() throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(URLs.GET_CLIENT_URL);

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

                // Convert the InputStream into ArrayList<Client>
                ArrayList<Client> clients = readInputStream(is);
                return clients;
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

    private ArrayList<Client> readInputStream(InputStream is)
            throws IOException, UnsupportedEncodingException {

        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return readClientsArray(reader);
        } finally {
            reader.close();
        }
    }

    private ArrayList<Client> readClientsArray(JsonReader reader) throws IOException {
        ArrayList<Client> clients = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            clients.add(readClient(reader));
        }
        reader.endArray();
        return clients;
    }

    private Client readClient(JsonReader reader) throws IOException {
        Client client = new Client();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();    // name variable refers to the name (key) of JSON object
            if (name.equals("ID")) {
                client.setId(reader.nextInt());
            } else if (name.equals("Name")) {
                client.setName(reader.nextString());
            }

            else  if (name.equals("IC")){
                client.setIc(reader.nextString());
            } else if (name.equals("Contact")){
                client.setContact(reader.nextString());
            }else if (name.equals("BirthYear")){
                client.setAge(reader.nextString());
            }else if (name.equals("Address")){
                client.setAddress(reader.nextString());
            }else if (name.equals("Gender")){
                client.setGender(reader.nextString());
            }else if (name.equals("RegisDate")){
                client.setRegister_date(reader.nextString());
            }else if (name.equals("RegisType")){
                client.setRegister_type(reader.nextString());
            }else if(name.equals("Patient_ID")){
                client.setPatientID(reader.nextString());
            }

            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return client;
    }
}
