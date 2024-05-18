package com.example.mylogin;


import static com.example.mylogin.Classes.Constants.URL_POST_APPOINTMENT;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.mylogin.Api_request.kidFetcher_forClient;
import com.example.mylogin.Api_request.kidFetcher_forClient.OnKidsFetchListener;
import com.example.mylogin.Classes.SharedPrefManager;
import com.example.mylogin.Classes.VerificationRequest;
import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.Classes.nurse_class;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;

public class AppointementsFragment extends Fragment implements  OnKidsFetchListener {
    private AppCompatSpinner  spinner2;
    private List<nurse_class> nurses;
    private List<kid_class> kids;

    private ArrayAdapter<nurse_class> adapter;

    private ArrayAdapter<kid_class> adapter2;

    Button send_request ;

    EditText txt_picker;
    Calendar myCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointements, container, false);

        send_request = view.findViewById(R.id.reques_ver_client_toNurse_btn);


        spinner2 = view.findViewById(R.id.spinner_for_Kids);

        adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinner2.setAdapter(adapter2);

        String client_profile_ID = String.valueOf(SharedPrefManager.getInstance(getContext()).getID());









        /*******  Spinner For Kids of Client **********/

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kid_class selectedKid = adapter2.getItem(position);
                if (selectedKid != null) {
                    String kidId = selectedKid.getKidId();
                    String kidName = selectedKid.getKidName();
                    // Handle the selected kid ID and name here
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        kidFetcher_forClient.fetchClientKids(getContext(), client_profile_ID,this);




        /*******    DATE PICKER   ******/

        myCalendar = Calendar.getInstance();
        txt_picker = view.findViewById(R.id.date_pick);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        txt_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), date,  myCalendar.get(Calendar.YEAR),  myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });



        send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestToNurse();
                resetFields();
            }
        });

        return view;
    }

    /**********   Outside Oncreate  **********/

    private void updateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat(myFormat, Locale.UK);
        }
        txt_picker.setText(sdf.format(myCalendar.getTime()));
    }



    @Override
    public void onKidsFetch(List<kid_class> kids) {
        // Get the client ID from SharedPreferences
        int clientId = SharedPrefManager.getInstance(getContext()).getID();

        // Filter the kids list based on the client ID
        List<kid_class> filteredKids = new ArrayList<>();
        for (kid_class kid : kids) {
            if (kid.getClientId().equals(String.valueOf(clientId))) {
                filteredKids.add(kid);
            }
        }

        this.kids = filteredKids;

        // Update the adapter with the filtered list of kids
        if (adapter2 != null) {
            adapter2.clear();
            adapter2.addAll(filteredKids);
            adapter2.notifyDataSetChanged();
        }
    }




    private void sendRequestToNurse() {
        String selectedDate = txt_picker.getText().toString();

        String clientId = String.valueOf(SharedPrefManager.getInstance(getContext()).getID());
        String kidId = getSelectedKidId();
        // Get the client information from your form or database
        String clientInfo = getClientInformation();



        System.out.println("selectedDate: " + selectedDate);
        System.out.println("clientInfo: " + clientInfo);

        // Prepare the verification request

        VerificationRequest request = new VerificationRequest(clientId, "0", selectedDate, kidId);


        sendVerificationRequest(request);
    }

    private String getClientInformation() {
        // Get the SharedPreferences instance
       SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());

        // Retrieve the client information (username and id) from SharedPreferences
        String username = sharedPrefManager.getUsername();
        String id = String.valueOf(sharedPrefManager.getID());

        // Create the client information string
        String clientInfo = "Username: " + username + ", ID: " + id;

        return clientInfo;
    }


    private void sendVerificationRequest(VerificationRequest request) {

        String url = URL_POST_APPOINTMENT;

        // Convert the verification request object to JSON
        String jsonRequest = new Gson().toJson(request);
        System.out.println("Request Data: " + jsonRequest);

        // Create a request body with the JSON data
       // RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonRequest);
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("Client_ID", request.getClientId())
                .add("Nurse_ID", request.getNurseId())
                .add("App_Date", request.getAppointmentDate())
                .add("Kids_ID", request.getKidId());
        // Create the HTTP request
        okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                .url(url)
                .post(formBuilder.build())
                .build();

        // Create an OkHttpClient to execute the request
        OkHttpClient client = new OkHttpClient();

        // Execute the request asynchronously
        client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // Handle request failure
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Failed to send Appointment request.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                // Handle request success
                String responseBody = response.body().string();
                Log.d("Response", responseBody);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Appointment request sent successfully.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    private void resetFields() {
        // Clear EditText fields
        txt_picker.setText("");

        // Reset Spinners to the default position

        spinner2.setSelection(0);
    }

    private String getSelectedKidId() {
        // Retrieve the selected kid ID from the spinner
        kid_class selectedKid = (kid_class) spinner2.getSelectedItem();
        return selectedKid.getKidId();
    }

}






