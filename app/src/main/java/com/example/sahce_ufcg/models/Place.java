package com.example.sahce_ufcg.models;

import java.util.List;

public class Place implements Comparable{
    private String name;
    private List<User.UserType> authorizedUsers;

    public Place(String name, List<User.UserType> authorizedUsers){
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

    @Override
    public int compareTo(Object o) {
        Place otherPlace = (Place) o;
        return name.toLowerCase().compareTo(otherPlace.getName().toLowerCase());
    }
}
