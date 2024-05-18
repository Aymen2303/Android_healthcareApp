package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_GET_Vaccine;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylogin.Classes.Vaccine_Class;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class Vaccine {
    private static final String URL_GET_VACCINES = URL_GET_Vaccine;

    private Context context;
    private RequestQueue requestQueue;

    public Vaccine(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void getVaccines(VaccineCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_VACCINES, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse the JSON response
                        List<Vaccine_Class> Vaccine = null;
                        try {
                            Vaccine = parseVaccines(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // Invoke the callback with the vaccine information
                        callback.onVaccineReceived(Vaccine);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Invoke the callback with the error message
                        callback.onVaccineError(error.getMessage());
                    }
                });

        requestQueue.add(request);
    }

    private List<Vaccine_Class> parseVaccines(JSONArray jsonArray) throws JSONException {
        List<Vaccine_Class> vaccine = new ArrayList<>();

        // Iterate over the JSON array and extract vaccine information
        for (int i = 0; i < jsonArray.length(); i++) {


            int VaccineID = Integer.parseInt(jsonArray.getJSONObject(i).getString("ID"));
            String VaccineName = jsonArray.getJSONObject(i).getString("Nom");
            int VaccineAge = Integer.parseInt(jsonArray.getJSONObject(i).getString("Age"));


            Vaccine_Class vaccines= new Vaccine_Class(VaccineID,VaccineName,VaccineAge);
            vaccine.add(vaccines);
        }

        return vaccine;
    }

    public interface VaccineCallback {
        void onVaccineReceived(List<Vaccine_Class> vaccines);

        void onVaccineError(String errorMessage);
    }
}

