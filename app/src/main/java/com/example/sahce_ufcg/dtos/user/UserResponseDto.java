package com.example.sahce_ufcg.dtos.user;

import com.example.sahce_ufcg.models.User;
import com.squareup.moshi.Json;

public class UserResponseDto {
    @Json(name = "name")
    private String name;
    @Json(name = "phone")
    private String phone;
    @Json(name = "address")
    private String address;
    @Json(name = "email")
    private String email;
    @Json(name = "password")
    private String password;
    @Json(name = "userType")
    private User.UserType userType;

    public UserResponseDto(String name, String phone, String address, String email,
                           String password, User.UserType userType) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public UserResponseDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User.UserType getUserType() {
        return userType;
    }
}
