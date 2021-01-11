package com.app.liferdeal.ui.activity.restaurant;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.menuitems.AllCategoryResponse;
import com.app.liferdeal.model.menuitems.MenuCat;
import com.app.liferdeal.model.restaurant.RestDetailClickFoodModel;
import com.app.liferdeal.model.restaurant.RestaurantDetailsModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.activity.cart.CartActivity;
import com.app.liferdeal.ui.activity.login.SignInActivity;
import com.app.liferdeal.ui.adapters.Restaurant_Details_quick;
import com.app.liferdeal.ui.adapters.SectionDetailAdapter;
import com.app.liferdeal.ui.interfaces.Section;
import com.app.liferdeal.ui.interfaces.SelectMenuItem;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;
import com.bumptech.glide.Glide;
import com.shuhart.stickyheader.StickyAdapter;
import com.shuhart.stickyheader.StickyHeaderItemDecorator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantDetails extends AppCompatActivity implements View.OnClickListener, Restaurant_Details_quick.RestaurantDetailsQuickInterface, SelectMenuItem {
    public static TextView tvMenuItemName, tvCartItemCount, tvCartItemCount1, txt_title, tv_cart_item_countt;
    public static int cart_Item_number;
    @BindView(R.id.img_restmenu_info)
    ImageView imgRestmenuInfo;
    @BindView(R.id.tvName)
    TextView tvName;
    //    @BindView(R.id.sv)
//    NestedScrollView sv;
    @BindView(R.id.rlt_card_view)
    CardView rlt_card_view;
    @BindView(R.id.rlt_sec)
    RelativeLayout rltSec;
    @BindView(R.id.rlt_third)
    RelativeLayout rltThird;
    @BindView(R.id.rlt_four)
    RelativeLayout rltFour;
    @BindView(R.id.rlt_main)
    RelativeLayout rlt_main;
    @BindView(R.id.rlt_fifth)
    RelativeLayout rlt_fifth;
    @BindView(R.id.tvTotalItemCnt)
    TextView tvTotalItemCnt;
    @BindView(R.id.tv_TotalPrice)
    TextView tv_TotalPrice;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    private PrefsHelper prefsHelper;
    private ImageView img_back, img_back1, shop_img_places, rset_logo, shop_img_places_cat, img_restmenu_info, img_discount_btn;
    private RecyclerView rcv_rest_details_list, quickrecycler;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private String restourantBookLimit, clickRestId, clickRestName, LOCATIONDISTANCE, RESTCOVER, RESTLOGO, RESTADDRESS, RESTISOPEN, SIZEITEMID, tempExtraItemidWithSizeIdd, globTempExtraItemidWithSizeIdd, getRatingValue;
    private RelativeLayout rl_cart, rl_cart1, rl_cartt;
    private LinearLayout img_view_gallery, lnr_rest_menyu_details, lnr_bookatable, lnr_view_rating;
    private Database database;
    private ProgressBar banner_progress;
    private TextView shop_image_place_text, tv_item_discount_cost, txt_rest_address, txt_rest_name, txt_rest_name1, tv_restaurant_rating_value,
            tvCusineName, tvOrderOnline, tvGallery, tvMenu, tvReserveTable, tvRating, tvViewCart;
    private int subPizzaItemId;
    private RatingBar ratingBar;
    private String homeDelAvailable, pickUpAvailable, dineInAvailable, restDeliveryAvail, RESTCUSINE;
    private int oldPos;
    private ArrayList<Section> sectionArrayList = new ArrayList<>();
    private Restaurant_Details_quick adapterCategory;
    private Boolean[] selectedItem;
    private int selectedPos = 0;
    private List<RestaurantDetailsModel.RestaurantMencategory> listnew;
    private String globcategoryImage = "", globRestId = "", colorCode, table_booking = "";
    private int globSelectedCatId;
    private ArrayList<String> itemCount = new ArrayList<>();
    private LanguageResponse model = new LanguageResponse();

    private SharedPreferencesData sharedPreferencesData;

    DotToCommaClass dotToCommaClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(RestaurantDetails.this) != null) {
            model = App.retrieveLangFromGson(RestaurantDetails.this);
        }
        init();
    }

    private void init() {
//        try {
        sharedPreferencesData = new SharedPreferencesData(getApplicationContext());
        dotToCommaClass = new DotToCommaClass(getApplicationContext());
        prefsHelper = new PrefsHelper(this);

        progressDialog = new ProgressDialog(this);
        tvCusineName = findViewById(R.id.tvCusineName);
        img_back = findViewById(R.id.img_back);
        img_back1 = findViewById(R.id.img_back1);
        database = new Database(RestaurantDetails.this);
        rcv_rest_details_list = findViewById(R.id.rcv_rest_details_list);
        tvCartItemCount = findViewById(R.id.tv_cart_item_count);
        tvCartItemCount1 = findViewById(R.id.tv_cart_item_count1);
        tv_cart_item_countt = findViewById(R.id.tv_cart_item_countt);
        shop_image_place_text = findViewById(R.id.shop_image_place_text);
        tv_item_discount_cost = findViewById(R.id.tv_item_discount_cost);
        shop_img_places = findViewById(R.id.shop_img_places);
        rset_logo = findViewById(R.id.rset_logo);
        txt_rest_address = findViewById(R.id.txt_rest_address);
        banner_progress = findViewById(R.id.banner_progress);
        rl_cart = findViewById(R.id.cart_count_layout);
        rl_cart1 = findViewById(R.id.cart_count_layout1);
        rl_cartt = findViewById(R.id.rl_cart);
        txt_rest_name = findViewById(R.id.txt_rest_name);
        txt_rest_name1 = findViewById(R.id.txt_rest_name1);
        quickrecycler = findViewById(R.id.quickrecycler);
        img_view_gallery = findViewById(R.id.img_view_gallery);
        lnr_rest_menyu_details = findViewById(R.id.lnr_rest_menyu_details);
        lnr_bookatable = findViewById(R.id.lnr_bookatable);
        shop_img_places_cat = findViewById(R.id.shop_img_places_cat);
        lnr_view_rating = findViewById(R.id.lnr_view_rating);
        img_restmenu_info = findViewById(R.id.img_restmenu_info);
        img_discount_btn = findViewById(R.id.img_discount_btn);
        ratingBar = findViewById(R.id.ratingBar);
        tv_restaurant_rating_value = findViewById(R.id.tv_restaurant_rating_value);
        tvOrderOnline = findViewById(R.id.tvOrderOnline);
        tvGallery = findViewById(R.id.tvGallery);
        tvReserveTable = findViewById(R.id.tvReserveTable);
        tvMenu = findViewById(R.id.tvMenu);
        tvRating = findViewById(R.id.tvRating);
        tvViewCart = findViewById(R.id.tvViewCart);

        txt_rest_name1.setText(model.getMenu().trim());
        tvOrderOnline.setText(model.getOnlineOrder());
        tvGallery.setText(model.getGallery());
        tvReserveTable.setText(model.getReserveATable());
        tvMenu.setText(model.getMenu());
        tvRating.setText(model.getRating());
        tvViewCart.setText(model.getVIEWCART());


//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RestaurantDetails.this);
//            rcv_rest_details_list.setLayoutManager(mLayoutManager);
//            rcv_rest_details_list.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        quickrecycler.setLayoutManager(horizontalLayoutManager2);
        img_back.setOnClickListener(this);
        img_back1.setOnClickListener(this);
        rl_cart.setOnClickListener(this);
        rl_cart1.setOnClickListener(this);
        rl_cartt.setOnClickListener(this);
        img_view_gallery.setOnClickListener(this);
        lnr_rest_menyu_details.setOnClickListener(this);
        lnr_bookatable.setOnClickListener(this);
        lnr_view_rating.setOnClickListener(this);
        img_restmenu_info.setOnClickListener(this);
        img_discount_btn.setOnClickListener(this);

      /*  homeDelAvailable = getIntent().getStringExtra("HMDLVRYAVAIL");
        pickUpAvailable = getIntent().getStringExtra("PCKAVAILABLE");
        dineInAvailable = getIntent().getStringExtra("DINAVAILABLE");
        restDeliveryAvail = getIntent().getStringExtra("RESDELORAVAIL");*/

        //Log.e("DATA=",homeDelAvailable+"    "+pickUpAvailable+"    "+dineInAvailable+"     "+restDeliveryAvail);
        restourantBookLimit = getIntent().getStringExtra("TABLEBOOKINGLIMIT");
        clickRestId = getIntent().getStringExtra("RESTID");
        clickRestName = getIntent().getStringExtra("RESTName");
        RESTCOVER = getIntent().getStringExtra("RESTCOVER");
        RESTLOGO = getIntent().getStringExtra("RESTLOGO");
        getRatingValue = getIntent().getStringExtra("RATINGBARDATA");
        RESTADDRESS = getIntent().getStringExtra("RESTADDRESS");
        RESTISOPEN = getIntent().getStringExtra("RESTISOPEN");
        SIZEITEMID = getIntent().getStringExtra("SIZEITEMID");
        LOCATIONDISTANCE = getIntent().getStringExtra("LOCATIONDISTANCE");
        RESTCUSINE = getIntent().getStringExtra("RESTCUSINE");
        colorCode = getIntent().getStringExtra("color");
        table_booking = getIntent().getStringExtra("TABLEBOOKING");
        tvDistance.setText(LOCATIONDISTANCE);
        tempExtraItemidWithSizeIdd = getIntent().getStringExtra("tempExtraItemidWithSizeIdd");
        System.out.println("==== tempExtraItemidWithSizeIdd : " + tempExtraItemidWithSizeIdd);
        if (tempExtraItemidWithSizeIdd != null) {
            globTempExtraItemidWithSizeIdd = tempExtraItemidWithSizeIdd.replace("[", "");
            globTempExtraItemidWithSizeIdd = globTempExtraItemidWithSizeIdd.replace("]", "");
            System.out.println("==== globTempExtraItemidWithSizeIdd : " + globTempExtraItemidWithSizeIdd);
        }

        if (table_booking.equalsIgnoreCase("yes")) {
            lnr_bookatable.setVisibility(View.VISIBLE);
        } else {
            lnr_bookatable.setVisibility(View.GONE);
        }

        sharedPreferencesData.createNewSharedPreferences(Constants.PRICEPREFERENCE);
        sharedPreferencesData.setSharedPreferenceData(Constants.PRICEPREFERENCE, Constants.FORRESTIDCHANGE, clickRestId);

        tvCusineName.setText(RESTCUSINE);
        txt_rest_name.setText(clickRestName);
        txt_rest_name1.setText(clickRestName);
        System.out.println("=== clickRestId : " + clickRestId + " " + "clickRestName" + clickRestName + " SIZEITEMID " + SIZEITEMID);
        shop_image_place_text.setText(clickRestName);
//        tv_item_discount_cost.setText(RESTISOPEN);
        txt_rest_address.setText(RESTADDRESS);
        System.out.println("====== rating value : " + getRatingValue);
        if (!getRatingValue.equalsIgnoreCase("")) {
            ratingBar.setRating(Float.parseFloat(getRatingValue));
        }

        tv_restaurant_rating_value.setText("(" + getRatingValue + " " + ")");
        Glide.with(this).load(Uri.parse(RESTCOVER)).into(shop_img_places);
        Glide.with(this).load(Uri.parse(RESTLOGO)).into(rset_logo);

        if (RESTISOPEN.contains("open") || RESTISOPEN.contains("Offen bei")) {
//            tv_item_discount_cost.setBackgroundResource(R.drawable.circle_background);
            tv_item_discount_cost.setText(RESTISOPEN);
        } else if (RESTISOPEN.contains("Preorder") || RESTISOPEN.contains("Jetzt offen")) {
//            tv_item_discount_cost.setBackgroundResource(R.drawable.circle_back_orange);
            tv_item_discount_cost.setText(RESTISOPEN);
        } else if (RESTISOPEN.contains("closed") || RESTISOPEN.contains("Jetzt geschlossen")) {
//            tv_item_discount_cost.setBackgroundResource(R.drawable.circle_back_red);
            tv_item_discount_cost.setText(RESTISOPEN);
        }

        tv_item_discount_cost.setBackgroundColor(Color.parseColor(colorCode));

        getRestSearchDetailsData();
        getAllMenu();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        if (AddExtraActivity.cart_Item_number > 0) {
            // rl_cart.setVisibility(View.VISIBLE);
            RestaurantDetails.tvCartItemCount.setText("" + AddExtraActivity.cart_Item_number);
            RestaurantDetails.tvCartItemCount1.setText("" + AddExtraActivity.cart_Item_number);

            rl_cartt.setVisibility(View.VISIBLE);
            RestaurantDetails.tv_cart_item_countt.setText("" + AddExtraActivity.cart_Item_number);
            tvTotalItemCnt.setText("" + AddExtraActivity.cart_Item_number + " " + model.getItems());
            updateCart();
        } else {
            rl_cartt.setVisibility(View.GONE);
        }
    }

    private void getAllMenu() {
        banner_progress.setVisibility(View.VISIBLE);
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<AllCategoryResponse> observable = apiInterface.getAllList(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), clickRestId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllCategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AllCategoryResponse searchResult) {

                        Log.e("response--->", "success");
                        showProgress();
                        setCategoryInfo(searchResult.getMenuCat());
//                        setAdapterCategory(searchResult.getMenuCat());
                        banner_progress.setVisibility(View.GONE);
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

    private void setCategoryInfo(List<MenuCat> menuCat) {
        rcv_rest_details_list.setLayoutManager(new LinearLayoutManager(RestaurantDetails.this));
        SectionDetailAdapter adapter = new SectionDetailAdapter(RestaurantDetails.this, menuCat);
        rcv_rest_details_list.setAdapter(adapter);
        adapter.setClickListener(this);
        hideProgress();

        rcv_rest_details_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    int position = getCurrentItem();
                    Log.e("pos--->", "" + position);
                    //Toast.makeText(getApplicationContext(), "Scrolled called", Toast.LENGTH_LONG).show();

                    adapterCategory = new Restaurant_Details_quick(RestaurantDetails.this, listnew, RestaurantDetails.this, position);
                    quickrecycler.setAdapter(adapterCategory);
                    quickrecycler.scrollToPosition(position);

                    //Toast.makeText(getApplicationContext(),"Scroll called=",Toast.LENGTH_LONG).show();
                    if (position != 0 && oldPos != position) {
                        selectedPos = position;
//                    for (int i = 0; i < menuCat.size(); i++) {
//                        if (i == position) {
//                            selectedItem[i] = true;
//                        } else {
//                            selectedItem[i] = false;
//                        }
//                    }
                        adapterCategory.notifyDataSetChanged();
                    }

                    oldPos = position;
                }
            }
        });
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) rcv_rest_details_list.getLayoutManager()).findFirstVisibleItemPosition();
    }

    private void setCurrentItem(int position, boolean smooth) {
        if (smooth)
            rcv_rest_details_list.smoothScrollToPosition(position);
        else
            rcv_rest_details_list.scrollToPosition(position);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (AddExtraActivity.cart_Item_number > 0) {
            // rl_cart.setVisibility(View.VISIBLE);
            RestaurantDetails.tvCartItemCount.setText("" + AddExtraActivity.cart_Item_number);
            RestaurantDetails.tvCartItemCount1.setText("" + AddExtraActivity.cart_Item_number);

            rl_cartt.setVisibility(View.VISIBLE);
            RestaurantDetails.tv_cart_item_countt.setText("" + AddExtraActivity.cart_Item_number);
            tvTotalItemCnt.setText("" + AddExtraActivity.cart_Item_number + " Items");
            updateCart();
        } else {
            rl_cartt.setVisibility(View.GONE);
        }
    }

    private void updateCart() {
        try {
            Double totalPrice = 0.0;
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

                        totalPrice = totalPrice + Double.parseDouble(price);

                    } while (cursor.moveToNext());
                    //  tvTotalFoodCost.setText("+".concat(pound.concat("" + String.format("%.2f", totalPrice))));
                    //    tvTotalFoodCost.setText("+".concat(pound.concat("" +String.valueOf(totalPrice))));
                    // getDiscount();


                } else {

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
            DecimalFormat df2 = new DecimalFormat("#.##");
//            tv_TotalPrice.setText("€" + df2.format(totalPrice));
            tv_TotalPrice.setText("€" + dotToCommaClass.changeDot(String.format("%.2f", totalPrice)));

        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapterCategory(List<MenuCat> list) {

        for (int i = 0; i < list.size(); i++) {
            sectionArrayList.add(list.get(i));

            for (int j = 0; j < list.get(i).getSubItemsRecord().size(); j++) {
                sectionArrayList.add(list.get(i).getSubItemsRecord().get(j));
            }
        }
        rcv_rest_details_list.setLayoutManager(new LinearLayoutManager(RestaurantDetails.this));
        SectionAdapter adapterCategory = new SectionAdapter(RestaurantDetails.this, sectionArrayList);
        rcv_rest_details_list.setAdapter(adapterCategory);
        StickyHeaderItemDecorator decorator = new StickyHeaderItemDecorator(adapterCategory);
        decorator.attachToRecyclerView(rcv_rest_details_list);
//        adapterCategory.notifyDataSetChanged();
        hideProgress();

    /*    rcv_rest_details_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                getClickFoddMenuData(globRestId, globSelectedCatId, globcategoryImage);
            }
        });*/
    }

    private void setAdapterCategoryForQuick(List<RestaurantDetailsModel.RestaurantMencategory> list) {
//        selectedItem = new Boolean[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            if (i == 0) {
//                selectedItem[i] = true;
//            } else {
//                selectedItem[i] = false;
//            }
//        }
        listnew = new ArrayList<>();
        listnew = list;

        adapterCategory = new Restaurant_Details_quick(RestaurantDetails.this, list, RestaurantDetails.this, selectedPos);
        quickrecycler.setAdapter(adapterCategory);
        // adapterCategory.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
            case R.id.img_back1:
                //Toast.makeText(getApplicationContext(),"back Clicked",Toast.LENGTH_LONG).show();
                finish();
                break;

            case R.id.img_discount_btn:
                Intent discount = new Intent(RestaurantDetails.this, DiscountOrderActivity.class);
                discount.putExtra("clickRestId", clickRestId);
                startActivity(discount);
                break;

            case R.id.img_restmenu_info:
                Intent info = new Intent(RestaurantDetails.this, InfoRestMenuActivity.class);
                info.putExtra("clickRestId", clickRestId);
                startActivity(info);
                break;

            case R.id.lnr_view_rating:
                Intent writerating = new Intent(RestaurantDetails.this, RestMenuReviewActivity.class);
                writerating.putExtra("clickRestId", clickRestId);
                writerating.putExtra("RESTName", clickRestName);
                writerating.putExtra("RESTCOVER", RESTCOVER);
                writerating.putExtra("RESTLOGO", RESTLOGO);
                writerating.putExtra("RESTISOPEN", RESTISOPEN);
                writerating.putExtra("RATINGVAL", getRatingValue);
                writerating.putExtra("color", colorCode);

                startActivity(writerating);
                break;

            case R.id.img_view_gallery:
                Intent i = new Intent(RestaurantDetails.this, RestaurantPhotoGallery.class);
                i.putExtra("clickRestId", clickRestId);
                startActivity(i);
                break;
            case R.id.lnr_rest_menyu_details:
                Intent ii = new Intent(RestaurantDetails.this, RestaurantMenuPhotoGallery.class);
                ii.putExtra("clickRestId", clickRestId);
                startActivity(ii);
                break;

            case R.id.lnr_bookatable:
                if (prefsHelper.isLoggedIn()) {
                    Intent booktable = new Intent(RestaurantDetails.this, RestaurantBookTable.class);
                    booktable.putExtra("clickRestId", clickRestId);
                    booktable.putExtra("RESTBOOKLIMIT", restourantBookLimit);
                    startActivity(booktable);
                } else {
                    Intent booktable = new Intent(RestaurantDetails.this, SignInActivity.class);
                    booktable.putExtra("clickRestId", clickRestId);
                    booktable.putExtra("RESTBOOKLIMIT", restourantBookLimit);
                    booktable.putExtra("from", "table");
                    startActivity(booktable);
                }

                break;

            case R.id.cart_count_layout:
                SQLiteDatabase db = database.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM item_table", null);
                //    Cursor cursor1 = db.rawQuery("SELECT * FROM deal_item_table", null);
                if (cursor.moveToNext()) {
                    Intent intent = new Intent(RestaurantDetails.this, CartActivity.class);
                    intent.putExtra("RESTID", clickRestId);
                    intent.putExtra("RESTName", clickRestName);
                    intent.putExtra("RESTLOGO", RESTLOGO);
                    intent.putExtra("subPizzaItemId", "" + subPizzaItemId);
                    intent.putExtra("SIZEITEMID", "" + SIZEITEMID);
                    intent.putExtra("globTempExtraItemidWithSizeIdd", "" + globTempExtraItemidWithSizeIdd);
                    startActivity(intent);
                } else {
                    /// showCustomDialog1decline("Cart is empty,please add item in cart.");
                }
                break;
            case R.id.rl_cart:
                SQLiteDatabase dbb = database.getReadableDatabase();
                Cursor cursorr = dbb.rawQuery("SELECT * FROM item_table", null);
                //    Cursor cursor1 = db.rawQuery("SELECT * FROM deal_item_table", null);
                if (cursorr.moveToNext()) {
                    Intent intent = new Intent(RestaurantDetails.this, CartActivity.class);
                    intent.putExtra("RESTID", clickRestId);
                    intent.putExtra("RESTName", clickRestName);
                    intent.putExtra("RESTLOGO", RESTLOGO);
                    intent.putExtra("subPizzaItemId", "" + subPizzaItemId);
                    intent.putExtra("SIZEITEMID", "" + SIZEITEMID);
                    intent.putExtra("globTempExtraItemidWithSizeIdd", "" + globTempExtraItemidWithSizeIdd);
                    startActivity(intent);
                } else {
                    /// showCustomDialog1decline("Cart is empty,please add item in cart.");
                }
                break;
            default:
                break;
        }
    }

    private void getRestSearchDetailsData() {

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<RestaurantDetailsModel> observable = apiInterface.getSearchRestDetailsData(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), clickRestId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestaurantDetailsModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RestaurantDetailsModel searchResult) {

                        Log.e("response--->", "success-->");
                        showProgress();
                        //  setAdapterCategory(searchResult.getRestaurantMencategory());
                        if (searchResult.getRestaurantMencategory().size() > 0)
                            if (searchResult.getRestaurantMencategory().get(0).getError() == 1) {
                                Toast.makeText(RestaurantDetails.this, searchResult.getRestaurantMencategory().get(0).getError_msg(), Toast.LENGTH_SHORT).show();
                                hideProgress();
                            } else {
                                setAdapterCategoryForQuick(searchResult.getRestaurantMencategory());
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

    public void showProgress() {
        try {
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void hideProgress() {
        try {
            progressDialog.dismiss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void getRestaaurantQuickClickData(int position, String restId, int selectedCatId, String categoryImage, String name) {
        System.out.println("===== slected cat id : " + selectedCatId + "restId : " + restId + "categoryImage " + categoryImage);
        tvName.setText(name);
        globRestId = restId;
        globSelectedCatId = selectedCatId;
        globcategoryImage = categoryImage;
//        getClickFoddMenuData(restId, selectedCatId, categoryImage);
        if (!globcategoryImage.equalsIgnoreCase("")) {
            Glide.with(this).load(Uri.parse(globcategoryImage)).into(shop_img_places_cat);
        }
        rcv_rest_details_list.scrollToPosition(position);
    }

    private void getClickFoddMenuData(String restId, int catid, String categoryImage) {
        banner_progress.setVisibility(View.VISIBLE);
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<RestDetailClickFoodModel> observable = apiInterface.getClockFoodMenu(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), restId, catid);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestDetailClickFoodModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RestDetailClickFoodModel searchResult) {
                        showProgress();
//                        setAdapterCategory(searchResult.getMenuCat());
                        banner_progress.setVisibility(View.GONE);
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

    @Override
    public void getClickMenuDataRemoved(int itemId, String itemName, String amt, int qty) {
        System.out.println("===== item name : " + itemId);
        subPizzaItemId = itemId;
        System.out.println("===== subPizzaItemId name : " + subPizzaItemId);
//        prefsHelper.savePref(Constants.Rest_DETAIL_CLICK_ITEM_ID, itemId);
//        prefsHelper.savePref(Constants.Rest_DETAIL_CLICK_ITEM_NAME, itemName);
        itemCount.remove(itemName);

        RestaurantDetails.tvCartItemCount.setText("" + itemCount.size());
        RestaurantDetails.tvCartItemCount1.setText("" + itemCount.size());

        rl_cartt.setVisibility(View.VISIBLE);
        RestaurantDetails.tv_cart_item_countt.setText("" + itemCount.size());

        int qunt = 0;
        double price = 0.0;
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM item_table where item_id='" + subPizzaItemId + "'", null);
        if (cursor.moveToNext()) {
            qunt = Integer.parseInt(cursor.getString(7));
            price = Double.parseDouble(amt);
//            price = price / qunt;
//            price = (double) Math.round(price * 100) / 100;
            qunt = qunt - 1;
            price = price * qunt;

        }
        if (qunt > 0) {
            database.update_item(String.valueOf(subPizzaItemId), qunt, price);
        } else {
            database.delete_Item(String.valueOf(itemId));
            if (AddExtraActivity.cart_Item_number > 0) {
                AddExtraActivity.cart_Item_number = AddExtraActivity.cart_Item_number - 1;
            }
            onPostResume();
        }
        tvTotalItemCnt.setText("" + AddExtraActivity.cart_Item_number + " Items");
        database.close();
        updateCart();
    }

    @Override
    public void getClickMenuData(int itemId, String itemName, String amt, String subcatItemDetails) {
        System.out.println("===== item name : " + itemId);
        subPizzaItemId = itemId;
        System.out.println("===== subPizzaItemId name : " + subPizzaItemId);
        prefsHelper.savePref(Constants.Rest_DETAIL_CLICK_ITEM_ID, itemId);
        prefsHelper.savePref(Constants.Rest_DETAIL_CLICK_ITEM_NAME, itemName);
        itemCount.add(itemName);

        RestaurantDetails.tvCartItemCount.setText("" + itemCount.size());
        RestaurantDetails.tvCartItemCount1.setText("" + itemCount.size());

        rl_cartt.setVisibility(View.VISIBLE);
        RestaurantDetails.tv_cart_item_countt.setText("" + itemCount.size());

        int qunt = 0;
        double price = 0.0;
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM item_table where item_id='" + subPizzaItemId + "'", null);
        if (cursor.moveToNext()) {
            qunt = Integer.parseInt(cursor.getString(7));
            price = Double.parseDouble(amt);
//            price = price / qunt;
//            price = (double) Math.round(pQArice * 100) / 100;
            qunt = qunt + 1;
            price = price * qunt;
            database.update_item(String.valueOf(subPizzaItemId), qunt, price);
        } else {
            database.InsertItem(String.valueOf(itemId), itemName, "0", "0", "0", "0", Double.parseDouble(amt), 1, subcatItemDetails);
            AddExtraActivity.cart_Item_number = AddExtraActivity.cart_Item_number + 1;
        }
        tvTotalItemCnt.setText("" + AddExtraActivity.cart_Item_number + " Items");

        database.close();
        updateCart();
    }

    @OnClick(R.id.img_restmenu_info)
    public void onClickInfo() {
        Intent intent = new Intent(RestaurantDetails.this, InfoRestMenuActivity.class);
        intent.putExtra("RESTID", clickRestId);
        startActivity(intent);
    }

    @OnClick(R.id.img_restmenu_info)
    public void onClick() {
    }

   /* @OnClick(R.id.cardViewAdd)
    public void cardViewAddClicked(View view){
        Toast.makeText(getApplicationContext(),"Test test",Toast.LENGTH_LONG).show();
    }*/

    @OnClick(R.id.tv_cart_item_count1)
    public void cart_count_layout1Clicked(View view) {
        //Toast.makeText(getApplicationContext(),"Called From Cart",Toast.LENGTH_LONG).show();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM item_table", null);
        //    Cursor cursor1 = db.rawQuery("SELECT * FROM deal_item_table", null);
        if (cursor.moveToNext()) {
            Intent intent = new Intent(RestaurantDetails.this, CartActivity.class);
            intent.putExtra("RESTID", clickRestId);
            intent.putExtra("RESTName", clickRestName);
            intent.putExtra("RESTLOGO", RESTLOGO);
            intent.putExtra("subPizzaItemId", "" + subPizzaItemId);
            intent.putExtra("SIZEITEMID", "" + SIZEITEMID);
            intent.putExtra("globTempExtraItemidWithSizeIdd", "" + globTempExtraItemidWithSizeIdd);
            startActivity(intent);
        } else {
            /// showCustomDialog1decline("Cart is empty,please add item in cart.");
        }
    }

    public class SectionAdapter extends StickyAdapter<RecyclerView.ViewHolder, RecyclerView.ViewHolder> {

        private static final int LAYOUT_HEADER = 0;
        private static final int LAYOUT_CHILD = 1;
        private ArrayList<Section> sectionArrayList;

        public SectionAdapter(Context context, ArrayList<Section> sectionArrayList) {

            // inflater = LayoutInflater.from(context);
            // this.context = context;
            this.sectionArrayList = sectionArrayList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            if (viewType == LAYOUT_HEADER) {
                return new HeaderViewholder(inflater.inflate(R.layout.item_header, parent, false));
            } else {
                return new ItemViewHolder(inflater.inflate(R.layout.item_restaurant, parent, false));
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (sectionArrayList.get(position).isHeader()) {
                ((HeaderViewholder) holder).textView.setText(sectionArrayList.get(position).getName());
            } else {
                ((ItemViewHolder) holder).textView.setText(sectionArrayList.get(position).getName());
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (sectionArrayList.get(position).isHeader()) {
                return LAYOUT_HEADER;
            } else
                return LAYOUT_CHILD;
        }

        @Override
        public int getItemCount() {
            return sectionArrayList.size();
        }

        @Override
        public int getHeaderPositionForItem(int itemPosition) {
            Log.d("seeee", "" + itemPosition + "......" + sectionArrayList.get(itemPosition).sectionPosition());
            return sectionArrayList.get(itemPosition).sectionPosition();
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int headerPosition) {
            ((HeaderViewholder) holder).textView.setText(sectionArrayList.get(headerPosition).getName());
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            return createViewHolder(parent, LAYOUT_HEADER);
        }

        public class HeaderViewholder extends RecyclerView.ViewHolder {
            TextView textView;

            HeaderViewholder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tvName);
            }
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            ItemViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tvName);
            }
        }
    }

//    private class RestaurantMenuDetailsHeader extends RestaurantMenuDetailsAdapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
//
//        private List<MenuCat> listCategory;
//        private List<SubItemsRecord> listFilterSubCategory;
//        private Context mContext;
//        private RestaurantDetailsAdapter.RestaurantDetailsAdapterInterface restaurantDetailsAdapterInterface;
//        private String mainClickRestId, mainClickRestName;
//        private PrefsHelper prefsHelper;
//        private String pizzaglobcategoryImage = "";
//
//        public RestaurantMenuDetailsHeader(Context context, List<MenuCat> listSubCategory) {
//            this.mContext = context;
//            this.listCategory = listSubCategory;
////            this.listFilterSubCategory = listFilterSubCategory;
////            this.mainClickRestId = maniRestClickId;
////            this.mainClickRestName = mainRestClickName;
//            prefsHelper = new PrefsHelper(mContext);
////            this.pizzaglobcategoryImage = globcategoryImage;
//
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
//            return new RecyclerView.ViewHolder(view) {
//            };
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            TextView textView = (TextView) holder.itemView;
//            textView.setText(getItem(position));
//        }
//
//        @Override
//        public long getHeaderId(int position) {
//            if (position == 0) {
//                return -1;
//            } else {
//                return getItem(position).charAt(0);
//            }
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
//            return new RecyclerView.ViewHolder(view) {
//            };
//        }
//
//        @Override
//        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
//            TextView textView = (TextView) holder.itemView;
//            textView.setText(listCategory.get(position).getCategoryName());
//            holder.itemView.setBackgroundColor(getRandomColor());
//        }
//
//        private int getRandomColor() {
//            SecureRandom rgen = new SecureRandom();
//            return Color.HSVToColor(150, new float[]{
//                    rgen.nextInt(359), 1, 1
//            });
//        }
//
//    }
}
