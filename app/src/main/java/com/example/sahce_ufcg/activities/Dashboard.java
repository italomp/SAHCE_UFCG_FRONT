package com.example.sahce_ufcg.activities;


import static com.example.sahce_ufcg.util.Constants.SHARED_LOGIN_PREFERENCES_KEY;
import static com.example.sahce_ufcg.util.Constants.TOKEN_KEY;
import static com.example.sahce_ufcg.util.Constants.USER_EMAIL_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.fragments.PlacesFragment;
import com.example.sahce_ufcg.fragments.AppointmentsFragment;
import com.example.sahce_ufcg.fragments.TimesFragment;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.responseBodies.UserResponseBody;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {
    private String email, token;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_non_admin);
        getPreferences();
        getUserType();
    }

    private void getUserType(){
        ApiService.getUserService().getUser(email, token).enqueue(
                new Callback<UserResponseBody>() {
                    @Override
                    public void onResponse(
                            Call<UserResponseBody> call,
                            Response<UserResponseBody> response
                    ){
                        if(response.isSuccessful()){
                            User.UserType userType = response.body().getUserType();
                            setViews(userType);
                        }
                        else{
                            Util.showMessage(Dashboard.this,
                                    "Código de estado HTTP: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponseBody> call, Throwable t) {
                        Util.showMessage(Dashboard.this, "Falha na requisição");
                    }
                }
        );
    }

    private void getPreferences(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                SHARED_LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String defaultValue = "";
        email = sharedPreferences.getString(USER_EMAIL_KEY, defaultValue);
        token = sharedPreferences.getString(TOKEN_KEY, defaultValue);
    }

    private void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if(item.getItemId() == R.id.menu_item_reserved_times)
                    replaceFragment(new AppointmentsFragment());
                else if (item.getItemId() == R.id.menu_item_times_settings)
                    replaceFragment(new TimesFragment());
                else if(item.getItemId() == R.id.menu_item_places_settings)
                    replaceFragment(new PlacesFragment());
                return false;
            }
        });

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void setViews(User.UserType userType){
        if (userType == User.UserType.ADMIN) {
            setAdminViews();
        } else {
            setNonAdminViews();
        }
    }

    public void setAdminViews(){
        replaceFragment(new AppointmentsFragment());
        setContentView(R.layout.activity_dashboard_admin);
        setBottomNavigationView();
    }

    public void setNonAdminViews(){
        replaceFragment(new AppointmentsFragment());
        // The content view is already defined in onCreate()
    }
}