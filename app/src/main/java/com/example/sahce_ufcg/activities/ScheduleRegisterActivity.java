package com.example.sahce_ufcg.activities;

import static android.text.format.DateFormat.is24HourFormat;

import static com.example.sahce_ufcg.util.DateMapper.fromLocalDateMonthToNumericMoth;
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
import com.example.sahce_ufcg.util.DateMapper;
import com.example.sahce_ufcg.util.Mapper;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScheduleRegisterActivity extends AppCompatActivity {
    Spinner spinnerPlace;
    String token;
    Spinner spinnerDayOfWeek;
    ImageButton addDayOfWeekButton;
    LinearLayout selectedDaysLayout, timeLayout;
    private EditText inputPeriodStart, inputPeriodEnd;
    int labelCount = 0;
    private Button sendButton;
    private Map<DayOfWeek, TimesByDay> timesByDayMap;
    private List<DayOfWeek> daysList; // double daysStack length

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_register);
        timesByDayMap = new HashMap<>();
        daysList = new ArrayList<>();
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
                            List<Place> placeList = Mapper.fromPlaceResponseDtoListToPlaceList(response.body());
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
                System.out.println(1);
                addDayInputCard();
                System.out.println(2);
                addTimeInputCard();
                System.out.println(3);
            }
        });
    }

    public void addDayInputCard(){
        CardView dayCard = (CardView) LayoutInflater
                .from(getApplicationContext())
                .inflate(R.layout.day_of_week_card_of_schedule_register_screen, selectedDaysLayout, false);
        TextView dayView = dayCard.findViewById(R.id.text_day_output);
        String selectDay = spinnerDayOfWeek.getSelectedItem().toString();
        dayView.setText(selectDay);
        selectedDaysLayout.addView(dayCard);
        storageSelectedDay(selectDay);
    }

    public void storageSelectedDay(String selectDay){
        DayOfWeek day = fromStringToDayOfWeek(selectDay);
        // O dia é adicionado 2x, para atender a estratégia de controle dos horários, inicial
        // e final (dois horários para cada dia), do dia.
        daysList.add(day);
        daysList.add(day);

        timesByDayMap.put(day, new TimesByDay(day));
        System.out.println("storageSelectedDay and stack.size is " + daysList.size());
    }

    @SuppressLint("SetTextI18n")
    public void addTimeInputCard(){
        CardView inputTimeCard = (CardView) LayoutInflater
                .from(getApplicationContext())
                .inflate(R.layout.time_input_card, timeLayout, false);
        TextView label = inputTimeCard.findViewById(R.id.times_label);

        EditText inputTimeStart = inputTimeCard.findViewById(R.id.start_time);
        EditText inputTimeEnd = inputTimeCard.findViewById(R.id.end_time);

        label.setText("Horário do " + ++labelCount + "º dia" );
        setTimeInput(inputTimeStart, "Horário Inicial");
        setTimeInput(inputTimeEnd, "Horário Final");

        timeLayout.addView(inputTimeCard);
    }

    private void setPeriodInputs(){
        inputPeriodStart = findViewById(R.id.input_period_start);
        inputPeriodEnd = findViewById(R.id.input_period_end);
        setInputDate(inputPeriodStart, "Inicial");
        setInputDate(inputPeriodEnd, "Final");
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
                                String date = formatSelectedDateToBrazilianFormat(picker.getHeaderText());
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
                String hour = picker.getHour() == 0 ? "00" : picker.getHour() + "";
                String minute = picker.getMinute() == 0 ? "00" : picker.getMinute() + "";
                String selectTime = hour + ":" + minute;
                inputEditText.setText(selectTime);
                System.out.println("antes do storageTimesOfDay");
                storageTimesOfDay(
                        inputEditText.getId(),
                        Integer.parseInt(hour),
                        Integer.parseInt(minute));
                System.out.println("depois do storageTimesOfDay");
            }
        });
    }

    public void storageTimesOfDay(int inputViewId, int hour, int minute){
        DayOfWeek day = daysList.get(0);
        if(daysList.size() == 1){
            daysList.remove(0);
        }
        else{
            for(int i = 1; i < daysList.size(); i++){
                DayOfWeek curr = daysList.get(i);
                daysList.set(i - 1, curr);
            }
        }
        TimesByDay timeByDay = timesByDayMap.get(day);
        if (inputViewId == R.id.start_time)
            timeByDay.setInitialTime(LocalTime.of(hour, minute));
        else
            timeByDay.setFinalTime(LocalTime.of(hour, minute));
    }

    public void setSendButton(){
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placeName = spinnerPlace.getSelectedItem().toString();
                String initialDate = inputPeriodStart.getText().toString();
                String finalDate = inputPeriodEnd.getText().toString();

                sendRegisterRequest(new ScheduleRequestDto(
                    formatInputDateToLocalDate(initialDate),
                    formatInputDateToLocalDate(finalDate),
                    timesByDayMap,
                    placeName)
                );
            }
        });
    }

    public void sendRegisterRequest(ScheduleRequestDto scheduleRequestDto){
        ApiService.getScheduleService().save(scheduleRequestDto, token).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println(response.code());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                }
        );
    }

    // Receive da data like that: Nov 1, 2022
    public String formatSelectedDateToBrazilianFormat(String inputDate){
        String[] arrayDate = inputDate.split(" ");
        String month = fromLocalDateMonthToNumericMoth(arrayDate[0]);
        String day = arrayDate[1].split(",")[0];
        day = day.length() == 1 ? "0" + day : day;
        String year = arrayDate[2];
        return day + "/" + month + "/" + year;
    }

    // Receive a date like that: dd/mm/yyyy
    public LocalDate formatInputDateToLocalDate(String inputDate){
        if (inputDate == null) return null;
        if (inputDate.equals("")) return null;

        String[] arrayDate = inputDate.split("/");
        int day = Integer.parseInt(arrayDate[0]);
        int month = Integer.parseInt(arrayDate[1]);
        int year = Integer.parseInt(arrayDate[2]);

        return LocalDate.of(year, month, day);
    }
}