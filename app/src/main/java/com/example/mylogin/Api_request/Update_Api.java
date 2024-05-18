package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_UPDATE_APPOINTMENT_STATUS;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Update_Api {
    private Context context;
    private RequestQueue requestQueue;

    public Update_Api(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    // Method for updating the 'Approved' status and 'Nurse_ID' of an appointment
    public void updateAppointmentStatus(String appointmentID, String nurseID, AppointmentUpdateCallback callback) {
        String API_ENDPOINT_UPDATE_STATUS = URL_UPDATE_APPOINTMENT_STATUS + appointmentID + "&Nurse_ID=" + nurseID;

        StringRequest request = new StringRequest(Request.Method.POST, API_ENDPOINT_UPDATE_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Update was successful
                        callback.onAppointmentStatusUpdated();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Update failed
                        callback.onAppointmentStatusUpdateError("Failed to update status");
                    }
                });

        requestQueue.add(request);
    }

    // Callback interface for appointment status update
    public interface AppointmentUpdateCallback {
        void onAppointmentStatusUpdated();

        void onAppointmentStatusUpdateError(String errorMessage);
    }
}



