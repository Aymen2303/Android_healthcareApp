package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_GET_KIDS;

import android.os.AsyncTask;

import com.example.mylogin.Classes.kid_class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KidsFetcher {

    public interface OnKidsFetchListener {
        void onKidsFetch(List<kid_class> kids);
    }

    public static void fetchKids(OnKidsFetchListener listener) {
        new FetchKidsTask(listener).execute();
    }

    private static class FetchKidsTask extends AsyncTask<Void, Void, List<kid_class>> {

        private OnKidsFetchListener listener;

        public FetchKidsTask(OnKidsFetchListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<kid_class> doInBackground(Void... voids) {
            List<kid_class> kids = new ArrayList<>();

            try {
                // Construct the URL of your PHP file
                URL url = new URL(URL_GET_KIDS);

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
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String kidId = jsonObject.getString("ID");
                    String kidName = jsonObject.getString("name");
                    int age = jsonObject.getInt("age");
                    float height = (float) jsonObject.getDouble("height");
                    float weight = (float) jsonObject.getDouble("weight");
                    String lastVaccinationDate = jsonObject.getString("last_vaccination");
                    String lastVaccination = jsonObject.getString("last_vaccine");
                    String clientId = jsonObject.getString("Client_ID");

                    // Create a KidClass object and add it to the list
                    kid_class kid = new kid_class(kidId, kidName, age, height, weight, lastVaccinationDate, lastVaccination, clientId);
                    kids.add(kid);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return kids;
        }

        @Override
        protected void onPostExecute(List<kid_class> kids) {
            listener.onKidsFetch(kids);
        }
    }
}

