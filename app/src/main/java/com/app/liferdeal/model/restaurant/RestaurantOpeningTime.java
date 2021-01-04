package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantOpeningTime {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("MondayText")
    @Expose
    private String mondayText;
    @SerializedName("TuesdayText")
    @Expose
    private String tuesdayText;
    @SerializedName("WednesdayText")
    @Expose
    private String wednesdayText;
    @SerializedName("ThursdayText")
    @Expose
    private String thursdayText;
    @SerializedName("FridayText")
    @Expose
    private String fridayText;
    @SerializedName("SaturdayText")
    @Expose
    private String saturdayText;
    @SerializedName("SundayText")
    @Expose
    private String sundayText;

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

    public String getMondayText() {
        return mondayText;
    }

    public void setMondayText(String mondayText) {
        this.mondayText = mondayText;
    }

    public String getTuesdayText() {
        return tuesdayText;
    }

    public void setTuesdayText(String tuesdayText) {
        this.tuesdayText = tuesdayText;
    }

    public String getWednesdayText() {
        return wednesdayText;
    }

    public void setWednesdayText(String wednesdayText) {
        this.wednesdayText = wednesdayText;
    }

    public String getThursdayText() {
        return thursdayText;
    }

    public void setThursdayText(String thursdayText) {
        this.thursdayText = thursdayText;
    }

    public String getFridayText() {
        return fridayText;
    }

    public void setFridayText(String fridayText) {
        this.fridayText = fridayText;
    }

    public String getSaturdayText() {
        return saturdayText;
    }

    public void setSaturdayText(String saturdayText) {
        this.saturdayText = saturdayText;
    }

    public String getSundayText() {
        return sundayText;
    }

    public void setSundayText(String sundayText) {
        this.sundayText = sundayText;
    }
}
