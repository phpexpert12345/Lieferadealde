package com.app.liferdeal.ui.activity.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.liferdeal.R;
import com.app.liferdeal.util.PrefsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HelpSupportActivity extends AppCompatActivity {
    TextView mobile, email;
    private ProgressDialog pDialog;
    ImageView iv_back;
    private PrefsHelper prefsHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpsupport);
        prefsHelper = new PrefsHelper(HelpSupportActivity.this);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        iv_back = findViewById(R.id.iv_back);

    //    HitUrlForHelpSupportData();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }

    /*public void HitUrlForHelpSupportData() {
        pDialog = new ProgressDialog(HelpSupportActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, UrlConstants.HelpSupport, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ressponse", response);
                pDialog.dismiss();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String website_name = jsonObj.getString("WEBSITE_NAME");
                    String website_address = jsonObj.getString("WEBSITE_ADDRESS");
                    String website_support_email = jsonObj.getString("WEBSITE_SUPPORT_EMAIL");
                    String website_toll_free_number = jsonObj.getString("WEBSITE_TOLL_FREE_NUMBER");
                    String WEBSITE_DIGITAL_WALLET_TERMS = jsonObj.getString("WEBSITE_DIGITAL_WALLET_TERMS");
                    String success = jsonObj.getString("success");

                    authPreference = new AuthPreference(HelpSupportActivity.this);
                    authPreference.setDigitalWalletMessage(WEBSITE_DIGITAL_WALLET_TERMS);

                    email.setText(website_support_email);
                    mobile.setText(website_toll_free_number);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                showCustomDialog1decline(error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to customer login
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_country", myPref.getState());
                params.put("customer_city", myPref.getCity());
                params.put("customer_lat", myPref.getLatitude());
                params.put("customer_long", myPref.getLongitude());
                return params;
            }

        };
        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }*/

    private void showCustomDialog1decline(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);

        alertDialog.setIcon(R.drawable.tick);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
}