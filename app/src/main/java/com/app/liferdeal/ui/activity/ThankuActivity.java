package com.app.liferdeal.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.ui.activity.profile.ReferEarnFrndActivity;
import com.app.liferdeal.ui.activity.restaurant.RestaurantDetails;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankuActivity extends AppCompatActivity {
    @BindView(R.id.tvMsg)
    TextView tvMsg;
    @BindView(R.id.tvBookingNo)
    TextView tvBookingNo;
    @BindView(R.id.tvBookingDate)
    TextView tvBookingDate;
    @BindView(R.id.tvNote)
    TextView tvNote;
    @BindView(R.id.tvThankuTxt)
    TextView tvThankuTxt;
    @BindView(R.id.btnHome)
    Button btnHome;
    private String booking_no = "", msg = "", date = "", note = "";
    private LanguageResponse model = new LanguageResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);
        ButterKnife.bind(this);
        if (App.retrieveLangFromGson(ThankuActivity.this) != null) {
            model = App.retrieveLangFromGson(ThankuActivity.this);
        }

        if (getIntent() != null) {
            booking_no = getIntent().getStringExtra("booking_no");
            msg = getIntent().getStringExtra("msg");
            date = getIntent().getStringExtra("date");
            note = getIntent().getStringExtra("note");
        }

        btnHome.setText(model.getGOTOHOME());
        tvThankuTxt.setText(model.getThankYou());
        tvMsg.setText(msg);
        tvBookingNo.setText(model.getYourBookingNumber() + " " + booking_no);
        tvBookingDate.setText(model.getBookingDate() + ": " + date);
        tvNote.setText(model.getNoteString() + ": " + note);
    }

    @OnClick(R.id.btnHome)
    public void onClick() {
        Intent i = new Intent(ThankuActivity.this, RestaurantDetails.class);
        /*i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
        startActivity(i);
        finish();
    }
}