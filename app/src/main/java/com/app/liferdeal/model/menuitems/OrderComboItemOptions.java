package com.app.liferdeal.model.menuitems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderComboItemOptions {
    @SerializedName("ComboOptionName")
    @Expose
    private String ComboOptionName;
    @SerializedName("ComboOptionItemName")
    @Expose
    private String ComboOptionItemName;
    @SerializedName("ComboOptionItemSizeName")
    @Expose
    private String ComboOptionItemSizeName;

    public String getComboOptionName() {
        return ComboOptionName;
    }

    public void setComboOptionName(String comboOptionName) {
        ComboOptionName = comboOptionName;
    }

    public String getComboOptionItemName() {
        return ComboOptionItemName;
    }

    public void setComboOptionItemName(String comboOptionItemName) {
        ComboOptionItemName = comboOptionItemName;
    }

    public String getComboOptionItemSizeName() {
        return ComboOptionItemSizeName;
    }

    public void setComboOptionItemSizeName(String comboOptionItemSizeName) {
        ComboOptionItemSizeName = comboOptionItemSizeName;
    }

    public List<OrderComboItemExtras> getOrderComboItemExtra() {
        return OrderComboItemExtra;
    }

    public void setOrderComboItemExtra(List<OrderComboItemExtras> orderComboItemExtra) {
        OrderComboItemExtra = orderComboItemExtra;
    }

    @SerializedName("OrderComboItemExtra")
    @Expose
    private List<OrderComboItemExtras>OrderComboItemExtra= new ArrayList<>();

}
