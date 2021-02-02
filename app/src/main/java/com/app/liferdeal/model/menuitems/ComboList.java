package com.app.liferdeal.model.menuitems;

import com.app.liferdeal.ui.interfaces.Section;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComboList implements Section {
    @SerializedName("RestaurantPizzaItemOldPrice")
            @Expose
            private String RestaurantPizzaItemOldPrice;
    @SerializedName("RestaurantPizzaItemPrice")
    @Expose
    private String RestaurantPizzaItemPrice;
    @SerializedName("restaurant_id")
    @Expose
    private Integer  restaurant_id;
    @SerializedName("dealID")
    @Expose
    private Integer dealID;
    @SerializedName("combo_dealid")
    @Expose
    private Integer combo_dealid;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Combo_NameNo")
    @Expose
    private String Combo_NameNo;
    @SerializedName("deal_name")
    @Expose
    private String deal_name;
    @SerializedName("deal_description")
    @Expose
    private String deal_description;
    @SerializedName("Food_Type")
    @Expose
    private String Food_Type;
    @SerializedName("Food_Type_Non")
    @Expose
    private String Food_Type_Non;
    @SerializedName("Food_Popular")
    @Expose
    private String Food_Popular;
    @SerializedName("Food_Spicy")
    @Expose
    private String Food_Spicy;
    @SerializedName("MidFood_Spicy")
    @Expose
    private String MidFood_Spicy;
    @SerializedName("VeryFood_Spicy")
    @Expose
    private String VeryFood_Spicy;
    @SerializedName("GreenFood_Spicy")
    @Expose
    private String GreenFood_Spicy;
    @SerializedName("deal_image")
    @Expose
    private String deal_image;
    @SerializedName("error")
    @Expose
    private Integer error;

    public String getRestaurantPizzaItemOldPrice() {
        return RestaurantPizzaItemOldPrice;
    }

    public void setRestaurantPizzaItemOldPrice(String restaurantPizzaItemOldPrice) {
        RestaurantPizzaItemOldPrice = restaurantPizzaItemOldPrice;
    }

    public String getRestaurantPizzaItemPrice() {
        return RestaurantPizzaItemPrice;
    }

    public void setRestaurantPizzaItemPrice(String restaurantPizzaItemPrice) {
        RestaurantPizzaItemPrice = restaurantPizzaItemPrice;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Integer getDealID() {
        return dealID;
    }

    public void setDealID(Integer dealID) {
        this.dealID = dealID;
    }

    public Integer getCombo_dealid() {
        return combo_dealid;
    }

    public void setCombo_dealid(Integer combo_dealid) {
        this.combo_dealid = combo_dealid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCombo_NameNo() {
        return Combo_NameNo;
    }

    public void setCombo_NameNo(String combo_NameNo) {
        Combo_NameNo = combo_NameNo;
    }

    public String getDeal_name() {
        return deal_name;
    }

    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }

    public String getDeal_description() {
        return deal_description;
    }

    public void setDeal_description(String deal_description) {
        this.deal_description = deal_description;
    }

    public String getFood_Type() {
        return Food_Type;
    }

    public void setFood_Type(String food_Type) {
        Food_Type = food_Type;
    }

    public String getFood_Type_Non() {
        return Food_Type_Non;
    }

    public void setFood_Type_Non(String food_Type_Non) {
        Food_Type_Non = food_Type_Non;
    }

    public String getFood_Popular() {
        return Food_Popular;
    }

    public void setFood_Popular(String food_Popular) {
        Food_Popular = food_Popular;
    }

    public String getFood_Spicy() {
        return Food_Spicy;
    }

    public void setFood_Spicy(String food_Spicy) {
        Food_Spicy = food_Spicy;
    }

    public String getMidFood_Spicy() {
        return MidFood_Spicy;
    }

    public void setMidFood_Spicy(String midFood_Spicy) {
        MidFood_Spicy = midFood_Spicy;
    }

    public String getVeryFood_Spicy() {
        return VeryFood_Spicy;
    }

    public void setVeryFood_Spicy(String veryFood_Spicy) {
        VeryFood_Spicy = veryFood_Spicy;
    }

    public String getGreenFood_Spicy() {
        return GreenFood_Spicy;
    }

    public void setGreenFood_Spicy(String greenFood_Spicy) {
        GreenFood_Spicy = greenFood_Spicy;
    }

    public String getDeal_image() {
        return deal_image;
    }

    public void setDeal_image(String deal_image) {
        this.deal_image = deal_image;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMaximum_allow() {
        return maximum_allow;
    }

    public void setMaximum_allow(String maximum_allow) {
        this.maximum_allow = maximum_allow;
    }

    @SerializedName("maximum_allow")
    @Expose
    private String maximum_allow;

    public String getAllergy_Description() {
        return Allergy_Description;
    }

    public void setAllergy_Description(String allergy_Description) {
        Allergy_Description = allergy_Description;
    }

    @SerializedName("Allergy_Description")
    @Expose
    private String Allergy_Description;


//    {
//        "RestaurantPizzaItemOldPrice": "",
//            "RestaurantPizzaItemPrice": "6.00",
//            "restaurant_id": 3,
//            "dealID": 20,
//            "combo_dealid": 20,
//            "id": 20,
//            "Combo_NameNo": "A",
//            "deal_name": "PIZZA 026CM (3 BEILAGEN)",
//            "deal_description": "",
//            "Allergy_Description": "",
//            "Food_Type": "",
//            "Food_Type_Non": "",
//            "Food_Popular": "",
//            "Food_Spicy": "",
//            "MidFood_Spicy": "",
//            "VeryFood_Spicy": "",
//            "GreenFood_Spicy": "",
//            "deal_image": "",
//            "error": 0,
//            "maximum_allow": "MultiSelectionList"
//    }
    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int sectionPosition() {
        return 0;
    }
}
