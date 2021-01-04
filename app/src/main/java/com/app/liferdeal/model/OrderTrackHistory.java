
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class OrderTrackHistory {

    @SerializedName("order_status")
    private String orderStatus;
    @SerializedName("order_status_date")
    private String orderStatusDate;
    @SerializedName("order_status_message")
    private String orderStatusMessage;
    @SerializedName("order_status_time")
    private String orderStatusTime;

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

    public String getOrderStatusMessage() {
        return orderStatusMessage;
    }

    public void setOrderStatusMessage(String orderStatusMessage) {
        this.orderStatusMessage = orderStatusMessage;
    }

    public String getOrderStatusTime() {
        return orderStatusTime;
    }

    public void setOrderStatusTime(String orderStatusTime) {
        this.orderStatusTime = orderStatusTime;
    }

}
