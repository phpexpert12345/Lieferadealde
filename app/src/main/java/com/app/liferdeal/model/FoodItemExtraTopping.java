
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class FoodItemExtraTopping {

    @SerializedName("error")
    private String error;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("ExtraID")
    private Long extraID;
    @SerializedName("Food_Addons_Name")
    private String foodAddonsName;
    @SerializedName("Food_Price_Addons")
    private String foodPriceAddons;
    @SerializedName("Free_Topping_Selection_allowed")
    private String freeToppingSelectionAllowed;

    private boolean isChecked = false;

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

    public Long getExtraID() {
        return extraID;
    }

    public void setExtraID(Long extraID) {
        this.extraID = extraID;
    }

    public String getFoodAddonsName() {
        return foodAddonsName;
    }

    public void setFoodAddonsName(String foodAddonsName) {
        this.foodAddonsName = foodAddonsName;
    }

    public String getFoodPriceAddons() {
        return foodPriceAddons;
    }

    public void setFoodPriceAddons(String foodPriceAddons) {
        this.foodPriceAddons = foodPriceAddons;
    }

    public String getFreeToppingSelectionAllowed() {
        return freeToppingSelectionAllowed;
    }

    public void setFreeToppingSelectionAllowed(String freeToppingSelectionAllowed) {
        this.freeToppingSelectionAllowed = freeToppingSelectionAllowed;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
