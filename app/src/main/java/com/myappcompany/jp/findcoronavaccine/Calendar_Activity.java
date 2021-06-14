package com.myappcompany.jp.findcoronavaccine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RelativeLayout;

public class Calendar_Activity extends AppCompatActivity {


    CalendarView date_calenderView;
    Button cancel_Button;
    Button ok_Button;

    String date;

    RelativeLayout calendar_RelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);


        date_calenderView = findViewById(R.id.date_CalendarView);
        cancel_Button = findViewById(R.id.cancel_CalendarButton);
        ok_Button = findViewById(R.id.ok_CalendarButton);

        //calendar_RelativeLayout = (RelativeLayout) findViewById(R.id.calendar_RelativeLayout);

        date = "";


        date_calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String month = "", day = "";
                String year = Integer.toString(i);
                if(i1<9) {
                    month = "0"+Integer.toString(i1+1);
                } else {
                    month = Integer.toString(i1+1);
                }
                if(i2<9) {
                    day = "0"+Integer.toString(i2);
                } else {
                    day = Integer.toString(i2);
                }

                date = day+"-"+month+"-"+year;
                Log.i("DATE SELECTED", date);

            }
        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dateNotSelected = new Intent(getApplicationContext(), SearchByDistrictName_activity.class);
                dateNotSelected.putExtra("Date", "");
                startActivity(dateNotSelected);
            }
        });

        ok_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dateSelected = new Intent(getApplicationContext(), SearchByDistrictName_activity.class);
                dateSelected.putExtra("Date", date);
                startActivity(dateSelected);
            }
        });

    }
}