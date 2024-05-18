package com.example.mylogin;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView signupText;
    Button loginbutton;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
         startActivity(new Intent(this, client.class));
            return;
        }


        username = findViewById(R.id.username);
        password = findViewById(R.id.password_login);
        loginbutton = findViewById(R.id.loginbtn);




        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }

        });


        signupText = findViewById(R.id.signupText);

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp_Activity.class);
                startActivity(intent);
            }
        });

    }

    private void userLogin() {
        final String username2 = username.getText().toString().trim();
        final String password2 = password.getText().toString().trim();


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override

                   public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("LoginActivity", "Server response: " + obj.toString());
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        obj.getInt("id"),
                                        obj.getString("username"),
                                        obj.getString("email"),
                                        obj.getString("user_type")
                                );

                              String Usertype = obj.getString("user_type");
                                Log.d("LoginActivity", "User type: " + Usertype);

                               if(Usertype.equals("Nurse ")){
                                   Log.d("LoginActivity", "Launching nurse activity " );
                                   Toast.makeText(getApplicationContext(), "opening nurse activity",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(getApplicationContext(), nurse.class));
                                   finish();
                                   return;
                               }else if(Usertype.equals("Client")){
                                   Log.d("LoginActivity", "Launching client activity");
                                   Toast.makeText(getApplicationContext(), "opening client activity",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(getApplicationContext(), client.class));
                                   finish();
                                  return;
                               }
                               else{
                                   Toast.makeText(getApplicationContext(), "opening Admin activity",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(getApplicationContext(), Admin.class));
                                   finish();
                                   return;
                               }



                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username2);
                params.put("password", password2);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    /******   Nurse Login Void           *****/




    private void redirectToAppropriateActivity(String userType) {
        if (userType.equals("Nurse")) {
            // User is logged in as a nurse, redirect to the nurse activity
            startActivity(new Intent(this, nurse.class));
            finish();
        } else {
            // User is logged in with some other user_type, redirect to the main activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

}