package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_GET_Clients;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylogin.Classes.Client_Class;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ClientFetcher {
    private static final String URL_FETCH_CLIENTS = URL_GET_Clients;
    private Context context;
    private RequestQueue requestQueue;

    public ClientFetcher(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void fetchClients(final ClientCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_FETCH_CLIENTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse the JSON response
                        List<Client_Class> clients = null;
                        try {
                            clients = parseClients(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // Invoke the callback with the client information
                        callback.onClientsReceived(clients);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Invoke the callback with the error message
                        callback.onClientsError(error.getMessage());
                    }
                });

        requestQueue.add(request);
    }

    private List<Client_Class> parseClients(JSONArray jsonArray) throws JSONException {
        List<Client_Class> clients = new ArrayList<>();

        // Iterate over the JSON array and extract client information
        for (int i = 0; i < jsonArray.length(); i++) {
            int clientId = jsonArray.getJSONObject(i).getInt("id");
            String clientName = jsonArray.getJSONObject(i).getString("username");
            // Add more fields as per your database schema

            Client_Class client = new Client_Class(clientId, clientName);
            clients.add(client);
        }

        return clients;
    }

    public void getClass(ClientCallback clientCallback) {
    }

    public interface ClientCallback {
        void onClientsReceived(List<Client_Class> clients);
        void onClientsError(String errorMessage);
    }
}

