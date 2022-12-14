package com.example.sahce_ufcg.activities;

import static com.example.sahce_ufcg.models.User.UserType.INTERNAL_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextView userNameView, userPhoneView, userAddressView, userEmailView, userTypeView;
    private ImageView documentPictureView;
    private Button validateButton;
    private User user;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        user = (User) getIntent().getExtras().getSerializable("user");
        token = Util.getTokenPreferences(getApplicationContext());
        setViews();
    }

    public void setViews(){
        setUserNameView();
        setUserPhoneView();
        setUerAddressView();
        setUserEmailView();
        setUserTypeView();
        setDocumentPictureView();
        setValidateButton();
    }

    public void setUserNameView(){
        userNameView = findViewById(R.id.user_name_text);
        userNameView.setText(user.getName());
    }

    public void setUserPhoneView(){
        userPhoneView = findViewById(R.id.user_phone_text);
        userPhoneView.setText(user.getPhone());
    }

    public void setUerAddressView(){
        userAddressView = findViewById(R.id.user_address_text);
        userAddressView.setText(user.getAddress());
    }

    public void setUserEmailView(){
        userEmailView = findViewById(R.id.user_email_text);
        userEmailView.setText(user.getEmail());
    }

    public void setUserTypeView(){
        userTypeView = findViewById(R.id.user_type_text);
        userTypeView.setText(user.getUserType() == INTERNAL_USER ? "Interna" : "Externa");
    }

    public void setDocumentPictureView(){
        documentPictureView = findViewById(R.id.document_image);
        String requestParam = "?userEmail=" + user.getEmail();
        String uri = "http://10.0.2.2:8080/v1/anonymous/users/documentPicture" + requestParam;
        Picasso.get().load(uri).into(documentPictureView);
    }

    public void setValidateButton(){
        validateButton = findViewById(R.id.btn_validate);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendValidateRequest();
            }
        });
    }

    public void sendValidateRequest(){
        ApiService.getUserService().activeUser(user.getEmail(), token).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Util.showMessage(getBaseContext(), "Validação concluída.");
                            finish();
                        }
                        else{
                            Util.showMessage(
                                    getBaseContext(),"Http Status Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Util.showMessage(getBaseContext(), "Falha de Comunicação");
                        t.printStackTrace();
                    }
                }
        );
    }
}