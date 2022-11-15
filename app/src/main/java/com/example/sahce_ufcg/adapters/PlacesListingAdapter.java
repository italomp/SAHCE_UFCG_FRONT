package com.example.sahce_ufcg.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.Place;
import com.example.sahce_ufcg.models.User;

import java.util.List;

public class PlacesListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Place> placesList;

    public PlacesListingAdapter(List<Place> placesList){
        this.placesList = placesList;
    }

    public void setPlacesList(List<Place> placesList){
        this.placesList = placesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(
                R.layout.places_listing_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView cardTitleView = ((PlaceViewHolder) holder).getCardTitle();
        CheckBox externalCommunityView = ((PlaceViewHolder) holder).getExternalCommunity();
        CheckBox internalCommunityView = ((PlaceViewHolder) holder).getInternalCommunity();
        Place currPlace = placesList.get(position);
        System.out.println(currPlace.getUsersThatCanUseThePlace().size());

        cardTitleView.setText(currPlace.getName());
        if (currPlace.getUsersThatCanUseThePlace().contains(User.UserType.INTERNAL_USER)){
            System.out.println("currPlace.getUsersThatCanUseThePlace().contains(User.UserType.INTERNAL_USER)");
//            internalCommunityView.isChecked();
            internalCommunityView.setChecked(true);
        }
        if (currPlace.getUsersThatCanUseThePlace().contains(User.UserType.EXTERNAL_USER)){
            System.out.println("currPlace.getUsersThatCanUseThePlace().contains(User.UserType.EXTERNAL_USER)");
//            externalCommunityView.isChecked();
            externalCommunityView.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{
        TextView cardTitle;
        CheckBox externalCommunity, internalCommunity;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.card_title);
            externalCommunity = itemView.findViewById(R.id.checkbox_external_community);
            internalCommunity = itemView.findViewById(R.id.checkbox_internal_community);
        }

        public TextView getCardTitle() {
            return cardTitle;
        }

        public CheckBox getExternalCommunity() {
            return externalCommunity;
        }

        public CheckBox getInternalCommunity() {
            return internalCommunity;
        }
    }
}
