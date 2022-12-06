package com.example.sahce_ufcg.fragments;

import static com.example.sahce_ufcg.util.Mapper.fromUserResponseDtoListToUserList;

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
import com.example.sahce_ufcg.activities.UserRegisterActivity;
import com.example.sahce_ufcg.adapters.UserListingAdapter;
import com.example.sahce_ufcg.dtos.UserResponseDto;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersFragment extends Fragment {
    private View view;
    private FloatingActionButton addUserButton;
    private RecyclerView userListing;
    private UserListingAdapter adapter;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);
        getPreferences();
        getInactiveUsers();
        setUserListing();
        setAddUserButton();
        return view;
    }

    public void getPreferences(){
        token = Util.getTokenPreferences(view.getContext());
    }

    public void setUserListing(){
        userListing = view.findViewById(R.id.user_listing);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new UserListingAdapter();
        adapter.setHasStableIds(true);
        userListing.setHasFixedSize(true);
        userListing.setLayoutManager(layoutManager);
        userListing.setAdapter(adapter);
    }

    public void setAddUserButton(){
        addUserButton = view.findViewById(R.id.add_user_button);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UserRegisterActivity.class));
            }
        });
    }

    public void getInactiveUsers(){
        ApiService.getUserService().getAllInactiveUsers(token).enqueue(
                new Callback<List<UserResponseDto>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(
                            Call<List<UserResponseDto>> call,
                            Response<List<UserResponseDto>> response
                    ){
                     if(response.isSuccessful()){
                         List<UserResponseDto> userDtoList = response.body();
                         List<User> userList = fromUserResponseDtoListToUserList(userDtoList);
                         userListing.removeAllViews();
                         adapter.setUserList(userList);
                     }
                     else{
                         Util.showMessage(getContext(), "Http Status Code: " + response.code());
                     }
                    }

                    @Override
                    public void onFailure(Call<List<UserResponseDto>> call, Throwable t) {
                        Util.showMessage(getContext(), "Falha na comunicação");
                    }
                }
        );
    }
}