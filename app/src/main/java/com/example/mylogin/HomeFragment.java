package com.example.mylogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Classes.SharedPrefManager;
import com.example.mylogin.adapter.Valid_App_ForUser_Adapter;
import com.example.mylogin.Classes.Appointment_Class;
import com.example.mylogin.Api_request.ValidAppForUser_Api;

import java.util.List;

public class HomeFragment extends Fragment implements ValidAppForUser_Api.OnAppointmentsFetchListener {

    private RecyclerView recyclerView;
    private Valid_App_ForUser_Adapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView and adapter
        recyclerView = view.findViewById(R.id.recyclerView5);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Valid_App_ForUser_Adapter(getActivity());
        recyclerView.setAdapter(adapter);

        // Fetch valid appointments for the user
        fetchValidAppointments();

        return view;
    }

    private void fetchValidAppointments() {
        String clientID = String.valueOf(SharedPrefManager.getInstance(getContext()).getID()); // Pass the client ID here
        ValidAppForUser_Api.fetchValidAppointmentsForUser(getActivity(), clientID, this);
    }

    @Override
    public void onAppointmentsFetch(List<Appointment_Class> appointments) {
        // Update the adapter with the fetched appointments
        adapter.setAppointments(appointments);
    }

    @Override
    public void onAppointmentsError(String errorMessage) {
        // Handle error in fetching appointments
    }
}
