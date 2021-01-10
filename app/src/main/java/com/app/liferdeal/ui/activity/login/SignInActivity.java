package com.app.liferdeal.ui.activity.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentTransaction;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.loginsignup.SignInModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.activity.restaurant.RestaurantBookTable;
import com.app.liferdeal.ui.activity.restaurant.RestaurantDetails;
import com.app.liferdeal.ui.activity.splash.SplashActivity;
import com.app.liferdeal.ui.fragment.restaurant.RestaurantMain;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;
import com.bumptech.glide.Glide;

import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_usrName, edt_usrPass;
    private TextView btn_login, txt_forgot_pass, txt_create_new, tvTitle;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private String langCode, deviceId, devicePlateform;
    private ImageView img_logo, img_back;
    private LanguageResponse model = new LanguageResponse();
    private AppCompatTextView tvSignIn;
    private String clickRestId = "", restourantBookLimit = "", from = "";
    private SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment_layout);
        if (getIntent() != null) {
            clickRestId = getIntent().getStringExtra("clickRestId");
            restourantBookLimit = getIntent().getStringExtra("RESTBOOKLIMIT");
            from = getIntent().getStringExtra("from");
        }
        init();
    }

    private void init() {
        try {
            sharedPreferencesData=new SharedPreferencesData(getApplicationContext());

            prefsHelper = new PrefsHelper(this);
            String logo = prefsHelper.getPref(Constants.APP_LOGO);
            img_logo = findViewById(R.id.img_logo);
            edt_usrName = findViewById(R.id.edt_usr_name);
            edt_usrPass = findViewById(R.id.edt_usr_pass);
            txt_forgot_pass = findViewById(R.id.txt_forgot_pass);
            txt_create_new = findViewById(R.id.txt_create_acc);
            btn_login = findViewById(R.id.btn_login);
            tvTitle = findViewById(R.id.tvTitle);
            img_back = findViewById(R.id.img_back);
            tvSignIn = findViewById(R.id.tvSignIn);

            if (App.retrieveLangFromGson(SignInActivity.this) != null) {
                model = App.retrieveLangFromGson(SignInActivity.this);
                edt_usrName.setHint(model.getEmailAddress());
                edt_usrPass.setHint(model.getPassword());
                txt_forgot_pass.setText(model.getForgortPassword());
                txt_create_new.setText(model.getCreateANewAccount());
                btn_login.setText(model.getLogin());
                tvTitle.setText(model.getLogin());
                tvSignIn.setText(model.getLogin());
            }

            btn_login.setOnClickListener(this);
            img_back.setOnClickListener(this);
            txt_create_new.setOnClickListener(this);
            langCode = prefsHelper.getPref(Constants.LNG_CODE);
            deviceId = prefsHelper.getPref(Constants.deviceId);
            devicePlateform = prefsHelper.getPref(Constants.devicePlateform);
            if (!logo.equalsIgnoreCase("")) {
                Glide.with(SignInActivity.this).load(Uri.parse(logo)).into(img_logo);
            }
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

            case R.id.btn_login:
                if (edt_usrName.equals("")) {
                    edt_usrName.setError(model.getEMAILFIELDISREQUIRED());
                    edt_usrName.requestFocus();
                } else if (!isValidEmailId(edt_usrName.getText().toString().trim())) {
                    edt_usrName.setError(model.getPLEASEENTERVALIDEMAIL());
                    edt_usrName.requestFocus();
                } else if (edt_usrPass.getText().toString().length() == 0) {
                    edt_usrPass.setError(model.getPASSWORDFIELDISREQUIRED());
                    edt_usrPass.requestFocus();
                } else {
                    userLogin(edt_usrName.getText().toString().trim(), edt_usrPass.getText().toString().trim());
                }
                break;

            case R.id.txt_create_acc:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.txt_forgot_pass:
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
                check = android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }

    private void userLogin(String emailid, String pass) {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<SignInModel> observable = apiInterface.loginUser(emailid, pass, Constants.API_KEY, langCode, deviceId, devicePlateform);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignInModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SignInModel signin) {

                        if (signin.getSuccess() == 0) {
                            String cusomerId = signin.getCustomerId();
                            String username = signin.getUserName();
                            String mobilenum = signin.getUserPhone();
                            String usernemail = signin.getUserEmail();
                            String refercode = signin.getReferralCode();
                            String refercodeMessage = signin.getReferralCodeMessage().toString();
                            String referSharingMessage = signin.getReferralSharingMessage();
                            String referralEarnPoints = signin.getReferralEarnPoints();
                            String referralJoinFriends = signin.getReferralJoinFriends();
                            System.out.println("===== strReferCode : " + refercode + "refercodeMessage : " + refercodeMessage + "referSharingMessage :" + referSharingMessage + "referralEarnPoints : " + referralEarnPoints + "referralJoinFriends : " + referralJoinFriends);

                            prefsHelper.savePref(Constants.USER_NAME, username);
                            prefsHelper.savePref(Constants.USER_MOBILE, mobilenum);
                            prefsHelper.savePref(Constants.USER_EMAIL, usernemail);
                            prefsHelper.savePref(Constants.CUSTOMER_ID, cusomerId);
                            prefsHelper.savePref(Constants.REFERCODELOGIN, refercode);
                            prefsHelper.savePref(Constants.refercodeMessage, refercodeMessage);
                            prefsHelper.savePref(Constants.referSharingMessage, referSharingMessage);
                            prefsHelper.savePref(Constants.referralEarnPoints, referralEarnPoints);
                            prefsHelper.savePref(Constants.referralJoinFriends, referralJoinFriends);
                            prefsHelper.savePref(Constants.isLoggedIn, true);
                            openDialog(signin.getSuccessMsg());
                            hideProgress();
                            Log.e("CustomerId=", cusomerId);
                            //  initiateHomeFragment();
                            /*Intent i = new Intent(SignInActivity.this, SignInActivity.class);
                            startActivity(i);*/
                            // finish();
                        } else {
                            Toast.makeText(SignInActivity.this, signin.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }

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

    private void openDialog(String successMsg) {
        final Dialog dialog = new Dialog(SignInActivity.this, R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);

        AppCompatTextView tvTitle = dialog.findViewById(R.id.tvTitle);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);
        AppCompatTextView tvCancel = dialog.findViewById(R.id.tvCancel);
        tvTitle.setText(successMsg);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        tvOk.setOnClickListener(view -> {
            dialog.dismiss();
            LoginSuccess();
        });
    }

    public void LoginSuccess() {
        // if (!prefsHelper.isLoggedIn()) {
        prefsHelper.LogintoApp();
        if (SplashActivity.mInstance != null) {
            SplashActivity.mInstance.finish();
        }
            if (from != null && from.equalsIgnoreCase("table")) {
                Intent booktable = new Intent(SignInActivity.this, RestaurantBookTable.class);
                booktable.putExtra("clickRestId", clickRestId);
                booktable.putExtra("RESTBOOKLIMIT", restourantBookLimit);
                startActivity(booktable);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        finish();
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

    private void initiateHomeFragment() {
        //  HomeFragment homeFragment = HomeFragment.newInstance(containerID);
        // LocationMapFragment locationMapFragment = LocationMapFragment.newInstance(containerID);
        RestaurantMain locationMapFragment = new RestaurantMain();

        FragmentTransaction transaction = SignInActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, locationMapFragment);
        transaction.addToBackStack(locationMapFragment.getTag());
        transaction.commit();
    }
}
