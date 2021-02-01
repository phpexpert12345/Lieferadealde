package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.FoodExtraModel;
import com.app.liferdeal.ui.interfaces.RestaurantFoodItemExtraAdapterInterface;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestroExtraAdapter extends RecyclerView.Adapter<RestroExtraAdapter.RestroExtraViewHolder> {
    List<FoodExtraModel.MenuItemExtraGroup.SubExtraItemsRecord>extra_items;
    String type;
    String cur;
    DotToCommaClass dotToCommaClass;
    ArrayList<Integer> extra_item_list_id;
    ArrayList<String> temp_extra_item_list_id;
    ArrayList<String> extra_item_list_name;
    ArrayList<String> extra_item_list_price;
    int selected_pos=-1;
    private RestaurantFoodItemExtraAdapterInterface restaurantFoodItemExtraAdapterInterface;

    public RestroExtraAdapter(List<FoodExtraModel.MenuItemExtraGroup.SubExtraItemsRecord> extra_items, String type, String cur, Context context,RestaurantFoodItemExtraAdapterInterface restaurantFoodItemExtraAdapterInterface){
        this.extra_items=extra_items;
        this.type=type;
        this.cur=cur;
        dotToCommaClass=new DotToCommaClass(context);
        this.restaurantFoodItemExtraAdapterInterface=restaurantFoodItemExtraAdapterInterface;
        extra_item_list_id = new ArrayList<>();
        temp_extra_item_list_id = new ArrayList<>();
        extra_item_list_name = new ArrayList<>();
        extra_item_list_price = new ArrayList<>();
    }
    @NonNull
    @Override
    public RestroExtraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestroExtraViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_extra_radio,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestroExtraViewHolder holder, int position) {
        holder.view_extra.setVisibility(View.VISIBLE);
if(type.equalsIgnoreCase("Checkbox")){
    holder.cbitem.setVisibility(View.VISIBLE);
    holder.rbitem.setVisibility(View.GONE);
}
else{
    holder.cbitem.setVisibility(View.GONE);
    holder.rbitem.setVisibility(View.VISIBLE);
}
if(selected_pos==position){

    holder.rbitem.setChecked(true);
}
else{
    holder.rbitem.setChecked(false);
}
        FoodExtraModel.MenuItemExtraGroup.SubExtraItemsRecord subExtraItemsRecord=extra_items.get(position);
holder.txtitemname.setText(subExtraItemsRecord.getFoodAddonsName());
holder.txtprice.setText(cur + " " +dotToCommaClass.changeDot(extra_items.get(position).getFoodPriceAddons()));
holder.cv_RecyclerView.setTag(position);
holder.cv_RecyclerView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int pos= (int) v.getTag();
        selected_pos=pos;
        restaurantFoodItemExtraAdapterInterface.getCheckedItem(extra_items.get(pos).getFoodAddonsName(),extra_items.get(pos).getFoodPriceAddons(),extra_items.get(pos).getExtraID(),extra_items);
        notifyDataSetChanged();


    }
});

    }

    @Override
    public int getItemCount() {
        return extra_items.size();
    }

    public class RestroExtraViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtitemname)
        TextView txtitemname;
        @BindView(R.id.txtprice)
        TextView txtprice;
        @BindView(R.id.cbitem)
        CheckBox cbitem;
        @BindView(R.id.rbitem)
        RadioButton rbitem;
        @BindView(R.id.cv_RecyclerView)
        LinearLayout cv_RecyclerView;
        @BindView(R.id.view_extra)
        View view_extra;
        public RestroExtraViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
