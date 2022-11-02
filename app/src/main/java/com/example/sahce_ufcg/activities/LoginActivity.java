package com.example.sahce_ufcg.activities;

import android.content.Intent;
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
                System.out.println(email + password); // REMOVERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
                User newUser = new User(email, password);
                ApiService.getUserService().login(newUser).enqueue(
                        new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                System.out.println(response.code());
                                System.out.println(response.message());
                                System.out.println(response.headers());


                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                System.out.println("onFailure");
                            }
                        }
                );
            }
        });
    }
}