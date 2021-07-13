package com.app.liferdeal.ui.activity.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.menuitems.ComboSection;
import com.app.liferdeal.model.restaurant.FoodExtraModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.adapters.RestaurantFoodItemExtraAdapter;
import com.app.liferdeal.ui.interfaces.RestaurantFoodItemExtraAdapterInterface;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
    int com_slot;
    private ArrayList<Integer> extra_item_list_id;
    private ArrayList<Integer> temp_extra_item_list_id;
    private ArrayList<String> extra_item_list_name;
    private ArrayList<String> extra_item_list_price;
    int free_allowed=0;
    int free_selection_allowed=0;
    String topp_price="";
    @BindView(R.id.txt_free_toppings)
    TextView  txt_free_toppings;



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
        com_slot=getIntent().getIntExtra("com_slot",0);
        free_allowed= getIntent().getIntExtra("free_allowed",0);
        free_selection_allowed=getIntent().getIntExtra("free_selection_allowed",0);

        txt_view_name.setText(name);
        txt_view_cusine_name.setText(desc);
        extra_item_list_id = new ArrayList<>();
        temp_extra_item_list_id = new ArrayList<>();
        extra_item_list_name = new ArrayList<>();
        extra_item_list_price = new ArrayList<>();
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
        if(free_allowed>0){
            txt_free_toppings.setVisibility(View.VISIBLE);
            String free=model.getCHOOSEANYFREETOPINGS();
            free=free.replace("$",String.valueOf(free_allowed));
            txt_free_toppings.setText(free);
        }
        else{
            txt_free_toppings.setVisibility(View.GONE);
        }
        prefsHelper = new PrefsHelper(this);
        dotToCommaClass=new DotToCommaClass(getApplicationContext());
        tvExtraTopping.setText(model.getAddExtraTopping());
        tvTotal.setText(model.getTotal());
        btn_add_to_cart.setText(model.getAddToCart());
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
        txt_add_extra_total.setText(currencySymbol + " " + "" + dotToCommaClass.changeDot(String.format("%.2f", Double.parseDouble(price))));
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

              topp_price=  String.format("%.2f", Double.parseDouble(topp_price));
                intent.putExtra("price", topp_price);
                StringBuilder stringBuilder=new StringBuilder();
                StringBuilder id=new StringBuilder();
                if(extra_item_list_name.size()>0){
                    for(int i=0;i<extra_item_list_name.size();i++){
                        stringBuilder.append(extra_item_list_name.get(i));
                        stringBuilder.append("_");
                        id.append(extra_item_list_id.get(i));
                        id.append("_");
                    }
                }
                if(stringBuilder.length()>0){
                    stringBuilder=stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("_"));
                    id=id.deleteCharAt(id.lastIndexOf("_"));
                }
                intent.putExtra("item",stringBuilder.toString());
                intent.putExtra("ids",id.toString());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        getItemExtraData();
    }
    private void getItemExtraData(){

        String url="https://www.lieferadeal.de/WebAppAPI/phpexpert_food_combo_items_extra.php?api_key="+prefsHelper.getPref(Constants.API_KEY)+"&lang_code="+prefsHelper.getPref(Constants.LNG_CODE)+"&ItemID="+item_id+"&FoodItemSizeID="+size_id+"&comboslot_id="+com_slot;
        Log.i("url",url);
        banner_progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    banner_progress.setVisibility(View.GONE);
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.has("Menu_ItemExtraGroup")){
                        JSONArray jsonArray=jsonObject.getJSONArray("Menu_ItemExtraGroup");
                        if(jsonArray.length()>0){
                            JSONArray Menu_ItemExtraGroup=jsonArray.getJSONArray(0);
                            Gson gson=new Gson();
                            Type listType=new TypeToken<List<FoodExtraModel.MenuItemExtraGroup>>(){}.getType();
                            setAdapterCategory(gson.fromJson(Menu_ItemExtraGroup.toString(),listType));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                banner_progress.setVisibility(View.GONE);
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);
//
//        Observable<FoodExtraModel> observable = apiInterface.getcomboFoodItemExtra(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE),com_slot, item_id, size_id);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<FoodExtraModel>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(FoodExtraModel searchResult) {
////                        showProgress();
//
//                        if(searchResult.getMenuItemExtraGroup()!=null){
//                            setAdapterCategory(searchResult.getMenuItemExtraGroup());
////                            try {
////                                if (searchResult.getMenuItemExtraGroup() != null) {
////                                    if (searchResult.getMenuItemExtraGroup().size() > 0) {
////                                        if (searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed() != null &&
////                                                !searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed().equalsIgnoreCase("")) {
////                                            freeTopping = Integer.parseInt(searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed());
////                                            tvChooseTopping.setVisibility(View.VISIBLE);
////                                            tvChooseTopping.setText(model.getChooseAny5ToppingFree().replace("$", searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed()));
////                                        } else {
////
////                                            tvChooseTopping.setVisibility(View.GONE);
////                                        }
////                                    }
////
////                                } else {
////                                    tvChooseTopping.setVisibility(View.GONE);
////                                }
////
////
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }
//                        }
//                        else {
//                            if(searchResult.getError().equalsIgnoreCase("1")){
//                                tvExtraTopping.setVisibility(View.GONE);
//                                txt_no_data.setVisibility(View.VISIBLE);
//                                txt_no_data.setText(searchResult.getError_msg());
//                                tvTotal.setVisibility(View.GONE);
//                                txt_add_extra_total.setVisibility(View.GONE);
//                                btn_add_to_cart.setVisibility(View.GONE);
////                            Toast.makeText(getApplicationContext(), searchResult.getError_msg(), Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//
//
//                        banner_progress.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        hideProgress();
//                        banner_progress.setVisibility(View.GONE);
//                        Log.d("TAG", "log...." + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        //   activity.mySharePreferences.setBundle("login");
//
//                    }
//                });
    }
    private void setAdapterCategory(List<FoodExtraModel.MenuItemExtraGroup> list) {
        try {
            if (list != null && list.size() > 0) {
                if(list.get(0).getError().equalsIgnoreCase("0")) {
                    tvExtraTopping.setVisibility(View.VISIBLE);
                    txt_no_data.setVisibility(View.GONE);
                    tvTotal.setVisibility(View.VISIBLE);
                    txt_add_extra_total.setVisibility(View.VISIBLE);
                    btn_add_to_cart.setVisibility(View.VISIBLE);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                    RestaurantFoodItemExtraAdapter adapterCategory = new RestaurantFoodItemExtraAdapter(this, list, list.get(0).getSubExtraItemsRecord(), this, item_id, size_id);
                    food_item_extra_list_rcv.setAdapter(adapterCategory);
                    food_item_extra_list_rcv.setLayoutManager(mLayoutManager);
                    food_item_extra_list_rcv.setItemAnimator(new DefaultItemAnimator());
                }
                else{
                    tvExtraTopping.setVisibility(View.GONE);
                                txt_no_data.setVisibility(View.VISIBLE);
                                txt_no_data.setText(list.get(0).getErrorMsg());
                                tvTotal.setVisibility(View.GONE);
                                txt_add_extra_total.setVisibility(View.GONE);
                                btn_add_to_cart.setVisibility(View.GONE);
                }
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
        extra_item_list_id = extraId;
        extra_item_list_name = extraName;
        extra_item_list_price = extraPrice;
        double total = Double.parseDouble(price);
        double top_price=0.0;
        if(free_allowed>0){
            for (int i = free_allowed; i < extra_item_list_price.size(); i++) {
                top_price = top_price + Double.parseDouble(extra_item_list_price.get(i));
                total = total + Double.parseDouble(extra_item_list_price.get(i));
            }

        }
        else {
            for (int i = 0; i < extra_item_list_price.size(); i++) {
                top_price = top_price + Double.parseDouble(extra_item_list_price.get(i));
                total = total + Double.parseDouble(extra_item_list_price.get(i));
            }
        }
        topp_price=String.valueOf(total);


        txt_add_extra_total.setText(currencySymbol + " " + "" + dotToCommaClass.changeDot(String.format("%.2f", total)));
    }

    @Override
    public void getCheckedItem(String item, String price_top, int id, List<FoodExtraModel.MenuItemExtraGroup.SubExtraItemsRecord> list) {
        Log.i("url",item+" "+price+" "+id+" "+list.size());
        if(extra_item_list_name.contains(item)){
            extra_item_list_name.remove(item);
            extra_item_list_name.add(item);
            extra_item_list_price.remove(price_top);
            extra_item_list_price.add(price_top);
            int id_=extra_item_list_id.indexOf(id);
            extra_item_list_id.remove(id_);
            extra_item_list_id.add(id);
        }
        else {
            int added=-1;
            for(int i=0;i< list.size();i++){
                for(int j=0;j<extra_item_list_name.size();j++){
                    if(list.get(i).getFoodAddonsName().equalsIgnoreCase(extra_item_list_name.get(j))){
                        added=j;
                        break;
                    }
                }

            }
            if(added>=0){
                extra_item_list_name.remove(added);
                extra_item_list_price.remove(added);
                extra_item_list_id.remove(added);

            }
            extra_item_list_name.add(item);
            extra_item_list_price.add(price_top);
            extra_item_list_id.add(id);
        }
        double total = Double.parseDouble(price);
        double top_price=0.0;
        for (int i = 0; i < extra_item_list_price.size(); i++) {
            top_price=total+Double.parseDouble(extra_item_list_price.get(i));
            total = total + Double.parseDouble(extra_item_list_price.get(i));
        }
        topp_price=String.valueOf(top_price);
//        total_price=String.valueOf(total);
        txt_add_extra_total.setText(currencySymbol + " " + "" + dotToCommaClass.changeDot(String.format("%.2f", total)));
    }
}
