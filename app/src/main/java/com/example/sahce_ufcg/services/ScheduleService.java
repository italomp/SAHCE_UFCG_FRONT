package com.example.sahce_ufcg.services;

import com.example.sahce_ufcg.dtos.schedule.ScheduleRequestDto;
import com.example.sahce_ufcg.dtos.schedule.ScheduleResponseDto;
import com.example.sahce_ufcg.dtos.scheduling.SchedulingResponseDto;
import com.example.sahce_ufcg.util.SchedulingOperation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ScheduleService {
    @POST("/v1/admin/schedules")
    Call<Void> save(
            @Body ScheduleRequestDto scheduleRequestDto,
            @Header("Authorization") String token);

    @GET("/v1/admin/schedules")
    Call<List<ScheduleResponseDto>> getAll(
            @Query("placeName") String placeName,
            @Header("Authorization") String token);

    @GET("/v1/protected/scheduling")
    Call<List<SchedulingResponseDto>> getSchedulingListByPlaceNameAndPeriodRange(
            @Query("placeName") String placeName,
            @Query("initialDate") String initialDate,
            @Query("finalDate") String finalDate,
            @Header("Authorization") String token);

    @PUT("/v1/protected/scheduling")
    Call<Void> updateScheduling(@Query("scheduleId") long scheduleId,
                                @Query("userEmail") String userEmail,
                                @Query("schedulingOperation") SchedulingOperation schedulingOperation,
                                @Header("Authorization") String token);
}