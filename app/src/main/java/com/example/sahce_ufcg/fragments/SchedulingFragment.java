package com.example.sahce_ufcg.fragments;

import static com.example.sahce_ufcg.util.DateMapper.formatDateInputSelectedToBrazilianFormat;
import static com.example.sahce_ufcg.util.DateMapper.formatInputDateToLocalDate;
import static com.example.sahce_ufcg.util.Mapper.fromSchedulingDtoListToScheduleList;
import static com.example.sahce_ufcg.util.PlaceSpinnerSetter.fillPlaceSpinner;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.dtos.scheduling.SchedulingResponseDto;
import com.example.sahce_ufcg.models.Schedule;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulingFragment extends Fragment {
    private Spinner placeSpinner;
    private TextInputEditText inputPeriodStart, inputPeriodEnd;
    private RecyclerView schedulingListing;
    private ImageButton searchButton;
    private View view;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scheduling, container, false);
        getPreferences();
        placeSpinner();
        setPeriodInputs();
        setSearchButton();
        return view;
    }

    public void setSchedulingListing(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);
        // adapter
        // ...
        schedulingListing = view.findViewById(R.id.scheduling_listing);
        schedulingListing.setHasFixedSize(true);
        schedulingListing.setLayoutManager(layoutManager);
//        schedulingListing.setAdapter();
    }

    public void setSearchButton(){
        searchButton = view.findViewById(R.id.search_btn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (thereIsEmptyInputs()){
                    Util.showMessage(getContext(), "Preencha todos os campos.");
                    return;
                }
                String placeName = placeSpinner.getSelectedItem().toString();
                String rangeStart = "" + inputPeriodStart.getText();
                String rangeEnd = "" + inputPeriodEnd.getText();
                rangeStart = formatInputDateToLocalDate(rangeStart).toString(); // Mudando formato
                rangeEnd = formatInputDateToLocalDate(rangeEnd).toString();
                getSchedulingList(placeName, rangeStart, rangeEnd);
            }
        });
    }

    private boolean thereIsEmptyInputs(){
        return inputPeriodStart.getText() == null ||
                inputPeriodEnd.getText() == null ||
                placeSpinner.getSelectedItem() == null;
    }

    public void getSchedulingList(String placeName, String rangeStart, String rangeEnd){
        ApiService.getScheduleService()
                .getSchedulingListByPlaceNameAndPeriodRange(placeName, rangeStart, rangeEnd)
                .enqueue(new Callback<List<SchedulingResponseDto>>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(
                            Call<List<SchedulingResponseDto>> call,
                            Response<List<SchedulingResponseDto>> response
                    ){
                        if(response.isSuccessful()){
                            List<SchedulingResponseDto> schedulingList = response.body();
                            List<Schedule> scheduleList = fromSchedulingDtoListToScheduleList(schedulingList);

                        }
                        else{
                            Util.showMessage(getContext(), "Http Status Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SchedulingResponseDto>> call, Throwable t) {
                        Util.showMessage(getContext(), "Falha de comunicação");
                    }
                });
    }

    private void getPreferences(){
        token = Util.getTokenPreferences(getContext());
    }

    private void placeSpinner(){
        placeSpinner = view.findViewById(R.id.place_spinner);
        fillPlaceSpinner(getContext(), token, placeSpinner);
    }

    private void setDateInput(TextInputEditText input){
        input.clearFocus();
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Selecione uma Data")
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build();

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getParentFragmentManager(), "Material_Date_Picker");
                datePicker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                String formattedDate = formatDateInputSelectedToBrazilianFormat(datePicker.getHeaderText());
                                if(input.getId() == inputPeriodStart.getId()){
                                    inputPeriodStart.setText(formattedDate);
                                }
                                else if (input.getId() == inputPeriodEnd.getId()){
                                    inputPeriodEnd.setText(formattedDate);
                                }
                            }
                        });
            }
        });
    }

    private void setPeriodInputs(){
        inputPeriodStart = view.findViewById(R.id.input_period_start);
        inputPeriodEnd = view.findViewById(R.id.input_period_end);
        setDateInput(inputPeriodStart);
        setDateInput(inputPeriodEnd);
    }
}