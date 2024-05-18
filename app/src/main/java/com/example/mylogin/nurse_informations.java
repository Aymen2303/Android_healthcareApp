package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mylogin.Classes.SharedPrefManager;

public class nurse_informations extends AppCompatActivity {

    TextView txt1, txt2, txt3;
    Button btn_nurse;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_informations);

        txt1 = findViewById(R.id.username_nurse_info);
        txt2 = findViewById(R.id.email_nurse_info);
        txt3 = findViewById(R.id.account_status_nurse);
        btn_nurse = findViewById(R.id.request_verf_nurse);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        txt1.setText(SharedPrefManager.getInstance(this).getUsername());
        txt2.setText(SharedPrefManager.getInstance(this).getEmail());

        btn_nurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /********* OUTSIDE OnCreate********/
    public void requestVerification(View view) {
        progressDialog.show();
        // Perform the verification request logic here

        // Send the verification request to the admin
        sendVerificationRequest();

        // Show a toast message to inform the nurse that the request has been sent

    }

    private void sendVerificationRequest() {
        // Implement the logic to send the verification request to the admin
        // This can be done through an API call, database update, or any other means of communication with the admin
    }
}