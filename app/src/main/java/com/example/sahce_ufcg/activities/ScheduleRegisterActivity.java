package com.example.sahce_ufcg.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.responseBodies.PlaceResponseDto;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Mapper;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleRegisterActivity extends AppCompatActivity {
    Spinner spinnerPlace;
    String token;
    Spinner spinnerDayOfWeek;
    ImageButton addDayOfWeekButton;
    LinearLayout selectedDaysLayout;
    private TextInputEditText inputPeriodStart, inputPeriodEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_register);

        getToken();
        setViewsAndLayouts();
    }
    public void setViewsAndLayouts(){
        selectedDaysLayout = findViewById(R.id.selected_days_layout);
        setSpinnerPlace();
        setSpinnerDayOfWeek();
        setAddDayOfWeekButton();
        setPeriodInputs();
    }

    public void getToken(){
        token = Util.getTokenPreferences(this);
    }

    public void setSpinnerPlace(){
        spinnerPlace = findViewById(R.id.spinner_place);
        ApiService.getPlaceService().getAll(token).enqueue(
                new Callback<List<PlaceResponseDto>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(
                            Call<List<PlaceResponseDto>> call,
                            Response<List<PlaceResponseDto>> response
                    ){
                        if(response.isSuccessful()){
                            List<Place> placeList = Mapper.mapPlaceResponseDtoListToPlaceList(response.body());
                            SpinnerAdapter adapter = new ArrayAdapter<>(
                                    ScheduleRegisterActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    placeList
                                            .stream()
                                            .map(place -> place.getName().toUpperCase())
                                            .collect(Collectors.toList())
                                    );
                            spinnerPlace.setAdapter(adapter);
                        }
                        else{
                            Util.showMessage(
                                    ScheduleRegisterActivity.this,
                                    "Http Status Code: " + response.code());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<PlaceResponseDto>> call, Throwable t) {
                        Util.showMessage(
                                ScheduleRegisterActivity.this,
                                "Falha na comunicação");
                    }
                }
        );
    }

    public void setSpinnerDayOfWeek(){
        spinnerDayOfWeek = findViewById(R.id.spinner_day_of_week);
        SpinnerAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.days_of_week,android.R.layout.simple_spinner_item);
        spinnerDayOfWeek.setAdapter(arrayAdapter);
    }

    public void setAddDayOfWeekButton(){
        addDayOfWeekButton = findViewById(R.id.add_day_of_week_button);
        addDayOfWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardView dayCard = (CardView) LayoutInflater
                        .from(getApplicationContext())
                        .inflate(R.layout.day_of_week_card, selectedDaysLayout, false);
                TextView dayOutput = dayCard.findViewById(R.id.text_day_output);
                String selectDay = spinnerDayOfWeek.getSelectedItem().toString();
                dayOutput.setText(selectDay);
                selectedDaysLayout.addView(dayCard);
            }
        });
    }

    private void setPeriodInputs(){
        inputPeriodStart = findViewById(R.id.input_period_start);
        inputPeriodEnd = findViewById(R.id.input_period_end);
        inputPeriodStart.clearFocus();
        inputPeriodEnd.clearFocus();

        MaterialDatePicker<Long> startDatePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Data Inicial")
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build();

        MaterialDatePicker<Long> endDatePicker = MaterialDatePicker.Builder
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
}