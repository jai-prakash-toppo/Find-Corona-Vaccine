package com.myappcompany.jp.findcoronavaccine;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class Vaccine_Adapter extends RecyclerView.Adapter<Vaccine_Adapter.Vaccine_Center_ViewHolder>{

    ArrayList<Map<String, String>> centers;

    public Vaccine_Adapter(ArrayList<Map<String, String>> centerList) {
        this.centers = centerList;
    }

    @NonNull
    @Override
    public Vaccine_Center_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccine_center_layout_2,parent, false);
        return new Vaccine_Center_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Vaccine_Center_ViewHolder holder, int position) {

        String slot_time1[] = {"09:00AM-10:00AM","10:00AM-11:00AM","11:00AM-12:00PM","12:00PM-03:00PM"};
        String slot_time2[] = {"09:00AM-11:00AM","11:00AM-01:00PM","01:00PM-03:00PM","03:00PM-04:00PM"};
        String slot_time3[] = {"09:00AM-10:00AM","10:00AM-11:00AM","11:00AM-12:00PM","12:00PM-04:30PM"};
        String slot_time4[] = {"09:00AM-11:00AM", "11:00AM-12:30PM","12:30PM-03:00PM","03:00PM-04:30PM"};

        String add1 = centers.get(position).get("address");
        String add2 = centers.get(position).get("block_name") + ", " + centers.get(position).get("district_name");
        String add3 = centers.get(position).get("state_name") + "-" + centers.get(position).get("pincode");
        String fee = centers.get(position).get("fee_type");
        String Slots = centers.get(position).get("slots").trim();
        Slots = Slots.substring(2,Slots.length()-2);

        String slot_arr[] = Slots.split("\",\"");

        String timing[] = {};
        if(slot_arr.length == 4) {
            timing = slot_arr;
        } else{
            if(slot_arr[1].equals(slot_time1[1])) {
                timing = slot_time1;
            } else if(slot_arr[1].equals(slot_time2[1])) {
                timing = slot_time2;
            } else if(slot_arr[1].equals(slot_time3[1])) {
                timing = slot_time3;
            } else if(slot_arr[1].equals(slot_time4[1])) {
                timing = slot_time4;
            } else {
                timing = slot_time1;
            }
        }

        if(slot_arr.length == 4) {
            holder.slot1.setTextColor(Color.parseColor("#0AF613"));
            holder.slot2.setTextColor(Color.parseColor("#0AF613"));
            holder.slot3.setTextColor(Color.parseColor("#0AF613"));
            holder.slot4.setTextColor(Color.parseColor("#0AF613"));
        } else{
            holder.slot1.setTextColor(Color.parseColor("#0AF613"));
            holder.slot2.setTextColor(Color.parseColor("#0AF613"));
        }

        holder.slot1.setText(timing[0]);
        holder.slot2.setText(timing[1]);
        holder.slot3.setText(timing[2]);
        holder.slot4.setText(timing[3]);

        if(!fee.equals("Free")) {
            fee = "Paid : Rs." + centers.get(position).get("fee");
            holder.fee.setTextColor(Color.parseColor("#FF3300"));
        }

        holder.center.setText(centers.get(position).get("name"));
        holder.address1.setText(add1);
        holder.address2.setText(add2);
        holder.address3.setText(add3);
        holder.agelimit.setText("Age-limit: " + centers.get(position).get("min_age_limit") + "+");
        //holder.slots.setText("Slots: " + Slots);//centers.get(position).get("slots"));
        holder.date.setText("Date: " + centers.get(position).get("date"));

        holder.vaccine.setText(centers.get(position).get("vaccine"));
        holder.fee.setText(fee);
        String available_dose1 = centers.get(position).get("available_capacity_dose1");
        String available_dose2 = centers.get(position).get("available_capacity_dose2");
        holder.dose1.setText(available_dose1);                    //"Dose 1: " +
        holder.dose2.setText(available_dose2);                    //"Dose 2: " +

        if(!available_dose1.equals("0")) {
            holder.dose1.setTextColor(Color.parseColor("#0AF613"));
        }

        if(!available_dose2.equals("0")) {
            holder.dose2.setTextColor(0xFF00FF00);
        }

    }

    @Override
    public int getItemCount() {
        return centers.size();
    }

    public class Vaccine_Center_ViewHolder extends RecyclerView.ViewHolder{

        TextView center;
        TextView address1;
        TextView address2;
        TextView address3;
        TextView agelimit;
        TextView date;
        TextView slot1;
        TextView slot2;
        TextView slot3;
        TextView slot4;

        TextView vaccine;
        TextView fee;
        TextView dose1;
        TextView dose2;

        Button bookSlot;

        public Vaccine_Center_ViewHolder(@NonNull View itemView) {
            super(itemView);

            center = itemView.findViewById(R.id.center_name_textView);
            address1 =  itemView.findViewById(R.id.center_address1_textView);
            address2 = itemView.findViewById(R.id.center_address2_textView);
            address3 = itemView.findViewById(R.id.center_address3_textView);
            agelimit = itemView.findViewById(R.id.center_agelimit_textView);
            date = itemView.findViewById(R.id.center_date_textView);

            slot1 = itemView.findViewById(R.id.slot1_textView);
            slot2 = itemView.findViewById(R.id.slot2_textView);
            slot3 = itemView.findViewById(R.id.slot3_textView);
            slot4 = itemView.findViewById(R.id.slot4_textView);

            vaccine = itemView.findViewById(R.id.center_vaccine_textView);
            fee = itemView.findViewById(R.id.center_feetype_textView);
            dose1 = itemView.findViewById(R.id.center_dose1_textView);
            dose2 = itemView.findViewById(R.id.center_dose2_textView);

            bookSlot = itemView.findViewById(R.id.bookslot_Button);

        }
    }

}
