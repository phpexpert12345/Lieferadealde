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
        void getClickData(List<CuisineList> listCategory,int pos);
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
        if(listCategory.get(position).getSelected()){
            holder.txtitemname.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_rounded_bottom));
            holder.txtitemname.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        }
        else{
            holder.txtitemname.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.txtitemname.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }


holder.txtitemname.setTag(position);

        holder.txtitemname.setOnClickListener(view -> {
            int pos= (int) view.getTag();
            if(listCategory.get(pos).getSelected()){
                listCategory.get(pos).setSelected(false);
            }
            else{
                listCategory.get(pos).setSelected(true);
            }

            if (mClickListener != null) {
                mClickListener.getClickData(listCategory,pos);
            }
        });
holder.ivHorizontal.setTag(position);
        holder.ivHorizontal.setOnClickListener(view -> {
            int pos= (int) view.getTag();
            if(listCategory.get(pos).getSelected()){
                listCategory.get(pos).setSelected(false);
            }
            else{
                listCategory.get(pos).setSelected(true);
            }

            if (mClickListener != null) {
                mClickListener.getClickData(listCategory,pos);
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