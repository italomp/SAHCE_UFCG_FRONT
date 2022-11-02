package com.example.sahce_ufcg.services;

import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.responseBodies.UserResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface UserService {

    @POST("/v1/users/")
    Call<UserResponseBody> createUser(@Body User user);

//    @FormUrlEncoded
//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    @POST("/v1/anonymous/login")
//    Call<HttpHeaders> login(@Field("username") String username, @Field("password") String password);

    @POST("/v1/anonymous/login")
    Call<User> login(@Body User user);
}
