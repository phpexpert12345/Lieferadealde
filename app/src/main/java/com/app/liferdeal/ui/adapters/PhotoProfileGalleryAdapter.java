package com.app.liferdeal.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.GalleryPhoto;
import com.app.liferdeal.ui.activity.restaurant.TabFragment0;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment1;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment3;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PhotoProfileGalleryAdapter extends RecyclerView.Adapter<PhotoProfileGalleryAdapter.MyViewHolder> {
    //  private List<RestaurantGalleryModel.FoodGalleryList> profileGalleryModelList;
    private List<GalleryPhoto> profileGalleryModelList;
    private List<GalleryPhoto> galleryPhotoList;
    private Context mContext;
    private TabFragment1 mProfileGallery;
    private TabFragment0 mProfileGallery0;

    // public ProfileGalleryAdapter(Context context, List<RestaurantGalleryModel.FoodGalleryList> profileGalleryModelList) {
    public PhotoProfileGalleryAdapter(Context context, List<GalleryPhoto> profileGalleryModelList, TabFragment1 tabFragment1) {
        this.profileGalleryModelList = profileGalleryModelList;
        this.mContext = context;
        this.mProfileGallery = tabFragment1;
    }
    public PhotoProfileGalleryAdapter(Context context, List<GalleryPhoto> profileGalleryModelList, TabFragment0 tabFragment0) {
        this.profileGalleryModelList = profileGalleryModelList;
        this.mContext = context;
        this.mProfileGallery0 = tabFragment0;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_gallery_row, parent, false);
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
