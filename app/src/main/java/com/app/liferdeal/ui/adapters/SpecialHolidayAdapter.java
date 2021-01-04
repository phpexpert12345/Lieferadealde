package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.DeliveryListInfo;
import com.app.liferdeal.model.restaurant.RestaurantHoliday;

import java.util.List;

public class SpecialHolidayAdapter extends RecyclerView.Adapter<SpecialHolidayAdapter.Holder> {
    private List<RestaurantHoliday> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private RestaurantDetailsQuickInterface restaurantDetailsQuickInterface;
    private int selectedPosition;
    private int selectedPositionItem=0;
    public static SpecialHolidayAdapter.Holder mHolder;
    public interface RestaurantDetailsQuickInterface
    {
        public void getRestaaurantQuickClickData(String restId, int catId, String categoryImage);

    }
    public SpecialHolidayAdapter(Context context, List<RestaurantHoliday>listSubCategory){
        this.mContext=context;
        this.listCategory=listSubCategory;
        //this.selectedPosition = -1;
        this.selectedPosition = 0;

    }
    @NonNull
    @Override
    public SpecialHolidayAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_holiday_row_list,parent,false);
        return new SpecialHolidayAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialHolidayAdapter.Holder holder, final int position) {
        holder.txt_date.setText(listCategory.get(position).getHolidayDate());
       holder.txt_time.setText(listCategory.get(position).getHolidayStartTime() + "-" + listCategory.get(position).getHolidayEndTime());
    }




    @Override
    public int getItemCount() {
        return listCategory.size();
    }


    class  Holder extends RecyclerView.ViewHolder{
        private TextView txt_date, txt_time;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_time = itemView.findViewById(R.id.txt_time);



        }


    }
}

