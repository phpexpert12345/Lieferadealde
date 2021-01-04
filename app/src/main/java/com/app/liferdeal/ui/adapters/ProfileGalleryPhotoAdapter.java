package com.app.liferdeal.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.GalleryPhoto;
import com.app.liferdeal.ui.activity.restaurant.RestaurantPhotoGallery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProfileGalleryPhotoAdapter extends RecyclerView.Adapter<ProfileGalleryPhotoAdapter.MyViewHolder> {


  //  private List<RestaurantGalleryModel.FoodGalleryList> profileGalleryModelList;
    private List<GalleryPhoto> profileGalleryModelList;
    private List<GalleryPhoto> galleryPhotoList;
    private Context mContext;
    private RestaurantPhotoGallery mProfileGallery;

   // public ProfileGalleryAdapter(Context context, List<RestaurantGalleryModel.FoodGalleryList> profileGalleryModelList) {
    public ProfileGalleryPhotoAdapter(Context context, List<GalleryPhoto> profileGalleryModelList) {
        this.profileGalleryModelList = profileGalleryModelList;
        this.mContext = context;
      //  this.mProfileGallery = profileGallery;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_gallery_photo_row, parent, false);

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
              //  mProfileGallery.ShowSelectPackagePopup(profileGallery.getImage());
            } catch (Exception e) {

            }
        });

    }

    @Override
    public int getItemCount() {

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
