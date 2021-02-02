package com.app.liferdeal.model.menuitems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComValueItem {
    @SerializedName("Combo_Slot_ItemID")
    @Expose
    private Integer Combo_Slot_ItemID;
    @SerializedName("Combo_Slot_ItemName")
    @Expose
    private String Combo_Slot_ItemName;
    @SerializedName("ItemID")
    @Expose
    private Integer ItemID;
    @SerializedName("FoodItemSizeID")
    @Expose
    private String FoodItemSizeID;

    public Integer getCombo_Slot_ItemID() {
        return Combo_Slot_ItemID;
    }

    public void setCombo_Slot_ItemID(Integer combo_Slot_ItemID) {
        Combo_Slot_ItemID = combo_Slot_ItemID;
    }

    public String getCombo_Slot_ItemName() {
        return Combo_Slot_ItemName;
    }

    public void setCombo_Slot_ItemName(String combo_Slot_ItemName) {
        Combo_Slot_ItemName = combo_Slot_ItemName;
    }

    public Integer getItemID() {
        return ItemID;
    }

    public void setItemID(Integer itemID) {
        ItemID = itemID;
    }

    public String getFoodItemSizeID() {
        return FoodItemSizeID;
    }

    public void setFoodItemSizeID(String foodItemSizeID) {
        FoodItemSizeID = foodItemSizeID;
    }

    public String getCombo_Topping_Allow() {
        return Combo_Topping_Allow;
    }

    public void setCombo_Topping_Allow(String combo_Topping_Allow) {
        Combo_Topping_Allow = combo_Topping_Allow;
    }

    @SerializedName("Combo_Topping_Allow")
    @Expose
    private String Combo_Topping_Allow;

//    {
//        "Combo_Slot_ItemID": 764,
//            "Combo_Slot_ItemName": "Apfelsaft 1,0l ",
//            "FoodItemSizeID": "",
//            "ItemID": 204,
//            "Combo_Topping_Allow": "No"
//    }
}
