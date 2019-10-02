package com.bugged.themoviedb.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bugged.themoviedb.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper implements UserDataHelper{

    public static final String PREF_FILE_NAME = "android_boilerplate_pref_file";
    private Context context;
    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        this.context = context;
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }


    public void clear() {
        mPref.edit().clear().apply();
    }

    @Override
    public void storeValue(String key, String value) {
        PreferencesDataHelper.store(context,key,value);
    }

    @Override
    public String getValue(String key) {
        Log.d("check context", " "+context);
        return PreferencesDataHelper.retrieve(context, key);
    }

    @Override
    public void clearAll() {
        PreferencesDataHelper.clearPref(context);
    }
}
