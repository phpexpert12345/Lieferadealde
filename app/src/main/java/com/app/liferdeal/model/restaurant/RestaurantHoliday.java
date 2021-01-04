package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantHoliday {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("holiday_date")
    @Expose
    private String holidayDate;
    @SerializedName("holiday_start_time")
    @Expose
    private String holidayStartTime;
    @SerializedName("holiday_end_time")
    @Expose
    private String holidayEndTime;
    @SerializedName("holiday_start_time_second")
    @Expose
    private String holidayStartTimeSecond;
    @SerializedName("holiday_end_time_second")
    @Expose
    private String holidayEndTimeSecond;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayStartTime() {
        return holidayStartTime;
    }

    public void setHolidayStartTime(String holidayStartTime) {
        this.holidayStartTime = holidayStartTime;
    }

    public String getHolidayEndTime() {
        return holidayEndTime;
    }

    public void setHolidayEndTime(String holidayEndTime) {
        this.holidayEndTime = holidayEndTime;
    }

    public String getHolidayStartTimeSecond() {
        return holidayStartTimeSecond;
    }

    public void setHolidayStartTimeSecond(String holidayStartTimeSecond) {
        this.holidayStartTimeSecond = holidayStartTimeSecond;
    }

    public String getHolidayEndTimeSecond() {
        return holidayEndTimeSecond;
    }

    public void setHolidayEndTimeSecond(String holidayEndTimeSecond) {
        this.holidayEndTimeSecond = holidayEndTimeSecond;
    }
}
