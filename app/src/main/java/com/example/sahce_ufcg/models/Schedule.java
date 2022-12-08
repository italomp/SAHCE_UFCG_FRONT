package com.example.sahce_ufcg.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Schedule implements Serializable{
    private long id;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private List<TimesByDay> timesByDayLit;
    private String placeName;
    private String ownerEmail;
    private boolean available;

    public Schedule(LocalDate initialDate, LocalDate finalDate,
                    List<TimesByDay> timesByDayLit, String placeName) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.timesByDayLit = timesByDayLit;
        this.placeName = placeName;
        this.ownerEmail = null;
        this.available = true;
    }

    public Schedule(long id, LocalDate initialDate, LocalDate finalDate, List<TimesByDay> timesByDayLit,
                    String placeName, String ownerEmail, boolean available) {
        this.id = id;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.timesByDayLit = timesByDayLit;
        this.placeName = placeName;
        this.ownerEmail = ownerEmail;
        this.available = available;
    }

    public Schedule(){}

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<TimesByDay> getTimesByDayLit() {
        return timesByDayLit;
    }

    public void setTimesByDayLit(List<TimesByDay> timesByDayLit) {
        this.timesByDayLit = timesByDayLit;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getId() {
        return id;
    }
}
