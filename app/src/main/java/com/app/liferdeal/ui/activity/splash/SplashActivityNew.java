package com.app.liferdeal.ui.activity.splash;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.PhpInitialInfoModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class SplashActivityNew extends AppCompatActivity implements LocationListener {

    LinearLayout llgps, llani;
    TextView txtgps;
    final int REQUEST = 112;
    private final int SPLASH_TIME_OUT = 1000;
    double latitude, longitude;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    LocationManager locationManager;
    Location location;
    String lat = "", lng = "";
    RequestQueue requestQueue;
    ImageView imgnoapp;
    public static String customer_city, customer_locality, customer_country, customer_country_code, customer_postcode, customer_full_address, customer_lat, customer_long, customer_search, customer_search_display,
            currency_symbol;
    private PrefsHelper myPref;

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23 && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private ApiInterface apiInterface;

    private void getSplashData() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<PhpInitialInfoModel> observable = apiInterface.getSplashData();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PhpInitialInfoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PhpInitialInfoModel searchResult) {
                        //  showProgress();
                        myPref.savePref(Constants.API_KEY, searchResult.getApiKey());
                        myPref.savePref(Constants.APP_LOGO, searchResult.getAppLogo());
                        myPref.savePref(Constants.APP_TOP_LOGO_ICON, searchResult.getAppTopHomeIcon());
                        myPref.savePref(Constants.APP_DEF_LANG, searchResult.getAppDefaultLanguage());
                        myPref.savePref(Constants.APP_LNG_NAME, searchResult.getLangName());
                        myPref.savePref(Constants.APP_LNG_ICON, searchResult.getLangIcon());
                        myPref.savePref(Constants.APP_CURRENCY, searchResult.getAppCurrency());
                        myPref.savePref(Constants.google_map_key, searchResult.getAppGoogleKey());
                        myPref.savePref(Constants.LNG_CODE, searchResult.getAppDefaultLanguage());
                        getLanguageData(searchResult.getApiKey(), searchResult.getAppDefaultLanguage());
//                        Log.e("AppCurrency=",searchResult.getAppCurrency());
                        //Toast.makeText(getApplicationContext(),searchResult.getAppCurrency()+"====Other"+searchResult.getLangName(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // hideProgress();
//                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });

    }

    private void getLanguageData(String apiKey, String appDefaultLanguage) {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<LanguageResponse> observable = apiInterface.getLanguage(apiKey, appDefaultLanguage);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LanguageResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LanguageResponse response) {
                        App.addLangToGson(SplashActivityNew.this, response);
//                        btn_get_started.setText(response.);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // hideProgress();
//                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
            if (!
                    hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST);
                // q=true;
            } else {
                //Do here
                toStart();
            }
        } else {
            //Do here
            toStart();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fabric.with(this, new Crashlytics());
        //   statusBarColor();
        setContentView(R.layout.splash_activity_layout);
        myPref = new PrefsHelper(SplashActivityNew.this);
        requestQueue = Volley.newRequestQueue(this);
        getSplashData();
      /*  txtgps = findViewById(R.id.txtgps);
        llgps = (LinearLayout) findViewById(R.id.llgps);
        llani = (LinearLayout) findViewById(R.id.llani);
        imgnoapp = (ImageView) findViewById(R.id.imgnoapp);*/
     /*   txtgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llgps.setVisibility(View.GONE);
                llani.setVisibility(View.GONE);
                showDilog();
            }
        });*/
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    toStart();
                    //Do here
                } else {
                    //Toast.makeText(this, "The app was not allowed to write to your storage.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void showDilog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivityNew.this);
        dialog.setMessage("Gps network not enabled please enable to continue");
        dialog.setPositiveButton("Open_location_settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);

            }
        });
        dialog.setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {
            // TODO Auto-generated method stub
            llgps.setVisibility(View.VISIBLE);
            llani.setVisibility(View.GONE);
//                imgsplash.setVisibility(View.VISIBLE);
        });
        dialog.show();
    }

    private void fn_getlocation() {

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, (LocationListener) this);
                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    }
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {

                        Log.e("loc-latitude", location.getLatitude() + "");
                        Log.e("loc-longitude", location.getLongitude() + "");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        lat = String.valueOf(latitude);
                        lng = String.valueOf(longitude);
//                         Toast.makeText(this, "networl"+lat+lng, Toast.LENGTH_SHORT).show();

                    }
                }
            }

            if (isGPSEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, (LocationListener) this);
                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
//
                        lat = (String.format("%.6f", latitude));
                        lng = (String.format("%.6f", longitude));

                    }
                }
            }

        }

        if (lat.equals("") || lat.equals(null)) {
            fn_getlocation();
        } else {
            //   getData();

            /*startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            finish();*/
        }
    }

    public void toStart() {
        new Handler().postDelayed(() -> {
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    boolean gps_enabled = false;
                    boolean network_enabled = false;
                    try {
                        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    } catch (Exception ex) {
                    }

                    try {
                        network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    } catch (Exception ex) {
                    }

                    if (!gps_enabled && !network_enabled) {
                        // notify user
                        showDilog();
                    } else {

                        fn_getlocation();
                    }
                }, SPLASH_TIME_OUT
        );

    }

   /* public void getData() {


        InterUserdata interfaceRetrofit = RetrofitAdapter.createService(InterUserdata.class);
        Call<Model_location> userLoginCall = interfaceRetrofit.userLocation(lng, lat);
        userLoginCall.enqueue(new Callback<Model_location>() {
            @Override
            public void onResponse(Call<Model_location> call, retrofit2.Response<Model_location> response) {
                if (response.isSuccessful()) {
                    Model_location body = response.body();
                    customer_city = body.getCustomer_city();
                    customer_locality = body.getCustomer_locality();
                    customer_country = body.getCustomer_country();
                    customer_country_code = body.getCustomer_country_code();
                    customer_postcode = body.getCustomer_postcode();
                    customer_full_address = body.getCustomer_full_address();
                    myPref.setPickupAdd(body.getCustomer_full_address());
                    customer_lat = body.getCustomer_lat();
                    customer_long = body.getCustomer_long();
                    customer_search = body.getCustomer_search();
                    customer_search_display = body.getCustomer_search_display();
                    currency_symbol = body.getCustomer_currency();

                    myPref.setReferId(customer_search_display);
                    myPref.setEmergency(customer_search);
                    myPref.setCity(customer_city);
                    myPref.setDropAdd(customer_locality);
                    myPref.setState(customer_country);
                    myPref.setUserName(customer_country_code);
                    myPref.setFirebaseTokenId(customer_postcode);
                    myPref.setPickupAdd(customer_full_address);
                    myPref.setLatitude(customer_lat);
                    myPref.setLongitude(customer_long);

                    if (currency_symbol != null) {
                        currency_symbol = Utils.getCurrencySymbol(currency_symbol);

                    } else currency_symbol = "$";

                    myPref.setBookid(currency_symbol);


                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Model_location> call, Throwable t) {
                Toast.makeText(SplashScreenActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }*/

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    static class Utils {
        public static SortedMap<Currency, Locale> currencyLocaleMap;

        static {
            currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
                public int compare(Currency c1, Currency c2) {
                    return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
                }
            });
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    currencyLocaleMap.put(currency, locale);
                } catch (Exception e) {
                }
            }
        }


        public static String getCurrencySymbol(String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
            return currency.getSymbol(currencyLocaleMap.get(currency));
        }

    }
}
