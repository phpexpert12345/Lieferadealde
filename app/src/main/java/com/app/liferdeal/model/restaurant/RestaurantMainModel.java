package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantMainModel {
    @SerializedName("searchResult")
    @Expose
    private List<SearchResult> searchResult = null;

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    @SerializedName("success_msg")
    @Expose
    private String successMsg="";

    public List<SearchResult> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<SearchResult> searchResult) {
        this.searchResult = searchResult;
    }

    public class SearchResult {

        public String getTable_booking_limit() {
            return table_booking_limit;
        }

        public void setTable_booking_limit(String table_booking_limit) {
            this.table_booking_limit = table_booking_limit;
        }

        @SerializedName("table_booking_limit")
        @Expose
        private String table_booking_limit;

        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("restaurant_address")
        @Expose
        private String restaurantAddress;
        @SerializedName("restaurant_Impression")
        @Expose
        private String restaurantImpression;
        @SerializedName("restaurant_about")
        @Expose
        private String restaurantAbout;
        @SerializedName("restaurantCity")
        @Expose
        private String restaurantCity;
        @SerializedName("restaurant_Company_Register_City")
        @Expose
        private String restaurantCompanyRegisterCity;
        @SerializedName("restaurant_deliveryDistance")
        @Expose
        private String restaurantDeliveryDistance;
        @SerializedName("restaurant_cuisine")
        @Expose
        private String restaurantCuisine;
        @SerializedName("restaurant_discount_covered")
        @Expose
        private String restaurantDiscountCovered;
        @SerializedName("restaurant_distance_covered")
        @Expose
        private String restaurantDistanceCovered;
        @SerializedName("RestaurantTimeStatus")
        @Expose
        private String restaurantTimeStatus;
        @SerializedName("RestaurantTimeStatus1")
        @Expose
        private String restaurantTimeStatus1;
        @SerializedName("Restaurant_Order_Available")
        @Expose
        private String restaurantOrderAvailable;
        @SerializedName("RestaurantTimeStatusColorCode")
        @Expose
        private String restaurantTimeStatusColorCode;
        @SerializedName("RestaurantTimeStatusText")
        @Expose
        private String restaurantTimeStatusText;
        @SerializedName("restaurant_contact_name")
        @Expose
        private String restaurantContactName;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Fax")
        @Expose
        private String fax;
        @SerializedName("Company_Register")
        @Expose
        private String companyRegister;
        @SerializedName("Company_Register_No")
        @Expose
        private String companyRegisterNo;
        @SerializedName("VAT_Number")
        @Expose
        private String vATNumber;
        @SerializedName("Legal_Representative")
        @Expose
        private String legalRepresentative;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("restaurant_distance_check")
        @Expose
        private String restaurantDistanceCheck;
        @SerializedName("homeDeliveryStatus")
        @Expose
        private String homeDeliveryStatus;
        @SerializedName("PickupStatus")
        @Expose
        private String pickupStatus;
        @SerializedName("SocialSharingMessage")
        @Expose
        private String socialSharingMessage;
        @SerializedName("website_CurrencySymbole")
        @Expose
        private String websiteCurrencySymbole;
        @SerializedName("restaurant_Logo")
        @Expose
        private String restaurantLogo;
        @SerializedName("restaurant_cover")
        @Expose
        private String restaurantCover;
        @SerializedName("HomeDeliveryAvailable")
        @Expose
        private String homeDeliveryAvailable;

        public String getSponsorAvailable() {
            return SponsorAvailable;
        }

        public void setSponsorAvailable(String sponsorAvailable) {
            SponsorAvailable = sponsorAvailable;
        }

        @SerializedName("SponsorAvailable")
        @Expose
        private String SponsorAvailable;

        @SerializedName("PickupAvailable")
        @Expose
        private String pickupAvailable;
        @SerializedName("DineInAvailable")
        @Expose
        private String dineInAvailable;
        @SerializedName("loyalty_program_enable")
        @Expose
        private String loyaltyProgramEnable;
        @SerializedName("BookaTablesupportAvailable")
        @Expose
        private String bookaTablesupportAvailable;
        @SerializedName("Pre_homeDeliveryStatus")
        @Expose
        private String preHomeDeliveryStatus;
        @SerializedName("Pre_Order_PickupStatus")
        @Expose
        private String preOrderPickupStatus;
        @SerializedName("payLater_Available")
        @Expose
        private String payLaterAvailable;
        @SerializedName("payPaypal_Available")
        @Expose
        private String payPaypalAvailable;
        @SerializedName("payPaypal_Amount_Deposit")
        @Expose
        private String payPaypalAmountDeposit;
        @SerializedName("Wallet_Available")
        @Expose
        private String walletAvailable;
        @SerializedName("restaurant_onlycashonAvailable")
        @Expose
        private String restaurantOnlycashonAvailable;
        @SerializedName("restaurant_onlycashon")
        @Expose
        private String restaurantOnlycashon;
        @SerializedName("restaurant_cardacceptAvailable")
        @Expose
        private String restaurantCardacceptAvailable;
        @SerializedName("restaurant_cardaccept")
        @Expose
        private String restaurantCardaccept;


        @SerializedName("restaurant_serviceFees")
        @Expose
        private String restaurantServiceFees;
        @SerializedName("restaurant_saleTaxPercentage")
        @Expose
        private String restaurantSaleTaxPercentage;
        @SerializedName("restaurant_serviceVat")
        @Expose
        private String restaurantServiceVat;
        @SerializedName("BookaTablesupport")
        @Expose
        private String bookaTablesupport;
        @SerializedName("restaurant_deliverycharge")
        @Expose
        private String restaurantDeliverycharge;


        @SerializedName("restaurant_minimumorder")
        @Expose
        private String restaurantMinimumorder;
        @SerializedName("restaurant_avarage_deliveryTime")
        @Expose
        private String restaurantAvarageDeliveryTime;
        @SerializedName("restaurant_avarage_PickupTime")
        @Expose
        private String restaurantAvaragePickupTime;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("ratingValue")
        @Expose
        private String ratingValue;
        @SerializedName("hygien_rating")
        @Expose
        private String hygienRating;
        @SerializedName("hygien_ratingValue")
        @Expose
        private String hygienRatingValue;
        @SerializedName("restaurant_LatitudePoint")
        @Expose
        private String restaurantLatitudePoint;
        @SerializedName("restaurant_LongitudePoint")
        @Expose
        private String restaurantLongitudePoint;
        @SerializedName("restaurant_phone")
        @Expose
        private String restaurantPhone;
        @SerializedName("restaurant_contact_phone")
        @Expose
        private String restaurantContactPhone;
        @SerializedName("restaurant_contact_mobile")
        @Expose
        private String restaurantContactMobile;
        @SerializedName("restaurant_contact_email")
        @Expose
        private String restaurantContactEmail;
        @SerializedName("restaurant_sms_mobile")
        @Expose
        private String restaurantSmsMobile;
        @SerializedName("restaurant_OrderEmail")
        @Expose
        private String restaurantOrderEmail;
        @SerializedName("ratingAvg")
        @Expose
        private String ratingAvg;
        @SerializedName("restaurant_locality")
        @Expose
        private String restaurantLocality;
        @SerializedName("restaurant_Paypal_URL")
        @Expose
        private Object restaurantPaypalURL;
        @SerializedName("restaurant_Paypal_bussiness_act")
        @Expose
        private Object restaurantPaypalBussinessAct;
        @SerializedName("Manager_Email")
        @Expose
        private String managerEmail;
        @SerializedName("restaurant_fax")
        @Expose
        private String restaurantFax;
        @SerializedName("restaurant_Company_Register_No")
        @Expose
        private String restaurantCompanyRegisterNo;
        @SerializedName("restaurant_Vat_number")
        @Expose
        private String restaurantVatNumber;
        @SerializedName("Discount_Available")
        @Expose
        private String discountAvailable;
        @SerializedName("mealDealAvailable")
        @Expose
        private String mealDealAvailable;
        @SerializedName("error")
        @Expose
        private Integer error;

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

        public String getRestaurantImpression() {
            return restaurantImpression;
        }

        public void setRestaurantImpression(String restaurantImpression) {
            this.restaurantImpression = restaurantImpression;
        }

        public String getRestaurantAbout() {
            return restaurantAbout;
        }

        public void setRestaurantAbout(String restaurantAbout) {
            this.restaurantAbout = restaurantAbout;
        }

        public String getRestaurantCity() {
            return restaurantCity;
        }

        public void setRestaurantCity(String restaurantCity) {
            this.restaurantCity = restaurantCity;
        }

        public String getRestaurantCompanyRegisterCity() {
            return restaurantCompanyRegisterCity;
        }

        public void setRestaurantCompanyRegisterCity(String restaurantCompanyRegisterCity) {
            this.restaurantCompanyRegisterCity = restaurantCompanyRegisterCity;
        }

        public String getRestaurantDeliveryDistance() {
            return restaurantDeliveryDistance;
        }

        public void setRestaurantDeliveryDistance(String restaurantDeliveryDistance) {
            this.restaurantDeliveryDistance = restaurantDeliveryDistance;
        }

        public String getRestaurantCuisine() {
            return restaurantCuisine;
        }

        public void setRestaurantCuisine(String restaurantCuisine) {
            this.restaurantCuisine = restaurantCuisine;
        }

        public String getRestaurantDiscountCovered() {
            return restaurantDiscountCovered;
        }

        public void setRestaurantDiscountCovered(String restaurantDiscountCovered) {
            this.restaurantDiscountCovered = restaurantDiscountCovered;
        }

        public String getRestaurantDistanceCovered() {
            return restaurantDistanceCovered;
        }

        public void setRestaurantDistanceCovered(String restaurantDistanceCovered) {
            this.restaurantDistanceCovered = restaurantDistanceCovered;
        }

        public String getRestaurantTimeStatus() {
            return restaurantTimeStatus;
        }

        public void setRestaurantTimeStatus(String restaurantTimeStatus) {
            this.restaurantTimeStatus = restaurantTimeStatus;
        }

        public String getRestaurantTimeStatus1() {
            return restaurantTimeStatus1;
        }

        public void setRestaurantTimeStatus1(String restaurantTimeStatus1) {
            this.restaurantTimeStatus1 = restaurantTimeStatus1;
        }

        public String getRestaurantOrderAvailable() {
            return restaurantOrderAvailable;
        }

        public void setRestaurantOrderAvailable(String restaurantOrderAvailable) {
            this.restaurantOrderAvailable = restaurantOrderAvailable;
        }

        public String getRestaurantTimeStatusColorCode() {
            return restaurantTimeStatusColorCode;
        }

        public void setRestaurantTimeStatusColorCode(String restaurantTimeStatusColorCode) {
            this.restaurantTimeStatusColorCode = restaurantTimeStatusColorCode;
        }

        public String getRestaurantTimeStatusText() {
            return restaurantTimeStatusText;
        }

        public void setRestaurantTimeStatusText(String restaurantTimeStatusText) {
            this.restaurantTimeStatusText = restaurantTimeStatusText;
        }

        public String getRestaurantContactName() {
            return restaurantContactName;
        }

        public void setRestaurantContactName(String restaurantContactName) {
            this.restaurantContactName = restaurantContactName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getCompanyRegister() {
            return companyRegister;
        }

        public void setCompanyRegister(String companyRegister) {
            this.companyRegister = companyRegister;
        }

        public String getCompanyRegisterNo() {
            return companyRegisterNo;
        }

        public void setCompanyRegisterNo(String companyRegisterNo) {
            this.companyRegisterNo = companyRegisterNo;
        }

        public String getVATNumber() {
            return vATNumber;
        }

        public void setVATNumber(String vATNumber) {
            this.vATNumber = vATNumber;
        }

        public String getLegalRepresentative() {
            return legalRepresentative;
        }

        public void setLegalRepresentative(String legalRepresentative) {
            this.legalRepresentative = legalRepresentative;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRestaurantDistanceCheck() {
            return restaurantDistanceCheck;
        }

        public void setRestaurantDistanceCheck(String restaurantDistanceCheck) {
            this.restaurantDistanceCheck = restaurantDistanceCheck;
        }

        public String getHomeDeliveryStatus() {
            return homeDeliveryStatus;
        }

        public void setHomeDeliveryStatus(String homeDeliveryStatus) {
            this.homeDeliveryStatus = homeDeliveryStatus;
        }

        public String getPickupStatus() {
            return pickupStatus;
        }

        public void setPickupStatus(String pickupStatus) {
            this.pickupStatus = pickupStatus;
        }

        public String getSocialSharingMessage() {
            return socialSharingMessage;
        }

        public void setSocialSharingMessage(String socialSharingMessage) {
            this.socialSharingMessage = socialSharingMessage;
        }

        public String getWebsiteCurrencySymbole() {
            return websiteCurrencySymbole;
        }

        public void setWebsiteCurrencySymbole(String websiteCurrencySymbole) {
            this.websiteCurrencySymbole = websiteCurrencySymbole;
        }

        public String getRestaurantLogo() {
            return restaurantLogo;
        }

        public void setRestaurantLogo(String restaurantLogo) {
            this.restaurantLogo = restaurantLogo;
        }

        public String getRestaurantCover() {
            return restaurantCover;
        }

        public void setRestaurantCover(String restaurantCover) {
            this.restaurantCover = restaurantCover;
        }

        public String getHomeDeliveryAvailable() {
            return homeDeliveryAvailable;
        }

        public void setHomeDeliveryAvailable(String homeDeliveryAvailable) {
            this.homeDeliveryAvailable = homeDeliveryAvailable;
        }

        public String getPickupAvailable() {
            return pickupAvailable;
        }

        public void setPickupAvailable(String pickupAvailable) {
            this.pickupAvailable = pickupAvailable;
        }

        public String getDineInAvailable() {
            return dineInAvailable;
        }

        public void setDineInAvailable(String dineInAvailable) {
            this.dineInAvailable = dineInAvailable;
        }

        public String getLoyaltyProgramEnable() {
            return loyaltyProgramEnable;
        }

        public void setLoyaltyProgramEnable(String loyaltyProgramEnable) {
            this.loyaltyProgramEnable = loyaltyProgramEnable;
        }

        public String getBookaTablesupportAvailable() {
            return bookaTablesupportAvailable;
        }

        public void setBookaTablesupportAvailable(String bookaTablesupportAvailable) {
            this.bookaTablesupportAvailable = bookaTablesupportAvailable;
        }

        public String getPreHomeDeliveryStatus() {
            return preHomeDeliveryStatus;
        }

        public void setPreHomeDeliveryStatus(String preHomeDeliveryStatus) {
            this.preHomeDeliveryStatus = preHomeDeliveryStatus;
        }

        public String getPreOrderPickupStatus() {
            return preOrderPickupStatus;
        }

        public void setPreOrderPickupStatus(String preOrderPickupStatus) {
            this.preOrderPickupStatus = preOrderPickupStatus;
        }

        public String getPayLaterAvailable() {
            return payLaterAvailable;
        }

        public void setPayLaterAvailable(String payLaterAvailable) {
            this.payLaterAvailable = payLaterAvailable;
        }

        public String getPayPaypalAvailable() {
            return payPaypalAvailable;
        }

        public void setPayPaypalAvailable(String payPaypalAvailable) {
            this.payPaypalAvailable = payPaypalAvailable;
        }

        public String getPayPaypalAmountDeposit() {
            return payPaypalAmountDeposit;
        }

        public void setPayPaypalAmountDeposit(String payPaypalAmountDeposit) {
            this.payPaypalAmountDeposit = payPaypalAmountDeposit;
        }

        public String getWalletAvailable() {
            return walletAvailable;
        }

        public void setWalletAvailable(String walletAvailable) {
            this.walletAvailable = walletAvailable;
        }

        public String getRestaurantOnlycashonAvailable() {
            return restaurantOnlycashonAvailable;
        }

        public void setRestaurantOnlycashonAvailable(String restaurantOnlycashonAvailable) {
            this.restaurantOnlycashonAvailable = restaurantOnlycashonAvailable;
        }

        public String getRestaurantOnlycashon() {
            return restaurantOnlycashon;
        }

        public void setRestaurantOnlycashon(String restaurantOnlycashon) {
            this.restaurantOnlycashon = restaurantOnlycashon;
        }

        public String getRestaurantCardacceptAvailable() {
            return restaurantCardacceptAvailable;
        }

        public void setRestaurantCardacceptAvailable(String restaurantCardacceptAvailable) {
            this.restaurantCardacceptAvailable = restaurantCardacceptAvailable;
        }

        public String getRestaurantCardaccept() {
            return restaurantCardaccept;
        }

        public void setRestaurantCardaccept(String restaurantCardaccept) {
            this.restaurantCardaccept = restaurantCardaccept;
        }

        public String getRestaurantServiceFees() {
            return restaurantServiceFees;
        }

        public void setRestaurantServiceFees(String restaurantServiceFees) {
            this.restaurantServiceFees = restaurantServiceFees;
        }

        public String getRestaurantSaleTaxPercentage() {
            return restaurantSaleTaxPercentage;
        }

        public void setRestaurantSaleTaxPercentage(String restaurantSaleTaxPercentage) {
            this.restaurantSaleTaxPercentage = restaurantSaleTaxPercentage;
        }

        public String getRestaurantServiceVat() {
            return restaurantServiceVat;
        }

        public void setRestaurantServiceVat(String restaurantServiceVat) {
            this.restaurantServiceVat = restaurantServiceVat;
        }

        public String getBookaTablesupport() {
            return bookaTablesupport;
        }

        public void setBookaTablesupport(String bookaTablesupport) {
            this.bookaTablesupport = bookaTablesupport;
        }

        public String getRestaurantDeliverycharge() {
            return restaurantDeliverycharge;
        }

        public void setRestaurantDeliverycharge(String restaurantDeliverycharge) {
            this.restaurantDeliverycharge = restaurantDeliverycharge;
        }

        public String getRestaurantMinimumorder() {
            return restaurantMinimumorder;
        }

        public void setRestaurantMinimumorder(String restaurantMinimumorder) {
            this.restaurantMinimumorder = restaurantMinimumorder;
        }

        public String getRestaurantAvarageDeliveryTime() {
            return restaurantAvarageDeliveryTime;
        }

        public void setRestaurantAvarageDeliveryTime(String restaurantAvarageDeliveryTime) {
            this.restaurantAvarageDeliveryTime = restaurantAvarageDeliveryTime;
        }

        public String getRestaurantAvaragePickupTime() {
            return restaurantAvaragePickupTime;
        }

        public void setRestaurantAvaragePickupTime(String restaurantAvaragePickupTime) {
            this.restaurantAvaragePickupTime = restaurantAvaragePickupTime;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getRatingValue() {
            return ratingValue;
        }

        public void setRatingValue(String ratingValue) {
            this.ratingValue = ratingValue;
        }

        public String getHygienRating() {
            return hygienRating;
        }

        public void setHygienRating(String hygienRating) {
            this.hygienRating = hygienRating;
        }

        public String getHygienRatingValue() {
            return hygienRatingValue;
        }

        public void setHygienRatingValue(String hygienRatingValue) {
            this.hygienRatingValue = hygienRatingValue;
        }

        public String getRestaurantLatitudePoint() {
            return restaurantLatitudePoint;
        }

        public void setRestaurantLatitudePoint(String restaurantLatitudePoint) {
            this.restaurantLatitudePoint = restaurantLatitudePoint;
        }

        public String getRestaurantLongitudePoint() {
            return restaurantLongitudePoint;
        }

        public void setRestaurantLongitudePoint(String restaurantLongitudePoint) {
            this.restaurantLongitudePoint = restaurantLongitudePoint;
        }

        public String getRestaurantPhone() {
            return restaurantPhone;
        }

        public void setRestaurantPhone(String restaurantPhone) {
            this.restaurantPhone = restaurantPhone;
        }

        public String getRestaurantContactPhone() {
            return restaurantContactPhone;
        }

        public void setRestaurantContactPhone(String restaurantContactPhone) {
            this.restaurantContactPhone = restaurantContactPhone;
        }

        public String getRestaurantContactMobile() {
            return restaurantContactMobile;
        }

        public void setRestaurantContactMobile(String restaurantContactMobile) {
            this.restaurantContactMobile = restaurantContactMobile;
        }

        public String getRestaurantContactEmail() {
            return restaurantContactEmail;
        }

        public void setRestaurantContactEmail(String restaurantContactEmail) {
            this.restaurantContactEmail = restaurantContactEmail;
        }

        public String getRestaurantSmsMobile() {
            return restaurantSmsMobile;
        }

        public void setRestaurantSmsMobile(String restaurantSmsMobile) {
            this.restaurantSmsMobile = restaurantSmsMobile;
        }

        public String getRestaurantOrderEmail() {
            return restaurantOrderEmail;
        }

        public void setRestaurantOrderEmail(String restaurantOrderEmail) {
            this.restaurantOrderEmail = restaurantOrderEmail;
        }

        public String getRatingAvg() {
            return ratingAvg;
        }

        public void setRatingAvg(String ratingAvg) {
            this.ratingAvg = ratingAvg;
        }

        public String getRestaurantLocality() {
            return restaurantLocality;
        }

        public void setRestaurantLocality(String restaurantLocality) {
            this.restaurantLocality = restaurantLocality;
        }

        public Object getRestaurantPaypalURL() {
            return restaurantPaypalURL;
        }

        public void setRestaurantPaypalURL(Object restaurantPaypalURL) {
            this.restaurantPaypalURL = restaurantPaypalURL;
        }

        public Object getRestaurantPaypalBussinessAct() {
            return restaurantPaypalBussinessAct;
        }

        public void setRestaurantPaypalBussinessAct(Object restaurantPaypalBussinessAct) {
            this.restaurantPaypalBussinessAct = restaurantPaypalBussinessAct;
        }

        public String getManagerEmail() {
            return managerEmail;
        }

        public void setManagerEmail(String managerEmail) {
            this.managerEmail = managerEmail;
        }

        public String getRestaurantFax() {
            return restaurantFax;
        }

        public void setRestaurantFax(String restaurantFax) {
            this.restaurantFax = restaurantFax;
        }

        public String getRestaurantCompanyRegisterNo() {
            return restaurantCompanyRegisterNo;
        }

        public void setRestaurantCompanyRegisterNo(String restaurantCompanyRegisterNo) {
            this.restaurantCompanyRegisterNo = restaurantCompanyRegisterNo;
        }

        public String getRestaurantVatNumber() {
            return restaurantVatNumber;
        }

        public void setRestaurantVatNumber(String restaurantVatNumber) {
            this.restaurantVatNumber = restaurantVatNumber;
        }

        public String getDiscountAvailable() {
            return discountAvailable;
        }

        public void setDiscountAvailable(String discountAvailable) {
            this.discountAvailable = discountAvailable;
        }

        public String getMealDealAvailable() {
            return mealDealAvailable;
        }

        public void setMealDealAvailable(String mealDealAvailable) {
            this.mealDealAvailable = mealDealAvailable;
        }

        public Integer getError() {
            return error;
        }

        public void setError(Integer error) {
            this.error = error;
        }

    }
}
