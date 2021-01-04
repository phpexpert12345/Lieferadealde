package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTicketModel {
    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getOrder_identifyno() {
        return order_identifyno;
    }

    public void setOrder_identifyno(String order_identifyno) {
        this.order_identifyno = order_identifyno;
    }

    public String getOrder_Message() {
        return Order_Message;
    }

    public void setOrder_Message(String order_Message) {
        Order_Message = order_Message;
    }

    public String getOrder_Issue_Subject() {
        return Order_Issue_Subject;
    }

    public void setOrder_Issue_Subject(String order_Issue_Subject) {
        Order_Issue_Subject = order_Issue_Subject;
    }

    public String getOrder_Email_Address() {
        return Order_Email_Address;
    }

    public void setOrder_Email_Address(String order_Email_Address) {
        Order_Email_Address = order_Email_Address;
    }

    @SerializedName("CustomerId")
    @Expose
    private String CustomerId;
    @SerializedName("order_identifyno")
    @Expose
    private String order_identifyno;


    @SerializedName("Order_Message")
    @Expose
    private String Order_Message;

    @SerializedName("Order_Issue_Subject")
    @Expose
    private String Order_Issue_Subject;
    @SerializedName("Order_Email_Address")
    @Expose
    private String Order_Email_Address;

}
