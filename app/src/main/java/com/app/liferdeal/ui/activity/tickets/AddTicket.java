package com.app.liferdeal.ui.activity.tickets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.AddTicketDataModel;
import com.app.liferdeal.model.restaurant.AddTicketModel;
import com.app.liferdeal.model.restaurant.SaveAddressModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTicket extends Activity implements View.OnClickListener {
    private EditText edit_order_no, edit_issue_type, edit_comment;
    private ProgressDialog progressDialog;
    private Button btn_submit_ticket;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ticket);
        findViewById();

    }

    private void findViewById() {

        prefsHelper = new PrefsHelper(this);
        iv_back = findViewById(R.id.iv_back);
        edit_order_no = findViewById(R.id.edit_order_no);
        edit_issue_type = findViewById(R.id.edit_issue_type);
        edit_comment = findViewById(R.id.edit_comment);
        btn_submit_ticket = findViewById(R.id.btn_submit_ticket);
        btn_submit_ticket.setOnClickListener(this::onClick);
        iv_back.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_ticket:
                checkValidation();
                break;

            case R.id.iv_back:
               finish();
                break;

            default:
                break;
        }
    }

    private void checkValidation() {
        if (edit_order_no.getText().toString().isEmpty()) {
            edit_order_no.setError("Enter Order Number");
            edit_order_no.requestFocus();
        } else if (edit_issue_type.getText().toString().isEmpty()) {
            edit_issue_type.setError("Enter issue type");
            edit_issue_type.requestFocus();
        } else if (edit_comment.getText().toString().isEmpty()) {
            edit_comment.setError("Enter comment");
            edit_comment.requestFocus();
        } else {
            SaveTicket();
        }
    }

    private void SaveTicket() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<AddTicketDataModel> observable = apiInterface.submitTicket(prefsHelper.getPref(Constants.API_KEY),prefsHelper.getPref(Constants.LNG_CODE), prefsHelper.getPref(Constants.CUSTOMER_ID), edit_order_no.getText().toString(), edit_comment.getText().toString(), edit_issue_type.getText().toString(), prefsHelper.getPref(Constants.USER_EMAIL));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddTicketDataModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddTicketDataModel searchResult) {
                        showProgress();

                        showCustomDialog1decline(searchResult.getErrorMsg().toString());
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

    private void showCustomDialog1decline(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);

        alertDialog.setIcon(R.drawable.tick);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                edit_order_no.setText("");
                edit_issue_type.setText("");
                edit_issue_type.setText("");
                finish();
            }
        });
        alertDialog.show();

    }
}
