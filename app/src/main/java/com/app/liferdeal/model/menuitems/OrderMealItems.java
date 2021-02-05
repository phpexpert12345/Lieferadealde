package com.app.liferdeal.model.menuitems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderMealItems {
    @SerializedName("error")
     @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String error_msg;
    @SerializedName("ItemsName")
    @Expose
    private String ItemsName;
    @SerializedName("ItemsDescriptionName")
    @Expose
    private String ItemsDescriptionName;
    @SerializedName("Currency")
    @Expose
    private String Currency;
    @SerializedName("menuprice")
    @Expose
    private String menuprice;
    @SerializedName("quqntity")
    @Expose
    private int quqntity;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getItemsName() {
        return ItemsName;
    }

    public void setItemsName(String itemsName) {
        ItemsName = itemsName;
    }

    public String getItemsDescriptionName() {
        return ItemsDescriptionName;
    }

    public void setItemsDescriptionName(String itemsDescriptionName) {
        ItemsDescriptionName = itemsDescriptionName;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getMenuprice() {
        return menuprice;
    }

    public void setMenuprice(String menuprice) {
        this.menuprice = menuprice;
    }

    public List<OrderComboItemOptions> getOrderComboItemOption() {
        return OrderComboItemOption;
    }

    public void setOrderComboItemOption(List<OrderComboItemOptions> orderComboItemOption) {
        OrderComboItemOption = orderComboItemOption;
    }

    @SerializedName("OrderComboItemOption")
    @Expose
    private List<OrderComboItemOptions> OrderComboItemOption=new ArrayList<>();

    public int getQuqntity() {
        return quqntity;
    }

    public void setQuqntity(int quqntity) {
        this.quqntity = quqntity;
    }


//    {
//        "error": "0",
//            "error_msg": "success",
//            "ItemsName": "Menu 1",
//            "ItemsDescriptionName": "2 small pizzas (\u00d8 26cm) each with 4 ingredients of your choice and 2 mixed salads",
//            "quqntity": 1,
//            "Currency": "USD",
//            "menuprice": "17.50"
//    }
}
