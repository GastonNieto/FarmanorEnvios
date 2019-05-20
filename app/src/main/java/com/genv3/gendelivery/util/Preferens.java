package com.genv3.gendelivery.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.genv3.gendelivery.Objects.EntregasDisponibles;
import com.genv3.gendelivery.Objects.EntregasTomadas;
import com.google.gson.Gson;

public class Preferens {
    private static final String KEY_OBJETO ="offline";
    private static final String KEY_GUARDIA = "idCadete";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_RECUERDAME = "recuerdame";
    private static final String KEY_CONTROL = "control";
    private static final String KEY_LOG = "login";
    private static final String STATE_LOGIN = "LOGIN";
    private static final String STATE_LOGOUT = "LOGOUT";

    public static String getStateLogin() {
        return STATE_LOGIN;
    }

    public static String getStateLogout() {
        return STATE_LOGOUT;
    }

    public static String getKeyObjeto() {
        return KEY_OBJETO;
    }

    public static String getKeyLog() {
        return KEY_LOG;
    }

    public static void setEntregasDisponibles (Context context, final String key, final EntregasDisponibles disponibles){
        EntregasDisponibles myObject = disponibles;
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,json);
        editor.commit();
    }
    public static String getEntregasDisponibles(Context context,final String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,"");

    }
    public static String getKeyControl() {
        return KEY_CONTROL;
    }

    public static String getKeyGuardiaNombre() {
        return KEY_GUARDIA_NOMBRE;
    }

    private static final String KEY_GUARDIA_NOMBRE = "nombre";

    public static String getKeyGuardiaApellido() {
        return KEY_GUARDIA_APELLIDO;
    }

    private static final String KEY_GUARDIA_APELLIDO = "apellido";

    public static String getKeyRecuerdame() {
        return KEY_RECUERDAME;
    }

    public static String getKeyUser() {
        return KEY_USER;
    }

    public static String getKeyPass() {
        return KEY_PASS;
    }

    public static String getKeyGuardia() {
        return KEY_GUARDIA;
    }

    public static Long getInteger(Context context, final String key) {
        android.content.SharedPreferences shaPref = PreferenceManager.getDefaultSharedPreferences(context);
        return shaPref.getLong(key, 0);
    }

    public static void setInteger(Context context, final String key, final Long i) {
        android.content.SharedPreferences shaPref = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = shaPref.edit();
        editor.putLong(key, i);
        editor.commit();
    }

    public static String getString(Context context, final String key) {
        android.content.SharedPreferences shaPref = PreferenceManager.getDefaultSharedPreferences(context);
        return shaPref.getString(key, "");
    }

    public static void setString(Context context, final String key, final String value) {
        android.content.SharedPreferences shaPref = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = shaPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, final String key, final boolean defaultValue) {

        android.content.SharedPreferences shaPref = PreferenceManager.getDefaultSharedPreferences(context);
        return shaPref.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, final String key, final boolean value) {

        android.content.SharedPreferences shaPref = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = shaPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
