
package com.app.liferdeal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailItem {

    @SerializedName("CompanyAddress")
    private String companyAddress;
    @SerializedName("CompanyCity")
    private String companyCity;
    @SerializedName("CompanyMobile")
    private String companyMobile;
    @SerializedName("CompanyName")
    private String companyName;
    @SerializedName("CompanyPostcode")
    private String companyPostcode;
    @SerializedName("CouponDiscription")
    private String couponDiscription;
    @SerializedName("CouponPrice")
    private String couponPrice;
    @SerializedName("CouponType")
    private String couponType;
    @SerializedName("Currency")
    private String currency;
    @SerializedName("customer_address")
    private String customerAddress;
    @SerializedName("customer_city")
    private String customerCity;
    @SerializedName("customer_email")
    private String customerEmail;
    @SerializedName("customer_instruction")
    private String customerInstruction;
    @SerializedName("customer_NewAddress")
    private String customerNewAddress;
    @SerializedName("customer_phone")
    private String customerPhone;
    @SerializedName("customer_zipcode")
    private String customerZipcode;
    @SerializedName("DeliveryCharge")
    private String deliveryCharge;
    @SerializedName("DiscountDiscription")
    private String discountDiscription;
    @SerializedName("DiscountPrice")
    private String discountPrice;
    @SerializedName("DiscountType")
    private String discountType;
    @SerializedName("extraTipAddAmount")
    private String extraTipAddAmount;
    @SerializedName("FoodCosts")
    private String foodCosts;
    @SerializedName("GiftCardPay")
    private String giftCardPay;
    @SerializedName("name_customer")
    private String nameCustomer;
    @SerializedName("OrderAcceptedDate")
    private String orderAcceptedDate;
    @SerializedName("OrderAcceptedTime")
    private String orderAcceptedTime;
    @SerializedName("order_close")
    private String orderClose;
    @SerializedName("order_closeMessage")
    private String orderCloseMessage;
    @SerializedName("order_confirmed_time")
    private String orderConfirmedTime;
    @SerializedName("order_delivery_time")
    private String orderDeliveryTime;
    @SerializedName("order_identifyno")
    private String orderIdentifyno;
    @SerializedName("order_kitchen_time")
    private String orderKitchenTime;
    @SerializedName("OrderNumber")
    private String orderNumber;
    @SerializedName("orderPickupStatus")
    private Long orderPickupStatus;
    @SerializedName("OrderPrice")
    private String orderPrice;
    @SerializedName("order_status_color_code")
    private String orderStatusColorCode;
    @SerializedName("order_status_msg")
    private String orderStatusMsg;
    @SerializedName("orderStatusNo")
    private Long orderStatusNo;
    @SerializedName("OrderType")
    private String orderType;
    @SerializedName("orderid")
    private Long orderid;
    @SerializedName("orders")
    private Orders orders;
    @SerializedName("PackageFees")
    private String packageFees;
    @SerializedName("PackageFeesType")
    private String packageFeesType;
    @SerializedName("PayByLoyality")
    private String payByLoyality;
    @SerializedName("PayOptionStatus")
    private String payOptionStatus;
    @SerializedName("PaymentMethod")
    private String paymentMethod;
    @SerializedName("PrevOrdersFromCustomer")
    private Long prevOrdersFromCustomer;
    @SerializedName("RequestAtDate")
    private String requestAtDate;
    @SerializedName("RequestAtTime")
    private String requestAtTime;
    @SerializedName("RequestforDate")
    private String requestforDate;
    @SerializedName("RequestforTime")
    private String requestforTime;
    @SerializedName("resid")
    private Long resid;
    @SerializedName("restaurant_address")
    private String restaurantAddress;
    @SerializedName("restaurant_name")
    private String restaurantName;
    @SerializedName("rewardpoint")
    private String rewardpoint;
    @SerializedName("SalesTaxAmount")
    private String salesTaxAmount;
    @SerializedName("ServiceFees")
    private String serviceFees;
    @SerializedName("ServiceFeesType")
    private String serviceFeesType;
    @SerializedName("status")
    private String status;
    @SerializedName("subTotal")
    private String subTotal;
    @SerializedName("success")
    private Long success;
    @SerializedName("Table_Booking_Number")
    private String tableBookingNumber;
    @SerializedName("VatTax")
    private String vatTax;
    @SerializedName("WalletPay")
    private String walletPay;
    @SerializedName("review_done")
    private String review_done;
    @SerializedName("getFoodTaxTotal7")
    private String foodTaxTotal7;
    @SerializedName("getFoodTaxTotal19")
    private String foodTaxTotal19;

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

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPostcode() {
        return companyPostcode;
    }

    public void setCompanyPostcode(String companyPostcode) {
        this.companyPostcode = companyPostcode;
    }

    public String getCouponDiscription() {
        return couponDiscription;
    }

    public void setCouponDiscription(String couponDiscription) {
        this.couponDiscription = couponDiscription;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
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

    public String getCustomerNewAddress() {
        return customerNewAddress;
    }

    public void setCustomerNewAddress(String customerNewAddress) {
        this.customerNewAddress = customerNewAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerZipcode() {
        return customerZipcode;
    }

    public void setCustomerZipcode(String customerZipcode) {
        this.customerZipcode = customerZipcode;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDiscountDiscription() {
        return discountDiscription;
    }

    public void setDiscountDiscription(String discountDiscription) {
        this.discountDiscription = discountDiscription;
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

    public String getExtraTipAddAmount() {
        return extraTipAddAmount;
    }

    public void setExtraTipAddAmount(String extraTipAddAmount) {
        this.extraTipAddAmount = extraTipAddAmount;
    }

    public String getFoodCosts() {
        return foodCosts;
    }

    public void setFoodCosts(String foodCosts) {
        this.foodCosts = foodCosts;
    }

    public String getGiftCardPay() {
        return giftCardPay;
    }

    public void setGiftCardPay(String giftCardPay) {
        this.giftCardPay = giftCardPay;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
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

    public String getOrderConfirmedTime() {
        return orderConfirmedTime;
    }

    public void setOrderConfirmedTime(String orderConfirmedTime) {
        this.orderConfirmedTime = orderConfirmedTime;
    }

    public String getOrderDeliveryTime() {
        return orderDeliveryTime;
    }

    public void setOrderDeliveryTime(String orderDeliveryTime) {
        this.orderDeliveryTime = orderDeliveryTime;
    }

    public String getOrderIdentifyno() {
        return orderIdentifyno;
    }

    public void setOrderIdentifyno(String orderIdentifyno) {
        this.orderIdentifyno = orderIdentifyno;
    }

    public String getOrderKitchenTime() {
        return orderKitchenTime;
    }

    public void setOrderKitchenTime(String orderKitchenTime) {
        this.orderKitchenTime = orderKitchenTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getOrderPickupStatus() {
        return orderPickupStatus;
    }

    public void setOrderPickupStatus(Long orderPickupStatus) {
        this.orderPickupStatus = orderPickupStatus;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderStatusColorCode() {
        return orderStatusColorCode;
    }

    public void setOrderStatusColorCode(String orderStatusColorCode) {
        this.orderStatusColorCode = orderStatusColorCode;
    }

    public String getOrderStatusMsg() {
        return orderStatusMsg;
    }

    public void setOrderStatusMsg(String orderStatusMsg) {
        this.orderStatusMsg = orderStatusMsg;
    }

    public Long getOrderStatusNo() {
        return orderStatusNo;
    }

    public void setOrderStatusNo(Long orderStatusNo) {
        this.orderStatusNo = orderStatusNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
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

    public String getPayByLoyality() {
        return payByLoyality;
    }

    public void setPayByLoyality(String payByLoyality) {
        this.payByLoyality = payByLoyality;
    }

    public String getPayOptionStatus() {
        return payOptionStatus;
    }

    public void setPayOptionStatus(String payOptionStatus) {
        this.payOptionStatus = payOptionStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPrevOrdersFromCustomer() {
        return prevOrdersFromCustomer;
    }

    public void setPrevOrdersFromCustomer(Long prevOrdersFromCustomer) {
        this.prevOrdersFromCustomer = prevOrdersFromCustomer;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRewardpoint() {
        return rewardpoint;
    }

    public void setRewardpoint(String rewardpoint) {
        this.rewardpoint = rewardpoint;
    }

    public String getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(String salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public String getTableBookingNumber() {
        return tableBookingNumber;
    }

    public void setTableBookingNumber(String tableBookingNumber) {
        this.tableBookingNumber = tableBookingNumber;
    }

    public String getVatTax() {
        return vatTax;
    }

    public void setVatTax(String vatTax) {
        this.vatTax = vatTax;
    }

    public String getWalletPay() {
        return walletPay;
    }

    public void setWalletPay(String walletPay) {
        this.walletPay = walletPay;
    }

    public String getReview_done() {
        return review_done;
    }

    public void setReview_done(String review_done) {
        this.review_done = review_done;
    }

    public String getFoodTaxTotal7() {
        return foodTaxTotal7;
    }

    public void setFoodTaxTotal7(String foodTaxTotal7) {
        this.foodTaxTotal7 = foodTaxTotal7;
    }

    public String getFoodTaxTotal19() {
        return foodTaxTotal19;
    }

    public void setFoodTaxTotal19(String foodTaxTotal19) {
        this.foodTaxTotal19 = foodTaxTotal19;
    }

    public class Orders {

        @SerializedName("OrderViewResult")
        private List<OrderViewResult> orderViewResult;

        public List<OrderViewResult> getOrderViewResult() {
            return orderViewResult;
        }

        public void setOrderViewResult(List<OrderViewResult> orderViewResult) {
            this.orderViewResult = orderViewResult;
        }

    }

    public class OrderViewResult {

        @SerializedName("FoodCosts")
        private String foodCosts;
        @SerializedName("ordPrice")
        private String ordPrice;
        @SerializedName("order_date")
        private String orderDate;
        @SerializedName("order_display_message")
        private String orderDisplayMessage;
        @SerializedName("order_identifyno")
        private String orderIdentifyno;
        @SerializedName("order_time")
        private String orderTime;
        @SerializedName("order_type")
        private String orderType;
        @SerializedName("payment_mode")
        private String paymentMode;
        @SerializedName("restaurant_address")
        private String restaurantAddress;
        @SerializedName("restaurant_id")
        private Long restaurantId;
        @SerializedName("restaurant_name")
        private String restaurantName;

        public String getFoodCosts() {
            return foodCosts;
        }

        public void setFoodCosts(String foodCosts) {
            this.foodCosts = foodCosts;
        }

        public String getOrdPrice() {
            return ordPrice;
        }

        public void setOrdPrice(String ordPrice) {
            this.ordPrice = ordPrice;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrderDisplayMessage() {
            return orderDisplayMessage;
        }

        public void setOrderDisplayMessage(String orderDisplayMessage) {
            this.orderDisplayMessage = orderDisplayMessage;
        }

        public String getOrderIdentifyno() {
            return orderIdentifyno;
        }

        public void setOrderIdentifyno(String orderIdentifyno) {
            this.orderIdentifyno = orderIdentifyno;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getRestaurantAddress() {
            return restaurantAddress;
        }

        public void setRestaurantAddress(String restaurantAddress) {
            this.restaurantAddress = restaurantAddress;
        }

        public Long getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(Long restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

    }

}
