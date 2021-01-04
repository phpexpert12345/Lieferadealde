package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantBranch {

    @SerializedName("Branch_delivery_distance")
    private String branchDeliveryDistance;
    @SerializedName("branch_latitude")
    private String branchLatitude;
    @SerializedName("branch_longitude")
    private String branchLongitude;
    @SerializedName("Branch_Mobile")
    private String branchMobile;
    @SerializedName("error")
    private String error;
    @SerializedName("id")
    private Long id;
    @SerializedName("RestaurantBranch_Address")
    private String restaurantBranchAddress;
    @SerializedName("RestaurantBranchName")
    private String restaurantBranchName;
    @SerializedName("RestaurantBranchZipCode")
    private String restaurantBranchZipCode;

    public String getBranchDeliveryDistance() {
        return branchDeliveryDistance;
    }

    public void setBranchDeliveryDistance(String branchDeliveryDistance) {
        this.branchDeliveryDistance = branchDeliveryDistance;
    }

    public String getBranchLatitude() {
        return branchLatitude;
    }

    public void setBranchLatitude(String branchLatitude) {
        this.branchLatitude = branchLatitude;
    }

    public String getBranchLongitude() {
        return branchLongitude;
    }

    public void setBranchLongitude(String branchLongitude) {
        this.branchLongitude = branchLongitude;
    }

    public String getBranchMobile() {
        return branchMobile;
    }

    public void setBranchMobile(String branchMobile) {
        this.branchMobile = branchMobile;
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

    public String getRestaurantBranchAddress() {
        return restaurantBranchAddress;
    }

    public void setRestaurantBranchAddress(String restaurantBranchAddress) {
        this.restaurantBranchAddress = restaurantBranchAddress;
    }

    public String getRestaurantBranchName() {
        return restaurantBranchName;
    }

    public void setRestaurantBranchName(String restaurantBranchName) {
        this.restaurantBranchName = restaurantBranchName;
    }

    public String getRestaurantBranchZipCode() {
        return restaurantBranchZipCode;
    }

    public void setRestaurantBranchZipCode(String restaurantBranchZipCode) {
        this.restaurantBranchZipCode = restaurantBranchZipCode;
    }

}
