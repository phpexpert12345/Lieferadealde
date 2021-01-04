package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantGalleryModel {

    @SerializedName("FoodGalleryList")
    @Expose
    private List<FoodGalleryList> foodGalleryList = null;

    public List<FoodGalleryList> getFoodGalleryList() {
        return foodGalleryList;
    }

    public void setFoodGalleryList(List<FoodGalleryList> foodGalleryList) {
        this.foodGalleryList = foodGalleryList;
    }

    public class FoodGalleryList {

        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("error_msg")
        @Expose
        private String errorMsg;
        @SerializedName("photo_tab_id")
        @Expose
        private Integer photoTabId;
        @SerializedName("tab_name")
        @Expose
        private String tabName;
        @SerializedName("GalleryPhoto")
        @Expose
        private List<GalleryPhoto> galleryPhoto = null;

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

        public Integer getPhotoTabId() {
            return photoTabId;
        }

        public void setPhotoTabId(Integer photoTabId) {
            this.photoTabId = photoTabId;
        }

        public String getTabName() {
            return tabName;
        }

        public void setTabName(String tabName) {
            this.tabName = tabName;
        }

        public List<GalleryPhoto> getGalleryPhoto() {
            return galleryPhoto;
        }

        public void setGalleryPhoto(List<GalleryPhoto> galleryPhoto) {
            this.galleryPhoto = galleryPhoto;
        }

    }
}