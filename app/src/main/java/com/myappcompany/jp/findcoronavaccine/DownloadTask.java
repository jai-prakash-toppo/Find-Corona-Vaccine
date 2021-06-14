package com.myappcompany.jp.findcoronavaccine;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class  DownloadTask extends AsyncTask<String, Void, String> {


    ArrayList<String> allStates = new ArrayList<>();

    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection;
        try {

            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();
            while (data != -1) {
                char current_data = (char) data;
                result = result + current_data;
                data = reader.read();
            }
            Log.i("RESULT**************************", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void  onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("SEARCH", "SUCCESSFULL***********************************");

        try{
            JSONObject jsonObject = new JSONObject(s);
            String states = jsonObject.getString("states");
            JSONArray states_jsonArray = new JSONArray(states);
            for(int i = 0; i<states_jsonArray.length(); i++) {
                JSONObject current_state = states_jsonArray.getJSONObject(i);
                String state_id = current_state.getString("state_id");
                String state_name = current_state.getString("state_name");
                Log.i("STATE ID "+state_id,state_name);
                allStates.add(state_name +" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}