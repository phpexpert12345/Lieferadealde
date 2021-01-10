package com.app.liferdeal.model.restaurant;

import com.app.liferdeal.model.CuisineList;
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
}
