package com.example.sahce_ufcg.adapters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter {

    public LocalTimeAdapter(){}

    @ToJson
    public String toJson(LocalTime localTime){
        return localTime.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @FromJson
    public LocalTime fromJson(String strDate){
        return LocalTime.parse(strDate, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
