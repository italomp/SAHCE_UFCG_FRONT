package com.example.sahce_ufcg.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.activities.ScheduleRegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScheduleFragment extends Fragment {
    private View view;
    private FloatingActionButton addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_schedule, container, false);
        setAddButton();
        return view;
    }

    public void setAddButton(){
        addButton = view.findViewById(R.id.add_schedule_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ScheduleRegisterActivity.class));
            }
        });
    }
}