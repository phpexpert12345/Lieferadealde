package com.app.liferdeal.ui.activity.restaurant;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.GalleryPhoto;
import com.app.liferdeal.model.restaurant.RestaurantGalleryModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.ui.adapters.PhotoProfileGalleryAdapter;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment1;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class TabFragment0 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private PrefsHelper prefsHelper;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private RecyclerView rest_food_gallery;
    private ProgressBar banner_progress;
    private String clickRestId = "";
    private TextView txt_view_for_no_data;
    private List<RestaurantGalleryModel.FoodGalleryList> mlist;
    private List<GalleryPhoto> mListParty;
    private List<GalleryPhoto> mListPhoto;
    private LanguageResponse model = new LanguageResponse();

    private String title;
    private int page;
    private static List<GalleryPhoto> photoData1;
    private static int position1;
    // newInstance constructor for creating fragment with arguments
    public static TabFragment0 newInstance(List<GalleryPhoto> photoData, int position) {
        TabFragment0 fragmentFirst = new TabFragment0();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);
        photoData1 = photoData;
        position1 = position;
        return fragmentFirst;
    }

    public TabFragment0(List<GalleryPhoto> photoData) {

        photoData1=new ArrayList<>();
        photoData1.clear();
        photoData1 = photoData;
        //position1 = position;
    }

    public TabFragment0() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_frag_one, container, false);
        if (App.retrieveLangFromGson(getActivity()) != null) {
            model = App.retrieveLangFromGson(getActivity());
        }
        init(view);
        return view;
    }



    private void init(View v) {
        //Log.e("DATACHECK=", photoData1.get(0).getFoodPhoto());
        prefsHelper = new PrefsHelper(getActivity());
        selectPackageDialog = new Dialog(getActivity());
        rest_food_gallery = v.findViewById(R.id.rest_food_gallery);
        banner_progress = v.findViewById(R.id.banner_progress);
        txt_view_for_no_data = v.findViewById(R.id.txt_view_for_no_data);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rest_food_gallery.setLayoutManager(mLayoutManager);
        rest_food_gallery.setItemAnimator(new DefaultItemAnimator());
        rest_food_gallery.setHasFixedSize(true);
        // clickRestId = getIntent().getStringExtra("clickRestId");
        System.out.println("==== clickRestId in rest gallery activity : " + clickRestId);
        Gson gson = new Gson();

       /* String gson1 = getArguments().getString("MListdata");
        Type type = new TypeToken<List<RestaurantGalleryModel.FoodGalleryList>>() {
        }.getType();

        mlist = gson.fromJson(gson1, type);

        //////////////////////////////////////////////

        String gsonparty = getArguments().getString("mListPartyData");
        Type typeparty = new TypeToken<List<GalleryPhoto>>() {
        }.getType();

        mListParty = gson.fromJson(gsonparty, typeparty);
        if (mListParty.size() > 0) {
            banner_progress.setVisibility(View.VISIBLE);
            setAdapterPhotoGallery();
        } else {
            txt_view_for_no_data.setText("Sorry! No image available");
            txt_view_for_no_data.setVisibility(View.VISIBLE);
        }*/
        if (photoData1.size() > 0) {
            if (photoData1.get(0).getError().equalsIgnoreCase("1")) {
                txt_view_for_no_data.setText(model.getNOPICTUREUPLOADEDYET());
                txt_view_for_no_data.setVisibility(View.VISIBLE);
            } else {
                banner_progress.setVisibility(View.VISIBLE);
                setAdapterPhotoGallery();
                txt_view_for_no_data.setVisibility(View.GONE);
            }
        } else {
            txt_view_for_no_data.setText(model.getNOPICTUREUPLOADEDYET());
            txt_view_for_no_data.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapterPhotoGallery() {
        PhotoProfileGalleryAdapter adapterCategory = new PhotoProfileGalleryAdapter(getActivity(), photoData1, TabFragment0.this);
        rest_food_gallery.setAdapter(adapterCategory);
        banner_progress.setVisibility(View.GONE);
        // adapterCategory.notifyDataSetChanged();
        // hideProgress();
    }

    private Dialog selectPackageDialog;

    public void ShowSelectPackagePopup(String imageUrl) {
        selectPackageDialog.setContentView(R.layout.profilefullviewpopup);
        ImageView image = selectPackageDialog.findViewById(R.id.image);
        Button crossimagepopup = selectPackageDialog.findViewById(R.id.crossimagepopup);
        WindowManager.LayoutParams home = new WindowManager.LayoutParams();
        Window window_home = selectPackageDialog.getWindow();
        if (window_home != null) {
            home.copyFrom(window_home.getAttributes());
            home.width = WindowManager.LayoutParams.MATCH_PARENT;
            home.height = WindowManager.LayoutParams.MATCH_PARENT;
            window_home.setAttributes(home);
            selectPackageDialog.setCancelable(true);
            selectPackageDialog.getWindow().getAttributes().windowAnimations = R.style.DialogeAnimation;
            selectPackageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            selectPackageDialog.show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setWhiteNavigationBar(selectPackageDialog);
            }
        }

        crossimagepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPackageDialog.dismiss();
            }
        });
       /* ViewPager mPager = (ViewPager) selectPackageDialog.findViewById(R.id.viewpagerphoto);
        mPager.setAdapter(new ProfileViewGalleryMediaAdapter(TabFragment1.this, getActivity(), mListParty));

        Button crossButton  = selectPackageDialog.findViewById(R.id.crossimagepopup);
        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPackageDialog.dismiss();
            }
        });

        Log.d("mPager",""+""+imageUrl );
        mPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<mListParty.size();i++){
                    if(String.valueOf(mListParty.get(i).getFoodPhoto()).equalsIgnoreCase(""+imageUrl)){
                        mPager.setCurrentItem(i);
                        Log.d("mPager",""+""+imageUrl );
                        break;
                    }
                }

            }
        }, 100);
*/

        Glide.with(getActivity()).load(imageUrl).apply(new RequestOptions().placeholder(R.drawable.user)).into(image);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setWhiteNavigationBar(@NonNull Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            GradientDrawable dimDrawable = new GradientDrawable();
            GradientDrawable navigationBarDrawable = new GradientDrawable();
            navigationBarDrawable.setShape(GradientDrawable.RECTANGLE);
            navigationBarDrawable.setColor(Color.WHITE);
            Drawable[] layers = {dimDrawable, navigationBarDrawable};
            LayerDrawable windowBackground = new LayerDrawable(layers);
            windowBackground.setLayerInsetTop(1, metrics.heightPixels);
            window.setBackgroundDrawable(windowBackground);
        }
    }
}