package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class DeleteAddressResponse {

    @SerializedName("success")
    private Long success;
    @SerializedName("success_msg")
    private String successMsg;

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

}
