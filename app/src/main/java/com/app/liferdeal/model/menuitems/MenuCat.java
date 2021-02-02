
package com.app.liferdeal.model.menuitems;

import com.app.liferdeal.ui.interfaces.Section;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuCat implements Section {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("menu_category_time_enable")
    @Expose
    private String menuCategoryTimeEnable;
    @SerializedName("menu_time_open")
    @Expose
    private String menuTimeOpen;
    @SerializedName("menu_time_close")
    @Expose
    private String menuTimeClose;
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
    @SerializedName("subItemsRecord")
    @Expose
    private List<SubItemsRecord> subItemsRecord = null;

    public List<com.app.liferdeal.model.menuitems.ComboList> getComboList() {
        return ComboList;
    }

    public void setComboList(List<com.app.liferdeal.model.menuitems.ComboList> comboList) {
        ComboList = comboList;
    }

    @SerializedName("ComboList")
    @Expose
    private List<ComboList>  ComboList=null;
    int position;

    public MenuCat(int position) {
        this.position = position;
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

    public String getMenuCategoryTimeEnable() {
        return menuCategoryTimeEnable;
    }

    public void setMenuCategoryTimeEnable(String menuCategoryTimeEnable) {
        this.menuCategoryTimeEnable = menuCategoryTimeEnable;
    }

    public String getMenuTimeOpen() {
        return menuTimeOpen;
    }

    public void setMenuTimeOpen(String menuTimeOpen) {
        this.menuTimeOpen = menuTimeOpen;
    }

    public String getMenuTimeClose() {
        return menuTimeClose;
    }

    public void setMenuTimeClose(String menuTimeClose) {
        this.menuTimeClose = menuTimeClose;
    }

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

    public List<SubItemsRecord> getSubItemsRecord() {
        return subItemsRecord;
    }

    public void setSubItemsRecord(List<SubItemsRecord> subItemsRecord) {
        this.subItemsRecord = subItemsRecord;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public String getName() {
        return getCategoryName();
    }

    @Override
    public int sectionPosition() {
        return position;
    }
}
