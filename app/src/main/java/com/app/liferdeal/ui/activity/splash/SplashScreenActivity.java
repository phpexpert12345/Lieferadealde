package com.app.liferdeal.ui.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.app.liferdeal.R;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.ui.activity.cart.StripePaymentActivity;
import com.app.liferdeal.ui.activity.cart.paypal.PaypalActivity;
import com.app.liferdeal.ui.activity.cart.paypal.TestClass;
import com.app.liferdeal.util.PrefsHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends Activity {

    @BindView(R.id.ivLogo)
    AppCompatImageView ivLogo;
    private PrefsHelper authPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        authPreference = new PrefsHelper(this);

        Animation animation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.slide_up);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(700);
        ivLogo.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (authPreference.isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //startActivity(new Intent(getApplicationContext(), StripePaymentActivity.class));
                    //startActivity(new Intent(getApplicationContext(), PaypalActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                    finish();
                }
            }
        }, 1000);
    }
}
