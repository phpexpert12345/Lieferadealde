package com.app.liferdeal.model.restaurant;

import com.app.liferdeal.model.OrderFoodItem;
import com.app.liferdeal.model.menuitems.OrderMealItems;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailItem {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("order_identifyno")
    @Expose
    private String orderIdentifyno;
    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("OrderType")
    @Expose
    private String orderType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("orderStatusNo")
    @Expose
    private Integer orderStatusNo;
    @SerializedName("orderPickupStatus")
    @Expose
    private Integer orderPickupStatus;
    @SerializedName("review_done")
    @Expose
    private String reviewDone;
    @SerializedName("order_close")
    @Expose
    private String orderClose;
    @SerializedName("order_closeMessage")
    @Expose
    private String orderCloseMessage;
    @SerializedName("order_status_msg")
    @Expose
    private String orderStatusMsg;
    @SerializedName("order_status_color_code")
    @Expose
    private String orderStatusColorCode;
    @SerializedName("order_confirmed_time")
    @Expose
    private Object orderConfirmedTime;
    @SerializedName("order_kitchen_time")
    @Expose
    private Object orderKitchenTime;
    @SerializedName("order_delivery_time")
    @Expose
    private Object orderDeliveryTime;
    @SerializedName("Table_Booking_Number")
    @Expose
    private String tableBookingNumber;
    @SerializedName("PrevOrdersFromCustomer")
    @Expose
    private Integer prevOrdersFromCustomer;
    @SerializedName("PayOptionStatus")
    @Expose
    private String payOptionStatus;
    @SerializedName("rewardpoint")
    @Expose
    private String rewardpoint;
    @SerializedName("RequestAtDate")
    @Expose
    private String requestAtDate;
    @SerializedName("RequestAtTime")
    @Expose
    private String requestAtTime;
    @SerializedName("RequestforDate")
    @Expose
    private String requestforDate;
    @SerializedName("RequestforTime")
    @Expose
    private String requestforTime;
    @SerializedName("OrderAcceptedDate")
    @Expose
    private String orderAcceptedDate;
    @SerializedName("OrderAcceptedTime")
    @Expose
    private String orderAcceptedTime;
    @SerializedName("orderid")
    @Expose
    private Integer orderid;
    @SerializedName("PaymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("name_customer")
    @Expose
    private String nameCustomer;
    @SerializedName("customer_address")
    @Expose
    private String customerAddress;
    @SerializedName("customer_NewAddress")
    @Expose
    private String customerNewAddress;
    @SerializedName("customer_city")
    @Expose
    private String customerCity;
    @SerializedName("customer_zipcode")
    @Expose
    private String customerZipcode;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_instruction")
    @Expose
    private String customerInstruction;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("resid")
    @Expose
    private Integer resid;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_address")
    @Expose
    private String restaurantAddress;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("CompanyAddress")
    @Expose
    private String companyAddress;
    @SerializedName("CompanyCity")
    @Expose
    private String companyCity;
    @SerializedName("CompanyPostcode")
    @Expose
    private String companyPostcode;
    @SerializedName("CompanyMobile")
    @Expose
    private String companyMobile;
    @SerializedName("subTotal")
    @Expose
    private String subTotal;
    @SerializedName("DeliveryCharge")
    @Expose
    private Object deliveryCharge;
    @SerializedName("PackageFees")
    @Expose
    private String packageFees;
    @SerializedName("PackageFeesType")
    @Expose
    private String packageFeesType;
    @SerializedName("ServiceFees")
    @Expose
    private String serviceFees;
    @SerializedName("ServiceFeesType")
    @Expose
    private String serviceFeesType;
    @SerializedName("CouponPrice")
    @Expose
    private String couponPrice;
    @SerializedName("CouponType")
    @Expose
    private String couponType;
    @SerializedName("DiscountPrice")
    @Expose
    private String discountPrice;
    @SerializedName("DiscountType")
    @Expose
    private String discountType;
    @SerializedName("PayByLoyality")
    @Expose
    private String payByLoyality;
    @SerializedName("FoodCosts")
    @Expose
    private Object foodCosts;
    @SerializedName("getTotalItemDiscount")
    @Expose
    private String getTotalItemDiscount;
    @SerializedName("getFoodTaxTotal7")
    @Expose
    private String getFoodTaxTotal7;
    @SerializedName("getFoodTaxTotal19")
    @Expose
    private String getFoodTaxTotal19;
    @SerializedName("TotalSavedDiscount")
    @Expose
    private String totalSavedDiscount;
    @SerializedName("discountOfferFreeItems")
    @Expose
    private String discountOfferFreeItems;
    @SerializedName("VatTax")
    @Expose
    private String vatTax;
    @SerializedName("extraTipAddAmount")
    @Expose
    private String extraTipAddAmount;
    @SerializedName("SalesTaxAmount")
    @Expose
    private String salesTaxAmount;
    @SerializedName("GiftCardPay")
    @Expose
    private String giftCardPay;
    @SerializedName("WalletPay")
    @Expose
    private String walletPay;
    @SerializedName("OrderPrice")
    @Expose
    private String orderPrice;
    @SerializedName("OrderFoodItem")
    @Expose
    private List<OrderFoodItem> orderFoodItem = null;

    public List<OrderMealItems> getOrderMealItem() {
        return orderMealItem;
    }

    public void setOrderMealItem(List<OrderMealItems> orderMealItem) {
        this.orderMealItem = orderMealItem;
    }

    @SerializedName("OrderMealItem")
    @Expose
    private List<OrderMealItems> orderMealItem = new ArrayList<>();

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getOrderIdentifyno() {
        return orderIdentifyno;
    }

    public void setOrderIdentifyno(String orderIdentifyno) {
        this.orderIdentifyno = orderIdentifyno;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrderStatusNo() {
        return orderStatusNo;
    }

    public void setOrderStatusNo(Integer orderStatusNo) {
        this.orderStatusNo = orderStatusNo;
    }

    public Integer getOrderPickupStatus() {
        return orderPickupStatus;
    }

    public void setOrderPickupStatus(Integer orderPickupStatus) {
        this.orderPickupStatus = orderPickupStatus;
    }

    public String getReviewDone() {
        return reviewDone;
    }

    public void setReviewDone(String reviewDone) {
        this.reviewDone = reviewDone;
    }

    public String getOrderClose() {
        return orderClose;
    }

    public void setOrderClose(String orderClose) {
        this.orderClose = orderClose;
    }

    public String getOrderCloseMessage() {
        return orderCloseMessage;
    }

    public void setOrderCloseMessage(String orderCloseMessage) {
        this.orderCloseMessage = orderCloseMessage;
    }

    public String getOrderStatusMsg() {
        return orderStatusMsg;
    }

    public void setOrderStatusMsg(String orderStatusMsg) {
        this.orderStatusMsg = orderStatusMsg;
    }

    public String getOrderStatusColorCode() {
        return orderStatusColorCode;
    }

    public void setOrderStatusColorCode(String orderStatusColorCode) {
        this.orderStatusColorCode = orderStatusColorCode;
    }

    public Object getOrderConfirmedTime() {
        return orderConfirmedTime;
    }

    public void setOrderConfirmedTime(Object orderConfirmedTime) {
        this.orderConfirmedTime = orderConfirmedTime;
    }

    public Object getOrderKitchenTime() {
        return orderKitchenTime;
    }

    public void setOrderKitchenTime(Object orderKitchenTime) {
        this.orderKitchenTime = orderKitchenTime;
    }

    public Object getOrderDeliveryTime() {
        return orderDeliveryTime;
    }

    public void setOrderDeliveryTime(Object orderDeliveryTime) {
        this.orderDeliveryTime = orderDeliveryTime;
    }

    public String getTableBookingNumber() {
        return tableBookingNumber;
    }

    public void setTableBookingNumber(String tableBookingNumber) {
        this.tableBookingNumber = tableBookingNumber;
    }

    public Integer getPrevOrdersFromCustomer() {
        return prevOrdersFromCustomer;
    }

    public void setPrevOrdersFromCustomer(Integer prevOrdersFromCustomer) {
        this.prevOrdersFromCustomer = prevOrdersFromCustomer;
    }

    public String getPayOptionStatus() {
        return payOptionStatus;
    }

    public void setPayOptionStatus(String payOptionStatus) {
        this.payOptionStatus = payOptionStatus;
    }

    public String getRewardpoint() {
        return rewardpoint;
    }

    public void setRewardpoint(String rewardpoint) {
        this.rewardpoint = rewardpoint;
    }

    public String getRequestAtDate() {
        return requestAtDate;
    }

    public void setRequestAtDate(String requestAtDate) {
        this.requestAtDate = requestAtDate;
    }

    public String getRequestAtTime() {
        return requestAtTime;
    }

    public void setRequestAtTime(String requestAtTime) {
        this.requestAtTime = requestAtTime;
    }

    public String getRequestforDate() {
        return requestforDate;
    }

    public void setRequestforDate(String requestforDate) {
        this.requestforDate = requestforDate;
    }

    public String getRequestforTime() {
        return requestforTime;
    }

    public void setRequestforTime(String requestforTime) {
        this.requestforTime = requestforTime;
    }

    public String getOrderAcceptedDate() {
        return orderAcceptedDate;
    }

    public void setOrderAcceptedDate(String orderAcceptedDate) {
        this.orderAcceptedDate = orderAcceptedDate;
    }

    public String getOrderAcceptedTime() {
        return orderAcceptedTime;
    }

    public void setOrderAcceptedTime(String orderAcceptedTime) {
        this.orderAcceptedTime = orderAcceptedTime;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerNewAddress() {
        return customerNewAddress;
    }

    public void setCustomerNewAddress(String customerNewAddress) {
        this.customerNewAddress = customerNewAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerZipcode() {
        return customerZipcode;
    }

    public void setCustomerZipcode(String customerZipcode) {
        this.customerZipcode = customerZipcode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerInstruction() {
        return customerInstruction;
    }

    public void setCustomerInstruction(String customerInstruction) {
        this.customerInstruction = customerInstruction;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getResid() {
        return resid;
    }

    public void setResid(Integer resid) {
        this.resid = resid;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyPostcode() {
        return companyPostcode;
    }

    public void setCompanyPostcode(String companyPostcode) {
        this.companyPostcode = companyPostcode;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public Object getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Object deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getPackageFees() {
        return packageFees;
    }

    public void setPackageFees(String packageFees) {
        this.packageFees = packageFees;
    }

    public String getPackageFeesType() {
        return packageFeesType;
    }

    public void setPackageFeesType(String packageFeesType) {
        this.packageFeesType = packageFeesType;
    }

    public String getServiceFees() {
        return serviceFees;
    }

    public void setServiceFees(String serviceFees) {
        this.serviceFees = serviceFees;
    }

    public String getServiceFeesType() {
        return serviceFeesType;
    }

    public void setServiceFeesType(String serviceFeesType) {
        this.serviceFeesType = serviceFeesType;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getPayByLoyality() {
        return payByLoyality;
    }

    public void setPayByLoyality(String payByLoyality) {
        this.payByLoyality = payByLoyality;
    }

    public Object getFoodCosts() {
        return foodCosts;
    }

    public void setFoodCosts(Object foodCosts) {
        this.foodCosts = foodCosts;
    }

    public String getGetTotalItemDiscount() {
        return getTotalItemDiscount;
    }

    public void setGetTotalItemDiscount(String getTotalItemDiscount) {
        this.getTotalItemDiscount = getTotalItemDiscount;
    }

    public String getGetFoodTaxTotal7() {
        return getFoodTaxTotal7;
    }

    public void setGetFoodTaxTotal7(String getFoodTaxTotal7) {
        this.getFoodTaxTotal7 = getFoodTaxTotal7;
    }

    public String getGetFoodTaxTotal19() {
        return getFoodTaxTotal19;
    }

    public void setGetFoodTaxTotal19(String getFoodTaxTotal19) {
        this.getFoodTaxTotal19 = getFoodTaxTotal19;
    }

    public String getTotalSavedDiscount() {
        return totalSavedDiscount;
    }

    public void setTotalSavedDiscount(String totalSavedDiscount) {
        this.totalSavedDiscount = totalSavedDiscount;
    }

    public String getDiscountOfferFreeItems() {
        return discountOfferFreeItems;
    }

    public void setDiscountOfferFreeItems(String discountOfferFreeItems) {
        this.discountOfferFreeItems = discountOfferFreeItems;
    }

    public String getVatTax() {
        return vatTax;
    }

    public void setVatTax(String vatTax) {
        this.vatTax = vatTax;
    }

    public String getExtraTipAddAmount() {
        return extraTipAddAmount;
    }

    public void setExtraTipAddAmount(String extraTipAddAmount) {
        this.extraTipAddAmount = extraTipAddAmount;
    }

    public String getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(String salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public String getGiftCardPay() {
        return giftCardPay;
    }

    public void setGiftCardPay(String giftCardPay) {
        this.giftCardPay = giftCardPay;
    }

    public String getWalletPay() {
        return walletPay;
    }

    public void setWalletPay(String walletPay) {
        this.walletPay = walletPay;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public List<OrderFoodItem> getOrderFoodItem() {
        return orderFoodItem;
    }

    public void setOrderFoodItem(List<OrderFoodItem> orderFoodItem) {
        this.orderFoodItem = orderFoodItem;
    }

}
