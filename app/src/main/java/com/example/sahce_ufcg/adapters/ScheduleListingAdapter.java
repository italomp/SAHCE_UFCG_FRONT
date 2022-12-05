package com.example.sahce_ufcg.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.Schedule;

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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        LinearLayout dayOfWeekLayout = (LinearLayout) LayoutInflater
//                .from(holder.itemView.getContext())
//                .inflate(R.layout.day_of_week_layout, (ViewGroup) holder.itemView, false);
        TextView initialDateView = ((ScheduleViewHolder) holder).getInitialDateView();
        TextView finalDateView = ((ScheduleViewHolder) holder).getFinalDateView();
//        RecyclerView daysOfWeekListing = ((ScheduleViewHolder) holder).getDaysOfWeekListing();
        Schedule currSchedule = scheduleList.get(position);
        initialDateView.setText(currSchedule.getInitialDate().toString());
        finalDateView.setText(currSchedule.getFinalDate().toString());
//        daysOfWeekListing
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder{
        TextView initialDateView, finalDateView;
        RecyclerView daysOfWeekListing;

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

        public RecyclerView getDaysOfWeekListing() {
            return daysOfWeekListing;
        }
    }
}
