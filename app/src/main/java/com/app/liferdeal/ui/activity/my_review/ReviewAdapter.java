package com.app.liferdeal.ui.activity.my_review;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.liferdeal.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.observers.TestObserver;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.HomeProfile> {

    Context context;
    ArrayList<List<ReviewMainData>> list;


    public ReviewAdapter(Context context,ArrayList<List<ReviewMainData>> list){
        this.context=context;
        this.list=list;

    }
    @NonNull
    @Override
    public ReviewAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_review_user,parent,false);
        ReviewAdapter.HomeProfile homeProfile=new ReviewAdapter.HomeProfile(view);


        return homeProfile;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.HomeProfile holder, int position) {
        //Toast.makeText(context,list.get(0).size()+"",Toast.LENGTH_LONG).show();
         holder.tvOrderID.setText(list.get(0).get(position).getOrderNumber());
         holder.tvRatingText.setText(list.get(0).get(position).getCustomerReviewComment());
         holder.ratingBar.setRating(Float.parseFloat(list.get(0).get(position).getRestaurantReviewRating()));

    }

    @Override
    public int getItemCount() {
        return list.get(0).size();
    }
    public class HomeProfile extends RecyclerView.ViewHolder{
     TextView tvOrderID,tvRatingText;
     RatingBar ratingBar;

        public HomeProfile(@NonNull View itemView) {
            super(itemView);
            tvOrderID=itemView.findViewById(R.id.tvOrderID);
            tvRatingText=itemView.findViewById(R.id.tvRatingText);
            ratingBar=itemView.findViewById(R.id.ratingBar);

        }
    }


}
