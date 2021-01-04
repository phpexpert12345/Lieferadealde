package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.TicketListDataModel;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.Currency;
import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.Holder> {
    private List<TicketListDataModel.ComplaintsHistory> listCategory;
    // private List<ModelSubCategory>listFilterSubCategory;
    private Context mContext;
    private String currencySymbol;
    private PrefsHelper prefsHelper;


    public TicketListAdapter(Context context, List<TicketListDataModel.ComplaintsHistory> listSubCategory) {
        this.mContext = context;
        this.listCategory = listSubCategory;
        prefsHelper = new PrefsHelper(mContext);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
        holder.txt_ticket_number.setText(listCategory.get(position).getComplaintId());
        holder.txt_order_number.setText(listCategory.get(position).getOrderIDNumber());
        holder.txt_order_status.setText(listCategory.get(position).getOrderIssueMessage());
    }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CardView card_subCategory;
        private TextView txt_ticket_number, txt_order_number, txt_order_status;
        private ImageView iv_restaurant_logo;
        private RatingBar ratingBar;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txt_ticket_number = itemView.findViewById(R.id.txt_ticket_number);
            txt_order_number = itemView.findViewById(R.id.txt_order_number);
            txt_order_status = itemView.findViewById(R.id.txt_order_status);

        }
    }

}
