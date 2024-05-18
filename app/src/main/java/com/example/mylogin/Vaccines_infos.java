package com.example.mylogin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Api_request.Vaccine;
import com.example.mylogin.Classes.Vaccine_Class;
import com.example.mylogin.adapter.VaccineAdapter;

import java.util.ArrayList;
import java.util.List;

public class Vaccines_infos extends AppCompatActivity implements Vaccine.VaccineCallback {

    private RecyclerView recyclerView;
    private VaccineAdapter vaccineAdapter;
    private List<Vaccine_Class> vaccineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccines_infos);

        recyclerView = findViewById(R.id.recyclerView4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vaccineList = new ArrayList<>();
        vaccineAdapter = new VaccineAdapter(vaccineList);
        recyclerView.setAdapter(vaccineAdapter);

        fetchVaccineInformation();
    }

    private void fetchVaccineInformation() {
        Vaccine vaccineApi = new Vaccine(this);
        vaccineApi.getVaccines(this);
    }

    @Override
    public void onVaccineReceived(List<Vaccine_Class> vaccines) {
        vaccineList.clear();
        vaccineList.addAll(vaccines);
        vaccineAdapter.notifyDataSetChanged();
    }

    @Override
    public void onVaccineError(String errorMessage) {
        Toast.makeText(getApplicationContext(), "No Vaccines Found", Toast.LENGTH_SHORT).show();
    }
}
