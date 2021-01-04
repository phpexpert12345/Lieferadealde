package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscountModel {
    @SerializedName("RestaurantDiscountCouponList")
    @Expose
    private List<RestaurantDiscountCouponList> restaurantDiscountCouponList = null;

    public List<RestaurantDiscountCouponList> getRestaurantDiscountCouponList() {
        return restaurantDiscountCouponList;
    }

    public void setRestaurantDiscountCouponList(List<RestaurantDiscountCouponList> restaurantDiscountCouponList) {
        this.restaurantDiscountCouponList = restaurantDiscountCouponList;
    }

    public class RestaurantDiscountCouponList {

        @SerializedName("CouponTitle")
        @Expose
        private String couponTitle;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("CouponCode")
        @Expose
        private String couponCode;
        @SerializedName("coupon_description")
        @Expose
        private String couponDescription;
        @SerializedName("CouponValidTill")
        @Expose
        private String couponValidTill;
        @SerializedName("coupon_img")
        @Expose
        private String couponImg;
        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("error_msg")
        @Expose
        private String errorMsg;

        public String getCouponTitle() {
            return couponTitle;
        }

        public void setCouponTitle(String couponTitle) {
            this.couponTitle = couponTitle;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponDescription() {
            return couponDescription;
        }

        public void setCouponDescription(String couponDescription) {
            this.couponDescription = couponDescription;
        }

        public String getCouponValidTill() {
            return couponValidTill;
        }

        public void setCouponValidTill(String couponValidTill) {
            this.couponValidTill = couponValidTill;
        }

        public String getCouponImg() {
            return couponImg;
        }

        public void setCouponImg(String couponImg) {
            this.couponImg = couponImg;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

    }
}
