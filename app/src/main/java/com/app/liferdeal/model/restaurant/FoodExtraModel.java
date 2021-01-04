package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodExtraModel {
    @SerializedName("Menu_ItemExtraGroup")
    @Expose
    private List<MenuItemExtraGroup> menuItemExtraGroup = null;

    public List<MenuItemExtraGroup> getMenuItemExtraGroup() {
        return menuItemExtraGroup;
    }

    public void setMenuItemExtraGroup(List<MenuItemExtraGroup> menuItemExtraGroup) {
        this.menuItemExtraGroup = menuItemExtraGroup;
    }

    public class MenuItemExtraGroup {

        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("error_msg")
        @Expose
        private String errorMsg;
        @SerializedName("Group_ID")
        @Expose
        private Integer groupID;
        @SerializedName("Food_Group_Name")
        @Expose
        private String foodGroupName;
        @SerializedName("Food_addons_selection_Type")
        @Expose
        private String foodAddonsSelectionType;
        @SerializedName("subExtraItemsRecord")
        @Expose
        private List<SubExtraItemsRecord> subExtraItemsRecord = null;

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

        public Integer getGroupID() {
            return groupID;
        }

        public void setGroupID(Integer groupID) {
            this.groupID = groupID;
        }

        public String getFoodGroupName() {
            return foodGroupName;
        }

        public void setFoodGroupName(String foodGroupName) {
            this.foodGroupName = foodGroupName;
        }

        public String getFoodAddonsSelectionType() {
            return foodAddonsSelectionType;
        }

        public void setFoodAddonsSelectionType(String foodAddonsSelectionType) {
            this.foodAddonsSelectionType = foodAddonsSelectionType;
        }

        public List<SubExtraItemsRecord> getSubExtraItemsRecord() {
            return subExtraItemsRecord;
        }

        public void setSubExtraItemsRecord(List<SubExtraItemsRecord> subExtraItemsRecord) {
            this.subExtraItemsRecord = subExtraItemsRecord;
        }

        public class SubExtraItemsRecord {

            @SerializedName("ExtraID")
            @Expose
            private Integer extraID;
            @SerializedName("error")
            @Expose
            private String error;
            @SerializedName("error_msg")
            @Expose
            private String errorMsg;
            @SerializedName("Food_Addons_Name")
            @Expose
            private String foodAddonsName;
            @SerializedName("Food_Price_Addons")
            @Expose
            private String foodPriceAddons;
            @SerializedName("Free_Topping_Selection_allowed")
            @Expose
            private String freeToppingSelectionAllowed;

            public Integer getExtraID() {
                return extraID;
            }

            public void setExtraID(Integer extraID) {
                this.extraID = extraID;
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

            public String getFoodAddonsName() {
                return foodAddonsName;
            }

            public void setFoodAddonsName(String foodAddonsName) {
                this.foodAddonsName = foodAddonsName;
            }

            public String getFoodPriceAddons() {
                return foodPriceAddons;
            }

            public void setFoodPriceAddons(String foodPriceAddons) {
                this.foodPriceAddons = foodPriceAddons;
            }

            public String getFreeToppingSelectionAllowed() {
                return freeToppingSelectionAllowed;
            }

            public void setFreeToppingSelectionAllowed(String freeToppingSelectionAllowed) {
                this.freeToppingSelectionAllowed = freeToppingSelectionAllowed;
            }

        }
    }

}
