package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mylogin.Classes.SharedPrefManager;

public class client_personal_info extends AppCompatActivity {

    TextView txt1, txt2, txt3;
    Button btn_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_personal_info);


        txt1 = findViewById(R.id.username_client_info);
        txt2 = findViewById(R.id.email_client_info);
        txt3 = findViewById(R.id.account_status_client);
        btn_client = findViewById(R.id.request_verf_client);




        txt1.setText(SharedPrefManager.getInstance(this).getUsername());
        txt2.setText(SharedPrefManager.getInstance(this).getEmail());

        btn_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}