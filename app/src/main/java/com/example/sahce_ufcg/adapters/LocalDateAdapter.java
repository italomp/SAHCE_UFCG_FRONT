package com.example.sahce_ufcg.adapters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter {

    public LocalDateAdapter(){}

    @ToJson
    public String toJson(LocalDate localDate){
        return localDate.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @FromJson
    public LocalDate fromJson(String strDate){
        return LocalDate.parse(strDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
