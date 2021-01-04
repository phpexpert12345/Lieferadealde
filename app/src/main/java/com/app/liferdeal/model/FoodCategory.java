package com.app.liferdeal.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class FoodCategory {

    @SerializedName("category_description")
    private String categoryDescription;
    @SerializedName("Category_ID")
    private Long categoryID;
    @SerializedName("category_img")
    private String categoryImg;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("error")
    private Long error;
    @SerializedName("Favorites_display")
    private String favoritesDisplay;
    @SerializedName("id")
    private Long id;
    @SerializedName("restaurant_id")
    private String restaurantId;
    @SerializedName("sc_obj_id")
    private String scObjId;

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

    public String getFavoritesDisplay() {
        return favoritesDisplay;
    }

    public void setFavoritesDisplay(String favoritesDisplay) {
        this.favoritesDisplay = favoritesDisplay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
