package com.app.liferdeal.ui.activity.my_review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewMain {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String error_msg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    @SerializedName("CustomerReviewlistingList")
    @Expose
    private List<List<ReviewMainData>> customerReviewlistingList = null;

    public List<List<ReviewMainData>> getCustomerReviewlistingList() {
        return customerReviewlistingList;
    }

    public void setCustomerReviewlistingList(List<List<ReviewMainData>> customerReviewlistingList) {
        this.customerReviewlistingList = customerReviewlistingList;
    }
}
