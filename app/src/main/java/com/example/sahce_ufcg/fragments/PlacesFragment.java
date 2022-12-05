package com.example.sahce_ufcg.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.activities.PlaceRegisterActivity;
import com.example.sahce_ufcg.adapters.PlacesListingAdapter;
import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.dtos.PlaceResponseDto;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Mapper;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PlacesFragment extends Fragment {
    private View view;
    private FloatingActionButton addButton;
    private RecyclerView placeListing;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_places, container, false);
        getPreferences();
        setViews();
        getPlaces();
        return view;
    }

    public void setAddButton(){
        addButton = view.findViewById(R.id.add_place_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), PlaceRegisterActivity.class));
            }
        });
    }

    public void setViews(){
        placeListing = view.findViewById(R.id.place_listing);
        setAddButton();
    }

    public void getPlaces(){
        ApiService.getPlaceService().getAll(token).enqueue(
                new Callback<List<PlaceResponseDto>>() {
                    @Override
                    public void onResponse(Call<List<PlaceResponseDto>> call, Response<List<PlaceResponseDto>> response) {
                        if(response.isSuccessful()){
                            List<PlaceResponseDto> placeDtoList = response.body();
                            List<Place> placeList = Mapper.fromPlaceResponseDtoListToPlaceList(placeDtoList);
                            placeList = placeList.stream().sorted().collect(Collectors.toList());
                            fillPlaceListing(placeList);
                        }
                        else{
                            Util.showMessage(
                                    view.getContext(),
                                    "Http Status code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PlaceResponseDto>> call, Throwable t) {
                        Util.showMessage(
                                view.getContext(),
                                "Falha de Comunicação");
                        t.printStackTrace();
                    }
                }
        );
    }

    public void getPreferences(){
        token = Util.getTokenPreferences(view.getContext());
    }

    public void fillPlaceListing(List<Place> placeList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        PlacesListingAdapter placesListingAdapter = new PlacesListingAdapter(placeList);
        placesListingAdapter.setHasStableIds(true);

        placeListing.setLayoutManager(layoutManager);
        placeListing.setHasFixedSize(true);
        placeListing.setAdapter(placesListingAdapter);
    }
}