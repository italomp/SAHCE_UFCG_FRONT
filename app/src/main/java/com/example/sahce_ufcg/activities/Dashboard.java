package com.example.sahce_ufcg.activities;


import static com.example.sahce_ufcg.util.Constants.SHARED_LOGIN_PREFERENCES_KEY;
import static com.example.sahce_ufcg.util.Constants.TOKEN_KEY;
import static com.example.sahce_ufcg.util.Constants.USER_EMAIL_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.responseBodies.UserResponseBody;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {
    private String email, token;
    private User.UserType userType;
    private TextInputEditText inputPeriodStart, inputPeriodEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getPreferences();
        getUserType();
        setPeriodButtons();
    }

    public void setPeriodButtons(){
        inputPeriodStart = findViewById(R.id.input_period_start);
        inputPeriodEnd = findViewById(R.id.input_period_end);
        inputPeriodStart.clearFocus();
        inputPeriodEnd.clearFocus();

        MaterialDatePicker startDatePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Data Inicial")
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build();

        MaterialDatePicker endDatePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Data Final")
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build();

        inputPeriodStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
                startDatePicker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                inputPeriodStart.setText(startDatePicker.getHeaderText());
                            }
                        });
            }
        });

        inputPeriodEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDatePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
                endDatePicker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                inputPeriodEnd.setText(endDatePicker.getHeaderText());
                            }
                        });
            }
        });
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