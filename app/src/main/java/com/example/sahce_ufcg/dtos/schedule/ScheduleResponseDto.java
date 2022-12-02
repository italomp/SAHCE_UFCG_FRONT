package com.example.sahce_ufcg.dtos.schedule;

import com.example.sahce_ufcg.models.TimesByDay;
import com.squareup.moshi.Json;

import java.time.LocalDate;
import java.util.List;

public class ScheduleResponseDto {
    @Json(name = "initialDate")
    private LocalDate initialDate;
    @Json(name = "finalDate")
    private LocalDate finalDate;
    @Json(name = "timesByDayList")
    private List<TimesByDay> timesByDayList;
    @Json(name = "placeName")
    private String placeName;
    @Json(name = "ownerEmail")
    private String ownerEmail;

    public ScheduleResponseDto(LocalDate initialDate, LocalDate finalDate,
                               List<TimesByDay> timesByDayList,
                               String placeName, String ownerEmail) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.timesByDayList = timesByDayList;
        this.placeName = placeName;
        this.ownerEmail = ownerEmail;
    }

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

    public List<TimesByDay> getTimesByDayList() {
        return timesByDayList;
    }

    public void setTimesByDayList(List<TimesByDay> timesByDayList) {
        this.timesByDayList = timesByDayList;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
