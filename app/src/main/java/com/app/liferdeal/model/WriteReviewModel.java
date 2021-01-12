package com.app.liferdeal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WriteReviewModel {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("resid")
    @Expose
    private String resid;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;
    @SerializedName("order_identifyno")
    @Expose
    private String orderIdentifyno;
    @SerializedName("RestaurantReviewRating")
    @Expose
    private String restaurantReviewRating;
    @SerializedName("Quality_ratingN")
    @Expose
    private String qualityRatingN;
    @SerializedName("Service_ratingN")
    @Expose
    private String serviceRatingN;
    @SerializedName("Time_ratingN")
    @Expose
    private String timeRatingN;
    @SerializedName("RestaurantReviewContent")
    @Expose
    private String restaurantReviewContent;
    @SerializedName("RestaurantReviewName")
    @Expose
    private String restaurantReviewName;
    @SerializedName("ratingAvg")
    @Expose
    private Object ratingAvg;
    @SerializedName("success_msg")
    @Expose
    private String successMsg;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderIdentifyno() {
        return orderIdentifyno;
    }

    public void setOrderIdentifyno(String orderIdentifyno) {
        this.orderIdentifyno = orderIdentifyno;
    }

    public String getRestaurantReviewRating() {
        return restaurantReviewRating;
    }

    public void setRestaurantReviewRating(String restaurantReviewRating) {
        this.restaurantReviewRating = restaurantReviewRating;
    }

    public String getQualityRatingN() {
        return qualityRatingN;
    }

    public void setQualityRatingN(String qualityRatingN) {
        this.qualityRatingN = qualityRatingN;
    }

    public String getServiceRatingN() {
        return serviceRatingN;
    }

    public void setServiceRatingN(String serviceRatingN) {
        this.serviceRatingN = serviceRatingN;
    }

    public String getTimeRatingN() {
        return timeRatingN;
    }

    public void setTimeRatingN(String timeRatingN) {
        this.timeRatingN = timeRatingN;
    }

    public String getRestaurantReviewContent() {
        return restaurantReviewContent;
    }

    public void setRestaurantReviewContent(String restaurantReviewContent) {
        this.restaurantReviewContent = restaurantReviewContent;
    }

    public String getRestaurantReviewName() {
        return restaurantReviewName;
    }

    public void setRestaurantReviewName(String restaurantReviewName) {
        this.restaurantReviewName = restaurantReviewName;
    }

    public Object getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(Object ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}
