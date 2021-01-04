package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class RmGetServiceChargeResponse {

    @SerializedName("deliveryChargeValue")
    private String deliveryChargeValue;
    @SerializedName("PackageFees")
    private String packageFees;
    @SerializedName("PackageFeesType")
    private String packageFeesType;
    @SerializedName("PackageFeesValue")
    private String packageFeesValue;
    @SerializedName("SalesTaxAmount")
    private String salesTaxAmount;
    @SerializedName("ServiceFees")
    private String serviceFees;
    @SerializedName("ServiceFeesType")
    private String serviceFeesType;
    @SerializedName("SeviceFeesValue")
    private String seviceFeesValue;
    @SerializedName("VatTax")
    private String vatTax;

    public String getDeliveryChargeValue() {
        return deliveryChargeValue;
    }

    public void setDeliveryChargeValue(String deliveryChargeValue) {
        this.deliveryChargeValue = deliveryChargeValue;
    }

    public String getPackageFees() {
        return packageFees;
    }

    public void setPackageFees(String packageFees) {
        this.packageFees = packageFees;
    }

    public String getPackageFeesType() {
        return packageFeesType;
    }

    public void setPackageFeesType(String packageFeesType) {
        this.packageFeesType = packageFeesType;
    }

    public String getPackageFeesValue() {
        return packageFeesValue;
    }

    public void setPackageFeesValue(String packageFeesValue) {
        this.packageFeesValue = packageFeesValue;
    }

    public String getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(String salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public String getServiceFees() {
        return serviceFees;
    }

    public void setServiceFees(String serviceFees) {
        this.serviceFees = serviceFees;
    }

    public String getServiceFeesType() {
        return serviceFeesType;
    }

    public void setServiceFeesType(String serviceFeesType) {
        this.serviceFeesType = serviceFeesType;
    }

    public String getSeviceFeesValue() {
        return seviceFeesValue;
    }

    public void setSeviceFeesValue(String seviceFeesValue) {
        this.seviceFeesValue = seviceFeesValue;
    }

    public String getVatTax() {
        return vatTax;
    }

    public void setVatTax(String vatTax) {
        this.vatTax = vatTax;
    }

}
