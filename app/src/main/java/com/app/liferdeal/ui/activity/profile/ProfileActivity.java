package com.app.liferdeal.ui.activity.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.GetProfileModel;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.activity.login.SignInActivity;
import com.app.liferdeal.ui.activity.restaurant.AddressActivity;
import com.app.liferdeal.ui.activity.restaurant.CusineFilter;
import com.app.liferdeal.ui.activity.restaurant.MyOrderActivity;
import com.app.liferdeal.ui.activity.tickets.TicketList;
import com.app.liferdeal.ui.fragment.LocationMapFragment;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.tvMyOrder)
    TextView tvMyOrder;
    @BindView(R.id.tvMyAddress)
    TextView tvMyAddress;
    @BindView(R.id.tvHelp)
    TextView tvHelp;
    @BindView(R.id.tvSetting)
    TextView tvSetting;
    @BindView(R.id.tvProfile)
    TextView tvProfile;
    @BindView(R.id.tvLoyaltyPoint)
    TextView tvLoyaltyPoint;
    @BindView(R.id.tvMyTicket)
    TextView tvMyTicket;
    @BindView(R.id.tvPasswordSecurity)
    TextView tvPasswordSecurity;
    @BindView(R.id.tvPrivacyPolicy)
    TextView tvPrivacyPolicy;
    @BindView(R.id.tvAboutUs)
    TextView tvAboutUs;
    @BindView(R.id.tvLogout)
    TextView tvLogout;
    private ImageView iv_back, userimage;
    private LinearLayout lnr_my_order, aboutus, help_suuport, termscondition, rl_loyality_setting, rl_my_profile, lnr_address, logout, rl_myticket_setting, rl_change_password;
    private PrefsHelper prefsHelper;
    private TextView tv_user_name, tv_user_email, tv_user_phone;
    public static ProfileActivity mInstance;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    PrefsHelper authPreference;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(ProfileActivity.this) != null) {
            model = App.retrieveLangFromGson(ProfileActivity.this);
        }
        init();
        authPreference = new PrefsHelper(this);

        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.getMenu().findItem(R.id.action_profile).setChecked(true);
    }

    private void init() {
        try {
            prefsHelper = new PrefsHelper(this);
            mInstance = this;
            iv_back = findViewById(R.id.iv_back);
            aboutus = findViewById(R.id.aboutus);
            userimage = findViewById(R.id.userimage);
            tv_user_name = findViewById(R.id.tv_user_name);
            tv_user_phone = findViewById(R.id.tv_user_phone);
            tv_user_email = findViewById(R.id.tv_user_email);
            termscondition = findViewById(R.id.termscondition);
            help_suuport = findViewById(R.id.help_suuport);
            rl_loyality_setting = findViewById(R.id.rl_loyality_setting);
            rl_my_profile = findViewById(R.id.rl_my_profile);
            lnr_address = findViewById(R.id.lnr_address);
            logout = findViewById(R.id.logout);
            rl_myticket_setting = findViewById(R.id.rl_myticket_setting);
            rl_change_password = findViewById(R.id.rl_change_password);
            lnr_my_order = findViewById(R.id.lnr_my_order);

            tvMyOrder.setText(model.getMyOrder().trim());
            tvMyAddress.setText(model.getMyAddress().trim());
            tvHelp.setText(model.getHelpCenter().trim());
            tvSetting.setText(model.getSettings().trim());
            tvProfile.setText("" + model.getMyProfile().trim());
            tvLoyaltyPoint.setText(model.getLoyaltyPoints().trim());
            tvMyTicket.setText(model.getMyTicket().trim());
            tvPasswordSecurity.setText(model.getPasswordSecurity().trim());
            tvPrivacyPolicy.setText(model.getPrivacyPolicy().trim());
            tvAboutUs.setText(model.getAboutUs().trim());
            tvLogout.setText(model.getLogout().trim());

            aboutus.setOnClickListener(this);
            termscondition.setOnClickListener(this);
            help_suuport.setOnClickListener(this);
            rl_loyality_setting.setOnClickListener(this);
            iv_back.setOnClickListener(this);
            rl_my_profile.setOnClickListener(this);
            lnr_address.setOnClickListener(this);
            logout.setOnClickListener(this);
            rl_myticket_setting.setOnClickListener(this);
            rl_change_password.setOnClickListener(this);
            lnr_my_order.setOnClickListener(this);

//            System.out.println("=== name : " + prefsHelper.getPref(Constants.USER_NAME));
//            System.out.println("=== name email : " + prefsHelper.getPref(Constants.USER_EMAIL));
//            tv_user_name.setText(prefsHelper.getPref(Constants.USER_NAME));
//            tv_user_email.setText(prefsHelper.getPref(Constants.USER_EMAIL));
//            String userProfileImage = prefsHelper.getPref(Constants.USER_PROFILE_IMAGE);
//            System.out.println("=== name userProfileImage : " + userProfileImage);

//            if (userProfileImage != null) {
//                Glide.with(this).load(userProfileImage).apply(new RequestOptions().override(100, 100).placeholder(R.drawable.user)).into(userimage);
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        getProfileData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfileData();
    }

    private void getProfileData() {
        ApiInterface apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<GetProfileModel> observable = apiInterface.getProfileData(prefsHelper.getPref(Constants.CUSTOMER_ID));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetProfileModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetProfileModel searchResult) {
                        showProgress();
                        String fstName = searchResult.getFirstName();
                        String lstName = searchResult.getLastName();
                        String usrName = searchResult.getUserName();
                        String houseNu = searchResult.getCustomerFloorHouseNumber();
                        String flatName = searchResult.getCustomerFlatName();
                        String streetName = searchResult.getCompanyStreet();
                        String zipcode = searchResult.getUserPostcode();
                        String userpgone = searchResult.getUserPhone();
                        String userphoto = searchResult.getUserPhoto();
                        //  setAddAdapter(searchResult.getAddressModel().getDeliveryaddress());
                        tv_user_name.setText(fstName + " " + lstName);
                        tv_user_phone.setText(userpgone);
                        tv_user_email.setText(searchResult.getUserEmail());
                        if (userphoto != null) {
                            Glide.with(ProfileActivity.this).load(userphoto).apply(new RequestOptions().override(100, 100).
                                    placeholder(R.drawable.profile_pic)).into(userimage);
                        }
                        hideProgress();
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

    private ProgressDialog progressDialog;

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        for (int i = 0; i < bottomNavigation.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigation.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }
        switch (item.getItemId()) {
            case R.id.action_location:
                Intent intent1 = new Intent(ProfileActivity.this, LocationMapFragment.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                break;
            case R.id.action_home:
                Intent ii = new Intent(ProfileActivity.this, MainActivity.class);
                ii.putExtra("FROMWHERE", "fromhome");
                ii.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(ii);
                break;
            case R.id.action_profile:
                break;
            case R.id.action_filter:
                Intent intent2 = new Intent(ProfileActivity.this, CusineFilter.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_myticket_setting:
                startActivity(new Intent(this, TicketList.class));
                break;
            case R.id.aboutus:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.termscondition:
                Intent t = new Intent(this, PrivacyAndPolicy.class);
                t.putExtra("from", "privacy");
                startActivity(t);
                break;
            case R.id.help_suuport:
                Intent h = new Intent(this, ContactUs.class);
                startActivity(h);
                break;

            case R.id.rl_loyality_setting:
                Intent il = new Intent(this, LoyaltyPoints.class);
                startActivity(il);
                break;

            case R.id.rl_change_password:
                startActivity(new Intent(this, ChangePassword.class));
                break;

            case R.id.lnr_my_order:
                startActivity(new Intent(this, MyOrderActivity.class));
                break;

            case R.id.rl_my_profile:
                Intent ipro = new Intent(this, EditUpdateProfileActivity.class);
                startActivity(ipro);
                break;

            case R.id.lnr_address:
                Intent addadress = new Intent(this, AddressActivity.class);
                startActivity(addadress);
                break;

            case R.id.logout:
                logoutDialog();
                break;

            default:
                break;
        }
    }

    public void updateProfilePic(String profileimage) {
        System.out.println("=== profileiamge : " + profileimage);
        Glide.with(this).load(profileimage).apply(new RequestOptions().override(100, 100).placeholder(R.drawable.defpizzaimg)).into(userimage);
    }

    private void logoutDialog() {
        new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Liferdeal")
                .setMessage(model.getAREYOUSUREYOUWANTTOLOGOUT())
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    File dir = getCacheDir();
                                    //deleteDir(dir);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

//                                prefsHelper.clearAllPref();
                                prefsHelper.loggedOut();
                                openDialog();
//                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                                finish();
                            }
                        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(ProfileActivity.this, R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);

        AppCompatTextView tvTitle = dialog.findViewById(R.id.tvTitle);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);
        AppCompatTextView tvCancel = dialog.findViewById(R.id.tvCancel);
        tvTitle.setText(model.getSuccessfullLogout());
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        tvOk.setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//            prefsHelper.clearAllPref();
        });
    }
}
