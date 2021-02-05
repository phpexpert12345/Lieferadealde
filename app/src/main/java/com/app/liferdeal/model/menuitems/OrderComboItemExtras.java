package com.app.liferdeal.model.menuitems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderComboItemExtras {
    @SerializedName("ComboExtraItemName")
    @Expose
    private String ComboExtraItemName;
    @SerializedName("ComboExtraItemQuantity")
    @Expose
    private String ComboExtraItemQuantity;

    public String getComboExtraItemName() {
        return ComboExtraItemName;
    }

    public void setComboExtraItemName(String comboExtraItemName) {
        ComboExtraItemName = comboExtraItemName;
    }

    public String getComboExtraItemQuantity() {
        return ComboExtraItemQuantity;
    }

    public void setComboExtraItemQuantity(String comboExtraItemQuantity) {
        ComboExtraItemQuantity = comboExtraItemQuantity;
    }

    public String getComboExtraItemPrice() {
        return ComboExtraItemPrice;
    }

    public void setComboExtraItemPrice(String comboExtraItemPrice) {
        ComboExtraItemPrice = comboExtraItemPrice;
    }

    @SerializedName("ComboExtraItemPrice")
    @Expose
    private String ComboExtraItemPrice;
}
