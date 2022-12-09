package com.example.sahce_ufcg.dtos.schedule;

import com.example.sahce_ufcg.models.TimesByDay;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class ScheduleRequestDto {
    private LocalDate initialDate;
    private LocalDate finalDate;
    private LocalDate releaseInternalCommunity;
    private LocalDate releaseExternalCommunity;
    private Map<DayOfWeek, TimesByDay> timesByDayMap;
    private String placeName;
    private String ownerEmail;

    public ScheduleRequestDto(LocalDate initialDate, LocalDate finalDate,
                    Map<DayOfWeek, TimesByDay> timesByDayMap, String placeName, String ownerEmail) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.timesByDayMap = timesByDayMap;
        this.placeName = placeName;
        this.ownerEmail = ownerEmail;
    }

    public ScheduleRequestDto(LocalDate initialDate, LocalDate finalDate,
                              LocalDate releaseInternalCommunity, LocalDate releaseExternalCommunity,
                              Map<DayOfWeek, TimesByDay> timesByDayMap, String placeName) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.timesByDayMap = timesByDayMap;
        this.placeName = placeName;
        this.releaseInternalCommunity = releaseInternalCommunity;
        this.releaseExternalCommunity = releaseExternalCommunity;
        this.ownerEmail = null;
    }

    public ScheduleRequestDto(){}

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

    public Map<DayOfWeek, TimesByDay> getTimesByDayMap() {
        return timesByDayMap;
    }

    public void setTimesByDayMap(Map<DayOfWeek, TimesByDay> timesByDayMap) {
        this.timesByDayMap = timesByDayMap;
    }
}
