
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("DriverID")
    private String driverID;
    @SerializedName("Favorites_display")
    private String favoritesDisplay;
    @SerializedName("ordPrice")
    private String ordPrice;
    @SerializedName("order_date")
    private String orderDate;
    @SerializedName("order_display_message")
    private String orderDisplayMessage;
    @SerializedName("order_identifyno")
    private String orderIdentifyno;
    @SerializedName("order_status_color_code")
    private String orderStatusColorCode;
    @SerializedName("order_status_msg")
    private String orderStatusMsg;
    @SerializedName("order_time")
    private String orderTime;
    @SerializedName("order_type")
    private String orderType;
    @SerializedName("payment_mode")
    private String paymentMode;
    @SerializedName("restaurant_address")
    private String restaurantAddress;
    @SerializedName("restaurant_id")
    private String restaurantId;
    @SerializedName("restaurant_name")
    private String restaurantName;
    @SerializedName("rider_order_close")
    private String riderOrderClose;
    @SerializedName("rider_otp")
    private String riderOtp;
    @SerializedName("RiderRating")
    private String riderRating;
    @SerializedName("RiderRatingComment")
    private String riderRatingComment;
    @SerializedName("rider_review")
    private String riderReview;

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getFavoritesDisplay() {
        return favoritesDisplay;
    }

    public void setFavoritesDisplay(String favoritesDisplay) {
        this.favoritesDisplay = favoritesDisplay;
    }

    public String getOrdPrice() {
        return ordPrice;
    }

    public void setOrdPrice(String ordPrice) {
        this.ordPrice = ordPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDisplayMessage() {
        return orderDisplayMessage;
    }

    public void setOrderDisplayMessage(String orderDisplayMessage) {
        this.orderDisplayMessage = orderDisplayMessage;
    }

    public String getOrderIdentifyno() {
        return orderIdentifyno;
    }

    public void setOrderIdentifyno(String orderIdentifyno) {
        this.orderIdentifyno = orderIdentifyno;
    }

    public String getOrderStatusColorCode() {
        return orderStatusColorCode;
    }

    public void setOrderStatusColorCode(String orderStatusColorCode) {
        this.orderStatusColorCode = orderStatusColorCode;
    }

    public String getOrderStatusMsg() {
        return orderStatusMsg;
    }

    public void setOrderStatusMsg(String orderStatusMsg) {
        this.orderStatusMsg = orderStatusMsg;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
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

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRiderOrderClose() {
        return riderOrderClose;
    }

    public void setRiderOrderClose(String riderOrderClose) {
        this.riderOrderClose = riderOrderClose;
    }

    public String getRiderOtp() {
        return riderOtp;
    }

    public void setRiderOtp(String riderOtp) {
        this.riderOtp = riderOtp;
    }

    public String getRiderRating() {
        return riderRating;
    }

    public void setRiderRating(String riderRating) {
        this.riderRating = riderRating;
    }

    public String getRiderRatingComment() {
        return riderRatingComment;
    }

    public void setRiderRatingComment(String riderRatingComment) {
        this.riderRatingComment = riderRatingComment;
    }

    public String getRiderReview() {
        return riderReview;
    }

    public void setRiderReview(String riderReview) {
        this.riderReview = riderReview;
    }

}
