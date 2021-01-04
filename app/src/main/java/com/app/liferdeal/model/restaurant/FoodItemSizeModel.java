package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodItemSizeModel {
    @SerializedName("RestaurantMenItemsSize")
    @Expose
    private List<RestaurantMenItemsSize> restaurantMenItemsSize = null;

    public List<RestaurantMenItemsSize> getRestaurantMenItemsSize() {
        return restaurantMenItemsSize;
    }

    public void setRestaurantMenItemsSize(List<RestaurantMenItemsSize> restaurantMenItemsSize) {
        this.restaurantMenItemsSize = restaurantMenItemsSize;
    }

    public class RestaurantMenItemsSize {

        @SerializedName("extraavailable")
        @Expose
        private String extraavailable;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("FoodItemSizeID")
        @Expose
        private Integer foodItemSizeID;
        @SerializedName("FoodItemID")
        @Expose
        private Integer foodItemID;
        @SerializedName("FoodItemName")
        @Expose
        private String foodItemName;
        @SerializedName("RestaurantPizzaItemName")
        @Expose
        private String restaurantPizzaItemName;
        @SerializedName("RestaurantPizzaItemOldPrice")
        @Expose
        private String restaurantPizzaItemOldPrice;
        @SerializedName("RestaurantPizzaItemPrice")
        @Expose
        private String restaurantPizzaItemPrice;

        public String getExtraavailable() {
            return extraavailable;
        }

        public void setExtraavailable(String extraavailable) {
            this.extraavailable = extraavailable;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getFoodItemSizeID() {
            return foodItemSizeID;
        }

        public void setFoodItemSizeID(Integer foodItemSizeID) {
            this.foodItemSizeID = foodItemSizeID;
        }

        public Integer getFoodItemID() {
            return foodItemID;
        }

        public void setFoodItemID(Integer foodItemID) {
            this.foodItemID = foodItemID;
        }

        public String getFoodItemName() {
            return foodItemName;
        }

        public void setFoodItemName(String foodItemName) {
            this.foodItemName = foodItemName;
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
    }
}
