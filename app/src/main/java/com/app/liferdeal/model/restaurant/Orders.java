package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orders {

        @SerializedName("OrderViewResult")
        @Expose
        private List<OrderViewResult> orderViewResult = null;

        public List<OrderViewResult> getOrderViewResult() {
            return orderViewResult;
        }

        public void setOrderViewResult(List<OrderViewResult> orderViewResult) {
            this.orderViewResult = orderViewResult;
        }

    public class OrderViewResult {

        @SerializedName("order_identifyno")
        @Expose
        private String orderIdentifyno;

        public String getOrder_status_close() {
            return order_status_close;
        }

        public void setOrder_status_close(String order_status_close) {
            this.order_status_close = order_status_close;
        }

        @SerializedName("order_status_close")
        @Expose
        private String order_status_close;
        @SerializedName("order_time")
        @Expose
        private String orderTime;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("ordPrice")
        @Expose
        private String ordPrice;
        @SerializedName("order_type")
        @Expose
        private String orderType;
        @SerializedName("payment_mode")
        @Expose
        private String paymentMode;
        @SerializedName("restaurant_Logo")
        @Expose
        private String restaurantLogo;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("restaurant_address")
        @Expose
        private String restaurantAddress;
        @SerializedName("order_status_msg")
        @Expose
        private String orderStatusMsg;
        @SerializedName("order_status_color_code")
        @Expose
        private String orderStatusColorCode;
        @SerializedName("Favorites_display")
        @Expose
        private String favoritesDisplay;
        @SerializedName("rider_otp")
        @Expose
        private String riderOtp;
        @SerializedName("rider_review")
        @Expose
        private String riderReview;
        @SerializedName("rider_order_close")
        @Expose
        private String riderOrderClose;
        @SerializedName("DriverID")
        @Expose
        private String driverID;
        @SerializedName("RiderRating")
        @Expose
        private String riderRating;
        @SerializedName("RiderRatingComment")
        @Expose
        private String riderRatingComment;
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

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getOrdPrice() {
            return ordPrice;
        }

        public void setOrdPrice(String ordPrice) {
            this.ordPrice = ordPrice;
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

        public String getRestaurantLogo() {
            return restaurantLogo;
        }

        public void setRestaurantLogo(String restaurantLogo) {
            this.restaurantLogo = restaurantLogo;
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

        public String getOrderStatusMsg() {
            return orderStatusMsg;
        }

        public void setOrderStatusMsg(String orderStatusMsg) {
            this.orderStatusMsg = orderStatusMsg;
        }

        public String getOrderStatusColorCode() {
            return orderStatusColorCode;
        }

        public void setOrderStatusColorCode(String orderStatusColorCode) {
            this.orderStatusColorCode = orderStatusColorCode;
        }

        public String getFavoritesDisplay() {
            return favoritesDisplay;
        }

        public void setFavoritesDisplay(String favoritesDisplay) {
            this.favoritesDisplay = favoritesDisplay;
        }

        public String getRiderOtp() {
            return riderOtp;
        }

        public void setRiderOtp(String riderOtp) {
            this.riderOtp = riderOtp;
        }

        public String getRiderReview() {
            return riderReview;
        }

        public void setRiderReview(String riderReview) {
            this.riderReview = riderReview;
        }

        public String getRiderOrderClose() {
            return riderOrderClose;
        }

        public void setRiderOrderClose(String riderOrderClose) {
            this.riderOrderClose = riderOrderClose;
        }

        public String getDriverID() {
            return driverID;
        }

        public void setDriverID(String driverID) {
            this.driverID = driverID;
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
}
