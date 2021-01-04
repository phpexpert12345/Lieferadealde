package com.app.liferdeal.network;

import android.app.ProgressDialog;
import android.content.Context;

import com.app.liferdeal.util.Utility;


public class NetworkOperations {

    private Boolean isVisible;
    private ProgressDialog progressDialog;

    public NetworkOperations(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void onStart(Context context, String msg) {
        if (isVisible) {
            progressDialog = Utility.createProgressDialog(context);
        }
    }

    public void onComplete() {
        if (isVisible) {
            progressDialog.dismiss();
        }
    }
}
