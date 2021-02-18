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
    @BindView(R.id.tvPointtttttttt)
    TextView tvPointtttttttt;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private RecyclerView rcv_loyality_points_list;
    List<LoyalityPointsModel> listt;
    private ImageView iv_back;
    private TextView tv_free_signup_points, tv_place_fst_order_points, tv_item_points, tv_place_point_order, tv_bday_celebrate_points, tvRefer, tvReferfriend,
            tv_social_media_points, tv_refer_points, tv_spen_more_points, tvHead, tv_loyality_des, tvPointttttttttt, tvEuro, tvEuroDes, tv_euro_point;
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
            tvEuro = findViewById(R.id.tvEuro);
            tvEuroDes = findViewById(R.id.tvEuroDes);
            tv_euro_point = findViewById(R.id.tv_euro_point);
            tvPointttttttttt = findViewById(R.id.tvPointttttttttt);
            tvRefer = findViewById(R.id.tvRefer);
            tvReferfriend = findViewById(R.id.tvReferfriend);

            tvHead.setText(model.getLoyaltyPoints().trim());
            tvFreeSignUp.setText(model.getFreeSignUp().trim());
            tv_loyality_des.setText(model.getFreeSignUpWithHarpersGet().trim());
            tvPlaceFirstOrder.setText(model.getPlaceFirstOrders().trim());
            tvPlaceOrderToGet.setText(model.getPlaceFirstOrdersFromHarpersGet().trim());
            tvPostingaReview.setText(model.getPostingAReview().trim());
            tvPostingReviewGet.setText(model.getPostingAReviewGet().trim());
            tvGroupOrdering.setText(model.getGroupOrdering().trim());
            tvPlaceGroupOrdering.setText(model.getPlaceGroupingOrderingGet().trim());
            tvBirthdayCelebration.setText(model.getBirthdayCelebrations().trim());
            tvBirthdayCelebrationPlaceOrder.setText(model.getBirthdayCelebrationsAndPlaceOrdersFromHarpersGet().trim());
            tvSocialSharing.setText(model.getSocialSharing().trim());
            tvSharingFb.setText(model.getSharingWithFacebookTwitterWhatsAppEtcGet().trim());
            tvSpendEarn.setText(model.getSpendEarn().trim());
            tvEarnPoint.setText(model.getEarn1PointsForEvery1SpentWithHarpers().trim());
            tvEuro.setText(model.get250Spend().trim());
            tvEuroDes.setText(model.getSpendMoreThan250WithHarpersWithPlaceOneOrdersGetExtra().trim());
            tvRefer.setText(model.getReferAFriend().trim());
            tvReferfriend.setText(model.getReferWithFriendsWhenFriendJoinAndPlaceOrdersFromHarper().trim());
            tvPoint.setText(model.getPoints().trim());
            tvPointt.setText(model.getPoints().trim());
            tvPointtt.setText(model.getPoints().trim());
            tvPointttt.setText(model.getPoints().trim());
            tvPointtttt.setText(model.getPoints().trim());
            tvPointttttt.setText(model.getPoints().trim());
            tvPointtttttt.setText(model.getPoints().trim());
            tvPointttttttttt.setText(model.getPoints().trim());
            tvPointtttttttt.setText(model.getPoints().trim());

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
        Observable<LoyalityPointsModel> observable = apiInterface.getLoyalityPoints(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE));

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
            tv_free_signup_points.setText(""+searchResult.getSignPoints().toString().trim());
            tv_place_fst_order_points.setText(""+searchResult.getPlaceFirstOrdersPoints().toString().trim());
            tv_item_points.setText(searchResult.getPostReviewPoints().toString().trim());
            tv_place_point_order.setText(searchResult.getPlaceGroupOrderingPoints().toString().trim());
            tv_bday_celebrate_points.setText(searchResult.getBirthdayCelebrationsPoints().toString().trim());
            tv_social_media_points.setText(searchResult.getSocialMediaSharingPoints().toString().trim());
            tv_refer_points.setText(searchResult.getReferFriendsPoints().toString().trim());
            tv_spen_more_points.setText(searchResult.getSpendMoreThanPoints().toString().trim());
            tv_refer_points.setText(searchResult.getReferFriendsPoints().toString().trim());
            tv_euro_point.setText("" + searchResult.getPerOrderLoyalityPoint());
//            tvEuro.setText(searchResult.g().toString().trim());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(model.getPlease_wait_text().trim()+"...");
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
