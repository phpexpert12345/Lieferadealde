package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeListModel {
    @SerializedName("TimeList")
    @Expose
    private List<TimeList> timeList = null;

    public List<TimeList> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<TimeList> timeList) {
        this.timeList = timeList;
    }

    public class TimeList {

        @SerializedName("GetTime")
        @Expose
        private String getTime;
        @SerializedName("success")
        @Expose
        private String success;

        public String getGetTime() {
            return getTime;
        }

        public void setGetTime(String getTime) {
            this.getTime = getTime;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

    }
}
