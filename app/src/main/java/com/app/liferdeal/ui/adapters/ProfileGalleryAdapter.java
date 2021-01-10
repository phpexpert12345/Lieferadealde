package com.app.liferdeal.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.GalleryPhoto;
import com.app.liferdeal.model.restaurant.RestaurantGalleryModel;
import com.app.liferdeal.ui.activity.restaurant.RestaurantPhotoGallery;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment1;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment3;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ProfileGalleryAdapter extends RecyclerView.Adapter<ProfileGalleryAdapter.MyViewHolder> {
    //  private List<RestaurantGalleryModel.FoodGalleryList> profileGalleryModelList;
    private List<GalleryPhoto> profileGalleryModelList;
    private List<GalleryPhoto> galleryPhotoList;
    private Context mContext;
    private TabFragment3 mProfileGallery;

    // public ProfileGalleryAdapter(Context context, List<RestaurantGalleryModel.FoodGalleryList> profileGalleryModelList) {
    public ProfileGalleryAdapter(Context context, List<GalleryPhoto> profileGalleryModelList, TabFragment3 tabFragment1) {
        this.profileGalleryModelList = profileGalleryModelList;
        this.mContext = context;
        this.mProfileGallery = tabFragment1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_gallery_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // holder.txt_text.setText(profileGalleryModelList.get(position).getTabName());
        //galleryPhotoList = profileGalleryModelList.get(position).getGalleryPhoto();
        String profileGalleryImage = profileGalleryModelList.get(position).getFoodPhoto();
        Glide.with(mContext).load(Uri.parse(profileGalleryImage)).apply(new RequestOptions().placeholder(R.drawable.defpizzaimg)).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            try {
                // Toast.makeText(context, "photo click", Toast.LENGTH_SHORT).show();
                //ProfileGallery.getImageList(context, profileGalleryModelList, position);
                mProfileGallery.ShowSelectPackagePopup(profileGalleryModelList.get(position).getFoodPhoto());
            } catch (Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (profileGalleryModelList == null) {
            profileGalleryModelList = new ArrayList<>();
        }
        return profileGalleryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        private TextView txt_text;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.imageView);
            txt_text = view.findViewById(R.id.txt_text);
        }
    }
}
