package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class RmVerifyCouponCodeResponse {
    @SerializedName("CouponCode")
    private String couponCode;
    @SerializedName("CouponCodePrice")
    private String couponCodePrice;
    @SerializedName("couponCodeType")
    private String couponCodeType;
    @SerializedName("error")
    private Long error;
    @SerializedName("error_msg")
    private String error_msg;
    @SerializedName("success_msg")
    private String successMsg;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponCodePrice() {
        return couponCodePrice;
    }

    public void setCouponCodePrice(String couponCodePrice) {
        this.couponCodePrice = couponCodePrice;
    }

    public String getCouponCodeType() {
        return couponCodeType;
    }

    public void setCouponCodeType(String couponCodeType) {
        this.couponCodeType = couponCodeType;
    }

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}
