package com.example.mylogin;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Api_request.ValidAppointmentsApi;
import com.example.mylogin.Classes.Appointment;
import com.example.mylogin.Classes.SharedPrefManager;
import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.adapter.Valid_Appointment_Adapter;

import java.util.ArrayList;
import java.util.List;

public class nurse_Valid_Appointments extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Valid_Appointment_Adapter adapter;
    private List<Appointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_valid_appointments);

        recyclerView = findViewById(R.id.recyclerView3);
        appointmentList = new ArrayList<>();
        adapter = new Valid_Appointment_Adapter(appointmentList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Create an instance of ValidAppointmentsApi
        ValidAppointmentsApi validAppointmentsApi = new ValidAppointmentsApi(this);

        // Get the valid appointments
        String nurseID = String.valueOf(SharedPrefManager.getInstance(this).getID()); // Replace with the actual nurse ID
        validAppointmentsApi.getValidAppointments(nurseID, new ValidAppointmentsApi.ValidAppointmentsCallback() {
            @Override
            public void onValidAppointmentsReceived(List<Appointment> validAppointments) {
                appointmentList.addAll(validAppointments);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onValidAppointmentsError(String errorMessage) {
                Log.e("ValidAppointmentsApi", "Error loading valid appointments: " + errorMessage);
            }
        });
    }

    /********      Outside OnCreate   ****/
    public void openNurseHomeFragment(kid_class kid) {
        nurse_HomeFragment fragment = new nurse_HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable("kid", (Parcelable) kid);
        fragment.setArguments(args);

        // Replace the current fragment with NurseHomeFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
