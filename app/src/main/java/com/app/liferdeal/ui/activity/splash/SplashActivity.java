package com.app.liferdeal.ui.activity.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.PhpInitialInfoModel;
import com.app.liferdeal.model.splash.SplashModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.adapters.splashadapter.SplashViewPagerAdapter;
import com.app.liferdeal.ui.fragment.LocationMapFragment;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;
import com.google.android.material.tabs.TabLayout;
import com.viewpagerindicator.CirclePageIndicator;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton btn_get_started;
    private ViewPager mImageViewPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] slideImages = {R.drawable.fst_rst_img_hd, R.drawable.group_2496, R.drawable.splashbanana};
    private List<SplashModel.SplashBannersList> slideImagesArray = new ArrayList<SplashModel.SplashBannersList>();
    private static final int CURRENT_LOCATION_REQUEST = 1001;
    private static final String TAG = SplashActivity.class.getName();
    private static final int PERMISSION_READ_PHONE_STATE = 11;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 111;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private double longitude = 0.0;
    private double latitude = 0.0;
    private String myDeviceModel = "";
    private String android_id = "";
    private String ip = "";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    Context context;
    private PrefsHelper prefsHelper;
    private String latlong;
    private ApiInterface apiInterface;
    public static SplashActivity mInstance;
    private ProgressDialog progressDialog;
    private ProgressBar banner_progress;

    @BindView(R.id.tl_tut)
    TabLayout tlTut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        ButterKnife.bind(this);
        init();
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
                        App.addLangToGson(context, response);
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

    private void init() {
        try {
            sharedPreferencesData = new SharedPreferencesData(getApplicationContext());
            context = SplashActivity.this;
            //FirebaseCrashlytics.getInstance();
            mInstance = this;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, PERMISSION_READ_PHONE_STATE);
                }
            } else {
                // goToNext();
            }

            if (!checkPermission(context)) {
                showPermissionDialog();
                getgps();
            }
            prefsHelper = new PrefsHelper(this);

            //btn_get_started.setVisibility(View.GONE);
            getgps();
            getDeviceInfo();
            getSplashData();

            btn_get_started = findViewById(R.id.btn_getstarted);
            mImageViewPager = findViewById(R.id.img_view_pager);
            banner_progress = findViewById(R.id.banner_progress);
            // slideImagesArray = populateList();

            btn_get_started.setOnClickListener(this);
            //   displayLocationSettingsRequest(SplashActivity.this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   /* private ArrayList<SlideImageModel> populateList() {

        ArrayList<SlideImageModel> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            SlideImageModel imageModel = new SlideImageModel();
            imageModel.setImage_drawable(slideImages[i]);
            list.add(imageModel);
        }

        return list;
    }*/

    SharedPreferencesData sharedPreferencesData;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getstarted:
                if (sharedPreferencesData != null) {
                    String firstTime = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.FIRSTTIMELOGINPAGE);
                    if (firstTime.equalsIgnoreCase("")) {
                        sharedPreferencesData.createNewSharedPreferences(Constants.PRICEPREFERENCE);
                        //sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.FIRSTTIMELOGINPAGE, "FirstTimeLogin");
                        Intent intent1 = new Intent(SplashActivity.this, LocationMapFragment.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        finish();
                    } else {

                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }


               /* Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();*/
                break;

            default:
                break;
        }
    }

    final int REQUEST = 112;

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

    public static boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    private void showPermissionDialog() {
        if (!SplashActivity.checkPermission(this)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    public String getgps() {
        try {
//            Log.d("GET GPS", "MAIN");

            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            if (lm != null) {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }
            Location net_loc = null, gps_loc = null, finalLoc = null;


            if (gps_enabled)
                assert lm != null;


            assert lm != null;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            } else {
                // Toast.makeText(SplashActivity.this, "Kindly allow location to Login", Toast.LENGTH_LONG).show();
            }
            gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (network_enabled)
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
            net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (gps_loc != null && net_loc != null) {

                //smaller the number more accurate result will
                if (gps_loc.getAccuracy() > net_loc.getAccuracy())
                    finalLoc = net_loc;
                else
                    finalLoc = gps_loc;

                // I used this just to get an idea (if both avail, its upto you which you want to take as I've taken location with more accuracy)

            } else {

                if (gps_loc != null) {
                    finalLoc = gps_loc;
                } else if (net_loc != null) {
                    finalLoc = net_loc;
                }
            }
            if (finalLoc != null) {
                longitude = finalLoc.getLongitude();
                latitude = finalLoc.getLatitude();
                latlong = String.valueOf((longitude + latitude));
                prefsHelper = new PrefsHelper(this);
                prefsHelper.createLatLong(String.valueOf(latitude), String.valueOf(longitude));

//            Log.d("LATLONG", "MAIN" + longitude + latitude);
                getAddressFromCurrentLatLong(String.valueOf(latitude), String.valueOf(longitude));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latlong;
    }

    @SuppressLint("HardwareIds")
    private void getIPDevice() {
        myDeviceModel = android.os.Build.MODEL;
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

//        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        assert wm != null;
        getLocalIpAddress();
//        ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        prefsHelper = new PrefsHelper(this);
        //  session.CreateDeviceSession("ANDROID", myDeviceModel, ip, android_id);
//        Log.d("IP", "MAIN" + android_id + myDeviceModel + ip);
    }

    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ip = Formatter.formatIpAddress(inetAddress.hashCode());
//                        Log.i("Main", "***** IP=" + ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
//            Log.e("Main", ex.toString());
        }
        return null;
    }

    private void getAddressFromCurrentLatLong(String latitudeString, String logitudeString) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(SplashActivity.this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitudeString), Double.parseDouble(logitudeString), 1);
            String address = addresses.get(0).getAddressLine(0);
//            System.out.println("===== addresss " + address);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();

            prefsHelper.savePref(Constants.SAVE_FULL_ADDRESS, address);
            prefsHelper.savePref(Constants.SAVE_CITY_NAME, city);
            prefsHelper.savePref(Constants.SAVE_STATE, state);
            prefsHelper.savePref(Constants.SAVE_COUNTRY, country);
            prefsHelper.savePref(Constants.SAVE_POSTAL_CODE, postalCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void getDeviceInfo() {
        try {
            String deviceId = Settings.Secure.getString(SplashActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            String platform = "Android";

            prefsHelper.savePref(Constants.deviceId, deviceId);
            prefsHelper.savePref(Constants.devicePlateform, platform);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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
                        prefsHelper.savePref(Constants.API_KEY, searchResult.getApiKey());
                        prefsHelper.savePref(Constants.APP_LOGO, searchResult.getAppLogo());
                        prefsHelper.savePref(Constants.APP_TOP_LOGO_ICON, searchResult.getAppTopHomeIcon());
                        prefsHelper.savePref(Constants.APP_DEF_LANG, searchResult.getAppDefaultLanguage());
                        prefsHelper.savePref(Constants.APP_LNG_NAME, searchResult.getLangName());
                        prefsHelper.savePref(Constants.APP_LNG_ICON, searchResult.getLangIcon());
                        prefsHelper.savePref(Constants.APP_CURRENCY, searchResult.getAppCurrency());
                        prefsHelper.savePref(Constants.google_map_key, searchResult.getAppGoogleKey());
                        prefsHelper.savePref(Constants.LNG_CODE, searchResult.getAppDefaultLanguage());
                        getLanguageData(searchResult.getApiKey(), searchResult.getAppDefaultLanguage());
                        getSplashImageData(searchResult.getApiKey(), searchResult.getAppDefaultLanguage());
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

    private void getSplashImageData(String apiKey, String appDefaultLanguage) {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<SplashModel> observable = apiInterface.getSplashImageData(apiKey, appDefaultLanguage, "1");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SplashModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SplashModel searchResult) {
                        //  showProgress();
                        //Toast.makeText(getApplicationContext(),"slide Array called",Toast.LENGTH_LONG).show();

                        btn_get_started.setVisibility(View.VISIBLE);
                        try {
                            btn_get_started.setText(searchResult.getSplashBannersList().get(0).getGetStartedText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        slideImagesArray = searchResult.getSplashBannersList();
                        mImageViewPager.setAdapter(new SplashViewPagerAdapter(SplashActivity.this, slideImagesArray));
                        try {
                            //btn_get_started.setVisibility(View.VISIBLE);
                            btn_get_started.setText(searchResult.getSplashBannersList().get(0).getGetStartedText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        CirclePageIndicator indicator = (CirclePageIndicator)
                                findViewById(R.id.indicator);

                        indicator.setViewPager(mImageViewPager);
                        tlTut.setupWithViewPager(mImageViewPager, true);
                        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
                        indicator.setRadius(5 * density);

                        NUM_PAGES = slideImagesArray.size();

                        // Auto start of viewpager
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == NUM_PAGES) {
                                    currentPage = 0;
                                }
                                mImageViewPager.setCurrentItem(currentPage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, 3000, 2500);

                        mImageViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                            @Override
                            public void transformPage(@NonNull View page, float position) {
                            }
                        });


                        banner_progress.setVisibility(View.GONE);
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

    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
