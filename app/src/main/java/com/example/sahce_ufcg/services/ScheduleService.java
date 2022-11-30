package com.example.sahce_ufcg.services;

import com.example.sahce_ufcg.models.Schedule;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ScheduleService {
    @POST("/v1/admin/schedules")
    Call<Void> save(@Body Schedule schedule, @Header("Authorization") String token);
}