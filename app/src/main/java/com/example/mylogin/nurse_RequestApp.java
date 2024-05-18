package com.example.mylogin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Api_request.AppointmentApiService;
import com.example.mylogin.Classes.Appointment;
import com.example.mylogin.adapter.AppointmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class nurse_RequestApp extends Fragment implements AppointmentApiService.AppointmentListener {

    private List<Appointment> appointmentRequests;
    private AppointmentAdapter adapter;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appointmentRequests = new ArrayList<>();
        adapter = new AppointmentAdapter(appointmentRequests);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nurse__request_app, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Fetch appointment requests from API
        AppointmentApiService appointmentApiService = new AppointmentApiService(requireContext(), this);
        appointmentApiService.execute();
    }

    @Override
    public void onAppointmentsReceived(List<Appointment> appointments) {
        // Update the list of appointment requests
        appointmentRequests.clear();
        appointmentRequests.addAll(appointments);
        adapter.notifyDataSetChanged();
    }
}
