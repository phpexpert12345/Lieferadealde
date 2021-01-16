package com.app.liferdeal.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.FoodItemSizeModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.activity.cart.CartActivity;
import com.app.liferdeal.ui.adapters.RestaurantFoodItemSizeAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;
import com.tbuonomo.viewpagerdotsindicator.DotsGradientDrawable;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddClickDetails extends AppCompatActivity implements View.OnClickListener, RestaurantFoodItemSizeAdapter.RestaurantFoodSizeItemInterface {
    @BindView(R.id.iv_restaurant_logo)
    ImageView ivRestaurantLogo;
    private int clickItemtId;
    private ProgressDialog progressDialog;
    private ProgressBar banner_progress;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private AppCompatButton btn_add_extra;
    private TextView btn_add_to_cart, txt_view_name, txt_currect_symbol, txt_view_cusine_name, txt_total, tvHead, tvSize, tvTotal;
    private int globItemId, globItemSizeId;
    private String globExtraAvail, CLICKITEMNAME, selectFoodSizeName, ClickPizzdesc, CLICKITEMPRICE, mainClickRestId, mainClickRestName, sizePizzaPrice, imgName;
    private RecyclerView food_item_size_list_rcv;
    private ImageView img_back;
    private String currencySymbol = "", extraTopping;
    private double totalPriceAdd = 0.0;
    private Context mContext;

    @BindView(R.id.tv_cart_item_count)
    TextView tv_cart_item_count;

    @BindView(R.id.cart_count_layout)
    RelativeLayout cart_count_layout;
    DotToCommaClass dotToCommaClass;

    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_item_size);
        ButterKnife.bind(this);
        mContext = AddClickDetails.this;
        if (App.retrieveLangFromGson(AddClickDetails.this) != null) {
            model = App.retrieveLangFromGson(AddClickDetails.this);
        }
        init();
    }

    @OnClick(R.id.cart_count_layout)
    public void cart_count_layoutClicked(View view) {
        Intent info = new Intent(AddClickDetails.this, CartActivity.class);
        startActivity(info);
    }

    private void init() {
        dotToCommaClass=new DotToCommaClass(getApplicationContext());
        if (AddExtraActivity.cart_Item_number != 0) {
            // rl_cart.setVisibility(View.VISIBLE);
            tv_cart_item_count.setText("" + AddExtraActivity.cart_Item_number);

        }
        prefsHelper = new PrefsHelper(this);
        food_item_size_list_rcv = findViewById(R.id.food_item_size_list_rcv);
        btn_add_extra = findViewById(R.id.btn_add_extra);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        txt_view_name = findViewById(R.id.txt_view_name);
        txt_currect_symbol = findViewById(R.id.txt_currect_symbol);
        txt_view_cusine_name = findViewById(R.id.txt_view_cusine_name);
        txt_total = findViewById(R.id.txt_total);
        img_back = findViewById(R.id.img_back);
        tvHead = findViewById(R.id.tvHead);
        tvSize = findViewById(R.id.tvSize);
        tvTotal = findViewById(R.id.tvTotal);
        banner_progress = findViewById(R.id.banner_progress);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AddClickDetails.this);
        food_item_size_list_rcv.setLayoutManager(mLayoutManager);
        food_item_size_list_rcv.setItemAnimator(new DefaultItemAnimator());

        tvHead.setText(model.getMenu());
        tvSize.setText(model.getSize());
        tvTotal.setText(model.getTotal());
        btn_add_extra.setText(model.getAddExtraTopping());
        btn_add_to_cart.setText(model.getAddToCart());

        clickItemtId = getIntent().getIntExtra("CLICKITEMID", 0);
        CLICKITEMNAME = getIntent().getStringExtra("CLICKITEMNAME");
        CLICKITEMPRICE = getIntent().getStringExtra("CLICKITEMPRICE");
        ClickPizzdesc = getIntent().getStringExtra("CLICKPIZZDESC");
        mainClickRestId = getIntent().getStringExtra("mainClickRestId");
        mainClickRestName = getIntent().getStringExtra("mainClickRestName");
        imgName = getIntent().getStringExtra("img");
        extraTopping = getIntent().getStringExtra("extraTopping");

        Glide.with(mContext).load(Uri.parse(imgName)).placeholder(R.drawable.ic_plate).into(ivRestaurantLogo);

        System.out.println("=== clickItemtId : " + clickItemtId + "CLICKITEMNAME" + CLICKITEMNAME + "CLICKITEMPRICE" + CLICKITEMPRICE + "mainClickRestId" + mainClickRestId + "mainClickRestName" + mainClickRestName);
        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();
        txt_view_name.setText(CLICKITEMNAME);
        txt_view_cusine_name.setText(ClickPizzdesc);
        txt_currect_symbol.setText("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        txt_total.setText(currencySymbol + " " + "" + dotToCommaClass.changeDot(CLICKITEMPRICE));
        btn_add_to_cart.setOnClickListener(this);
        btn_add_extra.setOnClickListener(this);
        img_back.setOnClickListener(this);
        getItemSizeData(clickItemtId);
    }

    private void getItemSizeData(int clickItemtId) {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<FoodItemSizeModel> observable = apiInterface.getFoodItemSize(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), clickItemtId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodItemSizeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodItemSizeModel searchResult) {
//                        showProgress();
                        setAdapterCategory(searchResult.getRestaurantMenItemsSize());
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

    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        if(model.getPlease_wait_text()!=null) {
            progressDialog.setMessage(model.getPlease_wait_text().trim() + "...");
        }
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    private Boolean[] selectedList;
    private RestaurantFoodItemSizeAdapter adapterCategory;

    private void setAdapterCategory(List<FoodItemSizeModel.RestaurantMenItemsSize> list) {
        selectedList = new Boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                selectedList[i] = true;
            } else {
                selectedList[i] = false;
            }
        }
        if (list.size() > 0) {
            globItemId = list.get(0).getFoodItemID();
            globItemSizeId = list.get(0).getFoodItemSizeID();
            selectFoodSizeName = list.get(0).getRestaurantPizzaItemName();
            sizePizzaPrice = list.get(0).getRestaurantPizzaItemPrice();
        }

        adapterCategory = new RestaurantFoodItemSizeAdapter(AddClickDetails.this, list, AddClickDetails.this, selectedList);
        food_item_size_list_rcv.setAdapter(adapterCategory);
//        hideProgress();
        if (list.size() > 0)
            globExtraAvail = list.get(0).getExtraavailable();

        if (extraTopping.equalsIgnoreCase("yes")) {
            btn_add_extra.setText(model.getAddExtraTopping());
        } else {
            btn_add_extra.setText(model.getAddToCart());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_extra:
                if (btn_add_extra.getText().equals(model.getAddExtraTopping())) {
                    Intent i = new Intent(AddClickDetails.this, AddExtraActivity.class);
                    i.putExtra("ITEMID", globItemId);
                    i.putExtra("FOODITEMSIZEID", globItemSizeId);
                    i.putExtra("selectFoodItemName", selectFoodSizeName);
                    i.putExtra("sizePizzaPrice", sizePizzaPrice);
                    i.putExtra("mainClickRestId", mainClickRestId);
                    i.putExtra("mainClickRestName", mainClickRestName);
                    i.putExtra("SUBCATCLICKITEMID", clickItemtId);
                    i.putExtra("SUBCATCLICKITEMNAME", CLICKITEMNAME);
                    i.putExtra("SUBCATCLICKITEMPRICE", sizePizzaPrice);
                    i.putExtra("SUBCATCLICKITEMDesc", ClickPizzdesc);
                    i.putExtra("img", imgName);
                    i.putExtra("TotalPriceWithPizzaItemAndSize", "" + totalPriceAdd);
                    startActivity(i);
                } else {
                    addToCartCalled();
                }
                break;
//            case R.id.btn_add_to_cart:
//                break;

            case R.id.img_back:
                finish();
                break;

            default:
                break;
        }
    }

    Database database = new Database(AddClickDetails.this);

    public void addToCartCalled() {
        double total = 0.0;
        int extaId;
        int qunt = 0;
        double price1 = 0.0;

        double sizePizzaPricee = 0.0;
        double restDetailsItemPricee = 0.0;
        if (!sizePizzaPrice.equalsIgnoreCase("")) {
            sizePizzaPricee = Double.parseDouble(sizePizzaPrice);
        }
        double price = 0.0;
        if (!CLICKITEMPRICE.equalsIgnoreCase("")) {
            restDetailsItemPricee = Double.parseDouble(CLICKITEMPRICE);
        }
       /* if (extra_item_list_price.size() != 0) {

            for (int i = 0; i < extra_item_list_price.size(); i++) {
                total = total + Double.parseDouble(extra_item_list_price.get(i));
            }
            //  total = total + restDetailsItemPricee;
            total = total + Double.parseDouble(TotalPriceWithPizzaItemAndSize);
        }*/

        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM item_table where item_id='" + clickItemtId + "'", null);
        database.InsertItem(String.valueOf(clickItemtId), CLICKITEMNAME,String.valueOf(globItemSizeId), selectFoodSizeName, "0", "0", Double.parseDouble(sizePizzaPrice), 1,ClickPizzdesc);
        AddExtraActivity.cart_Item_number = AddExtraActivity.cart_Item_number + 1;
        RestaurantDetails.tvCartItemCount.setText("" + AddExtraActivity.cart_Item_number);
        Intent i = new Intent(this, RestaurantDetails.class);
        i.putExtra("SIZEITEMID", globItemSizeId);
        //i.putExtra("tempExtraItemidWithSizeIdd", tempExtraItemidWithSizeIdd);
        //i.putExtra("", ext);
        startActivity(i);

        database.close();
    }

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    public void getrvcheckeddata(int position, int itemId, int itemSizeId, String extraAvail, String selectFoodItemName, String sizePizzaPricee) {
        System.out.println("==== checked in clickdetails : " + itemId + " " + "sizeid : " + itemSizeId + " " + "extraavail : " + extraAvail + " " + "fooditemname" + selectFoodItemName + " sizePizzaPrice " + sizePizzaPricee);

        globItemId = itemId;
        globItemSizeId = itemSizeId;
        globExtraAvail = extraAvail;
        selectFoodSizeName = selectFoodItemName;
        sizePizzaPrice = sizePizzaPricee;

        totalPriceAdd = /*Double.parseDouble(CLICKITEMPRICE) +*/ Double.parseDouble(sizePizzaPrice);
        System.out.println("==== totalprice Add : " + totalPriceAdd);

        txt_total.setText(currencySymbol + " " + "" + "" + String.format("%.2f", totalPriceAdd));
        if (extraAvail.equalsIgnoreCase("yes")) {
            btn_add_extra.setText(model.getAddExtraTopping());
//            btn_add_extra.setVisibility(View.VISIBLE);
//            btn_add_to_cart.setVisibility(View.GONE);
        } else {
            btn_add_extra.setText(model.getAddToCart());
//            btn_add_to_cart.setVisibility(View.VISIBLE);
//            btn_add_extra.setVisibility(View.GONE);
        }
        for (int i = 0; i < selectedList.length; i++) {
            if (i == position) {
                selectedList[i] = true;
            } else {
                selectedList[i] = false;
            }
        }

        adapterCategory.notifyDataSetChanged();
    }
}
