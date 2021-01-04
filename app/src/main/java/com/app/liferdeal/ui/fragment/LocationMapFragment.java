package com.app.liferdeal.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.GeocodingResponse;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.activity.login.SignInActivity;
import com.app.liferdeal.ui.activity.profile.ProfileActivity;
import com.app.liferdeal.ui.activity.restaurant.CusineFilter;
import com.app.liferdeal.ui.activity.searching.SearchAddressGooglePlacesActivity;
import com.app.liferdeal.ui.fragment.restaurant.RestaurantMain;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.GPSTracker;
import com.app.liferdeal.util.PrefsHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationMapFragment extends AppCompatActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "LocationMapFragment";
    //  private LocationMapLayoutBinding mapFragmentBinding;
    private int containerID;
    private GoogleMap googleMap;
    //private MapFragment mapFragment;
    private SupportMapFragment mapFragment;
    Double currentLatitude, currentLongitude;
    private PrefsHelper prefsHelper;
    private static final String ARG_PARAM = "";
    private TextView ed_search, search, tvConfirmLocation;
    private String currentLat, currentLong;
    private ApiInterface apiInterface;
    private Marker secondMarker;
    private ProgressDialog progressDialog;
    private LinearLayout lnr_confirm_location;
    private ImageView img_back;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    PrefsHelper authPreference;
    private LanguageResponse model = new LanguageResponse();

    public LocationMapFragment() {
    }

    public static LocationMapFragment newInstance() {
        LocationMapFragment fragment = new LocationMapFragment();
        return fragment;
    }

   /* public static LocationMapFragment newInstance(String param) {
        LocationMapFragment fragment = new LocationMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map_layout);
        ButterKnife.bind(this);
        intializingView();

        if (App.retrieveLangFromGson(LocationMapFragment.this) != null) {
            model = App.retrieveLangFromGson(LocationMapFragment.this);
        }
        authPreference = new PrefsHelper(this);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.getMenu().findItem(R.id.action_location).setChecked(true);
    }

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // locationMapFragmentBinding = LocationMapFragmentBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.location_map_layout, container, false);
        intializingView(view);
        return view;
    }*/

    private void intializingView() {
        prefsHelper = new PrefsHelper(this);
        //mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        ed_search = findViewById(R.id.search);
        search = findViewById(R.id.search);
        tvConfirmLocation = findViewById(R.id.tvConfirmLocation);
        lnr_confirm_location = findViewById(R.id.lnr_confirm_location);
        img_back = findViewById(R.id.img_back);
        GPSTracker trackerObj = new GPSTracker(this);
        currentLatitude = trackerObj.getLatitude();
        currentLongitude = trackerObj.getLongitude();

        search.setHint(model.getSearchForLocation());
        ed_search.setHint(model.getSearchForLocation());
        tvConfirmLocation.setHint(model.getConfirmLocation());

        ed_search.setOnClickListener(this);
        lnr_confirm_location.setOnClickListener(this);
        img_back.setOnClickListener(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
//                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(LocationMapFragment.this);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        for (int i = 0; i < bottomNavigation.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigation.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }
        switch (item.getItemId()) {
            case R.id.action_location:
//                Intent intent1 = new Intent(LocationMapFragment.this, LocationMapFragment.class);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent1);
//                finish();
                break;
            case R.id.action_home:
                Intent ii = new Intent(LocationMapFragment.this, MainActivity.class);
                ii.putExtra("FROMWHERE", "fromhome");
                ii.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(ii);
                break;
            case R.id.action_profile:
                System.out.println("==== check is login : " + authPreference.isLoggedIn());
                if (authPreference.isLoggedIn()) {
                    Intent intent = new Intent(LocationMapFragment.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else {
                    Intent is = new Intent(LocationMapFragment.this, SignInActivity.class);
                    is.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(is);
                }
                break;
            case R.id.action_filter:
                Intent intent2 = new Intent(LocationMapFragment.this, CusineFilter.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
                break;
        }
        return true;
    }

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(markerOptions);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
               /* Toast.makeText(getApplicationContext(),
                        latLng.latitude + ", " + latLng.longitude,
                        Toast.LENGTH_SHORT).show();*/
                googleMap.clear();
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
                getAddressFromCurrentLatLong(latLng.latitude+"",latLng.longitude+"");
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

//    @Override
//    public void onMapReady(GoogleMap googleMaps) {
//        mMap = googleMaps;
////        googleMap = googleMaps;
////        Geocoder geocoder;
////        List<Address> addresses = null;
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
////
////        // RestaurantModel restaurantModel = new RestaurantModel();
////        try {
////           /* lat = Double.parseDouble(HomeFragment.restaurantModel.getRestaurant_LatitudePoint());
////            longs = Double.parseDouble(HomeFragment.restaurantModel.getRestaurant_LongitudePoint());*/
////
////          /*  lat = Double.parseDouble(latitude);
////            longs = Double.parseDouble(longitude);*/
////
////        } catch (NullPointerException e) {
////            e.printStackTrace();
////
////        }
////
////        System.out.println("===== lat is :" + currentLatitude + " " + "long :" + currentLongitude);
////        LatLng latLong = new LatLng(currentLatitude, currentLongitude);
////        // LatLng currentLatLong = new LatLng(currentLatitude, currentLongitude);
////        // LatLng currentLatLong = new LatLng(28.5463658 ,-82.2084836);
////        geocoder = new Geocoder(this, Locale.getDefault());
//        try {
////            addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
////            //  String address = addresses.get(0).getAddressLine(0);
////            //   System.out.println("===== addresss " + address);
////            //    String city = addresses.get(0).getLocality();
//////            String state = addresses.get(0).getAdminArea();
//////            String country = addresses.get(0).getCountryName();
//////            String postalCode = addresses.get(0).getPostalCode();
////            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
////                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
////                    != PackageManager.PERMISSION_GRANTED) {
////                // TODO: Consider calling
////                //    ActivityCompat#requestPermissions
////                // here to request the missing permissions, and then overriding
////                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                //                                          int[] grantResults)
////                // to handle the case where the user grants the permission. See the documentation
////                // for ActivityCompat#requestPermissions for more details.
////                return;
////            }
////            googleMap.setMyLocationEnabled(true);
////            googleMap.setTrafficEnabled(false);
////            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 15.0f));
////            googleMap.getUiSettings().setCompassEnabled(false);
////            googleMap.getUiSettings().setZoomControlsEnabled(false);
////            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
////            googleMap.getUiSettings().setMapToolbarEnabled(false);
////            googleMap.getUiSettings().setZoomControlsEnabled(false);
////            //googleMaps.addMarker(new MarkerOptions().position(latLong).title((""+getIntent().getStringExtra("resturtantaddress"))));
////            secondMarker = googleMaps.addMarker(new MarkerOptions().position(latLong));//.title((address)));
////            // startNavigation(secondMarker, currentLatLong, latLong );
////
////            //   googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 17));
//
////            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
////
////            //Initialize Google Play Services
////            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                if (ContextCompat.checkSelfPermission(this,
////                        Manifest.permission.ACCESS_FINE_LOCATION)
////                        == PackageManager.PERMISSION_GRANTED) {
////                    //Location Permission already granted
////                    buildGoogleApiClient();
////                    mMap.setMyLocationEnabled(true);
////                } else {
////                    //Request Location Permission
////                    checkLocationPermission();
////                }
////            } else {
////                buildGoogleApiClient();
////                mMap.setMyLocationEnabled(true);
////            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                Intent addressIntent = new Intent(this, SearchAddressGooglePlacesActivity.class);
                addressIntent.putExtra("chooser", getResources().getString(R.string.pickup_location));
                startActivityForResult(addressIntent, 18);
                overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);
                break;

            case R.id.lnr_confirm_location:
                //  initiateRestFragment();
                Intent i = new Intent(LocationMapFragment.this, MainActivity.class);
                i.putExtra("SEARCHADDRESS", mPICKUP_ADDRESS);
                startActivity(i);
                finish();
                break;

            case R.id.img_back:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//                        mMap.setMyLocationEnabled(true);
//                    }
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }

    /**
     * method used to get address from lat long
     */
    @SuppressLint("StaticFieldLeak")
    private class BackgroundGeocodingTaskNew extends AsyncTask<String, Void, String> {
        GeocodingResponse response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pickupLocationAddress.setText(mPICKUP_ADDRESS);
        }

        @Override
        protected String doInBackground(String... params) {

            String url = "https://maps.google.com/maps/api/geocode/json?latlng=" + currentLat + ","
                    + currentLong + "&key=" + prefsHelper.getPref(Constants.google_map_key);
            // + currentLong + "&key=" + Constants.MAPS_DIRECTION;
//                    + currentLongitude + "&sensor=false";

            String stringResponse = CommonMethods.callhttpRequest(url);

            if (stringResponse != null) {
                Gson gson = new Gson();
                response = gson.fromJson(stringResponse, GeocodingResponse.class);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (response != null) {
                if (response.getStatus().equals("OK") && response.getResults() != null) {
                    System.out.println("==== formated address : " + response.getResults().get(0).getFormatted_address());
                    ed_search.setText(response.getResults().get(0).getFormatted_address());
                }
            }

        }
    }

    private String formattedAddress = "", locationName = "", mPICKUP_ADDRESS = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 18) {
            if (resultCode == Activity.RESULT_OK) {
                String latitudeString = data.getStringExtra("LATITUDE_SEARCH");
                String logitudeString = data.getStringExtra("LONGITUDE_SEARCH");
                mPICKUP_ADDRESS = data.getStringExtra("SearchAddress");

                formattedAddress = data.getStringExtra("SearchAddress");
                locationName = data.getStringExtra("ADDRESS_NAME");

                if (mPICKUP_ADDRESS != null) {

                    if (locationName != null && !locationName.isEmpty()) {
                        ed_search.setText(locationName + " " + mPICKUP_ADDRESS);
                    } else {
                        ed_search.setText(mPICKUP_ADDRESS);
                    }
                }
                LatLng latLng = new LatLng(Double.parseDouble(latitudeString), Double.parseDouble(logitudeString));
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                secondMarker = googleMap.addMarker(new MarkerOptions().position(latLng));//.title((address)));

                getAddressFromCurrentLatLong(latitudeString, logitudeString);

                System.out.println("onActivityResult latitudeString...." + latitudeString + "...logitudeString..." + logitudeString);

            }
        }
    }

    private void getAddressFromCurrentLatLong(String latitudeString, String logitudeString) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitudeString), Double.parseDouble(logitudeString), 1);
            String address = addresses.get(0).getAddressLine(0);
            System.out.println("===== addresss " + address);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();

            prefsHelper.savePref(Constants.SAVE_FULL_ADDRESS, address);
            prefsHelper.savePref(Constants.SAVE_CITY_NAME, city);
            prefsHelper.savePref(Constants.SAVE_STATE, state);
            prefsHelper.savePref(Constants.SAVE_COUNTRY, country);
            prefsHelper.savePref(Constants.SAVE_POSTAL_CODE, postalCode);

            //  showProgress();
            //  initiateRestFragment();
            mPICKUP_ADDRESS=address;

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public static String getTAG() {
        return TAG;
    }

    private void initiateRestFragment() {
        //  HomeFragment homeFragment = HomeFragment.newInstance(containerID);
        // LocationMapFragment locationMapFragment = LocationMapFragment.newInstance(containerID);
        RestaurantMain restaurantMain = new RestaurantMain();
        Bundle bundle = new Bundle();
        bundle.putString("SEARCHLOCATIONADDRESS", mPICKUP_ADDRESS);

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, restaurantMain);
        restaurantMain.setArguments(bundle);
        transaction.addToBackStack(restaurantMain.getTag());
        transaction.commit();
        hideProgress();
    }

    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

}

