package com.example.sahce_ufcg.activities;

import static android.text.format.DateFormat.is24HourFormat;

import static com.example.sahce_ufcg.util.DateMapper.formatDateInputSelectedToBrazilianFormat;
import static com.example.sahce_ufcg.util.DateMapper.formatInputDateToLocalDate;
import static com.example.sahce_ufcg.util.DateMapper.fromStringToDayOfWeek;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.dtos.PlaceResponseDto;
import com.example.sahce_ufcg.dtos.schedule.ScheduleRequestDto;
import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.models.TimesByDay;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Mapper;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScheduleRegisterActivity extends AppCompatActivity {
    private Spinner spinnerPlace;
    private String token;
    private Spinner spinnerDayOfWeek;
    private ImageButton addDayOfWeekButton;
    private LinearLayout selectedDaysLayout, timeLayout;
    private EditText inputPeriodStart, inputPeriodEnd;
    private EditText inputReleaseInternalCommunity, inputReleaseExternalCommunity;
    private int labelCount = 0;
    private Button sendButton;
    private Map<DayOfWeek, TimesByDay> timesByDayMap;
    private Map<String, CardView> linkBetweenDaysOfWeekAndDynamicInputTimesCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_register);
        timesByDayMap = new HashMap<>();
        linkBetweenDaysOfWeekAndDynamicInputTimesCard = new HashMap<>();
        getToken();
        setViewsAndLayouts();
    }

    public void setViewsAndLayouts(){
        selectedDaysLayout = findViewById(R.id.selected_days_layout);
        timeLayout = findViewById(R.id.time_layout);
        setSpinnerPlace();
        setSpinnerDayOfWeek();
        setAddDayOfWeekButton();
        setPeriodInputs();
        setSendButton();
    }

    public void getToken(){
        token = Util.getTokenPreferences(this);
    }

    public void setSpinnerPlace(){
        spinnerPlace = findViewById(R.id.spinner_place);
        ApiService.getPlaceService().getAll(token).enqueue(
                new Callback<List<PlaceResponseDto>>() {
                    @Override
                    public void onResponse(
                            Call<List<PlaceResponseDto>> call,
                            Response<List<PlaceResponseDto>> response
                    ){
                        if(response.isSuccessful()){
                            List<Place> placeList = Mapper.fromPlaceDtoListToPlaceList(response.body());
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
                String selectedDay = spinnerDayOfWeek.getSelectedItem().toString();
                addDayInputCard(selectedDay);
                addTimeInputCard(selectedDay);
            }
        });
    }

    public void addDayInputCard(String selectedDay){
        CardView dayCard = (CardView) LayoutInflater
                .from(getApplicationContext())
                .inflate(R.layout.day_of_week_card_of_schedule_register_screen, selectedDaysLayout, false);
        TextView dayView = dayCard.findViewById(R.id.text_day_output);
        dayView.setText(selectedDay);
        selectedDaysLayout.addView(dayCard);
    }

    @SuppressLint("SetTextI18n")
    public void addTimeInputCard(String selectedDay){
        CardView inputTimeCard = (CardView) LayoutInflater
                .from(getApplicationContext())
                .inflate(R.layout.time_input_card, timeLayout, false);
        TextView label = inputTimeCard.findViewById(R.id.times_label);

        EditText inputTimeStart = inputTimeCard.findViewById(R.id.start_time);
        EditText inputTimeEnd = inputTimeCard.findViewById(R.id.end_time);

        label.setText("Horário do " + ++labelCount + "º dia.");
        setTimeInput(inputTimeStart, "Horário Inicial");
        setTimeInput(inputTimeEnd, "Horário Final");

        linkBetweenDaysOfWeekAndDynamicInputTimesCard.put(selectedDay, inputTimeCard);

        timeLayout.addView(inputTimeCard);
    }

    private void setPeriodInputs(){
        inputPeriodStart = findViewById(R.id.input_period_start);
        inputPeriodEnd = findViewById(R.id.input_period_end);
        inputReleaseInternalCommunity = findViewById(R.id.internal_community_release_input);
        inputReleaseExternalCommunity = findViewById(R.id.external_community_release_input);
        setInputDate(inputPeriodStart, "Inicial");
        setInputDate(inputPeriodEnd, "Final");
        setInputDate(inputReleaseInternalCommunity, "Data");
        setInputDate(inputReleaseExternalCommunity, "Data");
    }

    public void setInputDate(EditText inputDate, String pickerFlag){
        inputDate.clearFocus();
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText(pickerFlag)
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build();

        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show(getSupportFragmentManager(), "Selecione Uma Data");
                picker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                String date = formatDateInputSelectedToBrazilianFormat(picker.getHeaderText());
                                inputDate.setText(date);
                            }
                        });
            }
        });
    }

    public void setTimeInput(EditText inputEditText, String pickerFlag){
        boolean isSystem24Hour = is24HourFormat(this);
        int clockFormat = isSystem24Hour ? TimeFormat.CLOCK_24H : TimeFormat.CLOCK_12H;

        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Selecionar Horário")
                .build();

        inputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show(getSupportFragmentManager(), pickerFlag);
            }
        });

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hour = picker.getHour() < 10 ? "0" + picker.getHour() : "" + picker.getHour();
                String minute = picker.getMinute() < 10 ? "0" + picker.getMinute() : "" + picker.getMinute();
                String selectTime = hour + ":" + minute;
                inputEditText.setText(selectTime);
            }
        });
    }

    public void setSendButton(){
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placeName = spinnerPlace.getSelectedItem().toString();
                String initialDate = inputPeriodStart.getText().toString();
                String finalDate = inputPeriodEnd.getText().toString();
                String releaseInternalCommunity = inputReleaseInternalCommunity.getText().toString();
                String releaseExternalCommunity = inputReleaseExternalCommunity.getText().toString();
                fillTimesByDayMap();

                sendRegisterRequest(new ScheduleRequestDto(
                    formatInputDateToLocalDate(initialDate),
                    formatInputDateToLocalDate(finalDate),
                    formatInputDateToLocalDate(releaseInternalCommunity),
                    formatInputDateToLocalDate(releaseExternalCommunity),
                    timesByDayMap,
                    placeName)
                );
            }
        });
    }

    public void fillTimesByDayMap(){
        linkBetweenDaysOfWeekAndDynamicInputTimesCard.keySet().forEach(
                key -> {
                    DayOfWeek timesByDayKey = fromStringToDayOfWeek(key);
                    CardView timesByDayCard =  linkBetweenDaysOfWeekAndDynamicInputTimesCard.get(key);
                    EditText inputTimeStart = timesByDayCard.findViewById(R.id.start_time);
                    EditText inputTimeEnd = timesByDayCard.findViewById(R.id.end_time);

                    String strInitialTime = inputTimeStart.getText().toString();
                    String strFinalTime = inputTimeEnd.getText().toString();

                    String hourOfInitialTime = strInitialTime.split(":")[0];
                    String minuteOfInitialTime = strInitialTime.split(":")[1];
                    String hourOfFinalTime = strFinalTime.split(":")[0];
                    String minuteOfFinalTime = strFinalTime.split(":")[1];

                    DayOfWeek day = timesByDayKey;
                    LocalTime initialTime = LocalTime.of(Integer.parseInt(hourOfInitialTime), Integer.parseInt(minuteOfInitialTime));
                    LocalTime finalTime = LocalTime.of(Integer.parseInt(hourOfFinalTime), Integer.parseInt(minuteOfFinalTime));

                    TimesByDay timesByDayValue = new TimesByDay(day, initialTime, finalTime);

                    timesByDayMap.put(timesByDayKey, timesByDayValue);
                }
        );
    }

    public void sendRegisterRequest(ScheduleRequestDto scheduleRequestDto){
        ApiService.getScheduleService().save(scheduleRequestDto, token).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Util.showMessage(getBaseContext(), "Hoŕario salvo com sucesso.");
                        }
                        else{
                            Util.showMessage(getBaseContext(), "Http Status Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Util.showMessage(getBaseContext(), "Falha de comunicação");
                        t.printStackTrace();
                    }
                }
        );
    }
}