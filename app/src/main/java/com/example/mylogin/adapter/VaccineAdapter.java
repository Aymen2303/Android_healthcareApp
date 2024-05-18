package com.example.mylogin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Classes.Vaccine_Class;
import com.example.mylogin.R;

import java.util.List;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.ViewHolder> {
    private List<Vaccine_Class> vaccineList;

    public VaccineAdapter(List<Vaccine_Class> vaccineList) {
        this.vaccineList = vaccineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vaccine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vaccine_Class vaccine = vaccineList.get(position);

        String id2 = "id: " + String.valueOf(vaccine.getId());
        String name2 = "Name: " + vaccine.getName();
        String age2 = "Age (1st Dose): "+ String.valueOf(vaccine.getAge()) + " Months";

        holder.txtid.setText(id2);
        holder.txtname.setText(name2);
        holder.txtage.setText(age2);
    }


    @Override
    public int getItemCount() {
        return vaccineList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtname,txtage, txtid;

        // Other views in item_kid.xml

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtid = itemView.findViewById(R.id.vaccine_id);
            txtname = itemView.findViewById(R.id.vaccine_name);
            txtage = itemView.findViewById(R.id.vaccine_age);
            // Initialize other views if necessary
        }
    }
}


