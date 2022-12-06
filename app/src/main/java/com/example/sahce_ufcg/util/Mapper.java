package com.example.sahce_ufcg.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.sahce_ufcg.dtos.UserResponseDto;
import com.example.sahce_ufcg.dtos.schedule.ScheduleResponseDto;
import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.dtos.PlaceResponseDto;
import com.example.sahce_ufcg.models.Schedule;
import com.example.sahce_ufcg.models.User;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Mapper {
    public static List<Place> fromPlaceResponseDtoListToPlaceList(
            List<PlaceResponseDto> placeDtoList
    ){
        List<Place> result = new ArrayList<>();
        placeDtoList.forEach(
                dto -> result.add(new Place(dto.getName(), dto.getAuthorizedUsers()))
        );
        return result;
    }

    public static List<Schedule> fromScheduleResponseDtoListToScheduleList(
            List<ScheduleResponseDto> dtoList
    ){
        List<Schedule> result = new ArrayList<>();
        dtoList.forEach(
                dto -> result.add(new Schedule(
                        dto.getInitialDate(),
                        dto.getFinalDate(),
                        dto.getTimesByDayList(),
                        dto.getPlaceName()
                ))
        );
        return result;
    }

    public static List<User> fromUserResponseDtoListToUserList(List<UserResponseDto> userDtoList){
        List<User> result = new ArrayList<>();
        userDtoList.forEach(
                userDto -> result.add(new User(
                        userDto.getName(),
                        userDto.getPhone(),
                        userDto.getAddress(),
                        userDto.getEmail(),
                        userDto.getPassword(),
                        userDto.getUserType()
                ))
        );
        return result;
    }
}
