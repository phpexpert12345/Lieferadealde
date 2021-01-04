package com.app.liferdeal.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesData {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SharedPreferencesData(Context context) {
        this.context = context;
    }


    public void createNewSharedPreferences(String sharedPreferenceName) {
        sharedPreferences = context.getSharedPreferences(sharedPreferenceName, MODE_PRIVATE);
    }

    public void setSharedPreferenceData(String sharedPreferenceName, String fieldName, String data) {
        sharedPreferences = context.getSharedPreferences(sharedPreferenceName, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(fieldName, data);
        editor.commit();

    }

    public String getSharedPreferenceData(String sharedPreferenceName, String fieldName) {
        if (context!=null){
            sharedPreferences = context.getSharedPreferences(sharedPreferenceName, MODE_PRIVATE);
            return sharedPreferences.getString(fieldName, "");
        }
        return "";
    }

    public void clearSharedPreferenceData(String sharedPreferenceName) {
        sharedPreferences = context.getSharedPreferences(sharedPreferenceName, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void clearSingleFieldSharedData(String sharedPreferenceName, String fieldName){
        sharedPreferences = context.getSharedPreferences(sharedPreferenceName, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove(fieldName);
        editor.apply();
    }
}
