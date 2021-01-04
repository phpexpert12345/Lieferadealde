package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.DiscountModel;
import com.app.liferdeal.model.restaurant.RestMenuReviewModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.adapters.DiscountOrderAdapter;
import com.app.liferdeal.ui.adapters.RestReviewOrderAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestMenuReviewActivity extends AppCompatActivity implements View.OnClickListener {
    private PrefsHelper prefsHelper;
    private ImageView img_back, shop_img_places, rset_logo, shop_img_places_cat, img_restmenu_info, img_discount_btn;
    private RecyclerView rcv_rest_details_list, quickrecycler;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private ProgressBar banner_progress;
    private RatingBar ratingBar;
    private TextView shop_image_place_text, tv_item_discount_cost, txt_view_for_no_data, tv_restaurant_rating_value, txt_rest_name;
    private String customerId = "", restId = "", clickRestName = "", RESTCOVER = "", RESTLOGO = "", RESTADDRESS = "", RESTISOPEN = "", ratingValue = "";

    private LanguageResponse model = new LanguageResponse();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_menu_review_activity);
        if (App.retrieveLangFromGson(RestMenuReviewActivity.this) != null) {
            model = App.retrieveLangFromGson(RestMenuReviewActivity.this);
        }
        init();
    }

    private void init() {
        try {
            prefsHelper = new PrefsHelper(this);
            progressDialog = new ProgressDialog(this);
            img_back = findViewById(R.id.img_back);
            rcv_rest_details_list = findViewById(R.id.rcv_rest_details_list);
            shop_img_places = findViewById(R.id.shop_img_places);
            rset_logo = findViewById(R.id.rset_logo);
            banner_progress = findViewById(R.id.banner_progress);
            shop_image_place_text = findViewById(R.id.shop_image_place_text);
            tv_item_discount_cost = findViewById(R.id.tv_item_discount_cost);
            txt_view_for_no_data = findViewById(R.id.txt_view_for_no_data);
            ratingBar = findViewById(R.id.ratingBar);
            txt_rest_name = findViewById(R.id.txt_rest_name);
            tv_restaurant_rating_value = findViewById(R.id.tv_restaurant_rating_value);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RestMenuReviewActivity.this);
            rcv_rest_details_list.setLayoutManager(mLayoutManager);
            rcv_rest_details_list.setItemAnimator(new DefaultItemAnimator());
            restId = getIntent().getStringExtra("clickRestId");
            clickRestName = getIntent().getStringExtra("RESTName");
            RESTCOVER = getIntent().getStringExtra("RESTCOVER");
            RESTLOGO = getIntent().getStringExtra("RESTLOGO");
            RESTADDRESS = getIntent().getStringExtra("RESTADDRESS");
            RESTISOPEN = getIntent().getStringExtra("RESTISOPEN");
            ratingValue = getIntent().getStringExtra("RATINGVAL");

            txt_rest_name.setText(model.getRestaurantReview());

            shop_image_place_text.setText(clickRestName);
            tv_item_discount_cost.setText(RESTISOPEN);
            if (!ratingValue.equalsIgnoreCase("")) {
                ratingBar.setRating(Float.parseFloat(ratingValue));
            }
            tv_restaurant_rating_value.setText(ratingValue);
            Glide.with(this).load(Uri.parse(RESTCOVER)).into(shop_img_places);
            Glide.with(this).load(Uri.parse(RESTLOGO)).into(rset_logo);


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

        Observable<RestMenuReviewModel> observable = apiInterface.getRestMenuReviewData(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), restId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestMenuReviewModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RestMenuReviewModel searchResult) {
                        // showProgress();
                        // banner_progress.setVisibility(View.VISIBLE);
                        if (searchResult.getReviewlistingList() != null) {
                            setAdapterCategory(searchResult.getReviewlistingList());
                            banner_progress.setVisibility(View.GONE);
                        } else {
                            txt_view_for_no_data.setVisibility(View.VISIBLE);
                            txt_view_for_no_data.setText("There is no review & rating!");
                            banner_progress.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        // hideProgress();
                        banner_progress.setVisibility(View.GONE);
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

    private void setAdapterCategory(List<RestMenuReviewModel.ReviewlistingList> list) {
        RestReviewOrderAdapter adapterCategory = new RestReviewOrderAdapter(this, list);
        rcv_rest_details_list.setAdapter(adapterCategory);
        // hideProgress();
    }
}
