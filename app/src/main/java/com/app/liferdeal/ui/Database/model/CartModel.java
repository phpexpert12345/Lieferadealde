package com.app.liferdeal.ui.Database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "MyCart")
public class CartModel {
    @PrimaryKey
    private int id;

    public String getMainrestname() {
        return mainrestname;
    }

    public void setMainrestname(String mainrestname) {
        this.mainrestname = mainrestname;
    }

    public String getMainrestId() {
        return mainrestId;
    }

    public void setMainrestId(String mainrestId) {
        this.mainrestId = mainrestId;
    }

    public int getSubcatrestid() {
        return subcatrestid;
    }

    public void setSubcatrestid(int subcatrestid) {
        this.subcatrestid = subcatrestid;
    }

    public String getSubcatrestname() {
        return subcatrestname;
    }

    public void setSubcatrestname(String subcatrestname) {
        this.subcatrestname = subcatrestname;
    }



    @ColumnInfo(name = "mainrestname")
    public String mainrestname;

    @ColumnInfo(name = "mainrestId")
    public String mainrestId;

    @ColumnInfo(name = "subcatrestid")
    public int subcatrestid;

    public void setSubcatrestprice(double subcatrestprice) {
        this.subcatrestprice = subcatrestprice;
    }

    @ColumnInfo(name = "subcatrestname")
    public String subcatrestname;

    public double getSubcatrestprice() {
        return subcatrestprice;
    }


    @ColumnInfo(name = "subcatrestprice")
    public double subcatrestprice;

    @ColumnInfo(name = "extraitemprice")
    public String extraitemprice;

    @ColumnInfo(name = "sizeprice")
    public String sizeprice;

    @ColumnInfo(name = "extra_item_list_id")
    public int extra_item_list_id;



    @ColumnInfo(name = "extra_item_list_name")
    public String extra_item_list_name;

    public int getExtra_item_list_id() {
        return extra_item_list_id;
    }

    public void setExtra_item_list_id(int extra_item_list_id) {
        this.extra_item_list_id = extra_item_list_id;
    }

    public String getExtra_item_list_name() {
        return extra_item_list_name;
    }

    public void setExtra_item_list_name(String extra_item_list_name) {
        this.extra_item_list_name = extra_item_list_name;
    }


    public double getExtra_item_list_price() {
        return extra_item_list_price;
    }

    public void setExtra_item_list_price(double extra_item_list_price) {
        this.extra_item_list_price = extra_item_list_price;
    }

    @ColumnInfo(name = "extra_item_list_price")
    public double extra_item_list_price;

    public String getExtraitemprice() {
        return extraitemprice;
    }

    public void setExtraitemprice(String extraitemprice) {
        this.extraitemprice = extraitemprice;
    }

    public String getSizeprice() {
        return sizeprice;
    }

    public void setSizeprice(String sizeprice) {
        this.sizeprice = sizeprice;
    }

    public int getSize_item_list_id() {
        return size_item_list_id;
    }

    public void setSize_item_list_id(int size_item_list_id) {
        this.size_item_list_id = size_item_list_id;
    }

    public String getSize_item_list_name() {
        return size_item_list_name;
    }

    public void setSize_item_list_name(String size_item_list_name) {
        this.size_item_list_name = size_item_list_name;
    }


    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    @ColumnInfo(name = "itemid")
    public int itemid;

    public double getSize_item_list_price() {
        return size_item_list_price;
    }

    public void setSize_item_list_price(double size_item_list_price) {
        this.size_item_list_price = size_item_list_price;
    }

    @ColumnInfo(name = "size_item_list_id")
    public int size_item_list_id;

    @ColumnInfo(name = "size_item_list_name")
    public String size_item_list_name;

    @ColumnInfo(name = "size_item_list_price")
    public double size_item_list_price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
