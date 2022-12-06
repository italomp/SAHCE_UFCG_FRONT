package com.example.sahce_ufcg.fragments;

import static com.example.sahce_ufcg.util.PlaceSpinnerSetter.fillPlaceSpinner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.activities.ScheduleRegisterActivity;
import com.example.sahce_ufcg.adapters.ScheduleListingAdapter;
import com.example.sahce_ufcg.dtos.PlaceResponseDto;
import com.example.sahce_ufcg.dtos.schedule.ScheduleResponseDto;
import com.example.sahce_ufcg.models.Schedule;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Mapper;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {
    private View view;
    private FloatingActionButton addButton;
    private RecyclerView scheduleListing;
    private String token;
    private ScheduleListingAdapter adapter;
    private Spinner placeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_schedule, container, false);
        getPreferences();
        setViews();
        return view;
    }

    public void setViews(){
        setScheduleListing();
        setAddButton();
        setPlaceSpinner();
    }

    public void setPlaceSpinner(){
        placeSpinner = view.findViewById(R.id.spinner_place);
        fillPlaceSpinner(getContext(), token, placeSpinner);
        placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlace = placeSpinner.getSelectedItem().toString();
                getSchedulesByPlaceName(selectedPlace);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void setScheduleListing(){
        scheduleListing = view.findViewById(R.id.schedule_listing);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new ScheduleListingAdapter();
        adapter.setHasStableIds(true);
        scheduleListing.setLayoutManager(layoutManager);
        scheduleListing.setAdapter(adapter);
        scheduleListing.setHasFixedSize(true);
    }

    public void setAddButton(){
        addButton = view.findViewById(R.id.add_schedule_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ScheduleRegisterActivity.class));
            }
        });
    }

    public void getPreferences(){
        token = Util.getTokenPreferences(view.getContext());
    }

    public void getSchedulesByPlaceName(String placeName){
        ApiService.getScheduleService().getAll(placeName, token).enqueue(
                new Callback<List<ScheduleResponseDto>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(
                            Call<List<ScheduleResponseDto>> call,
                            Response<List<ScheduleResponseDto>> response
                    ){
                        if(response.isSuccessful()){
                            scheduleListing.removeAllViews();
                            if(response.body() == null)
                                return;

                            if(response.body().size() == 0)
                                Util.showMessage(view.getContext(), "Espaço sem horários cadastrados.");

                            List<Schedule> scheduleList = Mapper.fromScheduleResponseDtoListToScheduleList(response.body());
                            adapter.setScheduleList(scheduleList);
                        }
                        else{
                            Util.showMessage(getContext(), "Http Status Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ScheduleResponseDto>> call, Throwable t) {
                        Util.showMessage(getContext(),"Falha na comunicação");
                    }
                }
        );
    }
}