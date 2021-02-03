package com.app.liferdeal.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.menuitems.SingleCom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleComAdapter extends RecyclerView.Adapter<SingleComAdapter.SingleComViewHolder> {
    List<SingleCom>singleComs;
    public SingleComAdapter(List<SingleCom> singleComs){
        this.singleComs=singleComs;
    }
    @NonNull
    @Override
    public SingleComViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SingleComViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_com_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SingleComViewHolder holder, int position) {
SingleCom singleCom=singleComs.get(position);
holder.tv_menu_item_sec.setText(singleCom.section);
holder.tv_menu_item_size.setText(singleCom.size);
if(singleCom.tops.contains("_")){
        String[] extra=singleCom.tops.split("_");
        if(extra.length>0){
            StringBuilder stringBuilder=new StringBuilder();
            for(int i=0;i<extra.length;i++){
                stringBuilder.append("+").append(extra[i]);
                stringBuilder.append("\n");
            }
            if(stringBuilder.length()>0){
                stringBuilder=stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n"));
                holder.tv_menu_item_extra.setText(stringBuilder.toString());
            }
        }
}
    }

    @Override
    public int getItemCount() {
        return singleComs.size();
    }

    public class SingleComViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_menu_item_sec)
        TextView tv_menu_item_sec;
        @BindView(R.id.tv_menu_item_size)
        TextView tv_menu_item_size;
        @BindView(R.id.tv_menu_item_extra)
        TextView tv_menu_item_extra;
        public SingleComViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
