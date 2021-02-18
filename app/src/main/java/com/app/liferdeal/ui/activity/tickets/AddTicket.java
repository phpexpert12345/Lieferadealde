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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.AddTicketDataModel;
import com.app.liferdeal.model.restaurant.AddTicketModel;
import com.app.liferdeal.model.restaurant.SaveAddressModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTicket extends Activity implements View.OnClickListener {
    private EditText edit_order_no, edit_issue_type, edit_comment;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private ImageView iv_back;
    private TextView tv_faq;
    private AppCompatButton btn_submit_ticket;
    private TextInputLayout ipOrderNo, ipIssueType, ipComment;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ticket);
        if (App.retrieveLangFromGson(AddTicket.this) != null) {
            model = App.retrieveLangFromGson(AddTicket.this);
        }
        findViewById();

    }

    private void findViewById() {

        prefsHelper = new PrefsHelper(this);
        iv_back = findViewById(R.id.iv_back);
        tv_faq = findViewById(R.id.tv_faq);
        ipOrderNo = findViewById(R.id.ipOrderNo);
        ipIssueType = findViewById(R.id.ipIssueType);
        ipComment = findViewById(R.id.ipComment);
        btn_submit_ticket = findViewById(R.id.btn_submit_ticket);
        edit_order_no = findViewById(R.id.edit_order_no);
        edit_issue_type = findViewById(R.id.edit_issue_type);
        edit_comment = findViewById(R.id.edit_comment);
        btn_submit_ticket = findViewById(R.id.btn_submit_ticket);
        btn_submit_ticket.setOnClickListener(this::onClick);
        iv_back.setOnClickListener(this::onClick);

        tv_faq.setText("" + model.getSubmitTicket().trim());
        btn_submit_ticket.setText("" + model.getSubmit().trim());
        ipOrderNo.setHint("" + model.getOrderNumber().trim());
        ipIssueType.setHint("" + model.getIssueType().trim());
        ipComment.setHint("" + model.getComment().trim());
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
            edit_order_no.setError(model.getPleaseEnterOrderNumber());
            edit_order_no.requestFocus();
        } else if (edit_issue_type.getText().toString().isEmpty()) {
            edit_issue_type.setError(model.getPleaseEnterIssueType());
            edit_issue_type.requestFocus();
        } else if (edit_comment.getText().toString().isEmpty()) {
            edit_comment.setError(model.getPleaseEnterComment());
            edit_comment.requestFocus();
        } else {
            SaveTicket();
        }
    }

    private void SaveTicket() {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<AddTicketDataModel> observable = apiInterface.submitTicket(prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE), prefsHelper.getPref(Constants.CUSTOMER_ID), edit_order_no.getText().toString(),
                edit_comment.getText().toString(), edit_issue_type.getText().toString(), prefsHelper.getPref(Constants.USER_EMAIL));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddTicketDataModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddTicketDataModel searchResult) {
                        showCustomDialog1decline(searchResult.getError(), searchResult.getErrorMsg().toString());
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
                        hideProgress();
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });
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

    private void showCustomDialog1decline(Integer error, String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);

        alertDialog.setIcon(R.drawable.tick);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (error == 1) {
                    edit_order_no.setText("");
                    edit_issue_type.setText("");
                    edit_comment.setText("");
                } else {
                    edit_order_no.setText("");
                    edit_issue_type.setText("");
                    edit_comment.setText("");
                    finish();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
}
