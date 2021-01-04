package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Table implements Serializable {

    @SerializedName("available_for_book")
    private String availableForBook;
    @SerializedName("error")
    private String error;
    @SerializedName("id")
    private Long id;
    @SerializedName("number_of_people")
    private String numberOfPeople;
    @SerializedName("table_icon_img")
    private String tableIconImg;
    @SerializedName("table_name")
    private String tableName;
    @SerializedName("table_number")
    private String tableNumber;
    @SerializedName("tabl_per_person_charge")
    private String tablePerPersonCharge;
    @SerializedName("minimum_deposit_percentage")
    private String minimumDepositPercentage;
    @SerializedName("table_discount_amount")
    private String tableDiscountAmount;
    @SerializedName("table_service_charge_amount")
    private String tableServiceChargeAmount;
    @SerializedName("discount_available_days")
    private String discountAvailableDays;


    public String getAvailableForBook() {
        return availableForBook;
    }

    public void setAvailableForBook(String availableForBook) {
        this.availableForBook = availableForBook;
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

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getTableIconImg() {
        return tableIconImg;
    }

    public void setTableIconImg(String tableIconImg) {
        this.tableIconImg = tableIconImg;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getTablePerPersonCharge() {
        return tablePerPersonCharge;
    }

    public void setTablePerPersonCharge(String tablePerPersonCharge) {
        this.tablePerPersonCharge = tablePerPersonCharge;
    }

    public String getMinimumDepositPercentage() {
        return minimumDepositPercentage;
    }

    public void setMinimumDepositPercentage(String minimumDepositPercentage) {
        this.minimumDepositPercentage = minimumDepositPercentage;
    }

    public String getTableDiscountAmount() {
        return tableDiscountAmount;
    }

    public void setTableDiscountAmount(String tableDiscountAmount) {
        this.tableDiscountAmount = tableDiscountAmount;
    }

    public String getTableServiceChargeAmount() {
        return tableServiceChargeAmount;
    }

    public void setTableServiceChargeAmount(String tableServiceChargeAmount) {
        this.tableServiceChargeAmount = tableServiceChargeAmount;
    }

    public String getDiscountAvailableDays() {
        return discountAvailableDays;
    }

    public void setDiscountAvailableDays(String discountAvailableDays) {
        this.discountAvailableDays = discountAvailableDays;
    }
}
