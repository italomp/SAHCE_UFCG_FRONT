package com.example.sahce_ufcg.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.services.ApiService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText emailEditText, passwordEditText;
    Button loginButton;
    TextView signupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
    }

    public void setViews(){
        emailEditText = findViewById(R.id.email_field);
        passwordEditText = findViewById(R.id.password_field);
        setLoginButton();
        setSignupTextView();
    }

    public void setSignupTextView(){
        signupTextView = findViewById(R.id.signup_text);
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserRegisterActivity.class));
            }
        });
    }

    public void setLoginButton(){
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(emailEditText.getText());
                String password = String.valueOf(passwordEditText.getText());
                User newUser = new User(email, password);
                ApiService.getUserService().login(newUser).enqueue(
                        new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    String token = response.headers().get("Authorization");
                                    saveToken(token);
                                    goToDashboard();
                                }
                                else{
                                    System.out.println("Status Code: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("onFailure");
                                t.printStackTrace();
                            }
                        }
                );
            }
        });
    }

    public void saveToken(String token){
        SharedPreferences sharedPreferences = this.getSharedPreferences("loginPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(R.string.token), token);
        editor.apply();
    }

    public void goToDashboard(){
        startActivity(new Intent(getApplicationContext(), Dashboard.class));
    }
}