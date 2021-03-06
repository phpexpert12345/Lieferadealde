package com.app.liferdeal.ui.activity.profile;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.util.Constants;

public class PrivacyAndPolicy extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private WebView webView;
    private TextView tv_terms_condition;
    private LanguageResponse model;
    private ProgressDialog progressDialog;
    private ProgressBar pbLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.privacypolicy);
        model = new LanguageResponse();
        if (App.retrieveLangFromGson(PrivacyAndPolicy.this) != null) {
            model = App.retrieveLangFromGson(PrivacyAndPolicy.this);
        }

        initialization();
        initializedListener();
        statusBarColor();
    }
    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            //showProgress();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

//            hideProgress();
        }
    }

    private void initialization() {
//        showProgress();
        ivBack = findViewById(R.id.iv_back);
        pbLoad = findViewById(R.id.pbLoad);
        //pbLoad.setVisibility(View.VISIBLE);
        tv_terms_condition = findViewById(R.id.tv_terms_condition);
        webView = (WebView) findViewById(R.id.webview);


        if (getIntent() != null) {
            String from = getIntent().getStringExtra("from");
            if (from.equalsIgnoreCase("privacy")) {
                tv_terms_condition.setText(model.getPrivacyPolicy());

                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new MyWebViewClient());
                webView.loadUrl(Constants.Url.BASE_URL + "privacy_statement_web.php");
                pbLoad.setVisibility(View.GONE);
                //hideProgress();
                //Toast.makeText(getApplicationContext(),"Calllllleddd",Toast.LENGTH_LONG).show();
                //pbLoad.setVisibility(View.GONE);
                //hideProgress();
            } else {
                tv_terms_condition.setText(model.getTermsOfService());
                webView.loadUrl(Constants.Url.BASE_URL + "terms_of_use_web.php");
                pbLoad.setVisibility(View.GONE);
                //hideProgress();
            }
            //pbLoad.setVisibility(View.GONE);

        } else {
            tv_terms_condition.setText(model.getTermsOfService());
            webView.loadUrl(Constants.Url.BASE_URL + "terms_of_use_web.php");
            pbLoad.setVisibility(View.GONE);
            // hideProgress();
            //pbLoad.setVisibility(View.GONE);
        }
        // hideProgress();
    }

    public void showProgress() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(model.getPlease_wait_text().trim()+"...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    private void initializedListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }

    }

    private void statusBarColor() {
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

