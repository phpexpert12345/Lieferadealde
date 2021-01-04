package com.app.liferdeal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deliveryaddress {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("user_locality")
    @Expose
    private Object userLocality;
    @SerializedName("customerState")
    @Expose
    private Object customerState;
    @SerializedName("customerCountry")
    @Expose
    private Object customerCountry;
    @SerializedName("address_direction")
    @Expose
    private String addressDirection;
    @SerializedName("customer_address_lat")
    @Expose
    private String customerAddressLat;
    @SerializedName("customer_address_long")
    @Expose
    private String customerAddressLong;
    @SerializedName("customerFloor_House_Number")
    @Expose
    private Object customerFloorHouseNumber;
    @SerializedName("customerFlat_Name")
    @Expose
    private String customerFlatName;
    @SerializedName("company_street")
    @Expose
    private String companyStreet;
    @SerializedName("company_streetNo")
    @Expose
    private String companyStreetNo;
    @SerializedName("address_title")
    @Expose
    private String addressTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Object getUserLocality() {
        return userLocality;
    }

    public void setUserLocality(Object userLocality) {
        this.userLocality = userLocality;
    }

    public Object getCustomerState() {
        return customerState;
    }

    public void setCustomerState(Object customerState) {
        this.customerState = customerState;
    }

    public Object getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(Object customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getAddressDirection() {
        return addressDirection;
    }

    public void setAddressDirection(String addressDirection) {
        this.addressDirection = addressDirection;
    }

    public String getCustomerAddressLat() {
        return customerAddressLat;
    }

    public void setCustomerAddressLat(String customerAddressLat) {
        this.customerAddressLat = customerAddressLat;
    }

    public String getCustomerAddressLong() {
        return customerAddressLong;
    }

    public void setCustomerAddressLong(String customerAddressLong) {
        this.customerAddressLong = customerAddressLong;
    }

    public Object getCustomerFloorHouseNumber() {
        return customerFloorHouseNumber;
    }

    public void setCustomerFloorHouseNumber(Object customerFloorHouseNumber) {
        this.customerFloorHouseNumber = customerFloorHouseNumber;
    }

    public String getCustomerFlatName() {
        return customerFlatName;
    }

    public void setCustomerFlatName(String customerFlatName) {
        this.customerFlatName = customerFlatName;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    public String getCompanyStreetNo() {
        return companyStreetNo;
    }

    public void setCompanyStreetNo(String companyStreetNo) {
        this.companyStreetNo = companyStreetNo;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

}
