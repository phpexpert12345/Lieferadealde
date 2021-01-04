package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryListInfo {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("Admin_district")
    @Expose
    private String adminDistrict;
    @SerializedName("Postcode_lat")
    @Expose
    private String postcodeLat;
    @SerializedName("Postcode_long")
    @Expose
    private String postcodeLong;
    @SerializedName("min_radius_range")
    @Expose
    private String minRadiusRange;
    @SerializedName("max_radius_range")
    @Expose
    private String maxRadiusRange;
    @SerializedName("minimum_order")
    @Expose
    private String minimumOrder;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("holiday_date")
    @Expose
    private String holidayDate;
    @SerializedName("holiday_start_time")
    @Expose
    private String holidayStartTime;
    @SerializedName("holiday_end_time")
    @Expose
    private String holidayEndTime;
    @SerializedName("holiday_start_time_second")
    @Expose
    private String holidayStartTimeSecond;
    @SerializedName("holiday_end_time_second")
    @Expose
    private String holidayEndTimeSecond;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAdminDistrict() {
        return adminDistrict;
    }

    public void setAdminDistrict(String adminDistrict) {
        this.adminDistrict = adminDistrict;
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

    public String getMinRadiusRange() {
        return minRadiusRange;
    }

    public void setMinRadiusRange(String minRadiusRange) {
        this.minRadiusRange = minRadiusRange;
    }

    public String getMaxRadiusRange() {
        return maxRadiusRange;
    }

    public void setMaxRadiusRange(String maxRadiusRange) {
        this.maxRadiusRange = maxRadiusRange;
    }

    public String getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(String minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayStartTime() {
        return holidayStartTime;
    }

    public void setHolidayStartTime(String holidayStartTime) {
        this.holidayStartTime = holidayStartTime;
    }

    public String getHolidayEndTime() {
        return holidayEndTime;
    }

    public void setHolidayEndTime(String holidayEndTime) {
        this.holidayEndTime = holidayEndTime;
    }

    public String getHolidayStartTimeSecond() {
        return holidayStartTimeSecond;
    }

    public void setHolidayStartTimeSecond(String holidayStartTimeSecond) {
        this.holidayStartTimeSecond = holidayStartTimeSecond;
    }

    public String getHolidayEndTimeSecond() {
        return holidayEndTimeSecond;
    }

    public void setHolidayEndTimeSecond(String holidayEndTimeSecond) {
        this.holidayEndTimeSecond = holidayEndTimeSecond;
    }

}
