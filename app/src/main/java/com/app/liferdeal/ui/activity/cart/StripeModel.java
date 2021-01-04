package com.app.liferdeal.ui.activity.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StripeModel {
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private String message;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
