package com.myappcompany.jp.findcoronavaccine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SearchByDistrictName_activity extends AppCompatActivity {


    Spinner state_Spinner;
    Spinner district_Spinner;
    TextView district_date_TextView;
    //ConstraintLayout district_ConstraintLayout;
    //RelativeLayout district_Calendar_RelativeLayout;

    ArrayAdapter<String> stateAdapter;
    ArrayAdapter<String> districtAdapter;

    ArrayList states_list;
    ArrayList<String> districts_list;
    ArrayList<String> districts_ids_list;

    int selected_state_id;
    int selected_district_id;
    String selected_state;
    String selected_district;
    String date_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_district_name_activity);

        state_Spinner = findViewById(R.id.state_Spinner);
        district_Spinner = findViewById(R.id.district_Spinner);
        district_date_TextView = findViewById(R.id.district_TextViewDate);

        //district_ConstraintLayout = (ConstraintLayout) findViewById(R.id.selectDistrict_ConstraintLayout);
        //district_Calendar_RelativeLayout = (RelativeLayout) findViewById(R.id.selectDistrict_RelativeLayout);
        //district_Calendar_RelativeLayout.setVisibility(View.INVISIBLE);

        String states[] = {"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh",
                "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir",
                "Jharkhand", "Karnataka", "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya",
                "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
                "Uttar Pradesh", "Uttarakhand", "West Bengal"};

        states_list = new ArrayList<>(Arrays.asList(states));
        districts_list = new ArrayList<>();
        districts_ids_list = new ArrayList<>();

        selected_district_id = -1;
        selected_state_id = -1;
        selected_district = "";
        selected_state = "";
        date_selected = "";
/*
        Intent dateSelected = getIntent();
        date_selected = dateSelected.getStringExtra("Date");
        if (date_selected.equals(""))
            district_date_TextView.setText("Select Date");
        else
            district_date_TextView.setText(date_selected);
*/
        stateAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.select_dialog_item, states_list);
        state_Spinner.setAdapter(stateAdapter);

        //Log.i("START", "#################################################");

        //DownloadTask2 state_task = new DownloadTask2();
        //state_task.execute("https://cdn-api.co-vin.in/api/v2/admin/location/states");

        districtAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.select_dialog_item, districts_list);
        district_Spinner.setAdapter(districtAdapter);

        //Log.i("MIDDLE", "+++++++++++++++++++++++++++++++++++++++++++++++++");


        state_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i < 8) {
                    selected_state_id = i + 1;
                } else if (i == 8) {
                    selected_state_id = 36;
                } else {
                    selected_state_id = i;
                }

                selected_state = (String) states_list.get(i);

                Log.i("STATE SELECTED", selected_state + "\t State Id : " + selected_state_id);

                getDistrictsTask district_task = new getDistrictsTask();
                district_task.execute("https://cdn-api.co-vin.in/api/v2/admin/location/districts/" + Integer.toString(selected_state_id));

/*                Log.i("CHECKING", "|||||||||||||||||||||||||||||||||||||||||||||");
                Log.i("DISTRICT LIST 3", "\n\n" + districts_list.toString());
                Log.i("ID LIST 3", "\n\n" + districts_ids_list.toString());
                Log.i("CHECKING COMPLETED", "///////////////////////////////////////");
*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        district_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_district_id = Integer.parseInt(districts_ids_list.get(i));
                selected_district = districts_list.get(i);
                Log.i("DISTRICT SELECTED", selected_district + "\tDistrict Id : " + Integer.toString(selected_district_id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Log.i("STATES LIST", states_list.toString());
        //Log.i("STOP", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");


        district_date_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 /*               Intent selectDate = new Intent(getApplicationContext(), Calendar_Activity.class);
                startActivity(selectDate);
                //setContentView(R.layout.select_calendar_layout);
                //district_ConstraintLayout.setVisibility(View.INVISIBLE);
                //district_Calendar_RelativeLayout.setVisibility(View.VISIBLE);
 */
                Calendar myCalender = Calendar.getInstance();
                int year = myCalender.get(Calendar.YEAR);
                int month = myCalender.get(Calendar.MONTH);
                int day = myCalender.get(Calendar.DAY_OF_MONTH);

                long currentDateTime = System.currentTimeMillis();

                DatePickerDialog getDate = new DatePickerDialog(SearchByDistrictName_activity.this);
                getDate.getDatePicker().setMinDate(currentDateTime - 1000);
                getDate.getDatePicker().setMaxDate(currentDateTime + (1000*60*60*24*7));
                getDate.updateDate(year, month, day);
                getDate.show();
                getDate.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String dd = " ", mm = " ", yy = " ";
                        if(i2<9)
                            dd = "0" + Integer.toString(i2);
                        else
                            dd = Integer.toString(i2);
                        if(i1<9)
                            mm = "0" + Integer.toString(i1);
                        else
                            mm = Integer.toString(i1);
                        yy = Integer.toString(i);

                        date_selected = dd + "-" + mm + "-" + yy;
                        district_date_TextView.setText(date_selected);
                    }
                });

            }
        });

    }

    public void findVaccineByDistrict(View view) {

        Toast.makeText(getApplicationContext(), "State ID: " + Integer.toString(selected_state_id) + "\nState: " + selected_state + "\nDistrict ID: "
                + Integer.toString(selected_district_id) + " , " + "\nDistrict: " + selected_district, Toast.LENGTH_SHORT).show();
        getVaccine getVaccineDetails = new getVaccine();
        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=" + Integer.toString(selected_district_id) + "&date=" + date_selected;
        getVaccineDetails.execute(url);

        //Todo: able to get state and districts from the setu API      --> Done
        //Todo: now add the calendar to get the date for vaccine and   --> Done
        //Todo: finish this method for search button                   --> Done
        //Todo: Create a RecylerView with CardView
        //Todo: Retrive data from the JSON data received from SetuAPI
        //Todo : Store the data in the ArrayList as per their tag
        //Todo : Show the Vaccination data on the RecyclerView
        //Todo: To search from setu API for vaccine using District API and show the avaliable slot in a new acitivity using card view and recycler view
    }


    // Begining of getDistrictTask class *************************************************

    public class getDistrictsTask extends AsyncTask<String, Void, String> {

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
                //Log.i("RESULT**************************", result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("SEARCH", "SUCCESSFULL***********************************");
            try {
                ArrayList<String> allDistricts = new ArrayList<>();
                ArrayList<String> allDistrict_ids = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(s);
                String jSONdistricts = jsonObject.getString("districts");
                JSONArray districts_jsonArray = new JSONArray(jSONdistricts);

                for (int i = 0; i < districts_jsonArray.length(); i++) {
                    JSONObject current_district = districts_jsonArray.getJSONObject(i);
                    String district_id = current_district.getString("district_id");
                    String district_name = current_district.getString("district_name");
                    //Log.i("DISTRICT ID " + district_id, district_name);
                    allDistricts.add(district_name);
                    allDistrict_ids.add(district_id);
                }
                districts_list.clear();
                districts_list.addAll(allDistricts);
                districtAdapter.notifyDataSetChanged();

                districts_ids_list.clear();
                districts_ids_list.addAll(allDistrict_ids);
 /*               Log.i("DISTRICT LIST 22222", "\n"+districts_list.toString());
                Log.i("ID LIST 22222", "\n"+districts_ids_list.toString());
*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //End of getDistrictTask Class *******************************************************


    public static class getVaccine extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
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
                Log.i("VACCINATION DETAILS", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @NonNull
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }


}