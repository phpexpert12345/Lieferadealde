package com.app.liferdeal.ui.activity.my_review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewMain {

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
