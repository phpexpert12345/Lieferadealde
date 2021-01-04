package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantBookTableModel {
    @SerializedName("Booking_Number")
    @Expose
    private String bookingNumber;
    @SerializedName("Transaction_Number")
    @Expose
    private String transactionNumber;
    @SerializedName("booking_mobile")
    @Expose
    private String bookingMobile;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("booking_time")
    @Expose
    private String bookingTime;
    @SerializedName("booking_name")
    @Expose
    private Object bookingName;
    @SerializedName("booking_email")
    @Expose
    private String bookingEmail;
    @SerializedName("booking_instruction")
    @Expose
    private String bookingInstruction;
    @SerializedName("Number_of_person")
    @Expose
    private String numberOfPerson;
    @SerializedName("Thankyou")
    @Expose
    private String thankyou;
    @SerializedName("success_msg")
    @Expose
    private String successMsg;
    @SerializedName("Important_Note")
    @Expose
    private String importantNote;
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getBookingMobile() {
        return bookingMobile;
    }

    public void setBookingMobile(String bookingMobile) {
        this.bookingMobile = bookingMobile;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Object getBookingName() {
        return bookingName;
    }

    public void setBookingName(Object bookingName) {
        this.bookingName = bookingName;
    }

    public String getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(String bookingEmail) {
        this.bookingEmail = bookingEmail;
    }

    public String getBookingInstruction() {
        return bookingInstruction;
    }

    public void setBookingInstruction(String bookingInstruction) {
        this.bookingInstruction = bookingInstruction;
    }

    public String getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(String numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    public String getThankyou() {
        return thankyou;
    }

    public void setThankyou(String thankyou) {
        this.thankyou = thankyou;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getImportantNote() {
        return importantNote;
    }

    public void setImportantNote(String importantNote) {
        this.importantNote = importantNote;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
