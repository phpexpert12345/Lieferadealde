package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.liferdeal.R;
import com.app.liferdeal.adapter.DetailOrderAdapter;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.OrderFoodItem;
import com.app.liferdeal.model.menuitems.OrderComboItemExtras;
import com.app.liferdeal.model.menuitems.OrderComboItemOptions;
import com.app.liferdeal.model.menuitems.OrderMealItems;
import com.app.liferdeal.model.restaurant.MYOrderTrackDetailModel;
import com.app.liferdeal.model.restaurant.OrderDetailItem;
import com.app.liferdeal.model.restaurant.Orders;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.adapters.MyOrderAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_view_ordernumber, txt_view_orderstatus, txt_view_sub_total_price, txt_view_total_price, txt_view_sunmenu, tvSubTotal, tvTotal,
            txt_pizza_section_cuisine, txt_view_sub_menu, txt_view_sub_menu_one, txt_sub_sub_menu_cuisine, txt_btn_track, tvMyOrder, txt_vie_menu;

    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private ImageView img_back;
    private String strordernumber = "";
    private LanguageResponse model;
    //    @BindView(R.id.tvQuantityMenu)
//    TextView tvQuantityMenu;
    @BindView(R.id.rvItemList)
    RecyclerView rvItemList;
    @BindView(R.id.txt_payment_mode)
    TextView txt_payment_mode;
    @BindView(R.id.txt_payment_status)
    TextView txt_payment_status;
    @BindView(R.id.txt_restro_name)
    TextView txt_restro_name;
    @BindView(R.id.txt_restro_address)
    TextView txt_restro_address;
    private String currencySymbol;
    DotToCommaClass dotToCommaClass;
    List<OrderFoodItem> foodItems=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_details_actvity);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(MyOrderDetailsActivity.this) != null) {
            model = App.retrieveLangFromGson(MyOrderDetailsActivity.this);
        }
        init();
    }

    private void init() {
        try {
            dotToCommaClass = new DotToCommaClass(getApplicationContext());

            prefsHelper = new PrefsHelper(this);
            Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
            currencySymbol = hh.getSymbol();

            progressDialog = new ProgressDialog(this);
            img_back = findViewById(R.id.img_back);
            tvMyOrder = findViewById(R.id.tvMyOrder);
            txt_view_ordernumber = findViewById(R.id.txt_view_ordernumber);
            txt_view_orderstatus = findViewById(R.id.txt_view_orderstatus);
            txt_view_sub_total_price = findViewById(R.id.txt_view_sub_total_price);
            txt_view_total_price = findViewById(R.id.txt_view_total_price);
//            txt_view_sunmenu = findViewById(R.id.txt_view_sunmenu);
//            txt_pizza_section_cuisine = findViewById(R.id.txt_pizza_section_cuisine);
            txt_view_sub_menu = findViewById(R.id.txt_view_sub_menu);
            txt_view_sub_menu_one = findViewById(R.id.txt_view_sub_menu_one);
            txt_sub_sub_menu_cuisine = findViewById(R.id.txt_sub_sub_menu_cuisine);
            txt_btn_track = findViewById(R.id.txt_btn_track);
            tvSubTotal = findViewById(R.id.tvSubTotal);
            tvTotal = findViewById(R.id.tvTotal);
            txt_vie_menu = findViewById(R.id.txt_vie_menu);

            tvMyOrder.setText(model.getOrderDetails());
            tvSubTotal.setText(model.getSubtotal());
            tvTotal.setText(model.getTotal());
            txt_vie_menu.setText(model.getMenu());
            txt_btn_track.setText(model.getTrack());
            txt_payment_mode.setText(model.getPayment());

            strordernumber = getIntent().getStringExtra("orderid");
            Log.e("OrderId=", strordernumber);
            img_back.setOnClickListener(this);
            txt_btn_track.setOnClickListener(this);
            getOrderDetails();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                hideProgress();
                finish();
                break;
            case R.id.txt_btn_track:
                Intent i = new Intent(MyOrderDetailsActivity.this, OrderTrackActivity.class);
                i.putExtra("orderid", strordernumber);
                i.putParcelableArrayListExtra("food_items", (ArrayList<? extends Parcelable>) foodItems);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    private String currency, menuprice, itemname, itemSize,extraTopping;
    private long quantity;

    private void getOrderDetails() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        String url=Constants.Url.BASE_URL+"phpexpert_Order_DetailsDisplay.php?api_key="+prefsHelper.getPref(Constants.API_KEY)+"&lang_code="+ prefsHelper.getPref(Constants.LNG_CODE)+"&order_identifyno="+strordernumber;
        Log.i("url",url);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Type type= new TypeToken<MYOrderTrackDetailModel>(){}.getType();
                MYOrderTrackDetailModel searchResult=gson.fromJson(response,type);

                if(foodItems.size()>0){
                    foodItems.clear();
                }
                if (searchResult.getOrderDetailItem() != null) {
                    foodItems=searchResult.getOrderDetailItem().get(0).getOrderFoodItem();
                            if(searchResult.getOrderDetailItem().get(0).getOrderMealItem().size()>0){
                                for(OrderMealItems orderMealItems:searchResult.getOrderDetailItem().get(0).getOrderMealItem()){
                                    OrderFoodItem orderFoodItem=new OrderFoodItem();
                                    orderFoodItem.setItemsName(orderMealItems.getItemsName());
                                    orderFoodItem.setDesc(orderMealItems.getItemsDescriptionName());
                                    orderFoodItem.setQuantity((long) orderMealItems.getQuqntity());
                                    orderFoodItem.setMenuprice(orderMealItems.getMenuprice());
                                    StringBuilder sizes=new StringBuilder();
                                    StringBuilder tops=new StringBuilder();
                                    if(orderMealItems.getOrderComboItemOption().size()>0){
                                        for(OrderComboItemOptions orderComboItemOptions:orderMealItems.getOrderComboItemOption()){
                                            if(sizes.length()>0){
                                                sizes.append(",");
                                            }
                                            if(tops.length()>0){
                                                tops.append(",");
                                            }
                                            sizes.append(orderComboItemOptions.getComboOptionName());
                                            sizes.append("_");
                                            sizes.append(orderComboItemOptions.getComboOptionItemName());
                                            sizes.append(orderComboItemOptions.getComboOptionItemSizeName());
                                            if(orderComboItemOptions.getOrderComboItemExtra().size()>0){
                                                for(OrderComboItemExtras orderComboItemExtras:orderComboItemOptions.getOrderComboItemExtra()){
                                                    if(tops.length()>0){
                                                        tops.append("_");
                                                    }
                                                    tops.append(orderComboItemExtras.getComboExtraItemName());

                                                }
                                            }


                                        }
                                    }
                                    orderFoodItem.setItemSize(sizes.toString());
                                    orderFoodItem.setExtraTopping(tops.toString());
                                    orderFoodItem.setIs_com(1);
                                    foodItems.add(orderFoodItem);

                                }
                            }
                    setAdapterCategory();

//                        banner_progress.setVisibility(View.GONE);
                    String orderno = searchResult.getOrderDetailItem().get(0).getOrderIdentifyno();
                    String orderstatusmsg = searchResult.getOrderDetailItem().get(0).getStatus();
                    String subtotal = searchResult.getOrderDetailItem().get(0).getSubTotal();
                    String orderpricetotal = searchResult.getOrderDetailItem().get(0).getOrderPrice();
                    String restname = searchResult.getOrderDetailItem().get(0).getRestaurantName();
                    String payment_mode=searchResult.getOrderDetailItem().get(0).getPaymentMethod();
                    String restro_address=searchResult.getOrderDetailItem().get(0).getRestaurantAddress();
                    txt_payment_status.setText(payment_mode);

                    currency = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getCurrency();
                    menuprice = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getMenuprice();
                    itemname = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getItemsName();
                    quantity = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getQuantity();
                    itemSize = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getItemSize();
                    extraTopping=searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getExtraTopping();
                    String statusColor_order=searchResult.getOrderDetailItem().get(0).getOrderStatusColorCode();
                    if(statusColor_order!=null){
                        txt_view_orderstatus.setTextColor(Color.parseColor(statusColor_order));
                    }


//                        if (getIntent() != null) {
//                            from = getIntent().getStringExtra("from");
//                            if (from != null && from.equalsIgnoreCase("track")) {
//                                Intent i = new Intent(MyOrderDetailsActivity.this, OrderTrackActivity.class);
//                                i.putExtra("orderid", strordernumber);
//                                startActivity(i);
//                            }
//                        }
                    setTextData(orderno, orderstatusmsg, subtotal, orderpricetotal, restname, restro_address, menuprice, itemname, quantity, itemSize);
                    //  String orderpricetotal = searchResult.getOrderPrice().toString();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
Log.i("url",error.getMessage());
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);
//        Observable<MYOrderTrackDetailModel> observable = apiInterface.getMyOrderDetailsDisplay(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), strordernumber);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<MYOrderTrackDetailModel>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(MYOrderTrackDetailModel searchResult) {
//                        // showProgress();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // hideProgress();
//                        Log.d("TAG", "log...." + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        //   activity.mySharePreferences.setBundle("login");
//
//                    }
//                });
    }

    private void setAdapterCategory() {
        rvItemList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DetailOrderAdapter adapterCategory = new DetailOrderAdapter(this, foodItems);
        rvItemList.setAdapter(adapterCategory);
        // hideProgress();
    }

    public void showProgress() {
        progressDialog.setMessage(model.getPlease_wait_text().trim() + "...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void setTextData(String orderno, String orderstatusmsg, String subtotal, String orderpricetotal, String restname, String currency, String menuprice, String itemname, long quantity, String itemSize) {
        txt_view_ordernumber.setText(model.getOrderID() + " : " + " " + orderno);
        txt_view_orderstatus.setText(orderstatusmsg);
        txt_view_sub_total_price.setText(currencySymbol + dotToCommaClass.changeDot(subtotal));
        txt_view_total_price.setText(currencySymbol + dotToCommaClass.changeDot(orderpricetotal));
        txt_restro_name.setText(restname);
        txt_restro_address.setText(currency);
//        txt_view_sunmenu.setText(itemname);
//        txt_pizza_section_cuisine.setText(itemSize);
//        tvQuantityMenu.setText(currencySymbol + dotToCommaClass.changeDot(menuprice) + "×" + quantity);
    }
}
