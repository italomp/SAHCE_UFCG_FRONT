package com.example.sahce_ufcg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.Util;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        ApiService.getUserService().getUserDocumentPicture("italo@gmail.com").enqueue(new Callback<byte[]>() {
            @Override
            public void onResponse(Call<byte[]> call, Response<byte[]> response) {
                System.out.println(response.code());
                if(response.isSuccessful()){
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<byte[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}