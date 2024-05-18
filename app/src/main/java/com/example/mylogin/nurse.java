package com.example.mylogin;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mylogin.Classes.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class nurse extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionButton fab2;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView2;

    TextView txt;
    NavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse);

        /**---------------------- HOOKS ----------------------**/

        navigationView = findViewById(R.id.nav_view_nurse);
        drawerLayout = findViewById(R.id.drawer_layout_nurse);
        bottomNavigationView2 = findViewById(R.id.bottomNavigationView_nurse);
        fab2 = findViewById(R.id.fab_nurse);
        Toolbar toolbar = findViewById(R.id.toolbar_nurse);

        /**---------------------- TOOLBAR ----------------------**/
        setSupportActionBar(toolbar);

        /**---------------------- NAVIGATION MENU ----------------------**/
       navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        /**---------------------- DEFAULT ITEM SELECTION FOR DRAWER ----------------------**/

        Menu menu = navigationView.getMenu();
        MenuItem defaultItem = menu.findItem(R.id.nav_nurse_home);
        if (defaultItem != null) {
            defaultItem.setChecked(true);
        }





        /**---------------------- SETTING TEXT VIEW TO CALL USERS BY PERSONAL USERNAME ----------------------**/
        View headerView = navigationView.getHeaderView(0);
        TextView txtHeader = headerView.findViewById(R.id.txt_nurse_nav_header);
        txtHeader.setText(SharedPrefManager.getInstance(this).getUsername());



        /** ------------------------- Retrieve UserType and set it to this Test--------------------------**/

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            // User is not logged in, redirect to the login activity
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        String userType = SharedPrefManager.getInstance(this).getUserType();

        if(!userType.equals("Nurse ")) {
            // User is not logged in as a nurse, redirect to the login activity
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_nurse, new nurse_HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_nurse_home);
        }



        replaceFragment(new nurse_HomeFragment());

        bottomNavigationView2.setBackground(null);
        bottomNavigationView2.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home_nurse:
                    replaceFragment(new nurse_HomeFragment());
                    break;

              case R.id.nurse_valid_appointments:
                    replaceFragment(new nurse_RequestApp());
            }
            return true;

        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomDialog();
            }

        });



    }

    /*** ------------- Outside Oncreate Methhod ----------- ***/


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
        fragmentTransaction.replace(R.id.frame_layout_nurse, fragment);
        fragmentTransaction.commit();
    }


    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_nurse);

        TextView add_kids = dialog.findViewById(R.id.add_kids_nurse);

        TextView Check_valid_appointments = dialog.findViewById(R.id.check_valid_appointments_nurse);

        ImageView cancelButtonNurse = dialog.findViewById(R.id.close_button_nurse);

        TextView Vaccinekid = dialog.findViewById(R.id.Vaccinate_kid);

        add_kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
              startActivity(new Intent(nurse.this, add_kid.class));
            }
        });

        Check_valid_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(nurse.this, nurse_Valid_Appointments.class));

            }
        });

        Vaccinekid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(nurse.this, kid_vaccination.class));
            }
        });

        cancelButtonNurse.setOnClickListener(new View.OnClickListener() {
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
            case R.id.nav_nurse_home:
                replaceFragment(new nurse_HomeFragment());
                break;
            case R.id.nav_nurse_info:
                startActivity(new Intent(this, nurse_informations.class));
                break;
            case R.id.nav_vacc_info:
                startActivity(new Intent(this, Vaccines_infos.class));
                break;
            case R.id.nav_about_nurse:
                startActivity(new Intent(this, About_me_Activity.class));
                break;
            case R.id.nav_logout_nurse:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public int getId() {
        int nurseId =   SharedPrefManager.getInstance(this).getID();
      return nurseId ;
    }

    public String getName() {
      String  nurseName =  SharedPrefManager.getInstance(this).getUsername();
        return nurseName ;
    }
}
