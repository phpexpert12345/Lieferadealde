package com.app.liferdeal.model.splash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SplashModel {
    @SerializedName("SplashBannersList")
    @Expose
    private List<SplashBannersList> splashBannersList = null;

    public List<SplashBannersList> getSplashBannersList() {
        return splashBannersList;
    }

    public void setSplashBannersList(List<SplashBannersList> splashBannersList) {
        this.splashBannersList = splashBannersList;
    }

    public class SplashBannersList {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("splash_banner_img")
        @Expose
        private String splashBannerImg;
        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("Get_Started_Text")
        @Expose
        private String getStartedText;

        public String getGetStartedText() {
            return getStartedText;
        }

        public void setGetStartedText(String getStartedText) {
            this.getStartedText = getStartedText;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSplashBannerImg() {
            return splashBannerImg;
        }

        public void setSplashBannerImg(String splashBannerImg) {
            this.splashBannerImg = splashBannerImg;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
