package com.example.sahce_ufcg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sahce_ufcg.R;
import com.google.android.material.textfield.TextInputEditText;

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
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTextInput.getText().toString();
                String phone = phoneTextInput.getText().toString();
                String address = addressTextInput.getText().toString();
                String email = emailTextInput.getText().toString();
                String password = passwordTextInput.getText().toString();

                System.out.println(name + " - " + phone + " - " + address + " - " + email + " - " + password);
            }
        });
    }
}