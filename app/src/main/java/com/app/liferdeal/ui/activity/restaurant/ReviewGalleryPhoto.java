package com.app.liferdeal.ui.activity.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewGalleryPhoto {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("review_img")
    @Expose
    private String reviewImg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getReviewImg() {
        return reviewImg;
    }

    public void setReviewImg(String reviewImg) {
        this.reviewImg = reviewImg;
    }

}
