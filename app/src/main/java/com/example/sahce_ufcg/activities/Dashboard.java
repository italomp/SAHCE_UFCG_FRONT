package com.example.sahce_ufcg.activities;


import static com.example.sahce_ufcg.util.Constants.SHARED_LOGIN_PREFERENCES_KEY;
import static com.example.sahce_ufcg.util.Constants.TOKEN_KEY;
import static com.example.sahce_ufcg.util.Constants.USER_EMAIL_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.responseBodies.UserResponseBody;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {
    private String email, token;
    private User.UserType userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getPreferences();
        getUserType();
    }

    public void getUserType(){
        ApiService.getUserService().getUser(email, token).enqueue(
                new Callback<UserResponseBody>() {
                    @Override
                    public void onResponse(
                            Call<UserResponseBody> call,
                            Response<UserResponseBody> response
                    ){
                        if(response.isSuccessful()){
                            userType = response.body().getUserType();
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

    public void getPreferences(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                SHARED_LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String defaultValue = "";
        email = sharedPreferences.getString(USER_EMAIL_KEY, defaultValue);
        token = sharedPreferences.getString(TOKEN_KEY, defaultValue);
    }
}