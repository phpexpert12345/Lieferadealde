package com.app.liferdeal.ui.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.interfaces.CancelClicked;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.OrderFoodItem;
import com.app.liferdeal.model.restaurant.MYOrderTrackDetailModel;
import com.app.liferdeal.model.restaurant.Orders;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.restaurant.MyOrderActivity;
import com.app.liferdeal.ui.activity.restaurant.MyOrderDetailsActivity;
import com.app.liferdeal.ui.activity.restaurant.OrderTrackActivity;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Holder> {
    private List<Orders.OrderViewResult> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private String currencySymbol;
    private PrefsHelper prefsHelper;
    private LanguageResponse model = new LanguageResponse();
    DotToCommaClass dotToCommaClass;
    CancelClicked cancelClicked;

    public MyOrderAdapter(Context context, List<Orders.OrderViewResult> listSubCategory, CancelClicked cancelClicked) {
        this.mContext = context;
       this.cancelClicked=cancelClicked;
        this.listCategory = listSubCategory;
        if (App.retrieveLangFromGson(mContext) != null) {
            model = App.retrieveLangFromGson(mContext);
        }
        dotToCommaClass = new DotToCommaClass(mContext);
        prefsHelper = new PrefsHelper(mContext);

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
        holder.tv_restaurant_name.setText(listCategory.get(position).getRestaurantName());
        holder.tv_address.setText(listCategory.get(position).getRestaurantAddress());
        holder.txt_viewoderid.setText(listCategory.get(position).getOrderIdentifyno());
        holder.txt_order_status.setText(listCategory.get(position).getOrderStatusMsg());
        if(listCategory.get(position).getOrderStatusColorCode()!=null) {
        holder.txt_order_status.setTextColor(Color.parseColor(listCategory.get(position).getOrderStatusColorCode()));
        }
        holder.txt_view_orderprice.setText(currencySymbol + dotToCommaClass.changeDot(listCategory.get(position).getOrdPrice()));

        holder.txt_date_time.setText(listCategory.get(position).getOrderDate() + "/" + listCategory.get(position).getOrderTime());
        if (listCategory.get(position).getRestaurantLogo() != null) {
            Glide.with(mContext).load(Uri.parse(listCategory.get(position).getRestaurantLogo())).into(holder.iv_restaurant_logo);
        }

        holder.txt_btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrderDetails(listCategory.get(position).getOrderIdentifyno());
            }
        });

        holder.rl_restaurant_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MyOrderDetailsActivity.class);
                i.putExtra("orderid", listCategory.get(position).getOrderIdentifyno());
                mContext.startActivity(i);
            }
        });
        if (listCategory.get(position).getOrder_status_close().equalsIgnoreCase("1")) {
            holder.txt_btn_cancel.setVisibility(View.GONE);
        } else if(listCategory.get(position).getOrder_status_close().equalsIgnoreCase("0")) {
            holder.txt_btn_cancel.setVisibility(View.VISIBLE);
        }
        holder.txt_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelClicked.cancleButton(listCategory.get(position).getOrderIdentifyno(), mContext);
            }
        });
    }

    private ApiInterface apiInterface;

    private void getOrderDetails(String strordernumber) {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<MYOrderTrackDetailModel> observable = apiInterface.getMyOrderDetailsDisplay(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), strordernumber);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MYOrderTrackDetailModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MYOrderTrackDetailModel searchResult) {
                        showProgress();
                       /* setAdapterCategory(searchResult.getOrders().getOrderViewResult());
                        banner_progress.setVisibility(View.GONE);*/

                        if(searchResult.getOrderDetailItem()!=null){
                            List<OrderFoodItem>foodItems=searchResult.getOrderDetailItem().get(0).getOrderFoodItem();
                            String orderno = searchResult.getOrderDetailItem().get(0).getOrderIdentifyno();
                            Intent i = new Intent(mContext, OrderTrackActivity.class);
                            if(foodItems!=null) {
                                if(foodItems.size()>0) {
                                    i.putParcelableArrayListExtra("food_items", (ArrayList<? extends Parcelable>) foodItems);
                                }
                            }

                            i.putExtra("orderid", orderno);
                            mContext.startActivity(i);

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

    private ProgressDialog progressDialog;

    public void showProgress() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(model.getPlease_wait_text().trim() + "...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        RelativeLayout rl_restaurant_list;
        private TextView tv_restaurant_name, tv_address, txt_viewoderid, txt_view_orderprice, txt_date_time, txt_order_status, txt_btn_cancel, txt_btn_track;
        private ImageView iv_restaurant_logo;
        private RatingBar ratingBar;
        private LinearLayout lnr_discount_avail, lnr_open_close;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_restaurant_name = itemView.findViewById(R.id.tv_restaurant_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            txt_viewoderid = itemView.findViewById(R.id.txt_viewoderid);
            txt_view_orderprice = itemView.findViewById(R.id.txt_view_orderprice);
            txt_date_time = itemView.findViewById(R.id.txt_date_time);
            txt_order_status = itemView.findViewById(R.id.txt_order_status);
            txt_btn_cancel = itemView.findViewById(R.id.txt_btn_cancel);
            txt_btn_track = itemView.findViewById(R.id.txt_btn_track);
            iv_restaurant_logo = itemView.findViewById(R.id.iv_restaurant_logo);
            rl_restaurant_list = itemView.findViewById(R.id.rl_restaurant_list);

            txt_btn_cancel.setText(model.getCancel().trim());
            txt_btn_track.setText(model.getTrack().trim());
        }
    }
}
