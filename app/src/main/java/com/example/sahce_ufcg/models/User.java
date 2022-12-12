package com.example.sahce_ufcg.models;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String phone;
    private String address;
    private String email;
    private String password;
    private UserType userType;
    private byte[] documentImage;

    public User(String name, String phone, String address, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        userType = null;
    }

    public User(String name, String phone, String address,
                String email, String password, UserType userType) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public User(String name, String phone, String address,
                String email, String password, UserType userType, byte[] documentImage) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.documentImage = documentImage;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User() {
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public byte[] getDocumentImage() {
        return documentImage;
    }

    public void setDocumentImage(byte[] documentImage) {
        this.documentImage = documentImage;
    }

    public enum UserType{
        ADMIN, EXTERNAL_USER, INTERNAL_USER
    }
}
