package com.example.sahce_ufcg.util;

import static com.example.sahce_ufcg.util.Constants.SHARED_LOGIN_PREFERENCES_KEY;
import static com.example.sahce_ufcg.util.Constants.TOKEN_KEY;
import static com.example.sahce_ufcg.util.Constants.USER_EMAIL_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Util {

    public static void showMessage(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static String getTokenPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARED_LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String defaultValue = "";
        return sharedPreferences.getString(TOKEN_KEY, defaultValue);
    }

    public static String getEmailPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARED_LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String defaultValue = "";
        return sharedPreferences.getString(USER_EMAIL_KEY, defaultValue);
    }
}
