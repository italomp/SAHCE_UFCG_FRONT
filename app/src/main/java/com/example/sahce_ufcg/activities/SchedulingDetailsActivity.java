package com.example.sahce_ufcg.activities;

import static com.example.sahce_ufcg.util.DateMapper.fromDayOfWeekToString;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    TextView placeNameView, availableView, scheduleOwnerView;
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
        placeNameView = findViewById(R.id.place_name_scheduling_details_activity);
        availableView = findViewById(R.id.available_scheduling_details_activity);
        scheduleOwnerView = findViewById(R.id.schedule_owner_scheduling_details_activity);
        daysOfWeekLayout = findViewById(R.id.days_of_week_layout_scheduling_details_activity);
        placeNameView.setText(schedule.getPlaceName());
        availableView.setText(schedule.isAvailable() ? "Disponível" : "Indisponível");
        scheduleOwnerView.setText(schedule.getOwnerEmail() != null ? schedule.getOwnerEmail() : "");
        setDaysOfWeekLayout(schedule);

        setScheduleButton();
    }

    public void setSchedule(){
        schedule = (Schedule) getIntent().getExtras().getSerializable("schedule");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDaysOfWeekLayout(Schedule schedule) {
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
        scheduleButton = findViewById(R.id.schedule_button_activity_scheduling_details);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCreateSchedulingRequest();
            }
        });
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
                            scheduleButton.setText("Agendado");
                            scheduleButton.setBackgroundColor(0xFF00CC00);
                            scheduleButton.setClickable(false);

                            availableView.setText("Indisponível");
                            scheduleOwnerView.setText(userEmail);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                }
        );
    }
}