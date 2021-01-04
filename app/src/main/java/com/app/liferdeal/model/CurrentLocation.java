package com.app.liferdeal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentLocation {

    @SerializedName("customer_city")
    @Expose
    private String customerCity;
    @SerializedName("customer_locality")
    @Expose
    private String customerLocality;
    @SerializedName("customer_country")
    @Expose
    private String customerCountry;
    @SerializedName("customer_postcode")
    @Expose
    private String customerPostcode;
    @SerializedName("customer_full_address")
    @Expose
    private String customerFullAddress;
    @SerializedName("customer_lat")
    @Expose
    private String customerLat;
    @SerializedName("customer_long")
    @Expose
    private String customerLong;

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerLocality() {
        return customerLocality;
    }

    public void setCustomerLocality(String customerLocality) {
        this.customerLocality = customerLocality;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerPostcode() {
        return customerPostcode;
    }

    public void setCustomerPostcode(String customerPostcode) {
        this.customerPostcode = customerPostcode;
    }

    public String getCustomerFullAddress() {
        return customerFullAddress;
    }

    public void setCustomerFullAddress(String customerFullAddress) {
        this.customerFullAddress = customerFullAddress;
    }

    public String getCustomerLat() {
        return customerLat;
    }

    public void setCustomerLat(String customerLat) {
        this.customerLat = customerLat;
    }

    public String getCustomerLong() {
        return customerLong;
    }

    public void setCustomerLong(String customerLong) {
        this.customerLong = customerLong;
    }

}
