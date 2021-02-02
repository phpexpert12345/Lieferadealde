package com.app.liferdeal.ui.activity.restaurant;

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
import com.app.liferdeal.model.menuitems.ComValue;
import com.app.liferdeal.model.menuitems.ComboSection;
import com.app.liferdeal.ui.adapters.ComValueAdapter;
import com.app.liferdeal.ui.adapters.ComboSectionAdapter;
import com.app.liferdeal.ui.interfaces.SelectSec;
import com.app.liferdeal.util.Constants;
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
    List<ComboSection>comboSections=new ArrayList<>();
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
        ComValueAdapter comValueAdapter=new ComValueAdapter(comValues,this,price,combo,desc);
        recyler_sec.setAdapter(comValueAdapter);
        recyler_sec.setLayoutManager(linearLayoutManager);
    }

}
