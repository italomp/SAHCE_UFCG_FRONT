package com.example.sahce_ufcg.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.responseBodies.PlaceResponseDto;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Mapper {
    public static List<Place> mapPlaceResponseDtoListToPlaceList(List<PlaceResponseDto> placeDtoList){
        List<Place> result = new ArrayList<>();
        placeDtoList.forEach(
                dto -> result.add(new Place(dto.getName(), dto.getAuthorizedUsers()))
        );
        return result;
    }
}
