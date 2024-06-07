package com.example.behomeapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private static final String APP_PREFS = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PISO_ID = "id_piso";


    /**
     * Guarda el correo electrónico del usuario
     *
     * @param context contexto usado para acceder a las SharedPreferences
     * @param email   correo electronico del usuario
     */
    public static void saveUserEmail(Context context, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    /**
     * Obtiene el correo electrónico del usuario
     *
     * @param context contexto usado para acceder a las SharedPreferences
     * @return correo electrónico del usuario
     */
    public static String getUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    /**
     * Guarda el ID del piso
     *
     * @param context contexto usado para acceder a las SharedPreferences
     * @param pisoId  id del piso
     */
    public static void savePisoId(Context context, String pisoId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PISO_ID, pisoId);
        editor.apply();
    }

    /**
     * Obtiene el el ID del piso
     *
     * @param context contexto usado para acceder a las SharedPreferences
     * @return id del piso
     */
    public static String getPisoId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PISO_ID, "");
    }

    /**
     * Borra las credenciales de la sesión
     *
     * @param context contexto usado para acceder a las SharedPreferences
     */
    public static void clearCredentials(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_EMAIL);
        editor.apply();
    }

    /**
     * Borra el ID del piso
     *
     * @param context contexto usado para acceder a las SharedPreferences
     */
    public static void clearPisoId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_PISO_ID);
        editor.apply();
    }

    /**
     * Borra todas las preferencias
     *
     * @param context contexto usado para acceder a las SharedPreferences
     */
    public static void clearAllPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
