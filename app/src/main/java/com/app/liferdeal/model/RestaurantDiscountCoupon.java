
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantDiscountCoupon {

    @SerializedName("CouponCode")
    private String couponCode;
    @SerializedName("coupon_description")
    private String couponDescription;
    @SerializedName("coupon_img")
    private String couponImg;
    @SerializedName("CouponTitle")
    private String couponTitle;
    @SerializedName("CouponValidTill")
    private String couponValidTill;
    @SerializedName("error")
    private String error;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("id")
    private Long id;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public String getCouponImg() {
        return couponImg;
    }

    public void setCouponImg(String couponImg) {
        this.couponImg = couponImg;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponValidTill() {
        return couponValidTill;
    }

    public void setCouponValidTill(String couponValidTill) {
        this.couponValidTill = couponValidTill;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
