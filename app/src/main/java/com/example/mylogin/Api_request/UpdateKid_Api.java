package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_UPDATE_KID;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UpdateKid_Api {

    public static void updateKid(Context context, String kidId, String age, String height, String weight, String lastVaccination, String lastVaccine, final KidUpdateCallback callback) {
        // Construct the URL with the parameters
        String url = URL_UPDATE_KID + kidId +
                "&age=" + age +
                "&height=" + height +
                "&weight=" + weight +
                "&lastVaccination=" + lastVaccination +
                "&lastVaccine=" + lastVaccine;

        // Create a request queue using Volley
        Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // The update was successful
                        callback.onKidUpdateSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // An error occurred while updating the kid
                        callback.onKidUpdateError(error.getMessage());
                    }
                }));
    }

    public interface KidUpdateCallback {
        void onKidUpdateSuccess(String response);
        void onKidUpdateError(String errorMessage);
    }
}
