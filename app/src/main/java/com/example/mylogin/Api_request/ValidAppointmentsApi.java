package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_VALID_APPOINTMETNS_ONLY;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylogin.Classes.Appointment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ValidAppointmentsApi {
    private static final String BASE_URL = URL_VALID_APPOINTMETNS_ONLY;

    private Context context;
    private RequestQueue requestQueue;

    public ValidAppointmentsApi(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void getValidAppointments(String nurseID, ValidAppointmentsCallback callback) {
        String url = BASE_URL + nurseID;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Appointment> validAppointments = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject appointmentObj = response.getJSONObject(i);

                                // Parse the appointment data from the JSON object
                                String id_of_appointment = appointmentObj.getString("ID");
                                String clientId = appointmentObj.getString("Client_ID");
                                String nurseId = appointmentObj.getString("Nurse_ID");
                                String appointmentDate = appointmentObj.getString("App_Date");
                                String kidId = appointmentObj.getString("Kids_ID");
                                String clientUsername = appointmentObj.getString("Client_Username");
                                String kidName = appointmentObj.getString("Kid_Name");

                                Appointment appointment = new Appointment(id_of_appointment,clientId, nurseId, appointmentDate, kidId, clientUsername, kidName);
                                validAppointments.add(appointment);

                            }

                            // Callback with the valid appointments list
                            callback.onValidAppointmentsReceived(validAppointments);
                        } catch (JSONException e) {
                            Log.e("ValidAppointmentsApi", "JSON parsing error: " + e.getMessage());
                            callback.onValidAppointmentsError("Error parsing JSON response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ValidAppointmentsApi", "API request error: " + error.getMessage());
                        callback.onValidAppointmentsError("API request error");
                    }
                });

        requestQueue.add(request);
    }

    public interface ValidAppointmentsCallback {
        void onValidAppointmentsReceived(List<Appointment> validAppointments);

        void onValidAppointmentsError(String errorMessage);
    }
}

