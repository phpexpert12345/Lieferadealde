package com.app.liferdeal.ui.activity.restaurant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.SaveAddressModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private EditText edit_add_tittle, edit_flat_no, edit_flat_name, edit_address, edit_zipcode, edit_city, edit_street_name, edit_mobile;
    private String title, address, zipcode, city, landmark, mobile, flatNo, flatName, streetName;
    private AppCompatButton btn_submit;
    private ProgressDialog progressDialog;
    private ProgressBar pbLoad;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private TextView tvHead, tvAddNewAddress;
    private LanguageResponse model = new LanguageResponse();
    private SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address_activity);
        if (App.retrieveLangFromGson(AddAddressActivity.this) != null) {
            model = App.retrieveLangFromGson(AddAddressActivity.this);
        }
        init();
    }

    private void init() {
        prefsHelper = new PrefsHelper(this);
        rl_back = findViewById(R.id.rl_back);
        edit_add_tittle = findViewById(R.id.edit_add_tittle);
        edit_flat_no = findViewById(R.id.edit_flat_no);
        edit_flat_name = findViewById(R.id.edit_flat_name);
        edit_address = findViewById(R.id.edit_address);
        edit_zipcode = findViewById(R.id.edit_zipcode);
        edit_city = findViewById(R.id.edit_city);
        edit_street_name = findViewById(R.id.edit_street_name);
        edit_mobile = findViewById(R.id.edit_mobile);
        btn_submit = findViewById(R.id.btn_submit);
        pbLoad = findViewById(R.id.pbLoad);
        tvAddNewAddress = findViewById(R.id.tvAddNewAddress);
        tvHead = findViewById(R.id.tvHead);

        tvHead.setText(model.getAddAddress().trim());
        tvAddNewAddress.setText(model.getAddNewAddress().trim());
        btn_submit.setText(model.getAddAddress().trim());
        edit_add_tittle.setHint(model.getAddressTitle().trim());
        edit_flat_no.setHint(model.getHouseDoorNumber().trim());
        edit_flat_name.setHint(model.getFlatName().trim());
        edit_street_name.setHint(model.getStreetName().trim());
        edit_city.setHint(model.getCityName().trim());
        edit_zipcode.setHint(model.getPostalCode().trim());
        edit_mobile.setHint(model.getMobileNo().trim());

        rl_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        sharedPreferencesData=new SharedPreferencesData(getApplicationContext());
        edit_add_tittle.setText(sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE,Constants.ADDRESSTITLE));
        edit_flat_no.setText(sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE,Constants.HOUSENO));
        edit_street_name.setText( sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE,Constants.STREETNAME));
        edit_city.setText(sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE,Constants.CITYNAME));
        edit_zipcode.setText(sharedPreferencesData.getSharedPreferenceData(Constants.PRICEPREFERENCE,Constants.POSTALCODE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_submit:
                updateProfileValidation();
                break;
            default:
                break;
        }
    }

    public void updateProfileValidation() {

        title = CommonMethods.getStringDataInbase64(edit_add_tittle.getText().toString().trim());
        flatNo = CommonMethods.getStringDataInbase64(edit_flat_no.getText().toString().trim());
        flatName = CommonMethods.getStringDataInbase64(edit_flat_name.getText().toString().trim());
        address = CommonMethods.getStringDataInbase64(edit_address.getText().toString().trim());
        city = CommonMethods.getStringDataInbase64(edit_city.getText().toString().trim());
        streetName = CommonMethods.getStringDataInbase64(edit_street_name.getText().toString().trim());
        zipcode = edit_zipcode.getText().toString().trim();
        mobile = edit_mobile.getText().toString().trim();


        if (title.isEmpty()) {
            edit_add_tittle.setError(model.getPleaseEnterAddressTitle());
            edit_add_tittle.requestFocus();
        } else if (edit_flat_no.getText().toString().trim().isEmpty()) {
            edit_flat_no.setError(model.getPleaseEnterHouseDoorNumber());
            edit_flat_no.requestFocus();
        } else if (edit_flat_name.getText().toString().trim().isEmpty()) {
            edit_flat_name.setError(model.getPleaseEnterFlatName());
            edit_flat_name.requestFocus();
        } /*else if (edit_address.getText().toString().trim().isEmpty()) {
            edit_address.setError("Enter Address");
            edit_address.requestFocus();
        } */ else if (edit_street_name.getText().toString().trim().isEmpty()) {
            edit_street_name.setError(model.getPleaseEnterStreetName());
            edit_street_name.requestFocus();
        } else if (edit_city.getText().toString().trim().isEmpty()) {
            edit_city.setError(model.getPleaseEnterCity());
            edit_city.requestFocus();
        } else if (zipcode.isEmpty()) {
            edit_zipcode.setError(model.getPleaseEnterPostalCode());
            edit_zipcode.requestFocus();

        } else if (mobile.isEmpty()) {
            edit_mobile.setError(model.getPleaseEnterMobileNo());
            edit_mobile.requestFocus();
        } else {
            saveAddress();
        }
    }

    private void saveAddress() {
        pbLoad.setVisibility(View.VISIBLE);
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<SaveAddressModel> observable = apiInterface.saveNewAddress(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE),
                mobile, title, flatNo, streetName, city, zipcode, "", prefsHelper.getPref(Constants.CUSTOMER_ID), flatName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveAddressModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SaveAddressModel searchResult) {
//                        showProgress();
                        pbLoad.setVisibility(View.GONE);
                        prefsHelper.savePref(Constants.CUSTOMER_ADDRESS_ID, searchResult.getCustomerAddressId());
                        if (searchResult.getSuccess() == 1) {
                            showCustomDialog1decline(searchResult.getSuccessMsg());
                        }
//                        hideProgress();
                        //setAdapterCategory(searchResult.getMenuItemExtraGroup());
                    }

                    @Override
                    public void onError(Throwable e) {
//                        hideProgress();
                        pbLoad.setVisibility(View.GONE);
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");
//                        hideProgress();
                        pbLoad.setVisibility(View.GONE);
                    }
                });
    }

    public void showCustomDialog1decline(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(AddAddressActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);
        alertDialog.setIcon(R.drawable.ic_place_order_check);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog.show();
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


}
