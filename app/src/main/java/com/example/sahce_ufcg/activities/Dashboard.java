package com.example.sahce_ufcg.activities;


import static com.example.sahce_ufcg.util.Constants.SHARED_LOGIN_PREFERENCES_KEY;
import static com.example.sahce_ufcg.util.Constants.TOKEN_KEY;
import static com.example.sahce_ufcg.util.Constants.USER_EMAIL_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.responseBodies.UserResponseBody;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {
    private String email, token;
    private User.UserType userType;
    private TextInputEditText inputPeriodStart, inputPeriodEnd;
    private Animation rotateOpen, rotateClose, fromBottom, toBottom;
    private FloatingActionButton addButton, addTimeButton, addPlaceButton;
    private Boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getPreferences();
        getUserType();
        setPeriodInputs();
        setViews();
    }

    private void setViews(){
        setAnimationReferences();
        addButton = findViewById(R.id.add_btn);
        addTimeButton = findViewById(R.id.add_time_btn);
        addPlaceButton = findViewById(R.id.add_place_btn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });

        addTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "add time", Toast.LENGTH_LONG).show();
            }
        });

        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "add place", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onAddButtonClicked(){
        setFloatButtonsVisibility(clicked);
        setFloatButtonsAnimations(clicked);
        clicked = !clicked;
    }

    public void setFloatButtonsVisibility(Boolean clicked){
        if(!clicked){
            addTimeButton.setVisibility(View.VISIBLE);
            addPlaceButton.setVisibility(View.VISIBLE);
        }
        else{
            addTimeButton.setVisibility(View.INVISIBLE);
            addPlaceButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setFloatButtonsAnimations(Boolean clicked){
        if(!clicked){
            addTimeButton.startAnimation(fromBottom);
            addPlaceButton.startAnimation(fromBottom);
            addButton.startAnimation(rotateOpen);
        }
        else{
            addTimeButton.startAnimation(toBottom);
            addPlaceButton.startAnimation(toBottom);
            addButton.startAnimation(rotateClose);
        }
    }

    private void setAnimationReferences(){
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
    }

    private void setPeriodInputs(){
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

    private void getUserType(){
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

    private void getPreferences(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                SHARED_LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String defaultValue = "";
        email = sharedPreferences.getString(USER_EMAIL_KEY, defaultValue);
        token = sharedPreferences.getString(TOKEN_KEY, defaultValue);
    }
}