package com.app.liferdeal.ui.activity.cart;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.RaviCartModle;
import com.app.liferdeal.model.restaurant.TimeListModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.NetworkUtil;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.activity.cart.paypal.PayPalConfig;
import com.app.liferdeal.ui.activity.restaurant.TimeListActivity;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;
import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
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
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PayCheckOutActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.rl_header)
    CardView rlHeader;
    @BindView(R.id.tv)
    MaterialTextView tv;
    @BindView(R.id.tvToPayPrice)
    MaterialTextView tvToPayPrice;
    @BindView(R.id.txtSelectPaymentType)
    MaterialTextView txtSelectPaymentType;
    @BindView(R.id.radioButtonASAP)
    MaterialRadioButton radioButtonASAP;
    @BindView(R.id.radioButtonLater)
    MaterialRadioButton radioButtonLater;
    @BindView(R.id.textViewDeliverySchedule)
    MaterialTextView textViewDeliverySchedule;
    @BindView(R.id.linearLayoutTime)
    LinearLayout linearLayoutTime;
    @BindView(R.id.cardViewOrderTime)
    CardView cardViewOrderTime;
    @BindView(R.id.radioButtonCreditDebitCard)
    RadioButton radioButtonCreditDebitCard;
    @BindView(R.id.txtCreditDebitCard)
    MaterialTextView txtCreditDebitCard;
    @BindView(R.id.cardViewCreditDebitCard)
    CardView cardViewCreditDebitCard;
    @BindView(R.id.radioButtonPaypal)
    RadioButton radioButtonPaypal;
    @BindView(R.id.txtPaypal)
    MaterialTextView txtPaypal;
    @BindView(R.id.cardViewPaypal)
    CardView cardViewPaypal;
    @BindView(R.id.radioButtonCashOnDelivery)
    RadioButton radioButtonCashOnDelivery;
    @BindView(R.id.textViewCashOnDelivery)
    MaterialTextView textViewCashOnDelivery;
    @BindView(R.id.cardViewCashOnDelivery)
    CardView cardViewCashOnDelivery;
    @BindView(R.id.radioButtonPayLater)
    RadioButton radioButtonPayLater;
    @BindView(R.id.cardViewPayLater)
    CardView cardViewPayLater;
    @BindView(R.id.cardViewGallery)
    CardView cardViewGallery;
    @BindView(R.id.txtNoteText)
    MaterialTextView txtNoteText;
    @BindView(R.id.editTextInstruction)
    EditText editTextInstruction;
    @BindView(R.id.textViewAllergy)
    MaterialTextView textViewAllergy;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.buttonMakePayment)
    MaterialButton buttonMakePayment;
    @BindView(R.id.tvPayment)
    TextView tvPayment;
    @BindView(R.id.tvSelectPayment)
    MaterialTextView tvSelectPayment;
    @BindView(R.id.rgFood)
    RadioGroup rgFood;
    @BindView(R.id.tvLeaveMsg)
    AppCompatTextView tvLeaveMsg;
    private String allergy_Data="";
    Database database;
    ArrayList<RaviCartModle> raviCartModles;
    private String totalPrice = "";
    List<TimeListModel.TimeList> list = new ArrayList<TimeListModel.TimeList>();

    SharedPreferencesData sharedPreferencesData;
    private LanguageResponse model = new LanguageResponse();
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    private String strPostalCode = "", strCityname = "", strFullAddress = "", restId = "", TotalPrice = "", currencySymbol = "", pizzaQuantity = "", Pizzaname = "", selectedPizzaItemPrice = "";
    private String strMainRestName = "", strMainRestLogo = "", pizzaItemid = "", subTotalPrice = "", emailId, strAppCurrency = "";
    private String payment_transaction_paypal = "", quantity = "", deliveryChargeValue = "", SeviceFeesValue = "", ServiceFees = "", ServiceFeesType = "", PackageFeesType = "", PackageFees = "", PackageFeesValue = "", SalesTaxAmount = "", VatTax = "";
    private String strsizeid;
    private String extraItemID;
    private String CustomerId;
    private String CustomerAddressId;
    private String payment_type;
    private String order_price;
    private String subTotalAmount;
    private String delivery_date;
    private String delivery_time = "";
    private String instructions = "";
    private String deliveryCharge;
    private String CouponCode = "";
    private String CouponCodePrice = "";
    private String couponCodeType = "";
    private String order_type = "";
    private String SpecialInstruction = "";
    private String extraTipAddAmount = "";
    private String RestaurantNameEstimate = "";
    private String discountOfferDescription = "";
    private String discountOfferPrice = "";
    private String RestaurantoffrType = "";
    private String PaymentProcessingFees = "";
    private String deliveryChargeValueType = "";
    private String WebsiteCodePrice = "";
    private String WebsiteCodeType = "";
    private String WebsiteCodeNo = "";
    private String preorderTime = "";
    private String addressId = "";
    private String GiftCardPay = "";
    private String WalletPay = "";
    private String loyptamount = "";
    private String table_number_assign = "";
    private String customer_country = "";
    private String group_member_id = "";
    private String loyltPnts = "";
    private String branch_id = "";
    private String FoodCosts = "";
    private String getTotalItemDiscount = "";
    private String getFoodTaxTotal7 = "";
    private String getFoodTaxTotal19 = "";
    private String TotalSavedDiscount = "";
    private String discountOfferFreeItems = "";
    private String number_of_people = "";
    private String customer_allow_register = "";
    private String address = "";
    private String mealID = "";
    private String mealquqntity = "";
    private String mealPrice = "";
    private String mealItemExtra = "";
    private String mealItemOption = "";
    private String item_Id;
    DotToCommaClass dotToCommaClass;
    String Price;
    String extraItemId1;
    String extraItemId2, extra_toppings="";
    String rest_address="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_checkout);
        ButterKnife.bind(this);

        prefsHelper = new PrefsHelper(this);
        progressDialog = new ProgressDialog(this);
        dotToCommaClass = new DotToCommaClass(getApplicationContext());
        database = new Database(PayCheckOutActivity.this);
        raviCartModles=new ArrayList<>();

        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();

        if (App.retrieveLangFromGson(PayCheckOutActivity.this) != null) {
            model = App.retrieveLangFromGson(PayCheckOutActivity.this);
        }

        tvPayment.setText(model.getPayment().trim());
        tv.setText(model.getAmountToBePaid().trim());
        txtSelectPaymentType.setText(model.getWhenWouldYouLikeYourFood().trim());
        radioButtonASAP.setText(model.getAsap().trim());
        radioButtonLater.setText(model.getLater().trim());
        tvSelectPayment.setText(model.getPleaseSelectPaymentType());
        txtCreditDebitCard.setText(model.getCreditDebitCard().trim());
        txtPaypal.setText(model.getPaypal().trim());
        textViewCashOnDelivery.setText(model.getCashOnDelivery().trim());
        txtNoteText.setText(model.getAllergyDetails().trim());
        editTextInstruction.setHint(model.getSpecialInstructions().trim());
        textViewAllergy.setText(model.getDoYouHaveAnyAllergy().trim());
        buttonMakePayment.setText(model.getMakeAPayment().trim());
        tvLeaveMsg.setText(model.getLeaveANoteForTheRestaurantText().trim());


        if (getIntent() != null) {



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
            addressId = getIntent().getStringExtra("addressId");
            instructions = getIntent().getStringExtra("instructions");
            customer_allow_register = "yes";
            rest_address=getIntent().getStringExtra("rest_address");
            payment_type = "Cash";
        }

        sharedPreferencesData = new SharedPreferencesData(getApplicationContext());

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.FORCASHONDELIVERY).equalsIgnoreCase("no")) {
            cardViewCashOnDelivery.setVisibility(View.GONE);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.PAYPALPAY).equalsIgnoreCase("no")) {
            cardViewPaypal.setVisibility(View.GONE);
        }

        if (sharedPreferencesData.getSharedPreferenceData(Constants.FORDELIVERY, Constants.CARDPAY).equalsIgnoreCase("no")) {
            cardViewCreditDebitCard.setVisibility(View.GONE);
        }
        viewFinds();
        callTimeApi();
        getCartData();
    }


    private void viewFinds() {
        cardViewCreditDebitCard.setOnClickListener(this);
        cardViewPaypal.setOnClickListener(this);
        cardViewCashOnDelivery.setOnClickListener(this);
        radioButtonPayLater.setOnClickListener(this);
        buttonMakePayment.setOnClickListener(this);
        rlBack.setOnClickListener(this);


      /*  if (App.retrieveLangFromGson(PayCheckOutActivity.this) != null) {
            model = App.retrieveLangFromGson(PayCheckOutActivity.this);
        }*/
        init();
    }

    private void init() {
      /*  tv_signIn = findViewById(R.id.tv_signIn);
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
*/

        emailId = prefsHelper.getPref(Constants.USER_EMAIL);
        strPostalCode = prefsHelper.getPref(Constants.SAVE_POSTAL_CODE);
        strCityname = prefsHelper.getPref(Constants.SAVE_CITY_NAME);
        strFullAddress = prefsHelper.getPref(Constants.SAVE_FULL_ADDRESS);
        strAppCurrency = prefsHelper.getPref(Constants.APP_CURRENCY);

      /*  edit_post_code.setText(strPostalCode);
        edt_city_name.setText(strCityname);
        deleviry_add.setText(strFullAddress);
*/
        restId = getIntent().getStringExtra("RestId");
        TotalPrice = getIntent().getStringExtra("TotalPrice");
        if(TotalPrice!=null){
            tvToPayPrice.setText(currencySymbol + dotToCommaClass.changeDot(String.format("%.2f",Double.parseDouble(TotalPrice))));
        }
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
        payment_type = "Cash";

        //btn_order_placed.setText("Order on pay : " + currencySymbol + " " + "" + String.format("%.2f", Double.parseDouble(TotalPrice)));

        if (prefsHelper.isLoggedIn()) {
            CustomerId = prefsHelper.getPref(Constants.CUSTOMER_ID);
//            CustomerAddressId = prefsHelper.getPref(Constants.CUSTOMER_ADDRESS_ID);
        }
      /*  btn_order_placed.setOnClickListener(this);
        tv_signIn.setOnClickListener(this);
        ll_delivery_time.setOnClickListener(this);
        img_back.setOnClickListener(this);
        rd_cash.setOnClickListener(this);
        rd_paypal.setOnClickListener(this);
        rd_card.setOnClickListener(this);*/

        rgFood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonASAP) {

                } else if (checkedId == R.id.radioButtonLater) {
                   /* Intent i = new Intent(PayCheckOutActivity.this, TimeListActivity.class);
                    i.putExtra("RestId", restId);
                    i.putExtra("OrderType", order_type);
                    startActivityForResult(i, 10);*/
                    dialogTimeSelection(list);

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                String gettime = data.getStringExtra("gettime");
                System.out.println("==== gettime : " + gettime);
                delivery_time = gettime;
//                txt_selected_time.setText(gettime);
            }
        } else if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE) {
            final PaymentMethodsActivityStarter.Result result =
                    PaymentMethodsActivityStarter.Result.fromIntent(data);
            final PaymentMethod paymentMethod = result != null ?
                    result.paymentMethod : null;
            Toast.makeText(PayCheckOutActivity.this, "On Activity Result Called", Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButtonASAP:
                Intent i = new Intent(PayCheckOutActivity.this, TimeListActivity.class);
                i.putExtra("RestId", restId);
                i.putExtra("OrderType", order_type);
                startActivityForResult(i, 10);
                break;
            case R.id.radioButtonLater:

                break;
            case R.id.cardViewCreditDebitCard:
                // for stripe payment
                //Toast.makeText(getApplicationContext(),"Test called",Toast.LENGTH_LONG).show();
                payment_type = "card";
                radioButtonCreditDebitCard.setChecked(true);
                radioButtonPaypal.setChecked(false);
                radioButtonCashOnDelivery.setChecked(false);
                radioButtonPayLater.setChecked(false);
                break;
            case R.id.cardViewPaypal:
                //for paypal payment
                payment_type = "paypal";
                radioButtonCreditDebitCard.setChecked(false);
                radioButtonPaypal.setChecked(true);
                radioButtonCashOnDelivery.setChecked(false);
                radioButtonPayLater.setChecked(false);
                break;
            case R.id.cardViewCashOnDelivery:
                //for cash on delivery payment
                payment_type = "Cash";
                radioButtonCreditDebitCard.setChecked(false);
                radioButtonPaypal.setChecked(false);
                radioButtonCashOnDelivery.setChecked(true);
                radioButtonPayLater.setChecked(false);
                break;
            case R.id.radioButtonPayLater:
                // for payment later
                radioButtonCreditDebitCard.setChecked(false);
                radioButtonPaypal.setChecked(false);
                radioButtonCashOnDelivery.setChecked(false);
                radioButtonPayLater.setChecked(true);

                break;
            case R.id.editTextInstruction:

                break;
            case R.id.buttonMakePayment:

                if (payment_type.equalsIgnoreCase("card")) {
                    dialogOpen();
                } else if (payment_type.equalsIgnoreCase("paypal")) {
                    getPayment();
                } else if (payment_type.equalsIgnoreCase("cash")) {
                    SpecialInstruction = CommonMethods.getStringDataInbase64(editTextInstruction.getText().toString().trim());
                    getPaymentRequestData();
                    break;
                }
            case R.id.rl_back:
                finish();
                break;

        }
    }

    private void getPaymentRequestData() {
        showProgress();
//        Price =\(itemPrice) & CouponCode =\(self.couponCode) & CouponCodePrice =\
//        (couponAmount) & couponCodeType = &
//        SpecialInstruction = SPECIAL_INSTRUCTION & extraTipAddAmount =\
//        (tipAmount) & RestaurantNameEstimate =&discountOfferDescription =&discountOfferPrice =\
//        (restaurantDiscountAmount) & RestaurantoffrType =&ServiceFees =\
//        (serviceFeesAmount) & PaymentProcessingFees =\
//        (paymentProcessigFees) & deliveryChargeValueType =&ServiceFeesType =&PackageFeesType =&
//        PackageFees =\(packageFeesAmount) & WebsiteCodePrice =&WebsiteCodeType =&WebsiteCodeNo =&
//        preorderTime =&VatTax =\(vatTaxAmount) & GiftCardPay =&WalletPay =&loyptamount =\
//        (loyaltyPointsAmount) & table_number_assign =\(tableID) & customer_country =&
//        group_member_id =&loyltPnts =\(loyaltyPoints) & branch_id = 0 & FoodCosts =\
//        (foodCost) & getTotalItemDiscount =\(getTotalItemDiscount) & getFoodTaxTotal7 =\
//        (getFoodTaxTotal7) & getFoodTaxTotal19 =\(getFoodTaxTotal19) & TotalSavedDiscount =\
//        (totalSavedDiscount) & discountOfferFreeItems =\
//        (discountOfferFreeItems) & number_of_people =\(personCount) & resid =\
//        (self.restaurantInfo.restaurantId) & mealID =\(mealItemID) & mealquqnti0ty =\
//        (mealItemQuantity) & mealPrice =\(mealItemPrice) & mealItemExtra =\
//        (mealItemExtra) & mealItemOption =\(mealItemOption) & discountOfferFreeItems =\
//        (self.txtFieldFreeFood.text !)"


        //Toast.makeText(getApplicationContext(),"Toast calledin cash on delivery",Toast.LENGTH_LONG).show();
        //delivery_time = "10:15:20AM";
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        if(delivery_time.equalsIgnoreCase("")){
            delivery_time=getCurrentTime();
        }
        String url="https://www.lieferadeal.de/WebAppAPI/phpexpert_payment_android_submit.php?api_key="+prefsHelper.getPref(Constants.API_KEY)+"&lang_code="+ prefsHelper.getPref(Constants.LNG_CODE)+"&payment_transaction_paypal=&itemId="+item_Id+"&Quantity="+quantity+"&Price="+Price+"&strsizeid="+strsizeid+"&extraItemID="+extraItemID+"&CustomerId="+CustomerId+"&CustomerAddressId="+addressId+"&payment_type=Cash&order_price="+order_price+"&subTotalAmount="+subTotalAmount+"&delivery_date="+delivery_date+"&delivery_time="+delivery_time+"&instructions="+instructions+"&deliveryCharge="+deliveryChargeValue+"&CouponCode="+CouponCode+"&CouponCodePrice="+CouponCodePrice+"&couponCodeType="+couponCodeType+"&SalesTaxAmount="+SalesTaxAmount+"&order_type="+order_type+"&SpecialInstruction="+SpecialInstruction+"&extraTipAddAmount="+extraTipAddAmount+"&RestaurantNameEstimate="+RestaurantNameEstimate+"&discountOfferDescription="+discountOfferDescription+"&discountOfferPrice="+discountOfferPrice+"&RestaurantoffrType="+RestaurantoffrType+"&ServiceFees="+ServiceFees+"&PaymentProcessingFees="+PaymentProcessingFees+"&deliveryChargeValueType="+deliveryChargeValueType+"&ServiceFeesType="+ServiceFeesType+"&PackageFeesType="+PackageFeesType+"&PackageFees="+PackageFees+"&WebsiteCodePrice="+WebsiteCodePrice+"&WebsiteCodeType="+WebsiteCodeType+"&WebsiteCodeNo="+WebsiteCodeNo+"&preorderTime="+preorderTime+"&VatTax="+VatTax+"&GiftCardPay="+GiftCardPay+"&WalletPay="+WalletPay+"&loyptamount="+loyptamount+"&table_number_assign="+table_number_assign+"&customer_country="+customer_country+"&group_member_id="+group_member_id+"&loyltPnts="+loyltPnts+"&branch_id="+branch_id+"&FoodCosts="+FoodCosts+"&getTotalItemDiscount="+getTotalItemDiscount+"&getFoodTaxTotal7="+getFoodTaxTotal7+"&getFoodTaxTotal19="+getFoodTaxTotal19+"&TotalSavedDiscount="+TotalSavedDiscount+"&discountOfferFreeItems="+discountOfferFreeItems+"&number_of_people="+number_of_people+"&resid="+restId+"&mealID="+mealID+"&mealquqntity="+mealquqntity+"&mealPrice="+mealPrice+"&mealItemExtra="+mealItemExtra+"&mealItemOption="+mealItemOption+"&discountOfferFreeItems="+discountOfferFreeItems+"&extraItemID1="+extraItemId1+"&extraItemIDName1="+extraItemId2;
        Log.i("reason",url);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if(response!=null){
                Log.i("reason",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Gson gson=new Gson();
                    LoginMainData searchResult=gson.fromJson(jsonObject.toString(),LoginMainData.class);
                    if (searchResult.getSuccess() != null) {
//
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

                            //Toast.makeText(getApplicationContext(),restTime,Toast.LENGTH_LONG).show();
                            Intent ii = new Intent(PayCheckOutActivity.this, ThankyouPageActivity.class);
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
                            ii.putExtra("extra_toppings",extra_toppings);
                            ii.putExtra("rest_address",rest_address);
                            ii.putExtra("selectedPizzaItemPrice", selectedPizzaItemPrice);
                            startActivity(ii);
                            finish();

                        }
                        hideProgress();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, error -> {
hideProgress();
        });
       RequestQueue queue= Volley.newRequestQueue(this);
       queue.add(stringRequest);

//        Observable<LoginMainData> observable =
//                apiInterface.getPaymentLoginData(prefsHelper.getPref(Constants.API_KEY),
//                prefsHelper.getPref(Constants.LNG_CODE), "", pizzaItemid, quantity, Price, strsizeid, extraItemID,
//                CustomerId, addressId, payment_type, order_price, subTotalAmount, delivery_date, delivery_time, instructions, deliveryChargeValue,
//                CouponCode, CouponCodePrice, couponCodeType, SalesTaxAmount, order_type, SpecialInstruction, extraTipAddAmount, RestaurantNameEstimate,
//                discountOfferDescription, discountOfferPrice, RestaurantoffrType, ServiceFees, PaymentProcessingFees, deliveryChargeValueType,
//                ServiceFeesType, PackageFeesType, PackageFees, WebsiteCodePrice, WebsiteCodeType, WebsiteCodeNo, preorderTime,
//                VatTax, GiftCardPay, WalletPay, loyptamount, table_number_assign, customer_country, group_member_id, loyltPnts, branch_id, FoodCosts,
//                getTotalItemDiscount, getFoodTaxTotal7, getFoodTaxTotal19, TotalSavedDiscount, discountOfferFreeItems, number_of_people,
////                customer_allow_register, address, street, floor_no, zipcode, phone, email, name_customer, city,
//                restId, mealID, mealquqntity, mealPrice, mealItemExtra, mealItemOption,extraItemId1,extraItemId2);
//
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<LoginMainData>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(LoginMainData searchResult) {
//                        hideProgress();
//
//
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        hideProgress();
//                        Log.d("TAG", "log...." + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        //   activity.mySharePreferences.setBundle("login");
//
//                    }
//                });

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


    //stripe payment code for success
    Dialog dialog;
    public void getCartData(){
        SQLiteDatabase db = database.getReadableDatabase();
        StringBuilder itemId=new StringBuilder();
        StringBuilder quant=new StringBuilder();
        StringBuilder price_total=new StringBuilder();
        StringBuilder strsize_all=new StringBuilder();
        StringBuilder extra_all=new StringBuilder();
        StringBuilder extraItemid3=new StringBuilder();
        StringBuilder extra_name=new StringBuilder();
        Cursor cursor = db.rawQuery("SELECT * FROM item_table", null);
        if (cursor.moveToFirst()) {
            do {
                String item_id = cursor.getString(0);
                String item_name = cursor.getString(1);
                String size_item_id = cursor.getString(2);
                String size_item_name = cursor.getString(3);
                String extra_item_id = cursor.getString(4);
                String extra_item_name = cursor.getString(5);
                String price = cursor.getString(6);
                String subcatdetals = cursor.getString(8);
                String item_quantity = cursor.getString(7);
                raviCartModles.add(new RaviCartModle(item_id, item_name, size_item_id, size_item_name, extra_item_id, extra_item_name, price, item_quantity, subcatdetals));

            }
            while (cursor.moveToNext());
            if(raviCartModles.size()>0){
                double toatl_price=0.0;
                for(int i=0;i<raviCartModles.size();i++){

                    toatl_price+=Double.parseDouble(raviCartModles.get(i).getPrice());
                    itemId.append(raviCartModles.get(i).getItem_id());
                    itemId.append(",");
                    quant.append(raviCartModles.get(i).getItem_quantity());
                    quant.append(",");
                    price_total.append(raviCartModles.get(i).getPrice());
                    price_total.append(",");
                    strsize_all.append(raviCartModles.get(i).getItem_id()+"_"+raviCartModles.get(i).getSize_item_id());
                    strsize_all.append(",");
                    if(raviCartModles.get(i).getExtra_item_id().startsWith("[")){
                        String extra_id=raviCartModles.get(i).getExtra_item_id().substring(1,raviCartModles.get(i).getExtra_item_id().lastIndexOf("]"));
                        extra_id=extra_id.replaceAll(", ",",");
                        extraItemid3.append(extra_id.trim());
                        extraItemid3.append("_");
                        String[] array=extra_id.split(",");
                        for(int j=0;j<array.length;j++){
                            extra_all.append(raviCartModles.get(i).getItem_id()+"_"+raviCartModles.get(i).getSize_item_id()+"_"+array[j].trim());
                            extra_all.append(",");
                        }
                        if(extra_all.length()>0){
                            extra_all=extra_all.deleteCharAt(extra_all.lastIndexOf(","));
                        }

                    }
                    else{
                        extraItemid3.append("0");
                        extraItemid3.append("_");

                    }
                    if (raviCartModles.get(i).getExtra_item_name().startsWith("[")){
                        String extra_name_txt=raviCartModles.get(i).getExtra_item_name().substring(1,raviCartModles.get(i).getExtra_item_name().lastIndexOf("]"));
                        extra_name_txt=extra_name_txt.replaceAll(", ",",");
                        extra_all.append(",");
                        String[] array=extra_name_txt.split(",");
                        if(array.length>0){
                            for(int b=0;b<array.length;b++){
                                extra_name.append("+"+array[b]);
                            }

                        }
                        extra_name.append("_");

                    }
                    else{
                        extra_name.append("0");
                        extra_name.append("_");
                    }
                    Log.i("reason",raviCartModles.get(i).getExtra_item_name());

                }
                if(itemId.length()>0){
                    item_Id=itemId.deleteCharAt(itemId.lastIndexOf(",")).toString();
                }
                if(quant.length()>0){
                    quantity=quant.deleteCharAt(quant.lastIndexOf(",")).toString();
                }
                if(price_total.length()>0){
                    Price=price_total.deleteCharAt(price_total.lastIndexOf(",")).toString();
                }
                if(strsize_all.length()>0){
                    strsizeid=strsize_all.deleteCharAt(strsize_all.lastIndexOf(",")).toString();
                }
                if(extra_all.length()>0){
                    extraItemID=extra_all.deleteCharAt(extra_all.lastIndexOf(",")).toString();
                }
                if(toatl_price>0.0){

                    subTotalAmount= String.valueOf(toatl_price);
                    if(TotalPrice==null) {
                        double delivery = Double.parseDouble(deliveryChargeValue);
                        double total = toatl_price + delivery;
                        tvToPayPrice.setText(currencySymbol + dotToCommaClass.changeDot(String.format("%.2f", total)));
                        order_price= String.valueOf(total);
                    }

                    FoodCosts=subTotalAmount;
                }
                if(extraItemid3.length()>0){
                    extraItemId1=extraItemid3.deleteCharAt(extraItemid3.lastIndexOf("_")).toString();
                }

                if(extra_name.length()>0){
                    extraItemId2=extra_name.deleteCharAt(extra_name.lastIndexOf("_")).toString();
                    extra_toppings=extraItemId2;
                }
                extraItemId2=convertToBase64(extraItemId2);
            }


        }
        Log.i("reason",item_Id+" "+quantity+" "+Price+" "+strsizeid+" "+extraItemID+" "+subTotalAmount+' '+FoodCosts+" "+extraItemId1+" "+extraItemId2);
    }
    public String convertToBase64(String source){
String base64="";
        try {
            byte[] byteArray=source.getBytes("UTF-8");
            base64= Base64.encodeToString(byteArray,Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64;

    }

    private void dialogOpen() {
        dialog = new Dialog(PayCheckOutActivity.this);
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

    //stripe payment token send to server for success
    private void stripePayment(String stripeToken) {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<StripeModel> observable = apiInterface.stripePaymentFromServer(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), subTotalAmount, strAppCurrency, stripeToken, emailId);

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

    private void createPaymentcall(CardMultilineWidget card_details) {
        showProgress();
        //pk_test_51I04dtKD2jSEa72lEFrtZpRLIdB41dgp5cv421yfPcQ2bPjW6dXswhLYlZoSUTuhbGyMtHjI7n4dMHCcp4N8gqKf00kKQTk8UX
        // live==  pk_live_51H335kI4oh76Z6dpfFRYuAHNcBAQtEZGtf6D7Hs2IG92vaM0x9Do2YFgNBmFNUx5d7fdAv9zsHyUxPjkydKfUCEX00j0eCL1ae
        Stripe stripe = new Stripe(PayCheckOutActivity.this, "pk_test_51H335kI4oh76Z6dpZGTM13kKY5tMuzpQpGAzDOxhjLIHvzgD3IUWsznINS83NYvmTtXWOugAVvlnMfIDC5c8X2cm00V8TXD3tL");
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
                    Toast.makeText(PayCheckOutActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(Token token) {
                    if (NetworkUtil.checkNetworkStatus(PayCheckOutActivity.this)) {
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
        //Toast.makeText(getApplicationContext(),currencySymbol,Toast.LENGTH_LONG).show();
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


    @OnClick(R.id.textViewAllergy)
    public void textViewAllergyClicked(View view) {
        dialogOpenAllergy(allergy_Data);
    }


    public void dialogOpenAllergy(String text) {
        //selectDate="";
        dialog = new Dialog(PayCheckOutActivity.this);
        dialog.setContentView(R.layout.dialog_allergy);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tvText = dialog.findViewById(R.id.tvText);
        TextView tvTextHead = dialog.findViewById(R.id.tvTextHead);
        ImageView imvBack = dialog.findViewById(R.id.imvBack);
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tvText.setText(text);
        tvTextHead.setText(model.getAllergyDetails().trim());
        dialog.show();
    }


    //stripe payment token send to server for success
    private void allergyGetting() {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<AlergyMain> observable = apiInterface.allergyFromServer(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), restId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AlergyMain>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AlergyMain signin) {
                        hideProgress();
                        allergy_Data=signin.getAlleryInfo();

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

    private void callTimeApi() {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<TimeListModel> observable = apiInterface.getTableTimeListData(prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE), restId, order_type);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TimeListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TimeListModel searchResult) {
                        hideProgress();
                        if (searchResult.getTimeList() != null && searchResult.getTimeList().size() > 0) {

                            list.clear();
//                            TimeListModel.TimeList newList = new TimeListModel.TimeList();
//                            newList.setGetTime(model.getASSOONASPOSSIBLE());
//                            newList.setSuccess("true");
//                            list.add(newList);
                            list.addAll(searchResult.getTimeList());
                            allergyGetting();

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


    private void dialogTimeSelection(List<TimeListModel.TimeList> sessionTypeMainData) {

        dialog = new Dialog(PayCheckOutActivity.this);
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
                radioButtonLater.setChecked(false);
                radioButtonASAP.setChecked(true);
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
                delivery_time = sessionTypeMainData.get(index).getGetTime();
                //tvBookingTime.setText(sessionTypeMainData.get(index).getGetTime());
                //txt_selected_time.setText(sessionTypeMainData.get(index).getGetTime());
                //Toast.makeText(getApplicationContext(), "" + sessionTypeMainData.get(index).getSessionType(), Toast.LENGTH_SHORT).show();
              /*  sessionType = sessionTypeMainData.get(index).getId();
                tvSessionType.setText(sessionTypeMainData.get(index).getSessionType());*/
            }
        });
        dialog.show();
    }
    public String getCurrentTime(){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm");
        String time=simpleDateFormat.format(calendar.getTime());
        Log.i("reason",time);
        return time;
    }

}
