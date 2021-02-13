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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.FoodExtraModel;
import com.app.liferdeal.ui.interfaces.RestaurantFoodItemExtraAdapterInterface;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class RestaurantFoodItemExtraAdapter extends RecyclerView.Adapter<RestaurantFoodItemExtraAdapter.Holder> {
    private List<FoodExtraModel.MenuItemExtraGroup> listCategory;
    private List<FoodExtraModel.MenuItemExtraGroup.SubExtraItemsRecord> listSubCategory;
    private Context mContext;
    ArrayList<Integer> extra_item_list_id;
    ArrayList<String> temp_extra_item_list_id;
    ArrayList<String> extra_item_list_name;
    ArrayList<String> extra_item_list_price;
    RadioButton globalradio = null;
    private PrefsHelper prefsHelper;
    private int itemId;
    private int itemSizeId;
    private CheckBox globalCheckbox = null;
    private RestaurantFoodItemExtraAdapterInterface restaurantFoodItemExtraAdapterInterface;
    DotToCommaClass dotToCommaClass;
    int selected_pos=-1;



    public RestaurantFoodItemExtraAdapter(Context context, List<FoodExtraModel.MenuItemExtraGroup> listCategory1, List<FoodExtraModel.MenuItemExtraGroup.SubExtraItemsRecord> listSubCategory, RestaurantFoodItemExtraAdapterInterface restaurantFoodItemExtraAdapterInterface1, int itemIdd, int itemSizeIdd) {
        this.mContext = context;
        this.listCategory = listCategory1;
        this.listSubCategory = listSubCategory;
        this.restaurantFoodItemExtraAdapterInterface = restaurantFoodItemExtraAdapterInterface1;
        itemId = itemIdd;
        itemSizeId = itemSizeIdd;
        extra_item_list_id = new ArrayList<>();
        temp_extra_item_list_id = new ArrayList<>();
        extra_item_list_name = new ArrayList<>();
        extra_item_list_price = new ArrayList<>();
        prefsHelper = new PrefsHelper(mContext);
        dotToCommaClass=new DotToCommaClass(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_extra_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        // listFilterSubCategory = listCategory.get(position).getSubItemsRecord();


        // holder.txtitemname.setText(listCategory.get(position).getRestaurantPizzaItemName());
      /*  if (listCategory.get(position).getExtraavailable().equalsIgnoreCase("yes"))
        {
            holder.btn_add_extra.setVisibility(View.VISIBLE);
        }*/
        if (listCategory.get(0).getFoodAddonsSelectionType().equalsIgnoreCase("Checkbox")) {
            holder.cbitem.setVisibility(View.VISIBLE);
            holder.rbitem.setVisibility(View.GONE);
            holder.txt_extra_head.setVisibility(View.GONE);
            holder.txtprice.setVisibility(View.VISIBLE);
            holder.recyler_extras.setVisibility(View.GONE);
            holder.txtitemname.setText(listSubCategory.get(position).getFoodAddonsName());
            holder.view_extra.setVisibility(View.VISIBLE);
            Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
            String jj = hh.getSymbol();
            if(listSubCategory.get(position).getFoodPriceAddons().equalsIgnoreCase("0.00")){
                holder.txtprice.setVisibility(View.GONE);
            }
            else {
                holder.txtprice.setVisibility(View.VISIBLE);
                holder.txtprice.setText(jj + " " + dotToCommaClass.changeDot(listSubCategory.get(position).getFoodPriceAddons()));
            }
        } else {
            holder.cbitem.setVisibility(View.GONE);
            holder.rbitem.setVisibility(View.GONE);
            holder.txt_extra_head.setVisibility(View.VISIBLE);
            holder.txt_extra_head.setText(listCategory.get(position).getFoodGroupName());
            holder.txtitemname.setVisibility(View.GONE);
            holder.txtprice.setVisibility(View.GONE);
            holder.view_extra.setVisibility(View.GONE);
            Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
            String jj = hh.getSymbol();
            RestroExtraAdapter restroExtraAdapter=new RestroExtraAdapter(listCategory.get(position).getSubExtraItemsRecord(),listCategory.get(0).getFoodAddonsSelectionType(),jj,mContext,restaurantFoodItemExtraAdapterInterface);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
            holder.recyler_extras.setVisibility(View.VISIBLE);
            holder.recyler_extras.setAdapter(restroExtraAdapter);
            holder.recyler_extras.setLayoutManager(linearLayoutManager);
        }

//        holder.cbitem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.cbitem.isChecked()) {
//                    System.out.println("==== checkbox is click");
//
//                    extra_item_list_id.add(listSubCategory.get(position).getExtraID());
//                    temp_extra_item_list_id.add(itemId + "_" + itemSizeId + "_" + listSubCategory.get(position).getExtraID().toString());
//                    extra_item_list_name.add(holder.txtitemname.getText().toString());
//                    extra_item_list_price.add(listSubCategory.get(position).getFoodPriceAddons());
//                    restaurantFoodItemExtraAdapterInterface.getrvcheckeddata(temp_extra_item_list_id, extra_item_list_id, extra_item_list_name, extra_item_list_price);
//                } else {
//                    System.out.println("==== checkbox else is click");
//
//                    extra_item_list_id.remove(listSubCategory.get(position).getExtraID());
//                    temp_extra_item_list_id.add(itemId + "_" + itemSizeId + "_" + listSubCategory.get(position).getExtraID().toString());
//                    extra_item_list_name.remove(holder.txtitemname.getText().toString());
//                    extra_item_list_price.remove(listSubCategory.get(position).getFoodPriceAddons());
//                    restaurantFoodItemExtraAdapterInterface.getrvcheckeddata(temp_extra_item_list_id, extra_item_list_id, extra_item_list_name, extra_item_list_price);
//
//                }
//                System.out.println("=== extra add : " + extra_item_list_id + extra_item_list_name + extra_item_list_price);
//            }
//        });

        /*holder.rbitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton checked_rb = (RadioButton) view;
                if(globalradio != null) {
                    globalradio.setChecked(false);
                    System.out.println("==== radio is click");
                    extra_item_list_id.add(listSubCategory.get(position).getExtraID());
                    temp_extra_item_list_id.add(itemId+"_"+itemSizeId+"_"+listSubCategory.get(position).getExtraID().toString());
                    extra_item_list_name.add(holder.txtitemname.getText().toString());
                    extra_item_list_price.add(listSubCategory.get(position).getFoodPriceAddons());
                    restaurantFoodItemExtraAdapterInterface.getrvcheckeddata(temp_extra_item_list_id,extra_item_list_id, extra_item_list_name, extra_item_list_price);

                }
                else {
                    System.out.println("==== radio is click");

                    extra_item_list_id.remove(listSubCategory.get(position).getExtraID());
                    temp_extra_item_list_id.add(itemId+"_"+itemSizeId+"_"+listSubCategory.get(position).getExtraID().toString());
                    extra_item_list_name.remove(holder.txtitemname.getText().toString());
                    extra_item_list_price.remove(listSubCategory.get(position).getFoodPriceAddons());
                    restaurantFoodItemExtraAdapterInterface.getrvcheckeddata(temp_extra_item_list_id,extra_item_list_id, extra_item_list_name, extra_item_list_price);

                }
                globalradio=checked_rb;

                System.out.println("=== extra add rb: " + extra_item_list_id + extra_item_list_name + extra_item_list_price);
            }
        });*/
        holder.cv_RecyclerView.setTag(position);

        holder.cv_RecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                CheckBox checkbox_fb = holder.cbitem;
                if(listCategory.get(0).getFoodAddonsSelectionType().equalsIgnoreCase("Checkbox")) {
                    //   checkbox_fb.setChecked(true);
                    if (checkbox_fb.isChecked()) {
                        System.out.println("==== checkbox is click");

                   /* extra_item_list_id.add(listSubCategory.get(position).getExtraID());
                    temp_extra_item_list_id.add(itemId+"_"+itemSizeId+"_"+listSubCategory.get(position).getExtraID().toString());
                    extra_item_list_name.add(holder.txtitemname.getText().toString());
                    extra_item_list_price.add(listSubCategory.get(position).getFoodPriceAddons());
                    restaurantFoodItemExtraAdapterInterface.getrvcheckeddata(temp_extra_item_list_id,extra_item_list_id, extra_item_list_name, extra_item_list_price);
*/
                        checkbox_fb.setChecked(false);
                        if (listSubCategory.size() > 0) {
                            extra_item_list_id.remove(listSubCategory.get(pos).getExtraID());
                            temp_extra_item_list_id.add(itemId + "_" + itemSizeId + "_" + listSubCategory.get(pos).getExtraID().toString());
                            extra_item_list_name.remove(holder.txtitemname.getText().toString());
                            extra_item_list_price.remove(listSubCategory.get(pos).getFoodPriceAddons());
                            restaurantFoodItemExtraAdapterInterface.getrvcheckeddata(temp_extra_item_list_id, extra_item_list_id, extra_item_list_name, extra_item_list_price);
                        }

                    } else {
                        System.out.println("==== checkbox else is click");
                        checkbox_fb.setChecked(true);
                        if (listSubCategory.size() > 0) {
                            extra_item_list_id.add(listSubCategory.get(pos).getExtraID());
                            temp_extra_item_list_id.add(itemId + "_" + itemSizeId + "_" + listSubCategory.get(pos).getExtraID().toString());
                            extra_item_list_name.add(holder.txtitemname.getText().toString());
                            extra_item_list_price.add(listSubCategory.get(pos).getFoodPriceAddons());
                            restaurantFoodItemExtraAdapterInterface.getrvcheckeddata(temp_extra_item_list_id, extra_item_list_id, extra_item_list_name, extra_item_list_price);
                        }

                    }
                }


                System.out.println("=== extra add : " + extra_item_list_id + extra_item_list_name + extra_item_list_price);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listCategory.get(0).getFoodAddonsSelectionType().equalsIgnoreCase("Checkbox")){
            return listSubCategory.size();
        }
        else{
            return listCategory.size();
        }


    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView txtitemname, txtprice, btn_add_extra;
        private RadioButton rbitem;
        private CheckBox cbitem;
        private LinearLayout cv_RecyclerView;
        private TextView txt_extra_head;
        private RecyclerView recyler_extras;
        private View view_extra;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtitemname = itemView.findViewById(R.id.txtitemname);
            txtprice = itemView.findViewById(R.id.txtprice);
            cbitem = (CheckBox) itemView.findViewById(R.id.cbitem);
            rbitem = (RadioButton) itemView.findViewById(R.id.rbitem);
            cv_RecyclerView = itemView.findViewById(R.id.cv_RecyclerView);
            txt_extra_head=itemView.findViewById(R.id.txt_head_extra);
            recyler_extras=itemView.findViewById(R.id.recyler_extras);
            view_extra=itemView.findViewById(R.id.view_extra);
        }
    }
}
