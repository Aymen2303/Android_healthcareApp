package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_GET_APPOINTMENTS;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mylogin.Classes.Appointment;

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

public class AppointmentApiService extends AsyncTask<String, Void, List<Appointment>> {

    private static final String TAG = "AppointmentApiService";
    private Context context;
    private String nurseId;

    public interface AppointmentListener {
        void onAppointmentsReceived(List<Appointment> appointments);
    }

    private AppointmentListener listener;

    public AppointmentApiService(Context context, AppointmentListener listener) {
        this.context = context;
        this.listener = listener;

    }

    @Override
    protected List<Appointment> doInBackground(String... params) {
        String apiUrl = URL_GET_APPOINTMENTS;

        List<Appointment> appointments = new ArrayList<>();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = readResponse(connection.getInputStream());
                appointments = parseAppointments(response);
            } else {
                Log.e(TAG, "Error response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to API: " + e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
        }

        return appointments;
    }

    @Override
    protected void onPostExecute(List<Appointment> appointments) {
        super.onPostExecute(appointments);

        if (appointments == null) {
            // Notify the listener about the error
            if (listener != null) {
                listener.onAppointmentsReceived(new ArrayList<>());
            }
            return;
        }

        // Notify the listener with the appointment list
        if (listener != null) {
            listener.onAppointmentsReceived(appointments);
        }
    }

    private String readResponse(InputStream inputStream) throws IOException {
        StringBuilder response = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

private List<Appointment> parseAppointments(String json) throws JSONException {
    List<Appointment> appointments = new ArrayList<>();

    JSONArray jsonArray = new JSONArray(json);
    for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        String id_of_appointment = jsonObject.getString("ID");
        String clientId = jsonObject.getString("Client_ID");
        String nurseId = jsonObject.getString("Nurse_ID");
        String appointmentDate = jsonObject.getString("App_Date");
        String kidId = jsonObject.getString("Kids_ID");
        String clientUsername = jsonObject.getString("Client_Username");
        String kidName = jsonObject.getString("Kid_Name");

        Appointment appointment = new Appointment(id_of_appointment,clientId, nurseId, appointmentDate, kidId, clientUsername, kidName);
        appointments.add(appointment);
    }

    return appointments;
}





}
