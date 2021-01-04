package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrderModel {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("orders")
    @Expose
    private Orders orders;
    @SerializedName("Table_Booking_Number")
    @Expose
    private String tableBookingNumber;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public String getTableBookingNumber() {
        return tableBookingNumber;
    }

    public void setTableBookingNumber(String tableBookingNumber) {
        this.tableBookingNumber = tableBookingNumber;
    }
}
