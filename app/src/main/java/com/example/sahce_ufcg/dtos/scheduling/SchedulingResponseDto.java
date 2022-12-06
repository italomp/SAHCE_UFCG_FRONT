package com.example.sahce_ufcg.dtos.scheduling;

import com.example.sahce_ufcg.models.TimesByDay;
import com.squareup.moshi.Json;

import java.time.LocalDate;
import java.util.List;

public class SchedulingResponseDto {
    @Json(name = "placeName")
    private String placeName;
    @Json(name = "initialDate")
    private String initialDate;
    @Json(name = "finalDate")
    private String finalDate;
    @Json(name = "ownerEmail")
    private String ownerEmail;
    @Json(name = "available")
    private boolean available;
    @Json(name = "timesByDayList")
    private List<TimesByDay> timesByDayList;


    public SchedulingResponseDto(
            String placeName, String initialDate, String finalDate,
            String ownerEmail, boolean available, List<TimesByDay> timesByDayList
    ){
        this.placeName = placeName;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.ownerEmail = ownerEmail;
        this.available = available;
        this.timesByDayList = timesByDayList;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public boolean getAvailable() {
        return available;
    }

    public List<TimesByDay> getTimesByDayList() {
        return timesByDayList;
    }
}
