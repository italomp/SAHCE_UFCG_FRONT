package com.example.sahce_ufcg.activities;


import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.fragments.AppointmentsFragment;
import com.example.sahce_ufcg.fragments.PlacesFragment;
import com.example.sahce_ufcg.fragments.TimesFragment;
import com.example.sahce_ufcg.fragments.UsersFragment;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.responseBodies.UserResponseBody;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
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
        email = Util.getEmailPreferences(this);
        token = Util.getTokenPreferences(this);
    }

    private void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if(item.getItemId() == R.id.menu_item_appointments)
                    replaceFragment(new AppointmentsFragment());
                else if (item.getItemId() == R.id.menu_item_users)
                    replaceFragment(new UsersFragment());
                else if (item.getItemId() == R.id.menu_item_times)
                    replaceFragment(new TimesFragment());
                else if(item.getItemId() == R.id.menu_item_places)
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