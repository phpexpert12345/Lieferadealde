package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.RestaurantDetailsModel;
import com.app.liferdeal.model.restaurant.TimeListModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.cart.CartCheckout;
import com.app.liferdeal.ui.adapters.Restaurant_Details_quick;
import com.app.liferdeal.ui.adapters.TimeSlotAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TimeListActivity extends AppCompatActivity implements View.OnClickListener, TimeSlotAdapter.TimeSlotAdapterInterface {

    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private ImageView img_back;
    private RecyclerView recv_time_slot;
    private String restId = "", orderType;
    private ProgressBar banner_progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_time_slot_activity);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        prefsHelper = new PrefsHelper(this);
        img_back = findViewById(R.id.img_back);
        recv_time_slot = findViewById(R.id.recv_time_slot);
        banner_progress = findViewById(R.id.banner_progress);

        restId = getIntent().getStringExtra("RestId");
        orderType = getIntent().getStringExtra("OrderType");
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(TimeListActivity.this, 3);
        recv_time_slot.setLayoutManager(mLayoutManager);
        recv_time_slot.setItemAnimator(new DefaultItemAnimator());
        recv_time_slot.addItemDecoration(new SpacesItemDecoration(10));
        recv_time_slot.setHasFixedSize(true);

        img_back.setOnClickListener(this);

        getTimeSlotData();
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

    private void getTimeSlotData() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<TimeListModel> observable = apiInterface.getTimeListData(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE),
                restId, orderType, formattedDate);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TimeListModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TimeListModel searchResult) {
                showProgress();
                if (searchResult.getTimeList() != null && searchResult.getTimeList().size() > 0) {
                    setAdapterCategoryForQuick(searchResult.getTimeList());
                    banner_progress.setVisibility(View.GONE);
                }
                hideProgress();
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

    private void setAdapterCategoryForQuick(List<TimeListModel.TimeList> list) {
        TimeSlotAdapter adapterCategory = new TimeSlotAdapter(TimeListActivity.this, list, TimeListActivity.this);
        recv_time_slot.setAdapter(adapterCategory);
        // adapterCategory.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void getClickTimeSlot(String gettime) {
        System.out.println("==== get click time : " + gettime);
        /*Intent i = new Intent(TimeListActivity.this, CartCheckout.class);
        startActivity(i);*/
        Intent returnIntent = new Intent();
        returnIntent.putExtra("gettime", gettime);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
