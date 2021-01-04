
package com.app.liferdeal.model.menuitems;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCategoryResponse {

    @SerializedName("Menu_Cat")
    @Expose
    private List<MenuCat> menuCat = null;

    public List<MenuCat> getMenuCat() {
        return menuCat;
    }

    public void setMenuCat(List<MenuCat> menuCat) {
        this.menuCat = menuCat;
    }

}
