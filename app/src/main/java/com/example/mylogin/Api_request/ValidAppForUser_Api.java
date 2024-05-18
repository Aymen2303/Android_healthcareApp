package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_GET_VALID_APP_USER;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mylogin.Classes.Appointment_Class;

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

public class ValidAppForUser_Api {

    public interface OnAppointmentsFetchListener {
        void onAppointmentsFetch(List<Appointment_Class> appointments);
        void onAppointmentsError(String errorMessage);
    }

    public static void fetchValidAppointmentsForUser(Context context, String clientID, OnAppointmentsFetchListener listener) {
        String urlString = URL_GET_VALID_APP_USER + clientID;

        new FetchAppointmentsTask(listener).execute(urlString);
    }

    private static class FetchAppointmentsTask extends AsyncTask<String, Void, String> {
        private final OnAppointmentsFetchListener listener;

        public FetchAppointmentsTask(OnAppointmentsFetchListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... urls) {
            String result = null;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                result = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
                listener.onAppointmentsError("Error occurred during API call");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    // Process the JSON array and extract appointment information
                    List<Appointment_Class> appointments = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String appointmentID = jsonObject.getString("ID");
                        String clientID = jsonObject.getString("Client_ID");
                        String nurseID = jsonObject.getString("Nurse_ID");
                        String appointmentDate = jsonObject.getString("App_Date");
                        String kidID = jsonObject.getString("Kids_ID");
                        String approved = jsonObject.getString("Approved");
                        String kidName = jsonObject.getString("kid_name");
                        String nurseName = jsonObject.getString("nurse_name");

                        Appointment_Class appointment = new Appointment_Class(appointmentID, clientID, nurseID, appointmentDate, kidID, approved, kidName, nurseName);
                        appointments.add(appointment);
                    }

                    // Notify the listener that the API call was successful
                    listener.onAppointmentsFetch(appointments);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Notify the listener that an error occurred
                    listener.onAppointmentsError("Error parsing JSON");
                }
            } else {
                // Notify the listener that an error occurred
                listener.onAppointmentsError("No data received from the server");
            }
        }
    }
}
