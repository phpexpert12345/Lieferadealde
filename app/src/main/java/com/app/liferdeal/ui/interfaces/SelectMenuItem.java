package com.app.liferdeal.ui.interfaces;

import com.app.liferdeal.model.menuitems.SubItemsRecord;
import com.app.liferdeal.util.PrefsHelper;

public interface SelectMenuItem {
    void getClickMenuData(int otemId, String itemName, String price,String subcatDetails);
    void getClickMenuDataRemoved(int otemId, String itemName, String price,int qty);
    void getClickComboItem(SubItemsRecord subItemsRecord);
}
