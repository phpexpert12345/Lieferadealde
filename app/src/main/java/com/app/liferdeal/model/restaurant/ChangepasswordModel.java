package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangepasswordModel {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("success_msg")
    @Expose
    private String successMsg;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_msg")
    @Expose
    private String error_msg;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}
