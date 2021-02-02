package com.app.liferdeal.ui.adapters;

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
import com.app.liferdeal.model.menuitems.ComValueItem;
import com.app.liferdeal.ui.interfaces.SelectSecItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComValueItemAdapter extends RecyclerView.Adapter<ComValueItemAdapter.ComValueItemViewHolder> {
    List<ComValueItem>comValueItems;
    int select_pos=-1;
    SelectSecItem selectSecItem;
    public ComValueItemAdapter(List<ComValueItem>comValueItems,SelectSecItem selectSecItem){
        this.comValueItems=comValueItems;
        this.selectSecItem=selectSecItem;
    }
    @NonNull
    @Override
    public ComValueItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComValueItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_extra_radio,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComValueItemViewHolder holder, int position) {
ComValueItem comValueItem=comValueItems.get(position);
        holder.cbitem.setVisibility(View.GONE);
        holder.txtprice.setVisibility(View.GONE);
        holder.rbitem.setVisibility(View.VISIBLE);
        if(select_pos==position){
            holder.rbitem.setChecked(true);
        }
        else{
            holder.rbitem.setChecked(false);
        }
        holder.txtitemname.setText(comValueItem.getCombo_Slot_ItemName());
        holder.cv_RecyclerView.setTag(position);
        holder.cv_RecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                select_pos=pos;
                selectSecItem.getClickSecComboItem(comValueItems.get(pos));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return comValueItems.size();
    }

    public class ComValueItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtitemname)
        TextView txtitemname;
        @BindView(R.id.cbitem)
        CheckBox cbitem;
        @BindView(R.id.rbitem)
        RadioButton rbitem;
        @BindView(R.id.txtprice)
        TextView txtprice;
        @BindView(R.id.cv_RecyclerView)
        LinearLayout cv_RecyclerView;
        public ComValueItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
