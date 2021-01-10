package com.app.liferdeal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.RestaurantGalleryModel;
import com.app.liferdeal.ui.interfaces.ItemClickListener;

import java.util.List;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private List<RestaurantGalleryModel.FoodGalleryList> list;
    private Boolean[] selectedList;

    public TabsAdapter(Context context, List<RestaurantGalleryModel.FoodGalleryList> list, Boolean[] selectedList) {
        this.mContext = context;
        this.list = list;
        this.selectedList = selectedList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnParty.setText(list.get(position).getTabName());
        if (selectedList[position]) {
            holder.btnParty.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_rounded));
            holder.btnParty.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        } else {
            holder.btnParty.setBackgroundDrawable(null);
            holder.btnParty.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0)
            return list.size();
        else
            return 0;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatButton btnParty;

        ViewHolder(View itemView) {
            super(itemView);
            btnParty = itemView.findViewById(R.id.btnParty);
            btnParty.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.tabItemClick(view, getLayoutPosition(), true);

            //Toast.makeText(mContext,"inside adapter",Toast.LENGTH_LONG).show();
                //notifyDataSetChanged();

        }
    }
}