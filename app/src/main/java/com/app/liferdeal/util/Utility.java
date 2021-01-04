package com.app.liferdeal.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.app.liferdeal.R;
import com.app.liferdeal.network.retrofit.RFClient;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;


public class Utility {
    private static final String TAG = "Utility";

    public static DateFormat DELIVERY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static DateFormat DELIVERY_TIME_FORMAT = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

    public static DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    public static DateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

    public static DateFormat DAY_FORMAT = new SimpleDateFormat("EEEE", Locale.ENGLISH);
    public static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

  /*  public static RFInterface getRetrofitInterface() {
        return RFClient.getClient().create(RFInterface.class);
    }*/

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog);

        return dialog;
    }

    public static void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    public static String getCurrencySymbol(String currencyCode) {
        try {
            Currency currency = Currency.getInstance(currencyCode);
            return currency.getSymbol();
        } catch (Exception e) {
            return currencyCode;
        }
    }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(mFileName, ".jpg", storageDir);
    }

    public interface OnPositiveClick {
        void onClick(DialogInterface dialogInterface);
    }

    public interface OnNegativeClick {
        void onClick(DialogInterface dialogInterface);
    }

}
