package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.AddressModel;
import com.app.liferdeal.ui.interfaces.DeleteAddressListener;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class SavedAddressAdapter extends RecyclerView.Adapter<SavedAddressAdapter.Holder> {
    private List<AddressModel.Deliveryaddress> list;
    private Context mContext;
    private PrefsHelper prefsHelper;
    private DeleteAddressListener listener;
    private Boolean[] selectedItem;

    public SavedAddressAdapter(Context context, List<AddressModel.Deliveryaddress> list, Boolean[] selectedItem) {
        this.mContext = context;
        this.list = list;
        this.selectedItem = selectedItem;
        prefsHelper = new PrefsHelper(mContext);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_address, parent, false);
        return new Holder(view);
    }

    public void setClickListener(DeleteAddressListener clickListener) {
        this.listener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        if (selectedItem[position]) {
            holder.rbAddressName.setChecked(true);
        } else {
            holder.rbAddressName.setChecked(false);
        }
        holder.rbAddressName.setText(list.get(position).getAddressTitle());
        holder.tvLocation.setText(list.get(position).getCompanyStreetNo() + " " + list.get(position).getCustomerFlatName()
                + " " + list.get(position).getCompanyStreet() + " " + list.get(position).getCityName() + " " + list.get(position).getZipcode());
        holder.tvContactAddress.setText("Mobile: " + list.get(position).getUserPhone());
        holder.ivDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemDelete(list.get(position).getId());
            }
        });


        holder.constraint_address.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemSelect(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private MaterialTextView tvContactAddress, tvLocation;
        private MaterialRadioButton rbAddressName;
        private AppCompatImageView ivDelete;
        private ConstraintLayout constraint_address;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvContactAddress = itemView.findViewById(R.id.tvContactAddress);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            rbAddressName = itemView.findViewById(R.id.rbAddressName);
            constraint_address=itemView.findViewById(R.id.constraint_address);
        }
    }
}
