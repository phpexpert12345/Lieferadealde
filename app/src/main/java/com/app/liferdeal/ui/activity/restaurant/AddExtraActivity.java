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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.FoodExtraModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.activity.cart.CartActivity;
import com.app.liferdeal.ui.adapters.RestaurantFoodItemExtraAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DotToCommaClass;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
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

public class AddExtraActivity extends AppCompatActivity implements View.OnClickListener, RestaurantFoodItemExtraAdapter.RestaurantFoodItemExtraAdapterInterface {
    @BindView(R.id.iv_restaurant_logo)
    ImageView ivRestaurantLogo;
    @BindView(R.id.tvChooseTopping)
    TextView tvChooseTopping;
    private PrefsHelper prefsHelper;
    private ProgressDialog progressDialog;
    private ProgressBar banner_progress;
    private ApiInterface apiInterface;
    private int itemId, itemSizeId, restDetailsItemId;
    private RecyclerView food_item_extra_list_rcv;
    private TextView btn_add_to_cart, txt_view_name, txt_add_extra_total, txt_view_cusine_name, txt_currect_symbol, tvExtra, tvExtraTopping, tvTotal;
    private ImageView img_back;
    Database database;
    private ArrayList<Integer> extra_item_list_id;
    private ArrayList<Integer> temp_extra_item_list_id;
    private ArrayList<String> extra_item_list_name;
    private ArrayList<String> extra_item_list_price;
    public static int cart_Item_number = 0;
    private String TotalPriceWithPizzaItemAndSize, SUBCATCLICKITEMDesc, restDealisItemId, restDetailsItemName, restDetailsItemPrice, sizePizzaPrice, selectFoodItemName, mainClickRestName, mainClickRestId;
    private String currencySymbol = "", imgName;
    private ArrayList<String> tempExtraItemidWithSizeIdd;
    private Context mContext;
    private int freeTopping = 0;
    private LanguageResponse model = new LanguageResponse();

    @BindView(R.id.tv_cart_item_count)
    TextView tv_cart_item_count;
    @BindView(R.id.cart_count_layout)
    RelativeLayout cart_count_layout;

    DotToCommaClass dotToCommaClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_item_exta);
        ButterKnife.bind(this);
        mContext = AddExtraActivity.this;
        if (App.retrieveLangFromGson(AddExtraActivity.this) != null) {
            model = App.retrieveLangFromGson(AddExtraActivity.this);
        }
        init();
    }

    @OnClick(R.id.cart_count_layout)
    public void cart_count_layoutClicked(View view) {
        Intent info = new Intent(AddExtraActivity.this, CartActivity.class);
        startActivity(info);
    }

    private void init() {
        dotToCommaClass=new DotToCommaClass(getApplicationContext());
        if (AddExtraActivity.cart_Item_number != 0) {
            // rl_cart.setVisibility(View.VISIBLE);
            tv_cart_item_count.setText("" + AddExtraActivity.cart_Item_number);
        }
        prefsHelper = new PrefsHelper(this);
        database = new Database(AddExtraActivity.this);
        txt_view_name = findViewById(R.id.txt_view_name);
        tvExtra = findViewById(R.id.tvExtra);
        txt_add_extra_total = findViewById(R.id.txt_add_extra_total);
        txt_view_cusine_name = findViewById(R.id.txt_view_cusine_name);
        txt_currect_symbol = findViewById(R.id.txt_currect_symbol);
        img_back = findViewById(R.id.img_back);
        tvExtraTopping = findViewById(R.id.tvExtraTopping);
        tvTotal = findViewById(R.id.tvTotal);
        banner_progress = findViewById(R.id.banner_progress);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        tempExtraItemidWithSizeIdd = new ArrayList<>();
        extra_item_list_id = new ArrayList<>();
        temp_extra_item_list_id = new ArrayList<>();
        extra_item_list_name = new ArrayList<>();
        extra_item_list_price = new ArrayList<>();

        tvExtra.setText(model.getExtraTopping());
        tvExtraTopping.setText(model.getAddExtraTopping());
        tvTotal.setText(model.getTotal());
        btn_add_to_cart.setText(model.getAddToCart());

        itemId = getIntent().getIntExtra("ITEMID", 0);
        itemSizeId = getIntent().getIntExtra("FOODITEMSIZEID", 0);
        sizePizzaPrice = getIntent().getStringExtra("sizePizzaPrice");
        selectFoodItemName = getIntent().getStringExtra("selectFoodItemName");
        mainClickRestName = getIntent().getStringExtra("mainClickRestName");
        mainClickRestId = getIntent().getStringExtra("mainClickRestId");
        restDetailsItemId = getIntent().getIntExtra("SUBCATCLICKITEMID", 0);
        restDetailsItemName = getIntent().getStringExtra("SUBCATCLICKITEMNAME");
        restDetailsItemPrice = getIntent().getStringExtra("SUBCATCLICKITEMPRICE");
        SUBCATCLICKITEMDesc = getIntent().getStringExtra("SUBCATCLICKITEMDesc");
        imgName = getIntent().getStringExtra("img");
        TotalPriceWithPizzaItemAndSize = getIntent().getStringExtra("TotalPriceWithPizzaItemAndSize");

        Glide.with(mContext).load(Uri.parse(imgName)).placeholder(R.drawable.ic_plate).into(ivRestaurantLogo);

        if (restDetailsItemId != 0) {
            restDealisItemId = String.valueOf(restDetailsItemId);
        }
        System.out.println("=== whole data : " + "itemId : " + itemId + "itemSizeId" + itemSizeId);
        System.out.println("=== whole data 1 : " + "sizePizzaPrice" + sizePizzaPrice + " selectFoodItemName " + selectFoodItemName + "mainClickRestName : " + mainClickRestName + "mainClickRestIDe : " + mainClickRestId);
        System.out.println("=== whole data 1 :" + "SUBCATCLICKITEMID" + restDealisItemId + " SUBCATCLICKITEMNAME : " + restDetailsItemName + "SUBCATCLICKITEMPRICE :" + restDetailsItemPrice);

        System.out.println("=== clickItemtId : " + itemId);
        food_item_extra_list_rcv = findViewById(R.id.food_item_extra_list_rcv);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AddExtraActivity.this);
        food_item_extra_list_rcv.setLayoutManager(mLayoutManager);
        food_item_extra_list_rcv.setItemAnimator(new DefaultItemAnimator());

        Currency hh = Currency.getInstance("" + prefsHelper.getPref(Constants.APP_CURRENCY));
        currencySymbol = hh.getSymbol();

        txt_view_name.setText(restDetailsItemName);
        txt_view_cusine_name.setText(SUBCATCLICKITEMDesc);
        txt_add_extra_total.setText(currencySymbol + " " + "" + dotToCommaClass.changeDot(String.format("%.2f", Double.parseDouble(restDetailsItemPrice))));
        getItemExtraData(itemId, itemSizeId);
        btn_add_to_cart.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    private void getItemExtraData(int clickItemtId, int itemSizeId) {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<FoodExtraModel> observable = apiInterface.getFoodItemExtra(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), clickItemtId, itemSizeId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodExtraModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodExtraModel searchResult) {
                        showProgress();
                        setAdapterCategory(searchResult.getMenuItemExtraGroup());
                        try {
                            if (searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed() != null &&
                                    !searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed().equalsIgnoreCase("")) {
                                freeTopping = Integer.parseInt(searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed());
                                tvChooseTopping.setVisibility(View.VISIBLE);
                                tvChooseTopping.setText(model.getChooseAny5ToppingFree().replace("$", searchResult.getMenuItemExtraGroup().get(0).getSubExtraItemsRecord().get(0).getFreeToppingSelectionAllowed()));
                            } else {
                                tvChooseTopping.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    private void setAdapterCategory(List<FoodExtraModel.MenuItemExtraGroup> list) {
        try {
            if (list != null && list.size() > 0) {
                RestaurantFoodItemExtraAdapter adapterCategory = new RestaurantFoodItemExtraAdapter(AddExtraActivity.this, list, list.get(0).getSubExtraItemsRecord(), AddExtraActivity.this, itemId, itemSizeId);
                food_item_extra_list_rcv.setAdapter(adapterCategory);
            } else {
                Toast.makeText(getApplicationContext(), model.getDATAISNOTAVAILABLE(), Toast.LENGTH_LONG).show();
            }

            hideProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.btn_add_to_cart:
                double total = 0.0;
                int extaId;
                int qunt = 0;
                double price1 = 0.0;

                double sizePizzaPricee = 0.0;
                double restDetailsItemPricee = 0.0;
                if (sizePizzaPrice != null && !sizePizzaPrice.equalsIgnoreCase("")) {
                    sizePizzaPricee = Double.parseDouble(sizePizzaPrice);
                }
                double price = 0.0;
                if (restDetailsItemPrice != null && !restDetailsItemPrice.equalsIgnoreCase("")) {
                    restDetailsItemPricee = Double.parseDouble(restDetailsItemPrice);
                }
                if (extra_item_list_price.size() > 0) {

                    for (int i = 0; i < extra_item_list_price.size(); i++) {
                        total = total + Double.parseDouble(extra_item_list_price.get(i));
                    }
                    //  total = total + restDetailsItemPricee;
                    total = total + Double.parseDouble(TotalPriceWithPizzaItemAndSize);
                }

                SQLiteDatabase db = database.getReadableDatabase();

                Cursor cursor;
                if (extra_item_list_id.size() > 0)
                    cursor = db.rawQuery("SELECT * FROM item_table where item_id='" + restDetailsItemId
                            + "' AND size_item_id = '" + itemSizeId + "' AND extra_item_id = '" + extra_item_list_id + "'", null);
                else
                    cursor = db.rawQuery("SELECT * FROM item_table where item_id='" + restDetailsItemId
                            + "' AND size_item_id = '" + itemSizeId + "'", null);
                if (cursor.moveToNext()) {
                    qunt = Integer.parseInt(cursor.getString(7)) + 1;
                    price = Double.parseDouble(restDetailsItemPrice) * qunt;
                    if (extra_item_list_id.size() > 0) {
                        qunt = Integer.parseInt(cursor.getString(7));
                        price1 = Double.parseDouble(cursor.getString(6));
                        price1 = price1 / qunt;
                        price1 = (double) Math.round(price1 * 100) / 100;
                        qunt = qunt + 1;
                        price1 = price1 * qunt;
                        database.update_item(String.valueOf(restDetailsItemId), qunt, price);
                        database.update_extra_quantity(getIntent().getStringExtra("itemid"), "" + extra_item_list_id, price1, qunt);
                    } else {
                        database.update_item(String.valueOf(restDetailsItemId), qunt, price);
                    }
                    cart_Item_number = cart_Item_number + 1;
                    RestaurantDetails.tvCartItemCount.setText("" + cart_Item_number);
                    Intent i = new Intent(this, RestaurantDetails.class);
                    i.putExtra("SIZEITEMID", itemSizeId);
                    i.putExtra("tempExtraItemidWithSizeIdd", txt_add_extra_total.getText().toString().trim());
                    //i.putExtra("", ext);
                    startActivity(i);
                } else {
                    if (extra_item_list_id.size() > 0)
                        database.InsertItem(String.valueOf(restDetailsItemId), restDetailsItemName, String.valueOf(itemSizeId), selectFoodItemName, "" + extra_item_list_id, "" + extra_item_list_name, Double.parseDouble(restDetailsItemPrice), 1,SUBCATCLICKITEMDesc);
                    else {
                        database.InsertItem(String.valueOf(restDetailsItemId), restDetailsItemName, String.valueOf(itemSizeId), selectFoodItemName, "0", "0", Double.parseDouble(restDetailsItemPrice), 1,SUBCATCLICKITEMDesc);

                    }
                    cart_Item_number = cart_Item_number + 1;
                    RestaurantDetails.tvCartItemCount.setText("" + cart_Item_number);
                    Intent i = new Intent(this, RestaurantDetails.class);
                    i.putExtra("SIZEITEMID", itemSizeId);
                    i.putExtra("tempExtraItemidWithSizeIdd", txt_add_extra_total.getText().toString().trim());
                    //i.putExtra("", ext);
                    startActivity(i);
                }
                database.close();
                break;

            default:
                break;
        }
    }

    private String extraItemidWithSizeId = "", tempExtraItemidWithSizeId = "";

    @Override
    public void getrvcheckeddata(ArrayList<String> temp_extraId, ArrayList<Integer> extraId, ArrayList<String> extraName, ArrayList<String> extraPrice) {
        tempExtraItemidWithSizeIdd = temp_extraId;
        extra_item_list_id = extraId;
        extra_item_list_name = extraName;
        extra_item_list_price = extraPrice;
        System.out.println("=== extra add inactivity: " + extra_item_list_id + extra_item_list_name + extra_item_list_price);

        System.out.println("===== extraItemidWithSizeId tempExtraItemidWithSizeIdd: " + tempExtraItemidWithSizeIdd);
        double total = Double.parseDouble(getIntent().getStringExtra("SUBCATCLICKITEMPRICE"));


        if (freeTopping > 0) {
            for (int i = freeTopping; i < extra_item_list_price.size(); i++) {
                total = total + Double.parseDouble(extra_item_list_price.get(i));
            }
        } else {
            for (int i = 0; i < extra_item_list_price.size(); i++) {
                total = total + Double.parseDouble(extra_item_list_price.get(i));
            }
        }


        if (freeTopping > 0 && extra_item_list_price.size() == freeTopping) {
            Toast.makeText(mContext, model.getYOURHAVECHOOSEDFREETOPPING().replace("$", "" + freeTopping), Toast.LENGTH_SHORT).show();
        }

//        total = total + Double.parseDouble(TotalPriceWithPizzaItemAndSize);
        System.out.println("=== total price after aff pizz and size price : " + total);
        restDetailsItemPrice = String.valueOf(total);
        txt_add_extra_total.setText(currencySymbol + " " + "" + String.format("%.2f", total));
    }
}
