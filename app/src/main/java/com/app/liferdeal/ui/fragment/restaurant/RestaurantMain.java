package com.app.liferdeal.ui.fragment.restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.CuisineList;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.CusineFilterModel;
import com.app.liferdeal.model.restaurant.RestaurantMainModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.activity.login.SignInActivity;
import com.app.liferdeal.ui.activity.profile.ProfileActivity;
import com.app.liferdeal.ui.activity.restaurant.CusineFilter;
import com.app.liferdeal.ui.adapters.CusineFiltersAdapter;
import com.app.liferdeal.ui.adapters.RestaurantMainAdapter;

import com.app.liferdeal.ui.fragment.LocationMapFragment;
import com.app.liferdeal.ui.interfaces.FilterInterface;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantMain extends Fragment implements View.OnClickListener, CusineFiltersAdapter.CusineFilterAdapterInterface {
    private static final String TAG = "RestaurantMainFrag";
    @BindView(R.id.rvFilterlist)
    RecyclerView rvFilterlist;
    @BindView(R.id.pbLoad)
    ProgressBar pbLoad;

    private RecyclerView rcv_rest_list;
    private TextView tvDataNotAvailable;
    private ProgressDialog progressDialog;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressBar banner_progress;
    private List<CuisineList> lists=new ArrayList<>();
    private ImageView home_icon, filter_cusine, img_location, img_profile;
    private String fullAddress = "", cityName = "", cityState = "", cityPostalcode = "", cityCountry = "", locationSearchAddress = "";
    private ArrayList<String> selected_cusines;
    private Observable<RestaurantMainModel> observable;
    private EditText edt_search;
    private List<RestaurantMainModel.SearchResult> mlist;
    RestaurantMainAdapter adapterCategory;
    private ArrayList<Integer> selected_cusines_id;
    private LanguageResponse model = new LanguageResponse();
    private Double currentLatitude, currentLongitude;
    private FilterInterface filterInterface;
    LinearLayout linear_no_data;
    Button btn_refresh;
    List<CuisineList> cuisineLists=new ArrayList<>();

    public RestaurantMain(Double currentLatitude, Double currentLongitude,FilterInterface filterInterface) {
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.filterInterface=filterInterface;

    }

    public RestaurantMain() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_main_layout, container, false);
        System.out.println("==== rest main is call");
        ButterKnife.bind(this, view);
        intializingView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (App.retrieveLangFromGson(getActivity()) != null) {
            model = App.retrieveLangFromGson(getActivity());
        }
        edt_search.setHint(model.getSearchForRestaurants());
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
        System.out.println("=== setretain is called");
    }

    private void intializingView(View v) {
        prefsHelper = new PrefsHelper(getActivity());
        selected_cusines = new ArrayList<>();
        selected_cusines_id = new ArrayList<>();
        mlist = new ArrayList<>();
        fullAddress = prefsHelper.getPref(Constants.SAVE_FULL_ADDRESS);
        cityName = prefsHelper.getPref(Constants.SAVE_CITY_NAME);
        cityState = prefsHelper.getPref(Constants.SAVE_STATE);
        cityPostalcode = prefsHelper.getPref(Constants.SAVE_POSTAL_CODE);
        cityCountry = prefsHelper.getPref(Constants.SAVE_COUNTRY);
        System.out.println("==== addresses rest main: " + fullAddress + cityName);
        rcv_rest_list = v.findViewById(R.id.rcv_rest_list);
        banner_progress = v.findViewById(R.id.banner_progress);
        home_icon = v.findViewById(R.id.home);
        tvDataNotAvailable = v.findViewById(R.id.tvDataNotAvailable);
        filter_cusine = v.findViewById(R.id.filter_cusine);
        img_location = v.findViewById(R.id.img_location);
        img_profile = v.findViewById(R.id.img_profile);
        edt_search = v.findViewById(R.id.edt_search);
        String applogo = prefsHelper.getPref(Constants.APP_TOP_LOGO_ICON);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rcv_rest_list.setLayoutManager(mLayoutManager);
        rcv_rest_list.setItemAnimator(new DefaultItemAnimator());
        banner_progress.setVisibility(View.VISIBLE);
        linear_no_data=v.findViewById(R.id.linear_no_data);
        btn_refresh=v.findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);
        //  Glide.with(getActivity()).load(Uri.parse(applogo)).into(home_icon);

        img_location.setOnClickListener(this);
        filter_cusine.setOnClickListener(this);
        home_icon.setOnClickListener(this);
        img_profile.setOnClickListener(this);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            selected_cusines = bundle.getStringArrayList("SELECTEDCUSINES");
            if(bundle.getParcelableArrayList("selected_filter")!=null){
                lists=bundle.getParcelableArrayList("selected_filter");
            }
            System.out.println("==== selected cusine in main restmain" + selected_cusines);

            locationSearchAddress = bundle.getString("locationSearchAddress");
            System.out.println("==== locationSearchAddress : " + locationSearchAddress);
            Gson gson1 = new Gson();
            filterData = gson1.toJson(selected_cusines, new TypeToken<ArrayList<String>>() {
            }.getType());

        }
        System.out.println("==== selected cusine in main restmain 1" + selected_cusines);

        getRestSearchInfo();
        getCusineFilterList();

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterByDistance(editable.toString());
            }
        });

       /* SharedPreferences prefs=getActivity().getSharedPreferences(Constants.PRICEPREFERENCE,
        Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet(Constants.FILTERDATA, null);
        List<String> sample=new ArrayList<String>(set);
        if (sample!=null){
            Log.e("SHARED=",sample.get(0));
        }*/
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

                       if(cuisineLists.size()>0){
                           cuisineLists.clear();
                       }
                       if(searchResult.getCuisineList()!=null) {
                           cuisineLists.addAll(searchResult.getCuisineList());
                           setFilterAdapterCategory();
                           banner_progress.setVisibility(View.GONE);
                       }
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

    private Boolean[] selectedList;
    private CusineFiltersAdapter adapterCategorys;
    private List<CuisineList> cuisineFilterList;

    private void setFilterAdapterCategory() {
        selectedList = new Boolean[cuisineLists.size()];
        cuisineFilterList = new ArrayList<>();
        cuisineFilterList.addAll(cuisineLists);
        if (cuisineLists.size() > 0) {
            if(lists.size()>0){
                for(int i=0;i<cuisineLists.size();i++){
                    for(int j=0;j<lists.size();j++){
                        if(lists.get(j).getId().equals(cuisineLists.get(i).getId())){
                            cuisineLists.get(i).setSelected(true);
                        }
                    }
                }
                if(selected_cusines.size()>0){
                    selected_cusines.clear();
                }
                for(int i=0;i<lists.size();i++){
                    selected_cusines.add(lists.get(i).getSeoUrlCall());
                }

                Gson gson1 = new Gson();
                filterData = gson1.toJson(selected_cusines, new TypeToken<ArrayList<String>>() {
                }.getType());


                getRestSearchInfo();

            }

            rvFilterlist.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
            adapterCategorys = new CusineFiltersAdapter(getActivity(), cuisineLists, selectedList);
            rvFilterlist.setAdapter(adapterCategorys);
            adapterCategorys.setClickListener(this);
        }
        hideProgress();
    }

    public static String getTAG() {
        return TAG;
    }

    private void setAdapterCategory(List<RestaurantMainModel.SearchResult> list) {
        mlist = list;
        adapterCategory = new RestaurantMainAdapter(getActivity(), list, prefsHelper,model);
        rcv_rest_list.setAdapter(adapterCategory);
        // hideProgress();
    }

    private void getRestSearchInfo() {
//        showProgress();
        pbLoad.setVisibility(View.VISIBLE);
        banner_progress.setVisibility(View.VISIBLE);
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        filterData = filterData.replace("[", "").replace("]", "").replace("\"", "");

        if (currentLatitude == null && currentLongitude == null) {
            currentLatitude = 0.0;
            currentLongitude = 0.0;
        }
        if (cityState == null) {
            cityState = "";
        }
        if (cityName == null) {
            cityName = "";
        }
        if (fullAddress == null) {
            fullAddress = "";
        }
        String url="https://www.lieferadeal.de/WebAppAPI/phpexpert_search.php?full_address="+CommonMethods.getStringDataInbase64(fullAddress)+"&current_city="+ CommonMethods.getStringDataInbase64(cityName)+"&current_postcode="+cityPostalcode+"&current_localty="+CommonMethods.getStringDataInbase64(cityState)+"&api_key="+prefsHelper.getPref(Constants.API_KEY)+"&lang_code="+prefsHelper.getPref(Constants.LNG_CODE)+"&current_lat="+String.valueOf(currentLatitude)+"&current_long="+String.valueOf(currentLatitude)+"&cuisine[]="+filterData;
            Log.i("reason",url);
        observable = apiInterface.getSearchRestData(CommonMethods.getStringDataInbase64(fullAddress), CommonMethods.getStringDataInbase64(cityName), cityPostalcode,
                CommonMethods.getStringDataInbase64(cityState), prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), String.valueOf(currentLatitude), String.valueOf(currentLongitude), filterData);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestaurantMainModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RestaurantMainModel searchResult) {
                        pbLoad.setVisibility(View.GONE);
                        if (searchResult.getSearchResult().size() > 0) {
                            setAdapterCategory(searchResult.getSearchResult());
                            rcv_rest_list.setVisibility(View.VISIBLE);
                            linear_no_data.setVisibility(View.GONE);
                        } else {
                            rcv_rest_list.setVisibility(View.GONE);
//                            setAdapterCategory(searchResult.getSearchResult());
                            linear_no_data.setVisibility(View.VISIBLE);
                            btn_refresh.setText(model.getBack_to_Restaurant_list());
                            if(searchResult.getSuccessMsg()!=null){
                                tvDataNotAvailable.setText(searchResult.getSuccessMsg());
                            }


                        }
                        banner_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        banner_progress.setVisibility(View.GONE);
                        pbLoad.setVisibility(View.GONE);
                        rcv_rest_list.setVisibility(View.GONE);
//                            setAdapterCategory(searchResult.getSearchResult());
                        linear_no_data.setVisibility(View.VISIBLE);
                        btn_refresh.setText(model.getBack_to_Restaurant_list());
//                        if(searchResult.getSuccessMsg()!=null){
//                            tvDataNotAvailable.setText(searchResult.getSuccessMsg());
//                        }
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        banner_progress.setVisibility(View.GONE);
                        pbLoad.setVisibility(View.GONE);

                        //   activity.mySharePreferences.setBundle("login");

                    }
                });

    }

    public void showProgress() {
        try {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(model.getPlease_wait_text().trim()+"...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_cusine:
                Intent i = new Intent(getActivity(), CusineFilter.class);
                startActivity(i);
                break;

            case R.id.home:
                Intent ii = new Intent(getActivity(), MainActivity.class);
                ii.putExtra("FROMWHERE", "fromhome");
                startActivity(ii);
                break;

            case R.id.img_location:
                Intent il = new Intent(getActivity(), LocationMapFragment.class);
                il.putExtra("FROMWHERE", "fromlocation");
                startActivity(il);
                break;

            case R.id.img_profile:
                System.out.println("==== check is login : " + prefsHelper.isLoggedIn());
                if (prefsHelper.isLoggedIn()) {
                    Intent ip = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(ip);
                } else {
                    Intent is = new Intent(getActivity(), SignInActivity.class);
                    startActivity(is);
                }

                break;
            case R.id.btn_refresh:
                if(filterData.length()>0)
                {
                    filterData="";
                }
                if(selected_cusines.size()>0){
                    selected_cusines.clear();
                }
                if(lists.size()>0){
                    lists.clear();
                }
                Gson gson1 = new Gson();
                filterData = gson1.toJson(selected_cusines, new TypeToken<ArrayList<String>>() {
                }.getType());
if(cuisineLists.size()>0){
    for(int j=0;j<cuisineLists.size();j++){
        cuisineLists.get(j).setSelected(false);
    }
    setFilterAdapterCategory();
}

                getRestSearchInfo();

            default:
                break;
        }
    }

    void filterByDistance(String text) {
        try {
            List<RestaurantMainModel.SearchResult> temp = new ArrayList();
            for (RestaurantMainModel.SearchResult d : mlist) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if (d.getRestaurantName().toLowerCase().contains(text)) {
                    temp.add(d);
                }
            }
            adapterCategory.updateList(temp, "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String filterData = "";

    @Override
    public void getClickData(List<CuisineList>cuisineLists,int pos) {
//        selected_cusines = extraName;
        selected_cusines.clear();
        lists.clear();
         CuisineList cuisineList=cuisineLists.get(pos);



        if(cuisineLists.get(pos).getSelected()){
            lists.add(cuisineLists.get(pos));
        }
        else{
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
        for(int i=0;i<lists.size();i++){
            selected_cusines.add(lists.get(i).getSeoUrlCall());
        }

        adapterCategorys.notifyDataSetChanged();
        if(filterData.length()>0)
        {
            filterData="";
        }
        Gson gson1 = new Gson();
        filterData = gson1.toJson(selected_cusines, new TypeToken<ArrayList<String>>() {
        }.getType());


        getRestSearchInfo();
    }
}
