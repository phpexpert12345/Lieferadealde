package com.app.liferdeal.ui.interfaces;

public interface SelectMenuItem {
    void getClickMenuData(int otemId, String itemName, String price,String subcatDetails);
    void getClickMenuDataRemoved(int otemId, String itemName, String price,int qty);
}
