
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantReview {

    @SerializedName("customerName")
    private String customerName;
    @SerializedName("customerReviewComment")
    private String customerReviewComment;
    @SerializedName("customerimage")
    private String customerimage;
    @SerializedName("deliveryrating")
    private String deliveryrating;
    @SerializedName("foodqualityrating")
    private String foodqualityrating;
    @SerializedName("friendlinessrating")
    private String friendlinessrating;
    @SerializedName("id")
    private Long id;
    @SerializedName("Order_Number")
    private String orderNumber;
    @SerializedName("rating")
    private String rating;
    @SerializedName("restaurant_name")
    private String restaurantName;
    @SerializedName("review_id")
    private Long reviewId;
    @SerializedName("reviewpostedDate")
    private String reviewpostedDate;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerReviewComment() {
        return customerReviewComment;
    }

    public void setCustomerReviewComment(String customerReviewComment) {
        this.customerReviewComment = customerReviewComment;
    }

    public String getCustomerimage() {
        return customerimage;
    }

    public void setCustomerimage(String customerimage) {
        this.customerimage = customerimage;
    }

    public String getDeliveryrating() {
        return deliveryrating;
    }

    public void setDeliveryrating(String deliveryrating) {
        this.deliveryrating = deliveryrating;
    }

    public String getFoodqualityrating() {
        return foodqualityrating;
    }

    public void setFoodqualityrating(String foodqualityrating) {
        this.foodqualityrating = foodqualityrating;
    }

    public String getFriendlinessrating() {
        return friendlinessrating;
    }

    public void setFriendlinessrating(String friendlinessrating) {
        this.friendlinessrating = friendlinessrating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewpostedDate() {
        return reviewpostedDate;
    }

    public void setReviewpostedDate(String reviewpostedDate) {
        this.reviewpostedDate = reviewpostedDate;
    }


}
