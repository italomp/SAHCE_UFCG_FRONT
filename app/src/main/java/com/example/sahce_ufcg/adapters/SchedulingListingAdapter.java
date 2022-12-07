package com.example.sahce_ufcg.adapters;

import static com.example.sahce_ufcg.util.DateMapper.formatAmericanDateToBrazilianFormat;
import static com.example.sahce_ufcg.util.DateMapper.fromDayOfWeekToString;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.activities.SchedulingDetailsActivity;
import com.example.sahce_ufcg.models.Schedule;
import com.example.sahce_ufcg.models.TimesByDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SchedulingListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Schedule> scheduleList;

    public SchedulingListingAdapter(){
        scheduleList = new ArrayList<>();
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardview = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scheduling_card, parent, false);
        return new SchedulingViewHolder(cardview);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        TextView availableView = ((SchedulingViewHolder) holder).getAvailableView();
        TextView periodView = ((SchedulingViewHolder) holder).getPeriodView();
        LinearLayout daysOfWeekLayout = ((SchedulingViewHolder) holder).getDaysOfWeekLayout();
        ImageButton detailsButton = ((SchedulingViewHolder) holder).getDetailsButton();
        Schedule currSchedule = scheduleList.get(position);

        LocalDate initialDate = currSchedule.getInitialDate();
        String strInitialDate = formatAmericanDateToBrazilianFormat(initialDate);
        LocalDate finalDate = currSchedule.getFinalDate();
        String strFinalDate = formatAmericanDateToBrazilianFormat(finalDate);

        setAvailableView(availableView, currSchedule.isAvailable());
        periodView.setText("De " + strInitialDate + " a " + strFinalDate);
        setDaysOfWeekLayout(context, daysOfWeekLayout, currSchedule.getTimesByDayLit());
        setOnClickListenerButton(context, detailsButton, currSchedule);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDaysOfWeekLayout(Context context, LinearLayout daysOfWeekLayout, List<TimesByDay> timesByDayList){
        timesByDayList.forEach(timesByDay -> {
            LinearLayout dayOfWeekCard = (LinearLayout) LayoutInflater.from(context)
                    .inflate(R.layout.day_of_week_card_of_scheduling_card, daysOfWeekLayout, false);
            TextView dayOfWeek = dayOfWeekCard.findViewById(R.id.day_of_week);
            TextView initialTime = dayOfWeekCard.findViewById(R.id.initial_time);
            TextView finalTime = dayOfWeekCard.findViewById(R.id.final_time);

            dayOfWeek.setText(fromDayOfWeekToString(timesByDay.getDay()));
            initialTime.setText(timesByDay.getInitialTime().toString());
            finalTime.setText(timesByDay.getFinalTime().toString());

            daysOfWeekLayout.addView(dayOfWeekCard);
        });
    }

    public void setOnClickListenerButton(Context context, ImageButton btn, Schedule schedule){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SchedulingDetailsActivity.class);
                intent.putExtra("schedule", schedule);
                context.startActivity(intent);
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public void setAvailableView(TextView availableView, Boolean available){
        if(available){
            availableView.setText("Disponível");
            availableView.setTextColor(R.color.green);
        }
        else{
            availableView.setText("Indisponível");
            availableView.setTextColor(R.color.red);
        }
    }

    public class SchedulingViewHolder extends RecyclerView.ViewHolder{
        TextView availableView, periodView;
        LinearLayout daysOfWeekLayout;
        ImageButton detailsButton;

        public SchedulingViewHolder(@NonNull View itemView) {
            super(itemView);
            availableView = itemView.findViewById(R.id.available);
            periodView = itemView.findViewById(R.id.period);
            daysOfWeekLayout = itemView.findViewById(R.id.days_of_week_layout);
            detailsButton = itemView.findViewById(R.id.details_button);
        }

        public TextView getAvailableView() {
            return availableView;
        }

        public LinearLayout getDaysOfWeekLayout() {
            return daysOfWeekLayout;
        }

        public ImageButton getDetailsButton() {
            return detailsButton;
        }

        public TextView getPeriodView() {
            return periodView;
        }
    }
}
