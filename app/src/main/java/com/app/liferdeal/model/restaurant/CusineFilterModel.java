package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CusineFilterModel {

    @SerializedName("CuisineList")
    @Expose
    private List<CuisineList> cuisineList = null;

    public List<CuisineList> getCuisineList() {
        return cuisineList;
    }

    public void setCuisineList(List<CuisineList> cuisineList) {
        this.cuisineList = cuisineList;
    }
    public class CuisineList {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("cuisine_name")
        @Expose
        private String cuisineName;
        @SerializedName("cuisine_desc")
        @Expose
        private String cuisineDesc;
        @SerializedName("cuisine_img")
        @Expose
        private String cuisineImg;
        @SerializedName("error")
        @Expose
        private Integer error;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCuisineName() {
            return cuisineName;
        }

        public void setCuisineName(String cuisineName) {
            this.cuisineName = cuisineName;
        }

        public String getCuisineDesc() {
            return cuisineDesc;
        }

        public void setCuisineDesc(String cuisineDesc) {
            this.cuisineDesc = cuisineDesc;
        }

        public String getCuisineImg() {
            return cuisineImg;
        }

        public void setCuisineImg(String cuisineImg) {
            this.cuisineImg = cuisineImg;
        }

        public Integer getError() {
            return error;
        }

        public void setError(Integer error) {
            this.error = error;
        }
    }
}
