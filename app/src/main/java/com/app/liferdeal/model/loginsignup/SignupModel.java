package com.app.liferdeal.model.loginsignup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupModel {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;
    @SerializedName("Total_wallet_money_available")
    @Expose
    private String totalWalletMoneyAvailable;
    @SerializedName("digital_wallet_card_number")
    @Expose
    private String digitalWalletCardNumber;
    @SerializedName("digital_wallet_qr_code")
    @Expose
    private String digitalWalletQrCode;
    @SerializedName("digital_wallet_card_pin_number")
    @Expose
    private String digitalWalletCardPinNumber;
    @SerializedName("WEBSITE_DIGITAL_WALLET_TERMS")
    @Expose
    private String wEBSITEDIGITALWALLETTERMS;
    @SerializedName("digital_wallet_member_since")
    @Expose
    private String digitalWalletMemberSince;
    @SerializedName("referral_sharing_Message")
    @Expose
    private String referralSharingMessage;
    @SerializedName("referral_codeMessage")
    @Expose
    private String referralCodeMessage;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("referral_earn_points")
    @Expose
    private String referralEarnPoints;
    @SerializedName("referral_join_friends")
    @Expose
    private String referralJoinFriends;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("success_msg")
    @Expose
    private String successMsg;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    @SerializedName("error_msg")
    @Expose
    private String error_msg;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTotalWalletMoneyAvailable() {
        return totalWalletMoneyAvailable;
    }

    public void setTotalWalletMoneyAvailable(String totalWalletMoneyAvailable) {
        this.totalWalletMoneyAvailable = totalWalletMoneyAvailable;
    }

    public String getDigitalWalletCardNumber() {
        return digitalWalletCardNumber;
    }

    public void setDigitalWalletCardNumber(String digitalWalletCardNumber) {
        this.digitalWalletCardNumber = digitalWalletCardNumber;
    }

    public String getDigitalWalletQrCode() {
        return digitalWalletQrCode;
    }

    public void setDigitalWalletQrCode(String digitalWalletQrCode) {
        this.digitalWalletQrCode = digitalWalletQrCode;
    }

    public String getDigitalWalletCardPinNumber() {
        return digitalWalletCardPinNumber;
    }

    public void setDigitalWalletCardPinNumber(String digitalWalletCardPinNumber) {
        this.digitalWalletCardPinNumber = digitalWalletCardPinNumber;
    }

    public String getWEBSITEDIGITALWALLETTERMS() {
        return wEBSITEDIGITALWALLETTERMS;
    }

    public void setWEBSITEDIGITALWALLETTERMS(String wEBSITEDIGITALWALLETTERMS) {
        this.wEBSITEDIGITALWALLETTERMS = wEBSITEDIGITALWALLETTERMS;
    }

    public String getDigitalWalletMemberSince() {
        return digitalWalletMemberSince;
    }

    public void setDigitalWalletMemberSince(String digitalWalletMemberSince) {
        this.digitalWalletMemberSince = digitalWalletMemberSince;
    }

    public String getReferralSharingMessage() {
        return referralSharingMessage;
    }

    public void setReferralSharingMessage(String referralSharingMessage) {
        this.referralSharingMessage = referralSharingMessage;
    }

    public String getReferralCodeMessage() {
        return referralCodeMessage;
    }

    public void setReferralCodeMessage(String referralCodeMessage) {
        this.referralCodeMessage = referralCodeMessage;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferralEarnPoints() {
        return referralEarnPoints;
    }

    public void setReferralEarnPoints(String referralEarnPoints) {
        this.referralEarnPoints = referralEarnPoints;
    }

    public String getReferralJoinFriends() {
        return referralJoinFriends;
    }

    public void setReferralJoinFriends(String referralJoinFriends) {
        this.referralJoinFriends = referralJoinFriends;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

}
