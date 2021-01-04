
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodGallery {

    @SerializedName("error")
    private String error;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("GalleryPhoto")
    private List<Photo> galleryPhoto;
    @SerializedName("photo_tab_id")
    private Long photoTabId;
    @SerializedName("tab_name")
    private String tabName;

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

    public List<Photo> getGalleryPhoto() {
        return galleryPhoto;
    }

    public void setGalleryPhoto(List<Photo> galleryPhoto) {
        this.galleryPhoto = galleryPhoto;
    }

    public Long getPhotoTabId() {
        return photoTabId;
    }

    public void setPhotoTabId(Long photoTabId) {
        this.photoTabId = photoTabId;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

}
