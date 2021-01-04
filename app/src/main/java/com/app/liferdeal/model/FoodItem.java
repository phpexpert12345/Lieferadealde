package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

public class FoodItem implements Serializable {

    @SerializedName("extraavailable")
    private String extraavailable;
    @SerializedName("FoodItemID")
    private Long foodItemID;
    @SerializedName("FoodItemName")
    private String foodItemName;
    @SerializedName("id")
    private Long id;
    @SerializedName("RestaurantPizzaItemName")
    private String restaurantPizzaItemName;
    @SerializedName("RestaurantPizzaItemOldPrice")
    private String restaurantPizzaItemOldPrice;
    @SerializedName("RestaurantPizzaItemPrice")
    private String restaurantPizzaItemPrice;

    public String getExtraavailable() {
        return extraavailable;
    }

    public void setExtraavailable(String extraavailable) {
        this.extraavailable = extraavailable;
    }

    public Long getFoodItemID() {
        return foodItemID;
    }

    public void setFoodItemID(Long foodItemID) {
        this.foodItemID = foodItemID;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantPizzaItemName() {
        return restaurantPizzaItemName;
    }

    public void setRestaurantPizzaItemName(String restaurantPizzaItemName) {
        this.restaurantPizzaItemName = restaurantPizzaItemName;
    }

    public String getRestaurantPizzaItemOldPrice() {
        return restaurantPizzaItemOldPrice;
    }

    public void setRestaurantPizzaItemOldPrice(String restaurantPizzaItemOldPrice) {
        this.restaurantPizzaItemOldPrice = restaurantPizzaItemOldPrice;
    }

    public String getRestaurantPizzaItemPrice() {
        return restaurantPizzaItemPrice;
    }

    public void setRestaurantPizzaItemPrice(String restaurantPizzaItemPrice) {
        this.restaurantPizzaItemPrice = restaurantPizzaItemPrice;
    }

    public static class PriceComparator implements Comparator<FoodItem> {

        @Override
        public int compare(FoodItem o1, FoodItem o2) {
            return (int) (Float.parseFloat(o1.getRestaurantPizzaItemPrice()) - Float.parseFloat(o2.getRestaurantPizzaItemPrice()));
        }
    }
}
