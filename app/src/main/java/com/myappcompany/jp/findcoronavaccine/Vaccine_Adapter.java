package com.myappcompany.jp.findcoronavaccine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class Vaccine_Adapter extends RecyclerView.Adapter<Vaccine_Adapter.Vaccine_Center_ViewHolder>{

    ArrayList<Map<String, String>> centers;

    public Vaccine_Adapter(ArrayList<Map<String, String>> centerList) {
        this.centers = centerList;
    }

    @NonNull
    @Override
    public Vaccine_Center_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccine_center_layout,parent, false);
        return new Vaccine_Center_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Vaccine_Center_ViewHolder holder, int position) {
        String add1 = centers.get(position).get("address");
        String add2 = centers.get(position).get("block_name") + ", " + centers.get(position).get("district_name");
        String add3 = centers.get(position).get("state_name") + "-" + centers.get(position).get("pincode");
        String fee = centers.get(position).get("fee_type");
        if(!fee.equals("Free")) {
            fee = "Rs." + centers.get(position).get("fee");
        }

        holder.center.setText(centers.get(position).get("name"));
        holder.address1.setText(add1);
        holder.address2.setText(add2);
        holder.address3.setText(add3);
        holder.agelimit.setText("Age-limit: " + centers.get(position).get("min_age_limit"));
        holder.slots.setText("Slots: " + centers.get(position).get("slots"));
        holder.date.setText("Date: " + centers.get(position).get("date"));

        holder.vaccine.setText(centers.get(position).get("vaccine"));
        holder.fee.setText(fee);
        holder.dose1.setText(centers.get(position).get("available_capacity_dose1"));                    //"Dose 1: " +
        holder.dose2.setText(centers.get(position).get("available_capacity_dose2"));                    //"Dose 2: " +

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
        TextView slots;

        TextView vaccine;
        TextView fee;
        TextView dose1;
        TextView dose2;

        public Vaccine_Center_ViewHolder(@NonNull View itemView) {
            super(itemView);

            center = itemView.findViewById(R.id.center_name_textView);
            address1 =  itemView.findViewById(R.id.center_address1_textView);
            address2 = itemView.findViewById(R.id.center_address2_textView);
            address3 = itemView.findViewById(R.id.center_address3_textView);
            agelimit = itemView.findViewById(R.id.center_agelimit_textView);
            date = itemView.findViewById(R.id.center_date_textView);
            slots = itemView.findViewById(R.id.center_slots_textView);

            vaccine = itemView.findViewById(R.id.center_vaccine_textView);
            fee = itemView.findViewById(R.id.center_feetype_textView);
            dose1 = itemView.findViewById(R.id.center_dose1_textView);
            dose2 = itemView.findViewById(R.id.center_dose2_textView);

        }
    }

}
