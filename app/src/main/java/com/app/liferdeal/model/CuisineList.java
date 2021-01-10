package com.app.liferdeal.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CuisineList implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cuisine_name")
    @Expose
    private String cuisineName;
    @SerializedName("cuisine_desc")
    @Expose
    private String cuisineDesc;
    @SerializedName("cuisine_img")
    @Expose
    private String cuisineImg;
    @SerializedName("seo_url_call")
    @Expose
    private String seoUrlCall;
    @SerializedName("error")
    @Expose
    private Integer error;

    protected CuisineList(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        cuisineName = in.readString();
        cuisineDesc = in.readString();
        cuisineImg = in.readString();
        seoUrlCall = in.readString();
        if (in.readByte() == 0) {
            error = null;
        } else {
            error = in.readInt();
        }
        isSelected = in.readByte() != 0;
    }

    public static final Creator<CuisineList> CREATOR = new Creator<CuisineList>() {
        @Override
        public CuisineList createFromParcel(Parcel in) {
            return new CuisineList(in);
        }

        @Override
        public CuisineList[] newArray(int size) {
            return new CuisineList[size];
        }
    };

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @SerializedName("isSelected")
    @Expose
    private boolean isSelected;

    public Integer getId() {
        return id;
    }

    public String getSeoUrlCall() {
        return seoUrlCall;
    }

    public void setSeoUrlCall(String seoUrlCall) {
        this.seoUrlCall = seoUrlCall;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public String getCuisineDesc() {
        return cuisineDesc;
    }

    public void setCuisineDesc(String cuisineDesc) {
        this.cuisineDesc = cuisineDesc;
    }

    public String getCuisineImg() {
        return cuisineImg;
    }

    public void setCuisineImg(String cuisineImg) {
        this.cuisineImg = cuisineImg;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(cuisineName);
        dest.writeString(cuisineDesc);
        dest.writeString(cuisineImg);
        dest.writeString(seoUrlCall);
        if (error == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(error);
        }
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}