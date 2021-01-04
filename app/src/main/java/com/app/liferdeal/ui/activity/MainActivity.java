package com.app.liferdeal.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.GetProfileModel;
import com.app.liferdeal.model.LanguageModel;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.splash.DataModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.activity.login.SignInActivity;
import com.app.liferdeal.ui.activity.login.SignUpActivity;
import com.app.liferdeal.ui.activity.profile.AboutUsActivity;
import com.app.liferdeal.ui.activity.profile.ChangePassword;
import com.app.liferdeal.ui.activity.profile.ContactUs;
import com.app.liferdeal.ui.activity.profile.EditUpdateProfileActivity;
import com.app.liferdeal.ui.activity.profile.LoyaltyPoints;
import com.app.liferdeal.ui.activity.profile.PrivacyAndPolicy;
import com.app.liferdeal.ui.activity.profile.ProfileActivity;
import com.app.liferdeal.ui.activity.profile.ReferEarnFrndActivity;
import com.app.liferdeal.ui.activity.restaurant.AddExtraActivity;
import com.app.liferdeal.ui.activity.restaurant.AddressActivity;
import com.app.liferdeal.ui.activity.restaurant.CusineFilter;
import com.app.liferdeal.ui.activity.restaurant.MyOrderActivity;
import com.app.liferdeal.ui.activity.restaurant.WriteAReviewActivity;
import com.app.liferdeal.ui.activity.splash.SplashActivity;
import com.app.liferdeal.ui.activity.tickets.TicketList;
import com.app.liferdeal.ui.adapters.DrawerItemCustomAdapter;
import com.app.liferdeal.ui.adapters.LanguageAdapter;
import com.app.liferdeal.ui.fragment.LocationMapFragment;
import com.app.liferdeal.ui.fragment.restaurant.RestaurantMain;
import com.app.liferdeal.ui.interfaces.ItemClickListener;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.GPSTracker;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, ItemClickListener {

    private String[] mNavigationDrawerItemTitles;
    public static DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    private PrefsHelper authPreference;
    private DataModel[] drawerItem;
    public static LinearLayout rl_main_left_drawer;
    private String customerId, firstName, lastName, userPhone, userEmail, userFloor, userStreetName, userZipCode, city, state, userAddress;
    private String locationSearchAddress = "", restaurantName = "", restaurantAddress = "", orderDate = "", orderTime = "", ordPrice = "", orderIdentifyno = "";
    Database database;
    LinearLayout editprofile, logonotshow, lnr_view_signin_signup, btn_signup, btn_sign;
    TextView username, usermobile, useremail;
    RelativeLayout myaccLayout, myorderLayout, menuLayout, logoutLayout, logoutLayout1;
    CircleImageView profileimage;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String CustomerId;
    public static String where_para = "", customerlocality_para = "";
    private ArrayList<String> selected_cusines;
    private ImageView img_logo, language_logo, img_current_img, lnr_edit;
    private TextView tv_location, textView_name, tvCreateAccount, tvSignIn;
    private Double currentLatitude, currentLongitude;
    private LanguageResponse model = new LanguageResponse();

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @BindView(R.id.imgHumbereger)
    ImageView imgHumbereger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(MainActivity.this) != null) {
            model = App.retrieveLangFromGson(MainActivity.this);
        }


        authPreference = new PrefsHelper(this);
        progressDialog = new ProgressDialog(this);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.getMenu().findItem(R.id.action_home).setChecked(true);

     /*   if (authPreference.getPref(Constants.USER_PROFILE_IMAGE).equals("") || (authPreference.getPref(Constants.USER_PROFILE_IMAGE).equals(null))) {

        } else {
            Picasso.get().load(authPreference.getUserPhoto()).into(profileimage);
        }
*/
        if (checkAndRequestPermissions()) {
        }
        lnr_edit = findViewById(R.id.lnr_edit);
        tvSignIn = findViewById(R.id.tvSignIn);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        editprofile = findViewById(R.id.editprofile);
        textView_name = findViewById(R.id.username);
        usermobile = findViewById(R.id.usermobile);
        profileimage = findViewById(R.id.profileimage);
        useremail = findViewById(R.id.useremail);
        lnr_view_signin_signup = findViewById(R.id.lnr_view_signin_signup);
        btn_signup = findViewById(R.id.btn_signup);
        btn_sign = findViewById(R.id.btn_sign);
        // statusBarColor();
        AddExtraActivity.cart_Item_number = 0;
        database = new Database(MainActivity.this);
        database.delete();
//      database.deal_delete();
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.main_nav_menu_recyclerview);

        rl_main_left_drawer = findViewById(R.id.rl_main_left_drawer);

        if (getIntent().getExtras() != null) {
            locationSearchAddress = getIntent().getStringExtra("SEARCHADDRESS");
            System.out.println("==== SEARCHADDRESS in main : " + locationSearchAddress);
        }
        System.out.println("==== SEARCHADDRESS in main : " + locationSearchAddress);

        lnr_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditUpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        tvSignIn.setText(model.getSignIn());
        tvCreateAccount.setText(model.getCreateANewAccount());
      /*  if (getIntent().getExtras() != null) {
            firstName = getIntent().getStringExtra("firstName");
            lastName = getIntent().getStringExtra("lastName");
            userEmail = getIntent().getStringExtra("email");
            userPhone = getIntent().getStringExtra("phoneNumber");

            city = getIntent().getStringExtra("userCity");
            state = getIntent().getStringExtra("userState");
            userAddress = getIntent().getStringExtra("userAddress");
            restaurantName = getIntent().getStringExtra("restaurantAddress");
            restaurantAddress = getIntent().getStringExtra("restaurantName");
            orderDate = getIntent().getStringExtra("date");
            orderTime = getIntent().getStringExtra("time");
            ordPrice = getIntent().getStringExtra("orderPrice");
            orderIdentifyno = getIntent().getStringExtra("orderIdentifyno");
            username.setText(firstName + " " + lastName);
            usermobile.setText(userPhone);
            useremail.setText(userEmail);

        }*/

        GPSTracker trackerObj = new GPSTracker(this);
        currentLatitude = trackerObj.getLatitude();
        currentLongitude = trackerObj.getLongitude();
        getAddressFromCurrentLatLong(String.valueOf(currentLatitude), String.valueOf(currentLongitude));
        setupToolbar();
        selected_cusines = new ArrayList<>();

        String address = authPreference.getPref(Constants.SAVE_FULL_ADDRESS);
        System.out.println("=== address in main activity : " + address);
        String logo = authPreference.getPref(Constants.APP_LOGO);
        String lnglogo = authPreference.getPref(Constants.APP_LNG_ICON);
        System.out.println("==== ff : " + logo);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* img_logo = toolbar.findViewById(R.id.logo_img);
        language_logo = toolbar.findViewById(R.id.language_logo);
        tv_location = toolbar.findViewById(R.id.tv_location);
        img_current_img = toolbar.findViewById(R.id.img_current_img);*/


        img_logo = findViewById(R.id.logo_img);
        language_logo = findViewById(R.id.language_logo);
        tv_location = findViewById(R.id.tv_location);
        img_current_img = findViewById(R.id.img_current_img);
        if (locationSearchAddress != null && !locationSearchAddress.equalsIgnoreCase("")) {
            tv_location.setText(locationSearchAddress);

        } else {
            tv_location.setText(address);

        }

        // setToolbar();
        tv_location.setOnClickListener(this);
        if (logo != null) {
            if (!logo.equalsIgnoreCase("")) {
                Glide.with(MainActivity.this).load(Uri.parse(logo)).into(img_logo);
            }
        }

        img_current_img.setOnClickListener(this);
        editprofile.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        btn_sign.setOnClickListener(this);
        if (lnglogo != null) {
            if (!lnglogo.equalsIgnoreCase("")) {
                Glide.with(MainActivity.this).load(Uri.parse(lnglogo)).into(language_logo);
            }
        }

        language_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageList();
            }
        });

        replaceHomeFragment();
        customerId = authPreference.getPref(Constants.CUSTOMER_ID);
        if (authPreference.isLoggedIn()) {
            getProfileData();
            drawerItem = new DataModel[12];
            drawerItem[0] = new DataModel(model.getHome(), R.drawable.my_account);
            drawerItem[1] = new DataModel(model.getMyAccount(), R.drawable.my_account);
            drawerItem[2] = new DataModel(model.getMyOrder(), R.drawable.my_order_new);
            drawerItem[3] = new DataModel(model.getMyAddress(), R.drawable.my_address_new);
            drawerItem[4] = new DataModel(model.getMyReview(), R.drawable.my_review_new);
            drawerItem[5] = new DataModel(model.getMyTicket(), R.drawable.my_ticket_new);
            drawerItem[6] = new DataModel(model.getMyLoyaltyPoints(), R.drawable.ic_loyalty_point);
            drawerItem[7] = new DataModel(model.getChangePassword(), R.drawable.change_password_new);
            drawerItem[8] = new DataModel(model.getReferAFriend(), R.drawable.refer_earn);
            drawerItem[9] = new DataModel(model.getContactUsHelp(), R.drawable.contactus_help_new);
            drawerItem[10] = new DataModel(model.getRateUs(), R.drawable.rate_us_new);
//          drawerItem[11] = new DataModel("Language Setting", R.drawable.ic_lang);
            drawerItem[11] = new DataModel(model.getLogout(), R.drawable.logout_new);
        } else {
            textView_name.setText(model.getWelcomeGuest());
            lnr_edit.setVisibility(View.GONE);
            usermobile.setVisibility(View.GONE);
            useremail.setVisibility(View.GONE);
            lnr_view_signin_signup.setVisibility(View.VISIBLE);
            drawerItem = new DataModel[5];

         /*   drawerItem[0] = new DataModel(model.getContactUsHelp(), R.drawable.help_support);
              drawerItem[1] = new DataModel(model.getAboutUs(), R.drawable.about_us);
              drawerItem[2] = new DataModel(model.getTermsOfService(), R.drawable.terms_condition);
              drawerItem[3] = new DataModel(model.getPrivacyPolicy(), R.drawable.policy);
              drawerItem[4] = new DataModel(model.getRateUs(), R.drawable.help_support);*/
//            drawerItem[5] = new DataModel("Language Setting", R.drawable.ic_lang);


            drawerItem[0] = new DataModel(model.getContactUsHelp(), R.drawable.contact_new);
            drawerItem[1] = new DataModel(model.getAboutUs(), R.drawable.aboutus_new);
            drawerItem[2] = new DataModel(model.getTermsOfService(), R.drawable.termofservice_new);
            drawerItem[3] = new DataModel(model.getPrivacyPolicy(), R.drawable.privacy_policy_new);
            drawerItem[4] = new DataModel(model.getRateUs(), R.drawable.rate_us_new);
        }

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.nav_list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();


       /* editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UpdateProfileActivity.class);
                intent.putExtra("firstName", authPreference.getFirstName());
                intent.putExtra("lastName", authPreference.getLastName());
                intent.putExtra("email", authPreference.getUserEmail());
                intent.putExtra("phoneNumber", authPreference.getUserPhone());
                intent.putExtra("streetNumber", authPreference.getCompanyStreetNo());//floor
                intent.putExtra("streetName", authPreference.getCompanyStreet());
                intent.putExtra("zipCode", authPreference.getUserPostcode());
                intent.putExtra("userCity", authPreference.getUserCity());
                intent.putExtra("userState", authPreference.getUserState());
                intent.putExtra("userAddress", authPreference.getUserAddress());
                startActivity(intent);
                // finish();
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
            }
        });*/


        Drawable img = getResources().getDrawable(R.drawable.edit_with_black);
        img.setBounds(0, 0, 25, 25);
        tv_location.setCompoundDrawables(null, null, img, null);

    }

    private void languageList() {
        showProgress();
        ApiInterface apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<LanguageModel> observable = apiInterface.getLanguageList(authPreference.getPref(Constants.API_KEY));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LanguageModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LanguageModel searchResult) {
                        hideProgress();
                        dialogOpen(searchResult.getLanguageListList());
                        //hideProgress();
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

    boolean isDrawerOpen = false;

    @OnClick(R.id.imgHumbereger)
    public void imgHumberegerClicked(View view) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        /*if(!isDrawerOpen) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
            isDrawerOpen = true;
        }
        else {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            isDrawerOpen = false;
        }*/
    }

    private Boolean[] selectedLang;
    private List<LanguageModel.LanguageListList> langList;
    private LanguageAdapter langAdapter;
    private Dialog dialog;


    private void dialogOpen(List<LanguageModel.LanguageListList> languageListList) {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_language);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        RadioGroup rgLanguage = dialog.findViewById(R.id.rgLanguage);
//        RadioButton rbGerman = dialog.findViewById(R.id.rbGerman);
//        RadioButton rbEng = dialog.findViewById(R.id.rbEng);
        AppCompatImageView ivClose = dialog.findViewById(R.id.ivClose);
//        AppCompatImageView ivEnglish = dialog.findViewById(R.id.ivEnglish);
//        AppCompatImageView ivGerman = dialog.findViewById(R.id.ivGerman);
        RecyclerView rvLanguageList = dialog.findViewById(R.id.rvLanguageList);
        langList = new ArrayList<>();
        langList.addAll(languageListList);
        selectedLang = new Boolean[languageListList.size()];
        for (int i = 0; i < languageListList.size(); i++) {
            selectedLang[i] = false;
        }
        langAdapter = new LanguageAdapter(MainActivity.this, languageListList, selectedLang);
        rvLanguageList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvLanguageList.setAdapter(langAdapter);
        langAdapter.setClickListener(this);

//        rbGerman.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getLanguageData(authPreference.getPref(Constants.API_KEY), "de");
//                authPreference.savePref(Constants.LNG_CODE, "de");
//                dialog.dismiss();
//            }
//        });
//
//        rbEng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getLanguageData(authPreference.getPref(Constants.API_KEY), "en");
//                authPreference.savePref(Constants.LNG_CODE, "en");
//                dialog.dismiss();
//            }
//        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    private void getLanguageData(String apiKey, String appDefaultLanguage) {
        ApiInterface apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<LanguageResponse> observable = apiInterface.getLanguage(apiKey, appDefaultLanguage);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LanguageResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LanguageResponse response) {
                        authPreference.savePref(Constants.LNG_CODE, appDefaultLanguage);
                        App.addLangToGson(MainActivity.this, response);
                        replaceHomeFragment();
                        replaceNavigationLanguage();
//                        btn_get_started.setText(response.);
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

    private void replaceNavigationLanguage() {
        authPreference = new PrefsHelper(getApplicationContext());
        if (App.retrieveLangFromGson(MainActivity.this) != null) {
            model = App.retrieveLangFromGson(MainActivity.this);
        }

        tvSignIn.setText(model.getSignIn());
        tvCreateAccount.setText(model.getCreateANewAccount());

        if (authPreference.isLoggedIn()) {
            //getProfileData();

            drawerItem = new DataModel[12];
            drawerItem[0] = new DataModel(model.getHome(), R.drawable.my_account);
            drawerItem[1] = new DataModel(model.getMyAccount(), R.drawable.my_account);
            drawerItem[2] = new DataModel(model.getMyOrder(), R.drawable.my_order_new);
            drawerItem[3] = new DataModel(model.getMyAddress(), R.drawable.my_address_new);
            drawerItem[4] = new DataModel(model.getMyReview(), R.drawable.my_review_new);
            drawerItem[5] = new DataModel(model.getMyTicket(), R.drawable.my_ticket_new);
            drawerItem[6] = new DataModel(model.getMyLoyaltyPoints(), R.drawable.ic_loyalty_point);
            drawerItem[7] = new DataModel(model.getChangePassword(), R.drawable.change_password_new);
            drawerItem[8] = new DataModel(model.getReferAFriend(), R.drawable.refer_earn);
            drawerItem[9] = new DataModel(model.getContactUsHelp(), R.drawable.contactus_help_new);
            drawerItem[10] = new DataModel(model.getRateUs(), R.drawable.rate_us_new);
//            drawerItem[11] = new DataModel("Language Setting", R.drawable.ic_lang);
            drawerItem[11] = new DataModel(model.getLogout(), R.drawable.logout_new);
        } else {
            textView_name.setText(model.getWelcomeGuest());
            lnr_edit.setVisibility(View.GONE);
            usermobile.setVisibility(View.GONE);
            useremail.setVisibility(View.GONE);
            lnr_view_signin_signup.setVisibility(View.VISIBLE);
            drawerItem = new DataModel[5];

         /*   drawerItem[0] = new DataModel(model.getContactUsHelp(), R.drawable.help_support);
            drawerItem[1] = new DataModel(model.getAboutUs(), R.drawable.about_us);
            drawerItem[2] = new DataModel(model.getTermsOfService(), R.drawable.terms_condition);
            drawerItem[3] = new DataModel(model.getPrivacyPolicy(), R.drawable.policy);
            drawerItem[4] = new DataModel(model.getRateUs(), R.drawable.help_support);*/
//            drawerItem[5] = new DataModel("Language Setting", R.drawable.ic_lang);


            drawerItem[0] = new DataModel(model.getContactUsHelp(), R.drawable.contact_new);
            drawerItem[1] = new DataModel(model.getAboutUs(), R.drawable.aboutus_new);
            drawerItem[2] = new DataModel(model.getTermsOfService(), R.drawable.termofservice_new);
            drawerItem[3] = new DataModel(model.getPrivacyPolicy(), R.drawable.privacy_policy_new);
            drawerItem[4] = new DataModel(model.getRateUs(), R.drawable.rate_us_new);
        }


        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.nav_list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
    }

    private void getProfileData() {
        ApiInterface apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<GetProfileModel> observable = apiInterface.getProfileData(authPreference.getPref(Constants.CUSTOMER_ID));
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
                        textView_name.setText(fstName + " " + lstName);
                        usermobile.setText(userpgone);
                        useremail.setText(authPreference.getPref(Constants.USER_EMAIL));
                        if (userphoto != null) {
                            Glide.with(MainActivity.this).load(userphoto).apply(new RequestOptions().override(100, 100).
                                    placeholder(R.drawable.user)).into(profileimage);
                        }
                        lnr_edit.setVisibility(View.VISIBLE);
                        usermobile.setVisibility(View.VISIBLE);
                        useremail.setVisibility(View.VISIBLE);

                        lnr_view_signin_signup.setVisibility(View.GONE);

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
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void replaceHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RestaurantMain homeFragment = new RestaurantMain(currentLatitude, currentLongitude);
        fragmentManager.beginTransaction().replace(R.id.main_content, homeFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                Intent i = new Intent(MainActivity.this, LocationMapFragment.class);
                startActivity(i);
                break;
            case R.id.img_current_img:
                Intent ii = new Intent(MainActivity.this, LocationMapFragment.class);
                startActivity(ii);
                break;

            case R.id.editprofile:
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;

            case R.id.btn_signup:
                Intent signup = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signup);
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case R.id.btn_sign:
                Intent signin = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(signin);
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            bottomNavigation.setOnNavigationItemSelectedListener(this);
            bottomNavigation.getMenu().findItem(R.id.action_home).setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                Intent intent1 = new Intent(MainActivity.this, LocationMapFragment.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                break;
            case R.id.action_home:
//                Intent ii = new Intent(MainActivity.this, MainActivity.class);
//                ii.putExtra("FROMWHERE", "fromhome");
//                ii.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(ii);
//                finish();
                break;
            case R.id.action_profile:
                System.out.println("==== check is login : " + authPreference.isLoggedIn());
                if (authPreference.isLoggedIn()) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else {
                    Intent is = new Intent(MainActivity.this, SignInActivity.class);
                    is.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(is);
                }
                break;
            case R.id.action_filter:
                Intent intent2 = new Intent(MainActivity.this, CusineFilter.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
                break;
        }
        return true;
    }

    @Override
    public void tabItemClick(View view, int adapterPosition, boolean b) {
        if (dialog.isShowing())
            dialog.dismiss();
        for (int i = 0; i < langList.size(); i++) {
            if (i == adapterPosition) {
                selectedLang[i] = true;
            } else
                selectedLang[i] = false;
        }
        if (!langList.get(adapterPosition).getLangIcon().equalsIgnoreCase("")) {
            authPreference.savePref(Constants.APP_LNG_ICON, langList.get(adapterPosition).getLangIcon());
            Glide.with(MainActivity.this).load(Uri.parse(langList.get(adapterPosition).getLangIcon())).into(language_logo);
        }
        //Toast.makeText(getApplicationContext(),"Tab clicked",Toast.LENGTH_LONG).show();
        getLanguageData(authPreference.getPref(Constants.API_KEY), langList.get(adapterPosition).getLangCode());
        langAdapter.notifyDataSetChanged();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("===== drawer click : " + customerId + "position : " + position);
            if (customerId != null) {
                System.out.println("===== drawer click if ");
                if (!customerId.isEmpty()) {
                    System.out.println("===== drawer click 1: " + customerId + "position : " + position);
                    selectItem(position);
                }
            } else {
                System.out.println("===== drawer click else ");
                selectItem1(position);
            }
        }
    }

    private void selectItem(int position) {
        System.out.println("===== drawer click 2: " + customerId + "position : " + position);
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new RestaurantMain(currentLatitude, currentLongitude);
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 1:
                startActivity(new Intent(this, ProfileActivity.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 2:
                startActivity(new Intent(this, MyOrderActivity.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 3:
                startActivity(new Intent(this, AddressActivity.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 4:
                startActivity(new Intent(this, WriteAReviewActivity.class));   // api missing
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 5:
                startActivity(new Intent(this, TicketList.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 6:
                startActivity(new Intent(this, LoyaltyPoints.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 7:
                startActivity(new Intent(this, ChangePassword.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 8:
                startActivity(new Intent(this, ReferEarnFrndActivity.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 9:
                startActivity(new Intent(this, ContactUs.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 10:// rate us
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
//            case 11:// language setting
            //startActivity(new Intent(this, ChangePasswordActivity.class));
//                mDrawerLayout.closeDrawer(rl_main_left_drawer);
//                break;
            case 11:
                // startActivity(new Intent(this, ReferEarnActivity.class));
                logoutDialog();
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(rl_main_left_drawer);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private void selectItem1(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                startActivity(new Intent(this, ContactUs.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 1:
                startActivity(new Intent(this, AboutUsActivity.class));
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 2:
                Intent intent = new Intent(this, PrivacyAndPolicy.class);
                intent.putExtra("from", "terms");
                startActivity(intent);
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 3:
                Intent intentO = new Intent(this, PrivacyAndPolicy.class);
                intentO.putExtra("from", "privacy");
                startActivity(intentO);
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
            case 4:
                mDrawerLayout.closeDrawer(rl_main_left_drawer);
                break;
//            case 5:
//                Intent intentOrder = new Intent(this, LoyaltyPoints.class);
//                startActivity(intentOrder);
//                mDrawerLayout.closeDrawer(rl_main_left_drawer);
//                break;
//            case 5:
//                mDrawerLayout.closeDrawer(rl_main_left_drawer);
//                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(rl_main_left_drawer);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private void logoutDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Liferdeal")
                .setMessage(model.getAREYOUSUREYOUWANTTOLOGOUT())
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                try {
                                    File dir = getCacheDir();
                                    deleteDir(dir);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                // new AuthPreference(HomeActivity.this).setCustomerId("");
//                                authPreference.clearAllPref();//clear the local data
                                authPreference.loggedOut();
                                openDialog();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();

                            }
                        }).show();
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogCustomTheme);
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
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // to hide Navigation icon

    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryone));

        mDrawerToggle.syncState();
    }

    private void statusBarColor() {
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("==== onnew " + intent);
        if (intent != null) {
            String checkFromWhere = intent.getStringExtra("FROMWHERE");
            locationSearchAddress = intent.getStringExtra("SEARCHADDRESS");
            System.out.println("==== SEARCHADDRESS in new intent : " + locationSearchAddress);
            if (locationSearchAddress != null) {
                if (!locationSearchAddress.equalsIgnoreCase("")) {
                    tv_location.setText(locationSearchAddress);

                }
            }
            if (checkFromWhere != null) {
                if (checkFromWhere.equalsIgnoreCase("pagecusine")) {
                    System.out.println("==== onnew 1");
                    selected_cusines = intent.getStringArrayListExtra("SELECTEDCUSINE");
                    System.out.println("==== selected cusine in main activityu" + selected_cusines);
                    initiateRestFragment();
                } else if (checkFromWhere.equalsIgnoreCase("fromhome")) {
                    RestaurantMain locationMapFragment = new RestaurantMain(currentLatitude, currentLongitude);
                    FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_content, locationMapFragment);
                    // transaction.addToBackStack(restaurantMain.getTag());
                    transaction.commit();
                } else if (checkFromWhere.equalsIgnoreCase("fromlocation")) {
                    RestaurantMain locationMapFragment = new RestaurantMain(currentLatitude, currentLongitude);
                    FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_content, locationMapFragment);
                    // transaction.addToBackStack(restaurantMain.getTag());
                    transaction.commit();
                }
            }
        }
    }

    private void initiateRestFragment() {

        RestaurantMain restaurantMain = new RestaurantMain(currentLatitude, currentLongitude);
        Bundle args = new Bundle();
        args.putStringArrayList("SELECTEDCUSINES", selected_cusines);
        restaurantMain.setArguments(args);
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, restaurantMain);
        // transaction.addToBackStack(restaurantMain.getTag());
        transaction.commit();
    }

    private void getAddressFromCurrentLatLong(String latitudeString, String logitudeString) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitudeString), Double.parseDouble(logitudeString), 1);
            String address = addresses.get(0).getAddressLine(0);
            System.out.println("===== addresss " + address);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();

            authPreference.savePref(Constants.SAVE_FULL_ADDRESS, address);
            authPreference.savePref(Constants.SAVE_CITY_NAME, city);
            authPreference.savePref(Constants.SAVE_STATE, state);
            authPreference.savePref(Constants.SAVE_COUNTRY, country);
            authPreference.savePref(Constants.SAVE_POSTAL_CODE, postalCode);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}