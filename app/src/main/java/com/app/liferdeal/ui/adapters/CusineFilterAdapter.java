package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.CuisineList;
import com.app.liferdeal.model.restaurant.CusineFilterModel;
import com.app.liferdeal.model.restaurant.SubItemsRecord;
import com.app.liferdeal.util.SharedPreferencesData;

import java.util.ArrayList;
import java.util.List;

public class CusineFilterAdapter extends RecyclerView.Adapter<CusineFilterAdapter.Holder> {
    private List<CuisineList> listCategory;
    private List<SubItemsRecord> listFilterSubCategory;
    private Context mContext;
    ArrayList<String> selected_cusines;
    ArrayList<Integer> selected_cusines_id;
    private CusineFilterAdapterInterface cusineFilterAdapterInterface;
    private SharedPreferencesData sharedPreferencesData;

    public interface CusineFilterAdapterInterface {
        void getClickData(ArrayList<Integer> extraId, ArrayList<String> extraName);

    }

    public CusineFilterAdapter(Context context, List<CuisineList> listSubCategory, CusineFilterAdapterInterface cusineFilterAdapterInterface1) {
        this.mContext = context;
        this.listCategory = listSubCategory;
        this.selected_cusines = new ArrayList<>();
        this.selected_cusines_id = new ArrayList<>();
        //this.listFilterSubCategory = listFilterSubCategory;
        cusineFilterAdapterInterface = cusineFilterAdapterInterface1;
        sharedPreferencesData=new SharedPreferencesData(context);

    }


    @NonNull
    @Override
    public CusineFilterAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cusinefilterrow, parent, false);
        return new CusineFilterAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CusineFilterAdapter.Holder holder, final int position) {
        if(listCategory.get(position).getSelected()){
            holder.cbitem.setChecked(true);
        }
        else{
            holder.cbitem.setChecked(false);
        }
        holder.cbitem.setText(listCategory.get(position).getCuisineName());
        holder.cbitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cbitem.isChecked()) {
                    System.out.println("==== checkbox is click");
                    selected_cusines_id.add(listCategory.get(position).getId());
                    selected_cusines.add(listCategory.get(position).getSeoUrlCall());
                    cusineFilterAdapterInterface.getClickData(selected_cusines_id, selected_cusines);

                } else {
                    selected_cusines_id.remove(listCategory.get(position).getId());
                    selected_cusines.remove(listCategory.get(position).getSeoUrlCall());
                    cusineFilterAdapterInterface.getClickData(selected_cusines_id, selected_cusines);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView txtitemname;
        private CheckBox cbitem;
        private LinearLayout llFull;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtitemname = itemView.findViewById(R.id.txtitemname);
            cbitem = itemView.findViewById(R.id.cbitem);
            llFull = itemView.findViewById(R.id.llFull);

            //  iv_restaurant_logo = itemView.findViewById(R.id.iv_restaurant_logo);
        }
    }
}
