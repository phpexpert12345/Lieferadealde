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
import com.app.liferdeal.model.restaurant.RestaurantDetailsModel;
import com.app.liferdeal.model.restaurant.TimeListModel;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.Holder> {
    private List<TimeListModel.TimeList> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private TimeSlotAdapterInterface restaurantDetailsQuickInterface;
    private int selectedPosition;
    private int selectedPositionItem=0;
    public static TimeSlotAdapter.Holder mHolder;
    public interface TimeSlotAdapterInterface
    {
        public void getClickTimeSlot(String restId);

    }
    public TimeSlotAdapter(Context context, List<TimeListModel.TimeList>listSubCategory, TimeSlotAdapterInterface restaurantDetailsQuickInterface1){
        this.mContext=context;
        this.restaurantDetailsQuickInterface = restaurantDetailsQuickInterface1;
        this.listCategory=listSubCategory;
        //this.selectedPosition = -1;
        this.selectedPosition = 0;

    }
    @NonNull
    @Override
    public TimeSlotAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_row,parent,false);
        return new TimeSlotAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.Holder holder, final int position) {
        holder.servicename.setText(listCategory.get(position).getGetTime());
        System.out.println("===== positon in quickdetails : " + position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantDetailsQuickInterface.getClickTimeSlot(listCategory.get(position).getGetTime());
            }
        });

    }





    @Override
    public int getItemCount() {
        return listCategory.size();
    }


    class  Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView servicename;
        private ImageView iv_restaurant_logo;
        private RatingBar ratingBar;
        private LinearLayout lnr_discount_avail, img_view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            servicename = itemView.findViewById(R.id.servicename);
           // servicename.setOnClickListener(this);


        }


    }
}

