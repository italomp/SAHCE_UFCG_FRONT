package com.example.sahce_ufcg.models;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimesByDay implements Serializable {
    private DayOfWeek day;
    private LocalTime initialTime;
    private LocalTime finalTime;

    public TimesByDay(DayOfWeek day, LocalTime initialTime, LocalTime finalTime) {
        this.day = day;
        this.initialTime = initialTime;
        this.finalTime = finalTime;
    }

    public TimesByDay(DayOfWeek day) {
        this.day = day;
    }

    public TimesByDay(){}

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getInitialTime() {
        return initialTime;
    }

    public LocalTime getFinalTime() {
        return finalTime;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public void setInitialTime(LocalTime initialTime) {
        this.initialTime = initialTime;
    }

    public void setFinalTime(LocalTime finalTime) {
        this.finalTime = finalTime;
    }
}

