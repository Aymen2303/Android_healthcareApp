package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_GET_OF_ClIENT;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import com.example.mylogin.Classes.Appointment;
import com.example.mylogin.Classes.SharedPrefManager;
import com.example.mylogin.Classes.kid_class;

public class kidFetcher_forClient {




    public interface OnKidsFetchListener {
        void onKidsFetch(List<kid_class> kids);
    }

    public static void fetchClientKids(Context context,String clientId, OnKidsFetchListener listener) {
        new FetchKidsTask(context, clientId, listener).execute();
    }

    private static class FetchKidsTask extends AsyncTask<Void, Void, List<kid_class>> {

        private final Context context;
        private String clientId;
        private OnKidsFetchListener listener;

        public FetchKidsTask(Context context, String clientId, OnKidsFetchListener listener) {
            this.context = context;
            this.clientId = clientId;
            this.listener = listener;
        }



        @Override
        protected List<kid_class> doInBackground(Void... voids) {
            List<kid_class> kids = new ArrayList<>();
            Log.d("MyTag", "Inside doInBackground");

            try {
                // Construct the URL of your PHP file
                String ID_of_Client = String.valueOf(SharedPrefManager.getInstance(context).getID());
                String urlString = URL_GET_OF_ClIENT + ID_of_Client;
                URL url = new URL(urlString);

                // Establish a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set the request method
                connection.setRequestMethod("GET");

                // Read the response from the connection
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();

                // Parse the JSON response
                Log.d("MyTag", "JSON response: " + response.toString());

                Object json = new JSONTokener(response.toString()).nextValue();

                if (json instanceof JSONObject) {
                    // Handle JSON object
                    JSONObject jsonObject = (JSONObject) json;
                    String message = jsonObject.getString("message");
                    Log.d("MyTag", "JSON response message: " + message);
                } else if (json instanceof JSONArray) {
                    // Handle JSON array
                    JSONArray jsonArray = (JSONArray) json;
                    Log.d("MyTag", "JSON response array: " + jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String kidId = jsonObject.getString("ID");
                        String kidName = jsonObject.getString("name");
                        int age = jsonObject.getInt("age");
                        float height = (float) jsonObject.getDouble("height");
                        float weight = (float) jsonObject.getDouble("weight");
                        String lastVaccinationDate = jsonObject.getString("last_vaccination");
                        String lastVaccination = jsonObject.getString("last_vaccine");
                        String clientID = jsonObject.getString("Client_ID");

                        // Check if the kid's client ID matches the specified client ID
                        if (clientID.equals(clientId)) {
                            // Create a KidClass object and add it to the list
                            kid_class kid = new kid_class(kidId, kidName, age, height, weight, lastVaccinationDate, lastVaccination, clientID);
                            kids.add(kid);
                        }
                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return kids;
        }


        @Override
        protected void onPostExecute(List<kid_class> kids) {
            Log.d("MyTag", "Inside onPostExecute");
            listener.onKidsFetch(kids);
        }
    }
}
