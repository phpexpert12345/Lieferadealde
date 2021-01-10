package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MYOrderTrackDetailModel {

    public List<OrderTrackHistory> getOrderTrackHistory() {
        return orderTrackHistory;
    }

    public void setOrderTrackHistory(List<OrderTrackHistory> orderTrackHistory) {
        this.orderTrackHistory = orderTrackHistory;
    }

    @SerializedName("OrderTrackHistory")
    @Expose
    private List<OrderTrackHistory> orderTrackHistory = null;



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
