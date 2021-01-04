package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryTime {

    @SerializedName("GetTime")
    private String getTime;
    @SerializedName("success")
    private String success;

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
