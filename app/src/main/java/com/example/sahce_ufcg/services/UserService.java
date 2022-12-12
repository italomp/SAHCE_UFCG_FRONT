package com.example.sahce_ufcg.services;

import com.example.sahce_ufcg.dtos.UserResponseDto;
import com.example.sahce_ufcg.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @POST("/v1/anonymous/users/")
    Call<UserResponseDto> createUser(@Body User user);

    @POST("/login")
    Call<Void> login(@Body User user);

    @GET("/v1/protected/users/{email}")
    Call<UserResponseDto> getUser(@Path("email") String email,
                                  @Header("Authorization") String token);

    @GET("/v1/admin/users/inactive")
    Call<List<UserResponseDto>> getAllInactiveUsers(@Header("Authorization") String token);

    @GET("/v1/anonymous/users/document")
    Call<byte[]> getUserDocumentPicture(@Query("userEmail") String userEmail);
}
