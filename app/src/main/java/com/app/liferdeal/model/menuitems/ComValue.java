package com.app.liferdeal.model.menuitems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class ComValue {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String error_msg;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("comboslot_id")
    @Expose
    private String comboslot_id;

    public String getSlot_Option_Name() {
        return Slot_Option_Name;
    }

    public void setSlot_Option_Name(String slot_Option_Name) {
        Slot_Option_Name = slot_Option_Name;
    }

    @SerializedName("Slot_Option_Name")
    @Expose
    private String Slot_Option_Name;
    @SerializedName("Free_allowed")
    @Expose
    private String Free_allowed;
    @SerializedName("Free_Topping_Selection_allowed")
    @Expose
    private String Free_Topping_Selection_allowed;

    public int getTopping_Selected() {
        return Topping_Selected;
    }

    public void setTopping_Selected(int topping_Selected) {
        Topping_Selected = topping_Selected;
    }

    @SerializedName("Topping_Selected")
    @Expose
    private int Topping_Selected=0;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComboslot_id() {
        return comboslot_id;
    }

    public void setComboslot_id(String comboslot_id) {
        this.comboslot_id = comboslot_id;
    }

    public String getFree_allowed() {
        return Free_allowed;
    }

    public void setFree_allowed(String free_allowed) {
        Free_allowed = free_allowed;
    }

    public String getFree_Topping_Selection_allowed() {
        return Free_Topping_Selection_allowed;
    }

    public void setFree_Topping_Selection_allowed(String free_Topping_Selection_allowed) {
        Free_Topping_Selection_allowed = free_Topping_Selection_allowed;
    }

    public List<ComValueItem> getComboSectionValueItems() {
        return ComboSectionValueItems;
    }

    public void setComboSectionValueItems(List<ComValueItem> comboSectionValueItems) {
        ComboSectionValueItems = comboSectionValueItems;
    }

    @SerializedName("ComboSectionValueItems")
    @Expose
    private List<ComValueItem> ComboSectionValueItems;
//    "error": "0", "error_msg": "success", "id": 222, "comboslot_id": 222, "Slot_Option_Name": "Drink section", "Free_allowed": "", "Free_Topping_Selection_allowed": ""
}
