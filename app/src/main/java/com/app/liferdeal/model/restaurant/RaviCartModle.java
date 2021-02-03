package com.app.liferdeal.model.restaurant;

public class RaviCartModle {
    String item_id;
    String item_name;
    String size_item_id;
    String size_item_name;
    String extra_item_id;
    String extra_item_name;
    String price;
    String item_quantity;

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    int combo;

    public String getItemSubcatDetails() {
        return itemSubcatDetails;
    }

    public void setItemSubcatDetails(String itemSubcatDetails) {
        this.itemSubcatDetails = itemSubcatDetails;
    }

    String itemSubcatDetails;

    public RaviCartModle(String item_id ,String item_name,String size_item_id,String size_item_name,String extra_item_id,String extra_item_name,String price,String item_quantity,String itemSubcatDetails,int combo)
    {
        this.item_id = item_id;
        this.item_name = item_name;
        this.size_item_id = size_item_id;
        this.size_item_name = size_item_name;
        this.extra_item_id = extra_item_id;
        this.extra_item_name = extra_item_name;
        this.price = price;
        this.item_quantity = item_quantity;
        this.itemSubcatDetails = itemSubcatDetails;
        this.combo=combo;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getSize_item_id() {
        return size_item_id;
    }

    public void setSize_item_id(String size_item_id) {
        this.size_item_id = size_item_id;
    }

    public String getSize_item_name() {
        return size_item_name;
    }

    public void setSize_item_name(String size_item_name) {
        this.size_item_name = size_item_name;
    }

    public String getExtra_item_id() {
        return extra_item_id;
    }

    public void setExtra_item_id(String extra_item_id) {
        this.extra_item_id = extra_item_id;
    }

    public String getExtra_item_name() {
        return extra_item_name;
    }

    public void setExtra_item_name(String extra_item_name) {
        this.extra_item_name = extra_item_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }


}
