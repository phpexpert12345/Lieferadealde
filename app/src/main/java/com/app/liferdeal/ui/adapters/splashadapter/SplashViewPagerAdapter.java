package com.app.liferdeal.ui.adapters.splashadapter;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;


import com.app.liferdeal.R;
import com.app.liferdeal.model.splash.SlideImageModel;
import com.app.liferdeal.model.splash.SplashModel;
import com.app.liferdeal.ui.activity.login.SignUpActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SplashViewPagerAdapter extends PagerAdapter {

    private List<SplashModel.SplashBannersList> mImages;
    private LayoutInflater inflater;
    private Context mContext;

     public SplashViewPagerAdapter(Context context, List<SplashModel.SplashBannersList> images) {
         this.mContext = context;
         this.mImages=images;
         inflater = LayoutInflater.from(context);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slideimagelayout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        if (mImages.get(position).getSplashBannerImg()!=null)
        {
            Glide.with(mContext).load(Uri.parse(mImages.get(position).getSplashBannerImg())).into(imageView);
        }



      //  imageView.setImageResource(mImages.get(position).getImage_drawable());


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
