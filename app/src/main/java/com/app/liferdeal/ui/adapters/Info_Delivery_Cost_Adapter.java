package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.graphics.Color;
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
import com.app.liferdeal.model.restaurant.DeliveryListInfo;
import com.app.liferdeal.model.restaurant.RestaurantDetailsModel;

import java.util.List;

public class Info_Delivery_Cost_Adapter extends RecyclerView.Adapter<Info_Delivery_Cost_Adapter.Holder> {
    private List<DeliveryListInfo> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private RestaurantDetailsQuickInterface restaurantDetailsQuickInterface;
    private int selectedPosition;
    private int selectedPositionItem = 0;
    public static Info_Delivery_Cost_Adapter.Holder mHolder;

    public interface RestaurantDetailsQuickInterface {
        public void getRestaaurantQuickClickData(String restId, int catId, String categoryImage);

    }

    public Info_Delivery_Cost_Adapter(Context context, List<DeliveryListInfo> listSubCategory) {
        this.mContext = context;
        this.listCategory = listSubCategory;
        //this.selectedPosition = -1;
        this.selectedPosition = 0;

    }

    @NonNull
    @Override
    public Info_Delivery_Cost_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_delivery_cost_row, parent, false);
        return new Info_Delivery_Cost_Adapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Info_Delivery_Cost_Adapter.Holder holder, final int position) {
        holder.txt_view_pincode.setText(listCategory.get(position).getPostcode());
        holder.txt_view_delivry_address.setText(listCategory.get(position).getAdminDistrict());
        holder.txt_view_delivery_fee.setText(listCategory.get(position).getDeliveryCharge());
        holder.txt_view_delivery_time.setText(listCategory.get(position).getDeliveryTime());
        holder.txt_view_min_order.setText(listCategory.get(position).getMinimumOrder());

    }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        private TextView txt_view_pincode, txt_view_delivry_address, txt_view_delivery_fee, txt_view_delivery_time, txt_view_min_order;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txt_view_pincode = itemView.findViewById(R.id.txt_view_pincode);
            txt_view_delivry_address = itemView.findViewById(R.id.txt_view_delivry_address);
            txt_view_delivery_fee = itemView.findViewById(R.id.txt_view_delivery_fee);
            txt_view_delivery_time = itemView.findViewById(R.id.txt_view_delivery_time);
            txt_view_min_order = itemView.findViewById(R.id.txt_view_min_order);


        }


    }
}

