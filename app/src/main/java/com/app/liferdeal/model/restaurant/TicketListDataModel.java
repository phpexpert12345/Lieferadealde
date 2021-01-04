package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketListDataModel {
    @SerializedName("ComplaintsHistory")
    @Expose
    private List<ComplaintsHistory> complaintsHistory = null;

    public List<ComplaintsHistory> getComplaintsHistory() {
        return complaintsHistory;
    }

    public void setComplaintsHistory(List<ComplaintsHistory> complaintsHistory) {
        this.complaintsHistory = complaintsHistory;
    }

    public class ComplaintsHistory {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("complaint_id")
        @Expose
        private String complaintId;
        @SerializedName("orderIssue")
        @Expose
        private String orderIssue;
        @SerializedName("contact_name")
        @Expose
        private String contactName;
        @SerializedName("contact_email")
        @Expose
        private String contactEmail;
        @SerializedName("contact_phone")
        @Expose
        private String contactPhone;
        @SerializedName("resid")
        @Expose
        private Integer resid;
        @SerializedName("orderIDNumber")
        @Expose
        private String orderIDNumber;
        @SerializedName("orderIssueEmail")
        @Expose
        private String orderIssueEmail;
        @SerializedName("Order_Status_Message")
        @Expose
        private String orderStatusMessage;
        @SerializedName("orderIssueMessage")
        @Expose
        private String orderIssueMessage;
        @SerializedName("restaurant_order_issue_reply")
        @Expose
        private Object restaurantOrderIssueReply;
        @SerializedName("restaurant_order_issue_date")
        @Expose
        private String restaurantOrderIssueDate;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("restaurant_address")
        @Expose
        private String restaurantAddress;
        @SerializedName("restaurant_city")
        @Expose
        private Object restaurantCity;
        @SerializedName("restaurant_postcode")
        @Expose
        private String restaurantPostcode;
        @SerializedName("restaurant_mobile_number")
        @Expose
        private String restaurantMobileNumber;
        @SerializedName("restaurant_phone_number")
        @Expose
        private String restaurantPhoneNumber;
        @SerializedName("error")
        @Expose
        private String error;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getComplaintId() {
            return complaintId;
        }

        public void setComplaintId(String complaintId) {
            this.complaintId = complaintId;
        }

        public String getOrderIssue() {
            return orderIssue;
        }

        public void setOrderIssue(String orderIssue) {
            this.orderIssue = orderIssue;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public Integer getResid() {
            return resid;
        }

        public void setResid(Integer resid) {
            this.resid = resid;
        }

        public String getOrderIDNumber() {
            return orderIDNumber;
        }

        public void setOrderIDNumber(String orderIDNumber) {
            this.orderIDNumber = orderIDNumber;
        }

        public String getOrderIssueEmail() {
            return orderIssueEmail;
        }

        public void setOrderIssueEmail(String orderIssueEmail) {
            this.orderIssueEmail = orderIssueEmail;
        }

        public String getOrderStatusMessage() {
            return orderStatusMessage;
        }

        public void setOrderStatusMessage(String orderStatusMessage) {
            this.orderStatusMessage = orderStatusMessage;
        }

        public String getOrderIssueMessage() {
            return orderIssueMessage;
        }

        public void setOrderIssueMessage(String orderIssueMessage) {
            this.orderIssueMessage = orderIssueMessage;
        }

        public Object getRestaurantOrderIssueReply() {
            return restaurantOrderIssueReply;
        }

        public void setRestaurantOrderIssueReply(Object restaurantOrderIssueReply) {
            this.restaurantOrderIssueReply = restaurantOrderIssueReply;
        }

        public String getRestaurantOrderIssueDate() {
            return restaurantOrderIssueDate;
        }

        public void setRestaurantOrderIssueDate(String restaurantOrderIssueDate) {
            this.restaurantOrderIssueDate = restaurantOrderIssueDate;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getRestaurantAddress() {
            return restaurantAddress;
        }

        public void setRestaurantAddress(String restaurantAddress) {
            this.restaurantAddress = restaurantAddress;
        }

        public Object getRestaurantCity() {
            return restaurantCity;
        }

        public void setRestaurantCity(Object restaurantCity) {
            this.restaurantCity = restaurantCity;
        }

        public String getRestaurantPostcode() {
            return restaurantPostcode;
        }

        public void setRestaurantPostcode(String restaurantPostcode) {
            this.restaurantPostcode = restaurantPostcode;
        }

        public String getRestaurantMobileNumber() {
            return restaurantMobileNumber;
        }

        public void setRestaurantMobileNumber(String restaurantMobileNumber) {
            this.restaurantMobileNumber = restaurantMobileNumber;
        }

        public String getRestaurantPhoneNumber() {
            return restaurantPhoneNumber;
        }

        public void setRestaurantPhoneNumber(String restaurantPhoneNumber) {
            this.restaurantPhoneNumber = restaurantPhoneNumber;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

    }
}
