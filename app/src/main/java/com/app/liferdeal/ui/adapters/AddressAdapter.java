package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.AddressModel;
import com.app.liferdeal.util.PrefsHelper;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Holder> {
    private List<AddressModel.Deliveryaddress> list;
    private AddressDelete addressDelete;
    private Context mContext;
    private PrefsHelper prefsHelper;

    public AddressAdapter(Context context, List<AddressModel.Deliveryaddress> list) {
        this.mContext = context;
        this.list = list;
        prefsHelper = new PrefsHelper(mContext);
    }

    public void callbackAddress(AddressDelete addressDelete) {
        this.addressDelete = addressDelete;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_layout_address, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.tv_name.setText(list.get(position).getAddressTitle());
        holder.tv_mobile.setText(list.get(position).getCompanyStreetNo() + " " + list.get(position).getCustomerFlatName()
                + " " + list.get(position).getCompanyStreet() + " " + list.get(position).getCityName() + " " + list.get(position).getZipcode());
        holder.tvNo.setText("Mobile num: " + list.get(position).getUserPhone());

        holder.img_edit.setOnClickListener(view -> {
            addressDelete.deleteAddress(list.get(position), "delete");
//                PopupMenu popup = new PopupMenu(mContext, holder.img_edit);
//                popup.inflate(R.menu.options_menu);
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
////                            case R.id.menu1:
////                                addressDelete.deleteAddress(list.get(position), "edit");
////                                return true;
//                            case R.id.menu2:
//                                return true;
//                            default:
//                                return false;
//                        }
//                    }
//                });
//
//                popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_mobile, tvNo;
        private ImageView img_edit;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tvNo = itemView.findViewById(R.id.tvNo);
            img_edit = itemView.findViewById(R.id.img_edit);
        }
    }

    public interface AddressDelete {
        void deleteAddress(AddressModel.Deliveryaddress address, String title);
    }
}
