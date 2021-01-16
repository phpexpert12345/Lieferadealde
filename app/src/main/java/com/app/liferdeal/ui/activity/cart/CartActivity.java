package com.app.liferdeal.ui.activity.cart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.CouponApplied;
import com.app.liferdeal.model.GetRestaurantDiscountResponse;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.LoyaltyApplied;
import com.app.liferdeal.model.RmGetLoyaltyPointResponse;
import com.app.liferdeal.model.RmVerifyCouponCodeResponse;
import com.app.liferdeal.model.RmVerifyLoyaltyPointResponse;
import com.app.liferdeal.model.restaurant.RaviCartModle;
import com.app.liferdeal.model.restaurant.ShippingCartModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.activity.restaurant.AddExtraActivity;
import com.app.liferdeal.ui.activity.restaurant.RestaurantDetails;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.DroidPrefs;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tvServiceFeeText)
    TextView tvServiceFeeText;
    @BindView(R.id.tvServiceFee)
    TextView tvServiceFee;
    @BindView(R.id.tvVatText)
    TextView tvVatText;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvVat)
    TextView tvVat;
    @BindView(R.id.tvPackagingText)
    TextView tvPackagingText;
    @BindView(R.id.tvPackagingFee)
    TextView tvPackagingFee;
    @BindView(R.id.llFull)
    LinearLayout llFull;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.img_delivery)
    ImageView img_delivery;
    @BindView(R.id.img_pickup)
    ImageView  img_pickup;
    private PrefsHelper prefsHelper;
    Database database;
    ArrayList<RaviCartModle> raviCartModles;
    public Double totalPrice = 0.0, disPrice = 0.0, subTot = 0.0, minOrder = 0.0, grandTotal = 0.0, coupon_price = 0.0, sales_tax = 0.0, loyalty_price = 0.0,
            deliveryCost = 0.0, packagingCost = 0.0;
    private LinearLayout rl_pre_order_delivery_time, rl_pre_order_collection_time;
    private LinearLayoutManager linearLayoutManager, linearLayoutManager1;
    private TextView tv_sipping, tv_sub_total, tv_food_item_total;
    private RecyclerView list_view_items;
    private ImageView img_select_picup, img_select_delivery;
    public String orderType = "", RestId = "", postalCode = "",rest_address="";
    private LinearLayout rl_pickup, rlDineIn, rl_delivery;
    private RelativeLayout card_checkout;
    private TextView tv_total, tv_count, checkout_total_price, tvDeliveryFee, tvTotalItem, tvDeliveryText, tvServiceChargeText, tvServiceCharge, tvDelivery,
            tvCart, tvOrderType, tvPickup, tvEatIn;
    private ImageView img_back;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private String currencySymbol = "", strMainRestName = "", strMainRestLogo = "", pizzaItemid = "";
    private String payment_transaction_paypal = "", quantity, deliveryChargeValue = "", SeviceFeesValue = "", ServiceFees = "", ServiceFeesType = "", PackageFeesType = "", PackageFees = "", PackageFeesValue = "", SalesTaxAmount = "", VatTax = "";
    private String strsizeid, extraItemID, CustomerId, CustomerAddressId, payment_type, order_price, subTotalAmount, delivery_date, delivery_time, instructions,
            CouponCode, CouponCodePrice, couponCodeType, order_type, SpecialInstruction, extraTipAddAmount, RestaurantNameEstimate,
            discountOfferPercentage, discountOfferDescription, discountOfferPrice, RestaurantoffrType, discountFoodItems,
            PaymentProcessingFees, deliveryChargeValueType, WebsiteCodePrice, WebsiteCodeType, WebsiteCodeNo, preorderTime,
            GiftCardPay, WalletPay, loyptamount, table_number_assign, customer_country, group_member_id, loyltPnts, branch_id, FoodCosts,
            getTotalItemDiscount, getFoodTaxTotal7, getFoodTaxTotal19, TotalSavedDiscount, discountOfferFreeItems, number_of_people, customer_allow_register, address,
            street, floor_no, zipcode, phone, email, name_customer, city, mealID, mealquqntity, mealPrice, mealItemExtra, mealItemOption;

    public static CartActivity mInstance;
    private double discount=0, service_fee=0, service_vat=0;
    @BindView(R.id.tv_save_discount_amount_msg)
    TextView tv_save_discount_amount_msg;
    @BindView(R.id.tvLoyalty)
    TextView tvLoyalty;
    @BindView(R.id.tvBillDetail)
    TextView tvBillDetail;
    @BindView(R.id.tcSubTotal)
    TextView tcSubTotal;
    @BindView(R.id.tvFoodItems)
    TextView tvFoodItems;
    @BindView(R.id.tvCheckout)
    TextView tvCheckout;
    private String redeemPointsTotal = "";
    @BindView(R.id.tvLoyaliteyPoints)
    TextView tvLoyaliteyPoints;
    @BindView(R.id.tvFoodDiscount)
    TextView tvFoodDiscount;
    @BindView(R.id.tv_food_discount_total)
    TextView tv_food_discount_total;
    @BindView(R.id.cardViewLoyality)
    CardView cardViewLoyality;
    @BindView(R.id.tvLoyaltyText)
    TextView tvLoyaltyText;
    @BindView(R.id.tvLoyaltyFee)
    TextView tvLoyaltyFee;
    @BindView(R.id.card_coupon)
    CardView card_coupon;
    @BindView(R.id.llLoyalityPoint)
            LinearLayout llLoyalityPoint;
    SharedPreferencesData sharedPreferencesData;
    private LanguageResponse model = new LanguageResponse();
    private DotToCommaClass dotToCommaClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(CartActivity.this) != null) {
            model = App.retrieveLangFromGson(CartActivity.this);
        }

        init();
    }

    private void init() {
        dotToCommaClass = new DotToCommaClass(getApplicationContext());
        sharedPreferencesData = new SharedPreferencesData(getApplicationContext());
        prefsHelper = new PrefsHelper(this);
        progressDialog = new ProgressDialog(this);
        mInstance = this;
        database = new Database(CartActivity.this);
        raviCartModles = new ArrayList<>();
        list_view_items = findViewById(R.id.list_view_items);
        img_back = findViewById(R.id.img_back);
        rl_pickup = findViewById(R.id.rl_pickup);
        rl_delivery = findViewById(R.id.rl_delivery);
        rlDineIn = findViewById(R.id.rlDineIn);
        card_checkout = findViewById(R.id.card_checkout);
        checkout_total_price = findViewById(R.id.checkout_total_price);
        tvServiceChargeText = findViewById(R.id.tvServiceChargeText);
        tvServiceCharge = findViewById(R.id.tvServiceCharge);
        tvCart = findViewById(R.id.tvCart);
        tvOrderType = findViewById(R.id.tvOrderType);
        tvDelivery = findViewById(R.id.tvDelivery);
        tvPickup = findViewById(R.id.tvPickup);
        tvEatIn = findViewById(R.id.tvEatIn);
        tvLoyalty = findViewById(R.id.tvLoyalty);
        // img_select_delivery = findViewById(R.id.img_select_delivery);
        // img_select_picup = findViewById(R.id.img_select_picup);
        tv_sub_total = findViewById(R.id.tv_sub_total);
        tvTotalItem = findViewById(R.id.tvTotalItem);
        tvCheckout = findViewById(R.id.tvCheckout);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvDeliveryText = findViewById(R.id.tvDeliveryText);
        tv_total = findViewById(R.id.tv_total);
        tv_food_item_total = findViewById(R.id.tv_food_item_total);
        // tv_count = findViewById(R.id.tv_count);
        RestId = getIntent().getStringExtra("RESTID");
        //Log.e("REST", RestId);
        strMainRestName = getIntent().getStringExtra("RESTName");
        strMainRestLogo = getIntent().getStringExtra("RESTLOGO");

        pizzaItemid = getIntent().getStringExtra("subPizzaItemId");
        strsizeid = getIntent().getStringExtra("SIZEITEMID");
        extraItemID = getIntent().getStringExtra("globTempExtraItemidWithSizeIdd");
        rest_address=getIntent().getStringExtra("rest_address");

        System.out.println("==== rest id in cart activity : " + RestId);
        postalCode = prefsHelper.getPref(Constants.SAVE_POSTAL_CODE);
        if (prefsHelper.isLoggedIn()) {
            CustomerId = prefsHelper.getPref(Constants.CUSTOMER_ID);
            CustomerAddressId = prefsHelper.getPref(Constants.CUSTOMER_ADDRESS_ID);
        }

        tvCart.setText(model.getCart());
        tvOrderType.setText(model.getOrderType());
        tvDelivery.setText(model.getDelivery());
        tvPickup.setText(model.getPickup());
        tvEatIn.setText(model.getEATIN());
        tv_save_discount_amount_msg.setText(model.getApplyCoupon());
        tvLoyalty.setText(model.getRedeemLoyaltyPoints());
        tvBillDetail.setText(model.getBillDetails());
        tcSubTotal.setText(model.getSubtotal());
        tvFoodItems.setText(model.getFoodDiscount());
        tvServiceFeeText.setText(model.getServiceFees());
        tvServiceChargeText.setText(model.getSaleTax());
        tvFoodDiscount.setText(model.getCouponDiscount());
        tvVatText.setText(model.getVatTax());
        tvDeliveryText.setText(model.getDeliveryCharge());
        tvPackagingText.setText(model.getPackageFees());
        tvTotal.setText(model.getTotal());
        tvCheckout.setText(model.getCheckout());

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        delivery_date = formattedDate;
        System.out.println("=== delivery_date date : " + delivery_date);

        img_back.setOnClickListener(this);
        rl_pickup.setOnClickListener(this);
        rl_delivery.setOnClickListener(this);
        rlDineIn.setOnClickListener(this);
        card_checkout.setOnClickListener(this);
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
//        orderType = "Delivery";

        if (prefsHelper.isLoggedIn()) {
            LoyaltyApplied loyaltyApplied=DroidPrefs.get(this,"loyalty_applied",LoyaltyApplied.class);
            if(loyaltyApplied!=null){
                if(loyaltyApplied.is_loyalty_applied){
                    String price=loyaltyApplied.loyalty_points;

                    if(price!=null){
                        loyalty_price=Double.parseDouble(price);
                        tvLoyaltyFee.setVisibility(View.VISIBLE);
                        tvLoyaltyText.setVisibility(View.VISIBLE);
                        tvLoyaliteyPoints.setText(model.getLoyaltyPoints());
                        tvLoyaliteyPoints.setVisibility(View.VISIBLE);
                        tvLoyaltyFee.setText("-" + currencySymbol + dotToCommaClass.changeDot(price));
                        updateData();

                    }
                    cardViewLoyality.setVisibility(View.GONE);
                }
                else{
                    showProgress();
                    getLoyalityPoitsAll();
                    cardViewLoyality.setVisibility(View.VISIBLE);
                }
            }
            else{
                showProgress();
                getLoyalityPoitsAll();
                cardViewLoyality.setVisibility(View.VISIBLE);;
            }



        } else {
            cardViewLoyality.setVisibility(View.GONE);
        }
        CouponApplied couponApplied=DroidPrefs.get(this,"coupon_applied",CouponApplied.class);
        if(couponApplied!=null){
            if(couponApplied.is_coupon_applied){
                String price=couponApplied.coupon_value;

                if(price!=null){
                    coupon_price=Double.parseDouble(price);
                    tvFoodDiscount.setVisibility(View.VISIBLE);
                    tv_food_discount_total.setVisibility(View.VISIBLE);

                    tv_food_discount_total.setText("-" + currencySymbol + dotToCommaClass.changeDot(price));
                    updateData();

                }
                card_coupon.setVisibility(View.GONE);

            }
            else {
                card_coupon.setVisibility(View.VISIBLE);
            }
        }
        else {
            card_coupon.setVisibility(View.VISIBLE);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.HMDLVRYAVAIL).equalsIgnoreCase("yes")) {
            rl_delivery.setVisibility(View.VISIBLE);
            orderType = "Delivery";

           changeSelect(orderType);
        } else {
            rl_delivery.setVisibility(View.GONE);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.PCKAVAILABLE).equalsIgnoreCase("yes")) {
            rl_pickup.setVisibility(View.VISIBLE);
        } else {
            rl_pickup.setVisibility(View.GONE);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.DINAVAILABLE).equalsIgnoreCase("yes")) {
            rlDineIn.setVisibility(View.VISIBLE);
        } else {
            rlDineIn.setVisibility(View.GONE);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.HMDLVRYAVAIL).equalsIgnoreCase("no") &&
                sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.PCKAVAILABLE).equalsIgnoreCase("yes")) {
            orderType = "Pickup";


            changeSelect(orderType);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.HMDLVRYAVAIL).equalsIgnoreCase("no") &&
                sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.PCKAVAILABLE).equalsIgnoreCase("no") &&
                sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.DINAVAILABLE).equalsIgnoreCase("yes")) {
            orderType = "EAT-IN";

           changeSelect(orderType);
        }

        updateCart();
        getDiscount();
        getShippingChargeData();
        // Log.e("extraItemID=",raviCartModles.get(0).getSize_item_name());
    }

    @Override
    protected void onStop() {
        super.onStop();
//        database.deal_delete();
        updateCart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateCart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateCart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCart();
    }

    private String extraItemNameG = "";
    private String extraItemIDG = "";
    private String selectedPizzaName = "";
    private String selectedPizzaItemPrice = "";

    private void updateCart() {
//        try {
        //made by me
        totalPrice = 0.0;
        raviCartModles.clear();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM item_table", null);
        //  Cursor cursor1 = db.rawQuery("SELECT * FROM deal_item_table", null);
        if (cursor.moveToFirst()) {
            if (cursor.moveToFirst()) {
                do {
                    String item_id = cursor.getString(0);
                    String item_name = cursor.getString(1);
                    String size_item_id = cursor.getString(2);
                    String size_item_name = cursor.getString(3);
                    String extra_item_id = cursor.getString(4);
                    String extra_item_name = cursor.getString(5);
                    String price = cursor.getString(6);
                    String subcatdetals = cursor.getString(8);
                    totalPrice = totalPrice + Double.parseDouble(price);
                    totalPrice = (double) Math.round(totalPrice * 100) / 100;
                    String item_quantity = cursor.getString(7);
                    extraItemNameG = extra_item_name;
                    extraItemIDG = extra_item_id;
                    quantity = item_quantity;
                    System.out.println("==== extraname : " + extraItemNameG);
                    System.out.println("==== extraname : " + extraItemIDG);
//                        tv_total.setText(currencySymbol + "" + "" + String.format("%.2f", totalPrice));
//                        checkout_total_price.setText(currencySymbol + "" + "" + String.format("%.2f", totalPrice));

                    grandTotal = totalPrice - discount - coupon_price - loyalty_price + service_fee + service_vat + sales_tax + deliveryCost + packagingCost;
                    tv_total.setText(currencySymbol + "" + "" + dotToCommaClass.changeDot(String.format("%.2f", grandTotal)));
                    checkout_total_price.setText(currencySymbol + "" + "" + dotToCommaClass.changeDot(String.format("%.2f", grandTotal)));
                    order_price = String.valueOf(totalPrice);
                    subTotalAmount = String.valueOf(totalPrice);
                    tv_sub_total.setText(currencySymbol + "" + "" + dotToCommaClass.changeDot(String.format("%.2f", totalPrice)));
//                        tv_food_item_total.setText(currencySymbol + "" + "" + String.format("%.2f", totalPrice));
                    FoodCosts = tv_food_item_total.getText().toString();

//                        if (deliveryCharge != null) {
//                            tvDeliveryFee.setText(currencySymbol + "" + "" + String.format("%.2f", deliveryCharge));
//                        }

                    if (totalPrice == 0) {
                        llFull.setVisibility(View.VISIBLE);
                    } else {
                        llFull.setVisibility(View.GONE);
                    }
                    raviCartModles.add(new RaviCartModle(item_id, item_name, size_item_id, size_item_name, extra_item_id, extra_item_name, price, item_quantity, subcatdetals));
                    AddExtraActivity.cart_Item_number = raviCartModles.size();
                } while (cursor.moveToNext());
                //  tvTotalFoodCost.setText("+".concat(pound.concat("" + String.format("%.2f", totalPrice))));
                //    tvTotalFoodCost.setText("+".concat(pound.concat("" +String.valueOf(totalPrice))));
                // getDiscount();
                tvTotalItem.setText(model.getTotal() + " " + raviCartModles.size() + " " + model.getItems());
                if(raviCartModles.size()>0){
                    double toatl_price=0.0;
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int j=0;j<raviCartModles.size();j++){
                        toatl_price+=Double.parseDouble(raviCartModles.get(j).getPrice());
                        String size_item=raviCartModles.get(j).getSize_item_name();
                        if(!size_item.equalsIgnoreCase("0")) {
                            stringBuilder.append(raviCartModles.get(j).getItem_quantity() + "x" + raviCartModles.get(j).getItem_name() + "(" + size_item + ")");

                        }
                        else{
                            stringBuilder.append(raviCartModles.get(j).getItem_quantity() + "x" + raviCartModles.get(j).getItem_name());

                        }
                        stringBuilder.append("\n");


                    }
                    if(toatl_price>0.0){
                        selectedPizzaItemPrice= String.valueOf(toatl_price);
                        subTotalAmount= String.valueOf(toatl_price);
                        FoodCosts=subTotalAmount;
                    }
                    if(stringBuilder.length()>0){
                        selectedPizzaName=stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n")).toString();
                    }


                }
                CartAdapterravi cartAdapterravi = new CartAdapterravi(CartActivity.this, raviCartModles);
                linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
                list_view_items.setLayoutManager(linearLayoutManager);
                list_view_items.setAdapter(cartAdapterravi);
            } else {
                list_view_items.setVisibility(View.GONE);
            }
        } else {
            db.close();
          /*      Ravifinalitem.cart_Item_number = 0;
                Intent iii = new Intent(CartActivity.this, EmptyCartActivity.class);
                iii.putExtra("restaurantAddress", restaurantAddress);
                iii.putExtra("restaurantName", restaurantName);
                iii.putExtra("id", id);
                iii.putExtra("restaurantCategoryId", restaurantCategoryId);
                startActivity(iii);
                finish();*/
        }

        //TODO Add empty cart
        if (raviCartModles.size() == 0) {
            nsv.setVisibility(View.GONE);
            card_checkout.setVisibility(View.GONE);
            llFull.setVisibility(View.VISIBLE);
            LayoutInflater inflater = LayoutInflater.from(this);
            final View view = inflater.inflate(R.layout.activity_empty_cart, (ViewGroup) llFull, false);
            AppCompatTextView tvWhoops = view.findViewById(R.id.tvWhoops);
            AppCompatTextView tvEmptyCart = view.findViewById(R.id.tvEmptyCart);
            AppCompatTextView tvLooks = view.findViewById(R.id.tvLooks);

            tvWhoops.setText(model.getWOOPS());
            tvEmptyCart.setText(model.getYOURCARTISEMPTY());
            tvLooks.setText(model.getLOOKLIKEYOUHAVE());
            llFull.addView(view);
            DroidPrefs.getDefaultInstance(this).clearkey("coupon_applied");
            DroidPrefs.getDefaultInstance(this).clearkey("loyalty_applied");
//            Toast.makeText(mInstance, "Empty", Toast.LENGTH_SHORT).show();
        } else {
            if (llFull.getVisibility() == View.VISIBLE) {
                llFull.removeAllViews();
                llFull.setVisibility(View.GONE);
            }
//            Toast.makeText(mInstance, "Not Empty", Toast.LENGTH_SHORT).show();
        }

//        } catch (Exception e) {
//
//            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
//
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_pickup:
                changeSelect("Pickup");
                orderType = "Pickup";
                getDiscount();
                getShippingChargeData();
                //  addQuatity.insert("1","sufiyan");
//                serviceChargedForDeliveryAndPickup();
                break;
            case R.id.rl_delivery:
                changeSelect("Delivery");
                orderType = "Delivery";
                getDiscount();
                getShippingChargeData();
                // addQuatity.updateItem("1","name.....");
//                serviceChargedForDeliveryAndPickup();
                break;

            case R.id.rlDineIn:
                changeSelect("EAT-IN");
                orderType = "EAT-IN";
                getDiscount();
                getShippingChargeData();
//                serviceChargedForDeliveryAndPickup();
                // addQuatity.updateItem("1","name.....");
                break;
            case R.id.card_checkout:
                if (!orderType.equals("")) {
                    //  activity.setCurrent_fragment(new FragmentAddressList(), FragmentAddressList.FRAG_TITLE);
                    System.out.println("=== extraItemNameG :" + extraItemNameG);
                    System.out.println("=== extraItemIDG :" + extraItemIDG);

                    sharedPreferencesData.createNewSharedPreferences(Constants.PRICEPREFERENCE);
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.SUBTOTAL, totalPrice + "");
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.FOODDISCOUNT, discount + "");
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.SERVICEFEES, service_fee + "");
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.SALESTAX, sales_tax.toString());
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.VAT, service_vat + "");
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.DELIVERYFEES, deliveryChargeValue + "");
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.PACKAGINGFEES, PackageFeesValue + "");
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.TOTALPR, grandTotal + "");
                    sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.SIZEOFPIZZA, raviCartModles.get(0).getSize_item_name());

                    if (prefsHelper.isLoggedIn()) {
                        if (orderType.equalsIgnoreCase("Delivery")) {
                            Intent intent = new Intent(CartActivity.this, SavedAddressActivity.class);
                            intent.putExtra("RestId", RestId);
                            intent.putExtra("TotalPrice", String.valueOf(grandTotal));
                            intent.putExtra("SubTotalPrice", String.valueOf(grandTotal));
                            intent.putExtra("RESTName", strMainRestName);
                            intent.putExtra("RESTLOGO", strMainRestLogo);
                            intent.putExtra("subPizzaItemId", pizzaItemid);
                            intent.putExtra("SIZEITEMID", strsizeid);
                            intent.putExtra("globTempExtraItemidWithSizeIdd", extraItemID);
                            intent.putExtra("delivery_date", delivery_date);
                            intent.putExtra("food_cost", FoodCosts);
                            intent.putExtra("quantity", quantity);
                            intent.putExtra("deliveryChargeValue", deliveryChargeValue);
                            intent.putExtra("SeviceFeesValue", SeviceFeesValue);
                            intent.putExtra("ServiceFees", ServiceFees);
                            intent.putExtra("ServiceFeesType", ServiceFeesType);
                            intent.putExtra("PackageFeesType", PackageFeesType);
                            intent.putExtra("PackageFees", PackageFees);
                            intent.putExtra("PackageFeesValue", PackageFeesValue);
                            intent.putExtra("SalesTaxAmount", SalesTaxAmount);
                            intent.putExtra("VatTax", VatTax);
                            intent.putExtra("deliveryType", orderType);
                            intent.putExtra("pizzaQuantity", quantity);
                            intent.putExtra("Pizzaname", selectedPizzaName);
                            intent.putExtra("rest_address",rest_address);
                            intent.putExtra("instructions", instructions);
                            intent.putExtra("selectedPizzaItemPrice", selectedPizzaItemPrice);


                            //sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE,Constants.PACKAGINGFEES,subTotalAmount);

                            startActivity(intent);
                        } else {
                            Intent i = new Intent(CartActivity.this, CartCheckout.class);
                            i.putExtra("RestId", RestId);
                            i.putExtra("TotalPrice", String.valueOf(grandTotal));
                            i.putExtra("SubTotalPrice", String.valueOf(grandTotal));
                            i.putExtra("RESTName", strMainRestName);
                            i.putExtra("RESTLOGO", strMainRestLogo);
                            i.putExtra("subPizzaItemId", pizzaItemid);
                            i.putExtra("SIZEITEMID", strsizeid);
                            i.putExtra("globTempExtraItemidWithSizeIdd", extraItemID);
                            i.putExtra("delivery_date", delivery_date);
                            i.putExtra("food_cost", FoodCosts);
                            i.putExtra("quantity", quantity);
                            i.putExtra("deliveryChargeValue", deliveryChargeValue);
                            i.putExtra("SeviceFeesValue", SeviceFeesValue);
                            i.putExtra("ServiceFees", ServiceFees);
                            i.putExtra("ServiceFeesType", ServiceFeesType);
                            i.putExtra("PackageFeesType", PackageFeesType);
                            i.putExtra("PackageFees", PackageFees);
                            i.putExtra("PackageFeesValue", PackageFeesValue);
                            i.putExtra("SalesTaxAmount", SalesTaxAmount);
                            i.putExtra("VatTax", VatTax);
                            i.putExtra("deliveryType", orderType);
                            i.putExtra("pizzaQuantity", quantity);
                            i.putExtra("Pizzaname", selectedPizzaName);
                            i.putExtra("instructions", instructions);
                            i.putExtra("rest_address",rest_address);
                            i.putExtra("selectedPizzaItemPrice", selectedPizzaItemPrice);
                            startActivity(i);
                        }
                    } else {
                        Intent i = new Intent(CartActivity.this, CartCheckout.class);
                        i.putExtra("RestId", RestId);
                        i.putExtra("TotalPrice", String.valueOf(grandTotal));
                        i.putExtra("SubTotalPrice", String.valueOf(grandTotal));
                        i.putExtra("RESTName", strMainRestName);
                        i.putExtra("RESTLOGO", strMainRestLogo);
                        i.putExtra("subPizzaItemId", pizzaItemid);
                        i.putExtra("SIZEITEMID", strsizeid);
                        i.putExtra("globTempExtraItemidWithSizeIdd", extraItemID);
                        i.putExtra("delivery_date", delivery_date);
                        i.putExtra("food_cost", FoodCosts);
                        i.putExtra("quantity", quantity);
                        i.putExtra("deliveryChargeValue", deliveryChargeValue);
                        i.putExtra("SeviceFeesValue", SeviceFeesValue);
                        i.putExtra("ServiceFees", ServiceFees);
                        i.putExtra("ServiceFeesType", ServiceFeesType);
                        i.putExtra("PackageFeesType", PackageFeesType);
                        i.putExtra("PackageFees", PackageFees);
                        i.putExtra("PackageFeesValue", PackageFeesValue);
                        i.putExtra("SalesTaxAmount", SalesTaxAmount);
                        i.putExtra("VatTax", VatTax);
                        i.putExtra("deliveryType", orderType);
                        i.putExtra("pizzaQuantity", quantity);
                        i.putExtra("Pizzaname", selectedPizzaName);
                        i.putExtra("instructions", instructions);
                        i.putExtra("rest_address",rest_address);
                        i.putExtra("selectedPizzaItemPrice", selectedPizzaItemPrice);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(this, model.getPLEASESELECTORDERTYPE(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.img_back:
                Intent i = new Intent(CartActivity.this, RestaurantDetails.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    public void changeSelect(String type) {
        if (type.equals("Pickup")) {
            rl_pickup.setBackgroundResource(R.drawable.circle_background);
            rl_delivery.setBackgroundResource(R.drawable.edit_back_with_gray);
            rlDineIn.setBackgroundResource(R.drawable.edit_back_with_gray);
            img_delivery.setColorFilter(Color.BLACK);
            img_pickup.setColorFilter(Color.WHITE);
            tvDelivery.setTextColor(Color.BLACK);
            tvPickup.setTextColor(Color.WHITE);
            tvEatIn.setTextColor(Color.BLACK);

            //  img_select_picup.setImageResource(R.drawable.img_select);
            //  img_select_delivery.setImageResource(R.drawable.unselect);
        }
      else  if (type.equals("Delivery")) {
            rl_delivery.setBackgroundResource(R.drawable.circle_background);
            rl_pickup.setBackgroundResource(R.drawable.edit_back_with_gray);
            rlDineIn.setBackgroundResource(R.drawable.edit_back_with_gray);
            img_delivery.setColorFilter(Color.WHITE);
            img_pickup.setColorFilter(Color.BLACK);
            tvEatIn.setTextColor(Color.WHITE);
            tvPickup.setTextColor(Color.BLACK);
            tvDelivery.setTextColor(Color.WHITE);
            // img_select_picup.setImageResource(R.drawable.unselect);
            // img_select_delivery.setImageResource(R.drawable.img_select);
        }
       else if (type.equals("EAT-IN")) {
            rl_delivery.setBackgroundResource(R.drawable.edit_back_with_gray);
            rl_pickup.setBackgroundResource(R.drawable.edit_back_with_gray);
            rlDineIn.setBackgroundResource(R.drawable.circle_background);
            tvDelivery.setTextColor(Color.BLACK);
            tvPickup.setTextColor(Color.BLACK);
            tvEatIn.setTextColor(Color.WHITE);
            // img_select_picup.setImageResource(R.drawable.unselect);
            // img_select_delivery.setImageResource(R.drawable.img_select);
        }
    }

    public class CartAdapterravi extends RecyclerView.Adapter<CartAdapterravi.ViewHolder> {
        Context context;
        ArrayList<RaviCartModle> rr;

        public CartAdapterravi(Context c, ArrayList<RaviCartModle> r) {
            this.context = c;
            this.rr = r;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vv = layoutInflater.inflate(R.layout.recyclerview_cart_item, parent, false);
            return new ViewHolder(vv);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            if (rr.get(position).getSize_item_name().equals("0") && rr.get(position).getExtra_item_name().equals("0")) {
                holder.tvMenuItemName.setText(rr.get(position).getItem_name());
//                selectedPizzaName = rr.get(position).getItem_name();
                holder.tv_menu_item_extra.setVisibility(View.GONE);
                holder.tv_menu_item_size.setVisibility(View.GONE);
            } else if (rr.get(position).getSize_item_name() != "0" && rr.get(position).getExtra_item_name().equals("0")) {
                holder.tvMenuItemName.setText(rr.get(position).getItem_name());
                holder.tv_menu_item_size.setText("(" + rr.get(position).getSize_item_name() + ")");
//                selectedPizzaName = rr.get(position).getItem_name();
                holder.tv_menu_item_extra.setVisibility(View.GONE);
            } else if (rr.get(position).getSize_item_name().equals("0") && rr.get(position).getExtra_item_name() != "0") {
                holder.tvMenuItemName.setText(rr.get(position).getItem_name());
//                selectedPizzaName = rr.get(position).getItem_name();

                holder.tv_menu_item_extra.setVisibility(View.VISIBLE);
                String a = rr.get(position).getExtra_item_name().replace("[", "");
                a = a.replace("]", "");
                a = a.replace(",", "\n+");
                extraItemNameG = a;
                extraItemIDG = rr.get(position).getExtra_item_id();
                System.out.println("==== extraname 1: " + extraItemNameG);
                System.out.println("==== extraname 1: " + extraItemIDG);
                holder.tv_menu_item_extra.setText("+" + a);
            } else if (rr.get(position).getSize_item_name() != "0" && rr.get(position).getExtra_item_name() != "0") {
                holder.tvMenuItemName.setText(rr.get(position).getItem_name());
                holder.tv_menu_item_size.setText("(" + rr.get(position).getSize_item_name() + ")");
//                selectedPizzaName = rr.get(position).getItem_name();
                holder.tv_menu_item_extra.setVisibility(View.VISIBLE);
                String a = rr.get(position).getExtra_item_name().replace("[", "");
                a = a.replace("]", "");
                a = a.replace(",", "\n+");
                extraItemNameG = a;
                extraItemIDG = rr.get(position).getExtra_item_id();
                System.out.println("==== extraname 1: " + extraItemNameG);
                System.out.println("==== extraname 1: " + extraItemIDG);
                holder.tv_menu_item_extra.setText("+" + a);
            }
            holder.tv_subcat_item_details.setText(rr.get(position).getItemSubcatDetails());
            holder.etInstruction.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length()>0) {
                        instructions =CommonMethods.getStringDataInbase64(s.toString());
                    }
                }
            });

            double pp = Double.parseDouble(rr.get(position).getPrice());
            holder.tvItemPrice.setText(currencySymbol + dotToCommaClass.changeDot(String.format("%.2f", pp)));
//            selectedPizzaItemPrice = holder.tvItemPrice.getText().toString();
            holder.tvQuantity.setText(rr.get(position).getItem_quantity());

            holder.ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qunt = 0;
                    double price = 0.0;
                    String subMenuItemId = rr.get(position).getItem_id();
                    String size_id = rr.get(position).getSize_item_id();
                    SQLiteDatabase db = database.getReadableDatabase();
                    Cursor cursor = db.rawQuery("SELECT * FROM item_table where item_id='" + subMenuItemId + "' AND size_item_id='"
                            + size_id + "' AND extra_item_id='" + rr.get(position).getExtra_item_id() + "'", null);
                    if (cursor.moveToNext()) {
                        qunt = Integer.parseInt(cursor.getString(7));
                        price = Double.parseDouble(rr.get(position).getPrice());
                        price = price / qunt;
                        price = (double) Math.round(price * 100) / 100;
                        qunt = qunt + 1;
                        price = price * qunt;
                        totalPrice = price;
                    }
                    database.update_item_size_with_topping(subMenuItemId, size_id, rr.get(position).getExtra_item_id(), qunt, price);
                    rr.get(position).setItem_quantity(String.valueOf(qunt));
                    updateCart();
                    getDiscount();
                    getShippingChargeData();
                }
            });

            holder.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qunt = 0;
                    double price = 0.0;
                    String subMenuItemId = rr.get(position).getItem_id();
                    String size_id = rr.get(position).getSize_item_id();
                    SQLiteDatabase db = database.getReadableDatabase();
                    Cursor cursor = db.rawQuery("SELECT * FROM item_table where item_id='" + subMenuItemId + "' AND size_item_id='"
                            + size_id + "' AND extra_item_id='" + rr.get(position).getExtra_item_id() + "'", null);
                    if (cursor.moveToNext()) {
                        qunt = Integer.parseInt(cursor.getString(7));
                        price = Double.parseDouble(rr.get(position).getPrice());
                        price = price / qunt;
                        price = (double) Math.round(price * 100) / 100;
                        qunt = qunt - 1;
                        price = price * qunt;
                        totalPrice = price;
                    }
                    if (qunt == 0) {
                        database.delete_Item_size(rr.get(position).getItem_id(), rr.get(position).getSize_item_id(), rr.get(position).getExtra_item_id());
                        RestaurantDetails.cart_Item_number = RestaurantDetails.cart_Item_number - 1;
                        rr.remove(position);
                        notifyDataSetChanged();
                    } else {
                        database.update_item_size_with_topping(subMenuItemId, size_id, rr.get(position).getExtra_item_id(), qunt, price);
                        rr.get(position).setItem_quantity(String.valueOf(qunt));
                    }
                    AddExtraActivity.cart_Item_number = rr.size();
                    updateCart();
                    getDiscount();
                    getShippingChargeData();
                }
            });

            holder.cartDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!rr.get(position).getSize_item_name().equalsIgnoreCase("0") && rr.get(position).getExtra_item_name().equals("0")) {
                        database.delete_Item_size(rr.get(position).getItem_id(), rr.get(position).getSize_item_id());
                        RestaurantDetails.cart_Item_number = RestaurantDetails.cart_Item_number - 1;
                    } else {
                        database.delete_Item_size(rr.get(position).getItem_id(), rr.get(position).getSize_item_id());

                        database.delete_Item(rr.get(position).getItem_id());
                        RestaurantDetails.cart_Item_number = RestaurantDetails.cart_Item_number - 1;
                    }
                    updateData();
                }
            });
        }

        @Override
        public int getItemCount() {
            return rr.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvMenuItemName, tvItemPrice, tvQuantity, tv_menu_item_extra, ivPlus, ivMinus, tv_menu_item_size, tv_subcat_item_details;
            private ImageView cartDelete;
            private EditText etInstruction;

            public ViewHolder(View itemView) {
                super(itemView);
                tvMenuItemName = itemView.findViewById(R.id.tv_menu_item_name);
                tvItemPrice = itemView.findViewById(R.id.tv_item_price);
                tvQuantity = itemView.findViewById(R.id.tv_quantity);
                tv_menu_item_extra = itemView.findViewById(R.id.tv_menu_item_extra);
                ivPlus = itemView.findViewById(R.id.iv_plus);
                ivMinus = itemView.findViewById(R.id.iv_minus);
                cartDelete = itemView.findViewById(R.id.cart_delete);
                etInstruction = itemView.findViewById(R.id.etInstruction);
                tv_menu_item_size = itemView.findViewById(R.id.tv_menu_item_size);
                tv_subcat_item_details = itemView.findViewById(R.id.tv_subcat_item_details);

                etInstruction.setHint(model.getInstruction());
            }
        }
    }

    private void updateData() {
        grandTotal = totalPrice - discount + service_fee + service_vat + sales_tax + deliveryCost + packagingCost - coupon_price - loyalty_price;
        tv_total.setText(currencySymbol + "" + "" + dotToCommaClass.changeDot(String.format("%.2f", grandTotal)));
        checkout_total_price.setText(currencySymbol + "" + "" + dotToCommaClass.changeDot(String.format("%.2f", grandTotal)));
        order_price = String.valueOf(totalPrice);
        subTotalAmount = String.valueOf(totalPrice);
        tv_sub_total.setText(currencySymbol + "" + "" + dotToCommaClass.changeDot(String.format("%.2f", totalPrice)));
//      tv_food_item_total.setText(currencySymbol + "" + "" + String.format("%.2f", totalPrice));
        FoodCosts = tv_food_item_total.getText().toString();
    }

    private void getShippingChargeData() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<ShippingCartModel> observable = apiInterface.getServiceCharge(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE),
                String.valueOf(totalPrice), RestId, postalCode, orderType);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShippingCartModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShippingCartModel searchResult) {
                        try {
                            // Toast.makeText(getApplicationContext(), "Success Called=", Toast.LENGTH_LONG).show();
                            deliveryChargeValue = searchResult.getDeliveryChargeValue();
                            SeviceFeesValue = searchResult.getSeviceFeesValue();
                            ServiceFees = searchResult.getServiceFees();
                            ServiceFeesType = searchResult.getServiceFeesType();
                            PackageFeesType = searchResult.getPackageFeesType();
                            PackageFees = searchResult.getPackageFees();
                            PackageFeesValue = searchResult.getPackageFeesValue();
                            SalesTaxAmount = searchResult.getSalesTaxAmount();
                            VatTax = searchResult.getVatTax();

                            service_fee = Double.parseDouble(SeviceFeesValue);
                            service_vat = Double.parseDouble(VatTax);
                            sales_tax = Double.parseDouble(SalesTaxAmount);
                            //Log.e("ServiceFee=",service_fee+"");

                            if (service_fee == 0 || service_fee == 0.00) {
                                tvServiceFee.setVisibility(View.GONE);
                                tvServiceFeeText.setVisibility(View.GONE);
                            } else {
                                tvServiceFee.setVisibility(View.VISIBLE);
                                tvServiceFeeText.setVisibility(View.VISIBLE);
                                tvServiceFee.setText(currencySymbol + "" + dotToCommaClass.changeDot(String.format("%.2f", service_fee)));
                            }

                            if (service_vat == 0 || service_vat == 0.00) {
                                tvVatText.setVisibility(View.GONE);
                                tvVat.setVisibility(View.GONE);
                            } else {
                                tvVatText.setVisibility(View.VISIBLE);
                                tvVat.setVisibility(View.VISIBLE);
                                tvVat.setText(currencySymbol + "" + dotToCommaClass.changeDot(String.format("%.2f", service_vat)));
                            }

                            if (sales_tax == 0 || sales_tax == 0.00) {
                                tvServiceChargeText.setVisibility(View.GONE);
                                tvServiceCharge.setVisibility(View.GONE);
                            } else {
                                tvServiceCharge.setVisibility(View.VISIBLE);
                                tvServiceChargeText.setVisibility(View.VISIBLE);
                                tvServiceCharge.setText(currencySymbol + "" + dotToCommaClass.changeDot(String.format("%.2f", sales_tax)));
                            }

                            if (orderType.equalsIgnoreCase("Delivery")) {
                                if (Double.parseDouble(deliveryChargeValue) == 0 || Double.parseDouble(deliveryChargeValue) == 0.00) {
                                    tvDeliveryFee.setVisibility(View.GONE);
                                    tvDeliveryText.setVisibility(View.GONE);
                                    deliveryCost = 0.0;
                                } else {
                                    deliveryCost = Double.parseDouble(deliveryChargeValue);
                                    tvDeliveryFee.setVisibility(View.VISIBLE);
                                    tvDeliveryFee.setText(currencySymbol + "" + dotToCommaClass.changeDot(String.format("%.2f", Double.parseDouble(deliveryChargeValue))));
                                    tvDeliveryText.setVisibility(View.VISIBLE);
                                }

                                if (Double.parseDouble(PackageFeesValue) == 0 || Double.parseDouble(PackageFeesValue) == 0.00) {
                                    packagingCost = 0.0;
                                    tvPackagingFee.setVisibility(View.GONE);
                                    tvPackagingText.setVisibility(View.GONE);
                                } else {
                                    packagingCost = Double.parseDouble(PackageFeesValue);
                                    tvPackagingFee.setVisibility(View.VISIBLE);
                                    tvPackagingFee.setText(currencySymbol + "" + dotToCommaClass.changeDot(String.format("%.2f", Double.parseDouble(PackageFeesValue))));
                                    tvPackagingText.setVisibility(View.VISIBLE);
                                }
//                            grandTotal = grandTotal + Double.parseDouble(deliveryChargeValue);
                            } else if (orderType.equalsIgnoreCase("Pickup")) {
                                if (Double.parseDouble(PackageFeesValue) == 0 || Double.parseDouble(PackageFeesValue) == 0.00) {
                                    tvPackagingFee.setVisibility(View.GONE);
                                    tvPackagingText.setVisibility(View.GONE);
                                    tvDeliveryText.setVisibility(View.GONE);
                                    tvDeliveryFee.setVisibility(View.GONE);
                                    deliveryCost = 0.0;
                                    packagingCost = 0.0;
                                } else {
                                    deliveryCost = 0.0;
                                    packagingCost = Double.parseDouble(PackageFeesValue);
                                    tvPackagingFee.setVisibility(View.VISIBLE);
                                    tvPackagingFee.setText(currencySymbol + "" + String.format("%.2f", Double.parseDouble(PackageFeesValue)));
                                    tvPackagingText.setVisibility(View.VISIBLE);
                                    tvDeliveryText.setVisibility(View.GONE);
                                    tvDeliveryFee.setVisibility(View.GONE);
                                }
                            } else {
                                deliveryCost = 0.0;
                                packagingCost = 0.0;
                                tvDeliveryFee.setVisibility(View.GONE);
                                tvDeliveryText.setVisibility(View.GONE);
                                tvPackagingText.setVisibility(View.GONE);
                                tvPackagingFee.setVisibility(View.GONE);
                            }
//                        updateCart();
                            updateData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });

    }

    private void getDiscount() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<GetRestaurantDiscountResponse> observable = apiInterface.getRestaurentDiscountPrice(prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE), String.valueOf(totalPrice), RestId, orderType);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetRestaurantDiscountResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetRestaurantDiscountResponse searchResult) {
                        // Toast.makeText(getApplicationContext(), "Success Called=", Toast.LENGTH_LONG).show();
                        try {
                            discountFoodItems = searchResult.getDiscountFoodItems();
                            discountOfferDescription = searchResult.getDiscountOfferDescription();
                            discountOfferPercentage = searchResult.getDiscountOfferPercentage();
                            discountOfferPrice = searchResult.getDiscountOfferPrice();

                            if (discountOfferPrice.equalsIgnoreCase("") || discountOfferPrice.equalsIgnoreCase("0")
                                    || discountOfferPrice.equalsIgnoreCase("0.00"))
                                discount = 0.0;
                            else {
                                discount = Double.parseDouble(discountOfferPrice);
                            }

                            if (discount == 0.0) {
                                tvFoodItems.setVisibility(View.GONE);
                                tv_food_item_total.setVisibility(View.GONE);
                            } else {
                                tvFoodItems.setVisibility(View.VISIBLE);
                                tv_food_item_total.setVisibility(View.VISIBLE);
                                tv_food_item_total.setText(currencySymbol + "" + dotToCommaClass.changeDot(String.format("%.2f", discount)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                        updateCart()
                        }
                        ;
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");
                    }
                });
    }

    public void showProgress() {
        try {
            progressDialog.setMessage(model.getPlease_wait_text().trim()+"...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void hideProgress() {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*  @BindView(R.id.llApplyCoupon)
      CardView llApplyCoupon;*/
    @OnClick(R.id.llApplyCOupon)
    public void rvApplyCouponClicked(View view) {
        dialogApplyCoupon();
    }

    @OnClick(R.id.llLoyalityPoint)
    public void llLoyalityPointClicked(View view) {
        if (redeemPointsTotal.equalsIgnoreCase("") || redeemPointsTotal.equalsIgnoreCase("0")) {
            Toast.makeText(getApplicationContext(), model.getYouHave().trim() + " 0 " + model.getLoyaltyPoints(), Toast.LENGTH_SHORT).show();
        } else {
            if (!loyalty)
                openLoyaltyDialog();
        }

        //showProgress();
//        if (redeemPointsTotal.equalsIgnoreCase("") || redeemPointsTotal.equalsIgnoreCase("0")) {
//            Toast.makeText(getApplicationContext(), model.getPleaseEnterLoyaltyPoint(), Toast.LENGTH_LONG).show();
//            showProgress();
//            redeemPointsUser(etLoyaltyPoint.getText().toString(), dialog);
//        } else {
//
//        }
    }

    private boolean loyalty = false;

    private void openLoyaltyDialog() {
        Dialog dialog = new Dialog(CartActivity.this);
        dialog.setContentView(R.layout.dialog_loyality);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        EditText etLoyaltyPoint = dialog.findViewById(R.id.etLoyaltyPoint);
        Button btnApply = dialog.findViewById(R.id.btnApply);
        TextView tvApply = dialog.findViewById(R.id.tvApply);
        TextView tvLoyaltyPoint = dialog.findViewById(R.id.tvLoyaltyPoint);

        tvApply.setText(model.getRedeemLoyaltyPoints());
        tvLoyaltyPoint.setText(model.getYouHave() + " " + redeemPointsTotal + " " + model.getLoyaltyPoints());
        btnApply.setText(model.getRedeem());
        etLoyaltyPoint.setHint(model.getEnterLoyaltyPointCode());

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLoyaltyPoint.getText().toString() != null && etLoyaltyPoint.getText().toString().length() > 0) {
                   int loyality_points=Integer.parseInt( etLoyaltyPoint.getText().toString());
                   int redeempoints= Integer.parseInt(redeemPointsTotal);
                   if(loyality_points>0 && loyality_points<=redeempoints) {
                       redeemPointsUser(etLoyaltyPoint.getText().toString(), dialog);
                   }
                   else{
                       Toast.makeText(getApplicationContext(), model.getPleaseEnterLoyaltyPoint(), Toast.LENGTH_LONG).show();
                   }
                } else {
                    Toast.makeText(getApplicationContext(), model.getPleaseEnterLoyaltyPoint(), Toast.LENGTH_LONG).show();
                }
            }
        });
       /* dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;*/
        dialog.show();
    }

    String couponCodeText = "";

    private void dialogApplyCoupon() {
        Dialog dialog = new Dialog(CartActivity.this);
        dialog.setContentView(R.layout.dialog_applycoupons);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        EditText etCouponCode = dialog.findViewById(R.id.etCouponCode);
        Button btnApply = dialog.findViewById(R.id.btnApply);
        TextView tvApply = dialog.findViewById(R.id.tvApply);

        btnApply.setText(model.getApply());
        tvApply.setText(model.getApplyCoupon());
        etCouponCode.setHint(model.getEnterCouponCode());

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCouponCode != null && etCouponCode.getText().toString().length() > 0) {
                    if(!etCouponCode.getText().toString().trim().isEmpty()) {
                        applyCoupon(etCouponCode.getText().toString(), dialog);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), model.getAPPLYCOUPONISREQUIRED(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), model.getAPPLYCOUPONISREQUIRED(), Toast.LENGTH_LONG).show();
                }
            }
        });
       /* dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;*/
        dialog.show();
    }


    private void applyCoupon(String couponCOde, Dialog dialog) {

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<RmVerifyCouponCodeResponse> observable = apiInterface.getCouponCode(RestId, prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE), String.valueOf(totalPrice), couponCOde);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RmVerifyCouponCodeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RmVerifyCouponCodeResponse searchResult) {

//                        grandTotal = grandTotal - Double.parseDouble(searchResult.getCouponCodePrice());
//                        tv_total.setText(new DecimalFormat("##.##").format(totalPrice).toString());
//                        checkout_total_price.setText(new DecimalFormat("##.##").format(totalPrice).toString());
                        if (searchResult.getError() == 2) {
                            tvFoodDiscount.setVisibility(View.GONE);
                            tv_food_discount_total.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), searchResult.getError_msg(), Toast.LENGTH_LONG).show();
                        } else {
                            coupon_price = Double.parseDouble(searchResult.getCouponCodePrice());
                            if (coupon_price == 0 || coupon_price == 0.00) {
                                tvFoodDiscount.setVisibility(View.GONE);
                                tv_food_discount_total.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), searchResult.getError_msg(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), searchResult.getSuccessMsg(), Toast.LENGTH_LONG).show();
                                CouponApplied couponApplied=DroidPrefs.get(CartActivity.this,"coupon_applied",CouponApplied.class);
                                if(couponApplied!=null){
                                    if(couponApplied.coupon_value==null){
                                        couponApplied.coupon_value=searchResult.getCouponCodePrice();
                                        couponApplied.is_coupon_applied=true;
                                        DroidPrefs.apply(CartActivity.this,"coupon_applied",couponApplied);
                                    }
                                }
                                card_coupon.setVisibility(View.GONE);
                                tvFoodDiscount.setVisibility(View.VISIBLE);
                                tv_food_discount_total.setVisibility(View.VISIBLE);

                                tv_food_discount_total.setText("-" + currencySymbol + dotToCommaClass.changeDot(searchResult.getCouponCodePrice()));
                                updateData();
                            }
                        }
                        dialog.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });

    }

    private void getLoyalityPoitsAll() {

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<RmGetLoyaltyPointResponse> observable = apiInterface.getTotalLoyaltyPoint(CustomerId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RmGetLoyaltyPointResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //hideProgress();
                    }

                    @Override
                    public void onNext(RmGetLoyaltyPointResponse searchResult) {
                        hideProgress();
                        redeemPointsTotal = searchResult.getTotalLoyaltyPoints();
                        tvLoyaliteyPoints.setText(searchResult.getTotalLoyaltyPoints() + " " + model.getTotalPoints());
                        //Toast.makeText(getApplicationContext(),searchResult.getTotalLoyaltyPoints().toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");
                        hideProgress();

                    }
                });
    }

    private void redeemPointsUser(String s, Dialog dialog) {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<RmVerifyLoyaltyPointResponse> observable = apiInterface.verifyLoyaltyPoint(CustomerId, s, String.valueOf(totalPrice));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RmVerifyLoyaltyPointResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //hideProgress();
                    }

                    @Override
                    public void onNext(RmVerifyLoyaltyPointResponse searchResult) {
                        try {
                            hideProgress();
                            dialog.dismiss();
                            if (searchResult.getSuccess() == 0) {
                                loyalty = true;
                                if (searchResult.getTotalLoyaltyAmount() != null) {
                                    loyalty_price = Double.parseDouble(searchResult.getTotalLoyaltyAmount());

//                        checkout_total_price.setText(new DecimalFormat("##.##").format(totalPrice).toString());
                                    if (loyalty_price == 0 || loyalty_price == 0.00) {
                                        tvLoyaltyText.setVisibility(View.GONE);
                                        tvLoyaltyFee.setVisibility(View.GONE);
                                    } else {
                                        tvLoyaltyFee.setVisibility(View.VISIBLE);
                                        tvLoyaltyText.setVisibility(View.VISIBLE);
                                        cardViewLoyality.setVisibility(View.GONE);
                                        LoyaltyApplied loyaltyApplied= DroidPrefs.get(CartActivity.this,"loyalty_applied",LoyaltyApplied.class);
                                        if(loyaltyApplied!=null){
                                            if(loyaltyApplied.loyalty_points==null){
                                                loyaltyApplied.loyalty_points=searchResult.getTotalLoyaltyAmount();
                                                loyaltyApplied.is_loyalty_applied=true;
                                                DroidPrefs.apply(CartActivity.this,"loyalty_applied",loyaltyApplied);
                                            }
                                        }
                                        tvLoyaltyFee.setText("-" + currencySymbol + dotToCommaClass.changeDot(searchResult.getTotalLoyaltyAmount()));
                                        updateData();
                                    }
                                }
                            } else {
                                cardViewLoyality.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), searchResult.getSuccessMsg(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");
                        hideProgress();
                    }
                });
    }
}
