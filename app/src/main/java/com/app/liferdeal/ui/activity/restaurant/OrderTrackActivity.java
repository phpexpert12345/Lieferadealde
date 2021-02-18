package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.adapter.DetailOrderAdapter;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.OrderFoodItem;
import com.app.liferdeal.model.restaurant.MYOrderTrackDetailModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderTrackActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_view_ordernumber, txt_orderdattime, txt_delivered_city, tvMyOrder, tvFoodItems, txt_vie_menu, txt_view_sub_menu,
            txt_view_sub_menu_one;

    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private ImageView img_back;
    private String strordernumber = "";
    private LanguageResponse model;

    @BindView(R.id.tvFoodName)
    TextView tvFoodName;
    @BindView(R.id.txtPrice)
    TextView txtPrice;

    @BindView(R.id.viewSquare1)
    View viewSquare1;

    @BindView(R.id.viewSquare2)
    View viewSquare2;

    @BindView(R.id.viewSquare3)
    View viewSquare3;

    @BindView(R.id.viewLine1)
    View viewLine1;

    @BindView(R.id.viewLine2)
    View viewLine2;

    @BindView(R.id.rlt_sub_menu)
    RelativeLayout rlt_sub_menu;
    @BindView(R.id.rvWriteAReview)
    RelativeLayout rvWriteAReview;


    @BindView(R.id.txtConfirmed)
    TextView txtConfirmed;
    @BindView(R.id.txtConfirmedDate)
    TextView txtConfirmedDate;
    @BindView(R.id.txtDelivered)
    TextView txtDelivered;
    @BindView(R.id.txtDeliveredDate)
    TextView txtDeliveredDate;
    @BindView(R.id.tvWrite)
    TextView tvWrite;
    @BindView(R.id.txt_payment_mode)
     TextView txt_payment_mode;
    @BindView(R.id.txt_payment_status)
    TextView txt_payment_status;
    @BindView(R.id.txt_restro_name)
    TextView txt_restro_name;
    @BindView(R.id.txt_restro_address)
    TextView txt_restro_address;
    @BindView(R.id.recycler_food_items)
    RecyclerView recycler_food_items;
    private String currencySymbol;
    List<OrderFoodItem> foodItems=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_activity_track);
        ButterKnife.bind(this);

        model = new LanguageResponse();
        if (App.retrieveLangFromGson(OrderTrackActivity.this) != null) {
            model = App.retrieveLangFromGson(OrderTrackActivity.this);
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

            txt_view_ordernumber = findViewById(R.id.txt_view_ordernumber);
            txt_orderdattime = findViewById(R.id.txt_orderdattime);
            txt_delivered_city = findViewById(R.id.txt_delivered_city);
            tvMyOrder = findViewById(R.id.tvMyOrder);
            txt_view_sub_menu_one = findViewById(R.id.txt_view_sub_menu_one);
            txt_vie_menu = findViewById(R.id.txt_vie_menu);
            tvFoodItems = findViewById(R.id.tvFoodItems);
            txt_view_sub_menu = findViewById(R.id.txt_view_sub_menu);

            tvMyOrder.setText(model.getTrack());
            txt_orderdattime.setText(model.getSubtotal());
            tvFoodItems.setText(model.getFoodItems() + "-");
            txt_vie_menu.setText(model.getDeliveredTo() + "-");
            txt_view_sub_menu.setText(model.getOrderComplete() + "-");
            tvWrite.setText(model.getWriteAReview());
            txt_payment_mode.setText(model.getPayment());

            strordernumber = getIntent().getStringExtra("orderid");
            if(getIntent().hasExtra("food_items")){
                foodItems=getIntent().getParcelableArrayListExtra("food_items");
                if(foodItems.size()>0){
                    setAdapterCategory(foodItems);
                }
            }
            img_back.setOnClickListener(this);
            // txt_btn_track.setOnClickListener(this);
            getOrderDetails();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void setAdapterCategory(List<OrderFoodItem> list) {
        if(list!=null) {
            if(list.size()>0) {
                recycler_food_items.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                DetailOrderAdapter adapterCategory = new DetailOrderAdapter(this, list);
                recycler_food_items.setAdapter(adapterCategory);
            }
        }
        // hideProgress();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;


            default:
                break;
        }
    }

    private String currency, menuprice, itemname;
    private long quantity;
    DotToCommaClass dotToCommaClass;
    String restId = "", orderNoSend = "";

    private void getOrderDetails() {

        apiInterface = RFClient.getClient().create(ApiInterface.class);

        Observable<MYOrderTrackDetailModel> observable = apiInterface.getMyOrderTrackData(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), strordernumber);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MYOrderTrackDetailModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MYOrderTrackDetailModel searchResult) {
                        if (searchResult.getOrderTrackHistory().size() > 0) {


                            if (searchResult.getOrderTrackHistory().get(0).getOrderStatus().equalsIgnoreCase("Delivered")) {
                                rvWriteAReview.setVisibility(View.VISIBLE);
                            } else {
                                rvWriteAReview.setVisibility(View.GONE);
                            }

                            restId = String.valueOf(searchResult.getOrderDetailItem().get(0).getResid());
                            orderNoSend = searchResult.getOrderDetailItem().get(0).getOrderIdentifyno();
                            String orderstatusmsg = searchResult.getOrderDetailItem().get(0).getOrderStatusMsg();
                            String subtotal = searchResult.getOrderDetailItem().get(0).getSubTotal();
                            String orderpricetotal = searchResult.getOrderDetailItem().get(0).getOrderPrice();
                            String restname = searchResult.getOrderDetailItem().get(0).getRestaurantName();
                            String resuestatdate = searchResult.getOrderDetailItem().get(0).getRequestAtDate();
                            String resuestattime = searchResult.getOrderDetailItem().get(0).getRequestAtTime();
                            String customercity = searchResult.getOrderDetailItem().get(0).getCustomerCity();
                            String payment_mode = searchResult.getOrderDetailItem().get(0).getPaymentMethod();
                            txt_payment_status.setText(payment_mode);
                            String restro_address = searchResult.getOrderDetailItem().get(0).getRestaurantAddress();
                            txt_restro_name.setText(restname);
                            txt_restro_address.setText(restro_address);
                            if (orderstatusmsg.equalsIgnoreCase("Delivered")) {
                                txt_delivered_city.setText(searchResult.getOrderDetailItem().get(0).getCustomerAddress());
                            } else {
                                txt_delivered_city.setText(restro_address);
                            }

                            String firstStatus = "", secondStatus = "", thirdStatus = "";
                            String firstDate = "", secondDate = "", thirdDate = "";
                            //Toast.makeText(getApplicationContext(),searchResult.getOrderTrackHistory().get(0).getOrderStatus(),Toast.LENGTH_LONG).show();

                            if (searchResult.getOrderTrackHistory() != null && searchResult.getOrderTrackHistory().get(0) != null) {
                                firstStatus = searchResult.getOrderTrackHistory().get(0).getOrderStatus();
                                firstDate = searchResult.getOrderTrackHistory().get(0).getOrderStatusDate() + " " + searchResult.getOrderTrackHistory().get(0).getOrderStatusTime();
                            } else {
                                firstStatus = "";
                            }
                            if (searchResult.getOrderTrackHistory() != null && searchResult.getOrderTrackHistory().size() > 1) {
                                secondStatus = searchResult.getOrderTrackHistory().get(1).getOrderStatus();
                                secondDate = searchResult.getOrderTrackHistory().get(1).getOrderStatusDate() + " " + searchResult.getOrderTrackHistory().get(1).getOrderStatusTime();
                            } else {
                                secondStatus = "";
                            }
                            if (searchResult.getOrderTrackHistory() != null && searchResult.getOrderTrackHistory().size() > 2) {
                                thirdStatus = searchResult.getOrderTrackHistory().get(2).getOrderStatus();
                                thirdDate = searchResult.getOrderTrackHistory().get(2).getOrderStatusDate() + " " + searchResult.getOrderTrackHistory().get(2).getOrderStatusTime();
                            } else {
                                thirdStatus = "";
                            }
                            // String itemsName = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getItemsName();

                     /*   currency = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getCurrency().toString();
                        menuprice = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getMenuprice().toString();
                        itemname = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getItemsName().toString();
                        quantity = searchResult.getOrderDetailItem().get(0).getOrderFoodItem().get(0).getQuantity();*/

                        /*for (int i = 0; i<searchResult.getOrderFoodItem().size(); i++)
                        {
                             currency = searchResult.getOrderFoodItem().get(i).getCurrency().toString();
                             menuprice = searchResult.getOrderFoodItem().get(i).getMenuprice().toString();
                             itemname = searchResult.getOrderFoodItem().get(i).getItemsName().toString();
                             quantity = searchResult.getOrderFoodItem().get(i).getQuantity();
                        }
*/
                            setTextData(orderNoSend, orderstatusmsg, subtotal, orderpricetotal, restname, resuestatdate, resuestattime, customercity, currency, menuprice, itemname, quantity, firstStatus, secondStatus, thirdStatus, firstDate, secondDate, thirdDate);
                            //  String orderpricetotal = searchResult.getOrderPrice().toString();

                        }
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
        progressDialog.setMessage(model.getPlease_wait_text().trim() + "...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }


    @BindView(R.id.rlConfirmed)
    RelativeLayout rlConfirmed;
    @BindView(R.id.rlDelivered)
    RelativeLayout rlDelivered;

    private void setTextData(String orderno, String orderstatusmsg, String subtotal, String orderpricetotal, String restname, String resuestatdate, String resuestattime, String customercity, String currency, String menuprice, String itemname, long quantity, String firstStatus, String secondStatus, String thirdStatus, String firstDate, String secondDate, String thirdDate) {
        txt_view_ordernumber.setText(model.getOrderID() + " : " + orderno);
        //txt_view_sub_menu_one.setText(resuestatdate + " " + resuestattime);
        txt_delivered_city.setText(customercity);
        tvFoodName.setText(itemname);
        txtPrice.setText(currencySymbol + dotToCommaClass.changeDot(subtotal));

        if (!firstStatus.equalsIgnoreCase("")) {
            txt_view_sub_menu.setText(firstStatus);
            txt_view_sub_menu_one.setText(firstDate);
            txt_orderdattime.setText(firstDate);

            rlt_sub_menu.setVisibility(View.VISIBLE);
            viewSquare1.setVisibility(View.VISIBLE);
        } else {
            rlt_sub_menu.setVisibility(View.GONE);
            viewSquare1.setVisibility(View.GONE);
        }

        if (!secondStatus.equalsIgnoreCase("")) {
            txtConfirmed.setText(secondStatus);
            txtConfirmedDate.setText(secondDate);
            rlConfirmed.setVisibility(View.VISIBLE);
            viewLine1.setVisibility(View.VISIBLE);
            viewSquare2.setVisibility(View.VISIBLE);
        } else {
            rlConfirmed.setVisibility(View.GONE);
            viewLine1.setVisibility(View.GONE);
            viewSquare2.setVisibility(View.GONE);
        }


        if (!thirdStatus.equalsIgnoreCase("")) {
            txtDelivered.setText(thirdStatus);
            txtDeliveredDate.setText(thirdDate);
            rlDelivered.setVisibility(View.VISIBLE);
            viewSquare3.setVisibility(View.VISIBLE);
        } else {
            rlDelivered.setVisibility(View.GONE);
            viewSquare3.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.rvWriteAReview)
    public void rvWriteAReviewClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), WriteAReviewActivity.class);
        intent.putExtra("clickRestId", restId);
        intent.putExtra("ORDERNO", orderNoSend);
        Log.e("RRRRID=", restId);
        startActivity(intent);
    }
}
