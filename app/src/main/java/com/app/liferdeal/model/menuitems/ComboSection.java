package com.app.liferdeal.model.menuitems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComboSection {
//    { "error": "0", "error_msg": "success", "id": 13, "deal_slot_name": "Any one nudeln + large pizza with two free topping 500 ml any drink",
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String error_msg;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("deal_slot_name")
    @Expose
    private String deal_slot_name;

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

    public String getDeal_slot_name() {
        return deal_slot_name;
    }

    public void setDeal_slot_name(String deal_slot_name) {
        this.deal_slot_name = deal_slot_name;
    }

    public List<ComValue> getComboSectionValue() {
        return ComboSectionValue;
    }

    public void setComboSectionValue(List<ComValue> comboSectionValue) {
        ComboSectionValue = comboSectionValue;
    }

    @SerializedName("ComboSectionValue")
    @Expose
    private List<ComValue> ComboSectionValue;

}
