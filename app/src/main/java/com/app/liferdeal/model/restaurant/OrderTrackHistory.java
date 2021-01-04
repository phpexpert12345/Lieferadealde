package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderTrackHistory {
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_status_date")
    @Expose
    private String orderStatusDate;
    @SerializedName("order_status_time")
    @Expose
    private String orderStatusTime;
    @SerializedName("order_status_message")
    @Expose
    private String orderStatusMessage;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusDate() {
        return orderStatusDate;
    }

    public void setOrderStatusDate(String orderStatusDate) {
        this.orderStatusDate = orderStatusDate;
    }

    public String getOrderStatusTime() {
        return orderStatusTime;
    }

    public void setOrderStatusTime(String orderStatusTime) {
        this.orderStatusTime = orderStatusTime;
    }

    public String getOrderStatusMessage() {
        return orderStatusMessage;
    }

    public void setOrderStatusMessage(String orderStatusMessage) {
        this.orderStatusMessage = orderStatusMessage;
    }
}
