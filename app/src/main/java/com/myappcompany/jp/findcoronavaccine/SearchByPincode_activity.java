package com.myappcompany.jp.findcoronavaccine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class SearchByPincode_activity extends AppCompatActivity {

    String pincodeText;
    String pincode_date;

    EditText pincode_editText;
    TextView pincode_date_textView;
    Button pincodeSearch_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_pincode_activity);
        setTitle("Select Pincode");

        pincodeText = "";
        pincode_date = "";

        pincode_editText = findViewById(R.id.pincode_editText);
        pincode_date_textView = findViewById(R.id.pincode_date_textView);

        pincodeSearch_Button = findViewById(R.id.pincodeSearch_Button);

        pincode_date_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalender = Calendar.getInstance();
                int year = myCalender.get(Calendar.YEAR);
                int month = myCalender.get(Calendar.MONTH);
                int day = myCalender.get(Calendar.DAY_OF_MONTH);

                long currentDateTime = System.currentTimeMillis();

                DatePickerDialog getDate = new DatePickerDialog(SearchByPincode_activity.this);
                getDate.getDatePicker().setMinDate(currentDateTime - 1000);
                getDate.getDatePicker().setMaxDate(currentDateTime + (1000*60*60*24*7));
                getDate.updateDate(year, month, day);
                getDate.show();
                getDate.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 += 1;
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

                        pincode_date = dd + "-" + mm + "-" + yy;
                        pincode_date_textView.setText(pincode_date);
                    }
                });
            }
        });

        pincodeSearch_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pincodeText = pincode_editText.getText().toString();
                pincode_date = pincode_date_textView.getText().toString();
                //https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=110001&date=31-03-2021
                String pincode_url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pincodeText+"&date="+pincode_date;
                Log.i("PIN CODE URL", pincode_url);
                Intent pincode_intent = new Intent(getApplicationContext(), VaccinationList_activity.class);
                pincode_intent.putExtra("Vaccine URL", pincode_url);
                startActivity(pincode_intent);

                //ToDo: Add Something for the exception like when there are no vaccination centers for a pincode        --> Done on 06-07-2021
                //ToDo: Add a progress bar/circle for showing while searching                                           --> Done on 06-07-2021
                //ToDo: Also add the "Book Slot Button" in the "vaccine_center_layout_2.xml" and add a function to open the below link when this button is clicked  --> Done on 06-07-2021
                //ToDo: https://selfregistration.cowin.gov.in/

            }
        });

    }
}