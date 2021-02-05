package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.menuitems.ComValue;
import com.app.liferdeal.model.menuitems.ComValueItem;
import com.app.liferdeal.ui.activity.restaurant.ComExtraActivity;
import com.app.liferdeal.ui.interfaces.SelectSecItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComValueAdapter extends RecyclerView.Adapter<ComValueAdapter.ComValueViewHolder> {
    List<ComValue>comValues;
    Context context;
    String price;
    String name;
    String desc;
   public interface ComValueClicked{
        void ComClicked(ComValueItem subItemsRecord,int comslot_id,String sec_);
        void ComValueClicked(ComValue comValue);
    }
    ComValueClicked comValueClicked;
    public ComValueAdapter(List<ComValue>comValues, Context context,ComValueClicked comValueClicked){
        this.comValues=comValues;
        this.context=context;
       this.comValueClicked=comValueClicked;
    }
    @NonNull
    @Override
    public ComValueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComValueViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sec_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComValueViewHolder holder, int position) {
ComValue comValue=comValues.get(position);
holder.txt_sec_text.setText(comValue.getSlot_Option_Name());
holder.relative_sec.setTag(position);
holder.relative_sec.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int pos= (int) v.getTag();
        comValueClicked.ComValueClicked(comValues.get(pos));
        if(holder.recyler_sec_item.getVisibility()==View.VISIBLE){
            holder.recyler_sec_item.setVisibility(View.GONE);
            holder.img_plus.setImageResource(R.drawable.plusbtn);
        }
        else{
            holder.recyler_sec_item.setVisibility(View.VISIBLE);
            holder.img_plus.setImageResource(R.drawable.minusbtn);
        }

    }
});
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        ComValueItemAdapter comValueAdapter=new ComValueItemAdapter(comValue.getComboSectionValueItems(), new SelectSecItem() {
            @Override
            public void getClickSecComboItem(ComValueItem subItemsRecord) {
          comValueClicked.ComClicked(subItemsRecord,Integer.parseInt(comValue.getComboslot_id()),comValue.getSlot_Option_Name());
            }
        });
        holder.recyler_sec_item.setAdapter(comValueAdapter);
        holder.recyler_sec_item.setLayoutManager(linearLayoutManager);


    }

    @Override
    public int getItemCount() {
        return comValues.size();
    }

    public class ComValueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_sec_text)
        TextView txt_sec_text;
        @BindView(R.id.img_plus)
        ImageView  img_plus;
        @BindView(R.id.recyler_sec_item)
        RecyclerView recyler_sec_item;
        @BindView(R.id.relative_sec)
        RelativeLayout relative_sec;
        public ComValueViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
