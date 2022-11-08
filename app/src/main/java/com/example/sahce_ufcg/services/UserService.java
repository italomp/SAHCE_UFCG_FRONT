package com.example.sahce_ufcg.services;

import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.responseBodies.UserResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("/v1/anonymous/users/")
    Call<UserResponseBody> createUser(@Body User user);

    @POST("/login")
    Call<Void> login(@Body User user);

    @GET("/v1/protected/users/{email}")
    Call<UserResponseBody> getUser(@Path("email") String email,
                                   @Header("Authorization") String token);
}
