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
import com.app.liferdeal.model.restaurant.LoyalityPointsModel;
import com.app.liferdeal.model.restaurant.RestMenuCat;
import com.app.liferdeal.model.restaurant.SubItemsRecord;
import com.app.liferdeal.ui.activity.restaurant.AddClickDetails;
import com.app.liferdeal.ui.activity.restaurant.RestaurantDetails;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class LoyalityPointsAdapter extends RecyclerView.Adapter<LoyalityPointsAdapter.Holder> {
    private List<LoyalityPointsModel> listCategory;
   // private List<SubItemsRecord> listFilterSubCategory;
    private Context mContext;
    private RestaurantDetailsAdapter.RestaurantDetailsAdapterInterface restaurantDetailsAdapterInterface;
    private int prePoints;
   // ArrayList<Integer> listt;
  /*  public interface RestaurantDetailsAdapterInterface {
        void getClickData(int otemId, String itemName);
    }
*/

    public LoyalityPointsAdapter(Context context, List<LoyalityPointsModel> prepopints) {
        this.mContext = context;
        this.listCategory = prepopints;
     //   this.listCategory = listSubCategory;
      //  this.listFilterSubCategory = listFilterSubCategory;
     //   restaurantDetailsAdapterInterface = restaurantDetailsAdapterInterface1;

    }

    @NonNull
    @Override
    public LoyalityPointsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loyality_points_row, parent, false);
        return new LoyalityPointsAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoyalityPointsAdapter.Holder holder, final int position) {
        if (listCategory.get(position).getSignPoints()==listCategory.get(position).getSignPoints())
        {
            holder.tv_loyality_name.setText("Free Sign Up");
            holder.tv_loyality_des.setText("Free sign up with us to get");
            holder.tv_item_points.setText(""+listCategory.get(position).getSignPoints());
            Glide.with(mContext).load(R.drawable.freesignup).into(holder.iv_restaurant_logo);


        }

       else if (listCategory.get(position).getPlaceFirstOrdersPoints()==listCategory.get(position).getPlaceFirstOrdersPoints())
        {
            holder.tv_loyality_name.setText("Place First Order");
            holder.tv_loyality_des.setText("Place first order from us to get");
            holder.tv_item_points.setText(""+listCategory.get(position).getSignPoints());
            Glide.with(mContext).load(R.drawable.placefstorder).into(holder.iv_restaurant_logo);
        }

        else if (listCategory.get(position).getPostReviewPoints()==listCategory.get(position).getPostReviewPoints())
        {
            holder.tv_loyality_name.setText("Posting a Review");
            holder.tv_loyality_des.setText("Posting a review to get");
            holder.tv_item_points.setText(""+listCategory.get(position).getSignPoints());
            Glide.with(mContext).load(R.drawable.postingreview).into(holder.iv_restaurant_logo);
        }

       else if (listCategory.get(position).getPlaceGroupOrderingPoints()==listCategory.get(position).getPlaceGroupOrderingPoints())
        {
            holder.tv_loyality_name.setText("Group Ordering");
            holder.tv_loyality_des.setText("Place group ordering to get");
            holder.tv_item_points.setText(""+listCategory.get(position).getSignPoints());
            Glide.with(mContext).load(R.drawable.groupordering).into(holder.iv_restaurant_logo);
        }

       else if (listCategory.get(position).getBirthdayCelebrationsPoints()==listCategory.get(position).getBirthdayCelebrationsPoints())
        {
            holder.tv_loyality_name.setText("Birthday Celebration");
            holder.tv_loyality_des.setText("Birthday celebration and place orders from us to get");
            holder.tv_item_points.setText(""+listCategory.get(position).getSignPoints());
            Glide.with(mContext).load(R.drawable.bdcelebrate).into(holder.iv_restaurant_logo);
        }

       else if (listCategory.get(position).getSocialMediaSharingPoints()==listCategory.get(position).getSocialMediaSharingPoints())
        {
            holder.tv_loyality_name.setText("Social Sharing");
            holder.tv_loyality_des.setText("Sharing with Facebook, Twitter, Whatsapp, etc to get");
            holder.tv_item_points.setText(""+listCategory.get(position).getSignPoints());
            Glide.with(mContext).load(R.drawable.socialshare).into(holder.iv_restaurant_logo);
        }

       else if (listCategory.get(position).getSpendMoreThanPoints()==listCategory.get(position).getSpendMoreThanPoints())
        {
            holder.tv_loyality_name.setText("Spend Earn");
            holder.tv_loyality_des.setText("Earn 1 point for every GBP 1 spent with Us");
            holder.tv_item_points.setText(""+listCategory.get(position).getSignPoints());
            Glide.with(mContext).load(R.drawable.referearn).into(holder.iv_restaurant_logo);
        }

       else if (listCategory.get(position).getReferFriendsPoints()==listCategory.get(position).getReferFriendsPoints())
        {
            holder.tv_loyality_name.setText("Refer a Friend");
            holder.tv_loyality_des.setText("Refer a friend when friend join and");
            holder.tv_item_points.setText(""+listCategory.get(position).getSignPoints());
            Glide.with(mContext).load(R.drawable.referfrnd).into(holder.iv_restaurant_logo);
        }

       // holder.tv_loyality_des.setText(listFilterSubCategory.get(position).getResPizzaDescription());

       /* if (listCategory.get(position).getFoodType() != null) {
            Glide.with(mContext).load(Uri.parse(listCategory.get(position).getFoodType())).into(holder.iv_restaurant_logo);
        }
*/

    }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView tv_loyality_name, tv_loyality_des, tv_item_points;
        private ImageView iv_restaurant_logo;


        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_loyality_name = itemView.findViewById(R.id.tv_loyality_name);
            tv_loyality_des = itemView.findViewById(R.id.tv_loyality_des);
            tv_item_points = itemView.findViewById(R.id.tv_item_points);
            iv_restaurant_logo = itemView.findViewById(R.id.iv_restaurant_logo);

        }
    }
}