package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.LanguageModel;
import com.app.liferdeal.model.restaurant.SubItemsRecord;
import com.app.liferdeal.ui.interfaces.ItemClickListener;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.Holder> {
    private List<LanguageModel.LanguageListList> listCategory;
    private List<SubItemsRecord> listFilterSubCategory;
    private Context mContext;
    private ItemClickListener mClickListener;
    private Boolean[] selectedLang;
    private PrefsHelper authPreference;
    static int post;
    private int lastCheckedPosition = -1;
    static int flag = 0;

    public LanguageAdapter(Context context, List<LanguageModel.LanguageListList> listSubCategory, Boolean[] selectedLang) {
        this.mContext = context;
        this.listCategory = listSubCategory;
        this.selectedLang = selectedLang;
        //  this.listFilterSubCategory = listFilterSubCategory;
        //  restaurantDetailsAdapterInterface = restaurantDetailsAdapterInterface1;
        authPreference = new PrefsHelper(context);
    }

    @NonNull
    @Override
    public LanguageAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_fragment_row, parent, false);
        return new LanguageAdapter.Holder(view);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.Holder holder, final int position) {
      /*  if (selectedLang[position])
            holder.rbEng.setChecked(true);
        else
            holder.rbEng.setChecked(false);*/

        holder.rbEng.setText(listCategory.get(position).getLangName());


        if (listCategory.get(position).getLangIcon() != null) {
            Glide.with(mContext).load(Uri.parse(listCategory.get(position).getLangIcon())).into(holder.ivEnglish);
        }

        if (lastCheckedPosition == -1) {
            if (authPreference.getPref(Constants.LNG_CODE).toString().equalsIgnoreCase("en") && listCategory.get(position).getLangName().equalsIgnoreCase("English")) {
                holder.rbEng.setChecked(true);
                post = position;
            } else if (authPreference.getPref(Constants.LNG_CODE).toString().equalsIgnoreCase("de") && listCategory.get(position).getLangName().equalsIgnoreCase("german")) {
                holder.rbEng.setChecked(true);
                post = position;
            } else {
                holder.rbEng.setChecked(false);
            }
        }

        if (lastCheckedPosition != -1) {
            holder.rbEng.setChecked(position == lastCheckedPosition);
        }

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<listCategory.size();i++){
                   if (position==i){
                       holder.rbEng.setChecked(true);
                   }else{
                       holder.rbEng.setChecked(false);
                   }

                }
            }
        });*/

  /*      holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listFilterSubCategory.get(position).getSizeavailable().equalsIgnoreCase("yes")) {
                    restaurantDetailsAdapterInterface.getClickData(listFilterSubCategory.get(position).getItemID(), listFilterSubCategory.get(position).getRestaurantPizzaItemName());
                    Intent i = new Intent(mContext, AddClickDetails.class);
                    i.putExtra("CLICKITEMID", listFilterSubCategory.get(position).getItemID());
                    i.putExtra("CLICKITEMNAME", listFilterSubCategory.get(position).getRestaurantPizzaItemName());
                    mContext.startActivity(i);
                } else {
                    restaurantDetailsAdapterInterface.getClickData(listFilterSubCategory.get(position).getItemID(), listFilterSubCategory.get(position).getRestaurantPizzaItemName());
                }

            }
        });*/

    }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RadioButton rbEng;
        AppCompatImageView ivEnglish;

        public Holder(@NonNull View itemView) {
            super(itemView);
            rbEng = itemView.findViewById(R.id.rbEng);
            ivEnglish = itemView.findViewById(R.id.ivEnglish);
            itemView.setOnClickListener(this);
            rbEng.setOnClickListener(this);
//            rbEng.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    authPreference.set(Constants.LNG_CODE, listCategory.get(getAdapterPosition()).getLangCode());
//                    int copyOfLastCheckedPosition = lastCheckedPosition;
//                    lastCheckedPosition = getAdapterPosition();
//                    //notifyItemChanged(copyOfLastCheckedPosition);
//                    //notifyItemChanged(lastCheckedPosition);
//                    notifyDataSetChanged();
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                //Toast.makeText(mContext,"Toast Called",Toast.LENGTH_LONG).show();
//                if (listCategory.get(getAdapterPosition()).getLangName().equalsIgnoreCase("English")) {
                authPreference.set(Constants.LNG_CODE, listCategory.get(getAdapterPosition()).getLangCode());
//                } else if (listCategory.get(getAdapterPosition()).getLangName().equalsIgnoreCase("German")) {
//                    authPreference.set(Constants.LNG_CODE, listCategory.get(getAdapterPosition()).getLangCode());
//                }
                mClickListener.tabItemClick(v, getAdapterPosition(), true);
                lastCheckedPosition = getAdapterPosition();

            }
        }
    }

}
