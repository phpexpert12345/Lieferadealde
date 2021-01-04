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
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.FoodItemSizeModel;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;

import java.util.Currency;
import java.util.List;

public class RestaurantFoodItemSizeAdapter extends RecyclerView.Adapter<RestaurantFoodItemSizeAdapter.Holder> {
    private List<FoodItemSizeModel.RestaurantMenItemsSize> listCategory;
    private Context mContext;
    RadioButton globalradio = null;
    private RestaurantFoodSizeItemInterface restaurantFoodSizeItemInterface;
    private PrefsHelper prefsHelper;
    private Boolean[] selectedList;
    DotToCommaClass dotToCommaClass;

    public interface RestaurantFoodSizeItemInterface {
        void getrvcheckeddata(int position, int itemId, int itemSizeId, String extraAvail, String foodItemName, String pizzaPrice);
    }

    public RestaurantFoodItemSizeAdapter(Context context, List<FoodItemSizeModel.RestaurantMenItemsSize> listSubCategory,
                                         RestaurantFoodSizeItemInterface restaurantFoodSizeItemInterface1, Boolean[] selectedList) {
        this.mContext = context;
        restaurantFoodSizeItemInterface = restaurantFoodSizeItemInterface1;
        this.listCategory = listSubCategory;
        this.selectedList = selectedList;
        prefsHelper = new PrefsHelper(mContext);
        dotToCommaClass=new DotToCommaClass(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_click_details, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        if (selectedList[position]) {
            holder.rbitem.setSelected(true);
            holder.rbitem.setChecked(true);
        } else {
            holder.rbitem.setSelected(false);
            holder.rbitem.setChecked(false);
        }

        holder.txtitemname.setText(listCategory.get(position).getRestaurantPizzaItemName());
        //String hh = Constants.getCurrencySymbol(prefsHelper.getPref(Constants.APP_CURRENCY));
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        System.out.println("==== hh in size  : " + hh);
        String jj = hh.getSymbol();
        System.out.println("==== jj in size  : " + jj);
        holder.txtprice.setText(jj + "  " + dotToCommaClass.changeDot(listCategory.get(position).getRestaurantPizzaItemPrice()));
        // holder.radioButton.setChecked(lastSelectedPosition == position);

        holder.cv_RecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton checked_rb = holder.rbitem;// (RadioButton) view;
                if (globalradio != null) {
                    globalradio.setChecked(false);
                    System.out.println("==== checked not null");
                } else {
                    System.out.println("==== checked  null");

                }
                restaurantFoodSizeItemInterface.getrvcheckeddata(position,listCategory.get(position).getFoodItemID(), listCategory.get(position).getFoodItemSizeID(), listCategory.get(position).getExtraavailable(), listCategory.get(position).getRestaurantPizzaItemName(), listCategory.get(position).getRestaurantPizzaItemPrice());
                globalradio = checked_rb;
                globalradio.setChecked(true);
            }
        });

        holder.rbitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton checked_rb = (RadioButton) view;
                selectedList[position] = true;
                if (globalradio != null) {
//                    globalradio.setChecked(false);
                    System.out.println("==== checked not null");
                } else {
                    System.out.println("==== checked  null");
                }
                restaurantFoodSizeItemInterface.getrvcheckeddata(position, listCategory.get(position).getFoodItemID(), listCategory.get(position).getFoodItemSizeID(), listCategory.get(position).getExtraavailable(), listCategory.get(position).getRestaurantPizzaItemName(), listCategory.get(position).getRestaurantPizzaItemPrice());
                globalradio = checked_rb;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView txtitemname, txtprice;
        private RadioButton rbitem;
        private CheckBox cbitem;
        private LinearLayout cv_RecyclerView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtitemname = itemView.findViewById(R.id.txtitemname);
            txtprice = itemView.findViewById(R.id.txtprice);
            cbitem = (CheckBox) itemView.findViewById(R.id.cbitem);
            rbitem = (RadioButton) itemView.findViewById(R.id.rbitem);
            cv_RecyclerView = itemView.findViewById(R.id.cv_RecyclerView);

        }
    }
}
