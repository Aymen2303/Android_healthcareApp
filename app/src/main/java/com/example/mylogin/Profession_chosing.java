package com.example.mylogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mylogin.Classes.Constants;
import com.example.mylogin.Classes.RequestHandler;
import com.example.mylogin.Classes.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Profession_chosing extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;

    private String username;
    private String email;
    private String password;
    private String userType;

    private ProgressDialog  progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_chosing);


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, client.class));
            return;
        }

        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        progressDialog  = new ProgressDialog(this);



        radioGroup = findViewById(R.id.radiogrp_signin);
        Button btn_check = findViewById(R.id.signin_btn_checkin);


      btn_check.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             String usertype = "";
             int selectedId = radioGroup.getCheckedRadioButtonId();
             RadioButton  selectedradio = findViewById(selectedId);

            userType = selectedradio.getText().toString();
                        registerUser();
                        finish();
                        startActivity(new Intent(Profession_chosing.this, MainActivity.class));

          }

      });
    }


    private void registerUser(){

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest  = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try{
                            JSONObject jsonObject  =  new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Profession_chosing.this, MainActivity.class));
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                params.put("user_type",userType);
                return params;
            }
        };

       RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}