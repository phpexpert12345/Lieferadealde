package com.app.liferdeal.ui.adapters;

import android.content.Context;
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
import com.app.liferdeal.model.restaurant.RestMenuReviewModel;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.Currency;
import java.util.List;

public class RestReviewOrderAdapter extends RecyclerView.Adapter<RestReviewOrderAdapter.Holder> {
    private List<RestMenuReviewModel.ReviewlistingList> listCategory;
   // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private String currencySymbol;
    private PrefsHelper prefsHelper;


    public RestReviewOrderAdapter(Context context, List<RestMenuReviewModel.ReviewlistingList>listSubCategory){
        this.mContext=context;
        this.listCategory=listSubCategory;
        prefsHelper = new PrefsHelper(mContext);
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rest_review_adapter_row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Currency hh = Currency.getInstance(""+prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol =  hh.getSymbol();
        holder.tv_restaurant_name.setText(listCategory.get(position).getCustomerName());
        holder.tv_address.setText(listCategory.get(position).getCustomerReviewComment());
        holder.txt_date_time.setText(listCategory.get(position).getReviewpostedDate());


        if (listCategory.get(position).getRestaurantReviewRating()!=null) {
            holder.ratingBar.setRating(Float.parseFloat(listCategory.get(position).getRestaurantReviewRating()));
        }



    }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class  Holder extends RecyclerView.ViewHolder{
        public CardView card_subCategory;
        private TextView tv_restaurant_name,tv_address,txt_viewoderid, txt_view_orderprice, txt_date_time, txt_order_status,
                txt_btn_cancel, txt_btn_track, tv_restaurant_discount_code;
        private ImageView iv_restaurant_logo;
        private RatingBar ratingBar;
        private LinearLayout lnr_discount_avail,lnr_open_close;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_restaurant_name = itemView.findViewById(R.id.tv_restaurant_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            ratingBar = itemView.findViewById(R.id.ratingBar);
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
