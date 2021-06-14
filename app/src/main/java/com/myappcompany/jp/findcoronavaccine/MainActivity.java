package com.myappcompany.jp.findcoronavaccine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView searchBy_textView;
    TextView or_textView;

    Button districtname_Button;
    Button pincode_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBy_textView = findViewById(R.id.searchby_textView);
        or_textView = findViewById(R.id.or_textView);

        districtname_Button = findViewById(R.id.districtname_Button);
        pincode_Button = findViewById(R.id.pincode_Button);

        districtname_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchByDistrict_intent = new Intent(getApplicationContext(), SearchByDistrictName_activity.class);
                //searchByDistrict_intent.putExtra("Date", "");
                startActivity(searchByDistrict_intent);
            }
        });

        pincode_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchByPincode_intent = new Intent(getApplicationContext(), SearchByPincode_activity.class);
                startActivity(searchByPincode_intent);
            }
        });

    }
}