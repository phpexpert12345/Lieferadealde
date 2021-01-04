package com.app.liferdeal.ui.activity.cart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.DeleteAddressResponse;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.AddressModel;
import com.app.liferdeal.model.restaurant.ModelAddressList;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.restaurant.AddAddressActivity;
import com.app.liferdeal.ui.activity.restaurant.AddressActivity;
import com.app.liferdeal.ui.adapters.SavedAddressAdapter;
import com.app.liferdeal.ui.interfaces.DeleteAddressListener;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.GPSTracker;
import com.app.liferdeal.util.PrefsHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SavedAddressActivity extends AppCompatActivity implements DeleteAddressListener {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.rl_header)
    CardView rlHeader;
    @BindView(R.id.rvAddressList)
    RecyclerView rvAddressList;
    @BindView(R.id.btnAddAddress)
    AppCompatButton btnAddAddress;
    @BindView(R.id.tvDeliveryAdd)
    AppCompatTextView tvDeliveryAdd;
    @BindView(R.id.tvAddressLint)
    TextView tvAddressLint;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private Context mContext;
    private List<AddressModel.Deliveryaddress> addressList;
    private Double currentLatitude, currentLongitude;
    private String totalPrice = "";

    private String strPostalCode = "", strCityname = "", strFullAddress = "", restId = "", TotalPrice = "", currencySymbol = "", pizzaQuantity = "", Pizzaname = "", selectedPizzaItemPrice = "";
    private String strMainRestName = "", strMainRestLogo = "", pizzaItemid = "", subTotalPrice = "";
    private String payment_transaction_paypal = "", quantity, deliveryChargeValue = "", SeviceFeesValue = "", ServiceFees = "", ServiceFeesType = "", PackageFeesType = "", PackageFees = "", PackageFeesValue = "", SalesTaxAmount = "", VatTax = "";
    private String strsizeid, extraItemID, CustomerId, CustomerAddressId, payment_type, order_price, subTotalAmount, delivery_date, delivery_time, instructions, deliveryCharge,
            CouponCode, CouponCodePrice, couponCodeType, order_type, SpecialInstruction, extraTipAddAmount, RestaurantNameEstimate, discountOfferDescription, discountOfferPrice, RestaurantoffrType,
            PaymentProcessingFees, deliveryChargeValueType, WebsiteCodePrice, WebsiteCodeType, WebsiteCodeNo, preorderTime, addressId,
            GiftCardPay, WalletPay, loyptamount, table_number_assign, customer_country, group_member_id, loyltPnts, branch_id, FoodCosts,
            getTotalItemDiscount, getFoodTaxTotal7, getFoodTaxTotal19, TotalSavedDiscount, discountOfferFreeItems, number_of_people, customer_allow_register, address,
            street, floor_no, zipcode, phone, email, name_customer, city, mealID, mealquqntity, mealPrice, mealItemExtra, mealItemOption;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        mContext = SavedAddressActivity.this;
        addressList = new ArrayList<>();
        prefsHelper = new PrefsHelper(this);
        GPSTracker trackerObj = new GPSTracker(this);
        currentLatitude = trackerObj.getLatitude();
        currentLongitude = trackerObj.getLongitude();

        if (App.retrieveLangFromGson(SavedAddressActivity.this) != null) {
            model = App.retrieveLangFromGson(SavedAddressActivity.this);
        }
        tvAddressLint.setText(model.getAddress());
        tvDeliveryAdd.setText(model.getConfirmDeliveryAddress());

        if (getIntent() != null) {
            totalPrice = getIntent().getStringExtra("TotalPrice");
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
            customer_allow_register = "yes";
            payment_type = "cash";
        }

        getSavedAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSavedAddress();
    }

    @OnClick({R.id.rl_back, R.id.btnAddAddress, R.id.tvDeliveryAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btnAddAddress:
                Intent intent1 = new Intent(SavedAddressActivity.this, AddAddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.tvDeliveryAdd:
                Intent intent = new Intent(SavedAddressActivity.this, PayCheckOutActivity.class);

                intent.putExtra("RestId", restId);
                intent.putExtra("TotalPrice", String.valueOf(order_price));
                intent.putExtra("SubTotalPrice", String.valueOf(subTotalAmount));
                intent.putExtra("RESTName", strMainRestName);
                intent.putExtra("RESTLOGO", strMainRestLogo);
                intent.putExtra("subPizzaItemId", pizzaItemid);
                intent.putExtra("SIZEITEMID", strsizeid);
                intent.putExtra("globTempExtraItemidWithSizeIdd", extraItemID);
                intent.putExtra("delivery_date", delivery_date);
                intent.putExtra("food_cost", FoodCosts);
                intent.putExtra("quantity", quantity);
                intent.putExtra("deliveryChargeValue", deliveryChargeValue);
                intent.putExtra("SeviceFeesValue", SeviceFeesValue);
                intent.putExtra("ServiceFees", ServiceFees);
                intent.putExtra("ServiceFeesType", ServiceFeesType);
                intent.putExtra("PackageFeesType", PackageFeesType);
                intent.putExtra("PackageFees", PackageFees);
                intent.putExtra("PackageFeesValue", PackageFeesValue);
                intent.putExtra("SalesTaxAmount", SalesTaxAmount);
                intent.putExtra("VatTax", VatTax);
                intent.putExtra("deliveryType", order_type);
                intent.putExtra("pizzaQuantity", quantity);
                intent.putExtra("Pizzaname", Pizzaname);
                intent.putExtra("selectedPizzaItemPrice", selectedPizzaItemPrice);
                intent.putExtra("totalPrice", totalPrice);
                intent.putExtra("addressId", addressId);
                startActivity(intent);
                break;
        }
    }

    private void getSavedAddress() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<ModelAddressList> observable = apiInterface.getSavedAddress(String.valueOf(currentLatitude), String.valueOf(currentLongitude),
                prefsHelper.getPref(Constants.CUSTOMER_ID));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ModelAddressList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ModelAddressList searchResult) {
                        showProgress();
                        if (addressList.size() > 0)
                            addressList.clear();
                        addressList.addAll(searchResult.getAddressModel().getDeliveryaddress());
                        setAddAdapter(searchResult.getAddressModel().getDeliveryaddress());
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

    private Boolean[] selectedItem;
    private SavedAddressAdapter adapterAddress;

    private void setAddAdapter(List<AddressModel.Deliveryaddress> deliveryaddress) {
        selectedItem = new Boolean[deliveryaddress.size()];
        for (int i = 0; i < selectedItem.length; i++) {
            selectedItem[i] = false;
        }

        rvAddressList.setLayoutManager(new LinearLayoutManager(mContext));
        adapterAddress = new SavedAddressAdapter(this, deliveryaddress, selectedItem);
        rvAddressList.setAdapter(adapterAddress);
        adapterAddress.setClickListener(this);
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
    public void onItemDelete(int id) {
        deleteAddressCustomer(id);
    }

    @Override
    public void onItemSelect(int position) {
        for (int i = 0; i < selectedItem.length; i++) {
            if (i == position) {
                selectedItem[i] = true;
            } else {
                selectedItem[i] = false;
            }
        }
        selectedItem[position] = true;
        adapterAddress.notifyDataSetChanged();
        addressId = String.valueOf(addressList.get(position).getId());
    }

    private void deleteAddressCustomer(int id) {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<DeleteAddressResponse> observable = apiInterface.deleteAddress(String.valueOf(id));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteAddressResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DeleteAddressResponse searchResult) {
                        hideProgress();
                        showCustomDialog(searchResult.getSuccessMsg());
//                        showProgress();
//                        setAddAdapter(searchResult.getAddressModel().getDeliveryaddress());
//                        hideProgress();
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

    private void showCustomDialog(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(SavedAddressActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                getSavedAddress();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
