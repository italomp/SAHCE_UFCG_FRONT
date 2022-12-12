package com.example.sahce_ufcg.dtos.scheduling;

import com.example.sahce_ufcg.models.TimesByDay;
import com.squareup.moshi.Json;

import java.util.List;

public class SchedulingResponseDto {
    @Json(name = "id")
    private long id;
    @Json(name = "placeName")
    private String placeName;
    @Json(name = "initialDate")
    private String initialDate;
    @Json(name = "finalDate")
    private String finalDate;
    @Json(name ="releaseInternalCommunity")
    private String releaseInternalCommunity;
    @Json(name ="releaseExternalCommunity")
    private String releaseExternalCommunity;
    @Json(name = "ownerEmail")
    private String ownerEmail;
    @Json(name = "available")
    private boolean available;
    @Json(name = "timesByDayList")
    private List<TimesByDay> timesByDayList;


    public SchedulingResponseDto(long id, String placeName, String initialDate, String finalDate,
                             String releaseInternalCommunity, String releaseExternalCommunity,
                             String ownerEmail, boolean available, List<TimesByDay> timesByDayList) {
        this.id = id;
        this.placeName = placeName;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.releaseInternalCommunity = releaseInternalCommunity;
        this.releaseExternalCommunity = releaseExternalCommunity;
        this.ownerEmail = ownerEmail;
        this.available = available;
        this.timesByDayList = timesByDayList;
    }

    public long getId() {
        return id;
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

    public boolean isAvailable() {
        return available;
    }

    public List<TimesByDay> getTimesByDayList() {
        return timesByDayList;
    }

    public String getReleaseInternalCommunity() {
        return releaseInternalCommunity;
    }

    public String getReleaseExternalCommunity() {
        return releaseExternalCommunity;
    }
}
