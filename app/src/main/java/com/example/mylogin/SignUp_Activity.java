package com.example.mylogin;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylogin.Classes.SharedPrefManager;


public class SignUp_Activity extends AppCompatActivity {


    TextView login_txt;
    EditText register_username, register_password, register_email, register_password2;
     Button btn_signin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        register_username = findViewById(R.id.username_signin);
        register_email = findViewById(R.id.Email);
        register_password = findViewById(R.id.Password_signin);
        register_password2 = findViewById(R.id.Cnf_password);
        btn_signin = findViewById(R.id.signbtn);
        login_txt = findViewById(R.id.signupText);


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }



        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = register_username.getText().toString();
                String email = register_email.getText().toString();
                String psd = register_password.getText().toString();
                String psdCnf = register_password2.getText().toString();

                if( username.length() == 0 || email.length() == 0 || psd.length() == 0 || psdCnf.length() == 0 ){
                    Toast.makeText(getApplicationContext(), "All fields must be entered!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!email_isvalid(email)){
                        Toast.makeText(getApplicationContext(), "Email is not valid!", Toast.LENGTH_SHORT).show();
                    }
                    else if(psd.compareTo(psdCnf) == 0){
                        if(isValid(psd)){
                            Intent intent = new Intent(SignUp_Activity.this, Profession_chosing.class);
                            intent.putExtra("username", username);
                            intent.putExtra("email", email);
                            intent.putExtra("password", psd);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, alphabets and digits!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Passwords are not the same!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    ////Text that sends you to login activity

      login_txt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent2 = new Intent(SignUp_Activity.this, MainActivity.class);
              startActivity(intent2);
          }
      });


    }



    public static boolean isValid(String password_entered){
        int f1=0, f2=0, f3=0;
        if(password_entered.length() < 8){
            return false;
        }
            else {
                for(int m=0; m< password_entered.length(); m++){
                    if(Character.isLetter(password_entered.charAt(m))){
                        f1 = 1;
                    }
                }

                for (int n=0; n< password_entered.length(); n++){
                    if(Character.isDigit(password_entered.charAt(n))){
                        f2 = 1;
                    }
                }

                for (int k=0; k<password_entered.length(); k++){

                    char c = password_entered.charAt(k);
                    if(c>=33 &&c<=46 ||c==64){
                        f3 = 1;
                    }

                }
                if(f1==1 || f2==  1 ||  f3==1)
                    return true;

                return false;

            }
    }

    public static boolean email_isvalid(String email_entered){
        String pattern =   "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email_entered.matches(pattern)) return true;
        else return false;
    }


}