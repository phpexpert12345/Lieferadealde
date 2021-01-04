package com.app.liferdeal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhpInitialInfoModel {
    @SerializedName("app_logo")
    @Expose
    private String appLogo;
    @SerializedName("app_top_home_icon")
    @Expose
    private String appTopHomeIcon;
    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("app_address")
    @Expose
    private String appAddress;
    @SerializedName("app_postcode")
    @Expose
    private String appPostcode;
    @SerializedName("app_city")
    @Expose
    private String appCity;
    @SerializedName("app_state")
    @Expose
    private String appState;
    @SerializedName("app_country")
    @Expose
    private String appCountry;
    @SerializedName("app_mobile_number")
    @Expose
    private String appMobileNumber;
    @SerializedName("app_phone_number")
    @Expose
    private String appPhoneNumber;
    @SerializedName("app_email_address")
    @Expose
    private String appEmailAddress;
    @SerializedName("app_default_language")
    @Expose
    private String appDefaultLanguage;
    @SerializedName("lang_code")
    @Expose
    private String langCode;
    @SerializedName("lang_name")
    @Expose
    private String langName;
    @SerializedName("lang_icon")
    @Expose
    private String langIcon;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("app_currency")
    @Expose
    private String appCurrency;
    @SerializedName("app_google_key")
    @Expose
    private String appGoogleKey;

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

    public String getAppTopHomeIcon() {
        return appTopHomeIcon;
    }

    public void setAppTopHomeIcon(String appTopHomeIcon) {
        this.appTopHomeIcon = appTopHomeIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppAddress() {
        return appAddress;
    }

    public void setAppAddress(String appAddress) {
        this.appAddress = appAddress;
    }

    public String getAppPostcode() {
        return appPostcode;
    }

    public void setAppPostcode(String appPostcode) {
        this.appPostcode = appPostcode;
    }

    public String getAppCity() {
        return appCity;
    }

    public void setAppCity(String appCity) {
        this.appCity = appCity;
    }

    public String getAppState() {
        return appState;
    }

    public void setAppState(String appState) {
        this.appState = appState;
    }

    public String getAppCountry() {
        return appCountry;
    }

    public void setAppCountry(String appCountry) {
        this.appCountry = appCountry;
    }

    public String getAppMobileNumber() {
        return appMobileNumber;
    }

    public void setAppMobileNumber(String appMobileNumber) {
        this.appMobileNumber = appMobileNumber;
    }

    public String getAppPhoneNumber() {
        return appPhoneNumber;
    }

    public void setAppPhoneNumber(String appPhoneNumber) {
        this.appPhoneNumber = appPhoneNumber;
    }

    public String getAppEmailAddress() {
        return appEmailAddress;
    }

    public void setAppEmailAddress(String appEmailAddress) {
        this.appEmailAddress = appEmailAddress;
    }

    public String getAppDefaultLanguage() {
        return appDefaultLanguage;
    }

    public void setAppDefaultLanguage(String appDefaultLanguage) {
        this.appDefaultLanguage = appDefaultLanguage;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangIcon() {
        return langIcon;
    }

    public void setLangIcon(String langIcon) {
        this.langIcon = langIcon;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAppCurrency() {
        return appCurrency;
    }

    public void setAppCurrency(String appCurrency) {
        this.appCurrency = appCurrency;
    }

    public String getAppGoogleKey() {
        return appGoogleKey;
    }

    public void setAppGoogleKey(String appGoogleKey) {
        this.appGoogleKey = appGoogleKey;
    }
}
