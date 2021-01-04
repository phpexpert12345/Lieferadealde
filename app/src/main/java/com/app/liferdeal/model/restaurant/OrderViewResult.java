package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderViewResult {
    @SerializedName("order_identifyno")
    @Expose
    private String orderIdentifyno;
    @SerializedName("order_time")
    @Expose
    private String orderTime;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("ordPrice")
    @Expose
    private String ordPrice;
    @SerializedName("FoodCosts")
    @Expose
    private Object foodCosts;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_address")
    @Expose
    private String restaurantAddress;
    @SerializedName("order_display_message")
    @Expose
    private String orderDisplayMessage;
    @SerializedName("order_date")
    @Expose
    private String orderDate;

    public String getOrderIdentifyno() {
        return orderIdentifyno;
    }

    public void setOrderIdentifyno(String orderIdentifyno) {
        this.orderIdentifyno = orderIdentifyno;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getOrdPrice() {
        return ordPrice;
    }

    public void setOrdPrice(String ordPrice) {
        this.ordPrice = ordPrice;
    }

    public Object getFoodCosts() {
        return foodCosts;
    }

    public void setFoodCosts(Object foodCosts) {
        this.foodCosts = foodCosts;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getOrderDisplayMessage() {
        return orderDisplayMessage;
    }

    public void setOrderDisplayMessage(String orderDisplayMessage) {
        this.orderDisplayMessage = orderDisplayMessage;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
