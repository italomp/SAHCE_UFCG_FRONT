package com.example.sahce_ufcg.services;


import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiService {
    private static UserService userService;

    public static UserService getUserService(){
        if(userService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            userService = retrofit.create(UserService.class);
        }
        return userService;
    }

}
