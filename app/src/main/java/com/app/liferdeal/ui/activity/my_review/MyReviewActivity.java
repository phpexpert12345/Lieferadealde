package com.app.liferdeal.ui.activity.my_review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.GetRestaurantDiscountResponse;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.ArrayList;
import java.util.List;

public class MyReviewActivity extends AppCompatActivity {
    @BindView(R.id.rvReviewList)
    RecyclerView rvReviewList;
    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    ReviewAdapter reviewAdapter;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        if (App.retrieveLangFromGson(MyReviewActivity.this) != null) {
            model = App.retrieveLangFromGson(MyReviewActivity.this);
        }
        ButterKnife.bind(this);
        viewFinds();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void viewFinds() {
        prefsHelper = new PrefsHelper(this);
        progressDialog = new ProgressDialog(this);
        rvReviewList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        getReviewOfUser();

    }

    private void getReviewOfUser() {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<ReviewMain> observable = apiInterface.getReviewData(prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE), prefsHelper.getPref(Constants.CUSTOMER_ID));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReviewMain>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReviewMain searchResult) {
                        hideProgress();
                        //Toast.makeText(getApplicationContext(),"Called api",Toast.LENGTH_LONG).show();

                        ArrayList<List<ReviewMainData>> list = new ArrayList();
                        list.clear();
                        list.addAll(searchResult.getCustomerReviewlistingList());


                        // Toast.makeText(getApplicationContext(), "Success Called=", Toast.LENGTH_LONG).show();
                        reviewAdapter = new ReviewAdapter(getApplicationContext(), list);
                        rvReviewList.setAdapter(reviewAdapter);
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
            progressDialog.setMessage(model.getPlease_wait_text().trim() + "...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void hideProgress() {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}