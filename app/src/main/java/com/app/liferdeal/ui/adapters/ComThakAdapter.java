package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.menuitems.SingleCom;
import com.app.liferdeal.model.restaurant.RaviCartModle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComThakAdapter extends RecyclerView.Adapter<ComThakAdapter.ComThakViewHolder> {
    Context context;
    ArrayList<RaviCartModle> rr;
    public ComThakAdapter(Context context,ArrayList<RaviCartModle> rr){
        this.context=context;
        this.rr=rr;
    }
    @NonNull
    @Override
    public ComThakViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComThakViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_com_thak_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComThakViewHolder holder, int position) {
        RaviCartModle com=rr.get(position);
        holder.tv_quantity.setText(com.getItem_quantity());
        holder.tv_menu_item_name.setText(com.getItem_name());
        holder.tv_subcat_item_details.setText(com.getItemSubcatDetails());
        double pp = Double.parseDouble(com.getPrice());
        holder.tv_item_price.setVisibility(View.GONE);
        holder.lnr_plus_minus.setVisibility(View.GONE);
        holder.etInstruction.setVisibility(View.GONE);
        List<SingleCom> singleComs=new ArrayList<>();
        if(com.getSize_item_name().contains(",")){
            String[] sec=com.getSize_item_name().split(",");
            String [] size=com.getSize_item_id().split(",");
            String[] tops=com.getExtra_item_name().split(",");
            for(int i=0;i<sec.length;i++){
                SingleCom singleCom=new SingleCom();
                singleCom.section=sec[i];
                singleCom.size=size[i];
                singleCom.tops=tops[i];
                singleComs.add(singleCom);
            }
        }
        else{
            SingleCom singleCom=new SingleCom();
            singleCom.section=com.getSize_item_name();
            singleCom.size=com.getSize_item_id();
            singleCom.tops=com.getExtra_item_name();
            singleComs.add(singleCom);
        }
        if (singleComs.size() > 0) {

            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            SingleComAdapter singleComAdapter=new SingleComAdapter(singleComs);
            holder.recyler_com.setAdapter(singleComAdapter);
            holder.recyler_com.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public int getItemCount() {
        return rr.size();
    }

    public class ComThakViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_menu_item_name)
        TextView tv_menu_item_name;
        @BindView(R.id.tv_subcat_item_details)
        TextView tv_subcat_item_details;
        @BindView(R.id.recyler_com)
        RecyclerView recyler_com;
        @BindView(R.id.iv_minus)
        TextView iv_minus;
        @BindView(R.id.iv_plus)
        TextView iv_plus;
        @BindView(R.id.tv_quantity)
        TextView tv_quantity;
        @BindView(R.id.tv_item_price)
        TextView tv_item_price;
        @BindView(R.id.lnr_plus_minus)
        LinearLayout lnr_plus_minus;
        @BindView(R.id.etInstruction)
        EditText etInstruction;
        public ComThakViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
