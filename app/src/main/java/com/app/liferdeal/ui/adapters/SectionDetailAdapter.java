package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.liferdeal.R;
import com.app.liferdeal.model.LanguageModel;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.menuitems.ComboList;
import com.app.liferdeal.model.menuitems.MenuCat;
import com.app.liferdeal.model.menuitems.SubItemsRecord;
import com.app.liferdeal.ui.activity.restaurant.ChooseComActivity;
import com.app.liferdeal.ui.interfaces.SelectMenuItem;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionDetailAdapter extends RecyclerView.Adapter<SectionDetailAdapter.Holder> implements SelectMenuItem {
    private List<MenuCat> listCategory;
    private Context mContext;
    private SelectMenuItem restaurantDetailsAdapterInterface;
    private LanguageResponse model;

    public SectionDetailAdapter(Context context, List<MenuCat> listCategory,LanguageResponse model) {
        this.mContext = context;
        this.listCategory = listCategory;
        this.model=model;
    }

    public void setClickListener(SelectMenuItem itemClickListener) {
        this.restaurantDetailsAdapterInterface = itemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.tvName.setText(listCategory.get(position).getCategoryName());
        if(!listCategory.get(position).getCategoryImg().trim().isEmpty()){
            if(!listCategory.get(position).getCategoryImg().equalsIgnoreCase("null")){
                holder.shop_img_places_cat.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(Uri.parse(listCategory.get(position).getCategoryImg())).into(holder.shop_img_places_cat);
            }
            else{
                holder.shop_img_places_cat.setVisibility(View.GONE);
            }
        }
        else{
            holder.shop_img_places_cat.setVisibility(View.GONE);
        }
        holder.rvItemList.setLayoutManager(new LinearLayoutManager(mContext));
        List<SubItemsRecord>subItemsRecords=new ArrayList<>();
if(listCategory.get(position).getComboAvailable().equalsIgnoreCase("Yes")){
    if(listCategory.get(position).getComboList()!=null){
        for(int i=0;i<listCategory.get(position).getComboList().size();i++){
            ComboList comboList=listCategory.get(position).getComboList().get(i);
            SubItemsRecord subItemsRecord=new SubItemsRecord(0);
            subItemsRecord.setRestaurantPizzaItemName(comboList.getDeal_name());
            subItemsRecord.setRestaurantPizzaItemOldPrice(comboList.getRestaurantPizzaItemOldPrice());
            subItemsRecord.setRestaurantPizzaItemPrice(comboList.getRestaurantPizzaItemPrice());
            subItemsRecord.setResPizzaDescription(comboList.getDeal_description());
            subItemsRecord.setExtraavailable("no");
            subItemsRecord.setSizeavailable("no");
            subItemsRecord.setAllergyDescription(comboList.getAllergy_Description());
            subItemsRecord.setRestaurantPizzaID(comboList.getDealID());
            subItemsRecord.setFoodNameNo(comboList.getCombo_NameNo());
            subItemsRecord.setIs_combo(true);
            subItemsRecords.add(subItemsRecord);


        }

    }
    if(listCategory.get(position).getSubItemsRecord().size()>0) {
        listCategory.get(position).getSubItemsRecord().addAll(subItemsRecords);
    }
    else{
        listCategory.get(position).setSubItemsRecord(subItemsRecords);
    }
}

            RestaurantDetailsAdapter adapter = new RestaurantDetailsAdapter(mContext, listCategory.get(position).getSubItemsRecord(), listCategory.get(position).getRestaurantId(), listCategory.get(position).getName(), model,listCategory.get(position).getComboAvailable(),listCategory.get(position).getComboList());
            holder.rvItemList.setAdapter(adapter);
            adapter.setClickListener(this);

    }


    @Override
    public int getItemCount() {
        if (listCategory == null) {
            listCategory = new ArrayList<>();
        }
        return listCategory.size();
    }

    @Override
    public void getClickMenuData(int otemId, String itemName, String price,String subcatItemDetails) {
        restaurantDetailsAdapterInterface.getClickMenuData(otemId, itemName, price,subcatItemDetails);
    }

    class Holder extends RecyclerView.ViewHolder {

        RecyclerView rvItemList;
        TextView tvName;
        ImageView shop_img_places_cat;

        public Holder(@NonNull View itemView) {
            super(itemView);
            rvItemList = itemView.findViewById(R.id.rvItemList);
            tvName = itemView.findViewById(R.id.tvName);
            shop_img_places_cat = itemView.findViewById(R.id.shop_img_places_cat);

        }
    }

    @Override
    public void getClickMenuDataRemoved(int otemId, String itemName, String price, int qty) {
        restaurantDetailsAdapterInterface.getClickMenuDataRemoved(otemId, itemName, price,qty);
    }

    @Override
    public void getClickComboItem(SubItemsRecord subItemsRecord) {

       mContext.startActivity(new Intent(mContext, ChooseComActivity.class).putExtra("com_id",subItemsRecord.getRestaurantPizzaID()).putExtra("combo",subItemsRecord.getRestaurantPizzaItemName()).putExtra("desc",subItemsRecord.getResPizzaDescription()).putExtra("price",subItemsRecord.getRestaurantPizzaItemPrice()));
    }
}
