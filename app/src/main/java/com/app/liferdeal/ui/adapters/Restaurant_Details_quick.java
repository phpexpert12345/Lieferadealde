package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class Restaurant_Details_quick extends RecyclerView.Adapter<Restaurant_Details_quick.Holder> {
    private List<RestaurantDetailsModel.RestaurantMencategory> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private RestaurantDetailsQuickInterface restaurantDetailsQuickInterface;
    private int selectedPosition;
    private int selectedPositionItem = 0;
    public static Restaurant_Details_quick.Holder mHolder;
    private int selectedPos;

    public interface RestaurantDetailsQuickInterface {
        public void getRestaaurantQuickClickData(int position, String restId, int catId, String categoryImage, String name);
    }

    public Restaurant_Details_quick(Context context, List<RestaurantDetailsModel.RestaurantMencategory> listSubCategory,
                                    RestaurantDetailsQuickInterface restaurantDetailsQuickInterface1, int selectedPos) {
        this.mContext = context;
        this.restaurantDetailsQuickInterface = restaurantDetailsQuickInterface1;
        this.listCategory = listSubCategory;
//        this.selectedPos = selectedPos;
        //this.selectedPosition = -1;
        this.selectedPosition = selectedPos;
    }


    @NonNull
    @Override
    public Restaurant_Details_quick.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_details_quick, parent, false);
        return new Restaurant_Details_quick.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Restaurant_Details_quick.Holder holder, final int position) {
        holder.servicename.setText(listCategory.get(position).getCategoryName());
        System.out.println("===== positon in quickdetails : " + position);
        System.out.println("===== positon in quickdetails1 : " + selectedPosition);


        if (position == selectedPosition) {
//            mHolder = holder;
//            updateSelectedView(position);
            Log.e("LOG=", position + "   Inside selected");

            holder.servicename.setBackgroundResource(R.drawable.button_rounded);
            holder.servicename.setTextColor(Color.WHITE);
        } else {
//            updateNonSelected(holder, position);
            Log.e("LOG=", position + "   Inside DEselected");
            holder.servicename.setBackgroundColor(mContext.getResources().getColor(R.color.color_lighteGray));
            holder.servicename.setTextColor(Color.BLACK);
        }

            /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    restaurantDetailsQuickInterface.getRestaaurantQuickClickData(listCategory.get(position).getRestaurantId(), listCategory.get(position).getCategoryID());


                }
            });*/


           /* holder.img_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, RestaurantPhotoGallery.class);
                    mContext.startActivity(i);
                }
            });*/
    }

    private void updateNonSelected(Holder holder, int position) {
        if (selectedPosition == -1) {
        } else {
            holder.servicename.setBackgroundColor(mContext.getResources().getColor(R.color.color_lighteGray));
            mHolder.servicename.setTextColor(Color.BLACK);
               /* if (selectedPosition == 0) {
                    holder.servicename.setTextColor(Color.WHITE);
                }
                else
                {
                    holder.servicename.setBackgroundColor(mContext.getResources().getColor(R.color.color_lighteGray));
                    mHolder.servicename.setTextColor(Color.BLACK);
                }*/
        }
    }

    private void updateSelectedView(int position) {
        System.out.println("==== selected positin in updateselectedview : " + selectedPosition);
//        if (selectedPosition == 0) {
//            mHolder.servicename.setTextColor(Color.WHITE);
//        }
        mHolder.servicename.setBackgroundResource(R.drawable.button_rounded);
        mHolder.servicename.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        if (listCategory == null)
            listCategory = new ArrayList<>();
        return listCategory.size();
    }

    @Override
    public void onViewRecycled(@NonNull Holder holder) {
        super.onViewRecycled(holder);
        if (selectedPosition == holder.getAdapterPosition()) {
            mHolder = holder;
        }
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView card_subCategory;
        private TextView servicename;
        private ImageView iv_restaurant_logo;
        private RatingBar ratingBar;
        private LinearLayout lnr_discount_avail, img_view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            servicename = itemView.findViewById(R.id.servicename);
            servicename.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (getAdapterPosition() == selectedPosition) {
//
//            } else {
//                selectedPosition = getAdapterPosition();
//                if (null != mHolder) {
//                    updateNonSelected(mHolder, getAdapterPosition());
//                }
//                mHolder = this;
//            }
//            updateSelectedView(getAdapterPosition());
            if (restaurantDetailsQuickInterface != null)
                // Toast.makeText(mContext,"called horizontally",Toast.LENGTH_LONG).show();
                restaurantDetailsQuickInterface.getRestaaurantQuickClickData(getLayoutPosition(), listCategory.get(getLayoutPosition()).getRestaurantId(), listCategory.get(getLayoutPosition()).getCategoryID(), listCategory.get(getLayoutPosition()).getCategoryImg(), listCategory.get(getLayoutPosition()).getCategoryName());
            selectedPosition = getAdapterPosition();
            notifyDataSetChanged();

        }
    }
}

