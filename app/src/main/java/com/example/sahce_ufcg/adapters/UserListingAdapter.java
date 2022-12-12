package com.example.sahce_ufcg.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.activities.UserDetailsActivity;
import com.example.sahce_ufcg.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<User> userList;

    public UserListingAdapter(){
        this.userList = new ArrayList<>();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView inactiveUserCard = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inactive_user_card, parent, false);
        return new UserViewHolder(inactiveUserCard);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView userNameView = ((UserViewHolder) holder).getUserNameView();
        ImageButton detailsButton = ((UserViewHolder) holder).getDetailsButton();

        userNameView.setText(userList.get(position).getName());
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), UserDetailsActivity.class);
                intent.putExtra("user", userList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView userNameView;
        ImageButton detailsButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameView = itemView.findViewById(R.id.user_name);
            detailsButton = itemView.findViewById(R.id.details_button);
        }

        public TextView getUserNameView() {
            return userNameView;
        }

        public ImageButton getDetailsButton() {
            return detailsButton;
        }
    }
}
