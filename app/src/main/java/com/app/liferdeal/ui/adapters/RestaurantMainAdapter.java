package com.app.liferdeal.ui.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.RestaurantMainModel;
import com.app.liferdeal.ui.activity.login.SignInActivity;
import com.app.liferdeal.ui.activity.restaurant.GPSTracker;
import com.app.liferdeal.ui.activity.restaurant.RestaurantDetails;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class RestaurantMainAdapter extends RecyclerView.Adapter<RestaurantMainAdapter.Holder> implements Filterable {
    private List<RestaurantMainModel.SearchResult> listCategory, filterList;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private PrefsHelper prefsHelper;
    DotToCommaClass dotToCommaClass;

    public RestaurantMainAdapter(Context context, List<RestaurantMainModel.SearchResult> listSubCategory, PrefsHelper prefsHelper) {
        this.mContext = context;
        this.listCategory = listSubCategory;
        this.filterList = new ArrayList<>(listSubCategory);
        this.prefsHelper = prefsHelper;
        dotToCommaClass = new DotToCommaClass(context);
    }

    @NonNull
    @Override
    public RestaurantMainAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_search_row, parent, false);
        return new RestaurantMainAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantMainAdapter.Holder holder, final int position) {
        if (listCategory.get(position).getRestaurantOrderAvailable().equalsIgnoreCase("false")) {
            holder.lnr_open_close.setBackgroundResource(R.drawable.circle_grey);

            holder.tv_restaurant_total_review.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.tv_item_discount_open_close.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.tv_restaurant_name.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.tv_restaurant_cusine_name.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.tvSponsored.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.tv_restaurant_address.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.tv_restaurant_miles.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.txt_view_timer.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.txt_rest_min_order.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.txt_rest_free_order.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            holder.txt_discount_avail.setTextColor(mContext.getResources().getColor(R.color.backlightgray));
            //holder.llTopLayout.setBackgroundColor(mContext.getResources().getColor(R.color.dark_gray));
            holder.kidred.setColorFilter(mContext.getResources().getColor(R.color.backlightgray));
            holder.petred.setColorFilter(mContext.getResources().getColor(R.color.backlightgray));
            holder.minorder.setColorFilter(mContext.getResources().getColor(R.color.backlightgray));
        } else {
            //holder.llTopLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.tv_restaurant_total_review.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.tv_item_discount_open_close.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.tv_restaurant_name.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.tv_restaurant_cusine_name.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.tvSponsored.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.tv_restaurant_address.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.tv_restaurant_miles.setTextColor(mContext.getResources().getColor(R.color.colorTextSecondary));
            holder.txt_view_timer.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.txt_rest_min_order.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.txt_rest_free_order.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.txt_discount_avail.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.kidred.setColorFilter(mContext.getResources().getColor(R.color.colorBlack));
            holder.petred.setColorFilter(mContext.getResources().getColor(R.color.colorBlack));
            holder.minorder.setColorFilter(mContext.getResources().getColor(R.color.colorBlack));

            //holder.llTopLayout.setBackgroundColor(mContext.getResources().getColor(R.color.dark_gray));
        }

        holder.tv_restaurant_name.setText(listCategory.get(position).getRestaurantName());
        /*if (listCategory.get(position).getRestaurantOrderAvailable().equalsIgnoreCase("false")) {
            holder.llTopLayout.setBackgroundColor(mContext.getResources().getColor(R.color.dark_gray));
        } else {
            holder.llTopLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }*/
        if (listCategory.get(position).getSponsorAvailable().equalsIgnoreCase("Yes")) {
            holder.lnr_sponsered.setVisibility(View.VISIBLE);
        } else {
            holder.lnr_sponsered.setVisibility(View.INVISIBLE);
        }
        if (!listCategory.get(position).getRatingAvg().equalsIgnoreCase("0")) {
            holder.tv_restaurant_total_review.setText("(" + listCategory.get(position).getRatingAvg() + " Reviews)");
        }

        if (listCategory.get(position).getRestaurantTimeStatus1().contains("open") ||
                listCategory.get(position).getRestaurantTimeStatus1().contains("Offen bei")) {
            if (listCategory.get(position).getRestaurantOrderAvailable().equalsIgnoreCase("false")) {
                holder.tv_item_discount_open_close.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            } else {
                holder.lnr_open_close.setBackgroundResource(R.drawable.circle_background);
                holder.tv_item_discount_open_close.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            }
            holder.tv_item_discount_open_close.setText(listCategory.get(position).getRestaurantTimeStatus1());
        } else if (listCategory.get(position).getRestaurantTimeStatus1().contains("Preorder")
                || listCategory.get(position).getRestaurantTimeStatus1().contains("Jetzt offen")) {
            if (listCategory.get(position).getRestaurantOrderAvailable().equalsIgnoreCase("false")) {
                holder.tv_item_discount_open_close.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            } else {
                holder.lnr_open_close.setBackgroundResource(R.drawable.circle_back_orange);
                holder.tv_item_discount_open_close.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            }
            holder.tv_item_discount_open_close.setText(listCategory.get(position).getRestaurantTimeStatus1());
        } else if (listCategory.get(position).getRestaurantTimeStatus1().contains("closed") ||
                listCategory.get(position).getRestaurantTimeStatus1().contains("Jetzt geschlossen")) {

            if (listCategory.get(position).getRestaurantOrderAvailable().equalsIgnoreCase("false")) {
                holder.tv_item_discount_open_close.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            } else {
                holder.lnr_open_close.setBackgroundResource(R.drawable.circle_back_red);
                holder.tv_item_discount_open_close.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            }
            holder.tv_item_discount_open_close.setText(listCategory.get(position).getRestaurantTimeStatus1());
        }

        if (listCategory.get(position).getRestaurantCuisine() != null) {
            if (!listCategory.get(position).getRestaurantCuisine().equalsIgnoreCase(" ")) {
                holder.tv_restaurant_cusine_name.setVisibility(View.VISIBLE);
                holder.tv_restaurant_cusine_name.setText(listCategory.get(position).getRestaurantCuisine());
            } else {
                holder.tv_restaurant_cusine_name.setVisibility(View.GONE);
            }
        }
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        String jj = hh.getSymbol();
        holder.tv_restaurant_address.setText(listCategory.get(position).getRestaurantAddress());
        holder.txt_view_timer.setText(listCategory.get(position).getRestaurantAvarageDeliveryTime());
        holder.txt_rest_free_order.setText(jj + " " + dotToCommaClass.changeDot(listCategory.get(position).getRestaurantDeliverycharge()));
        holder.txt_rest_min_order.setText(dotToCommaClass.changeDot(listCategory.get(position).getRestaurantMinimumorder()));
        //holder.tv_restaurant_miles.setText("(" + listCategory.get(position).getRestaurantDeliveryDistance() + ")");

        com.app.liferdeal.ui.activity.restaurant.GPSTracker gpsTracker = new GPSTracker(mContext);
        String distanceCalculate = distance(listCategory.get(position).getRestaurantLatitudePoint(), listCategory.get(position).getRestaurantLongitudePoint(), gpsTracker.getLatitude() + "", gpsTracker.getLongitude() + "") + " miles";
        holder.tv_restaurant_miles.setText(distanceCalculate);
        if (listCategory.get(position).getRestaurantLogo() != null) {
            Glide.with(mContext).load(Uri.parse(listCategory.get(position).getRestaurantLogo())).into(holder.iv_restaurant_logo);
        }

        if (!listCategory.get(position).getRatingValue().equalsIgnoreCase("null")) {
            holder.ratingBar.setRating(Float.parseFloat(listCategory.get(position).getRatingValue()));
        }

        if (!listCategory.get(position).getDiscountAvailable().equalsIgnoreCase("")) {
            holder.txt_discount_avail.setVisibility(View.VISIBLE);
            holder.txt_discount_avail.setText(listCategory.get(position).getDiscountAvailable() + " % Off");
        } else {
            holder.txt_discount_avail.setVisibility(View.GONE);
        }

//        System.out.println("===== rating vlue : " + listCategory.get(position).getRatingValue());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCategory.get(position).getRestaurantOrderAvailable().equalsIgnoreCase("true")) {
                    Intent i = new Intent(mContext, RestaurantDetails.class);
                    i.putExtra("RESTID", listCategory.get(position).getId());
                    i.putExtra("RESTName", listCategory.get(position).getRestaurantName());
                    i.putExtra("RESTCOVER", listCategory.get(position).getRestaurantCover());
                    i.putExtra("RESTCUSINE", listCategory.get(position).getRestaurantCuisine());
                    i.putExtra("RESTLOGO", listCategory.get(position).getRestaurantLogo());
                    i.putExtra("RESTADDRESS", listCategory.get(position).getRestaurantAddress());
                    i.putExtra("RESTISOPEN", listCategory.get(position).getRestaurantTimeStatus1());
                    i.putExtra("RATINGBARDATA", listCategory.get(position).getRatingValue());
                    i.putExtra("LOCATIONDISTANCE", distanceCalculate);
                    i.putExtra("HMDLVRYAVAIL", listCategory.get(position).getHomeDeliveryAvailable());
                    i.putExtra("PCKAVAILABLE", listCategory.get(position).getPickupAvailable());
                    i.putExtra("DINAVAILABLE", listCategory.get(position).getDineInAvailable());
                    i.putExtra("RESDELORAVAIL", listCategory.get(position).getRestaurantDeliverycharge().toString());

                    //for deliver,pickup and dinein
                    SharedPreferencesData sharedPreferencesData = new SharedPreferencesData(mContext);
                    sharedPreferencesData.createNewSharedPreferences(Constants.FORDELIVERY);
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, Constants.HMDLVRYAVAIL, listCategory.get(position).getHomeDeliveryAvailable());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, Constants.PCKAVAILABLE, listCategory.get(position).getPickupAvailable());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, Constants.DINAVAILABLE, listCategory.get(position).getDineInAvailable());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, Constants.RESDELORAVAIL, listCategory.get(position).getRestaurantDeliverycharge());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, "discount_available", listCategory.get(position).getDiscountAvailable());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, "restaurant_serviceFees", listCategory.get(position).getRestaurantServiceFees());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, "restaurant_serviceVat", listCategory.get(position).getRestaurantServiceVat());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, Constants.FORCASHONDELIVERY, listCategory.get(position).getRestaurantOnlycashonAvailable());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, Constants.PAYPALPAY, listCategory.get(position).getPayPaypalAvailable());
                    sharedPreferencesData.setSharedPreferenceData(Constants.FORDELIVERY, Constants.CARDPAY, listCategory.get(position).getRestaurantCardacceptAvailable());
                    Log.e("Response=",listCategory.get(position).getRestaurantOnlycashonAvailable()+"  "+listCategory.get(position).getPayPaypalAvailable()+"   "+listCategory.get(position).getRestaurantCardacceptAvailable());
                    mContext.startActivity(i);

                } else {
                    openDialog(listCategory.get(position).getRestaurantTimeStatusText());
                }
            }
        });
      /*  holder.card_subCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityBase.listDiscription.clear();
                activityBase.SubParentID = listSubCategory.get(position).getSubParentID();
                activityBase.setCurrent_fragment(new FragmentItemList(),FragmentItemList.FRAG_TITEL);
            }
        });*/
    }

    private String distance(String lat1, String lon1, String lat2, String lon2) {

        double theta = Double.parseDouble(lon1) - Double.parseDouble(lon2);
        double dist = Math.sin(deg2rad(Double.parseDouble(lat1)))
                * Math.sin(deg2rad(Double.parseDouble(lat2)))
                + Math.cos(deg2rad(Double.parseDouble(lat1)))
                * Math.cos(deg2rad(Double.parseDouble(lat2)))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        DecimalFormat df2 = new DecimalFormat("#.##");

        return (df2.format(dist));
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView tv_restaurant_total_review, tv_item_discount_open_close, tv_restaurant_name, tv_restaurant_cusine_name, tvSponsored,
                tv_restaurant_address, tv_restaurant_miles, txt_view_timer, txt_rest_min_order, txt_rest_free_order, txt_discount_avail;
        private ImageView iv_restaurant_logo, kidred, petred, minorder;
        private RatingBar ratingBar;
        private LinearLayout lnr_open_close, lnr_sponsered, llTopLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_restaurant_name = itemView.findViewById(R.id.tv_restaurant_name);
            tv_item_discount_open_close = itemView.findViewById(R.id.tv_item_discount_open_close);
            tv_restaurant_cusine_name = itemView.findViewById(R.id.tv_restaurant_cusine_name);
            tv_restaurant_address = itemView.findViewById(R.id.tv_restaurant_address);
            tv_restaurant_miles = itemView.findViewById(R.id.tv_restaurant_miles);
            txt_view_timer = itemView.findViewById(R.id.txt_view_timer);
            txt_rest_min_order = itemView.findViewById(R.id.txt_rest_min_order);
            txt_rest_free_order = itemView.findViewById(R.id.txt_rest_free_order);
            txt_discount_avail = itemView.findViewById(R.id.txt_discount_avail);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            iv_restaurant_logo = itemView.findViewById(R.id.iv_restaurant_logo);
            lnr_open_close = itemView.findViewById(R.id.lnr_open_close);
            tv_restaurant_total_review = itemView.findViewById(R.id.tv_restaurant_total_review);
            lnr_sponsered = itemView.findViewById(R.id.lnr_sponsered);
            tvSponsored = itemView.findViewById(R.id.tvSponsored);
            llTopLayout = itemView.findViewById(R.id.llTopLayout);
            kidred = itemView.findViewById(R.id.kidred);
            petred = itemView.findViewById(R.id.petred);
            minorder = itemView.findViewById(R.id.minorder);
        }
    }

    public void updateList(List<RestaurantMainModel.SearchResult> list, String places) {
        System.out.println("===== checkfromwher places 1 : " + places);
        listCategory = list;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RestaurantMainModel.SearchResult> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filterList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (RestaurantMainModel.SearchResult item : filterList) {
                    if (item.getRestaurantCuisine().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listCategory.clear();
            listCategory.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private void openDialog(String successMsg) {
        final Dialog dialog = new Dialog(mContext, R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);

        AppCompatTextView tvTitle = dialog.findViewById(R.id.tvTitle);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);
        AppCompatTextView tvCancel = dialog.findViewById(R.id.tvCancel);
        tvTitle.setText(successMsg);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        tvOk.setOnClickListener(view -> {
            dialog.dismiss();
            //LoginSuccess();
        });
    }
}
