package com.example.sahce_ufcg.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.activities.PlaceRegisterActivity;
import com.example.sahce_ufcg.adapters.PlacesListingAdapter;
import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlacesFragment extends Fragment {
    private View view;
    private FloatingActionButton addButton;
    private RecyclerView placeListing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_places, container, false);
        setViews();
        fillPlaceListing();
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

    public void fillPlaceListing(){
        List<Place> placeList = new ArrayList<>();
        placeList.add(new Place(
                "Quadra A",
                Arrays.asList(User.UserType.INTERNAL_USER)));
        placeList.add(new Place(
                "Quadra B",
                Arrays.asList(User.UserType.EXTERNAL_USER)));
        placeList.add(new Place(
                "Quadra C",
                Arrays.asList(User.UserType.INTERNAL_USER, User.UserType.EXTERNAL_USER)));
        placeList.add(new Place(
                "Quadra D",
                Arrays.asList(User.UserType.INTERNAL_USER, User.UserType.EXTERNAL_USER)));
        placeList.add(new Place(
                "Quadra E",
                Arrays.asList(User.UserType.INTERNAL_USER, User.UserType.EXTERNAL_USER)));
        placeList.add(new Place(
                "Quadra F",
                Arrays.asList(User.UserType.INTERNAL_USER, User.UserType.EXTERNAL_USER)));
        placeList.add(new Place(
                "Quadra G",
                Arrays.asList(User.UserType.INTERNAL_USER, User.UserType.EXTERNAL_USER)));
        placeList.add(new Place(
                "Quadra H",
                Arrays.asList(User.UserType.INTERNAL_USER, User.UserType.EXTERNAL_USER)));
        placeList.add(new Place(
                "Quadra I",
                Arrays.asList(User.UserType.INTERNAL_USER, User.UserType.EXTERNAL_USER)));
        placeList.add(new Place(
                "Quadra J",
                Arrays.asList(User.UserType.INTERNAL_USER, User.UserType.EXTERNAL_USER)));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        PlacesListingAdapter placesListingAdapter = new PlacesListingAdapter(placeList);
        placesListingAdapter.setHasStableIds(true);

        placeListing.setLayoutManager(layoutManager);
        placeListing.setHasFixedSize(true);
        placeListing.setAdapter(placesListingAdapter);
    }
}