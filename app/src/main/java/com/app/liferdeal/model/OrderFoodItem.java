
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class OrderFoodItem {

    @SerializedName("Currency")
    private String currency;
    @SerializedName("ExtraTopping")
    private String extraTopping;
    @SerializedName("item_size")
    private String itemSize;
    @SerializedName("ItemsName")
    private String itemsName;
    @SerializedName("menuprice")
    private String menuprice;
    @SerializedName("quantity")
    private Long quantity;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExtraTopping() {
        return extraTopping;
    }

    public void setExtraTopping(String extraTopping) {
        this.extraTopping = extraTopping;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getMenuprice() {
        return menuprice;
    }

    public void setMenuprice(String menuprice) {
        this.menuprice = menuprice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
