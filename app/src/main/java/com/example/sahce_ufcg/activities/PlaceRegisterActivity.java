package com.example.sahce_ufcg.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceRegisterActivity extends AppCompatActivity {
    TextInputEditText placeNameInput;
    RadioButton internalUser, externalUser;
    Button sendButton;
    String email, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_register);
        getPreferences();
        setViews();
    }

    public void setViews(){
        placeNameInput = findViewById(R.id.name_place_field);
        internalUser = findViewById(R.id.radio_internal_community);
        externalUser = findViewById(R.id.radio_external_community);
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User.UserType> userTypesList = new ArrayList<>();
                if(internalUser.isChecked()) userTypesList.add(User.UserType.INTERNAL_USER);
                if(externalUser.isChecked()) userTypesList.add(User.UserType.EXTERNAL_USER);
                Place place = new Place(placeNameInput.getText().toString(), userTypesList);
                ApiService.getPlaceService().savePlace(place, token).enqueue(
                        new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    Util.showMessage(
                                            getApplicationContext(),
                                            "Local cadastrado.");
                                }
                                else{
                                    Util.showMessage(
                                            PlaceRegisterActivity.this,
                                            "status code: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Util.showMessage(
                                        PlaceRegisterActivity.this,
                                        "Falha de rede");
                                t.printStackTrace();
                            }
                        }
                );
            }
        });
    }

    private void getPreferences(){
        email = Util.getEmailPreferences(this);
        token = Util.getTokenPreferences(this);
    }
}