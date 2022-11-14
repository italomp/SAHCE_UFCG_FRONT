package com.example.sahce_ufcg.services;


import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiService {
    private static UserService userService;
    private static PlaceService placeService;

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

    public static PlaceService getPlaceService(){
        if(placeService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            placeService = retrofit.create(PlaceService.class);
        }
        return placeService;
    }

}
