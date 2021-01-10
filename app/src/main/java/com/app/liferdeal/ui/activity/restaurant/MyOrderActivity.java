package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
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

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private PrefsHelper prefsHelper;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private ImageView img_back;
    private RecyclerView rcv_rest_list;
    private ProgressBar banner_progress;
    private String customerId = "";
    private TextView txt_view_for_no_data, tvHead;
    private LanguageResponse model=new LanguageResponse();

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
            rcv_rest_list = findViewById(R.id.rcv_rest_list);
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
        progressDialog.setMessage("Please wait...");
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

}
