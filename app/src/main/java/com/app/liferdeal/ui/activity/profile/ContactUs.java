package com.app.liferdeal.ui.activity.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.ContactusModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactUs extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_faq)
    TextView tvFaq;
    @BindView(R.id.tvFeelFree)
    AppCompatTextView tvFeelFree;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private TextView edit_name, edit_email_addres, edit_mobile, edit_message, tv_address;
    private ImageView iv_back;
    private Button btn_send;
    private String strName = "", stremail = "", strPhone = "", strMessage = "", straddress = "";
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(ContactUs.this) != null) {
            model = App.retrieveLangFromGson(ContactUs.this);
        }
        init();
    }

    private void init() {
        prefsHelper = new PrefsHelper(this);
        iv_back = findViewById(R.id.iv_back);
        btn_send = findViewById(R.id.btn_send);
        edit_name = findViewById(R.id.edit_name);
        edit_email_addres = findViewById(R.id.edit_email_addres);
        edit_mobile = findViewById(R.id.edit_mobile);
        edit_message = findViewById(R.id.edit_message);
        tv_address = findViewById(R.id.tv_address);

        tvFaq.setText(model.getContactUs());
        tvFeelFree.setText(model.getFeelFreeToDropUsAMessage());
        edit_name.setHint(model.getYourName());
        edit_email_addres.setHint(model.getEmailAddress());
        edit_mobile.setHint(model.getMobileNo());
        edit_message.setHint(model.getMessage());
        btn_send.setHint(model.getSend());

        straddress = prefsHelper.getPref(Constants.SAVE_FULL_ADDRESS);
        tv_address.setText(straddress);
        iv_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_send:
                strName = CommonMethods.getStringDataInbase64(edit_name.getText().toString().trim());
                stremail = CommonMethods.getStringDataInbase64(edit_email_addres.getText().toString().trim());
                strPhone = CommonMethods.getStringDataInbase64(edit_mobile.getText().toString().trim());
                strMessage = CommonMethods.getStringDataInbase64(edit_message.getText().toString().trim());
                if (edit_name.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_name.setError(model.getPleaseEnterYourName());
                    edit_name.requestFocus();
                } else if (edit_email_addres.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_email_addres.setError(model.getPleaseEnterYourEmail());
                    edit_email_addres.requestFocus();
                } else if (!isValidEmailId(edit_email_addres.getText().toString().trim())) {
                    edit_email_addres.setError(model.getPLEASEENTERVALIDEMAIL());
                    edit_email_addres.requestFocus();
                } else if (edit_mobile.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_mobile.setError(model.getPleaseEnterMobileNo());
                    edit_mobile.requestFocus();
                } else if (edit_message.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_message.setError(model.getPleaseEnterMessage());
                    edit_message.requestFocus();
                } else {
                    getContactUsData();
                }
                break;

            default:
                break;
        }
    }

    private boolean isValidEmailId(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,8})$").matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 9 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }

    private void getContactUsData() {

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<ContactusModel> observable = apiInterface.sendContactUsData(prefsHelper.getPref(Constants.API_KEY),prefsHelper.getPref(Constants.LNG_CODE), strName, stremail, strPhone, strMessage);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContactusModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ContactusModel searchResult) {
                        showProgress();
                        showCustomDialog1decline(searchResult.getErrorMsg().toString());
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });

    }

    private void showCustomDialog1decline(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);

        alertDialog.setIcon(R.drawable.tick);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog.show();

    }
}
