
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("error")
    private String error;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("food_photo")
    private String foodPhoto;
    @SerializedName("PhotoID")
    private Long photoID;
    @SerializedName("restaurant_ImageTitle")
    private String restaurantImageTitle;

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

    public String getFoodPhoto() {
        return foodPhoto;
    }

    public void setFoodPhoto(String foodPhoto) {
        this.foodPhoto = foodPhoto;
    }

    public Long getPhotoID() {
        return photoID;
    }

    public void setPhotoID(Long photoID) {
        this.photoID = photoID;
    }

    public String getRestaurantImageTitle() {
        return restaurantImageTitle;
    }

    public void setRestaurantImageTitle(String restaurantImageTitle) {
        this.restaurantImageTitle = restaurantImageTitle;
    }

}
