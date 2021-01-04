package com.app.liferdeal.ui.activity.cart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.activity.restaurant.MyOrderDetailsActivity;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.GPSTracker;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ThankyouPageActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private PrefsHelper prefsHelper;
    private ImageView img_back, img_logo;
    private Double currentLatitude, currentLongitude;
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private Marker secondMarker;
    private TextView txt_subtotal_price, txt_food_discount, txt_inclusive_food_text, txt_time_order, txt_rest_name, txt_order_with_rest_name, txt_pizz_price, txt_pizza_quantity, txt_pizz_name,
            txt_share_food_tracker, txt_order_number, txt_order_date_time, txt_btn_go_to_home, tvThankuTxt, tvThank, tvSubTotal, tvFoodDiscount,tvFoodTax;
    private String strOrderTime, strRestname, strOrderPrice;
    private String restname = "", restTime = "", deliveryDate = "", customeName = "", orderNumber = "", orderType = "", oldprice = "", restLogo = "", currencySymbol = "", pizzaQuantity = "", Pizzaname = "", selectedPizzaItemPrice = "";
    DotToCommaClass dotToCommaClass;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou_page_activity);

        if (App.retrieveLangFromGson(ThankyouPageActivity.this) != null) {
            model = App.retrieveLangFromGson(ThankyouPageActivity.this);
        }

        init();
    }

    private void init() {
        dotToCommaClass = new DotToCommaClass(getApplicationContext());
        prefsHelper = new PrefsHelper(this);
        img_back = findViewById(R.id.img_back);
        tvThankuTxt = findViewById(R.id.tvThankuTxt);
        img_logo = findViewById(R.id.img_logo);
        txt_subtotal_price = findViewById(R.id.txt_subtotal_price);
        txt_food_discount = findViewById(R.id.txt_food_discount);
        txt_inclusive_food_text = findViewById(R.id.txt_inclusive_food_text);
        txt_time_order = findViewById(R.id.txt_time_order);
        txt_rest_name = findViewById(R.id.txt_rest_name);
        txt_order_with_rest_name = findViewById(R.id.txt_order_with_rest_name);
        txt_pizz_price = findViewById(R.id.txt_pizz_price);
        txt_pizza_quantity = findViewById(R.id.txt_pizza_quantity);
        txt_pizz_name = findViewById(R.id.txt_pizz_name);
        txt_share_food_tracker = findViewById(R.id.txt_share_food_tracker);
        txt_order_number = findViewById(R.id.txt_order_number);
        txt_order_date_time = findViewById(R.id.txt_order_date_time);
        txt_btn_go_to_home = findViewById(R.id.txt_btn_go_to_home);
        tvThank = findViewById(R.id.tvThank);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvFoodTax = findViewById(R.id.tvFoodTax);
        tvFoodDiscount = findViewById(R.id.tvFoodDiscount);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String lat = prefsHelper.getPref(Constants.latitude);
        String longi = prefsHelper.getPref(Constants.longitude);
        System.out.println("==== lat : " + lat + " " + "longi : " + longi);

        restname = getIntent().getStringExtra("restname");
        restTime = getIntent().getStringExtra("restTime");
        deliveryDate = getIntent().getStringExtra("deliveryDate");
        customeName = getIntent().getStringExtra("customeName");
        orderNumber = getIntent().getStringExtra("orderNumber");
        orderType = getIntent().getStringExtra("orderType");
        oldprice = getIntent().getStringExtra("oldprice");
        restLogo = getIntent().getStringExtra("strMainRestLogo");
        pizzaQuantity = getIntent().getStringExtra("pizzaQuantity");
        Pizzaname = getIntent().getStringExtra("Pizzaname");
        selectedPizzaItemPrice = getIntent().getStringExtra("selectedPizzaItemPrice");

        tvThankuTxt.setText(model.getThankYouWeReceivedYourOrder());
        txt_share_food_tracker.setText(model.getShareFoodTracker());
        txt_btn_go_to_home.setText(model.getGOTOHOME());
        tvThank.setText(model.getThankYouYourOrderWith());
        tvSubTotal.setText(model.getSubtotal());
        tvFoodTax.setText(model.getFoodTax());
        tvFoodDiscount.setText(model.getFoodDiscount());

        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();

        GPSTracker trackerObj = new GPSTracker(this);
        currentLatitude = trackerObj.getLatitude();
        currentLongitude = trackerObj.getLongitude();
        img_back.setOnClickListener(this);
        txt_share_food_tracker.setOnClickListener(this);
        txt_btn_go_to_home.setOnClickListener(this);

        txt_pizza_quantity.setText(pizzaQuantity);
        txt_pizz_name.setText("x" + Pizzaname);
        txt_time_order.setText(restTime);
        txt_rest_name.setText(restname);
        txt_order_number.setText(model.getOrderNumber() + " # " + " " + orderNumber);
        txt_order_date_time.setText(model.getOrderText() + " " + deliveryDate + " " + restTime);
        txt_order_with_rest_name.setText(restname);
        txt_pizz_price.setText(dotToCommaClass.changeDot(selectedPizzaItemPrice));
        txt_subtotal_price.setText(dotToCommaClass.changeDot(currencySymbol + oldprice));
        txt_food_discount.setText(dotToCommaClass.changeDot(currencySymbol + oldprice));
        txt_inclusive_food_text.setText(dotToCommaClass.changeDot(currencySymbol + oldprice));
        Glide.with(this).load(Uri.parse(restLogo)).into(img_logo);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.txt_share_food_tracker:
                Intent ii = new Intent(ThankyouPageActivity.this, MyOrderDetailsActivity.class);
                ii.putExtra("orderid", orderNumber);
                startActivity(ii);
                // shareWithChooser();
                break;

            case R.id.txt_btn_go_to_home:
                Intent i = new Intent(ThankyouPageActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;

            default:
                break;
        }
    }

    private void shareWithChooser() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody = "Order Number : '" + orderNumber + "' ";
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        // intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        /*Fire!*/
        startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
    }

    @Override
    public void onMapReady(GoogleMap googleMaps) {
        googleMap = googleMaps;
        Geocoder geocoder;
        List<Address> addresses = null;
        googleMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        try {


        } catch (NullPointerException e) {
            e.printStackTrace();

        }

        System.out.println("===== lat is :" + currentLatitude + " " + "long :" + currentLongitude);
        LatLng latLong = new LatLng(currentLatitude, currentLongitude);
        // LatLng currentLatLong = new LatLng(currentLatitude, currentLongitude);
        // LatLng currentLatLong = new LatLng(28.5463658 ,-82.2084836);
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
            //  String address = addresses.get(0).getAddressLine(0);
            //   System.out.println("===== addresss " + address);
            //    String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(false);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 15.0f));
            googleMap.getUiSettings().setCompassEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            //googleMaps.addMarker(new MarkerOptions().position(latLong).title((""+getIntent().getStringExtra("resturtantaddress"))));
            secondMarker = googleMaps.addMarker(new MarkerOptions().position(latLong));//.title((address)));
            // startNavigation(secondMarker, currentLatLong, latLong );

            //   googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 17));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
