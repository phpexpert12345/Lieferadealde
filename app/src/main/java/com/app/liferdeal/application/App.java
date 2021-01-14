package com.app.liferdeal.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.util.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;

public class App extends Application {
    private static App instance;

    public static String USER_TOKEN;
    public static String USER_ID;
    public static String USER_NAME;
    public static String USER_LOGGED;
    public static String INSTANCE_URL;
    public static String TOKEN_TYPE;
    public static String SIGNATURE;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//                handleUncaughtException(thread, ex);
//            }
//        });
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        String stackTrace = Log.getStackTraceString(e);
        String message = e.getMessage();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"savita@phpexperttechnologies.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "MyApp Crash log file");
        intent.putExtra(Intent.EXTRA_TEXT, stackTrace);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        startActivity(intent);
    }

    public static App getInstance() {
        return instance;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;

    public static void addLangToGson(Context context, LanguageResponse model) {
        SharedPreferences.Editor editorPL = context.getSharedPreferences(Constants.LANGUAGE_DATA, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editorPL.putString(Constants.LANGUAGE_FORM_DATA, json);
        editorPL.apply();
    }

    public static LanguageResponse retrieveLangFromGson(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.LANGUAGE_DATA, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(Constants.LANGUAGE_FORM_DATA, "");
        Log.i("reason",json);
        return gson.fromJson(json, LanguageResponse.class);
    }
}