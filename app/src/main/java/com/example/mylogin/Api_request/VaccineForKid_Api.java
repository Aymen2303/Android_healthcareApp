package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_GET_VACCINES_FOR_KID;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylogin.Classes.Vaccine_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VaccineForKid_Api {

    private static final String TAG = VaccineForKid_Api.class.getSimpleName();
    private static final String API_URL = URL_GET_VACCINES_FOR_KID; // Replace with your actual API URL

    public interface VaccineCallback {
        void onSuccess(List<Vaccine_Class> vaccines);
        void onError(String errorMessage);
    }

    public static void getVaccinesForKid(Context context, String kidId, final VaccineCallback callback) {
        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Build the full API URL
        String url = API_URL + kidId;

        // Create a JSON array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Create a list to store the vaccines
                            List<Vaccine_Class> vaccines = new ArrayList<>();

                            // Parse the JSON response
                            // Parse the JSON response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                // Extract vaccine data from JSON
                                int id = jsonObject.getInt("ID");
                                String name = jsonObject.getString("Nom");
                                int age = jsonObject.getInt("Age");

                                // Create a Vaccine_Class object
                                Vaccine_Class vaccine = new Vaccine_Class(id, name, age);

                                // Add the vaccine to the list
                                vaccines.add(vaccine);
                            }


                            // Invoke the success callback with the vaccine list
                            callback.onSuccess(vaccines);
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON parsing error: " + e.getMessage());
                            callback.onError("Failed to parse JSON response.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "API request error: " + error.getMessage());
                        callback.onError("Failed to fetch vaccines.");
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }
}
