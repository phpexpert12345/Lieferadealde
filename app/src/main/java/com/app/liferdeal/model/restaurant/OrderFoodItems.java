package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderFoodItems {

    @SerializedName("ItemsName")
    @Expose
    private String itemsName;
    @SerializedName("item_size")
    @Expose
    private String itemSize;
    @SerializedName("ExtraTopping")
    @Expose
    private String extraTopping;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("menuprice")
    @Expose
    private String menuprice;

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getExtraTopping() {
        return extraTopping;
    }

    public void setExtraTopping(String extraTopping) {
        this.extraTopping = extraTopping;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMenuprice() {
        return menuprice;
    }

    public void setMenuprice(String menuprice) {
        this.menuprice = menuprice;
    }
}
