package com.example.sahce_ufcg.models;

import java.util.List;

public class Place {
    private String name;
    private List<User.UserType> usersThatCanUseThePlace;

    public Place(String name, List<User.UserType> usersThatCanUseThePlace){
        this.name = name;
        this.usersThatCanUseThePlace = usersThatCanUseThePlace;
    }
}
