package com.example.sahce_ufcg.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.activities.UserRegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class UsersFragment extends Fragment {
    View view;
    FloatingActionButton addUserButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);

        setAddUserButton();
        return view;
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
}