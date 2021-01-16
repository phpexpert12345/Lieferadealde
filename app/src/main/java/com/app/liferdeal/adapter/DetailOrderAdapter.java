package com.app.liferdeal.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.app.liferdeal.model.restaurant.OrderDetailItem;
import com.app.liferdeal.model.restaurant.Orders;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.restaurant.MyOrderActivity;
import com.app.liferdeal.ui.activity.restaurant.MyOrderDetailsActivity;
import com.app.liferdeal.ui.activity.restaurant.OrderTrackActivity;
import com.app.liferdeal.ui.adapters.MyOrderAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.Currency;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.Holder> {
    private List<OrderFoodItem> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private String currencySymbol;
    private PrefsHelper prefsHelper;
    private LanguageResponse model = new LanguageResponse();
    DotToCommaClass dotToCommaClass;

    public DetailOrderAdapter(Context context, List<OrderFoodItem> listSubCategory) {
        this.mContext = context;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_detail_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
        if(listCategory.get(position).getMenuprice()!=null) {
            holder.tvQuantityMenu.setVisibility(View.VISIBLE);
            holder.view_detail.setVisibility(View.VISIBLE);
            holder.tvQuantityMenu.setText(currencySymbol + dotToCommaClass.changeDot(listCategory.get(position).getMenuprice()) + "x" + listCategory.get(position).getQuantity());
        }
        else{
            holder.view_detail.setVisibility(View.GONE);
            holder.tvQuantityMenu.setVisibility(View.GONE);
        }
        if(listCategory.get(position).getItemSize()!=null) {
            holder.txt_pizza_section_cuisine.setVisibility(View.VISIBLE);
            holder.txt_pizza_section_cuisine.setText(listCategory.get(position).getItemSize());
        }
        else{
            holder.txt_pizza_section_cuisine.setVisibility(View.GONE);
        }
        holder.txt_view_sunmenu.setText(listCategory.get(position).getItemsName());
        String toppings=listCategory.get(position).getExtraTopping();
        if(toppings!=null) {

            if (toppings.contains("+")) {
                StringBuilder stringBuilder = new StringBuilder();

                String[] tops = toppings.split("\\+");

                if (tops.length > 0) {

                    for (int j = 1; j < tops.length; j++) {
                        stringBuilder.append("+" + tops[j]);
                        stringBuilder.append("\n");

                    }
                }
                if (stringBuilder.length() > 0) {
                    toppings = stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n")).toString();
                }
            }
            if(!toppings.equalsIgnoreCase("0")) {
                holder.txt_extra_toppings.setVisibility(View.VISIBLE);
                holder.txt_extra_toppings.setText(toppings);
            }
            else{
                holder.txt_extra_toppings.setVisibility(View.GONE);
            }
        }
        else{
            holder.txt_extra_toppings.setVisibility(View.GONE);
        }

    }

    private ApiInterface apiInterface;

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

        TextView txt_view_sunmenu, txt_pizza_section_cuisine, tvQuantityMenu,txt_extra_toppings;
        View view_detail;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txt_view_sunmenu = itemView.findViewById(R.id.txt_view_sunmenu);
            txt_pizza_section_cuisine = itemView.findViewById(R.id.txt_pizza_section_cuisine);
            tvQuantityMenu = itemView.findViewById(R.id.tvQuantityMenu);
            txt_extra_toppings=itemView.findViewById(R.id.txt_extra_toppings);
            view_detail=itemView.findViewById(R.id.view_detail);
        }
    }
}
