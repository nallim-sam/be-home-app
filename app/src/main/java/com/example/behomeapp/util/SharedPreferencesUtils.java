package com.example.behomeapp.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedPreferencesUtils {

    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "nombre";
    public static void saveUserData(Context context, String email, String name) {

        final SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        //editor.commit();
        editor.apply();

    }


    public static String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public static String getName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public static void clearCredentials(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
