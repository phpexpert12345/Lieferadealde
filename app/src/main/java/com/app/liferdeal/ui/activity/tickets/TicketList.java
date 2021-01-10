package com.app.liferdeal.ui.activity.tickets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.TicketListDataModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.adapters.TicketListAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TicketList extends Activity implements View.OnClickListener {
    @BindView(R.id.tv_faq)
    TextView tvFaq;
    @BindView(R.id.tvTicketNo)
    AppCompatTextView tvTicketNo;
    @BindView(R.id.tvOrderNo)
    AppCompatTextView tvOrderNo;
    @BindView(R.id.tvStatus)
    AppCompatTextView tvStatus;
    @BindView(R.id.tvGoHome)
    AppCompatTextView tvGoHome;
    private AppCompatButton btnAddAddress;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private RecyclerView recyler_view_ticket;
    private ImageView iv_back;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(TicketList.this) != null) {
            model = App.retrieveLangFromGson(TicketList.this);
        }
        findViewById();
    }

    private void findViewById() {

        prefsHelper = new PrefsHelper(this);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        recyler_view_ticket = findViewById(R.id.recyler_view_ticket);
        iv_back = findViewById(R.id.iv_back);

        tvFaq.setText(model.getManageTicket().trim());
        tvTicketNo.setText(model.getTicketNumber().trim());
        tvStatus.setText(model.getStatus().trim());
        tvOrderNo.setText(model.getOrderNumber().trim());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyler_view_ticket.setLayoutManager(mLayoutManager);
        recyler_view_ticket.setItemAnimator(new DefaultItemAnimator());
        btnAddAddress.setOnClickListener(this::onClick);
        iv_back.setOnClickListener(this::onClick);
        getTicketData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddAddress:
                startActivity(new Intent(this, AddTicket.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tvGoHome:
                finish();
                break;
            default:
                break;
        }
    }

    private void getTicketData() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<TicketListDataModel> observable = apiInterface.getTicketList(prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE), prefsHelper.getPref(Constants.CUSTOMER_ID));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TicketListDataModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TicketListDataModel searchResult) {
                        showProgress();
                        if (searchResult.getComplaintsHistory().size() > 0)
                            setAdapterCategory(searchResult.getComplaintsHistory());
                        else
                            showCustomDialog1decline("No data available");
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

    private void showCustomDialog1decline(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(TicketList.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
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

    private void setAdapterCategory(List<TicketListDataModel.ComplaintsHistory> list) {
        TicketListAdapter adapterCategory = new TicketListAdapter(this, list);
        recyler_view_ticket.setAdapter(adapterCategory);
        // hideProgress();
    }
}
