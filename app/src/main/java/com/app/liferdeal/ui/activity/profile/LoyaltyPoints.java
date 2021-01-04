package com.app.liferdeal.ui.activity.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.LoyalityPointsModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoyaltyPoints extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tvFreeSignUp)
    TextView tvFreeSignUp;
    @BindView(R.id.tv_loyality_des)
    TextView tvLoyalityDes;
    @BindView(R.id.tvPlaceFirstOrder)
    TextView tvPlaceFirstOrder;
    @BindView(R.id.tvPlaceOrderToGet)
    TextView tvPlaceOrderToGet;
    @BindView(R.id.tvPostingaReview)
    TextView tvPostingaReview;
    @BindView(R.id.tvPostingReviewGet)
    TextView tvPostingReviewGet;
    @BindView(R.id.tvGroupOrdering)
    TextView tvGroupOrdering;
    @BindView(R.id.tvPlaceGroupOrdering)
    TextView tvPlaceGroupOrdering;
    @BindView(R.id.tvBirthdayCelebration)
    TextView tvBirthdayCelebration;
    @BindView(R.id.tvBirthdayCelebrationPlaceOrder)
    TextView tvBirthdayCelebrationPlaceOrder;
    @BindView(R.id.tvSocialSharing)
    TextView tvSocialSharing;
    @BindView(R.id.tvSharingFb)
    TextView tvSharingFb;
    @BindView(R.id.tvSpendEarn)
    TextView tvSpendEarn;
    @BindView(R.id.tvEarnPoint)
    TextView tvEarnPoint;
    @BindView(R.id.tvPoint)
    TextView tvPoint;
    @BindView(R.id.tvPointt)
    TextView tvPointt;
    @BindView(R.id.tvPointtt)
    TextView tvPointtt;
    @BindView(R.id.tvPointtttt)
    TextView tvPointtttt;
    @BindView(R.id.tvPointttt)
    TextView tvPointttt;
    @BindView(R.id.tvPointttttt)
    TextView tvPointttttt;
    @BindView(R.id.tvPointtttttt)
    TextView tvPointtttttt;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private RecyclerView rcv_loyality_points_list;
    List<LoyalityPointsModel> listt;
    private ImageView iv_back;
    private TextView tv_free_signup_points, tv_place_fst_order_points, tv_item_points, tv_place_point_order, tv_bday_celebrate_points,
            tv_social_media_points, tv_refer_points, tv_spen_more_points, tvHead, tv_loyality_des;
    private LanguageResponse model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyaltypoints);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(LoyaltyPoints.this) != null) {
            model = App.retrieveLangFromGson(LoyaltyPoints.this);
        }
        init();
    }

    private void init() {
        try {
            prefsHelper = new PrefsHelper(this);
            progressDialog = new ProgressDialog(this);
            iv_back = findViewById(R.id.iv_back);
            tvHead = findViewById(R.id.tvHead);
            rcv_loyality_points_list = findViewById(R.id.rcv_loyality_points_list);
            tv_free_signup_points = findViewById(R.id.tv_free_signup_points);
            tv_place_fst_order_points = findViewById(R.id.tv_place_fst_order_points);
            tv_item_points = findViewById(R.id.tv_item_points);
            tv_place_point_order = findViewById(R.id.tv_place_point_order);
            tv_bday_celebrate_points = findViewById(R.id.tv_bday_celebrate_points);
            tv_social_media_points = findViewById(R.id.tv_social_media_points);
            tv_refer_points = findViewById(R.id.tv_refer_points);
            tv_spen_more_points = findViewById(R.id.tv_spen_more_points);
            tv_loyality_des = findViewById(R.id.tv_loyality_des);

            tvHead.setText(model.getLoyaltyPoints());
            tvFreeSignUp.setText(model.getFreeSignUp());
            tv_loyality_des.setText(model.getFreeSignUpWithHarpersGet());
            tvPlaceFirstOrder.setText(model.getPlaceFirstOrders());
            tvPlaceOrderToGet.setText(model.getPlaceFirstOrdersFromHarpersGet());
            tvPostingaReview.setText(model.getPostingAReview());
            tvPostingReviewGet.setText(model.getPostingAReviewGet());
            tvGroupOrdering.setText(model.getGroupOrdering());
            tvPlaceGroupOrdering.setText(model.getPlaceGroupingOrderingGet());
            tvBirthdayCelebration.setText(model.getBirthdayCelebrations());
            tvBirthdayCelebrationPlaceOrder.setText(model.getBirthdayCelebrationsAndPlaceOrdersFromHarpersGet());
            tvSocialSharing.setText(model.getSocialSharing());
            tvSharingFb.setText(model.getSharingWithFacebookTwitterWhatsAppEtcGet());
            tvSpendEarn.setText(model.getSpendEarn());
            tvEarnPoint.setText(model.getEarn1PointsForEvery1SpentWithHarpers());
            tvPoint.setText(model.getPoints());
            tvPointt.setText(model.getPoints());
            tvPointtt.setText(model.getPoints());
            tvPointttt.setText(model.getPoints());
            tvPointtttt.setText(model.getPoints());
            tvPointttttt.setText(model.getPoints());
            tvPointtttttt.setText(model.getPoints());

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(LoyaltyPoints.this);
            rcv_loyality_points_list.setLayoutManager(mLayoutManager);
            rcv_loyality_points_list.setItemAnimator(new DefaultItemAnimator());
            listt = new ArrayList<>();

            iv_back.setOnClickListener(this);
            getLoyalty();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getLoyalty() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<LoyalityPointsModel> observable = apiInterface.getLoyalityPoints(prefsHelper.getPref(Constants.API_KEY),prefsHelper.getPref(Constants.LNG_CODE));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoyalityPointsModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoyalityPointsModel searchResult) {
                        //   showProgress();
                        // setAdapterCategory(searchResult);

                        setTextOn(searchResult);

                      /*  LoyalityPointsModel p  = new LoyalityPointsModel();;
                       p.setPerOrderLoyalityPoint(searchResult.getPerOrderLoyalityPoint());
                       p.setBirthdayCelebrationsPoints(searchResult.getBirthdayCelebrationsPoints());
                       p.setPlaceFirstOrdersPoints(searchResult.getPlaceFirstOrdersPoints());
                       p.setPlaceGroupOrderingPoints(searchResult.getPlaceGroupOrderingPoints());
                       p.setPostReviewPoints(searchResult.getPostReviewPoints());
                       p.setReferFriendsPoints(searchResult.getReferFriendsPoints());
                       p.setSignPoints(searchResult.getSignPoints());
                       p.setSocialMediaSharingPoints(searchResult.getSocialMediaSharingPoints());
                       p.setSpendMoreThanPoints(searchResult.getSpendMoreThanPoints());*/
                        //  listt.add(searchResult.getBirthdayCelebrationsPoints());
                        /*listt.add(p);
                        LoyalityPointsAdapter adapterCategory = new LoyalityPointsAdapter(LoyaltyPoints.this,listt);
                        rcv_loyality_points_list.setAdapter(adapterCategory);*/
                        //   hideProgress();

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

    private void setTextOn(LoyalityPointsModel searchResult) {
        try {
            tv_free_signup_points.setText(searchResult.getSignPoints().toString());
            tv_place_fst_order_points.setText(searchResult.getPlaceFirstOrdersPoints().toString());
            tv_item_points.setText(searchResult.getPerOrderLoyalityPoint().toString());
            tv_place_point_order.setText(searchResult.getPlaceGroupOrderingPoints().toString());
            tv_bday_celebrate_points.setText(searchResult.getBirthdayCelebrationsPoints().toString());
            tv_social_media_points.setText(searchResult.getSocialMediaSharingPoints().toString());
            tv_refer_points.setText(searchResult.getReferFriendsPoints().toString());
            tv_spen_more_points.setText(searchResult.getSpendMoreThanPoints().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    private void setAdapterCategory(List<LoyaltyPoints> listt) {
      /*  LoyalityPointsAdapter adapterCategory = new LoyalityPointsAdapter(LoyaltyPoints.this,list);
        rcv_loyality_points_list.setAdapter(adapterCategory);*/


        hideProgress();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            default:
                break;
        }
    }
}
