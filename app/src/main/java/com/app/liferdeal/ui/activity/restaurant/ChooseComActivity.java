package com.app.liferdeal.ui.activity.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.liferdeal.R;
import com.app.liferdeal.model.menuitems.ComItemList;
import com.app.liferdeal.model.menuitems.ComValue;
import com.app.liferdeal.model.menuitems.ComValueItem;
import com.app.liferdeal.model.menuitems.ComboList;
import com.app.liferdeal.model.menuitems.ComboSection;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.adapters.ComValueAdapter;
import com.app.liferdeal.ui.adapters.ComboSectionAdapter;
import com.app.liferdeal.ui.interfaces.SelectSec;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.DroidPrefs;
import com.app.liferdeal.util.PrefsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseComActivity extends AppCompatActivity {
    PrefsHelper prefsHelper;
    int com_id;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.txt_com_details)
    TextView txt_com_details;
    @BindView(R.id.txt_com_name)
    TextView txt_com_name;
    @BindView(R.id.recyler_combo)
    RecyclerView recyler_combo;
    @BindView(R.id.progress_com)
    ProgressBar progress_com;
    @BindView(R.id.recyler_sec)
    RecyclerView recyler_sec;
    @BindView(R.id.btn_add_to_cart)
    TextView btn_add_to_cart;
    @BindView(R.id.txt_com_desc)
    TextView txt_com_desc;
    String combo="";
    String desc="";
    String price="";
   int Free_allowed;
         int  Free_Topping_Selection_allowed;
    List<ComboSection>comboSections=new ArrayList<>();
    private Database database;
    StringBuilder com_tops=new StringBuilder();
    StringBuilder sec_item=new StringBuilder();
    StringBuilder sec_value=new StringBuilder();
    StringBuilder com_tops_id=new StringBuilder();
    StringBuilder item_ids=new StringBuilder();
    StringBuilder  comboslot_ids=new StringBuilder();
    StringBuilder  Combo_Slot_ItemIDs=new StringBuilder();
    StringBuilder  FoodItemSizeIDs=new StringBuilder();
    String sec_="";
    String value="";
    String com_price="";
    double com_p=0.0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_com_details);
        ButterKnife.bind(this);
        prefsHelper = new PrefsHelper(this);
        com_id=getIntent().getIntExtra("com_id",0);
        combo=getIntent().getStringExtra("combo");
        desc=getIntent().getStringExtra("desc");
        price=getIntent().getStringExtra("price");
        txt_com_desc.setText(desc);
        txt_com_name.setText(combo);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getComDetails();
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveComboToDatabase();
            }
        });

    }
    private void getComDetails(){
        progress_com.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.Url.COM_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress_com.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.has("ComboList")){
                        JSONArray ComboList=jsonObject.getJSONArray("ComboList");
                        if(ComboList.length()>0){
                            JSONObject jsonObject1=ComboList.getJSONObject(0);
                            if(jsonObject1.has("ComboSectionList")){
                                JSONArray ComboSectionList=jsonObject1.getJSONArray("ComboSectionList");
                                Gson gson=new Gson();
                                Type listType=new TypeToken<List<ComboSection>>(){}.getType();
                                comboSections=gson.fromJson(ComboSectionList.toString(),listType);

                            }

                        }
                        if(comboSections.size()>0){
                            setComboSectionAdapter();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_com.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("api_key",prefsHelper.getPref(Constants.API_KEY));
                params.put("lang_code",prefsHelper.getPref(Constants.LNG_CODE));
                params.put("combo_id", String.valueOf(com_id));
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    private void setComboSectionAdapter(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        ComboSectionAdapter comboSectionAdapter=new ComboSectionAdapter(comboSections, new SelectSec() {
            @Override
            public void getClickSec(int pos) {

                setComboSectionValueAdapter(comboSections.get(pos).getComboSectionValue());
            }
        });
        recyler_combo.setAdapter(comboSectionAdapter);
        recyler_combo.setLayoutManager(linearLayoutManager);
    }
    private void setComboSectionValueAdapter(List<ComValue>comValues){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        ComValueAdapter comValueAdapter=new ComValueAdapter(comValues, this, new ComValueAdapter.ComValueClicked() {
            @Override
            public void ComClicked(ComValueItem subItemsRecord,int comslot_id,String com) {
                if(item_ids.length()>0){
                    item_ids.append(",");
                }
                if(FoodItemSizeIDs.length()>0){
                    FoodItemSizeIDs.append(",");
                }
                if(Combo_Slot_ItemIDs.length()>0){
                    Combo_Slot_ItemIDs.append(",");
                }

                item_ids.append(subItemsRecord.getItemID());
                if(!subItemsRecord.getFoodItemSizeID().equalsIgnoreCase("")) {
                    FoodItemSizeIDs.append(subItemsRecord.getFoodItemSizeID());
                }
                else{
                    FoodItemSizeIDs.append("0");
                }
                Combo_Slot_ItemIDs.append(subItemsRecord.getCombo_Slot_ItemID());

                Intent intent=new Intent(ChooseComActivity.this, ComExtraActivity.class);
                intent.putExtra("price",price);
                intent.putExtra("item_id",subItemsRecord.getItemID());
                if(!subItemsRecord.getFoodItemSizeID().equalsIgnoreCase("")){
                    intent.putExtra("size_id",Integer.parseInt(subItemsRecord.getFoodItemSizeID()));
                }
                else {
                    intent.putExtra("size_id",0);
                }
                intent.putExtra("name",combo);
                intent.putExtra("desc",desc);
                intent.putExtra("top_allowed",subItemsRecord.getCombo_Topping_Allow());
                intent.putExtra("com_slot",comslot_id);
                intent.putExtra("free_allowed",Free_allowed);
                intent.putExtra("free_selection_allowed",Free_Topping_Selection_allowed);
sec_=com;
value=subItemsRecord.getCombo_Slot_ItemName();
                startActivityForResult(intent,5);
            }

            @Override
            public void ComValueClicked(ComValue comValue) {
                if(comboslot_ids.length()>0){
                    comboslot_ids.append(",");
                }
                comboslot_ids.append(comValue.getComboslot_id());
                if(!comValue.getFree_allowed().equalsIgnoreCase("")) {
                    Free_allowed = Integer.parseInt(comValue.getFree_allowed());
                    Free_Topping_Selection_allowed=Integer.parseInt(comValue.getFree_Topping_Selection_allowed());
                }
            }
        });
        recyler_sec.setAdapter(comValueAdapter);
        recyler_sec.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==5) {
                if (data != null) {
                    if (data.hasExtra("price")) {
                        String p = data.getStringExtra("price");
                        String item = data.getStringExtra("item");
                        String ids = data.getStringExtra("ids");
if(com_tops.length()>0) {
    com_tops.append(",");

}
if(sec_item.length()>0){
    sec_item.append(",");
}
if(sec_value.length()>0){
    sec_value.append(",");
}
if(com_tops_id.length()>0){
    com_tops_id.append(",");
}


double pr=Double.parseDouble(price);
double price_demo= Double.parseDouble(p);
                        if(sec_item.length()>0){
com_p=com_p+price_demo;
                        }
                        else{
                          com_p=com_p+pr+price_demo;
                        }
                        com_price=String.valueOf(com_p);
                        com_tops.append(item);
                        sec_item.append(sec_);
                        sec_value.append(value);
                        com_tops_id.append(ids);

                }
                    Log.i("url", price + ", " + com_tops.toString() + ", " + sec_item.toString()+", "+sec_value+", "+com_tops_id);
                }
            }
        }
    }


    private void SaveComboToDatabase(){
        String com_list=DroidPrefs.get(this,"com_list",String.class);
        if(com_list!=null){
            Gson gson=new Gson();
            Type listType=new TypeToken<List<ComItemList>>(){}.getType();
            List<ComItemList> comItemLists=gson.fromJson(com_list,listType);
            if(comItemLists!=null) {
            }
            else {
                comItemLists=new ArrayList<>();
            }
                ComItemList comItemList = new ComItemList();
                comItemList.com_sec = sec_item.toString();
                comItemList.combo_name = combo;
                comItemList.sec_value = sec_value.toString();
                comItemList.combo_desc = desc;
            comItemList.combo_top=com_tops.toString();
            comItemList.combo_top_id=com_tops_id.toString();
            comItemList.ItemID=item_ids.toString();
            comItemList.Combo_Slot_ItemID= Combo_Slot_ItemIDs.toString();
            comItemList.FoodItemSizeID=FoodItemSizeIDs.toString();
            comItemList.comboslot_id=comboslot_ids.toString();
            comItemList.quantity=1;
            comItemList.deal_id= String.valueOf(com_id);
                comItemList.price = com_price;
                comItemLists.add(comItemList);
                String com = gson.toJson(comItemLists);
                DroidPrefs.apply(this, "com_list", com);

        }
        finish();




    }


}
