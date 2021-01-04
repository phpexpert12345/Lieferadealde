package com.app.liferdeal.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.app.liferdeal.ui.activity.splash.SplashActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;


public class PrefsHelper {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private static PrefsHelper instance;
    private Context _context;

    public static PrefsHelper getPrefsHelper() {
        return instance;
    }

    public void LogintoApp() {
        // Storing login value as TRUE
        editor.putBoolean(Constants.IsLogin, true);
        editor.apply();

    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(Constants.IsLogin, false);
    }

    public void logoutUser() {
        editor.putBoolean(Constants.IsLogin, false);

        // Clearing all data from Shared Preferences
        editor.clear();
        editor.apply();


        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SplashActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public void loggedOut() {

        editor.putBoolean(Constants.IsLogin, false);
        editor.apply();
    }

    public PrefsHelper(Context context) {
        instance = this;
        this._context = context;
        String prefsFile = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public PrefsHelper(Context context, String file) {
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }


    public void savePref(String key, Object value) {
        delete(key);

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }

        editor.apply();
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean isPrefExists(String key) {
        return sharedPreferences.contains(key);
    }

    public void clearAllPref() {
        editor.clear();
        editor.apply();
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public void set(String key, String value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putString(key, value);
            prefsEditor.apply();
        }
    }

  /*  public void createLoginSession(String firstName, String middlename, String lname, String memcode, String MemberID, String email, String mobile, String profileimage, String dob, String gender, String countryFlag, String countryCode, String userType, int countryISO, String memberUniqueId, String memberDeviceUniqueId) {
        editor.putString(Constants.firstName, firstName);
        editor.putString(Constants.email, email);        // Storing email in pref
        editor.putString(Constants.lastName, lname);
        editor.putString(Constants.middleName, middlename);
        editor.putString(Constants.profile, profileimage);
        editor.putString(Constants.memberCode, memcode);
        editor.putString(Constants.memberId, MemberID);
        editor.putString(Constants.mobile, mobile);
        editor.putString(Constants.dob, dob);
        editor.putString(Constants.gender, gender);
        editor.putString(Constants.countryFlag, countryFlag);
        editor.putString(Constants.CountryCode, countryCode);
        editor.putString(Constants.usertypeId, userType);
        editor.putInt(Constants.countryISD,countryISO);
        editor.putString(Constants.MEMBERUNIQUEID,memberUniqueId);
        editor.putString(Constants.MEMBERDeviceUNIQUEID,memberDeviceUniqueId);
        editor.apply();

    }*/

   /* public HashMap<String, Object> getUserDetails() {

        HashMap<String, Object> user = new HashMap<String, Object>();
        user.put(Constants.firstName, sharedPreferences.getString(Constants.firstName, ""));
        user.put(Constants.lastName, sharedPreferences.getString(Constants.lastName, ""));
        user.put(Constants.middleName, sharedPreferences.getString(Constants.middleName, ""));
        user.put(Constants.mobile, sharedPreferences.getString(Constants.mobile, ""));
        user.put(Constants.memberCode, sharedPreferences.getString(Constants.memberCode, ""));
        user.put(Constants.memberId, sharedPreferences.getString(Constants.memberId, ""));
        user.put(Constants.email, sharedPreferences.getString(Constants.email, ""));
        user.put(Constants.memberEmail, sharedPreferences.getString(Constants.email, ""));
        user.put(Constants.profile, sharedPreferences.getString(Constants.profile, ""));
        user.put(Constants.dob, sharedPreferences.getString(Constants.dob, ""));
        user.put(Constants.gender, sharedPreferences.getString(Constants.gender, ""));
        user.put(Constants.countryFlag, sharedPreferences.getString(Constants.countryFlag, ""));
        user.put(Constants.CountryCode, sharedPreferences.getString(Constants.CountryCode, ""));
        user.put(Constants.usertypeId, sharedPreferences.getString(Constants.usertypeId, ""));
        user.put(Constants.countryISD,sharedPreferences.getInt(Constants.countryISD,0));
        user.put(Constants.MEMBERUNIQUEID,sharedPreferences.getString(Constants.MEMBERUNIQUEID,""));
        user.put(Constants.MEMBERDeviceUNIQUEID,sharedPreferences.getString(Constants.MEMBERDeviceUNIQUEID,""));

        return user;
    }*/

    public HashMap<String, String> getLatLong() {
        HashMap<String, String> latlang = new HashMap<>();

        latlang.put(Constants.latitude, sharedPreferences.getString(Constants.latitude, ""));
        latlang.put(Constants.longitude, sharedPreferences.getString(Constants.longitude, ""));

        return latlang;
    }

    public void createLatLong(String lat, String longi) {
        editor.putString(Constants.latitude, lat);
        editor.putString(Constants.longitude, longi);
        editor.apply();

    }

    public void CreateDeviceSession(String devicename, String devicemodel, String ip, String deviceId) {
        editor.putString(Constants.deviceModel, devicemodel);
        editor.putString(Constants.deviceName, devicename);
        editor.putString(Constants.deviceId, deviceId);
        editor.putString(Constants.Device_Ip, ip);
        editor.commit();
    }
}
