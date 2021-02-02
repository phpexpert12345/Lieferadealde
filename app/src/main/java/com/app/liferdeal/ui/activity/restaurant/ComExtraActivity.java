package com.app.liferdeal.ui.activity.restaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.FoodExtraModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.adapters.RestaurantFoodItemExtraAdapter;
import com.app.liferdeal.ui.interfaces.RestaurantFoodItemExtraAdapterInterface;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ComExtraActivity extends AppCompatActivity implements RestaurantFoodItemExtraAdapterInterface {
    @BindView(R.id.txt_view_name)
    TextView txt_view_name;
    @BindView(R.id.txt_view_cusine_name)
    TextView txt_view_cusine_name;
    @BindView(R.id.tvExtraTopping)
    TextView tvExtraTopping;
    @BindView(R.id.food_item_extra_list_rcv)
    RecyclerView food_item_extra_list_rcv;
    @BindView(R.id.banner_progress)
    ProgressBar banner_progress;
    @BindView(R.id.txt_add_extra_total)
    TextView txt_add_extra_total;
    DotToCommaClass dotToCommaClass;
    @BindView(R.id.txt_no_data)
    TextView txt_no_data;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.btn_add_to_cart)
    TextView btn_add_to_cart;
    @BindView(R.id.img_back)
    ImageView img_back;
    private LanguageResponse model = new LanguageResponse();
    int item_id;
    int size_id=0;
    String name="";
    String desc="";
    String price="";
    String top_allowed="";
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private String currencySymbol = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_item_exta);
        ButterKnife.bind(this);
        item_id=getIntent().getIntExtra("item_id",0);
        size_id=getIntent().getIntExtra("size_id",0);
        name=getIntent().getStringExtra("name");
        desc=getIntent().getStringExtra("desc");
        price=getIntent().getStringExtra("price");

        txt_view_name.setText(name);
        txt_view_cusine_name.setText(desc);
        top_allowed=getIntent().getStringExtra("top_allowed");
        if (App.retrieveLangFromGson(this) != null) {
            model = App.retrieveLangFromGson(this);
        }
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        prefsHelper = new PrefsHelper(this);
        dotToCommaClass=new DotToCommaClass(getApplicationContext());
        tvExtraTopping.setText(model.getAddExtraTopping());
        tvTotal.setText(model.getTotal());
        btn_add_to_cart.setText(model.getAddToCart());
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
        txt_add_extra_total.setText(currencySymbol + " " + "" + dotToCommaClass.changeDot(String.format("%.2f", Double.parseDouble(price))));
        getItemExtraData();
    }
    private void getItemExtraData(){
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        String url="https://www.lieferadeal.de/WebAppAPI/phpexpert_food_items_extra_android.php?api_key="+prefsHelper.getPref(Constants.API_KEY)+"&lang_code="+prefsHelper.getPref(Constants.LNG_CODE)+"&ItemID="+item_id+"&FoodItemSizeID="+size_id;
        Log.i("url",url);
        Observable<FoodExtraModel> observable = apiInterface.getFoodItemExtra(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), item_id, size_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodExtraModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodExtraModel searchResult) {
//                        showProgress();
                        if(searchResult.getMenuItemExtraGroup()!=null){
                            setAdapterCategory(searchResult.getMenuItemExtraGroup());
//                            try {
//                                if (searchResult.getMenuItemExtraGroup() != null) {
//                                    if (searchResult.getMenuItemExtraGroup().size() > 0) {
//                                        if (searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed() != null &&
//                                                !searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed().equalsIgnoreCase("")) {
//                                            freeTopping = Integer.parseInt(searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed());
//                                            tvChooseTopping.setVisibility(View.VISIBLE);
//                                            tvChooseTopping.setText(model.getChooseAny5ToppingFree().replace("$", searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed()));
//                                        } else {
//
//                                            tvChooseTopping.setVisibility(View.GONE);
//                                        }
//                                    }
//
//                                } else {
//                                    tvChooseTopping.setVisibility(View.GONE);
//                                }
//
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                        }
                        else {
                            if(searchResult.getError().equalsIgnoreCase("1")){
                                tvExtraTopping.setVisibility(View.GONE);
                                txt_no_data.setVisibility(View.VISIBLE);
                                txt_no_data.setText(searchResult.getError_msg());
                                tvTotal.setVisibility(View.GONE);
                                txt_add_extra_total.setVisibility(View.GONE);
                                btn_add_to_cart.setVisibility(View.GONE);
//                            Toast.makeText(getApplicationContext(), searchResult.getError_msg(), Toast.LENGTH_LONG).show();

                            }
                        }


                        banner_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });
    }
    private void setAdapterCategory(List<FoodExtraModel.MenuItemExtraGroup> list) {
        try {
            if (list != null && list.size() > 0) {
                tvExtraTopping.setVisibility(View.VISIBLE);
                txt_no_data.setVisibility(View.GONE);
                tvTotal.setVisibility(View.VISIBLE);
                txt_add_extra_total.setVisibility(View.VISIBLE);
                btn_add_to_cart.setVisibility(View.VISIBLE);
                RestaurantFoodItemExtraAdapter adapterCategory = new RestaurantFoodItemExtraAdapter(this, list, list.get(0).getSubExtraItemsRecord(), this, item_id, size_id);
                food_item_extra_list_rcv.setAdapter(adapterCategory);
            } else {
                Toast.makeText(getApplicationContext(), model.getDATAISNOTAVAILABLE(), Toast.LENGTH_LONG).show();
            }

//            hideProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getrvcheckeddata(ArrayList<String> tempextraId, ArrayList<Integer> extraId, ArrayList<String> extraName, ArrayList<String> extraPrice) {

    }

    @Override
    public void getCheckedItem(String item, String price, int id, List<FoodExtraModel.MenuItemExtraGroup.SubExtraItemsRecord> list) {

    }
}
