package com.example.mylogin.Classes;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class kid_class extends RecyclerView.Adapter {
    private String kidId;
    private String kidName;
    private int age;
    private float height;
    private float weight;
    private String lastVaccinationDate;
    private String lastVaccination;
    private String clientId;

    public kid_class(String kidId, String kidName, int age, float height, float weight, String lastVaccinationDate, String lastVaccination, String clientId) {
        this.kidId = kidId;
        this.kidName = kidName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.lastVaccinationDate = lastVaccinationDate;
        this.lastVaccination = lastVaccination;
        this.clientId = clientId;
    }

    public kid_class(List<kid_class> kidInformationList) {
    }

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }

    public String getKidName() {
        return kidName;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getLastVaccinationDate() {
        return lastVaccinationDate;
    }

    public void setLastVaccinationDate(String lastVaccinationDate) {
        this.lastVaccinationDate = lastVaccinationDate;
    }

    public String getLastVaccination() {
        return lastVaccination;
    }

    public void setLastVaccination(String lastVaccination) {
        this.lastVaccination = lastVaccination;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    // Override the toString() method to return the kid id and name
    @Override
    public String toString() {
        return kidId + " - " + kidName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

