package com.app.liferdeal.model.loginsignup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInModel {
    @SerializedName("CustomerId")
    @Expose
    private String customerId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("Account_serial_No")
    @Expose
    private String accountSerialNo;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("company_street")
    @Expose
    private String companyStreet;
    @SerializedName("customerFloor_House_Number")
    @Expose
    private String customerFloorHouseNumber;
    @SerializedName("customerFlat_Name")
    @Expose
    private String customerFlatName;
    @SerializedName("user_city")
    @Expose
    private String userCity;
    @SerializedName("user_state")
    @Expose
    private String userState;
    @SerializedName("user_postcode")
    @Expose
    private String userPostcode;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("customerCountry")
    @Expose
    private String customerCountry;
    @SerializedName("customerState")
    @Expose
    private String customerState;
    @SerializedName("customerCity")
    @Expose
    private String customerCity;
    @SerializedName("customeraddresslat")
    @Expose
    private String customeraddresslat;
    @SerializedName("customeraddresslong")
    @Expose
    private String customeraddresslong;
    @SerializedName("user_photo")
    @Expose
    private String userPhoto;
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
    @SerializedName("user")
    @Expose
    private SignInUserModel user;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("success_msg")
    @Expose
    private String successMsg;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAccountSerialNo() {
        return accountSerialNo;
    }

    public void setAccountSerialNo(String accountSerialNo) {
        this.accountSerialNo = accountSerialNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    public String getCustomerFloorHouseNumber() {
        return customerFloorHouseNumber;
    }

    public void setCustomerFloorHouseNumber(String customerFloorHouseNumber) {
        this.customerFloorHouseNumber = customerFloorHouseNumber;
    }

    public String getCustomerFlatName() {
        return customerFlatName;
    }

    public void setCustomerFlatName(String customerFlatName) {
        this.customerFlatName = customerFlatName;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserPostcode() {
        return userPostcode;
    }

    public void setUserPostcode(String userPostcode) {
        this.userPostcode = userPostcode;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomeraddresslat() {
        return customeraddresslat;
    }

    public void setCustomeraddresslat(String customeraddresslat) {
        this.customeraddresslat = customeraddresslat;
    }

    public String getCustomeraddresslong() {
        return customeraddresslong;
    }

    public void setCustomeraddresslong(String customeraddresslong) {
        this.customeraddresslong = customeraddresslong;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
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

    public SignInUserModel getUser() {
        return user;
    }

    public void setUser(SignInUserModel user) {
        this.user = user;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}
