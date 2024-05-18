package com.example.mylogin.Api_request;

import static com.example.mylogin.Classes.Constants.URL_Post_Kid;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Kid_PostApi {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String PHP_FILE_URL = URL_Post_Kid;

    public static void addKid(String name, int age, float height, float weight, String clientId) {
        OkHttpClient client = new OkHttpClient();

        // Create the form body
        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
                .add("age", String.valueOf(age))
                .add("height", String.valueOf(height))
                .add("weight", String.valueOf(weight))
                .add("clientId", clientId)
                .build();

        // Create the HTTP POST request
        Request request = new Request.Builder()
                .url(PHP_FILE_URL)
                .post(requestBody)
                .build();

        // Send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // Handle the response
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Handle the failure
            }
        });
    }


}
