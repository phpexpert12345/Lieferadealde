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
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.Orders;
import com.app.liferdeal.ui.activity.restaurant.MyOrderDetailsActivity;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.Currency;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Holder> {
    private List<Orders.OrderViewResult> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private String currencySymbol;
    private PrefsHelper prefsHelper;
    private LanguageResponse model = new LanguageResponse();

    public MyOrderAdapter(Context context, List<Orders.OrderViewResult> listSubCategory) {
        this.mContext = context;
        this.listCategory = listSubCategory;
        if (App.retrieveLangFromGson(mContext) != null) {
            model = App.retrieveLangFromGson(mContext);
        }
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
        holder.txt_view_orderprice.setText(currencySymbol + listCategory.get(position).getOrdPrice());

        holder.txt_date_time.setText(listCategory.get(position).getOrderDate() + " " + listCategory.get(position).getOrderTime());
        if (listCategory.get(position).getRestaurantLogo() != null) {
            Glide.with(mContext).load(Uri.parse(listCategory.get(position).getRestaurantLogo())).into(holder.iv_restaurant_logo);
        }

        holder.txt_btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MyOrderDetailsActivity.class);
                i.putExtra("orderid", listCategory.get(position).getOrderIdentifyno());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView tv_restaurant_name, tv_address, txt_viewoderid, txt_view_orderprice, txt_date_time, txt_order_status,
                txt_btn_cancel, txt_btn_track;
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

            txt_btn_cancel.setText(model.getCancel());
            txt_btn_track.setText(model.getTrack());

        }
    }

}
