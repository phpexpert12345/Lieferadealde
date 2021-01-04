
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

public class ComplaintsHistory {

    @SerializedName("complaint_id")
    private String complaintId;
    @SerializedName("contact_email")
    private String contactEmail;
    @SerializedName("contact_name")
    private String contactName;
    @SerializedName("contact_phone")
    private String contactPhone;
    @SerializedName("error")
    private String error;
    @SerializedName("error_msg")
    private String error_msg;
    @SerializedName("id")
    private String id;
    @SerializedName("orderIDNumber")
    private String orderIDNumber;
    @SerializedName("orderIssue")
    private String orderIssue;
    @SerializedName("orderIssueEmail")
    private String orderIssueEmail;
    @SerializedName("orderIssueMessage")
    private String orderIssueMessage;
    @SerializedName("Order_Status_Message")
    private String orderStatusMessage;
    @SerializedName("resid")
    private Long resid;
    @SerializedName("restaurant_address")
    private String restaurantAddress;
    @SerializedName("restaurant_city")
    private Object restaurantCity;
    @SerializedName("restaurant_mobile_number")
    private String restaurantMobileNumber;
    @SerializedName("restaurant_name")
    private String restaurantName;
    @SerializedName("restaurant_order_issue_date")
    private String restaurantOrderIssueDate;
    @SerializedName("restaurant_order_issue_reply")
    private Object restaurantOrderIssueReply;
    @SerializedName("restaurant_phone_number")
    private String restaurantPhoneNumber;
    @SerializedName("restaurant_postcode")
    private String restaurantPostcode;

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderIDNumber() {
        return orderIDNumber;
    }

    public void setOrderIDNumber(String orderIDNumber) {
        this.orderIDNumber = orderIDNumber;
    }

    public String getOrderIssue() {
        return orderIssue;
    }

    public void setOrderIssue(String orderIssue) {
        this.orderIssue = orderIssue;
    }

    public String getOrderIssueEmail() {
        return orderIssueEmail;
    }

    public void setOrderIssueEmail(String orderIssueEmail) {
        this.orderIssueEmail = orderIssueEmail;
    }

    public String getOrderIssueMessage() {
        return orderIssueMessage;
    }

    public void setOrderIssueMessage(String orderIssueMessage) {
        this.orderIssueMessage = orderIssueMessage;
    }

    public String getOrderStatusMessage() {
        return orderStatusMessage;
    }

    public void setOrderStatusMessage(String orderStatusMessage) {
        this.orderStatusMessage = orderStatusMessage;
    }

    public Long getResid() {
        return resid;
    }

    public void setResid(Long resid) {
        this.resid = resid;
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

    public String getRestaurantMobileNumber() {
        return restaurantMobileNumber;
    }

    public void setRestaurantMobileNumber(String restaurantMobileNumber) {
        this.restaurantMobileNumber = restaurantMobileNumber;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantOrderIssueDate() {
        return restaurantOrderIssueDate;
    }

    public void setRestaurantOrderIssueDate(String restaurantOrderIssueDate) {
        this.restaurantOrderIssueDate = restaurantOrderIssueDate;
    }

    public Object getRestaurantOrderIssueReply() {
        return restaurantOrderIssueReply;
    }

    public void setRestaurantOrderIssueReply(Object restaurantOrderIssueReply) {
        this.restaurantOrderIssueReply = restaurantOrderIssueReply;
    }

    public String getRestaurantPhoneNumber() {
        return restaurantPhoneNumber;
    }

    public void setRestaurantPhoneNumber(String restaurantPhoneNumber) {
        this.restaurantPhoneNumber = restaurantPhoneNumber;
    }

    public String getRestaurantPostcode() {
        return restaurantPostcode;
    }

    public void setRestaurantPostcode(String restaurantPostcode) {
        this.restaurantPostcode = restaurantPostcode;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
