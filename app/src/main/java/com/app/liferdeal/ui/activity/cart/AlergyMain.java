package com.app.liferdeal.ui.activity.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlergyMain {

    @SerializedName("AlleryInfo")
    @Expose
    private String alleryInfo;

    public String getAlleryInfo() {
        return alleryInfo;
    }

    public void setAlleryInfo(String alleryInfo) {
        this.alleryInfo = alleryInfo;
    }
}
