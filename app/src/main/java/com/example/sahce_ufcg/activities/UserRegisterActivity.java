package com.example.sahce_ufcg.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sahce_ufcg.R;
import com.example.sahce_ufcg.dtos.UserResponseDto;
import com.example.sahce_ufcg.models.User;
import com.example.sahce_ufcg.services.ApiService;
import com.example.sahce_ufcg.util.RealPathUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends AppCompatActivity {
    TextInputEditText nameTextInput, phoneTextInput, addressTextInput, emailTextInput, passwordTextInput;
    Button sendButton, addImageButton;
    RadioButton internalCommunityRadioBtn, externalCommunityRadioBtn;
    ImageView documentImageView;
    private static final int SELECT_PHOTO = 10;
    private String path;
    private Bitmap documentImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        setViews();
    }

    public void setViews(){
        nameTextInput = findViewById(R.id.name_field);
        phoneTextInput = findViewById(R.id.phone_field);
        addressTextInput = findViewById(R.id.address_field);
        emailTextInput = findViewById(R.id.email_field);
        passwordTextInput = findViewById(R.id.password_field);
        internalCommunityRadioBtn = findViewById(R.id.internal_community_radio_btn);
        externalCommunityRadioBtn = findViewById(R.id.external_community_radio_btn);
        documentImageView = findViewById(R.id.document_image);
        setSendButton();
        setAddImageButton();
    }

    public void setAddImageButton(){
        addImageButton = findViewById(R.id.add_image_button);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    openCamera(v);
                }
                else{
                    ActivityCompat.requestPermissions(
                            UserRegisterActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                }
            }
        });
    }

    public void openCamera(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_PHOTO && resultCode == RESULT_OK){
            Uri uri = data.getData();
            path = RealPathUtil.getRealPath(getBaseContext(), uri);
            documentImageBitmap = BitmapFactory.decodeFile(path);
            documentImageView.setImageBitmap(documentImageBitmap);
        }
    }

    public void setSendButton(){
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTextInput.getText().toString();
                String phone = phoneTextInput.getText().toString();
                String address = addressTextInput.getText().toString();
                String email = emailTextInput.getText().toString();
                String password = passwordTextInput.getText().toString();
                User.UserType userType = getUserTypeSelected();
                byte[] documentImageBytes = mapperFromBitmapFromByteArray(documentImageBitmap);

                User newUser = new User(name, phone, address, email, password, userType, documentImageBytes);
                ApiService.getUserService().createUser(newUser).enqueue(new Callback<UserResponseDto>() {
                    @Override
                    public void onResponse(Call<UserResponseDto> call, Response<UserResponseDto> response) {
                        if(response.isSuccessful()){
                            showMessage("Cadastro realizado com sucesso!");
                            finish();
                        } else{
                            showMessage("Http Status Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponseDto> call, Throwable t) {
                        showMessage("Houve um erro com a resposta do servidor.");
                    }
                });
            }
        });
    }

    public byte[] mapperFromBitmapFromByteArray(Bitmap bitmap){
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer b = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(b);
        byte[] byteArray = new byte[size];
        try {
            b.get(byteArray, 0, byteArray.length);
        } catch (BufferUnderflowException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public User.UserType getUserTypeSelected(){
        if (internalCommunityRadioBtn.isChecked()){
            return User.UserType.INTERNAL_USER;
        }
        else if(externalCommunityRadioBtn.isChecked()) {
            return User.UserType.EXTERNAL_USER;
        }
        return null;
    }

    public void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}