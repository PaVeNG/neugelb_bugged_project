package com.bugged.themoviedb.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Singleton;


@Singleton
public class PreferencesDataHelper {

    private static final String GENERAL_PREFERENCE_NAME = "generalpreferences";


    public static void store(Context context, String key, String value) {

        SharedPreferences settings = context.getSharedPreferences(GENERAL_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String retrieve(Context context, String key) {
        Log.d("Watch Preference "+context,"TEst =" +key);
        SharedPreferences settings = context.getSharedPreferences(GENERAL_PREFERENCE_NAME, 0);
        return settings.getString(key, "");
    }

    public static void clearPref(Context context){
        SharedPreferences settings = context.getSharedPreferences(GENERAL_PREFERENCE_NAME, 0);
        settings.edit().clear().apply();
    }


}
