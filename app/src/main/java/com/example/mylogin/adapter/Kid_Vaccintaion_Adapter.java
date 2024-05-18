package com.example.mylogin.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.R;

import java.util.List;

public class Kid_Vaccintaion_Adapter extends RecyclerView.Adapter<Kid_Vaccintaion_Adapter.ViewHolder> {
    private List<kid_class> kidInformationList;

    public Kid_Vaccintaion_Adapter(List<kid_class> kidInformationList) {
        this.kidInformationList = kidInformationList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView kidNameTextView;
        TextView kidIdTextView;
        EditText kidAgeEditText;
        EditText kidHeightEditText;
        EditText kidWeightEditText;
        EditText kidDateEditText;
        EditText kidVaccineEditText;

        public ViewHolder(View itemView) {
            super(itemView);
            kidNameTextView = itemView.findViewById(R.id.kidName_text_vaccine);
            kidIdTextView = itemView.findViewById(R.id.kidId_text_vaccine);
            kidAgeEditText = itemView.findViewById(R.id.kidAge_txt_vaccine);
            kidHeightEditText = itemView.findViewById(R.id.kidHeight_txt_vaccine);
            kidWeightEditText = itemView.findViewById(R.id.kidWeight_txt_vaccine);
            kidDateEditText = itemView.findViewById(R.id.kidDate_txt_vaccine);
            kidVaccineEditText = itemView.findViewById(R.id.kidVaccine_txt_Name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_container, parent, false);
        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        kid_class kidInformation = kidInformationList.get(position);

        holder.kidNameTextView.setText(kidInformation.getKidName());
        holder.kidIdTextView.setText(kidInformation.getKidId());
        holder.kidAgeEditText.setText(kidInformation.getAge());
        holder.kidHeightEditText.setText((int) kidInformation.getHeight());
        holder.kidWeightEditText.setText((int) kidInformation.getWeight());
        holder.kidDateEditText.setText(kidInformation.getLastVaccinationDate());
        holder.kidVaccineEditText.setText(kidInformation.getLastVaccination());
    }

    @Override
    public int getItemCount() {
        return kidInformationList.size();
    }
}


