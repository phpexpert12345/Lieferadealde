package com.app.liferdeal.ui.interfaces;

public interface SelectMenuItem {
    void getClickMenuData(int otemId, String itemName, String price);
    void getClickMenuDataRemoved(int otemId, String itemName, String price,int qty);
}
