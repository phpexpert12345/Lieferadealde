package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.DiscountModel;
import com.app.liferdeal.model.restaurant.Orders;
import com.app.liferdeal.ui.activity.restaurant.MyOrderDetailsActivity;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DiscountOrderAdapter extends RecyclerView.Adapter<DiscountOrderAdapter.Holder> {
    private List<DiscountModel.RestaurantDiscountCouponList> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private String currencySymbol;
    private PrefsHelper prefsHelper;


    public DiscountOrderAdapter(Context context, List<DiscountModel.RestaurantDiscountCouponList> listSubCategory) {
        this.mContext = context;
        this.listCategory = listSubCategory;
        prefsHelper = new PrefsHelper(mContext);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_rest_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
        holder.tv_restaurant_name.setText(listCategory.get(position).getCouponTitle());
        holder.tv_restaurant_discount_code.setText(listCategory.get(position).getCouponCode());
        holder.tv_address.setText(listCategory.get(position).getCouponDescription());
        holder.txt_date_time.setText(listCategory.get(position).getCouponValidTill());
        if (listCategory.get(position).getCouponImg() != null) {
            Glide.with(mContext).load(Uri.parse(listCategory.get(position).getCouponImg())).into(holder.iv_restaurant_logo);
        }
    }


    @Override
    public int getItemCount() {
        if (listCategory == null) {
            listCategory = new ArrayList<>();
        }
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView tv_restaurant_name, tv_address, txt_viewoderid, txt_view_orderprice, txt_date_time, txt_order_status,
                txt_btn_cancel, txt_btn_track, tv_restaurant_discount_code;
        private ImageView iv_restaurant_logo;
        private RatingBar ratingBar;
        private LinearLayout lnr_discount_avail, lnr_open_close;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_restaurant_name = itemView.findViewById(R.id.tv_restaurant_name);
            tv_restaurant_discount_code = itemView.findViewById(R.id.tv_restaurant_discount_code);
            tv_address = itemView.findViewById(R.id.tv_address);
            txt_viewoderid = itemView.findViewById(R.id.txt_viewoderid);
            txt_view_orderprice = itemView.findViewById(R.id.txt_view_orderprice);
            txt_date_time = itemView.findViewById(R.id.txt_date_time);
            txt_order_status = itemView.findViewById(R.id.txt_order_status);
            txt_btn_cancel = itemView.findViewById(R.id.txt_btn_cancel);
            txt_btn_track = itemView.findViewById(R.id.txt_btn_track);
            iv_restaurant_logo = itemView.findViewById(R.id.iv_restaurant_logo);
        }
    }
}
