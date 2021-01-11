package com.app.liferdeal.ui.activity.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.ui.activity.MainActivity;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.List;

public class ReferEarnFrndActivity extends AppCompatActivity implements View.OnClickListener {

    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private RelativeLayout rlt_btn_got_to_home;
    private ImageView iv_back, watsapp, facebook, twitter, share;
    private String strReferCode = "", referSharingMessage, referralEarnPoints, refercodeMessage, referralJoinFriends;
    private TextView refercode, refercodemsg, earnfrndjointpoints, tv_faq, tvRefferalCode, joinfriends, joinfriendss;
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referearn_activity);
        if (App.retrieveLangFromGson(ReferEarnFrndActivity.this) != null) {
            model = App.retrieveLangFromGson(ReferEarnFrndActivity.this);
        }
        init();
    }

    private void init() {
        prefsHelper = new PrefsHelper(this);
        progressDialog = new ProgressDialog(this);
        rlt_btn_got_to_home = findViewById(R.id.rlt_btn_got_to_home);
        iv_back = findViewById(R.id.iv_back);
        refercode = findViewById(R.id.refercode);
        refercodemsg = findViewById(R.id.refercodemsg);
        earnfrndjointpoints = findViewById(R.id.earnpoints);
        watsapp = findViewById(R.id.watsapp);
        facebook = findViewById(R.id.facebook);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        share = findViewById(R.id.share);
        tv_faq = findViewById(R.id.tv_faq);
        tvRefferalCode = findViewById(R.id.tvRefferalCode);
        joinfriends = findViewById(R.id.joinfriends);
        joinfriendss = findViewById(R.id.joinfriendss);

        tv_faq.setText("" + model.getReferAFriend().trim());
        tvRefferalCode.setText("" + model.getReferralCode().trim());
        joinfriends.setText("" + model.getYouHaveReferred().trim());
        joinfriendss.setText("" + model.getYouHaveReferred().trim());
        refercodemsg.setText("" + model.getReferWithFriendsWhenFriendJoinAndPlaceOrdersFromHarper().trim());

        strReferCode = prefsHelper.getPref(Constants.REFERCODELOGIN);
        refercodeMessage = prefsHelper.getPref(Constants.refercodeMessage);
        referSharingMessage = prefsHelper.getPref(Constants.referSharingMessage);
        referralEarnPoints = prefsHelper.getPref(Constants.referralEarnPoints);
        referralJoinFriends = prefsHelper.getPref(Constants.referralJoinFriends);

        System.out.println("===== strReferCode : " + strReferCode + "refercodeMessage : " + refercodeMessage + "referSharingMessage :" + referSharingMessage + "referralEarnPoints : " + referralEarnPoints + "referralJoinFriends : " + referralJoinFriends);
        refercode.setText(strReferCode);
        refercodemsg.setText(refercodeMessage);
        earnfrndjointpoints.setText(referralJoinFriends + " " + model.getFriend());
        rlt_btn_got_to_home.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        watsapp.setOnClickListener(this);
        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.watsapp:
                shareWithWhatsapp();
                break;

            case R.id.facebook:
                shareWithWFacebook();
                break;

            case R.id.twitter:
                shareDataOnTwitter();
                break;

            case R.id.share:
                shareWithChooser();
                break;

            case R.id.rlt_btn_got_to_home:
                Intent i = new Intent(ReferEarnFrndActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;

            default:
                break;
        }
    }

    private void shareWithWhatsapp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, referSharingMessage);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            //   ToastHelper.MakeShortText("Whatsapp have not been installed.");
            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareWithWFacebook() {
        String urlToShare = "https://stackoverflow.com/questions/7545254";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, referSharingMessage);

// See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

// As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + referSharingMessage;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }

    private void shareDataOnTwitter() {
        Intent tweet = new Intent(Intent.ACTION_VIEW);
        tweet.setData(Uri.parse("http://twitter.com/?status=" + referSharingMessage));//where message is your string message
        startActivity(tweet);
    }

    private void shareWithChooser() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody = "Here is the share content body";
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        // intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, referSharingMessage);
        /*Fire!*/
        startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
    }
}
