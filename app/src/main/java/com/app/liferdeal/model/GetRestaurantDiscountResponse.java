package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class GetRestaurantDiscountResponse {

    @SerializedName("DiscountFoodItems")
    private String discountFoodItems;
    @SerializedName("discountOfferDescription")
    private String discountOfferDescription;
    @SerializedName("discountOfferPercentage")
    private String discountOfferPercentage;
    @SerializedName("discountOfferPrice")
    private String discountOfferPrice;

    public String getDiscountFoodItems() {
        return discountFoodItems;
    }

    public void setDiscountFoodItems(String discountFoodItems) {
        this.discountFoodItems = discountFoodItems;
    }

    public String getDiscountOfferDescription() {
        return discountOfferDescription;
    }

    public void setDiscountOfferDescription(String discountOfferDescription) {
        this.discountOfferDescription = discountOfferDescription;
    }

    public String getDiscountOfferPercentage() {
        return discountOfferPercentage;
    }

    public void setDiscountOfferPercentage(String discountOfferPercentage) {
        this.discountOfferPercentage = discountOfferPercentage;
    }

    public String getDiscountOfferPrice() {
        return discountOfferPrice;
    }

    public void setDiscountOfferPrice(String discountOfferPrice) {
        this.discountOfferPrice = discountOfferPrice;
    }

}
