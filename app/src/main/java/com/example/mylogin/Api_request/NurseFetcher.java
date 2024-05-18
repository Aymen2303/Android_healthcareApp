package com.example.mylogin.Api_request;

import android.os.AsyncTask;

import com.example.mylogin.Classes.nurse_class;

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

public class NurseFetcher {

    public interface OnNurseFetchListener {
        void onNurseFetch(List<nurse_class> nurses);
    }

    public static void fetchNurses(OnNurseFetchListener listener) {
        new FetchNursesTask(listener).execute();
    }

    private static class FetchNursesTask extends AsyncTask<Void, Void, List<nurse_class>> {

        private OnNurseFetchListener listener;

        public FetchNursesTask(OnNurseFetchListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<nurse_class> doInBackground(Void... voids) {
            List<nurse_class> nurses = new ArrayList<>();

            try {
                // Construct the URL of your PHP file
                URL url = new URL("http://192.168.1.109/database/SelectNurses.php");

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
                    String nurseId = jsonObject.getString("id");
                    String nurseName = jsonObject.getString("username");

                    // Create a NurseClass object and add it to the list
                    nurse_class nurse = new nurse_class(nurseId, nurseName);
                    nurses.add(nurse);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return nurses;
        }

        @Override
        protected void onPostExecute(List<nurse_class> nurses) {
            listener.onNurseFetch(nurses);
        }
    }
}
