package com.example.sahce_ufcg.adapters;

import static com.example.sahce_ufcg.util.DateMapper.formatAmericanDateToBrazilianFormat;
import static com.example.sahce_ufcg.util.DateMapper.fromDayOfWeekToString;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.Schedule;
import com.example.sahce_ufcg.models.TimesByDay;

import java.util.ArrayList;
import java.util.List;

public class ScheduleListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Schedule> scheduleList;

    public ScheduleListingAdapter(){
        this.scheduleList = new ArrayList<>();
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView scheduleCardView = (CardView) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.schedule_card, parent, false);
        return new ScheduleViewHolder(scheduleCardView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView initialDateView = ((ScheduleViewHolder) holder).getInitialDateView();
        TextView finalDateView = ((ScheduleViewHolder) holder).getFinalDateView();
        Schedule currSchedule = scheduleList.get(position);

        String initialDate = formatAmericanDateToBrazilianFormat(currSchedule.getInitialDate());
        String finalDate = formatAmericanDateToBrazilianFormat(currSchedule.getFinalDate());

        initialDateView.setText(initialDate);
        finalDateView.setText(finalDate);

        setDaysOfWeekListing((ScheduleViewHolder) holder, currSchedule.getTimesByDayLit());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDaysOfWeekListing(ScheduleViewHolder holder, List<TimesByDay> timesByDayList){
        LinearLayout daysOfWeekListing = holder.getDaysOfWeekListing();
        timesByDayList.forEach(
                timesByDay -> {
                    LinearLayout dayOfWeekLayout = setDayOfWeek(holder, timesByDay);
                    daysOfWeekListing.addView(dayOfWeekLayout);
                }
        );
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LinearLayout setDayOfWeek(ScheduleViewHolder holder, TimesByDay timesByDay){
        LinearLayout dayOfWeekLayout = (LinearLayout) LayoutInflater
                .from(holder.itemView.getContext())
                .inflate(R.layout.day_of_week_card_of_schedule_card,
                        (ViewGroup) holder.itemView.getParent(), false);
        TextView dayOfWeekTextView = dayOfWeekLayout.findViewById(R.id.day_of_week);
        TextView initialTimeTextView = dayOfWeekLayout.findViewById(R.id.initial_time);
        TextView finalTimeTextView = dayOfWeekLayout.findViewById(R.id.final_time);

        String day = fromDayOfWeekToString(timesByDay.getDay());
        String initialTime = timesByDay.getInitialTime().toString();
        String finalTime = timesByDay.getFinalTime().toString();

        dayOfWeekTextView.setText(day + ":");
        initialTimeTextView.setText(initialTime);
        finalTimeTextView.setText(finalTime);

        return dayOfWeekLayout;
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder{
        TextView initialDateView, finalDateView;
        LinearLayout daysOfWeekListing;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            initialDateView = itemView.findViewById(R.id.initial_date);
            finalDateView = itemView.findViewById(R.id.final_date);
            daysOfWeekListing = itemView.findViewById(R.id.days_of_week_listing);
        }

        public TextView getInitialDateView() {
            return initialDateView;
        }

        public TextView getFinalDateView() {
            return finalDateView;
        }

        public LinearLayout getDaysOfWeekListing() {
            return daysOfWeekListing;
        }
    }
}
