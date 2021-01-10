package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.menuitems.MenuCat;
import com.app.liferdeal.ui.interfaces.SelectMenuItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SectionDetailAdapter extends RecyclerView.Adapter<SectionDetailAdapter.Holder> implements SelectMenuItem {
    private List<MenuCat> listCategory;
    private Context mContext;
    private SelectMenuItem restaurantDetailsAdapterInterface;

    public SectionDetailAdapter(Context context, List<MenuCat> listCategory) {
        this.mContext = context;
        this.listCategory = listCategory;
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
        Glide.with(mContext).load(Uri.parse(listCategory.get(position).getCategoryImg())).into(holder.shop_img_places_cat);

        holder.rvItemList.setLayoutManager(new LinearLayoutManager(mContext));
        RestaurantDetailsAdapter adapter = new RestaurantDetailsAdapter(mContext, listCategory.get(position).getSubItemsRecord(), listCategory.get(position).getRestaurantId(), listCategory.get(position).getName());
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
}
