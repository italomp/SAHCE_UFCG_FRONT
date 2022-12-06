package com.example.sahce_ufcg.util;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.dtos.PlaceResponseDto;
import com.example.sahce_ufcg.dtos.schedule.ScheduleResponseDto;
import com.example.sahce_ufcg.models.Schedule;
import com.example.sahce_ufcg.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceSpinnerSetter {

    // A Adição de uma callback ao spinner é particular a cada classe.
    private static void setPlaceSpinner(Context context, Spinner placeSpinner, ArrayList<String> placeNameList){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, placeNameList);
        placeSpinner.setAdapter(spinnerAdapter);
    }

    public static void fillPlaceSpinner(Context context, String token, Spinner placeSpinner){
        ApiService.getPlaceService().getAll(token).enqueue(
                new Callback<List<PlaceResponseDto>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(
                            Call<List<PlaceResponseDto>> call,
                            Response<List<PlaceResponseDto>> response
                    ){
                        if(response.isSuccessful()){
                            if(response.body() == null){
                                Util.showMessage(
                                        context,
                                        "Não existem espaços/locais cadastrados.");
                                return;
                            }
                            ArrayList<String> placeNameList = new ArrayList<>();
                            response.body().forEach(
                                    placeResponseDto -> placeNameList.add(placeResponseDto.getName().toUpperCase())
                            );
                            setPlaceSpinner(context, placeSpinner, placeNameList);
                        }
                        else{
                            Util.showMessage(context, "Http Status: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PlaceResponseDto>> call, Throwable t) {
                        Util.showMessage(context, "Falha na comunicação");
                    }
                }
        );
    }
}
