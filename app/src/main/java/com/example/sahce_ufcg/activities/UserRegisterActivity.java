package com.example.sahce_ufcg.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.dtos.UserResponseBody;
import com.example.sahce_ufcg.services.ApiService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends AppCompatActivity {
    TextInputEditText nameTextInput, phoneTextInput, addressTextInput, emailTextInput, passwordTextInput;
    Button sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        setViews();
    }

    public void setViews(){
        nameTextInput = findViewById(R.id.name_field);
        phoneTextInput = findViewById(R.id.phone_field);
        addressTextInput = findViewById(R.id.address_field);
        emailTextInput = findViewById(R.id.email_field);
        passwordTextInput = findViewById(R.id.password_field);
        setSendButton();
    }

    public void setSendButton(){
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTextInput.getText().toString();
                String phone = phoneTextInput.getText().toString();
                String address = addressTextInput.getText().toString();
                String email = emailTextInput.getText().toString();
                String password = passwordTextInput.getText().toString();

                User newUser = new User(name, phone, address, email, password);
                ApiService.getUserService().createUser(newUser).enqueue(new Callback<UserResponseBody>() {
                    @Override
                    public void onResponse(Call<UserResponseBody> call, Response<UserResponseBody> response) {
                        if(response.isSuccessful()){
                            showMessage("Cadastro realizado com sucesso!");
                        } else{
                            showMessage("Http Status Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponseBody> call, Throwable t) {
                        showMessage("Houve um erro com a resposta do servidor.");
                    }
                });
            }
        });
    }

    public void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}