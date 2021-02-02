
package com.app.liferdeal.model.menuitems;

import com.app.liferdeal.ui.interfaces.Section;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubItemsRecord implements Section {

    @SerializedName("ItemID")
    @Expose
    private Integer itemID;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("Allergy_Description")
    @Expose
    private String allergyDescription;
    @SerializedName("RestaurantPizzaID")
    @Expose
    private Integer restaurantPizzaID;
    @SerializedName("RestaurantCategoryID")
    @Expose
    private String restaurantCategoryID;
    @SerializedName("RestaurantPizzaItemName")
    @Expose
    private String restaurantPizzaItemName;
    @SerializedName("food_tax_applicable")
    @Expose
    private String foodTaxApplicable;
    @SerializedName("extraavailable")
    @Expose
    private String extraavailable;
    @SerializedName("ResPizzaDescription")
    @Expose
    private String resPizzaDescription;
    @SerializedName("Food_NameNo")
    @Expose
    private String foodNameNo;
    @SerializedName("additives_Description")
    @Expose
    private String additivesDescription;
    @SerializedName("Dish_Ingredients")
    @Expose
    private String dishIngredients;
    @SerializedName("Food_Type")
    @Expose
    private String foodType;
    @SerializedName("Food_Type_Non")
    @Expose
    private String foodTypeNon;
    @SerializedName("Food_Popular")
    @Expose
    private String foodPopular;
    @SerializedName("Food_Spicy")
    @Expose
    private String foodSpicy;
    @SerializedName("MidFood_Spicy")
    @Expose
    private String midFoodSpicy;
    @SerializedName("VeryFood_Spicy")
    @Expose
    private String veryFoodSpicy;
    @SerializedName("GreenFood_Spicy")
    @Expose
    private String greenFoodSpicy;
    @SerializedName("food_Icon")
    @Expose
    private String foodIcon;
    @SerializedName("RestaurantPizzaItemSizeName")
    @Expose
    private String restaurantPizzaItemSizeName;
    @SerializedName("RestaurantPizzaItemOldPrice")
    @Expose
    private String restaurantPizzaItemOldPrice;
    @SerializedName("Discount_Food_Amount")
    @Expose
    private String discountFoodAmount;
    @SerializedName("RestaurantPizzaItemPrice")
    @Expose
    private String restaurantPizzaItemPrice;
    @SerializedName("sizeavailable")
    @Expose
    private String sizeavailable;
    @SerializedName("is_combo")
    @Expose
    private boolean is_combo=false;

    public String getFree_Topping_Selection_allowed() {
        return Free_Topping_Selection_allowed;
    }

    public void setFree_Topping_Selection_allowed(String free_Topping_Selection_allowed) {
        Free_Topping_Selection_allowed = free_Topping_Selection_allowed;
    }

    public String getCHOOSE_ANY_FREE_TOPINGS() {
        return CHOOSE_ANY_FREE_TOPINGS;
    }

    public void setCHOOSE_ANY_FREE_TOPINGS(String CHOOSE_ANY_FREE_TOPINGS) {
        this.CHOOSE_ANY_FREE_TOPINGS = CHOOSE_ANY_FREE_TOPINGS;
    }

    @SerializedName("Free_Topping_Selection_allowed")
    @Expose
    private String Free_Topping_Selection_allowed;
    @SerializedName("CHOOSE_ANY_FREE_TOPINGS")
    @Expose
    private String CHOOSE_ANY_FREE_TOPINGS;

    public String  getAllergyDescription() {
        return allergyDescription;
    }

    public void setAllergyDescription(String allergyDescription) {
        this.allergyDescription = allergyDescription;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getRestaurantPizzaID() {
        return restaurantPizzaID;
    }

    public void setRestaurantPizzaID(Integer restaurantPizzaID) {
        this.restaurantPizzaID = restaurantPizzaID;
    }

    public String getRestaurantCategoryID() {
        return restaurantCategoryID;
    }

    public void setRestaurantCategoryID(String restaurantCategoryID) {
        this.restaurantCategoryID = restaurantCategoryID;
    }

    public String getRestaurantPizzaItemName() {
        return restaurantPizzaItemName;
    }

    public void setRestaurantPizzaItemName(String restaurantPizzaItemName) {
        this.restaurantPizzaItemName = restaurantPizzaItemName;
    }

    public String getFoodTaxApplicable() {
        return foodTaxApplicable;
    }

    public void setFoodTaxApplicable(String foodTaxApplicable) {
        this.foodTaxApplicable = foodTaxApplicable;
    }

    public String getExtraavailable() {
        return extraavailable;
    }

    public void setExtraavailable(String extraavailable) {
        this.extraavailable = extraavailable;
    }

    public String getResPizzaDescription() {
        return resPizzaDescription;
    }

    public void setResPizzaDescription(String resPizzaDescription) {
        this.resPizzaDescription = resPizzaDescription;
    }

    public String getFoodNameNo() {
        return foodNameNo;
    }

    public void setFoodNameNo(String foodNameNo) {
        this.foodNameNo = foodNameNo;
    }

    public String getAdditivesDescription() {
        return additivesDescription;
    }

    public void setAdditivesDescription(String additivesDescription) {
        this.additivesDescription = additivesDescription;
    }

    public String getDishIngredients() {
        return dishIngredients;
    }

    public void setDishIngredients(String dishIngredients) {
        this.dishIngredients = dishIngredients;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodTypeNon() {
        return foodTypeNon;
    }

    public void setFoodTypeNon(String foodTypeNon) {
        this.foodTypeNon = foodTypeNon;
    }

    public String getFoodPopular() {
        return foodPopular;
    }

    public void setFoodPopular(String foodPopular) {
        this.foodPopular = foodPopular;
    }

    public String getFoodSpicy() {
        return foodSpicy;
    }

    public void setFoodSpicy(String foodSpicy) {
        this.foodSpicy = foodSpicy;
    }

    public String getMidFoodSpicy() {
        return midFoodSpicy;
    }

    public void setMidFoodSpicy(String midFoodSpicy) {
        this.midFoodSpicy = midFoodSpicy;
    }

    public String getVeryFoodSpicy() {
        return veryFoodSpicy;
    }

    public void setVeryFoodSpicy(String veryFoodSpicy) {
        this.veryFoodSpicy = veryFoodSpicy;
    }

    public String getGreenFoodSpicy() {
        return greenFoodSpicy;
    }

    public void setGreenFoodSpicy(String greenFoodSpicy) {
        this.greenFoodSpicy = greenFoodSpicy;
    }

    public String getFoodIcon() {
        return foodIcon;
    }

    public void setFoodIcon(String foodIcon) {
        this.foodIcon = foodIcon;
    }

    public String getRestaurantPizzaItemSizeName() {
        return restaurantPizzaItemSizeName;
    }

    public void setRestaurantPizzaItemSizeName(String restaurantPizzaItemSizeName) {
        this.restaurantPizzaItemSizeName = restaurantPizzaItemSizeName;
    }

    public String getRestaurantPizzaItemOldPrice() {
        return restaurantPizzaItemOldPrice;
    }

    public void setRestaurantPizzaItemOldPrice(String restaurantPizzaItemOldPrice) {
        this.restaurantPizzaItemOldPrice = restaurantPizzaItemOldPrice;
    }

    public String getDiscountFoodAmount() {
        return discountFoodAmount;
    }

    public void setDiscountFoodAmount(String discountFoodAmount) {
        this.discountFoodAmount = discountFoodAmount;
    }

    public String getRestaurantPizzaItemPrice() {
        return restaurantPizzaItemPrice;
    }

    public void setRestaurantPizzaItemPrice(String restaurantPizzaItemPrice) {
        this.restaurantPizzaItemPrice = restaurantPizzaItemPrice;
    }

    public String getSizeavailable() {
        return sizeavailable;
    }

    public void setSizeavailable(String sizeavailable) {
        this.sizeavailable = sizeavailable;
    }

    private int section;

    public SubItemsRecord(int section) {
        this.section = section;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public String getName() {
        return getRestaurantPizzaItemName();
    }

    @Override
    public int sectionPosition() {
        return section;
    }

    public boolean isIs_combo() {
        return is_combo;
    }

    public void setIs_combo(boolean is_combo) {
        this.is_combo = is_combo;
    }
}
