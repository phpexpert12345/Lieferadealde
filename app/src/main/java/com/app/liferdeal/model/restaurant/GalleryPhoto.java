package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GalleryPhoto {

    @SerializedName("PhotoID")
    @Expose
    private Integer photoID;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("restaurant_ImageTitle")
    @Expose
    private String restaurantImageTitle;
    @SerializedName("food_photo")
    @Expose
    private String foodPhoto;

    public Integer getPhotoID() {
        return photoID;
    }

    public void setPhotoID(Integer photoID) {
        this.photoID = photoID;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRestaurantImageTitle() {
        return restaurantImageTitle;
    }

    public void setRestaurantImageTitle(String restaurantImageTitle) {
        this.restaurantImageTitle = restaurantImageTitle;
    }

    public String getFoodPhoto() {
        return foodPhoto;
    }

    public void setFoodPhoto(String foodPhoto) {
        this.foodPhoto = foodPhoto;
    }
}
