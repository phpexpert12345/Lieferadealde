package com.app.liferdeal.ui.activity.restaurant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.app.liferdeal.ui.adapters.AddressAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.GPSTracker;
import com.app.liferdeal.util.PrefsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener, AddressAdapter.AddressDelete {
    @BindView(R.id.tvNoData)
    AppCompatTextView tvNoData;
    private RecyclerView list_view_details;
    private RelativeLayout rl_back;
    private AppCompatButton card_add_address;
    private List<AddressModel> list_add;
    private TextView tv_name, tv_mobile, tv_address, tvHead;
    private AppCompatTextView tvHome;
    private CardView default_add;
    private ImageView img_select;
    Double currentLatitude, currentLongitude;
    public String current_add;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private ProgressBar pbLoad;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_activity);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(AddressActivity.this) != null) {
            model = App.retrieveLangFromGson(AddressActivity.this);
        }
        init();
    }

    private void init() {
        prefsHelper = new PrefsHelper(this);
        list_add = new ArrayList<>();
        list_view_details = findViewById(R.id.list_view_details);
        card_add_address = findViewById(R.id.btnAddAddress);
        rl_back = findViewById(R.id.rl_back);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_address = findViewById(R.id.tv_address);
        default_add = findViewById(R.id.default_add);
        img_select = findViewById(R.id.img_select);
        tvHome = findViewById(R.id.tvHome);
        tvHead = findViewById(R.id.tvHead);
        pbLoad = findViewById(R.id.pbLoad);

        tvHead.setText(model.getAddress());
        tvHome.setText(model.getGOTOHOME());

        rl_back.setOnClickListener(this);
        card_add_address.setOnClickListener(this);
        default_add.setOnClickListener(this);
        tvHome.setOnClickListener(this);
        GPSTracker trackerObj = new GPSTracker(this);
        currentLatitude = trackerObj.getLatitude();
        currentLongitude = trackerObj.getLongitude();
        System.out.println("=== prefsHelper.getPref(Constants.CUSTOMER_ID) : " + prefsHelper.getPref(Constants.CUSTOMER_ID) + currentLongitude + currentLatitude);
        getAddress();
        getSavedAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
        getSavedAddress();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddAddress:
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.default_add:
                img_select.setImageResource(R.drawable.select);
                current_add = tv_address.getText().toString();
                // activity.setCurrent_fragment(new FragmentSelectDate(),FragmentSelectDate.FRAG_TITLE);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tvHome:
                finish();
                break;
            default:
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
//                        showProgress();
                        pbLoad.setVisibility(View.VISIBLE);
                        setAddAdapter(searchResult.getAddressModel().getDeliveryaddress());
                        if (searchResult.getAddressModel().getDeliveryaddress().size() > 0) {
                            tvNoData.setVisibility(View.GONE);
                        } else {
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(model.getDATAISNOTAVAILABLE());
                        }
//                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        hideProgress();
                        pbLoad.setVisibility(View.GONE);
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
//                        hideProgress();
                        pbLoad.setVisibility(View.GONE);
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });
    }

    private void getAddress() {
        try {
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0);
                    System.out.println("===== addresss " + address);
                      /*  String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();*/
                    tv_address.setText(address);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showProgress() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(model.getPlease_wait_text().trim()+"...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    public void setAddAdapter(List<AddressModel.Deliveryaddress> list) {
        list_view_details.setLayoutManager(new LinearLayoutManager(this));
        AddressAdapter adapterAddress = new AddressAdapter(this, list);
        list_view_details.setAdapter(adapterAddress);
        adapterAddress.callbackAddress(this);
    }


    @Override
    public void deleteAddress(AddressModel.Deliveryaddress address, String title) {
        if (title.equals("delete")) {
            showDeleteDialog(address);
        } else if (title.equalsIgnoreCase("edit")) {

        }
        /*else {
            activity.setCurrent_fragment(new FragmentUpdateAddress(), FragmentUpdateAddress.FRAG_TITLE);
            activity.modelAddress = address;
        }*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("===== onnew intent");
    }

    public void showDeleteDialog(AddressModel.Deliveryaddress address) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage(model.getAlertText());
        builder.setPositiveButton(model.getOKText(), (dialog, which) -> {
            dialog.dismiss();
            deleteAddressCustomer(address.getId());

        });
        builder.setNegativeButton(model.getCancel(), ((dialog, which) -> {
            dialog.dismiss();
        }));
        builder.create().show();
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
        final AlertDialog alertDialog = new AlertDialog.Builder(AddressActivity.this).create();
        alertDialog.setTitle("Alert");
        if (!s.equalsIgnoreCase("null")) {
            alertDialog.setMessage("" + s);
        }
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton(model.getOKText(), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                getSavedAddress();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
