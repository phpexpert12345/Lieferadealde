package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.MYOrderTrackDetailModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;

import java.util.Currency;

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
    private String strordernumber = "", from = "";
    private LanguageResponse model;
    @BindView(R.id.tvQuantityMenu)
    TextView tvQuantityMenu;
    private String currencySymbol;
    DotToCommaClass dotToCommaClass;

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
            txt_view_sunmenu = findViewById(R.id.txt_view_sunmenu);
            txt_pizza_section_cuisine = findViewById(R.id.txt_pizza_section_cuisine);
            txt_view_sub_menu = findViewById(R.id.txt_view_sub_menu);
            txt_view_sub_menu_one = findViewById(R.id.txt_view_sub_menu_one);
            txt_sub_sub_menu_cuisine = findViewById(R.id.txt_sub_sub_menu_cuisine);
            txt_btn_track = findViewById(R.id.txt_btn_track);
            tvSubTotal = findViewById(R.id.tvSubTotal);
            tvTotal = findViewById(R.id.tvTotal);
            txt_vie_menu = findViewById(R.id.txt_vie_menu);

            tvMyOrder.setText(model.getMyOrder());
            tvSubTotal.setText(model.getSubtotal());
            tvTotal.setText(model.getTotal());
            txt_vie_menu.setText(model.getMenu());
            txt_btn_track.setText(model.getTrack());

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
                finish();
                break;

            case R.id.txt_btn_track:
                Intent i = new Intent(MyOrderDetailsActivity.this, OrderTrackActivity.class);
                i.putExtra("orderid", strordernumber);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    private String currency, menuprice, itemname, itemSize;
    private long quantity;

    private void getOrderDetails() {

        apiInterface = RFClient.getClient().create(ApiInterface.class);

        Observable<MYOrderTrackDetailModel> observable = apiInterface.getMyOrderDetailsDisplay(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), strordernumber);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MYOrderTrackDetailModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MYOrderTrackDetailModel searchResult) {
                        // showProgress();
                       /* setAdapterCategory(searchResult.getOrders().getOrderViewResult());
                        banner_progress.setVisibility(View.GONE);*/
                        String orderno = searchResult.getOrderDetailItem().get(0).getOrderIdentifyno();
                        String orderstatusmsg = searchResult.getOrderDetailItem().get(0).getOrderStatusMsg();
                        String subtotal = searchResult.getOrderDetailItem().get(0).getSubTotal().toString();
                        String orderpricetotal = searchResult.getOrderDetailItem().get(0).getOrderPrice();
                        String restname = searchResult.getOrderDetailItem().get(0).getRestaurantName();

                        currency = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getCurrency();
                        menuprice = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getMenuprice();
                        itemname = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getItemsName();
                        quantity = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getQuantity();
                        itemSize = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getItemSize();

                        /*for (int i = 0; i<searchResult.getOrderFoodItem().size(); i++)
                        {
                             currency = searchResult.getOrderFoodItem().get(i).getCurrency().toString();
                             menuprice = searchResult.getOrderFoodItem().get(i).getMenuprice().toString();
                             itemname = searchResult.getOrderFoodItem().get(i).getItemsName().toString();
                             quantity = searchResult.getOrderFoodItem().get(i).getQuantity();
                        }
*/
//                        if (getIntent() != null) {
//                            from = getIntent().getStringExtra("from");
//                            if (from != null && from.equalsIgnoreCase("track")) {
//                                Intent i = new Intent(MyOrderDetailsActivity.this, OrderTrackActivity.class);
//                                i.putExtra("orderid", strordernumber);
//                                startActivity(i);
//                            }
//                        }
                        setTextData(orderno, orderstatusmsg, subtotal, orderpricetotal, restname, currency, menuprice, itemname, quantity, itemSize);
                        //  String orderpricetotal = searchResult.getOrderPrice().toString();

                    }

                    @Override
                    public void onError(Throwable e) {
                        // hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });

    }

    public void showProgress() {
        progressDialog.setMessage(model.getPlease_wait_text().trim()+"...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }


    private void setTextData(String orderno, String orderstatusmsg, String subtotal, String orderpricetotal, String restname, String currency, String menuprice, String itemname, long quantity, String itemSize) {
        txt_view_ordernumber.setText(model.getOrderID() + " : " + " " + orderno);
        txt_view_orderstatus.setText(orderstatusmsg);
        txt_view_sub_total_price.setText(currencySymbol + dotToCommaClass.changeDot(subtotal));
        txt_view_total_price.setText(currencySymbol + dotToCommaClass.changeDot(orderpricetotal));
        txt_view_sunmenu.setText(itemname);
        txt_pizza_section_cuisine.setText(itemSize);
        tvQuantityMenu.setText(currencySymbol + dotToCommaClass.changeDot(menuprice) + "×" + quantity);


    }
}
