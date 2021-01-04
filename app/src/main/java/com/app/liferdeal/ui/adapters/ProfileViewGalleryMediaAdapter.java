package com.app.liferdeal.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.liferdeal.R;
import com.app.liferdeal.model.restaurant.GalleryPhoto;
import com.app.liferdeal.model.restaurant.RestaurantGalleryModel;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment1;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewGalleryMediaAdapter extends FragmentPagerAdapter {

    private List<GalleryPhoto> profileGalleryModelList;
    private Context context;
    private LayoutInflater inflater;
    private TabFragment1 mProfileGallery;

    public ProfileViewGalleryMediaAdapter(FragmentManager fm, Context context, List<GalleryPhoto> imageModelArrayList) {

          super(fm);
        // super();
        this.context = context;
        this.profileGalleryModelList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return profileGalleryModelList.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Object instantiateItem(final ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.profileviewgallerymediaadapter, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        // imageView.setImageResource(Integer.parseInt(imageModelArrayList.get(position).getImage()));
        Glide.with(context).load(Uri.parse(profileGalleryModelList.get(position).getFoodPhoto())).apply(new RequestOptions().placeholder(R.drawable.user)).into(imageView);
        view.addView(imageLayout, 0);



        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
