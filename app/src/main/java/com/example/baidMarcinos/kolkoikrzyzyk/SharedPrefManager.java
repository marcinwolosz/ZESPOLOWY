package com.example.baidMarcinos.kolkoikrzyzyk;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MARCIN on 2017-05-27.
 */

public class SharedPrefManager {

    private static final String PREF_NAME = "application_preferences";

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context applicationContext) {
        this.context = applicationContext;
    }

    public void saveString(String key, String value) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }


    public String getString(String key, String defaultValue) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }
}
