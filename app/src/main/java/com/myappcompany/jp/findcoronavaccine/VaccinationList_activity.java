package com.myappcompany.jp.findcoronavaccine;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VaccinationList_activity extends AppCompatActivity {

    ArrayList<Map<String, String>> centerList;                         // To store all the detalis of all vaccination centers

    RecyclerView vaccine_list_recyclerView;
    Vaccine_Adapter vaccine_adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){//, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);//, persistentState);
        setContentView(R.layout.activity_vaccine_list);
        setTitle("Vaccine Centers");

        Toast.makeText(getApplicationContext(),"HEY, Vaccine Centers", Toast.LENGTH_SHORT).show();

        centerList = new ArrayList<>();

        Intent getUrl = getIntent();
        String vaccine_url = getUrl.getStringExtra("Vaccine URL");

        Log.i("LINE START", " \n\n");
        Log.i("VACCINELIST CLASS", "ENTERED");
        Log.i("VACCINE URL",vaccine_url);
        Log.i("LINE END", " \n\n");

        getVaccine getVaccineDetails = new getVaccine();
        getVaccineDetails.execute(vaccine_url);

        vaccine_list_recyclerView= findViewById(R.id.vaccineList_recyclerView);
        vaccine_adapter = new Vaccine_Adapter(centerList);

        vaccine_list_recyclerView.setLayoutManager(new LinearLayoutManager(VaccinationList_activity.this));
        vaccine_list_recyclerView.setAdapter(vaccine_adapter);

/*

Details for API Setu under JSONObject - sessions

        String center_id;
        String name;                                                        //**
        String name_1;
        String address;                                                     //**
        String address_1;
        String state_name;                                                  //**
        String district_name;                                               //**
        String district_name_1;
        String block_name;                                                  //**
        String block_name_1;
        String pincode;                                                     //**
        String lat;                         //latitude
        String long;                        //longitude
        String from;                        //time
        String to;                          //time
        String fee_type;
        String fee;                                                         //**
        String session_id;
        String date;                                                        //**
        String available_capacity;
        String available_capacity_dose1;                                    //**
        String available_capacity_dose2;                                    //**
        String min_age_limit;                                               //**
        String vaccine;                     //array                         //**
        String slots;                       //array                         //**



        JSON DATA below :

        center_id : 553843
        name : District NCD Clinic
        name_1 : **
        address : Gumla
        address_1 : **
        state_name : Jharkhand
        district_name : Gumla
        district_name_1 : **
        block_name : Gumla Sadar
        block_name_1 : **
        pincode : 835207
        lat : 23
        longi : 84
        from : 09:00:00
        to : 15:00:00
        fee_type : Free
        fee : **
        session_id : 72b9ce9f-a176-4c51-9256-419ac0c064ff
        date : 24-06-2021
        available_capacity : 82
        available_capacity_dose1 : 82
        available_capacity_dose2 : 0
        min_age_limit : 45
        vaccine : COVISHIELD
        slots : ["09:00AM-10:00AM","10:00AM-11:00AM","11:00AM-12:00PM","12:00PM-03:00PM"]


*/


    }


    // getVaccine class to get vaccine details from APISetu
    class getVaccine extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            Log.i("LINE", "\n\n");
            Log.i("CENTRE SEARCH", "BEGIN");
            Log.i("LINE", "\n\n");

            String result = "";
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1) {
                    char c = (char) data;
                    result += c;
                    data = reader.read();
                }
                Log.i("LINE BEGIN", "\n\n");
                Log.i("VACCINATION DETAILS", result);
                Log.i("LINE END", "\n\n");

                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("LINE", "\n\n");
            Log.i("CENTRE SEARCH", "END & SUCCESSFULL");
            Log.i("LINE", "\n\n");

            try {
                JSONObject jsonObject = new JSONObject(s);
                String vaccine_center_jsonObject = jsonObject.getString("sessions");
                //Log.i("JSON OBJECT", " \n \n "+vaccine_center_jsonObject+"\n \n ");
                JSONArray vaccine_center_jsonArray = new JSONArray(vaccine_center_jsonObject);

                for (int i = 0; i < vaccine_center_jsonArray.length(); i++) {
                    JSONObject current_center = vaccine_center_jsonArray.getJSONObject(i);

                    centerList.add(new HashMap<String, String>());

                    String name_1 = "", address_1 = "", district_name_1 = "", block_name_1 = "", fee = "";

                    String center_id = current_center.getString("center_id");                                       //1
                    String name = current_center.getString("name");                                                 //2
                    String address = current_center.getString("address");                                           //3
                    String state_name = current_center.getString("state_name");                                     //4
                    String district_name = current_center.getString("district_name");                               //5
                    String block_name = current_center.getString("block_name");                                     //6
                    String pincode = current_center.getString("pincode");                                           //7
                    String lat = current_center.getString("lat");                                                   //8         latitude
                    String longi = current_center.getString("long");                                                //9         longitude
                    String from = current_center.getString("from");                                                 //10         time
                    String to = current_center.getString("to");                                                     //11        time
                    String fee_type = current_center.getString("fee_type");                                         //12
                    String session_id = current_center.getString("session_id");                                     //13
                    String date = current_center.getString("date");                                                 //14
                    String available_capacity = current_center.getString("available_capacity");                     //15
                    String available_capacity_dose1 = current_center.getString("available_capacity_dose1");         //16
                    String available_capacity_dose2 = current_center.getString("available_capacity_dose2");         //17
                    String min_age_limit = current_center.getString("min_age_limit");                               //18
                    String vaccine = current_center.getString("vaccine");                                           //19        array  --> an array of vaccine will be stored in it like ["abc","pqr",.....]
                    String slots = current_center.getString("slots");                                               //20        array  --> an array of vaccine will be stored in it like ["abc","pqr",.....]

                    try {
                        if (current_center.getString("fee") != null) {
                            fee = current_center.getString("fee");                                //21
                        }
                        if (current_center.getString("name_1") != null) {
                            name_1 = current_center.getString("name_1");                                            //22
                        }
                        if (current_center.getString("address_1") != null) {
                            address_1 = current_center.getString("address_1");                                      //23
                        }
                        if (current_center.getString("district_name_1") != null) {
                            district_name_1 = current_center.getString("district_name_1");                          //24
                        }
                        if (current_center.getString("block_name_1") != null) {
                            block_name_1 = current_center.getString("block_name_1");                                //25
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }//array

                    //toDo: to separate vaccine into array vaccine
                    //toDo: to separate slots into array slots

                    centerList.get(centerList.size() - 1).put("center_id", center_id);                                                                      //1
                    centerList.get(centerList.size() - 1).put("name", name);                                                                                //2
                    centerList.get(centerList.size() - 1).put("address", address);                                                                          //3
                    centerList.get(centerList.size() - 1).put("state_name", state_name);                                                                    //4
                    centerList.get(centerList.size() - 1).put("district_name", district_name);                                                              //5
                    centerList.get(centerList.size() - 1).put("block_name", block_name);                                                                    //6
                    centerList.get(centerList.size() - 1).put("pincode", pincode);                                                                          //7
                    centerList.get(centerList.size() - 1).put("lat", lat);                                                                                  //8
                    centerList.get(centerList.size() - 1).put("longi", longi);                                                                              //9
                    centerList.get(centerList.size() - 1).put("from", from);                                                                                //10
                    centerList.get(centerList.size() - 1).put("to", to);                                                                                    //11
                    centerList.get(centerList.size() - 1).put("fee_type", fee_type);                                                                        //12
                    centerList.get(centerList.size() - 1).put("session_id", session_id);                                                                    //13
                    centerList.get(centerList.size() - 1).put("date", date);                                                                                //14
                    centerList.get(centerList.size() - 1).put("available_capacity", available_capacity);                                                    //15
                    centerList.get(centerList.size() - 1).put("available_capacity_dose1", available_capacity_dose1);                                        //16
                    centerList.get(centerList.size() - 1).put("available_capacity_dose2", available_capacity_dose2);                                        //17
                    centerList.get(centerList.size() - 1).put("min_age_limit", min_age_limit);                                                              //18
                    centerList.get(centerList.size() - 1).put("vaccine", vaccine);                                                                          //19
                    centerList.get(centerList.size() - 1).put("slots", slots);                                                                              //20
                    centerList.get(centerList.size() - 1).put("fee", fee);                                                                                  //21
                    centerList.get(centerList.size() - 1).put("name_1", name_1);                                                                            //22
                    centerList.get(centerList.size() - 1).put("address_1", address_1);                                                                      //23
                    centerList.get(centerList.size() - 1).put("district_name_1", district_name_1);                                                          //24
                    centerList.get(centerList.size() - 1).put("block_name_1", block_name_1);                                                                //25


                    Log.i("CENTRE " + Integer.toString(i), " \ncenter_id : " + center_id
                            + "\nname : " + name
                            + "\nname_1 : " + name_1
                            + "\naddress : " + address
                            + "\naddress_1 : " + address_1
                            + "\nstate_name : " + state_name
                            + "\ndistrict_name : " + district_name
                            + "\ndistrict_name_1 : " + district_name_1
                            + "\nblock_name : " + block_name
                            + "\nblock_name_1 : " + block_name_1
                            + "\npincode : " + pincode
                            + "\nlat : " + lat
                            + "\nlongi : " + longi
                            + "\nfrom : " + from
                            + "\nto : " + to
                            + "\nfee_type : " + fee_type
                            + "\nfee : " + fee
                            + "\nsession_id : " + session_id
                            + "\ndate : " + date
                            + "\navailable_capacity : " + available_capacity
                            + "\navailable_capacity_dose1 : " + available_capacity_dose1
                            + "\navailable_capacity_dose2 : " + available_capacity_dose2
                            + "\nmin_age_limit : " + min_age_limit
                            + "\nvaccine : " + vaccine
                            + "\nslots : " + slots + "\n");

                }

                System.out.println(centerList);

                vaccine_adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    // End of getVaccine class

}


