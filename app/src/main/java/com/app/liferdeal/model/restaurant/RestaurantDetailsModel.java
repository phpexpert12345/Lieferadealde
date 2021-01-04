package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantDetailsModel {
    @SerializedName("RestaurantMencategory")
    @Expose
    private List<RestaurantMencategory> restaurantMencategory = null;

    public List<RestaurantMencategory> getRestaurantMencategory() {
        return restaurantMencategory;
    }

    public void setRestaurantMencategory(List<RestaurantMencategory> restaurantMencategory) {
        this.restaurantMencategory = restaurantMencategory;
    }

    public class RestaurantMencategory {

        @SerializedName("Combo_Available")
        @Expose
        private String comboAvailable;
        @SerializedName("Favorites_display")
        @Expose
        private String favoritesDisplay;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("Category_ID")
        @Expose
        private Integer categoryID;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("sc_obj_id")
        @Expose
        private String scObjId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("category_description")
        @Expose
        private String categoryDescription;
        @SerializedName("category_img")
        @Expose
        private String categoryImg;
        @SerializedName("error")
        @Expose
        private Integer error;

        public String getError_msg() {
            return error_msg;
        }

        public void setError_msg(String error_msg) {
            this.error_msg = error_msg;
        }

        @SerializedName("error_msg")
        @Expose
        private String error_msg;

        public String getComboAvailable() {
            return comboAvailable;
        }

        public void setComboAvailable(String comboAvailable) {
            this.comboAvailable = comboAvailable;
        }

        public String getFavoritesDisplay() {
            return favoritesDisplay;
        }

        public void setFavoritesDisplay(String favoritesDisplay) {
            this.favoritesDisplay = favoritesDisplay;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(Integer categoryID) {
            this.categoryID = categoryID;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getScObjId() {
            return scObjId;
        }

        public void setScObjId(String scObjId) {
            this.scObjId = scObjId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryDescription() {
            return categoryDescription;
        }

        public void setCategoryDescription(String categoryDescription) {
            this.categoryDescription = categoryDescription;
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }

        public Integer getError() {
            return error;
        }

        public void setError(Integer error) {
            this.error = error;
        }
    }
}
