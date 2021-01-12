package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.WriteReviewModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WriteAReviewActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView btn_submit, tv_faq, tvHead, etText;
    private ImageView iv_back;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private String restid = "", customerId = "", orderId = "";
    private RatingBar ratingBar;
    private EditText edt_txt_write_review;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writeareview);
        if (App.retrieveLangFromGson(WriteAReviewActivity.this) != null) {
            model = App.retrieveLangFromGson(WriteAReviewActivity.this);
        }
        init();
    }

    private void init() {
        prefsHelper = new PrefsHelper(this);
        progressDialog = new ProgressDialog(this);
        iv_back = findViewById(R.id.iv_back);
        ratingBar = findViewById(R.id.ratingBar);
        edt_txt_write_review = findViewById(R.id.edt_txt_write_review);
        btn_submit = findViewById(R.id.btn_submit);
        tv_faq = findViewById(R.id.tv_faq);
        tvHead = findViewById(R.id.tvHead);
        etText = findViewById(R.id.etText);

        tv_faq.setText(model.getWriteAReview());
        tvHead.setText(model.getWriteAReview());
        etText.setText(model.getWriteAReview());
        btn_submit.setText(model.getSubmit());

        customerId = prefsHelper.getPref(Constants.CUSTOMER_ID);
        restid = getIntent().getStringExtra("clickRestId");
        orderId = getIntent().getStringExtra("ORDERNO");
        Log.e("REST=", restid.toString());
        iv_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                callApi();
                break;
            default:
                break;
        }
    }

    private void callApi() {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<WriteReviewModel> observable = apiInterface.submitReviewData(Constants.API_KEY, prefsHelper.getPref(Constants.LNG_CODE), customerId, String.valueOf(ratingBar.getRating()),
                String.valueOf(ratingBar.getRating()), String.valueOf(ratingBar.getRating()), CommonMethods.getStringDataInbase64(edt_txt_write_review.getText().toString().trim()), orderId, restid, String.valueOf(ratingBar.getRating()));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WriteReviewModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WriteReviewModel searchResult) {
                        // showProgress();
                        hideProgress();
                        Toast.makeText(getApplicationContext(), searchResult.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                        finish();
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
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(model.getPlease_wait_text().trim() + "...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
