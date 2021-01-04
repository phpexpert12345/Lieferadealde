package com.app.liferdeal.model.restaurant;

import com.app.liferdeal.model.OrderFoodItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderTrackDetailItemModel {
    @SerializedName("OrderDetailItem")
    @Expose
    private List<OrderDetailItem> orderDetailItem = null;
    @SerializedName("OrderFoodItem")
    @Expose
    private List<OrderFoodItem> orderFoodItem = null;
    @SerializedName("OrderTrackHistory")
    @Expose
    private List<OrderTrackHistory> orderTrackHistory = null;
    @SerializedName("OrderMealItem")
    @Expose
    private List<Object> orderMealItem = null;

    public List<OrderDetailItem> getOrderDetailItem() {
        return orderDetailItem;
    }

    public void setOrderDetailItem(List<OrderDetailItem> orderDetailItem) {
        this.orderDetailItem = orderDetailItem;
    }

    public List<OrderFoodItem> getOrderFoodItem() {
        return orderFoodItem;
    }

    public void setOrderFoodItem(List<OrderFoodItem> orderFoodItem) {
        this.orderFoodItem = orderFoodItem;
    }

    public List<OrderTrackHistory> getOrderTrackHistory() {
        return orderTrackHistory;
    }

    public void setOrderTrackHistory(List<OrderTrackHistory> orderTrackHistory) {
        this.orderTrackHistory = orderTrackHistory;
    }

    public List<Object> getOrderMealItem() {
        return orderMealItem;
    }

    public void setOrderMealItem(List<Object> orderMealItem) {
        this.orderMealItem = orderMealItem;
    }

}
