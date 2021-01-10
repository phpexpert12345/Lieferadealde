package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.CuisineList;
import com.app.liferdeal.model.restaurant.CusineFilterModel;
import com.app.liferdeal.model.restaurant.SubItemsRecord;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CusineFiltersAdapter extends RecyclerView.Adapter<CusineFiltersAdapter.Holder> {
    private List<CuisineList> listCategory;
    private List<SubItemsRecord> listFilterSubCategory;
    private Context mContext;
    ArrayList<String> selected_cusines;
    ArrayList<Integer> selected_cusines_id;
    private CusineFilterAdapterInterface mClickListener;
    private Boolean[] selectedList;

    public interface CusineFilterAdapterInterface {
        void getClickData(ArrayList<Integer> extraId, ArrayList<String> extraName, Boolean[] selectedList);
    }

    public CusineFiltersAdapter(Context context, List<CuisineList> listSubCategory, Boolean[] selectedList) {
        this.mContext = context;
        this.listCategory = listSubCategory;
        this.selected_cusines = new ArrayList<>();
        this.selected_cusines_id = new ArrayList<>();
        this.selectedList = selectedList;
        //  this.listFilterSubCategory = listFilterSubCategory;
//        cusineFilterAdapterInterface = cusineFilterAdapterInterface1;

    }

    public void setClickListener(CusineFilterAdapterInterface itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {

        holder.txtitemname.setText(listCategory.get(position).getCuisineName());
        if (listCategory.get(position).getCuisineImg() != null) {
            Glide.with(mContext).load(Uri.parse(listCategory.get(position).getCuisineImg())).into(holder.ivHorizontal);
        }

        if (selectedList[position]) {
            holder.txtitemname.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_rounded_bottom));
            holder.txtitemname.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        } else {
            holder.txtitemname.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.txtitemname.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }

        holder.txtitemname.setOnClickListener(view -> {
            if (selectedList[position]) {
                selected_cusines_id.remove(listCategory.get(position).getId());
                selected_cusines.remove(listCategory.get(position).getSeoUrlCall());
                selectedList[position] = false;
            } else {
                selected_cusines_id.add(listCategory.get(position).getId());
                selected_cusines.add(listCategory.get(position).getSeoUrlCall());
                selectedList[position] = true;
            }
            if (mClickListener != null) {
                mClickListener.getClickData(selected_cusines_id, selected_cusines, selectedList);
            }
        });

        holder.ivHorizontal.setOnClickListener(view -> {
            if (selectedList[position]) {
                selected_cusines_id.remove(listCategory.get(position).getId());
                selected_cusines.remove(listCategory.get(position).getSeoUrlCall());
                selectedList[position] = false;
            } else {
                selected_cusines_id.add(listCategory.get(position).getId());
                selected_cusines.add(listCategory.get(position).getSeoUrlCall());
                selectedList[position] = true;
            }
            if (mClickListener != null) {
                mClickListener.getClickData(selected_cusines_id, selected_cusines, selectedList);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listCategory == null) {
            listCategory = new ArrayList<>();
        }
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView txtitemname;
        private ImageView ivHorizontal;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtitemname = itemView.findViewById(R.id.txtitemname);
            ivHorizontal = itemView.findViewById(R.id.ivHorizontal);
        }
    }
}