package com.example.mylogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.MultiDex;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mylogin.Classes.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public  class client extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;

    TextView txt;
     NavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        MultiDex.install(this);


        /*---------------------- HOOKS ----------------------*/
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);



        /*---------------------- TOOLBAR ----------------------*/
        setSupportActionBar(toolbar);


        /*---------------------- NAVIGATION MENU ----------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);




        /*---------------------- DEFAULT ITEM SELECTION FOR DRAWER ----------------------*/
        Menu menu = navigationView.getMenu();
        MenuItem defaultItem = menu.findItem(R.id.nav_about); // replace nav_home with the ID of the item you want to be selected by default
        defaultItem.setChecked(true);




        /*---------------------- SETTING TEXT VIEW TO CALL USERS BY PERSONAL USERNAME ----------------------*/
        View headerView = navigationView.getHeaderView(0);
        TextView txtHeader = headerView.findViewById(R.id.txt_nav_header);
        txtHeader.setText(SharedPrefManager.getInstance(this).getUsername());


  /** ------------------------- Retrieve UserType and set it to this Test--------------------------**/




   /* if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }*/

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            String userType = SharedPrefManager.getInstance(this).getUserType();
            if (!userType.equals("Client")) {
                finish();
                // Redirect to appropriate activity for users with other user types
                startActivity(new Intent(this, nurse.class));
            }
        }






       /* if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            String userType = SharedPrefManager.getInstance(this).getUserType();

            if(userType.equals("Nurse ")) {
                // User is logged in as a nurse, redirect to the nurse activity
                finish();
                startActivity(new Intent(this, nurse.class));
                return;
            }
        } else{
            // User is not logged in
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }*/

     /*  if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            String userType = SharedPrefManager.getInstance(this).getUserType();

            if(userType.equals("Client")) {
                // User is logged in as a client, continue to the client activity
                // Your code for the client activity goes here
            } else {
                // User is logged in but has user_type set to "Nurse" or some other value
                // Redirect to the appropriate activity for their user_type or to the main activity
                redirectToAppropriateActivity(userType);
            }
        } else {
            // User is not logged in, redirect to the main activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }*/





        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


        replaceFragment(new HomeFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.appointments:
                    replaceFragment(new AppointementsFragment());
                    break;
            }
            return true;

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }

        });


    }

    //Outside OnCreate


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        TextView add_appointement = dialog.findViewById(R.id.appointment_text);

        TextView Check_valid_appointments = dialog.findViewById(R.id.check_valid_apps);

        ImageView cancelButton = dialog.findViewById(R.id.close_button);

        add_appointement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                replaceFragment(new AppointementsFragment());

            }
        });

        Check_valid_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Toast.makeText(client.this, "Check valid appointments is clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int Id = item.getItemId();
        switch (Id){
            case R.id.nav_home:
              replaceFragment(new HomeFragment());
                break;
            case R.id.nav_personal_info:
                startActivity(new Intent(this, client_personal_info.class));
                break;
            case R.id.nav_kids_info:
                startActivity(new Intent(this, client_Kids_info.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, About_me_Activity.class));
                break;
            case R.id.nav_logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void redirectToAppropriateActivity(String userType) {
        if (userType.equals("Nurse ")) {
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
