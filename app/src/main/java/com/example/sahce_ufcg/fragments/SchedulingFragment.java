package com.example.sahce_ufcg.fragments;

import static com.example.sahce_ufcg.util.DateMapper.formatDateInputSelectedToBrazilianFormat;
import static com.example.sahce_ufcg.util.PlaceSpinnerSetter.fillPlaceSpinner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

public class SchedulingFragment extends Fragment {
    private Spinner placeSpinner;
    private TextInputEditText inputPeriodStart, inputPeriodEnd;
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
        return view;
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