package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.CuisineList;
import com.app.liferdeal.model.LanguageModel;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.CusineFilterModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.adapters.CusineFilterAdapter;
import com.app.liferdeal.ui.adapters.LanguageAdapter;
import com.app.liferdeal.ui.fragment.LocationMapFragment;
import com.app.liferdeal.ui.fragment.restaurant.RestaurantMain;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CusineFilter extends AppCompatActivity implements View.OnClickListener, CusineFilterAdapter.CusineFilterAdapterInterface {

    private ImageView iv_backr;
    private TextView tvHead, txt_view_name;
    private RecyclerView rcv_cusine_view;
    private PrefsHelper prefsHelper;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private ArrayList<String> selected_cusines;
    private ArrayList<Integer> selected_cusines_id;
    private AppCompatButton btnSubmit;
    private ProgressBar banner_progress;
    private LanguageResponse model = new LanguageResponse();
    Intent intent;
    List<CuisineList> lists=new ArrayList<>();
    List<CuisineList>all_list=new ArrayList<>();
    private SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusinefilter);

        if (App.retrieveLangFromGson(CusineFilter.this) != null) {
            model = App.retrieveLangFromGson(CusineFilter.this);
        }
        intent=getIntent();
        if(intent.hasExtra("selected_filter")){
            lists=intent.getParcelableArrayListExtra("selected_filter");

        }
        init();
    }

    private void init() {
        try {
            sharedPreferencesData=new SharedPreferencesData(getApplicationContext());
            prefsHelper = new PrefsHelper(this);
            rcv_cusine_view = findViewById(R.id.rcv_cusine_view);
            selected_cusines = new ArrayList<>();
            selected_cusines_id = new ArrayList<>();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            rcv_cusine_view.setLayoutManager(mLayoutManager);
            rcv_cusine_view.setItemAnimator(new DefaultItemAnimator());
            iv_backr = findViewById(R.id.img_back);
            btnSubmit = findViewById(R.id.btnSubmit);
            banner_progress = findViewById(R.id.banner_progress);
            tvHead = findViewById(R.id.tvHead);
            txt_view_name = findViewById(R.id.txt_view_name);

            tvHead.setText(model.getFilter());
            txt_view_name.setText(model.getSearchForACuisine());
            btnSubmit.setText(model.getSubmit());

            btnSubmit.setOnClickListener(this);
            iv_backr.setOnClickListener(this);
            banner_progress.setVisibility(View.VISIBLE);
            getCusineFilterList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btnSubmit:
                initiateRestFragment();
                break;
            default:
                break;
        }
    }

    private void initiateRestFragment() {
        Intent i = new Intent(CusineFilter.this, MainActivity.class);
        i.putExtra("FROMWHERE", "pagecusine");
        i.putExtra("SELECTEDCUSINE", selected_cusines);
        i.putParcelableArrayListExtra("selected_filter", (ArrayList<? extends Parcelable>) lists);
        startActivity(i);
        finish();
    }

    private void getCusineFilterList() {
        showProgress();
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<CusineFilterModel> observable = apiInterface.getCusineFilterList(prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CusineFilterModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CusineFilterModel searchResult) {

                        if(all_list.size()>0){
                            all_list.clear();
                        }
                        all_list=searchResult.getCuisineList();
                        setAdapterCategory();
                        banner_progress.setVisibility(View.GONE);
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
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void setAdapterCategory() {
        if(lists!=null) {
            if (lists.size() > 0) {
                for (int i = 0; i < lists.size(); i++) {
                    for (int j = 0; j < all_list.size(); j++) {
                        if (lists.get(i).getId().equals(all_list.get(j).getId())) {
                            all_list.get(j).setSelected(true);
                            break;

                        }
                    }
                }
            }
        }
        CusineFilterAdapter adapterCategory = new CusineFilterAdapter(this, all_list, this);
        rcv_cusine_view.setAdapter(adapterCategory);
        hideProgress();
    }

    @Override
    public void getClickData(List<CuisineList>listcategory, int pos) {
        lists.clear();
        if(listcategory.size()>0){
            if(listcategory.get(pos).getSelected()){
                lists.add(listcategory.get(pos));
            }
            else{
                CuisineList cuisineList=listcategory.get(pos);
                int index=-1;
                for(int i=0;i<lists.size();i++){
                    if(lists.get(i).getSeoUrlCall().equalsIgnoreCase(cuisineList.getSeoUrlCall())){
                        index=i;
                        break;

                    }
                }

                if(index>=0){
                    lists.remove(index);
                }

            }

        }

       /* Set<String> set = new HashSet<String>();
        set.addAll(extraName);

        SharedPreferences prefs=this.getSharedPreferences(Constants.PRICEPREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=prefs.edit();
        edit.putStringSet(Constants.FILTERDATA, set);
        edit.commit();*/


    }
}
