package com.example.sahce_ufcg.activities;

import static com.example.sahce_ufcg.util.DateMapper.formatAmericanDateToBrazilianFormat;
import static com.example.sahce_ufcg.util.DateMapper.fromDayOfWeekToString;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.Schedule;
import com.example.sahce_ufcg.models.TimesByDay;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulingDetailsActivity extends AppCompatActivity {
    TextView placeNameView, availableView, scheduleOwnerView, periodView;
    TextView releaseInternalCommunityView, releaseExternalCommunityView;
    LinearLayout daysOfWeekLayout;
    Button scheduleButton, cancelButton, participateButton;
    Schedule schedule;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling_details);
        setSchedule();
        setViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setViews(){
        setPlaceNameView();
        setPeriodView();
        setReleaseInternalCommunityView();
        setReleaseExternalCommunityView();
        setDaysOfWeekLayout(schedule);
        setScheduleOwnerView();
        setAvailableView();
        setScheduleButton();
    }

    public void setPeriodView(){
        periodView = findViewById(R.id.schedule_period);
        String initialDate = formatAmericanDateToBrazilianFormat(schedule.getInitialDate());
        String finalDate = formatAmericanDateToBrazilianFormat(schedule.getFinalDate());
        String period = initialDate + " - " + finalDate;
        periodView.setText(period);
    }

    public void setReleaseInternalCommunityView(){
        releaseInternalCommunityView = findViewById(R.id.release_date_internal_community);
        String releaseDate = formatAmericanDateToBrazilianFormat(schedule.getReleaseInternalCommunity());
        releaseInternalCommunityView.setText(releaseDate);
    }

    public void setReleaseExternalCommunityView(){
        releaseExternalCommunityView = findViewById(R.id.release_date_external_community);
        String releaseDate = formatAmericanDateToBrazilianFormat(schedule.getReleaseExternalCommunity());
        releaseExternalCommunityView.setText(releaseDate);
    }

    public void setPlaceNameView(){
        placeNameView = findViewById(R.id.place_name_scheduling);
        placeNameView.setText(schedule.getPlaceName());
    }

    public void setAvailableView(){
        availableView = findViewById(R.id.available_scheduling_details_activity);
        if(schedule.isAvailable()){
            availableView.setText("Disponível");
            availableView.setTextColor(0xFF00CC00);
        }
        else{
            availableView.setText("Indisponível");
            availableView.setTextColor(0xFFFF0000);
        }
    }

    public void setScheduleOwnerView(){
        scheduleOwnerView = findViewById(R.id.schedule_owner);
        scheduleOwnerView.setText(schedule.getOwnerEmail() != null ? schedule.getOwnerEmail() : " - ");
    }

    public void setSchedule(){
        schedule = (Schedule) getIntent().getExtras().getSerializable("schedule");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDaysOfWeekLayout(Schedule schedule) {
        daysOfWeekLayout = findViewById(R.id.days_of_week_layout);
        List<TimesByDay> timesByDayList = schedule.getTimesByDayLit();
        timesByDayList.sort(new Comparator<TimesByDay>() {
            @Override
            public int compare(TimesByDay t1, TimesByDay t2) {
                return t1.getDay().compareTo(t2.getDay());
            }
        });
        schedule.getTimesByDayLit().forEach(
                timesByDay -> {
                    LinearLayout dayCard = (LinearLayout) LayoutInflater
                            .from(getBaseContext())
                            .inflate(R.layout.day_of_week_card_of_scheduling_card,
                                     daysOfWeekLayout, false);
                    TextView dayOfWeekView = dayCard.findViewById(R.id.day_of_week);
                    TextView initialTime = dayCard.findViewById(R.id.initial_time);
                    TextView finalTime = dayCard.findViewById(R.id.final_time);

                    String day = fromDayOfWeekToString(timesByDay.getDay());
                    dayOfWeekView.setText(day);
                    initialTime.setText(timesByDay.getInitialTime().toString());
                    finalTime.setText(timesByDay.getFinalTime().toString());

                    daysOfWeekLayout.addView(dayCard);
                }
        );
    }

    public void setScheduleButton(){
        scheduleButton = findViewById(R.id.schedule_button);

        if(!schedule.isAvailable()){
            scheduleButton.setClickable(false);
            scheduleButton.setBackgroundColor(0xFFCBC3EF);
        }
        else{
            scheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendCreateSchedulingRequest();
                }
            });
        }

    }

    public void sendCreateSchedulingRequest(){
        String userEmail = Util.getEmailPreferences(getBaseContext());
        String token = Util.getTokenPreferences(getBaseContext());
        long scheduleId = schedule.getId();

        ApiService.getScheduleService().createScheduling(scheduleId, userEmail, token).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println(response.code());
                        if(response.isSuccessful()){
                            scheduleButton.setClickable(false);
                            scheduleButton.setBackgroundColor(0xFFCBC3EF);

                            availableView.setText("Indisponível");
                            availableView.setTextColor(0xFFFF0000);
                            scheduleOwnerView.setText(userEmail);
                        }
                        else{
                            Util.showMessage(
                                    getBaseContext(),
                                    "Http Status Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Util.showMessage(getBaseContext(), "Falha de Comunicação");
                    }
                }
        );
    }
}