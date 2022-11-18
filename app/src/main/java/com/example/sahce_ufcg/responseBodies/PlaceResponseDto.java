package com.example.sahce_ufcg.responseBodies;

import com.example.sahce_ufcg.models.User;
import com.squareup.moshi.Json;

import java.util.List;

public class PlaceResponseDto {
    @Json(name = "name")
    private String name;
    @Json(name = "authorizedUsers")
    private List<User.UserType> authorizedUsers;

    public PlaceResponseDto(String name, List<User.UserType> authorizedUsers){
        this.name = name;
        this.authorizedUsers = authorizedUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User.UserType> getAuthorizedUsers() {
        return authorizedUsers;
    }

    public void setAuthorizedUsers(List<User.UserType> authorizedUsers) {
        this.authorizedUsers = authorizedUsers;
    }
}
