package com.example.sahce_ufcg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.User;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity {
    TextView textTestView;
    ImageView imageTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        textTestView = findViewById(R.id.text_test);
        imageTestView = findViewById(R.id.image_test);

        User user = (User) getIntent().getExtras().getSerializable("user");
        textTestView.setText(user.getEmail());

        String requestParam = "?userEmail=" + user.getEmail();
        String uri = "http://10.0.2.2:8080/v1/anonymous/users/documentPicture" + requestParam;
        Picasso.get().load(uri).into(imageTestView);
    }
}