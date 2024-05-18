package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import com.example.mylogin.Api_request.kidFetcher_forClient;
import com.example.mylogin.Classes.SharedPrefManager;
import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.adapter.Kid_Adapter;

import java.util.List;

public class client_Kids_info extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Kid_Adapter kidAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_kids_info);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String client_Id = String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getID());
        // Fetch the kids' information
        kidFetcher_forClient.fetchClientKids(this, client_Id, new kidFetcher_forClient.OnKidsFetchListener() {
            @Override
            public void onKidsFetch(List<kid_class> kids) {
                // Create an adapter and set it to the RecyclerView
                kidAdapter = new Kid_Adapter(kids);
                recyclerView.setAdapter(kidAdapter);
            }
        });
    }
}
