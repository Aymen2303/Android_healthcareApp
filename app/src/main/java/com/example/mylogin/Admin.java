package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mylogin.Classes.SharedPrefManager;

public class Admin extends AppCompatActivity {
        Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


      btn_logout = findViewById(R.id.admin_logout);

      btn_logout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              SharedPrefManager.getInstance(getApplicationContext()).logout();
              finish();
              startActivity(new Intent(Admin.this, MainActivity.class));
          }
      });
    }
}