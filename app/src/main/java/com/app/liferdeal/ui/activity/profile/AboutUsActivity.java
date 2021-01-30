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

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private WebView webView;
    private TextView tv_faq;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        if (App.retrieveLangFromGson(AboutUsActivity.this) != null) {
            model = App.retrieveLangFromGson(AboutUsActivity.this);
        }
        initialization();
        initializedListener();
        statusBarColor();
    }


    private void initialization() {
        ivBack = findViewById(R.id.iv_back);
        tv_faq = findViewById(R.id.tv_faq);
        webView = (WebView) findViewById(R.id.webview);

        tv_faq.setText(model.getAboutUs());
        webView.loadUrl("https://www.lieferadeal.de/about-us/");
    }

    private void initializedListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                //HomeActivity.mDrawerLayout.closeDrawer(HomeActivity.rl_main_left_drawer);
                break;
            default:
                break;
        }
    }

    private void statusBarColor() {
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}