
package com.app.liferdeal.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderFoodItem implements Parcelable {

    @SerializedName("Currency")
    private String currency;
    @SerializedName("ExtraTopping")
    private String extraTopping;
    @SerializedName("item_size")
    private String itemSize;
    @SerializedName("ItemsName")
    private String itemsName;
    @SerializedName("menuprice")
    private String menuprice;
    @SerializedName("quantity")
    private Long quantity;
public OrderFoodItem(){

}
    protected OrderFoodItem(Parcel in) {
        currency = in.readString();
        extraTopping = in.readString();
        itemSize = in.readString();
        itemsName = in.readString();
        menuprice = in.readString();
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readLong();
        }
    }

    public static final Creator<OrderFoodItem> CREATOR = new Creator<OrderFoodItem>() {
        @Override
        public OrderFoodItem createFromParcel(Parcel in) {
            return new OrderFoodItem(in);
        }

        @Override
        public OrderFoodItem[] newArray(int size) {
            return new OrderFoodItem[size];
        }
    };

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExtraTopping() {
        return extraTopping;
    }

    public void setExtraTopping(String extraTopping) {
        this.extraTopping = extraTopping;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getMenuprice() {
        return menuprice;
    }

    public void setMenuprice(String menuprice) {
        this.menuprice = menuprice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeString(extraTopping);
        dest.writeString(itemSize);
        dest.writeString(itemsName);
        dest.writeString(menuprice);
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(quantity);
        }
    }
}
