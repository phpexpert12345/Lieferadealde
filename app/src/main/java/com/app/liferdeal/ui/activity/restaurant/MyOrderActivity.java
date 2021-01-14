package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.interfaces.CancelClicked;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.OrderCancelModel;
import com.app.liferdeal.model.restaurant.MyOrderModel;
import com.app.liferdeal.model.restaurant.Orders;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.adapters.MyOrderAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener, CancelClicked {
    private PrefsHelper prefsHelper;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private ImageView img_back;
    /* private RecyclerView rcv_rest_list;*/
    private ProgressBar banner_progress;
    private static String customerId = "";
    private TextView txt_view_for_no_data, tvHead;
    private LanguageResponse model = new LanguageResponse();

   /* @BindView(R.id.cart_count_layout)
    RelativeLayout cart_count_layout;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_activity);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(MyOrderActivity.this) != null) {
            model = App.retrieveLangFromGson(MyOrderActivity.this);
        }
        init();
    }

    private void init() {
        try {
            prefsHelper = new PrefsHelper(this);
            progressDialog = new ProgressDialog(this);
            img_back = findViewById(R.id.img_back);
            /*  rcv_rest_list = findViewById(R.id.rcv_rest_list);*/
            banner_progress = findViewById(R.id.banner_progress);
            txt_view_for_no_data = findViewById(R.id.txt_view_for_no_data);
            tvHead = findViewById(R.id.tvHead);

            tvHead.setText(model.getMyOrder().trim());

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            rcv_rest_list.setLayoutManager(mLayoutManager);
            rcv_rest_list.setItemAnimator(new DefaultItemAnimator());
            // banner_progress.setVisibility(View.VISIBLE);

            customerId = prefsHelper.getPref(Constants.CUSTOMER_ID);
            img_back.setOnClickListener(this);
            getRestSearchInfo();
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
            default:
                break;
        }
    }

    private void getRestSearchInfo() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<MyOrderModel> observable = apiInterface.getMyOrderDetails(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), customerId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyOrderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyOrderModel searchResult) {
                        // showProgress();
                        if (searchResult.getOrders() != null) {
                            setAdapterCategory(searchResult.getOrders().getOrderViewResult());
                            banner_progress.setVisibility(View.GONE);
                        } else {
                            txt_view_for_no_data.setVisibility(View.VISIBLE);
                            txt_view_for_no_data.setText(model.getNOORDERYET());
                            banner_progress.setVisibility(View.GONE);
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
        progressDialog.setMessage("Please wait" + "...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    private void setAdapterCategory(List<Orders.OrderViewResult> list) {
        MyOrderAdapter adapterCategory = new MyOrderAdapter(this, list);
        rcv_rest_list.setAdapter(adapterCategory);
        // hideProgress();
    }

    @Override
    public void cancleButton(String orderId, Context context) {
        Log.e("Called=", orderId);
        setRestaurantCancel(orderId, context);
//        showCancelDialog(orderId, context);

    }
    public void showCancelDialog(String orderId,Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(MyOrderActivity.this);
        builder.setMessage(model.getAre_you_sure());
        builder.setPositiveButton(model.getOKText(),(dialog, which) -> {
            dialog.dismiss();
            setRestaurantCancel(orderId, MyOrderActivity.this);
        }).setNegativeButton(model.getCancel(),(dialog, which) -> {
            dialog.dismiss();
        }).create().show();
    }

    private void setRestaurantCancel(String orderId, Context context) {
        prefsHelper = new PrefsHelper(context);
        apiInterface = RFClient.getClient().create(ApiInterface.class);
//        showProgress();
        Observable<OrderCancelModel> observable = apiInterface.setOrderCancel(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), orderId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderCancelModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderCancelModel searchResult) {
//                        hideProgress();
                        if(searchResult.getErrorMsg()!=null) {
                            Toast.makeText(context, searchResult.getErrorMsg().toString(), Toast.LENGTH_LONG).show();
                        }
                        //getRestSearchInfoInside(context);
                        getRestSearchInfo();

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

    @BindView(R.id.rcv_rest_list)
    RecyclerView rcv_rest_list;

    private void setAdapterCategory1(List<Orders.OrderViewResult> list, Context context) {
        //MyOrderActivity myOrderActivity=new MyOrderActivity();
        MyOrderAdapter adapterCategory = new MyOrderAdapter(context, list);
        rcv_rest_list = findViewById(R.id.rcv_rest_list);
        rcv_rest_list.setAdapter(adapterCategory);
        // hideProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
