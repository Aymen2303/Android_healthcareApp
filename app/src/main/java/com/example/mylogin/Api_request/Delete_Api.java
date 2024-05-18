package com.example.mylogin.Api_request;


import static com.example.mylogin.Classes.Constants.URL_DELETE_APPOINTMENT;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Delete_Api {
    private Context context;
    private RequestQueue requestQueue;

    public Delete_Api(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void deleteAppointment(String appointmentID, final AppointmentDeleteCallback callback) {



        String API_ENDPOINT_DELETE_APPOINTMENT = URL_DELETE_APPOINTMENT + appointmentID;

        // Create a StringRequest with the appropriate URL, method, and listener
        StringRequest request = new StringRequest(Request.Method.DELETE, API_ENDPOINT_DELETE_APPOINTMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Deletion was successful
                        callback.onAppointmentDeleted();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Deletion failed
                        callback.onAppointmentDeleteError("Failed to delete appointment");
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(request);
    }

    public interface AppointmentDeleteCallback {
        void onAppointmentDeleted();
        void onAppointmentDeleteError(String errorMessage);
    }
}
