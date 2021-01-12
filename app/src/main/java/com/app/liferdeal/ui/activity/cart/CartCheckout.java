package com.app.liferdeal.ui.activity.cart;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.Language;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.loginsignup.SignInModel;
import com.app.liferdeal.model.restaurant.GuestUserPaymentModel;
import com.app.liferdeal.model.restaurant.TimeListModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.NetworkUtil;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.cart.paypal.PayPalConfig;
import com.app.liferdeal.ui.activity.cart.paypal.PaypalActivity;
import com.app.liferdeal.ui.activity.login.SignInActivity;
import com.app.liferdeal.ui.activity.restaurant.TimeListActivity;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;
import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;
import com.stripe.android.view.PaymentMethodsActivityStarter;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartCheckout extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_signIn, deleviry_add, edit_post_code, edt_city_name, edit_company_name, edit_add_note, edit_full_name,
            edit_email_add, edit_mobile_no, btn_order_placed, txt_selected_time, tvCheckout;
    private LinearLayout ll_delivery_time;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private ProgressDialog progressDialog;
    private RadioButton rd_cash, rd_paypal, rd_card;
    private ImageView img_back;
    private String strPostalCode = "", strCityname = "", strFullAddress = "", restId = "", TotalPrice = "", currencySymbol = "", pizzaQuantity = "", Pizzaname = "", selectedPizzaItemPrice = "";
    private String strMainRestName = "", strMainRestLogo = "", pizzaItemid = "", subTotalPrice = "", strAppCurrency = "";
    private String payment_transaction_paypal = "", quantity, deliveryChargeValue = "", SeviceFeesValue = "", ServiceFees = "", ServiceFeesType = "", PackageFeesType = "", PackageFees = "", PackageFeesValue = "", SalesTaxAmount = "", VatTax = "";
    private String strsizeid, extraItemID, CustomerId, CustomerAddressId, payment_type, order_price, subTotalAmount, delivery_date, delivery_time, instructions, deliveryCharge,
            CouponCode, CouponCodePrice, couponCodeType, order_type, SpecialInstruction, extraTipAddAmount, RestaurantNameEstimate, discountOfferDescription, discountOfferPrice, RestaurantoffrType,
            PaymentProcessingFees, deliveryChargeValueType, WebsiteCodePrice, WebsiteCodeType, WebsiteCodeNo, preorderTime,
            GiftCardPay, WalletPay, loyptamount, table_number_assign, customer_country, group_member_id, loyltPnts, branch_id, FoodCosts,
            getTotalItemDiscount, getFoodTaxTotal7, getFoodTaxTotal19, TotalSavedDiscount, discountOfferFreeItems, number_of_people, customer_allow_register, address,
            street, floor_no, zipcode, phone, email, name_customer, city, mealID, mealquqntity, mealPrice, mealItemExtra, mealItemOption;

    DotToCommaClass dotToCommaClass;
    private AppCompatTextView tvHaveAccount, tvDeliverAdd, tvPersonalDetails, tvplacing, note2, tvPlsSelectTime, tvPayWith, tvPaypal, tvByClicking, tvToAid;
    private SharedPreferencesData sharedPreferencesData;
    private TextInputLayout ipStreet, ipPostCode, ipCompany, ipCityName, ipNote, ipFullName, ipEmail, ipMobile;

    @BindView(R.id.rlPaypal)
    RelativeLayout rlPaypal;
    @BindView(R.id.rlCash)
    RelativeLayout rlCash;
    @BindView(R.id.rlCard)
    RelativeLayout rlCard;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_layout);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(CartCheckout.this) != null) {
            model = App.retrieveLangFromGson(CartCheckout.this);
        }
        init();
    }

    private void init() {

        sharedPreferencesData = new SharedPreferencesData(getApplicationContext());
        dotToCommaClass = new DotToCommaClass(getApplicationContext());
        prefsHelper = new PrefsHelper(this);
        progressDialog = new ProgressDialog(this);
        tv_signIn = findViewById(R.id.tv_signIn);
        deleviry_add = findViewById(R.id.deleviry_add);
        edit_post_code = findViewById(R.id.edit_post_code);
        edt_city_name = findViewById(R.id.edt_city_name);
        edit_company_name = findViewById(R.id.edit_company_name);
        edit_add_note = findViewById(R.id.edit_add_note);
        edit_full_name = findViewById(R.id.edit_full_name);
        edit_email_add = findViewById(R.id.edit_email_add);
        edit_mobile_no = findViewById(R.id.edit_mobile_no);
        ll_delivery_time = findViewById(R.id.ll_delivery_time);
        btn_order_placed = findViewById(R.id.btn_order_placed);
        txt_selected_time = findViewById(R.id.txt_selected_time);
        img_back = findViewById(R.id.img_back);
        rd_cash = findViewById(R.id.rd_cash);
        rd_paypal = findViewById(R.id.rd_paypal);
        rd_card = findViewById(R.id.rd_card);
        tvCheckout = findViewById(R.id.tvCheckout);
        tvHaveAccount = findViewById(R.id.tvHaveAccount);
        tvDeliverAdd = findViewById(R.id.tvDeliverAdd);
        tvPersonalDetails = findViewById(R.id.tvPersonalDetails);
        tvplacing = findViewById(R.id.tvplacing);
        note2 = findViewById(R.id.note2);
        tvPlsSelectTime = findViewById(R.id.tvPlsSelectTime);
        tvPayWith = findViewById(R.id.tvPayWith);
        tvPaypal = findViewById(R.id.tvPaypal);
        tvByClicking = findViewById(R.id.tvByClicking);
        tvToAid = findViewById(R.id.tvToAid);
        ipStreet = findViewById(R.id.ipStreet);
        ipPostCode = findViewById(R.id.ipPostCode);
        ipCityName = findViewById(R.id.ipCityName);
        ipCompany = findViewById(R.id.ipCompany);
        ipNote = findViewById(R.id.ipNote);
        ipFullName = findViewById(R.id.ipFullName);
        ipEmail = findViewById(R.id.ipEmail);
        ipMobile = findViewById(R.id.ipMobile);


        tvCheckout.setText(model.getCheckout());
        tv_signIn.setText(model.getSignIn());
        tvHaveAccount.setText(model.getDoYouHaveAnAccount());
        tvDeliverAdd.setText(model.getDeliveryAddress());
        ipStreet.setHint(model.getStreetAndHouseNumber());
        ipPostCode.setHint(model.getPostalCode());
        ipCityName.setHint(model.getCityName());
        ipCompany.setHint(model.getCompanyName());
        ipNote.setHint(model.getAddNoteForDelivery());
        ipFullName.setHint(model.getFullName());
        tvPersonalDetails.setText(model.getPersonalDetails());
        ipEmail.setHint(model.getEmailAddress());
        ipMobile.setHint(model.getMobileNo());
        txt_selected_time.setHint(model.getSelectDeliveryTime());
        txt_selected_time.setText(model.getASSOONASPOSSIBLE());
        tvplacing.setText(model.getWhenPlacingAnOrder());
        note2.setText(model.getSignUpToReceiceMessage());
        tvPlsSelectTime.setText(model.getPleaseSelectADeliveryTimeForYourOrder());
        tvPayWith.setText(model.getPayWith());
        tvPaypal.setText(model.getPayingWithPaypalFree());
        tvByClicking.setText(model.getByClickingOnOrderAndPay());
        tvToAid.setText(model.getToAidInThePrevention());
        rd_cash.setText(model.getCash());
        rd_card.setText(model.getCard());
        rd_paypal.setText(model.getPaypal());


        strPostalCode = prefsHelper.getPref(Constants.SAVE_POSTAL_CODE);
        strCityname = prefsHelper.getPref(Constants.SAVE_CITY_NAME);
        strFullAddress = prefsHelper.getPref(Constants.SAVE_FULL_ADDRESS);
        strAppCurrency = prefsHelper.getPref(Constants.APP_CURRENCY);
        edit_post_code.setText(strPostalCode);
        edt_city_name.setText(strCityname);
        deleviry_add.setText(strFullAddress);
        restId = getIntent().getStringExtra("RestId");
        TotalPrice = getIntent().getStringExtra("TotalPrice");
        order_price = TotalPrice;
        subTotalPrice = getIntent().getStringExtra("SubTotalPrice");
        subTotalAmount = subTotalPrice;
        strMainRestName = getIntent().getStringExtra("RESTName");
        strMainRestLogo = getIntent().getStringExtra("RESTLOGO");
        pizzaItemid = getIntent().getStringExtra("subPizzaItemId");
        strsizeid = getIntent().getStringExtra("SIZEITEMID");
        extraItemID = getIntent().getStringExtra("globTempExtraItemidWithSizeIdd");
        delivery_date = getIntent().getStringExtra("delivery_date");
        FoodCosts = getIntent().getStringExtra("food_cost");
        quantity = getIntent().getStringExtra("quantity");
        deliveryChargeValue = getIntent().getStringExtra("deliveryChargeValue");
        SeviceFeesValue = getIntent().getStringExtra("SeviceFeesValue");
        ServiceFees = getIntent().getStringExtra("ServiceFees");
        ServiceFeesType = getIntent().getStringExtra("ServiceFeesType");
        PackageFeesType = getIntent().getStringExtra("PackageFeesType");
        PackageFees = getIntent().getStringExtra("PackageFees");
        PackageFeesValue = getIntent().getStringExtra("PackageFeesValue");
        SalesTaxAmount = getIntent().getStringExtra("SalesTaxAmount");
        VatTax = getIntent().getStringExtra("VatTax");
        order_type = getIntent().getStringExtra("deliveryType");
        pizzaQuantity = getIntent().getStringExtra("pizzaQuantity");
        Pizzaname = getIntent().getStringExtra("Pizzaname");
        selectedPizzaItemPrice = getIntent().getStringExtra("selectedPizzaItemPrice");
        instructions = getIntent().getStringExtra("instructions");
        customer_allow_register = "yes";
        payment_type = "cash";
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
        btn_order_placed.setText(model.getOrderAndPay() + ": " + currencySymbol + " " + "" + dotToCommaClass.changeDot(String.format("%.2f", Double.parseDouble(TotalPrice))));

        if (prefsHelper.isLoggedIn()) {
            CustomerId = prefsHelper.getPref(Constants.CUSTOMER_ID);
            CustomerAddressId = prefsHelper.getPref(Constants.CUSTOMER_ADDRESS_ID);
        }
        btn_order_placed.setOnClickListener(this);
        tv_signIn.setOnClickListener(this);
        ll_delivery_time.setOnClickListener(this);
        img_back.setOnClickListener(this);
        rd_cash.setOnClickListener(this);
        rd_paypal.setOnClickListener(this);
        rd_card.setOnClickListener(this);

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.FORCASHONDELIVERY).equalsIgnoreCase("no")) {
            rlCash.setVisibility(View.GONE);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.PAYPALPAY).equalsIgnoreCase("no")) {
            rlPaypal.setVisibility(View.GONE);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.CARDPAY).equalsIgnoreCase("no")) {
            rlCard.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signIn:
                Intent intent = new Intent(CartCheckout.this, SignInActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_order_placed:
                zipcode = CommonMethods.getStringDataInbase64(edit_post_code.getText().toString());
                city = CommonMethods.getStringDataInbase64(edt_city_name.getText().toString());
                address = CommonMethods.getStringDataInbase64(deleviry_add.getText().toString());
                System.out.println("==== zipcode in base64" + zipcode);
                name_customer = CommonMethods.getStringDataInbase64(edit_full_name.getText().toString().trim());
                email = edit_mobile_no.getText().toString().trim();
                phone = CommonMethods.getStringDataInbase64(edit_mobile_no.getText().toString().trim());
                delivery_time = txt_selected_time.getText().toString();
                if (edit_full_name.getText().toString().equalsIgnoreCase("")) {
                    edit_full_name.setError(model.getPleaseEnterFullName());
                    edit_full_name.requestFocus();
                } else if (edit_email_add.getText().toString().equalsIgnoreCase("")) {
                    edit_email_add.setError(model.getPleaseEnterYourEmail());
                    edit_email_add.requestFocus();
                } else if (!isValidEmailId(edit_email_add.getText().toString().trim())) {
                    edit_email_add.setError(model.getPLEASEENTERVALIDEMAIL());
                    edit_email_add.requestFocus();
                } /*else if (!isValidEmailId(city)) {
                    edt_city_name.setError(model.getCITYNAMEFIELDISREQUIRED());
                    edt_city_name.requestFocus();
                }*/ else if (edit_mobile_no.getText().toString().equalsIgnoreCase("")) {
                    edit_mobile_no.setError(model.getMOBILENOFIELDISREQUIRED());
                    edit_mobile_no.requestFocus();
                } else if (txt_selected_time.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(CartCheckout.this, model.getPleaseSelectDeliveryTime(), Toast.LENGTH_SHORT).show();
                } else if (payment_type.equalsIgnoreCase("")) {
                    Toast.makeText(CartCheckout.this, model.getPleaseSelectPaymentType(), Toast.LENGTH_SHORT).show();

                } else if (payment_type.equalsIgnoreCase("paypal")) {
                  /*  Intent intent=new Intent(getApplicationContext(), PaypalActivity.class);
                    startActivity(intent);*/
                    getPayment();
                } else if (payment_type.equalsIgnoreCase("card")) {
                   /* Intent intent=new Intent(getApplicationContext(), StripePaymentActivity.class);
                    startActivity(intent);*/
                    dialogOpen();
                } else {
                    getPaymentRequestData();
                }

               /* if (payment_type.equalsIgnoreCase("paypal")){
                    Intent intent=new Intent(getApplicationContext(), PaypalActivity.class);
                    startActivity(intent);
                }else if (payment_type.equalsIgnoreCase("card")){
                    Intent intent=new Intent(getApplicationContext(), StripePaymentActivity.class);
                    startActivity(intent);
                }*/
                break;

            case R.id.img_back:
                finish();
                break;

            case R.id.ll_delivery_time:
                showProgress();
                getTimeSlotData();
                /*Intent i = new Intent(CartCheckout.this, TimeListActivity.class);
                i.putExtra("RestId", restId);
                i.putExtra("OrderType", order_type);
                startActivityForResult(i, 10);*/
                // startActivity(i);
                break;

            case R.id.rd_cash:

                payment_type = "cash";
                rd_cash.setChecked(true);
                rd_paypal.setChecked(false);
                rd_card.setChecked(false);
                break;

            case R.id.rd_paypal:
                payment_type = "paypal";
                rd_cash.setChecked(false);
                rd_paypal.setChecked(true);
                rd_card.setChecked(false);
                break;

            case R.id.rd_card:
                payment_type = "card";
                rd_cash.setChecked(false);
                rd_paypal.setChecked(false);
                rd_card.setChecked(true);
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


    //stripe payment code for success
    Dialog dialog;

    private void dialogOpen() {
        dialog = new Dialog(CartCheckout.this);
        dialog.setContentView(R.layout.dialog_stripe);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        CardMultilineWidget card_details = dialog.findViewById(R.id.card_details);
        Button btnPay = dialog.findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPaymentcall(card_details);
            }
        });


        dialog.show();
    }

    private void createPaymentcall(CardMultilineWidget card_details) {
        showProgress();
        //pk_test_51I04dtKD2jSEa72lEFrtZpRLIdB41dgp5cv421yfPcQ2bPjW6dXswhLYlZoSUTuhbGyMtHjI7n4dMHCcp4N8gqKf00kKQTk8UX
        // live==  pk_live_51H335kI4oh76Z6dpfFRYuAHNcBAQtEZGtf6D7Hs2IG92vaM0x9Do2YFgNBmFNUx5d7fdAv9zsHyUxPjkydKfUCEX00j0eCL1ae
        Stripe stripe = new Stripe(CartCheckout.this, "pk_test_51H335kI4oh76Z6dpZGTM13kKY5tMuzpQpGAzDOxhjLIHvzgD3IUWsznINS83NYvmTtXWOugAVvlnMfIDC5c8X2cm00V8TXD3tL");
        final Card cardToSave = card_details.getCard();
        if (cardToSave != null) {
            //Toast.makeText(getApplicationContext(),getResources().getString(R.string.wait),Toast.LENGTH_LONG).show();
            //progressBar.setVisibility(View.VISIBLE);
           /* stripe.createAccountToken(, "", "", new ApiResultCallback<Token>() {
                @Override
                public void onSuccess(@NotNull Token token) {

                }

                @Override
                public void onError(@NotNull Exception e) {

                }
            });*/
            stripe.createToken(cardToSave, new ApiResultCallback<Token>() {
                @Override
                public void onError(Exception error) {
                    Toast.makeText(CartCheckout.this, "Please try again", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(Token token) {
                    if (NetworkUtil.checkNetworkStatus(CartCheckout.this)) {
                        String description = "Payment success";
                        Log.e("getTokenId=", token.getId());
                        //Toast.makeText(CartCheckout.this, token.getId(), Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.VISIBLE);
                        //stripePayment(token.getId());

                        //hideProgress();
                        //dialog.cancel();
                        stripePayment(token.getId());
                        //here we send the token generated by strip to server for payment
                        //
                        //
                        //
                        //RetrofitHelper.getInstance().doStripePayment(stripePaymentCallback, user_id, token.getId(), amount, "1",package_id,"THis is for test");
                    } else {

                    }
                }
            });
        }
    }

    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE) {
                final PaymentMethodsActivityStarter.Result result =
                        PaymentMethodsActivityStarter.Result.fromIntent(data);
                final PaymentMethod paymentMethod = result != null ?
                        result.paymentMethod : null;

                // use paymentMethod
            }
        }*/
    // for paypal integration in app
    public static final int PAYPAL_REQUEST_CODE = 123;
    public static final int STRIPE_REQUEST_CODE = 125;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

    private void getPayment() {

        //Getting the amount from editText
        //paymentAmount = editTextAmount.getText().toString();

        //Creating a paypalpayment
        // Toast.makeText(getApplicationContext(),strAppCurrency,Toast.LENGTH_LONG).show();
        PayPalPayment payment = new PayPalPayment(new BigDecimal(subTotalPrice), strAppCurrency, "Total Amount",
                PayPalPayment.PAYMENT_INTENT_SALE);


        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                String gettime = data.getStringExtra("gettime");
                System.out.println("==== gettime : " + gettime);
                txt_selected_time.setText(gettime);
            }
        } else if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE) {
            final PaymentMethodsActivityStarter.Result result =
                    PaymentMethodsActivityStarter.Result.fromIntent(data);
            final PaymentMethod paymentMethod = result != null ?
                    result.paymentMethod : null;
            Toast.makeText(CartCheckout.this, "On Activity Result Called", Toast.LENGTH_SHORT).show();
            // use paymentMethod
        } else if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                       /* startActivity(new Intent(this, PaypalActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", 100));*/
                        getPaymentRequestData();

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("==== intent : " + intent);
    }

    private void getPaymentRequestData() {

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<GuestUserPaymentModel> observable = apiInterface.getPaymentGuestData(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), payment_transaction_paypal, pizzaItemid, quantity, TotalPrice, strsizeid, extraItemID, CustomerId, CustomerAddressId, payment_type, order_price, subTotalAmount, delivery_date, delivery_time, instructions, deliveryCharge,
                CouponCode, CouponCodePrice, couponCodeType, SalesTaxAmount, order_type, SpecialInstruction, extraTipAddAmount, RestaurantNameEstimate, discountOfferDescription, discountOfferPrice, RestaurantoffrType,
                ServiceFees, PaymentProcessingFees, deliveryChargeValueType, ServiceFeesType, PackageFeesType, PackageFees, WebsiteCodePrice, WebsiteCodeType, WebsiteCodeNo, preorderTime,
                VatTax, GiftCardPay, WalletPay, loyptamount, table_number_assign, customer_country, group_member_id, loyltPnts, branch_id, FoodCosts,
                getTotalItemDiscount, getFoodTaxTotal7, getFoodTaxTotal19, TotalSavedDiscount, discountOfferFreeItems, number_of_people, customer_allow_register, address,
                street, floor_no, zipcode, phone, email, name_customer, city, restId, mealID, mealquqntity, mealPrice, mealItemExtra, mealItemOption);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GuestUserPaymentModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GuestUserPaymentModel searchResult) {
                        showProgress();
                        if (searchResult.getSuccess() == 1) {
                            System.out.println("==== success");
                            String restname = searchResult.getRestaurantName().toString();
                            String restTime = searchResult.getRequestTime().toString();
                            String deliveryDate = searchResult.getDeliverydate().toString();
                            String customeName = searchResult.getNameCustomer();
                            String orderNumber = searchResult.getOrderNo();
                            String orderType = searchResult.getOrderType();
                            String oldprice = searchResult.getOrdPrice();
                            if (CartActivity.mInstance != null) {
                                CartActivity.mInstance.finish();
                            }
                            Intent ii = new Intent(CartCheckout.this, ThankyouPageActivity.class);
                            ii.putExtra("restname", restname);
                            ii.putExtra("restTime", restTime);
                            ii.putExtra("deliveryDate", deliveryDate);
                            ii.putExtra("customeName", customeName);
                            ii.putExtra("orderNumber", orderNumber);
                            ii.putExtra("orderType", orderType);
                            ii.putExtra("oldprice", oldprice);
                            ii.putExtra("strMainRestLogo", strMainRestLogo);
                            ii.putExtra("pizzaQuantity", quantity);
                            ii.putExtra("Pizzaname", Pizzaname);
                            ii.putExtra("selectedPizzaItemPrice", selectedPizzaItemPrice);

                            startActivity(ii);
                            finish();
                            hideProgress();
                        }

                        //  setAdapterCategory(searchResult.getRestaurantMencategory());
                       /* if (searchResult.getRestaurantMencategory().get(0).getError()==1)
                        {

                            Toast.makeText(RestaurantDetails.this, "There is no Details", Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                        else
                        {
                            setAdapterCategoryForQuick(searchResult.getRestaurantMencategory());                        }

                    }*/
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
            progressDialog.setMessage(model.getPlease_wait_text().trim()+"...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void hideProgress() {
        try {
            progressDialog.dismiss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //stripe payment token send to server for success
    private void stripePayment(String stripeToken) {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<StripeModel> observable = apiInterface.stripePaymentFromServer(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), subTotalAmount, strAppCurrency, stripeToken, email);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StripeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StripeModel signin) {
                        if (signin.getResponse().equalsIgnoreCase("success")) {
                            getPaymentRequestData();
                        } else {
                            hideProgress();
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), signin.getMessage(), Toast.LENGTH_LONG).show();
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


    private void dialogTimeSelection(List<TimeListModel.TimeList> sessionTypeMainData) {

        dialog = new Dialog(CartCheckout.this);
        dialog.setContentView(R.layout.dialog_session_type);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        TextView tvDone = dialog.findViewById(R.id.tvDone);
        tvDone.setText(model.getDone());
        tvCancel.setText(model.getCancel());
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (sessionType.equalsIgnoreCase("")){
                    sessionType= sessionTypeMainData.get(0).getId();
                    tvSessionType.setText(sessionTypeMainData.get(0).getSessionType());
                }*/
                // txt_selected_time.setText();
                dialog.cancel();
            }
        });

        WheelView wvSessionType = dialog.findViewById(R.id.wvSessionType);
        wvSessionType.setCyclic(false);

        /*final List<String> mOptionsItems = new ArrayList<>();
        for (int i=0;i<sessionTypeMainData.size();i++){
            mOptionsItems.add("item0");
        }*/

        wvSessionType.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return sessionTypeMainData.size();
            }

            @Override
            public Object getItem(int index) {
                return sessionTypeMainData.get(index).getGetTime();
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        });
        wvSessionType.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                txt_selected_time.setText(sessionTypeMainData.get(index).getGetTime());
                //Toast.makeText(getApplicationContext(), "" + sessionTypeMainData.get(index).getSessionType(), Toast.LENGTH_SHORT).show();
              /*  sessionType = sessionTypeMainData.get(index).getId();
                tvSessionType.setText(sessionTypeMainData.get(index).getSessionType());*/

            }
        });


        dialog.show();
    }


    private void getTimeSlotData() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<TimeListModel> observable = apiInterface.getTimeListData(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE),
                restId, order_type, formattedDate);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TimeListModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TimeListModel searchResult) {
                //showProgress();
                hideProgress();
                if (searchResult.getTimeList() != null && searchResult.getTimeList().size() > 0) {
                    //setAdapterCategoryForQuick(searchResult.getTimeList());
                    //banner_progress.setVisibility(View.GONE);
                    List<TimeListModel.TimeList> list = new ArrayList<TimeListModel.TimeList>();
                    list.clear();
                    TimeListModel.TimeList newList = new TimeListModel.TimeList();
                    newList.setGetTime(model.getASSOONASPOSSIBLE());
                    newList.setSuccess("true");
                    list.add(newList);
                    list.addAll(searchResult.getTimeList());
                    dialogTimeSelection(list);
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
}
