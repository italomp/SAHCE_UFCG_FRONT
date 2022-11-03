package com.example.sahce_ufcg.services;

import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.responseBodies.UserResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface UserService {

    @POST("/v1/users/")
    Call<UserResponseBody> createUser(@Body User user);

    @POST("/login")
    Call<Void> login(@Body User user);
}
