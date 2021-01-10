package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.menuitems.SubItemsRecord;
import com.app.liferdeal.ui.activity.restaurant.AddClickDetails;
import com.app.liferdeal.ui.interfaces.SelectMenuItem;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.Currency;
import java.util.List;

public class RestaurantDetailsAdapter extends RecyclerView.Adapter<RestaurantDetailsAdapter.Holder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private List<SubItemsRecord> listFilterSubCategory;
    private Context mContext;
    private SelectMenuItem restaurantDetailsAdapterInterface;
    private String mainClickRestId, mainClickRestName;
    private PrefsHelper prefsHelper;
    DotToCommaClass dotToCommaClass;

    public interface RestaurantDetailsAdapterInterface {
        void getClickData(int otemId, String itemName);
    }

    public void setClickListener(SelectMenuItem itemClickListener) {
        this.restaurantDetailsAdapterInterface = itemClickListener;
    }

    public RestaurantDetailsAdapter(Context context, List<SubItemsRecord> listFilterSubCategory, String maniRestClickId, String mainRestClickName) {
        this.mContext = context;
        this.listFilterSubCategory = listFilterSubCategory;
        this.mainClickRestId = maniRestClickId;
        this.mainClickRestName = mainRestClickName;
        prefsHelper = new PrefsHelper(mContext);
        dotToCommaClass=new DotToCommaClass(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.tv_restaurant_no.setText(listFilterSubCategory.get(position).getFoodNameNo() + ".");
        holder.tv_restaurant_name.setText(listFilterSubCategory.get(position).getRestaurantPizzaItemName());
        holder.tv_restaurant_pizza_des.setText(listFilterSubCategory.get(position).getResPizzaDescription());
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        String jj = hh.getSymbol();
        holder.tv_item_cost.setText(jj + "" +dotToCommaClass.changeDot(listFilterSubCategory.get(position).getRestaurantPizzaItemPrice()) );
        if (!listFilterSubCategory.get(position).getRestaurantPizzaItemOldPrice().equalsIgnoreCase("")) {
            holder.tv_item_cost_old.setText(jj + "" + dotToCommaClass.changeDot(listFilterSubCategory.get(position).getRestaurantPizzaItemOldPrice()));
            holder.tv_item_cost_old.setPaintFlags(holder.tv_item_cost_old.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (listFilterSubCategory.get(position).getAllergyDescription() != null &&
                !listFilterSubCategory.get(position).getAllergyDescription().equalsIgnoreCase("")) {
            holder.ivInfo.setVisibility(View.VISIBLE);
        } else {
            holder.ivInfo.setVisibility(View.GONE);
        }

        holder.ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvAllergy.setVisibility(View.VISIBLE);
                holder.tvAllergy.setText(listFilterSubCategory.get(position).getAllergyDescription());
            }
        });

        holder.tvCost.setText(dotToCommaClass.changeDot(listFilterSubCategory.get(position).getDiscountFoodAmount()) + "% discount applied. Original price " + dotToCommaClass.changeDot(holder.tv_item_cost_old.getText().toString()));

        if (listFilterSubCategory.get(position).getFoodType() != null) {
            Glide.with(mContext).load(Uri.parse(listFilterSubCategory.get(position).getFoodType()))
                    .placeholder(R.drawable.ic_plate)
                    .into(holder.iv_restaurant_logo);
        } else {
            Glide.with(mContext).load("")
                    .placeholder(R.drawable.ic_plate)
                    .into(holder.iv_restaurant_logo);
        }
        System.out.println("==== list size : " + listFilterSubCategory.size());
        System.out.println("==== list size 1: " + listFilterSubCategory.get(position));
        //  if (listFilterSubCategory.get(position) == listFilterSubCategory.size())

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i = new Intent(mContext, RestaurantDetails.class);
                mContext.startActivity(i);*/
                if (listFilterSubCategory.get(position).getSizeavailable().equalsIgnoreCase("yes")) {
//                    restaurantDetailsAdapterInterface.getClickMenuData(listFilterSubCategory.get(position).getItemID(),
//                            listFilterSubCategory.get(position).getRestaurantPizzaItemName(), listFilterSubCategory.get(position).getRestaurantPizzaItemPrice());
                    Intent i = new Intent(mContext, AddClickDetails.class);
                    i.putExtra("CLICKITEMID", listFilterSubCategory.get(position).getItemID());
                    i.putExtra("CLICKITEMNAME", listFilterSubCategory.get(position).getRestaurantPizzaItemName());
                    i.putExtra("CLICKITEMPRICE", listFilterSubCategory.get(position).getRestaurantPizzaItemPrice());
                    i.putExtra("CLICKPIZZDESC", listFilterSubCategory.get(position).getResPizzaDescription());
                    i.putExtra("mainClickRestId", mainClickRestId);
                    i.putExtra("mainClickRestName", mainClickRestName);
                    i.putExtra("extraTopping", listFilterSubCategory.get(position).getExtraavailable());
                    i.putExtra("img", listFilterSubCategory.get(position).getFoodType());
                    mContext.startActivity(i);
                } else {
                    restaurantDetailsAdapterInterface.getClickMenuData(listFilterSubCategory.get(position).getItemID(),
                            listFilterSubCategory.get(position).getRestaurantPizzaItemName(), listFilterSubCategory.get(position).getRestaurantPizzaItemPrice(),listFilterSubCategory.get(position).getResPizzaDescription());
                }
            }
        });

        holder.tv_plus_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listFilterSubCategory.get(position).getSizeavailable().equalsIgnoreCase("yes")) {
//                    restaurantDetailsAdapterInterface.getClickMenuData(listFilterSubCategory.get(position).getItemID(),
//                            listFilterSubCategory.get(position).getRestaurantPizzaItemName(), listFilterSubCategory.get(position).getRestaurantPizzaItemPrice());
                    Intent i = new Intent(mContext, AddClickDetails.class);
                    i.putExtra("CLICKITEMID", listFilterSubCategory.get(position).getItemID());
                    i.putExtra("CLICKITEMNAME", listFilterSubCategory.get(position).getRestaurantPizzaItemName());
                    i.putExtra("CLICKITEMPRICE", listFilterSubCategory.get(position).getRestaurantPizzaItemPrice());
                    i.putExtra("CLICKPIZZDESC", listFilterSubCategory.get(position).getResPizzaDescription());
                    i.putExtra("mainClickRestId", mainClickRestId);
                    i.putExtra("mainClickRestName", mainClickRestName);
                    i.putExtra("extraTopping", listFilterSubCategory.get(position).getExtraavailable());
                    i.putExtra("img", listFilterSubCategory.get(position).getFoodType());
                    mContext.startActivity(i);
                } else {
                    restaurantDetailsAdapterInterface.getClickMenuData(listFilterSubCategory.get(position).getItemID(),
                            listFilterSubCategory.get(position).getRestaurantPizzaItemName(), listFilterSubCategory.get(position).getRestaurantPizzaItemPrice(),listFilterSubCategory.get(position).getResPizzaDescription());
                    if (listFilterSubCategory.get(position).getSizeavailable().equalsIgnoreCase("yes")){
                        holder.linearOnlyPlus.setVisibility(View.VISIBLE);
                        holder.linearWithQuantity.setVisibility(View.INVISIBLE);
                    }else{
                        holder.linearOnlyPlus.setVisibility(View.INVISIBLE);
                        holder.linearWithQuantity.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantDetailsAdapterInterface.getClickMenuData(listFilterSubCategory.get(position).getItemID(),
                        listFilterSubCategory.get(position).getRestaurantPizzaItemName(), listFilterSubCategory.get(position).getRestaurantPizzaItemPrice(),listFilterSubCategory.get(position).getResPizzaDescription());
                holder.tv_quantity.setText(String.valueOf(Integer.parseInt(holder.tv_quantity.getText().toString())+1));
            }
        });
        holder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = String.valueOf(Integer.parseInt(holder.tv_quantity.getText().toString())-1);
                if (qty.equalsIgnoreCase("0")){
                    holder.linearOnlyPlus.setVisibility(View.VISIBLE);
                    holder.linearWithQuantity.setVisibility(View.INVISIBLE);
                }else{
                    holder.tv_quantity.setText(qty);
                }
                restaurantDetailsAdapterInterface.getClickMenuDataRemoved(listFilterSubCategory.get(position).getItemID(),
                        listFilterSubCategory.get(position).getRestaurantPizzaItemName(), listFilterSubCategory.get(position).getRestaurantPizzaItemPrice(),
                        Integer.parseInt(qty));

            }
        });

    }

    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return listFilterSubCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView tv_restaurant_name, tvAllergy, tvCost, tv_restaurant_no, tv_plus_corner,tv_quantity, tv_restaurant_pizza_des, tv_item_cost, tv_item_cost_old,
                tv_restaurant_address, tv_restaurant_miles, txt_view_timer, txt_rest_min_order, txt_rest_free_order, txt_discount_avail,tv_plus,iv_minus;
        private ImageView iv_restaurant_logo, shop_img_places_cat;
        private RatingBar ratingBar;
        private AppCompatImageView ivInfo;
        private LinearLayout linearOnlyPlus,linearWithQuantity;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_restaurant_name = itemView.findViewById(R.id.tv_restaurant_name);
            tv_restaurant_pizza_des = itemView.findViewById(R.id.tv_restaurant_pizza_des);
            tvCost = itemView.findViewById(R.id.tvCost);
            tv_plus = itemView.findViewById(R.id.tv_plus);
            tv_plus_corner = itemView.findViewById(R.id.tv_plus_corner);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_item_cost = itemView.findViewById(R.id.tv_item_cost);
            tv_item_cost_old = itemView.findViewById(R.id.tv_item_cost_old);
            iv_restaurant_logo = itemView.findViewById(R.id.iv_restaurant_logo);
            tv_restaurant_no = itemView.findViewById(R.id.tv_restaurant_no);
            ivInfo = itemView.findViewById(R.id.ivInfo);
            tvAllergy = itemView.findViewById(R.id.tvAllergy);
            linearOnlyPlus = itemView.findViewById(R.id.linearOnlyPlus);
            linearWithQuantity = itemView.findViewById(R.id.linearWithQuantity);
            iv_minus = itemView.findViewById(R.id.iv_minus);

        }
    }
}
