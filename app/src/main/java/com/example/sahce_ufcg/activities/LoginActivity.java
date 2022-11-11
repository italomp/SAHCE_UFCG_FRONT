package com.example.sahce_ufcg.activities;

import static com.example.sahce_ufcg.util.Constants.*;

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
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signupTextView;

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
                                handleResponse(response, email);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Util.showMessage(LoginActivity.this, "Falha de rede.");
                                t.printStackTrace();
                            }
                        }
                );
            }
        });
    }

    private void handleResponse(Response<Void> response, String usernameFromUser){
        if(response.isSuccessful()){
            String token = response.headers().get("Authorization");
            savePreferences(token, usernameFromUser);
            goToDashboard();
        }
        else if(response.code() == 401){
            Util.showMessage(LoginActivity.this,
                    "Credenciais inválidas.");
        }
        else{
            Util.showMessage(LoginActivity.this,
                    "Código de estado HTTP: " + response.code() + ".");
        }
    }

    public void savePreferences(String token, String email){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                SHARED_LOGIN_PREFERENCES_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.putString(USER_EMAIL_KEY, email);
        editor.apply();
    }

    public void goToDashboard(){
        startActivity(new Intent(getApplicationContext(), Dashboard.class));
    }
}