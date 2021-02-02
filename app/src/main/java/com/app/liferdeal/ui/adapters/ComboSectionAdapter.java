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
import com.app.liferdeal.model.menuitems.ComboSection;
import com.app.liferdeal.ui.interfaces.SelectSec;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComboSectionAdapter extends RecyclerView.Adapter<ComboSectionAdapter.ComboSectionViewHolder> {
    List<ComboSection>comboSections;
    int selected_pos=-1;
    SelectSec sec;
    public ComboSectionAdapter(List<ComboSection>comboSections,SelectSec sec){
        this.comboSections=comboSections;
        this.sec=sec;
    }
    @NonNull
    @Override
    public ComboSectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComboSectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_extra_radio,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComboSectionViewHolder holder, int position) {
        ComboSection comboSection=comboSections.get(position);
holder.cbitem.setVisibility(View.GONE);
holder.txtprice.setVisibility(View.GONE);
holder.rbitem.setVisibility(View.VISIBLE);
if(selected_pos==position){
    holder.rbitem.setChecked(true);
}
else{
    holder.rbitem.setChecked(false);
}
holder.txtitemname.setText(comboSection.getDeal_slot_name());
holder.cv_RecyclerView.setTag(position);
holder.cv_RecyclerView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int pos= (int) v.getTag();
        selected_pos=pos;
        sec.getClickSec(pos);
        notifyDataSetChanged();
    }
});

    }

    @Override
    public int getItemCount() {
        return comboSections.size();
    }

    public class ComboSectionViewHolder extends RecyclerView.ViewHolder {
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
        public ComboSectionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
