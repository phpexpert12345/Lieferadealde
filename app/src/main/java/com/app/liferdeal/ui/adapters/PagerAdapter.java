package com.app.liferdeal.ui.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.liferdeal.model.restaurant.GalleryPhoto;
import com.app.liferdeal.model.restaurant.RestaurantGalleryModel;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment1;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment2;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment3;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private List<RestaurantGalleryModel.FoodGalleryList> mlist;
    private Context mContext;
    private List<GalleryPhoto> mListParty;
    private List<GalleryPhoto> mListPhoto;
    public PagerAdapter(FragmentManager fm, int NumOfTabs, List<RestaurantGalleryModel.FoodGalleryList> list,  List<GalleryPhoto> listParty, List<GalleryPhoto> listPhoto, Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.mlist = list;
        this.mContext = context;
        this.mListParty = listParty;
        this.mListPhoto = listPhoto;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Gson gson = new Gson();
                Bundle bundle = new Bundle();
                bundle.putString("MListdata", gson.toJson(mlist));
                bundle.putString("mListPartyData", gson.toJson(mListParty));
                TabFragment3 tab1 = new TabFragment3();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                Gson gsonn = new Gson();
                Bundle bundlee = new Bundle();
                bundlee.putString("MListdata", gsonn.toJson(mlist));
                bundlee.putString("mListPhotoData", gsonn.toJson(mListPhoto));
                TabFragment2 tab2 = new TabFragment2();
                tab2.setArguments(bundlee);
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
