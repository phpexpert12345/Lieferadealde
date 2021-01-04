package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestDetailClickFoodModel {
    @SerializedName("Menu_Cat")
    @Expose
    private List<RestMenuCat> menuCat = null;

    public List<RestMenuCat> getMenuCat() {
        return menuCat;
    }

    public void setMenuCat(List<RestMenuCat> menuCat) {
        this.menuCat = menuCat;
    }

}
