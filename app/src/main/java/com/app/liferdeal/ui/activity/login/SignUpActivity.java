package com.app.liferdeal.ui.activity.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
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

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.loginsignup.SignInModel;
import com.app.liferdeal.model.loginsignup.SignupModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.Utility;
import com.bumptech.glide.Glide;

import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_fst_name, edt_lst_name, edt_pass, edt_mobile_num, edt_con_pass, edt_email_id;
    private TextView btn_signup, txt_login_view, txt_signin, tvTitle, tvAlreadyAccount;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private String deviceId = "", devicePlateform = "";
    private String country, langCode;
    private ProgressDialog progressDialog;
    private ImageView img_logo, img_back;
    private LanguageResponse model;
    private AppCompatTextView tvSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        init();
    }

    private void init() {
        try {
            prefsHelper = new PrefsHelper(this);
            String logo = prefsHelper.getPref(Constants.APP_LOGO);

            edt_fst_name = findViewById(R.id.edt_fst_name);
            tvSignUp = findViewById(R.id.tvSignUp);
            img_logo = findViewById(R.id.img_logo);
            edt_lst_name = findViewById(R.id.edt_lst_name);
            edt_email_id = findViewById(R.id.edt_email_id);
            edt_mobile_num = findViewById(R.id.edt_mobile);
            edt_pass = findViewById(R.id.edt_usr_pass);
            edt_con_pass = findViewById(R.id.edt_con_pass);
            btn_signup = findViewById(R.id.btn_signup);
            txt_login_view = findViewById(R.id.txt_clogin_acc);
            img_back = findViewById(R.id.img_back);
            txt_signin = findViewById(R.id.txt_signin);
            tvTitle = findViewById(R.id.tvTitle);
            tvAlreadyAccount = findViewById(R.id.tvAlreadyAccount);

            if (App.retrieveLangFromGson(SignUpActivity.this) != null) {
                model = App.retrieveLangFromGson(SignUpActivity.this);
                edt_fst_name.setHint(model.getFirstName());
                edt_lst_name.setHint(model.getLastName());
                edt_email_id.setHint(model.getEmailAddress());
                edt_mobile_num.setHint(model.getMobileNo());
                edt_pass.setHint(model.getPassword());
                btn_signup.setText(model.getSIGNUP());
                tvAlreadyAccount.setText(model.getDoYouHaveAnAccount());
                txt_signin.setText(model.getSignIn());
                tvTitle.setText(model.getSIGNUP());
                tvSignUp.setText(model.getSIGNUP());
            }
            edt_pass.setTransformationMethod(new PasswordTransformationMethod());
            Utility.ShowHidePassword(edt_pass,1);

            img_back.setOnClickListener(this);
            btn_signup.setOnClickListener(this);
            txt_login_view.setOnClickListener(this);
            txt_signin.setOnClickListener(this);
            country = "india";
            langCode = prefsHelper.getPref(Constants.LNG_CODE);
            deviceId = prefsHelper.getPref(Constants.deviceId);
            devicePlateform = prefsHelper.getPref(Constants.devicePlateform);
            if (!logo.equalsIgnoreCase("")) {
                Log.i("reason", logo);
                Glide.with(SignUpActivity.this).load(Uri.parse(logo)).into(img_logo);
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

            case R.id.btn_signup:
                if (edt_fst_name.getText().toString().trim().equalsIgnoreCase("")) {
                    edt_fst_name.setError(model.getPleaseEnterFullName());
                    edt_fst_name.requestFocus();
                } /*else if (edt_lst_name.getText().toString().trim().equalsIgnoreCase("")) {
                    edt_lst_name.setError(model.getPleaseEnterLastName());
                    edt_lst_name.requestFocus();
                } */ else if (edt_email_id.getText().toString().trim().equalsIgnoreCase("")) {
                    edt_email_id.setError(model.getPleaseEnterEmailAddress());
                    edt_email_id.requestFocus();
                } else if (!isValidEmailId(edt_email_id.getText().toString().trim())) {
                    edt_email_id.setError(model.getPLEASEENTERVALIDEMAIL());
                    edt_email_id.requestFocus();
                } else if (edt_mobile_num.getText().toString().length() == 0) {
                    edt_mobile_num.setError(model.getPleaseEnterMobileNo());
                    edt_mobile_num.requestFocus();
                } else if (!isValidMobile(edt_mobile_num.getText().toString().trim())) {
                    edt_mobile_num.setError(model.getPLEASEENTERVALIDMOBILE());
                    edt_mobile_num.requestFocus();
                } else if (edt_pass.getText().toString().length() == 0) {
                    edt_pass.setError(model.getPleaseEnterPassword());
                    edt_pass.requestFocus();
                } else {
                    userRegistration(edt_fst_name.getText().toString().trim(), edt_lst_name.getText().toString().trim(), edt_email_id.getText().toString().trim(), edt_mobile_num.getText().toString().trim(), edt_pass.getText().toString().trim());
                }
                break;

            case R.id.txt_signin:
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
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
            if (phone.length() < 9 || phone.length() > 14) {
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

    private void userRegistration(String fstName, String lstname, String emailid, String mobileno, String pass) {
        showProgress();
        String lName = " ";
        String currentString = fstName.trim();
        String[] separated = currentString.split(" ");
        String fName = separated[0];
        if (fstName.contains(" ")) {
            if (separated[1].equalsIgnoreCase(null) || separated[1].equalsIgnoreCase("")) {
                lName = " ";
            } else {
                lName = separated[1];
            }
        }

        String ffName = CommonMethods.getStringDataInbase64(fName);
        String llstname = CommonMethods.getStringDataInbase64(lName);

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<SignupModel> observable = apiInterface.registerUser(ffName, llstname, emailid, mobileno, pass, deviceId, country,
                devicePlateform, "", prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignupModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SignupModel signup) {
                        hideProgress();
                        if (signup.getSuccess() != null && signup.getSuccess() == 1) {

                            String cusomerId = signup.getCustomerId();
                            prefsHelper.savePref(Constants.CUSTOMER_ID, cusomerId);
                            prefsHelper.savePref(Constants.IsLogin,true);
//                            String username = signin.getUserName();
//                            String mobilenum = signin.getUserPhone();
//                            String usernemail = signin.getUserEmail();
//                            String refercode = signin.getReferralCode();
//                            String refercodeMessage = signin.getReferralCodeMessage().toString();
//                            String referSharingMessage = signin.getReferralSharingMessage();
//                            String referralEarnPoints = signin.getReferralEarnPoints();
//                            String referralJoinFriends = signin.getReferralJoinFriends();
//                            System.out.println("===== strReferCode : " + refercode + "refercodeMessage : " + refercodeMessage + "referSharingMessage :" + referSharingMessage + "referralEarnPoints : " + referralEarnPoints + "referralJoinFriends : " + referralJoinFriends);
//
//                            prefsHelper.savePref(Constants.USER_NAME, username);
//                            prefsHelper.savePref(Constants.USER_MOBILE, mobilenum);
//                            prefsHelper.savePref(Constants.USER_EMAIL, usernemail);
//                            prefsHelper.savePref(Constants.REFERCODELOGIN, refercode);
//                            prefsHelper.savePref(Constants.refercodeMessage, refercodeMessage);
//                            prefsHelper.savePref(Constants.referSharingMessage, referSharingMessage);
//                            prefsHelper.savePref(Constants.referralEarnPoints, referralEarnPoints);
//                            prefsHelper.savePref(Constants.referralJoinFriends, referralJoinFriends);
//                            prefsHelper.savePref(Constants.isLoggedIn, true);

                            openDialog(signup.getSuccessMsg(),1);
                        } else {
                            if(signup.getError_msg()!=null) {
                                openDialog(signup.getError_msg(),0);
//                                Toast.makeText(SignUpActivity.this, signup.getError_msg(), Toast.LENGTH_SHORT).show();
                            }
                        }
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

    private void openDialog(String successMsg,int type) {
        final Dialog dialog = new Dialog(SignUpActivity.this, R.style.DialogCustomTheme);
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
            if(type==1) {
                userLogin(edt_email_id.getText().toString(), edt_pass.getText().toString());
            }
//            Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
//            startActivity(i);
        });
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

                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            hideProgress();
                            finish();
                            Log.e("CustomerId=", cusomerId);
                            //  initiateHomeFragment();
                            /*Intent i = new Intent(SignInActivity.this, SignInActivity.class);
                            startActivity(i);*/
                            // finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, signin.getSuccessMsg(), Toast.LENGTH_SHORT).show();
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
