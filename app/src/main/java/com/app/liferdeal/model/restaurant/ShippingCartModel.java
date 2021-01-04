package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingCartModel {
    @SerializedName("deliveryChargeValue")
    @Expose
    private String deliveryChargeValue;
    @SerializedName("SeviceFeesValue")
    @Expose
    private String seviceFeesValue;
    @SerializedName("ServiceFees")
    @Expose
    private String serviceFees;
    @SerializedName("ServiceFeesType")
    @Expose
    private String serviceFeesType;
    @SerializedName("PackageFeesType")
    @Expose
    private String packageFeesType;
    @SerializedName("PackageFees")
    @Expose
    private String packageFees;
    @SerializedName("PackageFeesValue")
    @Expose
    private String packageFeesValue;
    @SerializedName("SalesTaxAmount")
    @Expose
    private String salesTaxAmount;
    @SerializedName("VatTax")
    @Expose
    private String vatTax;

    public String getDeliveryChargeValue() {
        return deliveryChargeValue;
    }

    public void setDeliveryChargeValue(String deliveryChargeValue) {
        this.deliveryChargeValue = deliveryChargeValue;
    }

    public String getSeviceFeesValue() {
        return seviceFeesValue;
    }

    public void setSeviceFeesValue(String seviceFeesValue) {
        this.seviceFeesValue = seviceFeesValue;
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

    public String getPackageFeesType() {
        return packageFeesType;
    }

    public void setPackageFeesType(String packageFeesType) {
        this.packageFeesType = packageFeesType;
    }

    public String getPackageFees() {
        return packageFees;
    }

    public void setPackageFees(String packageFees) {
        this.packageFees = packageFees;
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

    public String getVatTax() {
        return vatTax;
    }

    public void setVatTax(String vatTax) {
        this.vatTax = vatTax;
    }
}
