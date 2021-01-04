
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class SplashBanners {

    @SerializedName("error")
    private String error;
    @SerializedName("id")
    private Long id;
    @SerializedName("splash_banner_img")
    private String splashBannerImg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSplashBannerImg() {
        return splashBannerImg;
    }

    public void setSplashBannerImg(String splashBannerImg) {
        this.splashBannerImg = splashBannerImg;
    }

}
