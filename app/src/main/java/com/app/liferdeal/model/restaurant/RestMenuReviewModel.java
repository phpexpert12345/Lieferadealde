package com.app.liferdeal.model.restaurant;

import com.app.liferdeal.ui.activity.restaurant.ReviewGalleryPhoto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestMenuReviewModel {

    @SerializedName("reviewlistingList")
    @Expose
    private List<ReviewlistingList> reviewlistingList = null;

    public List<ReviewlistingList> getReviewlistingList() {
        return reviewlistingList;
    }

    public void setReviewlistingList(List<ReviewlistingList> reviewlistingList) {
        this.reviewlistingList = reviewlistingList;
    }



    public class ReviewlistingList {

        @SerializedName("deliveryrating")
        @Expose
        private String deliveryrating;
        @SerializedName("friendlinessrating")
        @Expose
        private String friendlinessrating;
        @SerializedName("foodqualityrating")
        @Expose
        private String foodqualityrating;
        @SerializedName("RestaurantReviewRating")
        @Expose
        private String restaurantReviewRating;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("customerName")
        @Expose
        private String customerName;
        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("success_msg")
        @Expose
        private String successMsg;
        @SerializedName("review_id")
        @Expose
        private Integer reviewId;
        @SerializedName("reviewpostedDate")
        @Expose
        private String reviewpostedDate;
        @SerializedName("customerReviewComment")
        @Expose
        private String customerReviewComment;
        @SerializedName("ReviewGalleryPhoto")
        @Expose
        private List<ReviewGalleryPhoto> reviewGalleryPhoto = null;

        public String getDeliveryrating() {
            return deliveryrating;
        }

        public void setDeliveryrating(String deliveryrating) {
            this.deliveryrating = deliveryrating;
        }

        public String getFriendlinessrating() {
            return friendlinessrating;
        }

        public void setFriendlinessrating(String friendlinessrating) {
            this.friendlinessrating = friendlinessrating;
        }

        public String getFoodqualityrating() {
            return foodqualityrating;
        }

        public void setFoodqualityrating(String foodqualityrating) {
            this.foodqualityrating = foodqualityrating;
        }

        public String getRestaurantReviewRating() {
            return restaurantReviewRating;
        }

        public void setRestaurantReviewRating(String restaurantReviewRating) {
            this.restaurantReviewRating = restaurantReviewRating;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getSuccessMsg() {
            return successMsg;
        }

        public void setSuccessMsg(String successMsg) {
            this.successMsg = successMsg;
        }

        public Integer getReviewId() {
            return reviewId;
        }

        public void setReviewId(Integer reviewId) {
            this.reviewId = reviewId;
        }

        public String getReviewpostedDate() {
            return reviewpostedDate;
        }

        public void setReviewpostedDate(String reviewpostedDate) {
            this.reviewpostedDate = reviewpostedDate;
        }

        public String getCustomerReviewComment() {
            return customerReviewComment;
        }

        public void setCustomerReviewComment(String customerReviewComment) {
            this.customerReviewComment = customerReviewComment;
        }

        public List<ReviewGalleryPhoto> getReviewGalleryPhoto() {
            return reviewGalleryPhoto;
        }

        public void setReviewGalleryPhoto(List<ReviewGalleryPhoto> reviewGalleryPhoto) {
            this.reviewGalleryPhoto = reviewGalleryPhoto;
        }

    }

}
