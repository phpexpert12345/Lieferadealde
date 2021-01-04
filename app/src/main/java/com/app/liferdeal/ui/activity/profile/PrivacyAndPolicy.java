package com.app.liferdeal.ui.activity.profile;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

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

    private void initialization() {
        ivBack = findViewById(R.id.iv_back);
        tv_terms_condition = findViewById(R.id.tv_terms_condition);
        webView = (WebView) findViewById(R.id.webview);
        if (getIntent() != null) {
            String from = getIntent().getStringExtra("from");
            if (from.equalsIgnoreCase("privacy")) {
                tv_terms_condition.setText(model.getPrivacyPolicy());
                webView.loadUrl(Constants.Url.BASE_URL + "privacy_statement_web.php");
            } else {
                tv_terms_condition.setText(model.getTermsOfService());
                webView.loadUrl(Constants.Url.BASE_URL + "terms_of_use_web.php");
            }
        } else {
            tv_terms_condition.setText(model.getTermsOfService());
            webView.loadUrl(Constants.Url.BASE_URL + "terms_of_use_web.php");
        }
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

