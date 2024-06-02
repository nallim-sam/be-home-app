package com.example.behomeapp.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "nombre";


    public static void saveUserData(Context context, String email) {

        final SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();

    }


    public static String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "");
    }



    public static void clearCredentials(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
