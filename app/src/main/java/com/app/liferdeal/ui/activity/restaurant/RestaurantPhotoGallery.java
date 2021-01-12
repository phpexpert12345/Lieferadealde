package com.app.liferdeal.ui.activity.restaurant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.liferdeal.R;
import com.app.liferdeal.adapter.TabsAdapter;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.GalleryPhoto;
import com.app.liferdeal.model.restaurant.RestaurantGalleryModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.adapters.AccountViewPagerAdapter;
import com.app.liferdeal.ui.adapters.PagerAdapter;
import com.app.liferdeal.ui.fragment.restaurant.TabFragment1;
import com.app.liferdeal.ui.interfaces.ItemClickListener;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantPhotoGallery extends AppCompatActivity implements View.OnClickListener, ItemClickListener {
    @BindView(R.id.rvTabs)
    RecyclerView rvTabs;
    private PrefsHelper prefsHelper;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private RecyclerView rest_food_gallery;
    private ProgressBar banner_progress;
    private String clickRestId = "";
    TabLayout tabLayout;
    private ImageView img_back;
    private TextView txt_header_name;
    private List<GalleryPhoto> listParty;
    private List<GalleryPhoto> listPhoto;
    private Context mContext;
    private ViewPager viewPager;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_photo_gallery);
        ButterKnife.bind(this);
        mContext = RestaurantPhotoGallery.this;
        if (App.retrieveLangFromGson(RestaurantPhotoGallery.this) != null) {
            model = App.retrieveLangFromGson(RestaurantPhotoGallery.this);
        }
        init();
    }

    private void init() {
        prefsHelper = new PrefsHelper(this);
        rest_food_gallery = findViewById(R.id.rest_food_gallery);
        banner_progress = findViewById(R.id.banner_progress);
        img_back = findViewById(R.id.img_back);
        txt_header_name = findViewById(R.id.txt_header_name);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(RestaurantPhotoGallery.this, 2);
        rest_food_gallery.setLayoutManager(mLayoutManager);
        rest_food_gallery.setItemAnimator(new DefaultItemAnimator());
        rest_food_gallery.setHasFixedSize(true);
        clickRestId = getIntent().getStringExtra("clickRestId");
        System.out.println("==== clickRestId in rest gallery activity : " + clickRestId);
        tabLayout = findViewById(R.id.tab_layout);

        txt_header_name.setText(model.getGallery());
        img_back.setOnClickListener(this);
        getPhotoGalleryData();

    }

    private ArrayList<RestaurantGalleryModel.FoodGalleryList> photoData;

    private void getPhotoGalleryData() {

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<RestaurantGalleryModel> observable = apiInterface.getRestaurantFoodGallery(prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE), clickRestId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestaurantGalleryModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RestaurantGalleryModel searchResult) {
                        showProgress();
                        //  setAdapterCategory(searchResult.getRestaurantMencategory());
                        // setAdapterPhotoGallery(searchResult.getFoodGalleryList());
                        photoData = new ArrayList<>();
                        photoData.clear();
                        photoData.addAll(searchResult.getFoodGalleryList());
                        setTabPageData(searchResult.getFoodGalleryList());
                        banner_progress.setVisibility(View.GONE);
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });

    }

    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(model.getPlease_wait_text().trim()+"...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

  /*  private void setAdapterPhotoGallery(List<RestaurantGalleryModel.FoodGalleryList> list){
        ProfileGalleryAdapter adapterCategory = new ProfileGalleryAdapter(RestaurantPhotoGallery.this,list);
        rest_food_gallery.setAdapter(adapterCategory);
        // adapterCategory.notifyDataSetChanged();
        hideProgress();
    }*/

    private Boolean[] selectedList;
    private TabsAdapter tabsAdapter;

    private void setTabPageData(List<RestaurantGalleryModel.FoodGalleryList> list) {
        selectedList = new Boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                selectedList[i] = true;
            } else
                selectedList[i] = false;
        }

        rvTabs.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        tabsAdapter = new TabsAdapter(mContext, list, selectedList);
        rvTabs.setAdapter(tabsAdapter);
        //tabsAdapter.notifyDataSetChanged();
        tabsAdapter.setClickListener(this);

        for (int i = 0; i < list.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(list.get(i).getTabName()));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            if (list.get(i).getPhotoTabId() == 5) {
                listParty = list.get(i).getGalleryPhoto();
            }
            if (list.get(i).getPhotoTabId() == 4) {
                listPhoto = list.get(i).getGalleryPhoto();
            }
        }

      /*  tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/

        viewPager = (ViewPager) findViewById(R.id.pager);
        // final PagerAdapter adapter = new PagerAdapter (getSupportFragmentManager(),tabLayout.getTabCount());
       /* final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), list, listParty, listPhoto, RestaurantPhotoGallery.this);
        viewPager.setAdapter(adapter);*/
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        AccountViewPagerAdapter accountViewPagerAdapter=new AccountViewPagerAdapter(getSupportFragmentManager());
        //accountViewPagerAdapter.addFragment(new TabFragment1(photoData.get(0).getGalleryPhoto()),"");
        boolean flag=true;
        for (int i=0;i<photoData.size();i++){
            if (flag){
                accountViewPagerAdapter.addFragment(new com.app.liferdeal.ui.activity.restaurant.TabFragment0(photoData.get(i).getGalleryPhoto()),"");
                flag=false;
            }else{
                accountViewPagerAdapter.addFragment(new TabFragment1(photoData.get(i).getGalleryPhoto()),"");
                flag=true;
            }
        }

        viewPager.setAdapter(accountViewPagerAdapter);

        //accountViewPagerAdapter.addFragment(new TabFragment1(photoData.get(1).getGalleryPhoto()),"");
        //accountViewPagerAdapter.addFragment(new TabFragment1(photoData.get(2).getGalleryPhoto()),"");

       /* if (list.size() > 0) {
            viewPager.setCurrentItem(0);
        }*/

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                selectedList = new Boolean[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        selectedList[i] = true;
                    } else
                        selectedList[i] = false;
                }
//                tabsAdapter.notifyDataSetChanged();
                changeTopTab(selectedList, list);
            }


            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });


      /*  tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/
    }

    private void changeTopTab(Boolean[] selectedList, List<RestaurantGalleryModel.FoodGalleryList> list) {
        tabsAdapter = new TabsAdapter(mContext, list, selectedList);
        rvTabs.setAdapter(tabsAdapter);
        tabsAdapter.setClickListener(this);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }


        @NotNull
        @Override
        public Fragment getItem(int pos) {
            //Log.e("post=",pos+"");
            /*return TabFragment1.newInstance(photoData,pos);*/
            if (pos==0){
                return new TabFragment1(photoData.get(0).getGalleryPhoto());
            }else if (pos==1){
                return new TabFragment1(photoData.get(1).getGalleryPhoto());
            }else if (pos==2){
                return new TabFragment1(photoData.get(2).getGalleryPhoto());
            }else{
                return null;
            }

            //return   fragments.get(pos);


        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }


        @Override
        public int getCount() {
            //Log.e("Size=",photoData.size()+"");
            return 3;
        }
    }

   /* class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.ViewHolder> {
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener;
        private Context mContext;
        private List<RestaurantGalleryModel.FoodGalleryList> list;
        private Boolean[] selectedList;

        public TabsAdapter(Context context, List<RestaurantGalleryModel.FoodGalleryList> list, Boolean[] selectedList) {
            this.mContext = context;
            this.list = list;
            this.selectedList = selectedList;
            this.mInflater = LayoutInflater.from(context);
        }



        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_tab, parent, false);
            return new TabsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TabsAdapter.ViewHolder holder, int position) {
            holder.btnParty.setText(list.get(position).getTabName());
            if (selectedList[position]) {
                holder.btnParty.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_rounded));
                holder.btnParty.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            } else {
                holder.btnParty.setBackgroundDrawable(null);
                holder.btnParty.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            }




        }

        @Override
        public int getItemCount() {
            if (list.size() > 0)
                return list.size();
            else
                return 0;
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            AppCompatButton btnParty;

            ViewHolder(View itemView) {
                super(itemView);
                btnParty = itemView.findViewById(R.id.btnParty);
                btnParty.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null)
                    mClickListener.tabItemClick(view, getLayoutPosition(), true);

            }
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

    int pos = 0;

    @Override
    public void tabItemClick(View view, int adapterPosition, boolean b) {
        viewPager.setCurrentItem(adapterPosition);
        selectedList[adapterPosition] = true;
        selectedList[pos] = false;
        tabsAdapter.notifyDataSetChanged();
        pos = adapterPosition;
        // Toast.makeText(getApplicationContext(),"Tab clicked",Toast.LENGTH_LONG).show();
    }
}