package com.app.liferdeal.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class FrontBanner {

    @SerializedName("app_banner_img")
    private String appBannerImg;
    @SerializedName("error")
    private String error;
    @SerializedName("flash_desc")
    private String flashDesc;
    @SerializedName("flash_title")
    private String flashTitle;
    @SerializedName("id")
    private Long id;

    public String getAppBannerImg() {
        return appBannerImg;
    }

    public void setAppBannerImg(String appBannerImg) {
        this.appBannerImg = appBannerImg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFlashDesc() {
        return flashDesc;
    }

    public void setFlashDesc(String flashDesc) {
        this.flashDesc = flashDesc;
    }

    public String getFlashTitle() {
        return flashTitle;
    }

    public void setFlashTitle(String flashTitle) {
        this.flashTitle = flashTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
