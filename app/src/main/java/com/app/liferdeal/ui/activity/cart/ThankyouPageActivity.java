package com.app.liferdeal.ui.activity.cart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.app.liferdeal.R;
import com.app.liferdeal.adapter.DetailOrderAdapter;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.Order;
import com.app.liferdeal.model.OrderFoodItem;
import com.app.liferdeal.model.menuitems.ComItemList;
import com.app.liferdeal.model.restaurant.RaviCartModle;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.activity.restaurant.AddExtraActivity;
import com.app.liferdeal.ui.activity.restaurant.MyOrderDetailsActivity;
import com.app.liferdeal.ui.activity.restaurant.RestaurantDetails;
import com.app.liferdeal.ui.adapters.ComThakAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.DroidPrefs;
import com.app.liferdeal.util.GPSTracker;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private ImageView imgViewLogo;
    private TextView txtTotal, txtPackagingFee, txtDeliveryFee, txtVat, txtSalexTax, tvTotal, txtServiceFee, tvClose, tvOrderNoText, tvOrderNumber, tvPlaceAnew, txt_subtotal_price, txt_food_discount, txt_inclusive_food_text, txt_time_order, txt_rest_name, txt_order_with_rest_name, txt_pizz_price, txt_pizza_quantity, txt_pizz_name,
            txt_share_food_tracker, txt_order_number, txt_order_date_time, txt_btn_go_to_home, tvThankuTxt, tvThank, tvSubTotal, tvFoodDiscount, tvFoodTax;
    private String strOrderTime, strRestname, strOrderPrice;
    private String restname = "", restTime = "", deliveryDate = "", customeName = "", orderNumber = "", orderType = "", oldprice = "", restLogo = "", currencySymbol = "", pizzaQuantity = "", Pizzaname = "", selectedPizzaItemPrice = "", rest_address="";
    DotToCommaClass dotToCommaClass;
    private LanguageResponse model = new LanguageResponse();
    double exact_offer=0.0;


    @BindView(R.id.rvSubtotal)
    RelativeLayout rvSubtotal;
    @BindView(R.id.rvFoodDiscount)
    RelativeLayout rvFoodDiscount;
    @BindView(R.id.rvServiceFees)
    RelativeLayout rvServiceFees;
    @BindView(R.id.rvSalesTax)
    RelativeLayout rvSalesTax;
    @BindView(R.id.rvVat)
    RelativeLayout rvVat;
    @BindView(R.id.rvDeliveryFees)
    RelativeLayout rvDeliveryFees;

    @BindView(R.id.rvPackagingFees)
    RelativeLayout rvPackagingFees;
    @BindView(R.id.rvTotal)
    RelativeLayout rvTotal;

    @BindView(R.id.viewSubtotal)
    View viewSubtotal;
    @BindView(R.id.viewServiceFees)
    View viewServiceFees;
    @BindView(R.id.viewSalesTax)
    View viewSalesTax;
    @BindView(R.id.viewVat)
    View viewVat;
    @BindView(R.id.viewDeliveryFees)
    View viewDeliveryFees;
    @BindView(R.id.viewPackagingFees)
    View viewPackagingFees;
    @BindView(R.id.tvDeliveryFee)
    TextView tvDeliveryFee;
    @BindView(R.id.tvSizeOf)
    TextView tvSizeOf;


    @BindView(R.id.rlForgot)
    RelativeLayout rlForgot;
    String extra_toppings="";
    @BindView(R.id.recyler_details)
    RecyclerView recyler_details;
    @BindView(R.id.recyler_com_details)
    RecyclerView recyler_com_details;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou_page_activity);
        ButterKnife.bind(this);

        if (App.retrieveLangFromGson(ThankyouPageActivity.this) != null) {
            model = App.retrieveLangFromGson(ThankyouPageActivity.this);
        }

        Database database = new Database(ThankyouPageActivity.this);
        database.delete();

        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR

            AddExtraActivity.cart_Item_number = 0;
            Database database = new Database(getApplicationContext());
            database.delete();
            DroidPrefs.getDefaultInstance(this).clearkey("com_list");
            Intent i = new Intent(ThankyouPageActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private SharedPreferencesData sharedPreferencesData;
    private String subtotalPr, fooddiscountPr, servicefeesPr, salestaxPr, vatPr, deliveryFeesPr, packagingfeespr, totalPricePr, sizeOfPizzaPr;

    private void init() {
        sharedPreferencesData = new SharedPreferencesData(getApplicationContext());
        if (sharedPreferencesData != null) {
            subtotalPr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.SUBTOTAL);
            fooddiscountPr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.FOODDISCOUNT);
            servicefeesPr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.SERVICEFEES);
            salestaxPr = salestaxPr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.SALESTAX);
            vatPr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.VAT);
            deliveryFeesPr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.DELIVERYFEES);
            packagingfeespr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.PACKAGINGFEES);
            totalPricePr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.TOTALPR);
            sizeOfPizzaPr = sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.SIZEOFPIZZA);
        }


        //Log.e("subTotal=",subtotalPr+"=="+fooddiscountPr+"=="+servicefeesPr+"=="+salestaxPr+"=="+vatPr+"=="+deliveryFeesPr+"=="+packagingfeespr+"=="+totalPricePr);


        dotToCommaClass = new DotToCommaClass(getApplicationContext());
        prefsHelper = new PrefsHelper(this);
        imgViewLogo = findViewById(R.id.imgViewLogo);


        txtTotal = findViewById(R.id.txtTotal);
        txtPackagingFee = findViewById(R.id.txtPackagingFee);
        txtDeliveryFee = findViewById(R.id.txtDeliveryFee);
        txtVat = findViewById(R.id.txtVat);
        txtSalexTax = findViewById(R.id.txtSalexTax);
        tvTotal = findViewById(R.id.tvTotal);
        txtServiceFee = findViewById(R.id.txtServiceFee);
        tvClose = findViewById(R.id.tvClose);
        tvOrderNoText = findViewById(R.id.tvOrderNoText);
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvPlaceAnew = findViewById(R.id.tvPlaceAnew);
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
        exact_offer=getIntent().getDoubleExtra("coupon_price",0.0);
        if(exact_offer>0.0){

            rvFoodDiscount.setVisibility(View.VISIBLE);
            viewServiceFees.setVisibility(View.VISIBLE);
            txt_food_discount.setText("-" + currencySymbol + dotToCommaClass.changeDot(String.format("%.2f", exact_offer)));

        }
        else{
            rvFoodDiscount.setVisibility(View.GONE);
            viewServiceFees.setVisibility(View.GONE);
        }
        Log.i("reason",restLogo);
        //restLogo = prefsHelper.getPref(Constants.APP_LOGO);
        pizzaQuantity = getIntent().getStringExtra("pizzaQuantity");
        Pizzaname = getIntent().getStringExtra("Pizzaname");
        selectedPizzaItemPrice = getIntent().getStringExtra("selectedPizzaItemPrice");
        rest_address=getIntent().getStringExtra("rest_address");
        extra_toppings=getIntent().getStringExtra("extra_toppings");
        tvDeliveryFee.setText(model.getDeliveryCost());
        List<OrderFoodItem> orderFoodItems=new ArrayList<>();
        if(Pizzaname.contains(",")){
            String[] pizzas=Pizzaname.split(",");
            String[] toppings=extra_toppings.split("_");
            for(int j=0;j<pizzas.length;j++){
                OrderFoodItem orderFoodItem=new OrderFoodItem();
                orderFoodItem.setItemsName(pizzas[j]);
                if(toppings.length>0) {
                    orderFoodItem.setExtraTopping(toppings[j]);
                }
                orderFoodItems.add(orderFoodItem);
            }
        }
        else{

            OrderFoodItem orderFoodItem=new OrderFoodItem();
            orderFoodItem.setItemsName(Pizzaname);
            if(extra_toppings!=null) {
                orderFoodItem.setExtraTopping(extra_toppings);
            }
            orderFoodItems.add(orderFoodItem);
        }
        if(orderFoodItems.size()>0){
            setThankyouAdapter(orderFoodItems);
        }
        setComboAdapter();



        tvThankuTxt.setText(model.getThankYouWeReceivedYourOrder());
        String share_food=model.getShareFoodTracker();
        Log.i("reason",share_food);
        txt_share_food_tracker.setText(share_food.trim());
        txt_btn_go_to_home.setText(model.getForgotSomething());
        tvPlaceAnew.setText(model.getPlaceANewOrder());

        tvThank.setText(model.getThankYouYourOrderWith().trim());
        tvSubTotal.setText(model.getSubtotal());
        tvFoodTax.setText(model.getFoodTax());
        tvFoodDiscount.setText(model.getFoodDiscount());
        tvOrderNumber.setText(model.getOrderNumber());
        //tvOrderNumber.setText(model.getOrderNumber());
        tvClose.setText(model.getClose());
        tvTotal.setText(model.getTotal());

        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();

        GPSTracker trackerObj = new GPSTracker(this);
        currentLatitude = trackerObj.getLatitude();
        currentLongitude = trackerObj.getLongitude();
        img_back.setOnClickListener(this);
        txt_share_food_tracker.setOnClickListener(this);
        txt_btn_go_to_home.setOnClickListener(this);

        tvOrderNoText.setText(orderNumber);
        txt_pizza_quantity.setText(pizzaQuantity);
        txt_pizz_name.setText(Pizzaname);
        txt_time_order.setText(restTime);
        txt_rest_name.setText(restname);
        txt_order_number.setText(model.getOrderNumber() + " # " + " " + orderNumber);
        txt_order_date_time.setText(model.getOrderText() + " " + deliveryDate + " " + restTime);
        txt_order_with_rest_name.setText(restname.trim());
        double price= Double.parseDouble(selectedPizzaItemPrice);
        txt_pizz_price.setText(currencySymbol+dotToCommaClass.changeDot(String.format("%.2f",price)));
        txt_subtotal_price.setText(currencySymbol+dotToCommaClass.changeDot(String.format("%.2f",Double.parseDouble(oldprice))));
        txt_inclusive_food_text.setText(currencySymbol+dotToCommaClass.changeDot( String.format("%.2f",Double.parseDouble(oldprice))));
        Glide.with(this).load(Uri.parse(restLogo)).into(img_logo);
        Glide.with(this).load(Uri.parse(prefsHelper.getPref(Constants.APP_LOGO))).into(imgViewLogo);


        if (subtotalPr.equalsIgnoreCase("0.0") || subtotalPr.equalsIgnoreCase("0")) {
            rvSubtotal.setVisibility(View.GONE);
            viewSubtotal.setVisibility(View.GONE);
        }

        if (servicefeesPr.equalsIgnoreCase("0.0") || servicefeesPr.equalsIgnoreCase("0")) {
            rvServiceFees.setVisibility(View.GONE);
            viewServiceFees.setVisibility(View.GONE);
        }
        if (salestaxPr.equalsIgnoreCase("0.0") || salestaxPr.equalsIgnoreCase("0")) {
            rvSalesTax.setVisibility(View.GONE);
            viewSalesTax.setVisibility(View.GONE);
        }
        if (vatPr.equalsIgnoreCase("0.0") || vatPr.equalsIgnoreCase("0")) {
            rvVat.setVisibility(View.GONE);
            viewVat.setVisibility(View.GONE);
        }
        if (deliveryFeesPr.equalsIgnoreCase("0.0") || deliveryFeesPr.equalsIgnoreCase("0")) {
            rvDeliveryFees.setVisibility(View.GONE);
            viewDeliveryFees.setVisibility(View.GONE);
        }
        if (packagingfeespr.equalsIgnoreCase("0.0") || packagingfeespr.equalsIgnoreCase("0")) {
            rvPackagingFees.setVisibility(View.GONE);
            viewPackagingFees.setVisibility(View.GONE);
        }
        if (totalPricePr.equalsIgnoreCase("0.0") || totalPricePr.equalsIgnoreCase("0")) {
            rvTotal.setVisibility(View.GONE);
        }
        tvSizeOf.setVisibility(View.VISIBLE);
        tvSizeOf.setText(rest_address);
        txt_subtotal_price.setText(currencySymbol+dotToCommaClass.changeDot(String.format("%.2f",Double.parseDouble(subtotalPr)) ));

        txtServiceFee.setText(dotToCommaClass.changeDot(currencySymbol + servicefeesPr));
        txtSalexTax.setText(dotToCommaClass.changeDot(currencySymbol + salestaxPr));
        txtVat.setText(dotToCommaClass.changeDot(currencySymbol + vatPr));
        txtDeliveryFee.setText(dotToCommaClass.changeDot(currencySymbol + deliveryFeesPr));
        txtPackagingFee.setText(dotToCommaClass.changeDot(currencySymbol + packagingfeespr));
        double order_price= Double.parseDouble(totalPricePr);
        txtTotal.setText(currencySymbol+dotToCommaClass.changeDot( String.format("%.2f", order_price)));
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExtraActivity.cart_Item_number = 0;
                Database database = new Database(getApplicationContext());
                database.delete();
                Intent i = new Intent(ThankyouPageActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        rlForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExtraActivity.cart_Item_number = 0;
                Database database = new Database(getApplicationContext());
                database.delete();
                Intent i = new Intent(ThankyouPageActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
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
    public void setThankyouAdapter(List<OrderFoodItem> list){
        recyler_details.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DetailOrderAdapter adapterCategory = new DetailOrderAdapter(this, list);
        recyler_details.setAdapter(adapterCategory);
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
    private void setComboAdapter(){
        String com_list=DroidPrefs.get(this,"com_list",String.class);
        if(com_list!=null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ComItemList>>() {
            }.getType();
            List<ComItemList> comItemLists = gson.fromJson(com_list, listType);
            if(comItemLists!=null){
                ArrayList<RaviCartModle> raviCartModles=new ArrayList<>();
                for(ComItemList comItemList:comItemLists){
                    raviCartModles.add(new RaviCartModle(comItemList.combo_name,comItemList.combo_name,comItemList.sec_value,comItemList.com_sec,"",comItemList.combo_top,comItemList.price,String.valueOf(comItemList.quantity),comItemList.combo_desc,1));
                }
                if(raviCartModles.size()>0){
                   LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
                    ComThakAdapter comThakAdapter=new ComThakAdapter(this,raviCartModles);
                    recyler_com_details.setAdapter(comThakAdapter);
                    recyler_com_details.setLayoutManager(linearLayoutManager);
                    DroidPrefs.getDefaultInstance(this).clearkey("com_list");
                }
            }
        }
    }
}
