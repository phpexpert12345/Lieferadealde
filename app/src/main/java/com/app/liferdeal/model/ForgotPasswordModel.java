package com.app.liferdeal.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordModel {

    @SerializedName("error")
    @Expose
    public Integer error;
    @SerializedName("error_msg")
    @Expose
    public String errorMsg;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}