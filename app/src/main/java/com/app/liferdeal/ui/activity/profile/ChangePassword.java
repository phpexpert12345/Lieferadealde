package com.app.liferdeal.ui.activity.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.ChangepasswordModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private Button btn_submit;
    private ImageView iv_back;
    private TextView edt_old_pwd, edt_new_pwd, edt_confirm_pwd, tv_faq;
    private LanguageResponse model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        init();
    }

    private void init() {
        prefsHelper = new PrefsHelper(this);
        progressDialog = new ProgressDialog(this);
        btn_submit = findViewById(R.id.btn_submit);
        iv_back = findViewById(R.id.iv_back);
        edt_old_pwd = findViewById(R.id.edt_old_pwd);
        edt_new_pwd = findViewById(R.id.edt_new_pwd);
        edt_confirm_pwd = findViewById(R.id.edt_confirm_pwd);
        tv_faq = findViewById(R.id.tv_faq);

        if (App.retrieveLangFromGson(ChangePassword.this) != null) {
            model = App.retrieveLangFromGson(ChangePassword.this);
        }

        tv_faq.setText("" + model.getChangePassword().trim());
        btn_submit.setText("" + model.getSubmit().trim());
        edt_old_pwd.setHint("" + model.getEnterOldPassword().trim());
        edt_new_pwd.setHint("" + model.getEnterNewPassword().trim());
        edt_confirm_pwd.setHint("" + model.getConfirmNewPassword().trim());

        btn_submit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                submitdata();
                break;
            default:
                break;
        }
    }

    private void submitdata() {
        if (edt_old_pwd.getText().toString().trim().isEmpty()) {
            edt_old_pwd.setError(model.getEnterOldPassword());
            edt_old_pwd.requestFocus();
        } else if (edt_new_pwd.getText().toString().trim().isEmpty()) {
            edt_new_pwd.setError(model.getEnterNewPassword());
            edt_new_pwd.requestFocus();
        } else if (edt_confirm_pwd.getText().toString().trim().isEmpty()) {
            edt_confirm_pwd.setError(model.getConfirmNewPassword());
            edt_confirm_pwd.requestFocus();
        } else if (!edt_confirm_pwd.getText().toString().trim().equalsIgnoreCase(edt_new_pwd.getText().toString().trim())) {
            edt_confirm_pwd.setError(model.getBOTHPASSWORDSHOULDBESAME());
            edt_confirm_pwd.requestFocus();
        } else {
            saveAddress();
        }
    }

    private void saveAddress() {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<ChangepasswordModel> observable = apiInterface.changePassword(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), prefsHelper.getPref(Constants.CUSTOMER_ID), edt_old_pwd.getText().toString().trim(), edt_new_pwd.getText().toString().trim());

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChangepasswordModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChangepasswordModel searchResult) {
                        try {
                            if (searchResult.getError() == 1) {
                                showCustomDialog1decline(searchResult.getError_msg(), "error");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            if (searchResult.getSuccess() == 1) {
                                showCustomDialog1decline(searchResult.getSuccessMsg(), "success");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        hideProgress();
                        //setAdapterCategory(searchResult.getMenuItemExtraGroup());
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

    public void showCustomDialog1decline(String s, String type) {
        final AlertDialog alertDialog = new AlertDialog.Builder(ChangePassword.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);
        alertDialog.setIcon(R.drawable.ic_place_order_check);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                if (type.equalsIgnoreCase("success")) {
                    finish();
                }
            }
        });
        alertDialog.show();
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

}
