package com.example.mylogin.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.R;

import java.util.List;

public class Kid_Adapter extends RecyclerView.Adapter<Kid_Adapter.KidViewHolder> {




    private List<kid_class> kids;


    public Kid_Adapter(List<kid_class> kids) {
        this.kids = kids;
    }

    @NonNull
    @Override
    public KidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kid, parent, false);
        return new KidViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull KidViewHolder holder, int position) {
        kid_class kid = kids.get(position);
        String name = "Name: " + kid.getKidName();
        String age = "Age: " + kid.getAge();
        String height = "Height: " + kid.getHeight() +" cm";
        String weight = "Weight: " + kid.getWeight() + " kg";
        String lastVaccinationDate = "Last Vaccination Date: " + kid.getLastVaccinationDate();
        String lastVaccination = "Last Vaccine: " + kid.getLastVaccination();

        holder.txtName.setText(name);
        holder.txtAge.setText(age);
        holder.txtHeight.setText(height);
        holder.txtWeight.setText(weight);
        holder.txtLastVaccinationDate.setText(lastVaccinationDate);
        holder.txtLastVaccination.setText(lastVaccination);
    }



    @Override
    public int getItemCount() {

        return kids.size();
    }

    public static class KidViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAge, txtHeight, txtWeight, txtLastVaccinationDate, txtLastVaccination;

        public KidViewHolder(@NonNull View itemView) {

            super(itemView);


            txtName = itemView.findViewById(R.id.txtName);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtHeight = itemView.findViewById(R.id.txtHeight);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtLastVaccinationDate = itemView.findViewById(R.id.txtLastVaccinationDate);
            txtLastVaccination = itemView.findViewById(R.id.txtLastVaccination);
        }
    }


}
