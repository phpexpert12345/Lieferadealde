
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryArea {

    @SerializedName("Admin_district")
    private String adminDistrict;
    @SerializedName("delivery_charge")
    private String deliveryCharge;
    @SerializedName("delivery_time")
    private String deliveryTime;
    @SerializedName("error")
    private String error;
    @SerializedName("id")
    private Long id;
    @SerializedName("max_radius_range")
    private String maxRadiusRange;
    @SerializedName("min_radius_range")
    private String minRadiusRange;
    @SerializedName("minimum_order")
    private String minimumOrder;
    @SerializedName("postcode")
    private String postcode;
    @SerializedName("Postcode_lat")
    private String postcodeLat;
    @SerializedName("Postcode_long")
    private String postcodeLong;
    @SerializedName("shipping_charge")
    private String shippingCharge;

    public String getAdminDistrict() {
        return adminDistrict;
    }

    public void setAdminDistrict(String adminDistrict) {
        this.adminDistrict = adminDistrict;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaxRadiusRange() {
        return maxRadiusRange;
    }

    public void setMaxRadiusRange(String maxRadiusRange) {
        this.maxRadiusRange = maxRadiusRange;
    }

    public String getMinRadiusRange() {
        return minRadiusRange;
    }

    public void setMinRadiusRange(String minRadiusRange) {
        this.minRadiusRange = minRadiusRange;
    }

    public String getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(String minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPostcodeLat() {
        return postcodeLat;
    }

    public void setPostcodeLat(String postcodeLat) {
        this.postcodeLat = postcodeLat;
    }

    public String getPostcodeLong() {
        return postcodeLong;
    }

    public void setPostcodeLong(String postcodeLong) {
        this.postcodeLong = postcodeLong;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

}
