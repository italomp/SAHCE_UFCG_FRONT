package com.example.sahce_ufcg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sahce_ufcg.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

public class ReservedTimesFragment extends Fragment {
    private TextInputEditText inputPeriodStart, inputPeriodEnd;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reserved_times, container, false);
        setPeriodInputs();
        return view;
    }

    private void setPeriodInputs(){
        inputPeriodStart = view.findViewById(R.id.input_period_start);
        inputPeriodEnd = view.findViewById(R.id.input_period_end);
        inputPeriodStart.clearFocus();
        inputPeriodEnd.clearFocus();

        MaterialDatePicker startDatePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Data Inicial")
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build();

        MaterialDatePicker endDatePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Data Final")
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build();

        inputPeriodStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePicker.show(getParentFragmentManager(), "Material_Date_Picker");
                startDatePicker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                inputPeriodStart.setText(startDatePicker.getHeaderText());
                            }
                        });
            }
        });

        inputPeriodEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDatePicker.show(getParentFragmentManager(), "Material_Date_Picker");
                endDatePicker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                inputPeriodEnd.setText(endDatePicker.getHeaderText());
                            }
                        });
            }
        });
    }
}