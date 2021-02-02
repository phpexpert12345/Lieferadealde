package com.app.liferdeal.util;

import static com.app.liferdeal.util.Constants.Url.BASE_URL;

public class Constants {

    public static final String FORDELIVERY = "forDeliverySharedPref";
    public static final String HMDLVRYAVAIL = "homeDelivery";
    public static final String DINAVAILABLE = "dineInAvailable";
    public static final String PCKAVAILABLE = "pickupAvailable";
    public static final String RESDELORAVAIL = "restaurantDelivery";
    public static final String LANGUAGE_DATA = "LANGUAGE_DATA";
    public static final String LANGUAGE_FORM_DATA = "LANGUAGE_FORM_DATA";
    public static final String FORCASHONDELIVERY ="forcashdelivery" ;
    public static final String PAYPALPAY ="paypalPay" ;
    public static final String CARDPAY ="cardpay" ;
    public static final String RESTAURANTID ="RESTAURANTID" ;
    public static final String PRICEPREFERENCE ="pricepreference" ;
    public static final String SUBTOTAL ="subtotalpr" ;
    public static final String FOODDISCOUNT ="fooddiscountpr" ;
    public static final String SERVICEFEES ="servicefeespr" ;
    public static final String SALESTAX ="salestaxpr" ;
    public static final String VAT ="vatpr" ;
    public static final String DELIVERYFEES ="deliveryfeespr" ;
    public static final String PACKAGINGFEES = "packagingfeespr";
    public static final String TOTALPR ="totalpr" ;
    public static final String FORRESTIDCHANGE ="forrestidchange" ;
    public static final String FIRSTTIMELOGINPAGE ="firsttimeloginpage" ;
    public static final String ADDRESSTITLE ="addresstielead" ;
    public static final String HOUSENO ="housenoad" ;
    public static final String STREETNAME ="streetnamead" ;
    public static final String CITYNAME ="citynamead" ;
    public static final String POSTALCODE = "postalcodead";
    public static final String SIZEOFPIZZA ="sizeofpizzapr" ;
    public static final String FILTERDATA ="filterdatapr" ;
    public static String isLoggedIn = "isLoggedIn";
    public static final String APP_ADDRESS = "appaddress";
    public static final String APP_NUMBER = "appnumber";
    public static final String APP_EMAIL = "appemail";
    public static final String APP_NAME = "appname";


    public static class Url {
        public static String BASE_URL = "https://www.lieferadeal.de/WebAppAPI/";

        public static final String LOCATION = "phpexpert_app_open_location.php";
        public static final String LANGUAGE = "phpexpert_customer_app_langauge.php";
        public static final String LANGUAGE_LIST = "phpexpert_customer_app_langauge_list.php";
        public static final String SPLASH = "phpexpert_restaurant_app_splash_gallery.php";
        public static final String RESTRURENT_INFORMATION = "phpexpert_restaurant_information.php";
        public static final String HOME_SLIDER = "phpexpert_home_api.php";
        public static final String FOOD_CATEGORY = "phpexpert_restaurantMenuCategory.php";
        public static final String FOOD = "phpexpert_food_items.php";
        public static final String FOOD_ITEM = "phpexpert_restaurantMenuItemSize.php";
        public static final String FOOD_ITEM_EXTRA_TOPPING = "phpexpert_food_items_extra.php";
        public static final String VERIFY_POSTALCODE = "phpexpert_postcode_validator.php";
        public static final String TABLE = "phpexpert_table_list.php";
        public static final String RESTRURENT_DISCOUNT_PRICE = "discountGet.php";
        public static final String SERVICE_CHARGE = "ServiceChargetGet.php";
        public static final String VERIFY_COUPON = "couponCode.php";
        public static final String GET_LOYALTY_POINT = "phpexpert_customer_loyalty_point.php";
        public static final String VERIFY_LOYALTY_POINT = "phpexpert_customer_loyalty_point_redeem.php";
        public static final String SIGNIN = "phpexpert_customer_login.php";
        public static final String SIGNUP = "phpexpert_customer_account_register.php";
        public static final String GET_BRANCH = "phpexpert_get_restaurant_branch_list.php";
        public static final String GET_ADDRESS = "phpexpert_customer_address_list.php";
        public static final String ADD_ADDRESS = "phpexpert_customer_add_new_address.php";
        public static final String DELETE_ADDRESS = "customer_address_delete.php";
        public static final String GET_TIME_SLOT = "phpexpert_time_fetch.php";
        public static final String GET_ALLERGY = "phpexpert_restaurant_allery_info.php";
        public static final String SEND_ORDER = "phpexpert_payment_Eat_In_Order.php";
        public static final String PLACE_ORDER = "phpexpert_payment_android_submit.php";
        public static final String GET_ORDER_LIST = "phpexpert_OrderDisplay.php";
        public static final String TRACK_ORDER = "phpexpert_Order_Track_Dettail.php";
        public static final String CANCEL_ORDER = "phpexpert_Customer_Order_Cancelled.php";
        public static final String GET_ORDER_DETAIL = "phpexpert_Order_DetailsDisplay.php";
        public static final String WRITE_REVIEW = "phpexpert_write_review.php";
        public static final String GET_REVIEWS = "phpexpert_CustomerReview.php";
        public static final String GET_TICKETS = "phpexpert_customer_ticket_list.php";
        public static final String SUBMIT_TICKET = "phpexpert_customer_ticket_submit.php";
        public static final String GET_RESTAURANT_REVIEW = "phpexpert_restaurantReview.php";
        public static final String GET_OPENING_HOURS = "phpexpert_restaurant_opening_hours.php";
        public static final String GET_DELIVERY_AREA = "phpexpert_get_restaurant_delivery_list.php";
        public static final String GET_GALLERY = "phpexpert_food_gallery.php";
        public static final String GET_RESTAURANT_OFFERS = "phpexpert_restaurant_Offers.php";
        public static final String SUBMIT_CONTACT_US = "phpexpert_customer_contact_submit.php";
        public static final String GET_LOYALTY_POINT_LIST = "phpexpert_restaurant_loyalty_point_list.php";
        public static final String GET_PAYMENT = "phpexpert_payment_key.php";
        public static final String PAYMENT_BACKEND = "phpexpert_payment_intent_generate.php";
        public static final String BOOK_A_TABLE = "phpexpert_customer_table_booking.php";
        public static final String UPDATE_PROFILE = "phpexpert_customer_profite_update.php";
        public static final String GET_PROFILE = "phpexpert_customer_profile_inform.php";
        public static final String CHANGE_PASSWORD = "phpexpert_customer_passwordChange.php";
        public static final String GET_PAY_LATER_ORDER_LIST = "phpexpert_OrderDisplayPayLater.php";
        public static final String GET_PAY_LATER_PLACE_ORDER = "phpexpert_payment_pay_later.php";
        public static final String GET_PAY_LATER_PLACE_ORDER_DIRECT = "phpexpert_payment_pay_later_direct.php";
        public static final String FORGET_PASSWORD = "phpexpert_customer_forgot_password.php";
        public static final String COM_DETAILS=BASE_URL+"phpexpert_combo_detail.php";
    }

    public static class Header {
        public static final String AUTH_ID = "Authorization";
    }

    public static class Data {
        public static final String CONTAINER_ID = "CONTAINER_ID";
        public static final String FOOD_CATEGORY_POSITION = "FOOD_CATEGORY_POSITION";
        public static final String FOOD = "FOOD";
        public static final String FOOD_ITEM = "FOOD_ITEM";
        public static final String ORDER_ID = "ORDER_ID";
        public static final String FOOD_IMAGE_LINK = "FOOD_IMAGE_LINK";
        public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
        public static final String CUSTOMER_MOBILE = "CUSTOMER_MOBILE";
        public static final String BOOKING_DATE = "BOOKING_DATE";
        public static final String BOOKING_TIME = "BOOKING_TIME";
        public static final String SPECIAL_INSTRUCTIONS = "SPECIAL_INSTRUCTIONS";
        public static final String TABLE = "TABLE";
        public static final String NO_OF_PERSON = "NO_OF_PERSON";
        public static final String CURRENT_PHOTO_PATH = "CURRENT_PHOTO_PATH";
        public static final String IS_CONTINUE_ORDER_VISIBLE = "IS_CONTINUE_ORDER_VISIBLE";
        public static final String SELECTED_TABLE_ID = "SELECTED_TABLE_ID";
    }

    public static class API {
        public static String FOOD_KEY = "foodkey";
        public static String LANGUAGE_CODE = "en";
        public static final String SLIDER = "slider";
    }

    public static class SharedPreference {
        public static final String ORDER_TYPE = "ORDER_TYPE";
        public static final String POSTAL_CODE = "POSTAL_CODE";
        public static final String TABLE_ID = "TABLE_ID";
        public static final String USER = "USER";
        public static final String SELECTED_FOOD = "SELECTED_FOOD";
        public static final String EAT_IN_ORDER_ID = "EAT_IN_ORDER_ID";
        public static final String COUPON_CODE = "COUPON_CODE";
        public static final String COUPON_CODE_DISCOUNT = "COUPON_CODE_DISCOUNT";
        public static final String LOYALTY_CODE_DISCOUNT = "LOYALTY_CODE_DISCOUNT";
        public static final String RESTAURANT_DISCOUNT = "RESTAURANT_DISCOUNT";
        public static final String FOOD_TAX = "FOOD_TAX";
        public static final String DRINK_TAX = "DRINK_TAX";
        public static final String RIDER_TIP = "RIDER_TIP";
        public static final String SUB_TOTAL = "SUB_TOTAL";
        public static final String TO_PAY = "TO_PAY";
        public static final String SELECTED_RESTAURANT_BRANCH = "SELECTED_RESTAURANT_BRANCH";
        public static final String SELECTED_DELIVERY_ADDRESS = "SELECTED_DELIVERY_ADDRESS";
        public static final String ORDER_SEND_TO_KITCHEN_RESPONSE = "ORDER_SEND_TO_KITCHEN_RESPONSE";
        public static final String ORDERED_FOOD = "ORDERED_FOOD";
    }

    public static String CUSTOMER_ID = "customer_id";
    public static String USER_NAME = "user_name";
    public static String USER_MOBILE = "user_mobile";
    public static String USER_EMAIL = "user_email";
    public static String CUSTOMER_ADDRESS_ID = "customer_address_id";
    public static String REFERCODELOGIN = "refercodelogin";
    public static String refercodeMessage = "refercodeMessage";
    public static String referSharingMessage = "referSharingMessage";
    public static String referralEarnPoints = "referralEarnPoints";
    public static String referralJoinFriends = "referralJoinFriends";


    public static String Rest_DETAIL_CLICK_ITEM_ID = "restclickitemid";
    public static String Rest_DETAIL_CLICK_ITEM_NAME = "restclickitemname";
    public static String UPDATE_PROFILE_URL = BASE_URL + "phpexpert_customer_profite_update.php";

    public static String latitude = "latitude";
    public static String longitude = "longitude";
    public static String locationList = "LocationList";
    public static String deviceName = "deviceName";
    public static String deviceId = "deviceId";
    public static String devicePlateform = "devicePlateform";
    public static String deviceType = "deviceType";
    public static final String Device_Ip = "Device_Ip";
    public static final String API_KEY = "foodkey";
    public static final String APP_LOGO = "applogo";
    public static final String APP_TOP_LOGO_ICON = "apptoplogo";
    public static final String APP_DEF_LANG = "appdeflng";
    public static final String APP_LNG_NAME = "applngname";
    public static final String APP_LNG_ICON = "applngicon";
    public static final String APP_CURRENCY = "appcurrency";
    public static final String LNG_CODE = "en";
    public static String deviceModel = "deviceModel";
    public static String deviceUID = "deviceUID";
    public static String google_map_key = "google_map_key";

    public static String IsLogin = "IsLoggedIn";


    public static String SAVE_FULL_ADDRESS = "full_address";
    public static String SAVE_CITY_NAME = "city_name";
    public static String SAVE_STATE = "state";
    public static String SAVE_COUNTRY = "country";
    public static String SAVE_POSTAL_CODE = "postal_code";
    public static String USER_PROFILE_IMAGE = "user_profile_image";

   /* public static String getCurrencySymbol(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
        return currency.getSymbol(currencyLocaleMap.get(currency));
    }*/

   /* public static ArrayList<NavMenuModel> getMenuNavigasi(){
        ArrayList<NavMenuModel> menu = new ArrayList<>();

        menu.add(new NavMenuModel("Home", R.drawable.home, LocationMapFragment.newInstance("locationmap")));
        menu.add(new NavMenuModel("Push Notification", R.drawable.notification, LocationFragment.newInstance("push")));
        menu.add(new NavMenuModel("My Order", R.drawable.shopping_bag, LocationFragment.newInstance("myorder")));
        menu.add(new NavMenuModel("My Favourites", R.drawable.heart, LocationFragment.newInstance("myorder")));
        menu.add(new NavMenuModel("My Address", R.drawable.location, LocationFragment.newInstance("myorder")));

        menu.add(new NavMenuModel("Need Help", R.drawable.needhelp,
                new ArrayList<NavMenuModel.SubMenuModel>() {{
                    add(new NavMenuModel.SubMenuModel("Frequently Asked Question", TemanDekatFragment.newInstance("unch")));
                    add(new NavMenuModel.SubMenuModel("Terms & Conditions", TemanDekatSekaliFragment.newInstance("utututu")));
                }}));

        menu.add(new NavMenuModel("Country", R.drawable.location, NotifikasiFragment.newInstance("country")));
        menu.add(new NavMenuModel("Language", R.drawable.ic_lang, LanguageFragment.newInstance("language")));
        menu.add(new NavMenuModel("Logout", R.drawable.logout, LocationMapFragment.newInstance("Logout")));


        return menu;*/
    //}
}
