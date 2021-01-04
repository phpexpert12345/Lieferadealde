package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class RmVerifyLoyaltyPointResponse {

    @SerializedName("success")
    private Long success;
    @SerializedName("success_msg")
    private String successMsg;
    @SerializedName("Total_Loyalty_amount")
    private String totalLoyaltyAmount;
    @SerializedName("Total_Loyalty_Point")
    private String totalLoyaltyPoint;
    @SerializedName("error_msg")
    private String errorMsg;

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getTotalLoyaltyAmount() {
        return totalLoyaltyAmount;
    }

    public void setTotalLoyaltyAmount(String totalLoyaltyAmount) {
        this.totalLoyaltyAmount = totalLoyaltyAmount;
    }

    public String getTotalLoyaltyPoint() {
        return totalLoyaltyPoint;
    }

    public void setTotalLoyaltyPoint(String totalLoyaltyPoint) {
        this.totalLoyaltyPoint = totalLoyaltyPoint;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
