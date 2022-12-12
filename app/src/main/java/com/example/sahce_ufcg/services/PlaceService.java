package com.example.sahce_ufcg.services;

import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.dtos.PlaceResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PlaceService {
    @POST("/v1/admin/places")
    Call<Void> savePlace(@Body Place place, @Header("Authorization") String authorization);

    @GET("/v1/protected/places")
    Call<List<PlaceResponseDto>> getAll(@Header("Authorization") String authorization);
}
