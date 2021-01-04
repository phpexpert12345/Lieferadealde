package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MYOrderTrackDetailModel {
    @SerializedName("OrderDetailItem")
    @Expose
    private List<OrderDetailItem> orderDetailItem = null;

    public List<OrderDetailItem> getOrderDetailItem() {
        return orderDetailItem;
    }

    public void setOrderDetailItem(List<OrderDetailItem> orderDetailItem) {
        this.orderDetailItem = orderDetailItem;
    }
}
