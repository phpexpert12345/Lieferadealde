package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class RmGetLoyaltyPointResponse {
    @SerializedName("success")
    private Long success;
    @SerializedName("success_msg")
    private String successMsg;
    @SerializedName("Total_Loyalty_points")
    private String totalLoyaltyPoints;

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

    public String getTotalLoyaltyPoints() {
        return totalLoyaltyPoints;
    }

    public void setTotalLoyaltyPoints(String totalLoyaltyPoints) {
        this.totalLoyaltyPoints = totalLoyaltyPoints;
    }
}
